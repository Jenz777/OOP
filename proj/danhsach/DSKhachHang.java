package danhsach;

import interfaces.IFile;
import interfaces.IQuanLy;
import model.KhachHang;

import java.io.*;
import java.util.*;

public class DSKhachHang implements IQuanLy<KhachHang>, IFile {
    Scanner sc = new Scanner(System.in);
    private KhachHang[] danhSach;
    private int soLuong;

    public DSKhachHang() {
        danhSach = new KhachHang[10];
        soLuong = 0;
    }

    // Constructor sao chép: tạo MẢNG MỚI và BẢN SAO của từng khách hàng
    public DSKhachHang(DSKhachHang a) {
        this.danhSach = new KhachHang[10]; // luôn có chỗ, them() sẽ tự mở rộng khi đầy
        this.soLuong = 0;
        for (int i = 0; i < a.soLuong; i++) {
            this.them(new KhachHang(a.danhSach[i]));
        }
    }

    public int getSoLuong() {
        return soLuong;
    }

    // Chỉ hàm này được tăng soLuong. Có kiểm tra trùng mã.
    @Override
    public void them(KhachHang kh) {
        if (timTheoMa(kh.getMaKH()) != null) {
            System.out.println("Ma khach hang " + kh.getMaKH() + " da ton tai, khong them!");
            return;
        }
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = kh;
        soLuong++;
    }

    // Tạo 1 khách hàng: cho nhập, nếu trùng mã thì bắt nhập lại
    public KhachHang taoKhachHang() {
        KhachHang kh = new KhachHang();
        kh.nhap();
        while (timTheoMa(kh.getMaKH()) != null) {
            System.out.println("Ma khach hang da ton tai, nhap lai!");
            kh.nhap();
        }
        return kh;
    }

    @Override
    public void nhapN() {
        if (soLuong > 0) {
            System.out.println("Dang co " + soLuong + " khach hang, neu nhap moi se xoa het");
            System.out.println("Ban co muon xoa? Nhap 1 xac nhan, 2 de huy");
            int t = sc.nextInt();
            sc.nextLine();
            if (t != 1) {
                System.out.println("Da huy!");
                return;
            }
        }
        danhSach = new KhachHang[10];
        soLuong = 0;
        System.out.print("Ban muon nhap bao nhieu khach hang ban dau? n = ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("--- Nhap khach hang thu " + (i + 1) + " ---");
            them(taoKhachHang());
        }
    }

    @Override
    public void themK() {
        System.out.print("Nhap k khach hang muon them: ");
        int k = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < k; i++) {
            System.out.println("--- Them khach hang thu " + (i + 1) + " ---");
            them(taoKhachHang());
        }
    }

    @Override
    public void xuat() {
        if (soLuong == 0) {
            System.out.println("Danh sach khach hang rong!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat();
        }
    }

    @Override
    public KhachHang timTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return null;
        }
        return danhSach[vt];
    }

    private int timViTri(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaKH().equals(ma)) {
                return i;
            }
        }
        return -1;
    }

    // Sửa cơ bản: chọn thuộc tính rồi gán giá trị mới (không sửa mã vì mã là khóa).
    @Override
    public void suaTheoMa(String ma) {
        KhachHang kh = timTheoMa(ma);
        if (kh == null) {
            System.out.println("Khong tim thay khach hang!");
            return;
        }
        System.out.println("Thong tin khach hang:");
        kh.xuat();
        System.out.println("1. Ho lot");
        System.out.println("2. Ten");
        System.out.println("3. Tong tien mua hang");
        System.out.print("Chon thong tin can sua: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                System.out.print("Ho lot moi: ");
                kh.setHoLot(sc.nextLine());
                break;
            case 2:
                System.out.print("Ten moi: ");
                kh.setTen(sc.nextLine());
                break;
            case 3:
                System.out.print("Tong tien mua hang moi: ");
                kh.setTongTienMuaHang(sc.nextDouble());
                sc.nextLine();
                break;
            default:
                System.out.println("Lua chon khong hop le!");
        }
    }

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

    // Tìm kiếm 2 khóa: theo mã (chính xác) và theo tên (gần đúng)
    // Tim gan dung theo ho ten, tra ve danh sach moi
    public DSKhachHang timTheoTen(String tuKhoa) {
        DSKhachHang kq = new DSKhachHang();
        String k = tuKhoa.toLowerCase();
        for (int i = 0; i < soLuong; i++) {
            String hoTen = danhSach[i].getHoLot() + " " + danhSach[i].getTen();
            if (hoTen.toLowerCase().contains(k)) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    // Tim theo khoang tong tien mua hang, tra ve danh sach moi
    public DSKhachHang timTheoKhoangTongTien(double min, double max) {
        DSKhachHang kq = new DSKhachHang();
        for (int i = 0; i < soLuong; i++) {
            double g = danhSach[i].getTongTienMuaHang();
            if (g >= min && g <= max) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    @Override
    public void timKiem() {
        System.out.println("1. Theo ma (chinh xac)  2. Theo ten (gan dung)");
        System.out.println("3. Theo khoang tong tien mua hang");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        DSKhachHang kq = new DSKhachHang();
        switch (opt) {
            case 1: {
                System.out.print("Nhap ma khach hang: ");
                KhachHang kh = timTheoMa(sc.nextLine());
                if (kh != null) {
                    kq.them(kh);
                }
                break;
            }
            case 2: {
                System.out.print("Nhap tu khoa: ");
                kq = timTheoTen(sc.nextLine());
                break;
            }
            case 3: {
                System.out.print("Tong tien mua hang tu: ");
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

    // ===== g. THỐNG KÊ NHIỀU KHÓA KHÁCH HÀNG (in ra màn hình) =====
    // Menu thống kê khách hàng: mỗi lựa chọn là một cách thống kê (một khóa) khác nhau,
    // mỗi cách được viết thành một hàm riêng bên dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach khach hang rong!");
            return;
        }
        System.out.println("===== THONG KE KHACH HANG =====");
        System.out.println("1. Liet ke khach hang co tong tien mua hang tu muc toi thieu tro len");
        System.out.println("2. Top N khach hang mua nhieu tien nhat");
        System.out.println("0. Quay lai");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeKhachCoTongMuaTuMucToiThieu();
                break;
            case 2:
                thongKeTopKhachMuaNhieuNhat();
                break;
            case 0:
                break;
            default:
                System.out.println("Lua chon khong hop le!");
                break;
        }
    }

    // 1. KHÁCH HÀNG CÓ TỔNG TIỀN MUA TỪ MỘT MỨC TRỞ LÊN.
    // Nhập mức tiền tối thiểu, liệt kê các khách có tongTienMuaHang >= mức đó,
    // rồi in số khách tìm được.
    public void thongKeKhachCoTongMuaTuMucToiThieu() {
        System.out.print("Nhap tong tien mua hang toi thieu (VND): ");
        double min = sc.nextDouble();
        sc.nextLine();
        System.out.println("KHACH HANG CO TONG TIEN MUA HANG TU " + (long) min + " VND TRO LEN");
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getTongTienMuaHang() >= min) {
                danhSach[i].xuat();
                dem++;
            }
        }
        if (dem == 0) {
            System.out.println("Khong co khach hang nao dat muc nay.");
        } else {
            System.out.println("Co " + dem + " khach hang.");
        }
    }

    // 2. TOP N KHÁCH HÀNG MUA NHIỀU TIỀN NHẤT.
    // Nhập N, sắp xếp một BẢN SAO của danh sách giảm dần theo tongTienMuaHang
    // (không làm đổi thứ tự danh sách gốc) rồi in N khách đầu tiên.
    public void thongKeTopKhachMuaNhieuNhat() {
        System.out.print("Nhap N (so khach hang can xem): ");
        int n = sc.nextInt();
        sc.nextLine();
        if (n <= 0) {
            System.out.println("N khong hop le!");
            return;
        }
        if (n > soLuong) {
            n = soLuong;
        }
        KhachHang[] ds = new KhachHang[soLuong];
        for (int i = 0; i < soLuong; i++) {
            ds[i] = new KhachHang(danhSach[i]);
        }
        for (int i = 0; i < soLuong - 1; i++) {
            for (int j = i + 1; j < soLuong; j++) {
                if (ds[i].getTongTienMuaHang() < ds[j].getTongTienMuaHang()) {
                    KhachHang temp = ds[i];
                    ds[i] = ds[j];
                    ds[j] = temp;
                }
            }
        }
        System.out.println("TOP " + n + " KHACH HANG MUA NHIEU TIEN NHAT");
        for (int i = 0; i < n; i++) {
            System.out.print((i + 1) + ". ");
            ds[i].xuat();
        }
    }

    // ===== ĐỌC / GHI FILE TXT (interface IFile) =====
    // Mỗi đối tượng 1 dòng, các thuộc tính ngăn cách nhau bằng dấu ;
    @Override
    public void docFile(String tenFile) {
        try {
            Scanner fs = new Scanner(new File(tenFile));
            while (fs.hasNextLine()) {
                KhachHang kh = new KhachHang();
                kh.fromFileString(fs.nextLine());
                them(kh);
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
            System.out.println("Khong ghi duoc file ");
        }
    }
}