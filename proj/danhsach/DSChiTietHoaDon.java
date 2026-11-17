package danhsach;

import interfaces.IFile;
import interfaces.IQuanLy;
import model.But;
import model.ChiTietHoaDon;
import model.TapViet;
import model.VanPhongPham;
import quanly.QLDSSP;

import java.io.*;
import java.util.*;

// Danh sách TẤT CẢ các dòng chi tiết của mọi hóa đơn (đủ chức năng IQuanLy + IFile).
// Khóa của một dòng là cặp (maHD, maSP) -> dùng chuỗi "maHD|maSP" làm "mã" cho các hàm của IQuanLy.
// Thêm / sửa / xóa ở đây chỉ làm cơ bản trên chính danh sách này (không đụng tới tồn kho, tổng tiền hóa đơn,
// tiền mua của khách). Phần nghiệp vụ nằm ở DSHoaDon.taoHoaDon (lập hóa đơn).
public class DSChiTietHoaDon implements IQuanLy<ChiTietHoaDon>, IFile {
    Scanner sc = new Scanner(System.in);
    private ChiTietHoaDon[] danhSach;
    private int soLuong;

    public DSChiTietHoaDon() {
        danhSach = new ChiTietHoaDon[10];
        soLuong = 0;
    }

    public int getSoLuong() {
        return soLuong;
    }

    // static: ghép mã hóa đơn + mã sản phẩm thành khóa của một dòng
    public static String taoKhoa(String maHD, String maSP) {
        return maHD + "|" + maSP;
    }

    // Thêm THÔ vào cuối mảng (không kiểm tra gì).
    // Dùng khi đọc file và khi DSHoaDon.taoHoaDon đang lập hóa đơn.
    public void themDong(ChiTietHoaDon ct) {
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = ct;
        soLuong++;
    }

    // ===== c. THÊM =====
    // Thêm cơ bản 1 dòng: chỉ kiểm tra số hợp lệ và không trùng (mã hóa đơn + mã
    // sản phẩm).
    @Override
    public void them(ChiTietHoaDon ct) {
        if (ct.getSoLuong() <= 0 || ct.getGiaBan() <= 0) {
            System.out.println("So luong va gia ban phai > 0!");
            return;
        }
        if (timTheoMa(ct.getKhoa()) != null) {
            System.out.println("Dong " + ct.getKhoa() + " da ton tai!");
            return;
        }
        themDong(ct);
    }

    // Nhập 1 dòng chi tiết từ bàn phím
    public ChiTietHoaDon taoChiTiet() {
        ChiTietHoaDon ct = new ChiTietHoaDon();
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
        danhSach = new ChiTietHoaDon[10];
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
            System.out.println("Chua co chi tiet hoa don nao!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat();
        }
    }

    // In các dòng chi tiết của một hóa đơn, trả về số dòng
    public int xuatTheoMaHD(String maHD) {
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaHD().equals(maHD)) {
                danhSach[i].xuat();
                dem++;
            }
        }
        return dem;
    }

    // ===== TÌM THEO MÃ (mã = "maHD|maSP") =====
    @Override
    public ChiTietHoaDon timTheoMa(String ma) {
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
    // Sửa cơ bản: chọn thuộc tính rồi gán giá trị mới (không sửa mã hóa đơn, mã sản
    // phẩm vì là khóa).
    // Thành tiền tự tính lại = số lượng x giá bán.
    @Override
    public void suaTheoMa(String ma) {
        ChiTietHoaDon ct = timTheoMa(ma);
        if (ct == null) {
            System.out.println("Khong tim thay dong chi tiet!");
            return;
        }
        System.out.println("Thong tin chi tiet hoa don:");
        ct.xuat();
        System.out.println("1. So luong ban");
        System.out.println("2. Gia ban");
        System.out.print("Chon thong tin can sua: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                System.out.print("So luong ban moi: ");
                ct.setSoLuong(sc.nextInt());
                sc.nextLine();
                break;
            case 2:
                System.out.print("Gia ban moi: ");
                ct.setGiaBan(sc.nextDouble());
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

    @Override
    public void timKiem() {
        System.out.println("1. Theo ma hoa don + ma san pham (chinh xac)");
        System.out.println("2. Theo ten san pham (gan dung)");
        System.out.println("3. Theo khoang thanh tien");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        DSChiTietHoaDon kq = new DSChiTietHoaDon();
        switch (opt) {
            case 1: {
                System.out.println("Nhap ma hd");
                String mahd = sc.nextLine();
                System.out.println("Nhap ma sp");
                ChiTietHoaDon ct = timTheoMa(taoKhoa(mahd, sc.nextLine()));
                if (ct != null) {
                    kq.themDong(ct);
                }
                break;
            }
            case 2: {
                System.out.println("Nhap ten san pham(gan dung)");
                String ten = sc.nextLine();
                kq = timTheoTenSP(ten);
                break;
            }
            case 3: {
                System.out.println("Nhap khoang tien tu:");
                double min = sc.nextDouble();
                sc.nextLine();
                System.out.println("Den:");
                double max = sc.nextDouble();
                sc.nextLine();
                kq = timTheoKhoangThanhTien(min, max);
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

    public DSChiTietHoaDon timTheoTenSP(String tuKhoa) {
        DSChiTietHoaDon kq = new DSChiTietHoaDon();
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
    public DSChiTietHoaDon timTheoKhoangThanhTien(double min, double max) {
        DSChiTietHoaDon kq = new DSChiTietHoaDon();
        for (int i = 0; i < soLuong; i++) {
            double g = danhSach[i].getThanhTien();
            if (g >= min && g <= max) {
                kq.themDong(danhSach[i]);
            }
        }
        return kq;
    }

    // ===== g. THỐNG KÊ NHIỀU KHÓA CHI TIẾT HÓA ĐƠN (in ra màn hình) =====
    // Menu thống kê hàng đã bán (dựa trên các dòng chi tiết hóa đơn): mỗi lựa chọn là
    // một cách thống kê (một khóa) khác nhau, mỗi cách được viết thành một hàm riêng
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach chi tiet hoa don rong!");
            return;
        }
        System.out.println("===== THONG KE HANG DA BAN =====");
        System.out.println("1. Theo loai san pham (But / Tap viet): so luong ban va doanh thu");
        System.out.println("2. San pham ban chay nhat (tong so luong ban tren moi hoa don nhieu nhat)");
        System.out.println("0. Quay lai");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeSoLuongBanVaDoanhThuTheoLoaiSP();
                break;
            case 2:
                thongKeSanPhamBanChayNhat();
                break;
            case 0:
                break;
            default:
                System.out.println("Lua chon khong hop le!");
                break;
        }
    }

    // 1. SỐ LƯỢNG BÁN VÀ DOANH THU THEO LOẠI SẢN PHẨM (Bút / Tập viết).
    // Mỗi dòng chi tiết thuộc loại nào được tra qua danh sách sản phẩm dùng chung
    // QLDSSP.dssp. Doanh thu = cộng thành tiền của các dòng thuộc loại đó.
    public void thongKeSoLuongBanVaDoanhThuTheoLoaiSP() {
        int slBut = 0;
        int slTap = 0;
        double tienBut = 0;
        double tienTap = 0;
        for (int i = 0; i < soLuong; i++) {
            VanPhongPham sp = QLDSSP.dssp.timTheoMa(danhSach[i].getMaSP());
            if (sp instanceof But) {
                slBut += danhSach[i].getSoLuong();
                tienBut += danhSach[i].getThanhTien();
            } else if (sp instanceof TapViet) {
                slTap += danhSach[i].getSoLuong();
                tienTap += danhSach[i].getThanhTien();
            }
        }
        System.out.println("SO LUONG BAN VA DOANH THU THEO LOAI SAN PHAM");
        System.out.println("Loai | So luong da ban | Doanh thu (VND)");
        System.out.println("---------------------------------------");
        System.out.println("But | " + slBut + " | " + (long) tienBut);
        System.out.println("Tap viet | " + slTap + " | " + (long) tienTap);
        System.out.println("TONG | " + (slBut + slTap) + " | " + (long) (tienBut + tienTap));
    }

    // 2. SẢN PHẨM BÁN CHẠY NHẤT.
    // Với mỗi sản phẩm, cộng số lượng bán trên mọi hóa đơn; sản phẩm có tổng lớn nhất
    // là bán chạy nhất (nếu bằng nhau thì lấy sản phẩm xuất hiện trước).
    public void thongKeSanPhamBanChayNhat() {
        if (soLuong == 0) {
            System.out.println("Chua co hoa don nao!");
            return;
        }
        String maBanChay = "";
        int slMax = 0;
        for (int i = 0; i < soLuong; i++) {
            int tong = 0;
            for (int j = 0; j < soLuong; j++) {
                if (danhSach[j].getMaSP().equals(danhSach[i].getMaSP())) {
                    tong += danhSach[j].getSoLuong();
                }
            }
            if (tong > slMax) {
                slMax = tong;
                maBanChay = danhSach[i].getMaSP();
            }
        }
        System.out.println("San pham ban chay nhat: " + maBanChay + " (da ban " + slMax + " san pham)");
    }

    // ===== ĐỌC / GHI FILE TXT (interface IFile) =====
    // Mỗi đối tượng 1 dòng, các thuộc tính ngăn cách nhau bằng dấu ;
    @Override
    public void docFile(String tenFile) {
        try {
            Scanner fs = new Scanner(new File(tenFile));
            while (fs.hasNextLine()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.fromFileString(fs.nextLine());
                themDong(ct); // đọc file: chỉ nạp dữ liệu, không trừ tồn kho / không cộng tiền lần nữa
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