package danhsach;

import interfaces.IFile;
import interfaces.IQuanLy;
import model.But;
import model.ChiTietPhieuNhap;
import model.TapViet;
import model.VanPhongPham;
import quanly.QLDSSP;

import java.io.*;
import java.util.*;

// Danh sách TẤT CẢ các dòng chi tiết của mọi phiếu nhập (đủ chức năng IQuanLy + IFile) - làm giống DSChiTietHoaDon.
// Khóa của một dòng là cặp (maPNH, maSP) -> dùng chuỗi "maPNH|maSP" làm "mã" cho các hàm của IQuanLy.
// Thêm / sửa / xóa ở đây chỉ làm cơ bản trên chính danh sách này (không đụng tới tồn kho, tổng tiền phiếu nhập).
// Phần nghiệp vụ nằm ở DSPhieuNhapHang.taoPhieuNhap (lập phiếu nhập).
public class DSChiTietPhieuNhap implements IQuanLy<ChiTietPhieuNhap>, IFile {
    Scanner sc = new Scanner(System.in);
    private ChiTietPhieuNhap[] danhSach;
    private int soLuong;

    public DSChiTietPhieuNhap() {
        danhSach = new ChiTietPhieuNhap[10];
        soLuong = 0;
    }

    public int getSoLuong() {
        return soLuong;
    }

    // static: ghép mã phiếu nhập + mã sản phẩm thành khóa của một dòng
    public static String taoKhoa(String maPNH, String maSP) {
        return maPNH + "|" + maSP;
    }

    // Thêm THÔ vào cuối mảng (không kiểm tra, không đụng tồn kho / tổng tiền).
    // Dùng khi đọc file và khi DSPhieuNhapHang.taoPhieuNhap đang lập phiếu (lúc đó
    // tồn kho đã cộng sẵn).
    public void themDong(ChiTietPhieuNhap ct) {
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = ct;
        soLuong++;
    }

    // ===== c. THÊM =====
    // Thêm cơ bản 1 dòng: chỉ kiểm tra số hợp lệ và không trùng (mã phiếu nhập + mã
    // sản phẩm).
    @Override
    public void them(ChiTietPhieuNhap ct) {
        if (ct.getSoLuongNhap() <= 0 || ct.getDonGiaNhap() <= 0) {
            System.out.println("So luong va don gia nhap phai > 0!");
            return;
        }
        if (timTheoMa(ct.getKhoa()) != null) {
            System.out.println("Dong " + ct.getKhoa() + " da ton tai!");
            return;
        }
        themDong(ct);
    }

    // Nhập 1 dòng chi tiết từ bàn phím
    public ChiTietPhieuNhap taoChiTiet() {
        ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
        ct.nhap();
        while (timTheoMa(ct.getKhoa()) != null) {
            System.out.println("Ma da ton tai, nhap lai!");
            ct.nhap();
        }
        return ct;
    }

    // ===== a. NHẬP N =====
    @Override
    public void nhapN() {
        if (soLuong > 0) {
            System.out.println("Dang co " + soLuong + " dong chi tiet, neu nhap moi se xoa het");
            System.out.println("Ban co muon xoa? Nhap 1 xac nhan, 2 de huy");
            int t = sc.nextInt();
            sc.nextLine();
            if (t != 1) {
                System.out.println("Da huy!");
                return;
            }
        }
        danhSach = new ChiTietPhieuNhap[10];
        soLuong = 0;
        System.out.print("Ban muon nhap bao nhieu dong chi tiet ban dau? n = ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("--- Nhap dong chi tiet thu " + (i + 1) + " ---");
            them(taoChiTiet());
        }
    }

    @Override
    public void themK() {
        System.out.print("Nhap k dong chi tiet muon them: ");
        int k = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < k; i++) {
            System.out.println("--- Them dong chi tiet thu " + (i + 1) + " ---");
            them(taoChiTiet());
        }
    }

    // ===== b. XUẤT =====
    @Override
    public void xuat() {
        if (soLuong == 0) {
            System.out.println("Chua co chi tiet phieu nhap nao!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat();
        }
    }

    // In các dòng chi tiết của một phiếu nhập, trả về số dòng

    // ===== TÌM THEO MÃ (mã = "maPNH|maSP") =====
    @Override
    public ChiTietPhieuNhap timTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return null;
        }
        return danhSach[vt];
    }

    private int timViTri(String khoa) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getKhoa().equals(khoa)) {
                return i;
            }
        }
        return -1;
    }

    // ===== d. SỬA THEO MÃ =====
    // Sửa cơ bản: chọn thuộc tính rồi gán giá trị mới (không sửa mã phiếu nhập, mã
    // sản phẩm vì là khóa).
    // Thành tiền tự tính lại = số lượng nhập x đơn giá nhập.
    @Override
    public void suaTheoMa(String ma) {
        ChiTietPhieuNhap ct = timTheoMa(ma);
        if (ct == null) {
            System.out.println("Khong tim thay dong chi tiet!");
            return;
        }

        System.out.println("Thong tin chi tiet phieu nhap hang:");
        ct.xuat();
        System.out.println("1. So luong nhap");
        System.out.println("2. Don gia nhap");
        System.out.print("Chon thong tin can sua: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                System.out.print("So luong nhap moi: ");
                ct.setSoLuongNhap(sc.nextInt());
                sc.nextLine();
                break;
            case 2:
                System.out.print("Don gia nhap moi: ");
                ct.setDonGiaNhap(sc.nextDouble());
                sc.nextLine();
                break;
            default:
                System.out.println("Lua chon khong hop le!");
        }
    }

    // ===== e. XÓA THEO MÃ =====
    // Xóa cơ bản: bỏ dòng khỏi mảng rồi dồn các dòng sau lên.
    @Override
    public boolean xoaTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return false;
        }
        for (int i = vt; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1];
        }
        danhSach[soLuong - 1] = null;
        soLuong--;
        return true;
    }

    // ===== f. TÌM KIẾM NHIỀU KHÓA =====
    // Tim gan dung theo ten san pham, tra ve danh sach moi
    public DSChiTietPhieuNhap timTheoTenSP(String tuKhoa) {
        DSChiTietPhieuNhap kq = new DSChiTietPhieuNhap();
        String k = tuKhoa.toLowerCase();
        for (int i = 0; i < soLuong; i++) {
            VanPhongPham sp = QLDSSP.dssp.timTheoMa(danhSach[i].getMaSP());
            if (sp != null && sp.getTenSP().toLowerCase().contains(k)) {
                kq.themDong(danhSach[i]);
            }
        }
        return kq;
    }

    // Tim theo khoang thanh tien, tra ve danh sach moi
    public DSChiTietPhieuNhap timTheoKhoangThanhTien(double min, double max) {
        DSChiTietPhieuNhap kq = new DSChiTietPhieuNhap();
        for (int i = 0; i < soLuong; i++) {
            double g = danhSach[i].getThanhTien();
            if (g >= min && g <= max) {
                kq.themDong(danhSach[i]);
            }
        }
        return kq;
    }

    @Override
    public void timKiem() {
        System.out.println("1. Theo ma phieu nhap + ma san pham (chinh xac)");
        System.out.println("2. Theo ten san pham (gan dung)");
        System.out.println("3. Theo khoang thanh tien");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        DSChiTietPhieuNhap kq = new DSChiTietPhieuNhap();
        switch (opt) {
            case 1: {
                System.out.println("Nhap ma phieu nhap hang");
                String ma = sc.nextLine();
                System.out.println("Nhap ma sp");
                ChiTietPhieuNhap ct = timTheoMa(taoKhoa(ma, sc.nextLine()));
                if (ct != null) {
                    kq.themDong(ct);
                }
                break;
            }
            case 2: {
                System.out.print("Nhap tu khoa (ten san pham): ");
                kq = timTheoTenSP(sc.nextLine());
                break;
            }
            case 3: {
                System.out.print("Thanh tien tu: ");
                double a = sc.nextDouble();
                sc.nextLine();
                System.out.print("Den: ");
                double b = sc.nextDouble();
                sc.nextLine();
                kq = timTheoKhoangThanhTien(a, b);
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

    // ===== g. THỐNG KÊ NHIỀU KHÓA CHI TIẾT PHIẾU NHẬP (in ra màn hình) =====
    // Menu thống kê hàng đã nhập (dựa trên các dòng chi tiết phiếu nhập): mỗi lựa
    // chọn
    // là một cách thống kê (một khóa) khác nhau, mỗi cách được viết thành một hàm
    // riêng
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach chi tiet phieu nhap rong!");
            return;
        }
        System.out.println("===== THONG KE HANG DA NHAP =====");
        System.out.println("1. Theo loai san pham (But / Tap viet): so luong nhap va tien nhap");
        System.out.println("2. San pham nhap nhieu nhat (tong so luong nhap tren moi phieu nhap nhieu nhat)");
        System.out.println("0. Quay lai");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeSoLuongNhapVaTienNhapTheoLoaiSP();
                break;
            case 2:
                thongKeSanPhamNhapNhieuNhat();
                break;
            case 0:
                break;
            default:
                System.out.println("Lua chon khong hop le!");
                break;
        }
    }

    // 1. SỐ LƯỢNG NHẬP VÀ TIỀN NHẬP THEO LOẠI SẢN PHẨM (Bút / Tập viết).
    // Mỗi dòng chi tiết thuộc loại nào được tra qua danh sách sản phẩm dùng chung
    // QLDSSP.dssp. Tiền nhập = cộng thành tiền của các dòng thuộc loại đó.
    public void thongKeSoLuongNhapVaTienNhapTheoLoaiSP() {
        int slBut = 0;
        int slTap = 0;
        double tienBut = 0;
        double tienTap = 0;
        for (int i = 0; i < soLuong; i++) {
            VanPhongPham sp = QLDSSP.dssp.timTheoMa(danhSach[i].getMaSP());
            if (sp instanceof But) {
                slBut += danhSach[i].getSoLuongNhap();
                tienBut += danhSach[i].getThanhTien();
            } else if (sp instanceof TapViet) {
                slTap += danhSach[i].getSoLuongNhap();
                tienTap += danhSach[i].getThanhTien();
            }
        }
        System.out.println("SO LUONG NHAP VA TIEN NHAP THEO LOAI SAN PHAM");
        System.out.println("Loai | So luong da nhap | Tien nhap (VND)");
        System.out.println("----------------------------------------");
        System.out.println("But | " + slBut + " | " + (long) tienBut);
        System.out.println("Tap viet | " + slTap + " | " + (long) tienTap);
        System.out.println("TONG | " + (slBut + slTap) + " | " + (long) (tienBut + tienTap));
    }

    // 2. SẢN PHẨM NHẬP NHIỀU NHẤT.
    // Với mỗi sản phẩm, cộng số lượng nhập trên mọi phiếu nhập; sản phẩm có tổng
    // lớn
    // nhất là nhập nhiều nhất (nếu bằng nhau thì lấy sản phẩm xuất hiện trước).
    public void thongKeSanPhamNhapNhieuNhat() {
        if (soLuong == 0) {
            System.out.println("Chua co phieu nhap nao!");
            return;
        }
        String maNhapNhieu = "";
        int slMax = 0;
        for (int i = 0; i < soLuong; i++) {
            int tong = 0;
            for (int j = 0; j < soLuong; j++) {
                if (danhSach[j].getMaSP().equals(danhSach[i].getMaSP())) {
                    tong += danhSach[j].getSoLuongNhap();
                }
            }
            if (tong > slMax) {
                slMax = tong;
                maNhapNhieu = danhSach[i].getMaSP();
            }
        }
        System.out.println("San pham nhap nhieu nhat: " + maNhapNhieu + " (da nhap " + slMax + " san pham)");
    }

    // ===== ĐỌC / GHI FILE TXT (interface IFile) =====
    // Mỗi đối tượng 1 dòng, các thuộc tính ngăn cách nhau bằng dấu ;
    @Override
    public void docFile(String tenFile) {
        try {
            Scanner fs = new Scanner(new File(tenFile));
            while (fs.hasNextLine()) {
                ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
                ct.fromFileString(fs.nextLine());
                themDong(ct); // đọc file: chỉ nạp dữ liệu, không cộng tồn kho lần nữa
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