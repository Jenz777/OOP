import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

// Danh sách hóa đơn: đủ chức năng như các danh sách khác (IQuanLy).
// Lưu ý nghiệp vụ: lập hóa đơn phải trừ tồn kho + cộng tiền cho khách, nên sửa / xóa cũng phải hoàn tác các việc đó.
public class DSHoaDon implements IQuanLy<HoaDon>, IFile {
    Scanner sc = new Scanner(System.in);
    private HoaDon[] danhSach;
    private int soLuong;

    public DSHoaDon() {
        danhSach = new HoaDon[10];
        soLuong = 0;
    }

    public int getSoLuong() {
        return soLuong;
    }

    @Override
    public void them(HoaDon hd) {
        if (hd == null) {
            return; // lập hóa đơn bị hủy (taoHoaDon đã báo lý do rồi)
        }
        if (timTheoMa(hd.getMaHD()) != null) {
            System.out.println("Ma hoa don " + hd.getMaHD() + " da ton tai, khong them!");
            return;
        }
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = hd;
        soLuong++;
    }

    // Lập 1 hóa đơn mới từ bàn phím (kèm các dòng chi tiết).
    // Trừ tồn kho, ghi các dòng vào QLDSCTHD.dscthd, cộng tiền mua cho khách.
    // Trả về null nếu bị hủy (sai mã, thiếu dữ liệu...), khi đó them(null) không
    // làm gì.
    public HoaDon taoHoaDon() {
        System.out.print("Ma hoa don: ");
        String maHD = sc.nextLine();
        if (maHD.isEmpty()) {
            System.out.println("Ma hoa don khong duoc de trong!");
            return null;
        }
        if (timTheoMa(maHD) != null) {
            System.out.println("Ma hoa don da ton tai!");
            return null;
        }
        if (QLDSSP.dssp.getSoLuong() == 0) {
            System.out.println("Chua co san pham nao de ban! Hay them san pham truoc.");
            return null;
        }
        System.out.print("Ma nhan vien: ");
        NhanVien nv = QLDSNV.dsnv.timTheoMa(sc.nextLine());
        if (nv == null) {
            System.out.println("Khong tim thay nhan vien nay!");
            return null;
        }
        System.out.print("Ma khach hang: ");
        KhachHang kh = QLDSKH.dskh.timTheoMa(sc.nextLine());
        if (kh == null) {
            System.out.println("Khong tim thay khach hang nay!");
            return null;
        }

        System.out.print("So san pham (so dong chi tiet) trong hoa don: ");
        int k = sc.nextInt();
        sc.nextLine();
        if (k < 1) {
            System.out.println("Hoa don phai co it nhat 1 san pham!");
            return null;
        }

        double tongTien = 0;
        int dem = 0; // số dòng chi tiết đã nhập thành công
        while (dem < k) {
            System.out.println("--- Dong thu " + (dem + 1) + " ---");
            System.out.print("Ma san pham: ");
            VanPhongPham sp = QLDSSP.dssp.timTheoMa(sc.nextLine());
            if (sp == null) {
                System.out.println("Khong tim thay san pham, nhap lai dong nay!");
                continue; // dem chưa tăng nên dòng này sẽ được nhập lại
            }
            System.out.print("So luong ban (con " + sp.getSoLuongTon() + "): ");
            int sl = sc.nextInt();
            sc.nextLine();
            // banHang vừa kiểm tra đủ hàng vừa trừ tồn kho
            if (!QLDSSP.dssp.banHang(sp.getMaSP(), sl)) {
                System.out.println("So luong khong hop le hoac khong du hang, nhap lai dong nay!");
                continue;
            }
            double dg = sp.tinhGiaBan(); // ĐA HÌNH: giá bán tùy sp là But hay TapViet
            tongTien = tongTien + sl * dg;
            ChiTietHoaDon ct = new ChiTietHoaDon();
            ct.setMaHD(maHD);
            ct.setMaSP(sp.getMaSP());
            ct.setSoLuong(sl);
            ct.setGiaBan(dg);
            QLDSCTHD.dscthd.themDong(ct);
            dem++;
        }

        HoaDon hd = new HoaDon();
        hd.setMaHD(maHD);
        hd.setMaNV(nv.getMaNV());
        hd.setMaKH(kh.getMaKH());
        hd.setNgayLap(LocalDate.now()); // hóa đơn lập vào hôm nay
        hd.setTongTien(tongTien);
        kh.congTienMuaHang(tongTien); // cộng vào tổng tiền mua của khách
        System.out.println("Da lap hoa don " + maHD + ", tong tien: " + (long) tongTien);
        return hd;
    }

    @Override
    public void nhapN() {
        System.out.print("Ban muon nhap bao nhieu hoa don ban dau? n = ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("--- Nhap hoa don thu " + (i + 1) + " ---");
            them(taoHoaDon());
        }
    }

    @Override
    public void themK() {
        System.out.print("Nhap k hoa don muon them: ");
        int k = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < k; i++) {
            System.out.println("--- Them hoa don thu " + (i + 1) + " ---");
            them(taoHoaDon());
        }
    }

    @Override
    public void xuat() {
        if (soLuong == 0) {
            System.out.println("Chua co hoa don nao!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat();
        }
    }

    @Override
    public HoaDon timTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return null;
        }
        return danhSach[vt];
    }

    private int timViTri(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaHD().equals(ma)) {
                return i;
            }
        }
        return -1;
    }

    // Sửa theo mã: chọn thuộc tính bằng switch-case, sửa được TẤT CẢ thuộc tính của
    // hóa đơn
    // (mã hóa đơn, nhân viên, khách hàng, ngày lập, tổng tiền).
    // - Đổi mã hóa đơn: mã mới không được trống / trùng; các dòng chi tiết tự đổi
    // theo mã mới.
    // - Đổi khách hàng: tiền mua được chuyển từ khách cũ sang khách mới.
    // - Sửa tổng tiền: tiền mua của khách tự tăng / giảm đúng phần chênh lệch.
    // Muốn đổi sản phẩm / số lượng từng dòng: dùng chức năng Sửa ở phần chi tiết
    // hóa đơn.
    @Override
    public void suaTheoMa(String ma) {
        HoaDon hd = timTheoMa(ma);
        if (hd == null) {
            System.out.println("Khong tim thay hoa don!");
            return;
        }
        int opt;
        do {
            System.out.println("\n--- SUA HOA DON ---");
            hd.xuat();
            System.out.println("1. Ma hoa don");
            System.out.println("2. Nhan vien lap");
            System.out.println("3. Khach hang");
            System.out.println("4. Ngay lap");
            System.out.println("5. Tong tien");
            System.out.println("0. Xong");
            System.out.print("Chon thong tin can sua: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1: {
                    System.out.print("Ma hoa don moi: ");
                    String maMoi = sc.nextLine();
                    if (maMoi.isEmpty()) {
                        System.out.println("Ma khong duoc de trong!");
                    } else if (maMoi.equals(hd.getMaHD())) {
                        System.out.println("Ma moi giong ma cu, khong doi.");
                    } else if (timTheoMa(maMoi) != null) {
                        System.out.println("Ma hoa don da ton tai!");
                    } else {
                        QLDSCTHD.dscthd.doiMaHD(hd.getMaHD(), maMoi); // đổi luôn mã trong các dòng chi tiết
                        hd.setMaHD(maMoi);
                    }
                    break;
                }
                case 2: {
                    System.out.print("Ma nhan vien moi: ");
                    NhanVien nv = QLDSNV.dsnv.timTheoMa(sc.nextLine());
                    if (nv == null) {
                        System.out.println("Khong tim thay nhan vien nay!");
                    } else {
                        hd.setMaNV(nv.getMaNV());
                    }
                    break;
                }
                case 3: {
                    System.out.print("Ma khach hang moi: ");
                    KhachHang khMoi = QLDSKH.dskh.timTheoMa(sc.nextLine());
                    if (khMoi == null) {
                        System.out.println("Khong tim thay khach hang nay!");
                    } else {
                        // chuyển tiền mua từ khách cũ sang khách mới
                        KhachHang khCu = QLDSKH.dskh.timTheoMa(hd.getMaKH());
                        if (khCu != null) {
                            khCu.congTienMuaHang(-hd.getTongTien());
                        }
                        khMoi.congTienMuaHang(hd.getTongTien());
                        hd.setMaKH(khMoi.getMaKH());
                    }
                    break;
                }
                case 4: {
                    System.out.print("Ngay lap moi (yyyy-MM-dd): ");
                    try {
                        hd.setNgayLap(LocalDate.parse(sc.nextLine()));
                    } catch (DateTimeParseException e) {
                        System.out.println("Ngay khong hop le!");
                    }
                    break;
                }
                case 5: {
                    System.out.print("Tong tien moi (>= 0): ");
                    double tt = sc.nextDouble();
                    sc.nextLine();
                    if (tt < 0) {
                        System.out.println("Tong tien khong duoc am, giu nguyen!");
                    } else {
                        KhachHang kh = QLDSKH.dskh.timTheoMa(hd.getMaKH());
                        if (kh != null) {
                            kh.congTienMuaHang(tt - hd.getTongTien()); // tiền mua của khách theo kịp
                        }
                        hd.setTongTien(tt);
                    }
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (opt != 0);
        System.out.println("Da sua xong!");
    }

    // Xóa hóa đơn = hoàn tác lập hóa đơn: trả hàng về kho, trừ tiền mua của khách,
    // xóa các dòng chi tiết.
    // Tự in lý do khi không xóa được; trả về true nếu đã xóa.
    @Override
    public boolean xoaTheoMa(String ma) {
        int index = timViTri(ma);
        if (index == -1) {
            System.out.println("Khong tim thay hoa don!");
            return false;
        }
        HoaDon hd = danhSach[index];
        QLDSCTHD.dscthd.hoanKhoTheoMaHD(ma);
        KhachHang kh = QLDSKH.dskh.timTheoMa(hd.getMaKH());
        if (kh != null) {
            kh.congTienMuaHang(-hd.getTongTien());
        }
        QLDSCTHD.dscthd.xoaTheoMaHD(ma);
        for (int i = index; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1];
        }
        danhSach[soLuong - 1] = null;
        soLuong--;
        return true;
    }

    // Tìm kiếm nhiều khóa: mã (chính xác / gần đúng), tên khách (gần đúng), nhân
    // viên, khoảng tổng tiền
    @Override
    public void timKiem() {
        System.out.println("1. Theo ma hoa don (chinh xac)");
        System.out.println("2. Theo ten khach hang (gan dung)");
        System.out.println("3. Theo khoang tong tien");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        int dem = 0;
        switch (opt) {
            case 1: {
                System.out.print("Nhap ma hoa don: ");
                HoaDon hd = timTheoMa(sc.nextLine());
                if (hd != null) {
                    hd.xuat();
                    dem = 1;
                }
                break;
            }
            case 2: {
                System.out.print("Nhap tu khoa (ten khach): ");
                String tuKhoa = sc.nextLine().toLowerCase();
                for (int i = 0; i < soLuong; i++) {
                    KhachHang kh = QLDSKH.dskh.timTheoMa(danhSach[i].getMaKH());
                    if (kh != null && (kh.getHoLot() + " " + kh.getTen()).toLowerCase().contains(tuKhoa)) {
                        danhSach[i].xuat();
                        dem++;
                    }
                }
                break;
            }
            case 3: {
                System.out.print("Tong tien tu: ");
                double min = sc.nextDouble();
                sc.nextLine();
                System.out.print("Den: ");
                double max = sc.nextDouble();
                sc.nextLine();
                for (int i = 0; i < soLuong; i++) {
                    if (danhSach[i].getTongTien() >= min && danhSach[i].getTongTien() <= max) {
                        danhSach[i].xuat();
                        dem++;
                    }
                }
                break;
            }
            default:
                System.out.println("Lua chon khong hop le!");
                return;
        }
        if (dem == 0) {
            System.out.println("Khong tim thay ket qua nao!");
        }
    }

    // ===== THỐNG KÊ HÓA ĐƠN =====
    // Nhập ngày dạng yyyy-MM-dd (giống ngày đang hiển thị trong danh sách), sai thì
    // nhập lại
    private LocalDate docNgay(String nhan) {
        while (true) {
            System.out.print(nhan + " (yyyy-MM-dd): ");
            try {
                return LocalDate.parse(sc.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Ngay khong hop le, nhap lai!");
            }
        }
    }

    // ===== g. THỐNG KÊ NHIỀU KHÓA HÓA ĐƠN (in ra màn hình) =====
    // Menu thống kê: mỗi lựa chọn là một khóa thống kê khác nhau, gọi hàm riêng bên
    // dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach hoa don rong!");
            return;
        }
        System.out.println("1. Thu - chi - loi nhuan tu ngay A den ngay B");
        System.out.println("2. Thu - chi - loi nhuan theo quy trong nam");
        System.out.println("3. Tong tien hoa don cua tung khach hang theo tung nam (bang)");
        System.out.println("0. Thoat");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeTheoKhoang();
                break;
            case 2:
                thongKeTheoQuy();
                break;
            case 3:
                thongKeTheoKH();
                break;
            default:
                break;
        }
    }

    // 1. Thu (hóa đơn) - Chi (phiếu nhập) - Lợi nhuận từ ngày A đến ngày B
    public void thongKeTheoKhoang() {
        LocalDate a = docNgay("Ngay bat dau");
        LocalDate b = docNgay("Ngay ket thuc");
        if (a.isAfter(b)) {
            System.out.println("Ngay bat dau phai truoc ngay ket thuc!");
            return;
        }
        double thu = tongThuTuNgayDenNgay(a, b);
        double chi = QLDSPNH.dspnh.tongChiTuNgayDenNgay(a, b);
        System.out.println("THONG KE TU " + a + " DEN " + b);
        System.out.println("Tong thu | Tong chi | Loi nhuan");
        System.out.println("-------------------------------");
        System.out.println((long) thu + " | " + (long) chi + " | " + (long) (thu - chi));
    }

    // 2. Bảng Thu - Chi - Lợi nhuận của từng quý trong một năm (có dòng tổng cả
    // năm)
    public void thongKeTheoQuy() {
        System.out.print("Nam: ");
        int nam = sc.nextInt();
        sc.nextLine();
        System.out.println("THONG KE THEO QUY - NAM " + nam);
        System.out.println("Quy | Tong thu | Tong chi | Loi nhuan");
        System.out.println("------------------------------------");
        double tongThu = 0;
        double tongChi = 0;
        for (int q = 1; q <= 4; q++) {
            double thu = tongThuTheoQuy(q, nam);
            double chi = QLDSPNH.dspnh.tongChiTheoQuy(q, nam);
            System.out.println(q + " | " + (long) thu + " | " + (long) chi + " | " + (long) (thu - chi));
            tongThu += thu;
            tongChi += chi;
        }
        System.out.println("------------------------------------");
        System.out.println("TONG | " + (long) tongThu + " | " + (long) tongChi + " | " + (long) (tongThu - tongChi));
    }

    // 3. Bảng tổng tiền hóa đơn: mỗi dòng là 1 khách hàng, mỗi cột là 1 năm (tự lấy
    // các năm có hóa đơn)
    public void thongKeTheoKH() {
        int[] dsNam = cacNamCoHoaDon();
        String tieuDe = "Ma KH - Ho ten";
        for (int j = 0; j < dsNam.length; j++) {
            tieuDe += " | " + dsNam[j];
        }
        System.out.println(tieuDe + " | Tong cong");
        System.out.println("------------------------------------------");
        double[] tongCot = new double[dsNam.length]; // tổng tiền của từng năm
        double tatCa = 0;
        for (int i = 0; i < soLuong; i++) {
            String maKH = danhSach[i].getMaKH();
            boolean daXet = false; // mỗi khách chỉ in 1 dòng
            for (int j = 0; j < i; j++) {
                if (danhSach[j].getMaKH().equals(maKH)) {
                    daXet = true;
                }
            }
            if (daXet) {
                continue;
            }
            String ten = "";
            KhachHang kh = QLDSKH.dskh.timTheoMa(maKH);
            if (kh != null) {
                ten = " - " + kh.getHoLot() + " " + kh.getTen();
            }
            String dong = maKH + ten;
            double tongKH = 0;
            for (int j = 0; j < dsNam.length; j++) {
                double tien = tongTienTheoKHVaNam(maKH, dsNam[j]);
                dong += " | " + (long) tien;
                tongKH += tien;
                tongCot[j] += tien;
            }
            System.out.println(dong + " | " + (long) tongKH);
            tatCa += tongKH;
        }
        String dongTong = "TONG";
        for (int j = 0; j < dsNam.length; j++) {
            dongTong += " | " + (long) tongCot[j];
        }
        System.out.println("------------------------------------------");
        System.out.println(dongTong + " | " + (long) tatCa);
    }

    // ===== Các hàm tính tổng (HD dùng lại để tính Thu - Chi) =====
    // Tổng thu từ ngày a đến ngày b (tính cả hai đầu)
    public double tongThuTuNgayDenNgay(LocalDate a, LocalDate b) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            LocalDate d = danhSach[i].getNgayLap();
            if (!d.isBefore(a) && !d.isAfter(b)) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    // Quý 1 = tháng 1-3, quý 2 = tháng 4-6, quý 3 = tháng 7-9, quý 4 = tháng 10-12
    public double tongThuTheoQuy(int quy, int nam) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            LocalDate d = danhSach[i].getNgayLap();
            if (d.getYear() == nam && (d.getMonthValue() - 1) / 3 + 1 == quy) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    public double tongTienTheoKHVaNam(String maKH, int nam) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaKH().equals(maKH) && danhSach[i].getNgayLap().getYear() == nam) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    // ===== Dùng để chặn xóa khách / nhân viên đã có hóa đơn =====
    public boolean coHoaDonCuaKH(String maKH) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaKH().equals(maKH)) {
                return true;
            }
        }
        return false;
    }

    public boolean coHoaDonCuaNV(String maNV) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(maNV)) {
                return true;
            }
        }
        return false;
    }

    // ===== Dùng cho thống kê nhân viên (DSNhanVien gọi qua QLDSHD.dshd) =====

    public double doanhThuTheoNV(String maNV) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(maNV)) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    // ===== Dùng khi ĐỔI MÃ khách hàng / nhân viên: đổi luôn mã trong mọi hóa đơn
    // =====
    public void doiMaKH(String maCu, String maMoi) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaKH().equals(maCu)) {
                danhSach[i].setMaKH(maMoi);
            }
        }
    }

    public void doiMaNV(String maCu, String maMoi) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(maCu)) {
                danhSach[i].setMaNV(maMoi);
            }
        }
    }

    // ===== Dùng cho thống kê nhân viên (DSNhanVien gọi qua QLDSHD.dshd) =====
    public int soHoaDonTheoNVVaNam(String maNV, int nam) {
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(maNV) && danhSach[i].getNgayLap().getYear() == nam) {
                dem++;
            }
        }
        return dem;
    }

    public double tongTienTheoNVVaNam(String maNV, int nam) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(maNV) && danhSach[i].getNgayLap().getYear() == nam) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    // Các năm có hóa đơn (không trùng, sắp xếp tăng dần) - dùng cho bảng thống kê
    // theo năm
    private int[] cacNamCoHoaDon() {
        int[] nam = new int[soLuong];
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            int y = danhSach[i].getNgayLap().getYear();
            boolean daCo = false;
            for (int j = 0; j < dem; j++) {
                if (nam[j] == y) {
                    daCo = true;
                }
            }
            if (!daCo) {
                nam[dem] = y;
                dem++;
            }
        }
        int[] ketQua = Arrays.copyOf(nam, dem);
        Arrays.sort(ketQua);
        return ketQua;
    }

    // ===== ĐỌC / GHI FILE TXT (interface IFile) =====
    // Mỗi đối tượng 1 dòng, các thuộc tính ngăn cách nhau bằng dấu ;
    @Override
    public void docFile(String tenFile) {
        try {
            Scanner fs = new Scanner(new File(tenFile));
            while (fs.hasNextLine()) {
                HoaDon hd = new HoaDon();
                hd.fromFileString(fs.nextLine());
                them(hd);
            }
            fs.close();
        } catch (Exception e) {
            System.out.println("Khong doc duoc file ");
        }
    }

    @Override
    public void ghiFile(String tenFile) {
        try {
            PrintWriter pw = new PrintWriter(tenFile);
            for (int i = 0; i < soLuong; i++) {
                pw.println(danhSach[i].toFileString());
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("Khong ghi duoc file " + tenFile);
        }
    }
}
