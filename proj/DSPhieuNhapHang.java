import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

// Danh sách phiếu nhập hàng (làm giống DSHoaDon): đủ chức năng IQuanLy.
// Lập phiếu nhập cộng tồn kho, nên xóa phiếu phải trừ lại tồn kho.
public class DSPhieuNhapHang implements IQuanLy<PhieuNhapHang>, IFile {
    Scanner sc = new Scanner(System.in);
    private PhieuNhapHang[] danhSach;
    private int soLuong;

    public DSPhieuNhapHang() {
        danhSach = new PhieuNhapHang[10];
        soLuong = 0;
    }

    public int getSoLuong() {
        return soLuong;
    }

    @Override
    public void them(PhieuNhapHang p) {
        if (p == null) {
            return; // lập phiếu bị hủy (taoPhieuNhap đã báo lý do rồi)
        }
        if (timTheoMa(p.getMaPNH()) != null) {
            System.out.println("Ma phieu nhap " + p.getMaPNH() + " da ton tai, khong them!");
            return;
        }
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = p;
        soLuong++;
    }

    // Lập 1 phiếu nhập mới từ bàn phím (kèm các dòng chi tiết).
    // Cộng tồn kho, ghi các dòng vào QLDSCTPNH.dsctpnh.
    // Trả về null nếu bị hủy, khi đó them(null) không làm gì.
    public PhieuNhapHang taoPhieuNhap() {
        System.out.print("Ma phieu nhap: ");
        String maPNH = sc.nextLine();
        if (maPNH.isEmpty()) {
            System.out.println("Ma phieu nhap khong duoc de trong!");
            return null;
        }
        if (timTheoMa(maPNH) != null) {
            System.out.println("Ma phieu nhap da ton tai!");
            return null;
        }
        if (QLDSSP.dssp.getSoLuong() == 0) {
            System.out.println("Chua co san pham nao! Hay them san pham truoc.");
            return null;
        }
        System.out.print("Ma nha cung cap: ");
        NhaCungCap ncc = QLDSNCC.dsncc.timTheoMa(sc.nextLine());
        if (ncc == null) {
            System.out.println("Khong tim thay nha cung cap nay!");
            return null;
        }
        System.out.print("Ma nhan vien: ");
        NhanVien nv = QLDSNV.dsnv.timTheoMa(sc.nextLine());
        if (nv == null) {
            System.out.println("Khong tim thay nhan vien nay!");
            return null;
        }

        System.out.print("So san pham (so dong chi tiet) trong phieu nhap: ");
        int k = sc.nextInt();
        sc.nextLine();
        if (k < 1) {
            System.out.println("Phieu nhap phai co it nhat 1 san pham!");
            return null;
        }

        double tongTien = 0;
        int dem = 0; // số dòng chi tiết đã nhập thành công
        while (dem < k) {
            System.out.println("--- Dong thu " + (dem + 1) + " ---");
            System.out.print("Ma san pham: ");
            VanPhongPham sp = QLDSSP.dssp.timTheoMa(sc.nextLine());
            if (sp == null) {
                System.out.println("San pham chua co trong danh sach, nhap lai dong nay!");
                continue; // dem chưa tăng nên dòng này sẽ được nhập lại
            }
            System.out.print("So luong nhap: ");
            int sl = sc.nextInt();
            sc.nextLine();
            System.out.print("Don gia nhap: ");
            double dgn = sc.nextDouble();
            sc.nextLine();
            if (sl < 1 || dgn <= 0) {
                System.out.println("So luong phai >= 1 va don gia nhap phai > 0, nhap lai dong nay!");
                continue;
            }
            tongTien = tongTien + sl * dgn;
            QLDSSP.dssp.nhapKho(sp.getMaSP(), sl); // cộng tồn kho
            sp.setDonGia(dgn); // giá gốc của sản phẩm cập nhật theo giá nhập mới nhất
            ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
            ct.setMaPNH(maPNH);
            ct.setMaSP(sp.getMaSP());
            ct.setSoLuongNhap(sl);
            ct.setDonGiaNhap(dgn);
            QLDSCTPNH.dsctpnh.themDong(ct);
            dem++;
        }

        PhieuNhapHang pnh = new PhieuNhapHang();
        pnh.setMaPNH(maPNH);
        pnh.setMaNCC(ncc.getMaNCC());
        pnh.setMaNV(nv.getMaNV());
        pnh.setNgayNhap(LocalDate.now()); // phiếu nhập lập vào hôm nay
        pnh.setTongTien(tongTien);
        System.out.println("Da lap phieu nhap " + maPNH + ", tong tien: " + (long) tongTien);
        return pnh;
    }

    @Override
    public void nhapN() {
        System.out.print("Ban muon nhap bao nhieu phieu nhap ban dau? n = ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("--- Nhap phieu nhap thu " + (i + 1) + " ---");
            them(taoPhieuNhap());
        }
    }

    @Override
    public void themK() {
        System.out.print("Nhap k phieu nhap muon them: ");
        int k = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < k; i++) {
            System.out.println("--- Them phieu nhap thu " + (i + 1) + " ---");
            them(taoPhieuNhap());
        }
    }

    @Override
    public void xuat() {
        if (soLuong == 0) {
            System.out.println("Chua co phieu nhap nao!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat();
        }
    }

    @Override
    public PhieuNhapHang timTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return null;
        }
        return danhSach[vt];
    }

    private int timViTri(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaPNH().equals(ma)) {
                return i;
            }
        }
        return -1;
    }

    // Sửa theo mã: chọn thuộc tính bằng switch-case, sửa được TẤT CẢ thuộc tính của
    // phiếu nhập
    // (mã phiếu, nhà cung cấp, nhân viên nhập, ngày nhập, tổng tiền).
    // - Đổi mã phiếu: mã mới không được trống / trùng; các dòng chi tiết tự đổi
    // theo mã mới.
    // Muốn đổi sản phẩm / số lượng từng dòng: dùng chức năng Sửa ở phần chi tiết
    // phiếu nhập.
    @Override
    public void suaTheoMa(String ma) {
        PhieuNhapHang p = timTheoMa(ma);
        if (p == null) {
            System.out.println("Khong tim thay phieu nhap!");
            return;
        }
        int opt;
        do {
            System.out.println("\n--- SUA PHIEU NHAP ---");
            p.xuat();
            System.out.println("1. Ma phieu nhap");
            System.out.println("2. Nha cung cap");
            System.out.println("3. Nhan vien nhap");
            System.out.println("4. Ngay nhap");
            System.out.println("5. Tong tien");
            System.out.println("0. Xong");
            System.out.print("Chon thong tin can sua: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1: {
                    System.out.print("Ma phieu nhap moi: ");
                    String maMoi = sc.nextLine();
                    if (maMoi.isEmpty()) {
                        System.out.println("Ma khong duoc de trong!");
                    } else if (maMoi.equals(p.getMaPNH())) {
                        System.out.println("Ma moi giong ma cu, khong doi.");
                    } else if (timTheoMa(maMoi) != null) {
                        System.out.println("Ma phieu nhap da ton tai!");
                    } else {
                        QLDSCTPNH.dsctpnh.doiMaPNH(p.getMaPNH(), maMoi); // đổi luôn mã trong các dòng chi tiết
                        p.setMaPNH(maMoi);
                    }
                    break;
                }
                case 2: {
                    System.out.print("Ma nha cung cap moi: ");
                    NhaCungCap ncc = QLDSNCC.dsncc.timTheoMa(sc.nextLine());
                    if (ncc == null) {
                        System.out.println("Khong tim thay nha cung cap nay!");
                    } else {
                        p.setMaNCC(ncc.getMaNCC());
                    }
                    break;
                }
                case 3: {
                    System.out.print("Ma nhan vien moi: ");
                    NhanVien nv = QLDSNV.dsnv.timTheoMa(sc.nextLine());
                    if (nv == null) {
                        System.out.println("Khong tim thay nhan vien nay!");
                    } else {
                        p.setMaNV(nv.getMaNV());
                    }
                    break;
                }
                case 4: {
                    System.out.print("Ngay nhap moi (yyyy-MM-dd): ");
                    try {
                        p.setNgayNhap(LocalDate.parse(sc.nextLine()));
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
                        p.setTongTien(tt);
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

    // Xóa phiếu nhập = trừ lại tồn kho + xóa các dòng chi tiết.
    // Không xóa được nếu hàng đã bán bớt (tồn không đủ để trừ). Tự in lý do; trả về
    // true nếu đã xóa.
    // (Giá gốc sản phẩm đã cập nhật theo giá nhập thì không khôi phục lại giá cũ.)
    @Override
    public boolean xoaTheoMa(String ma) {
        int index = timViTri(ma);
        if (index == -1) {
            System.out.println("Khong tim thay phieu nhap!");
            return false;
        }
        if (!QLDSCTPNH.dsctpnh.coTheHoanTac(ma)) {
            System.out.println("Khong the xoa: hang cua phieu nay da ban bot nen ton kho khong du de tru lai!");
            return false;
        }
        QLDSCTPNH.dsctpnh.hoanTacKho(ma);
        QLDSCTPNH.dsctpnh.xoaTheoMaPNH(ma);
        for (int i = index; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1];
        }
        danhSach[soLuong - 1] = null;
        soLuong--;
        return true;
    }

    // Tìm kiếm nhiều khóa: mã (chính xác / gần đúng), tên nhà cung cấp (gần đúng),
    // nhân viên, khoảng tổng tiền
    @Override
    public void timKiem() {
        System.out.println("1. Theo ma phieu nhap (chinh xac)");
        System.out.println("2. Theo ten nha cung cap (gan dung)");
        System.out.println("3. Theo khoang tong tien");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        int dem = 0;
        switch (opt) {
            case 1: {
                System.out.print("Nhap ma phieu nhap: ");
                PhieuNhapHang p = timTheoMa(sc.nextLine());
                if (p != null) {
                    p.xuat();
                    dem = 1;
                }
                break;
            }
            case 2: {
                System.out.print("Nhap tu khoa (ten nha cung cap): ");
                String tuKhoa = sc.nextLine().toLowerCase();
                for (int i = 0; i < soLuong; i++) {
                    NhaCungCap ncc = QLDSNCC.dsncc.timTheoMa(danhSach[i].getMaNCC());
                    if (ncc != null && ncc.getTenNCC().toLowerCase().contains(tuKhoa)) {
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

    // ===== THỐNG KÊ PHIẾU NHẬP =====
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

    // ===== g. THỐNG KÊ NHIỀU KHÓA PHIẾU NHẬP (in ra màn hình) =====
    // Menu thống kê: mỗi lựa chọn là một khóa thống kê khác nhau, gọi hàm riêng bên
    // dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach phieu nhap rong!");
            return;
        }
        System.out.println("1. Tong chi nhap hang tu ngay A den ngay B");
        System.out.println("2. Chi nhap hang theo quy trong nam");
        System.out.println("3. Tong tien nhap cua tung nha cung cap theo tung nam (bang)");
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
                thongKeTheoNCC();
                break;
            default:
                break;
        }
    }

    // 1. Số phiếu và tổng chi nhập hàng từ ngày A đến ngày B
    public void thongKeTheoKhoang() {
        LocalDate a = docNgay("Ngay bat dau");
        LocalDate b = docNgay("Ngay ket thuc");
        if (a.isAfter(b)) {
            System.out.println("Ngay bat dau phai truoc ngay ket thuc!");
            return;
        }
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            LocalDate d = danhSach[i].getNgayNhap();
            if (!d.isBefore(a) && !d.isAfter(b)) {
                dem++;
            }
        }
        System.out.println("THONG KE TU " + a + " DEN " + b);
        System.out.println("So phieu nhap | Tong chi");
        System.out.println("-------------------------");
        System.out.println(dem + " | " + (long) tongChiTuNgayDenNgay(a, b));
    }

    // 2. Bảng chi nhập hàng của từng quý trong một năm (có dòng tổng cả năm)
    public void thongKeTheoQuy() {
        System.out.print("Nam: ");
        int nam = sc.nextInt();
        sc.nextLine();
        System.out.println("CHI NHAP HANG THEO QUY - NAM " + nam);
        System.out.println("Quy | Tong chi");
        System.out.println("---------------");
        double tong = 0;
        for (int q = 1; q <= 4; q++) {
            double chi = tongChiTheoQuy(q, nam);
            System.out.println(q + " | " + (long) chi);
            tong += chi;
        }
        System.out.println("---------------");
        System.out.println("TONG | " + (long) tong);
    }

    // 3. Bảng tổng tiền nhập: mỗi dòng là 1 nhà cung cấp, mỗi cột là 1 năm (tự lấy
    // các năm có phiếu nhập)
    public void thongKeTheoNCC() {
        int[] dsNam = cacNamCoPhieuNhap();
        String tieuDe = "Ma NCC - Ten";
        for (int j = 0; j < dsNam.length; j++) {
            tieuDe += " | " + dsNam[j];
        }
        System.out.println(tieuDe + " | Tong cong");
        System.out.println("------------------------------------------");
        double[] tongCot = new double[dsNam.length]; // tổng tiền của từng năm
        double tatCa = 0;
        for (int i = 0; i < soLuong; i++) {
            String maNCC = danhSach[i].getMaNCC();
            boolean daXet = false; // mỗi nhà cung cấp chỉ in 1 dòng
            for (int j = 0; j < i; j++) {
                if (danhSach[j].getMaNCC().equals(maNCC)) {
                    daXet = true;
                }
            }
            if (daXet) {
                continue;
            }
            String ten = "";
            NhaCungCap ncc = QLDSNCC.dsncc.timTheoMa(maNCC);
            if (ncc != null) {
                ten = " - " + ncc.getTenNCC();
            }
            String dong = maNCC + ten;
            double tongNCC = 0;
            for (int j = 0; j < dsNam.length; j++) {
                double tien = tongTienTheoNCCVaNam(maNCC, dsNam[j]);
                dong += " | " + (long) tien;
                tongNCC += tien;
                tongCot[j] += tien;
            }
            System.out.println(dong + " | " + (long) tongNCC);
            tatCa += tongNCC;
        }
        String dongTong = "TONG";
        for (int j = 0; j < dsNam.length; j++) {
            dongTong += " | " + (long) tongCot[j];
        }
        System.out.println("------------------------------------------");
        System.out.println(dongTong + " | " + (long) tatCa);
    }

    // ===== Các hàm tính tổng (HD dùng lại để tính Tổng chi) =====
    public double tongChiTuNgayDenNgay(LocalDate a, LocalDate b) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            LocalDate d = danhSach[i].getNgayNhap();
            if (!d.isBefore(a) && !d.isAfter(b)) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    public double tongChiTheoQuy(int quy, int nam) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            LocalDate d = danhSach[i].getNgayNhap();
            if (d.getYear() == nam && (d.getMonthValue() - 1) / 3 + 1 == quy) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    public double tongTienTheoNCCVaNam(String maNCC, int nam) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNCC().equals(maNCC) && danhSach[i].getNgayNhap().getYear() == nam) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    // ===== Dùng để chặn xóa và để thống kê theo nhà cung cấp / nhân viên =====
    public boolean coPhieuCuaNV(String maNV) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(maNV)) {
                return true;
            }
        }
        return false;
    }

    public int soPhieuTheoNCC(String maNCC) {
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNCC().equals(maNCC)) {
                dem++;
            }
        }
        return dem;
    }

    public double tongTienTheoNCC(String maNCC) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNCC().equals(maNCC)) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    // ===== Dùng khi ĐỔI MÃ nhà cung cấp / nhân viên: đổi luôn mã trong mọi phiếu
    // nhập =====
    public void doiMaNCC(String maCu, String maMoi) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNCC().equals(maCu)) {
                danhSach[i].setMaNCC(maMoi);
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

    // Các năm có phiếu nhập (không trùng, sắp xếp tăng dần) - dùng cho bảng thống
    // kê theo năm
    private int[] cacNamCoPhieuNhap() {
        int[] nam = new int[soLuong];
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            int y = danhSach[i].getNgayNhap().getYear();
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
                PhieuNhapHang pnh = new PhieuNhapHang();
                pnh.fromFileString(fs.nextLine());
                them(pnh);
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
