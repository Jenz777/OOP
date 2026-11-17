package danhsach;

import interfaces.IFile;
import interfaces.IQuanLy;
import model.ChiTietPhieuNhap;
import model.NhaCungCap;
import model.NhanVien;
import model.PhieuNhapHang;
import model.VanPhongPham;
import quanly.QLDSCTPNH;
import quanly.QLDSNCC;
import quanly.QLDSNV;
import quanly.QLDSSP;

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
        if (soLuong > 0) {
            System.out.println("Dang co " + soLuong + " phieu nhap, neu nhap moi se xoa het");
            System.out.println("Ban co muon xoa? Nhap 1 xac nhan, 2 de huy");
            int t = sc.nextInt();
            sc.nextLine();
            if (t != 1) {
                System.out.println("Da huy!");
                return;
            }
        }
        danhSach = new PhieuNhapHang[10];
        soLuong = 0;
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

    // Sửa cơ bản: chọn thuộc tính rồi gán giá trị mới (không sửa mã vì mã là khóa).
    @Override
    public void suaTheoMa(String ma) {
        PhieuNhapHang pn = timTheoMa(ma);
        if (pn == null) {
            System.out.println("Khong tim thay phieu nhap!");
            return;
        }
        System.out.println("Thong tin phieu nhap:");
        pn.xuat();
        System.out.println("1. Ma nha cung cap");
        System.out.println("2. Ma nhan vien");
        System.out.println("3. Ngay nhap");
        System.out.println("4. Tong tien");
        System.out.print("Chon thong tin can sua: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                System.out.print("Ma nha cung cap moi: ");
                pn.setMaNCC(sc.nextLine());
                break;
            case 2:
                System.out.print("Ma nhan vien moi: ");
                pn.setMaNV(sc.nextLine());
                break;
            case 3:
                pn.setNgayNhap(docNgay("Ngay nhap moi"));
                break;
            case 4:
                System.out.print("Tong tien moi: ");
                pn.setTongTien(sc.nextDouble());
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

    // Tìm kiếm nhiều khóa: mã (chính xác / gần đúng), tên nhà cung cấp (gần đúng),
    // nhân viên, khoảng tổng tiền
    // Tim gan dung theo ten nha cung cap, tra ve danh sach moi
    public DSPhieuNhapHang timTheoTenNCC(String tuKhoa) {
        DSPhieuNhapHang kq = new DSPhieuNhapHang();
        String k = tuKhoa.toLowerCase();
        for (int i = 0; i < soLuong; i++) {
            NhaCungCap ncc = QLDSNCC.dsncc.timTheoMa(danhSach[i].getMaNCC());
            if (ncc != null && ncc.getTenNCC().toLowerCase().contains(k)) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    // Tim theo khoang tong tien, tra ve danh sach moi
    public DSPhieuNhapHang timTheoKhoangTongTien(double min, double max) {
        DSPhieuNhapHang kq = new DSPhieuNhapHang();
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
        System.out.println("1. Theo ma phieu nhap (chinh xac)");
        System.out.println("2. Theo ten nha cung cap (gan dung)");
        System.out.println("3. Theo khoang tong tien");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        DSPhieuNhapHang kq = new DSPhieuNhapHang();
        switch (opt) {
            case 1: {
                System.out.print("Nhap ma phieu nhap: ");
                PhieuNhapHang p = timTheoMa(sc.nextLine());
                if (p != null) {
                    kq.them(p);
                }
                break;
            }
            case 2: {
                System.out.print("Nhap tu khoa (ten nha cung cap): ");
                kq = timTheoTenNCC(sc.nextLine());
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
    // Menu thống kê phiếu nhập: mỗi lựa chọn là một cách thống kê (một khóa) khác nhau,
    // mỗi cách được viết thành một hàm riêng bên dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach phieu nhap rong!");
            return;
        }
        System.out.println("===== THONG KE CHI NHAP HANG =====");
        System.out.println("1. Chi nhap hang trong khoang tu ngay A den ngay B (so phieu, tong chi)");
        System.out.println("2. Chi nhap hang tung quy trong mot nam");
        System.out.println("0. Quay lai");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeChiNhapHangTuNgayDenNgay();
                break;
            case 2:
                thongKeChiNhapHangTheoQuy();
                break;
            case 0:
                break;
            default:
                System.out.println("Lua chon khong hop le!");
                break;
        }
    }

    // 1. CHI NHẬP HÀNG TỪ NGÀY A ĐẾN NGÀY B (tính cả hai đầu).
    // In số phiếu nhập và tổng tiền nhập hàng trong khoảng ngày đó.
    public void thongKeChiNhapHangTuNgayDenNgay() {
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
        System.out.println("CHI NHAP HANG TU " + a + " DEN " + b);
        System.out.println("So phieu nhap | Tong chi nhap hang (VND)");
        System.out.println("-----------------------------------------");
        System.out.println(dem + " | " + (long) tongChiTuNgayDenNgay(a, b));
    }

    // 2. CHI NHẬP HÀNG TỪNG QUÝ TRONG MỘT NĂM.
    // In bảng 4 quý và dòng TỔNG cả năm.
    public void thongKeChiNhapHangTheoQuy() {
        System.out.print("Nam: ");
        int nam = sc.nextInt();
        sc.nextLine();
        System.out.println("CHI NHAP HANG TUNG QUY - NAM " + nam);
        System.out.println("Quy | Tong chi nhap hang (VND)");
        System.out.println("----------------------------");
        double tong = 0;
        for (int q = 1; q <= 4; q++) {
            double chi = tongChiTheoQuy(q, nam);
            System.out.println(q + " | " + (long) chi);
            tong += chi;
        }
        System.out.println("----------------------------");
        System.out.println("TONG | " + (long) tong);
    }

    // ===== Các hàm tính tổng (dùng cho thống kê chi nhập hàng) =====
    // Tổng tiền các phiếu nhập có ngày nhập từ ngày a đến ngày b (tính cả hai đầu)
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

    // Tổng tiền các phiếu nhập trong một quý của một năm.
    // Quý 1 = tháng 1-3, quý 2 = tháng 4-6, quý 3 = tháng 7-9, quý 4 = tháng 10-12
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

    // ===== Các hàm tính cho thống kê nhà cung cấp (DSNhaCungCap gọi qua QLDSPNH.dspnh) =====
    // Số phiếu nhập của nhà cung cấp maNCC
    public int soPhieuTheoNCC(String maNCC) {
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNCC().equals(maNCC)) {
                dem++;
            }
        }
        return dem;
    }

    // Tổng tiền đã nhập hàng từ nhà cung cấp maNCC (mọi phiếu nhập)
    public double tongTienTheoNCC(String maNCC) {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNCC().equals(maNCC)) {
                tong += danhSach[i].getTongTien();
            }
        }
        return tong;
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