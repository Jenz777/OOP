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

    // Sửa theo mã: chọn thuộc tính cần sửa bằng switch-case, sửa được TẤT CẢ thuộc
    // tính.
    // Riêng sửa mã: mã mới không được trống / trùng; các hóa đơn của khách tự đổi
    // theo mã mới.
    @Override
    public void suaTheoMa(String ma) {
        KhachHang kh = timTheoMa(ma);
        if (kh == null) {
            System.out.println("Khong tim thay khach hang!");
            return;
        }
        int opt;
        do {
            System.out.println("\n--- SUA KHACH HANG ---");
            kh.xuat();
            System.out.println("1. Ma khach hang");
            System.out.println("2. Ho lot");
            System.out.println("3. Ten");
            System.out.println("4. Tong tien mua hang");
            System.out.println("0. Xong");
            System.out.print("Chon thong tin can sua: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1: {
                    System.out.print("Ma khach hang moi: ");
                    String maMoi = sc.nextLine();
                    if (maMoi.isEmpty()) {
                        System.out.println("Ma khong duoc de trong!");
                    } else if (maMoi.equals(kh.getMaKH())) {
                        System.out.println("Ma moi giong ma cu, khong doi.");
                    } else if (timTheoMa(maMoi) != null) {
                        System.out.println("Ma khach hang da ton tai!");
                    } else {
                        QLDSHD.dshd.doiMaKH(kh.getMaKH(), maMoi); // đổi luôn mã trong các hóa đơn
                        kh.setMaKH(maMoi);
                    }
                    break;
                }
                case 2:
                    System.out.print("Ho lot moi: ");
                    kh.setHoLot(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Ten moi: ");
                    kh.setTen(sc.nextLine());
                    break;
                case 4: {
                    System.out.print("Tong tien mua hang moi (>= 0): ");
                    double tien = sc.nextDouble();
                    sc.nextLine();
                    if (tien < 0) {
                        System.out.println("Tong tien khong duoc am, giu nguyen!");
                    } else {
                        kh.setTongTienMuaHang(tien);
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
    @Override
    public void timKiem() {
        System.out.println("1. Theo ma (chinh xac)  2. Theo ten (gan dung)");
        System.out.println("3. Theo khoang tong tien mua hang");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        int dem = 0;
        switch (opt) {
            case 1: {
                System.out.print("Nhap ma khach hang: ");
                KhachHang kh = timTheoMa(sc.nextLine());
                if (kh != null) {
                    kh.xuat();
                    dem = 1;
                }
                break;
            }
            case 2: {
                System.out.print("Nhap tu khoa: ");
                String tuKhoa = sc.nextLine().toLowerCase();
                for (int i = 0; i < soLuong; i++) {
                    String hoTen = danhSach[i].getHoLot() + " " + danhSach[i].getTen();
                    if (hoTen.toLowerCase().contains(tuKhoa)) {
                        danhSach[i].xuat();
                        dem++;
                    }
                }
                break;
            }
            case 3: {
                System.out.print("Tong tien mua hang tu: ");
                double min = sc.nextDouble();
                sc.nextLine();
                System.out.print("Den: ");
                double max = sc.nextDouble();
                sc.nextLine();
                for (int i = 0; i < soLuong; i++) {
                    if (danhSach[i].getTongTienMuaHang() >= min && danhSach[i].getTongTienMuaHang() <= max) {
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

    // ===== g. THỐNG KÊ NHIỀU KHÓA KHÁCH HÀNG (in ra màn hình) =====
    // Menu thống kê: mỗi lựa chọn là một khóa thống kê khác nhau, gọi hàm riêng bên
    // dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach khach hang rong!");
            return;
        }
        System.out.println("1. Khach co tong mua tu muc nhap vao tro len");
        System.out.println("2. Khach mua nhieu nhat");
        System.out.println("0. Thoat");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeTheoMucChiTieu();
                break;
            case 2:
                thongKeKhachMuaNhieuNhat();
                break;
            default:
                break;
        }
    }

    // 2. Khách có tổng tiền mua lớn nhất
    public void thongKeKhachMuaNhieuNhat() {
        int vtMax = 0;
        for (int i = 1; i < soLuong; i++) {
            if (danhSach[i].getTongTienMuaHang() > danhSach[vtMax].getTongTienMuaHang()) {
                vtMax = i;
            }
        }
        if (danhSach[vtMax].getTongTienMuaHang() == 0) {
            System.out.println("Chua co khach nao mua hang.");
        } else {
            System.out.print("Khach mua nhieu nhat: ");
            danhSach[vtMax].xuat();
        }
    }

    // 3. Liệt kê khách có tổng tiền mua từ mức nhập vào trở lên
    public void thongKeTheoMucChiTieu() {
        System.out.print("Nhap muc chi tieu toi thieu: ");
        double muc = sc.nextDouble();
        sc.nextLine();
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getTongTienMuaHang() >= muc) {
                danhSach[i].xuat();
                dem++;
            }
        }
        System.out.println("Co " + dem + " khach co tong mua tu " + (long) muc + " tro len.");
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
            System.out.println("Khong ghi duoc file " + tenFile);
        }
    }
}
