package danhsach;

import interfaces.IFile;
import interfaces.IQuanLy;
import model.ChiTietHoaDon;
import model.HoaDon;
import model.KhachHang;
import model.NhanVien;
import model.VanPhongPham;
import quanly.QLDSCTHD;
import quanly.QLDSKH;
import quanly.QLDSNV;
import quanly.QLDSPNH;
import quanly.QLDSSP;

import java.io.*;
import java.time.LocalDate;
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

    public void them(HoaDon hd) {
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
    public HoaDon taoHoaDon() {
        System.out.print("Ma hoa don: ");
        String maHD = sc.nextLine();
        while (timTheoMa(maHD) != null) {
            System.out.println("Ma hoa don da ton tai!,nhap lai dong nay!");
            System.out.print("Ma hoa don: ");
            maHD = sc.nextLine();
        }
        System.out.print("Ma nhan vien: ");
        NhanVien nv = QLDSNV.dsnv.timTheoMa(sc.nextLine());
        while (nv == null) {
            System.out.println("Khong tim thay nhan vien nay,nhap lai dong nay!");
            System.out.print("Ma nhan vien: ");
            nv = QLDSNV.dsnv.timTheoMa(sc.nextLine());
        }
        System.out.print("Ma khach hang: ");
        KhachHang kh = QLDSKH.dskh.timTheoMa(sc.nextLine());
        while (kh == null) {
            System.out.println("Khong tim thay khach hang nay!,nhap lai dong nay!");
            System.out.print("Ma khach hang: ");
            kh = QLDSKH.dskh.timTheoMa(sc.nextLine());
        }

        System.out.print("So san pham (so dong chi tiet) trong hoa don: ");
        int k = sc.nextInt();
        sc.nextLine();
        while (k < 1) {
            System.out.println("Hoa don phai co it nhat 1 san pham,nhap lai dong nay!");
            System.out.print("So san pham (so dong chi tiet) trong hoa don: ");
            k = sc.nextInt();
            sc.nextLine();
        }

        double tongTien = 0;
        int dem = 0; // số dòng chi tiết đã nhập thành công
        while (dem < k) {
            System.out.println("--- Dong thu " + (dem + 1) + " ---");
            System.out.print("Ma san pham: ");
            VanPhongPham sp = QLDSSP.dssp.timTheoMa(sc.nextLine());
            while (sp == null) {
                System.out.println("Khong tim thay san pham, nhap lai dong nay!");
                System.out.print("Ma san pham: ");
                sp = QLDSSP.dssp.timTheoMa(sc.nextLine());
            }
            System.out.print("So luong ban (con " + sp.getSoLuongTon() + "): ");
            int sl = sc.nextInt();
            sc.nextLine();
            // banHang vừa kiểm tra đủ hàng vừa trừ tồn kho
            while (QLDSSP.dssp.banHang(sp.getMaSP(), sl) == false) {
                System.out.println("So luong khong hop le hoac khong du hang, nhap lai dong nay!");
                System.out.print("So luong ban (con " + sp.getSoLuongTon() + "): ");
                sl = sc.nextInt();
                sc.nextLine();
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
        if (soLuong > 0) {
            System.out.println("Dang co " + soLuong + " hoa don, neu nhap moi se xoa het");
            System.out.println("Ban co muon xoa? Nhap 1 xac nhan, 2 de huy");
            int t = sc.nextInt();
            sc.nextLine();
            if (t != 1) {
                System.out.println("Da huy!");
                return;
            }
        }
        danhSach = new HoaDon[10];
        soLuong = 0;
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

    // Sửa cơ bản: chọn thuộc tính rồi gán giá trị mới (không sửa mã vì mã là khóa).
    @Override
    public void suaTheoMa(String ma) {
        HoaDon hd = timTheoMa(ma);
        if (hd == null) {
            System.out.println("Khong tim thay hoa don!");
            return;
        }
        System.out.println("Thong tin hoa don:");
        hd.xuat();
        System.out.println("---------------------------");
        System.out.println("1. Ma nhan vien");
        System.out.println("2. Ma khach hang");
        System.out.println("3. Ngay lap");
        System.out.println("4. Tong tien");
        System.out.print("Chon thong tin can sua: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                System.out.print("Ma nhan vien moi: ");
                hd.setMaNV(sc.nextLine());
                break;
            case 2:
                System.out.print("Ma khach hang moi: ");
                hd.setMaKH(sc.nextLine());
                break;
            case 3:
                hd.setNgayLap(docNgay("Ngay lap moi"));
                break;
            case 4:
                System.out.print("Tong tien moi: ");
                hd.setTongTien(sc.nextDouble());
                sc.nextLine();
                break;
            default:
                System.out.println("Lua chon khong hop le!");
        }
    }

    // Xóa cơ bản: bỏ phần tử khỏi mảng rồi dồn các phần tử sau lên.
    @Override
    public boolean xoaTheoMa(String ma) {
        int index = timViTri(ma);
        if (index == -1) {
            return false;
        }
        for (int i = index; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1];
        }
        danhSach[soLuong - 1] = null;
        soLuong--;
        return true;
    }

    // Tìm kiếm nhiều khóa: mã (chính xác / gần đúng), tên khách (gần đúng), nhân
    // viên, khoảng tổng tiền
    // Tim gan dung theo ten khach hang, tra ve danh sach moi
    public DSHoaDon timTheoTenKhach(String tuKhoa) {
        DSHoaDon kq = new DSHoaDon();
        String k = tuKhoa.toLowerCase();
        for (int i = 0; i < soLuong; i++) {
            KhachHang kh = QLDSKH.dskh.timTheoMa(danhSach[i].getMaKH());
            if (kh != null && (kh.getHoLot() + " " + kh.getTen()).toLowerCase().contains(k)) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    // Tim theo khoang tong tien, tra ve danh sach moi
    public DSHoaDon timTheoKhoangTongTien(double min, double max) {
        DSHoaDon kq = new DSHoaDon();
        for (int i = 0; i < soLuong; i++) {
            double g = danhSach[i].getTongTien();
            if (g >= min && g <= max) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    @Override
    public void timKiem() {
        System.out.println("1. Theo ma hoa don (chinh xac)");
        System.out.println("2. Theo ten khach hang (gan dung)");
        System.out.println("3. Theo khoang tong tien");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        DSHoaDon kq = new DSHoaDon();
        switch (opt) {
            case 1: {
                System.out.print("Nhap ma hoa don: ");
                HoaDon hd = timTheoMa(sc.nextLine());
                if (hd != null) {
                    kq.them(hd);
                }
                break;
            }
            case 2: {
                System.out.print("Nhap tu khoa (ten khach): ");
                kq = timTheoTenKhach(sc.nextLine());
                break;
            }
            case 3: {
                System.out.print("Tong tien tu: ");
                double a = sc.nextDouble();
                sc.nextLine();
                System.out.print("Den: ");
                double b = sc.nextDouble();
                sc.nextLine();
                kq = timTheoKhoangTongTien(a, b);
                break;
            }
            default:
                System.out.println("Lua chon khong hop le!");
                return;
        }
        if (kq.getSoLuong() == 0) {
            System.out.println("Khong tim thay!");
        } else {
            kq.xuat();
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
            } catch (Exception e) {
                System.out.println("Ngay khong hop le, nhap lai!");
            }
        }
    }

    // ===== g. THỐNG KÊ NHIỀU KHÓA HÓA ĐƠN (in ra màn hình) =====
    // Menu thống kê hóa đơn: mỗi lựa chọn là một cách thống kê (một khóa) khác
    // nhau,
    // mỗi cách được viết thành một hàm riêng bên dưới.
    // Quy ước: THU = tổng tiền các hóa đơn bán hàng, CHI = tổng tiền các phiếu nhập
    // hàng,
    // LỢI NHUẬN = THU - CHI (chênh lệch thu chi, chưa trừ lương hay chi phí khác)
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach hoa don rong!");
            return;
        }
        System.out.println("===== THONG KE THU - CHI - LOI NHUAN =====");
        System.out.println("(Thu = tien ban hang, Chi = tien nhap hang, Loi nhuan = Thu - Chi)");
        System.out.println("1. Thu - chi - loi nhuan trong khoang tu ngay A den ngay B");
        System.out.println("2. Thu - chi - loi nhuan tung quy trong mot nam");
        System.out.println("0. Quay lai");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeThuChiLoiNhuanTuNgayDenNgay();
                break;
            case 2:
                thongKeThuChiLoiNhuanTheoQuy();
                break;
            case 0:
                break;
            default:
                System.out.println("Lua chon khong hop le!");
                break;
        }
    }

    // 1. THU - CHI - LỢI NHUẬN TỪ NGÀY A ĐẾN NGÀY B (tính cả hai đầu).
    // Thu lấy từ hóa đơn (danh sách này), Chi lấy từ phiếu nhập (QLDSPNH.dspnh).
    public void thongKeThuChiLoiNhuanTuNgayDenNgay() {
        LocalDate a = docNgay("Ngay bat dau:");
        LocalDate b = docNgay("Ngay ket thuc:");
        if (a.isAfter(b)) {
            System.out.println("Ngay bat dau phai truoc hoac bang ngay ket thuc!");
            return;
        }
        double tongThu = tongThuTuNgayDenNgay(a, b);
        double tongChi = QLDSPNH.dspnh.tongChiTuNgayDenNgay(a, b);
        System.out.println("THU - CHI - LOI NHUAN TU " + a + " DEN " + b);
        System.out.println("Thu ban hang (VND) | Chi nhap hang (VND) | Loi nhuan = Thu - Chi (VND)");
        System.out.println("------------------------------------------------------------------");
        System.out.println((long) tongThu + " | " + (long) tongChi + " | " + (long) (tongThu - tongChi));
    }

    // 2. THU - CHI - LỢI NHUẬN TỪNG QUÝ TRONG MỘT NĂM.
    // In bảng 4 quý và dòng TỔNG cả năm.
    public void thongKeThuChiLoiNhuanTheoQuy() {
        System.out.print("Nam: ");
        int nam = sc.nextInt();
        sc.nextLine();
        System.out.println("THU - CHI - LOI NHUAN TUNG QUY - NAM " + nam);
        System.out.println("Quy | Thu ban hang (VND) | Chi nhap hang (VND) | Loi nhuan = Thu - Chi (VND)");
        System.out.println("----------------------------------------------------------------------------");
        double tongThu = 0;
        double tongChi = 0;
        for (int q = 1; q <= 4; q++) {
            double thu = tongThuTheoQuy(q, nam);
            double chi = QLDSPNH.dspnh.tongChiTheoQuy(q, nam);
            System.out.println(q + " | " + (long) thu + " | " + (long) chi + " | " + (long) (thu - chi));
            tongThu += thu;
            tongChi += chi;
        }
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("TONG | " + (long) tongThu + " | " + (long) tongChi + " | " + (long) (tongThu - tongChi));
    }

    // ===== Các hàm tính tổng (dùng cho thống kê thu - chi) =====
    // Tổng tiền các hóa đơn có ngày lập từ ngày a đến ngày b (tính cả hai đầu)
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

    // Tổng tiền các hóa đơn lập trong một quý của một năm.
    // Quý 1 = tháng 1-3, quý 2 = tháng 4-6, quý 3 = tháng 7-9, quý 4 = tháng 10-12
    public double tongThuTheoQuy(int quy, int nam) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            LocalDate ngay = danhSach[i].getNgayLap();
            if (nam == ngay.getYear() && (ngay.getMonthValue() - 1) / 3 + 1 == quy) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    // ===== Các hàm tính cho thống kê nhân viên (DSNhanVien gọi qua QLDSHD.dshd)
    // =====
    // Số hóa đơn do nhân viên maNV lập trong năm nam
    public int soHoaDonTheoNVVaNam(String maNV, int nam) {
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(maNV) && danhSach[i].getNgayLap().getYear() == nam) {
                dem++;
            }
        }
        return dem;
    }

    // Tổng tiền các hóa đơn do nhân viên maNV lập trong năm nam (doanh thu của nhân
    // viên)
    public double tongTienTheoNVVaNam(String maNV, int nam) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(maNV) && danhSach[i].getNgayLap().getYear() == nam) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
    }

    // ===== ĐỌC / GHI FILE TXT (interface IFile) =====
    // Mỗi đối tượng 1 dòng, các thuộc tính ngăn cách nhau bằng dấu ;
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