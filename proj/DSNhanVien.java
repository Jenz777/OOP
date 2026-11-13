import java.io.*;
import java.util.*;

public class DSNhanVien implements IQuanLy<NhanVien>, IFile {
    Scanner sc = new Scanner(System.in);
    private NhanVien[] danhSach;
    private int soLuong;

    public DSNhanVien() {
        danhSach = new NhanVien[10];
        soLuong = 0;
    }

    public DSNhanVien(DSNhanVien a) {
        this.danhSach = new NhanVien[10];
        this.soLuong = 0;
        for (int i = 0; i < a.soLuong; i++) {
            this.them(new NhanVien(a.danhSach[i]));
        }
    }

    public int getSoLuong() {
        return soLuong;
    }

    @Override
    public void them(NhanVien nv) {
        if (timTheoMa(nv.getMaNV()) != null) {
            System.out.println("Ma nhan vien " + nv.getMaNV() + " da ton tai, khong them!");
            return;
        }
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = nv;
        soLuong++;
    }

    public NhanVien taoNhanVien() {
        NhanVien nv = new NhanVien();
        nv.nhap();
        while (timTheoMa(nv.getMaNV()) != null) {
            System.out.println("Ma nhan vien da ton tai, nhap lai!");
            nv.nhap();
        }
        return nv;
    }

    @Override
    public void nhapN() {
        System.out.print("Ban muon nhap bao nhieu nhan vien ban dau? n = ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("--- Nhap nhan vien thu " + (i + 1) + " ---");
            them(taoNhanVien());
        }
    }

    @Override
    public void themK() {
        System.out.print("Nhap k nhan vien muon them: ");
        int k = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < k; i++) {
            System.out.println("--- Them nhan vien thu " + (i + 1) + " ---");
            them(taoNhanVien());
        }
    }

    @Override
    public void xuat() {
        if (soLuong == 0) {
            System.out.println("Danh sach nhan vien rong!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat();
        }
    }

    @Override
    public NhanVien timTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return null;
        }
        return danhSach[vt];
    }

    private int timViTri(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(ma)) {
                return i;
            }
        }
        return -1;
    }

    // Sửa theo mã: chọn thuộc tính cần sửa bằng switch-case, sửa được TẤT CẢ thuộc
    // tính.
    // Riêng sửa mã: mã mới không được trống / trùng; hóa đơn và phiếu nhập của nhân
    // viên tự đổi theo mã mới.
    @Override
    public void suaTheoMa(String ma) {
        NhanVien nv = timTheoMa(ma);
        if (nv == null) {
            System.out.println("Khong tim thay nhan vien!");
            return;
        }
        int opt;
        do {
            System.out.println("\n--- SUA NHAN VIEN ---");
            nv.xuat();
            System.out.println("1. Ma nhan vien");
            System.out.println("2. Ho lot");
            System.out.println("3. Ten");
            System.out.println("4. Vi tri (chuc vu)");
            System.out.println("5. Luong thang");
            System.out.println("0. Xong");
            System.out.print("Chon thong tin can sua: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1: {
                    System.out.print("Ma nhan vien moi: ");
                    String maMoi = sc.nextLine();
                    if (maMoi.isEmpty()) {
                        System.out.println("Ma khong duoc de trong!");
                    } else if (maMoi.equals(nv.getMaNV())) {
                        System.out.println("Ma moi giong ma cu, khong doi.");
                    } else if (timTheoMa(maMoi) != null) {
                        System.out.println("Ma nhan vien da ton tai!");
                    } else {
                        QLDSHD.dshd.doiMaNV(nv.getMaNV(), maMoi); // đổi luôn mã trong hóa đơn
                        QLDSPNH.dspnh.doiMaNV(nv.getMaNV(), maMoi); // và trong phiếu nhập
                        nv.setMaNV(maMoi);
                    }
                    break;
                }
                case 2:
                    System.out.print("Ho lot moi: ");
                    nv.setHoLot(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Ten moi: ");
                    nv.setTen(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Vi tri moi: ");
                    nv.setViTri(sc.nextLine());
                    break;
                case 5: {
                    System.out.print("Luong thang moi (>= 0): ");
                    double luong = sc.nextDouble();
                    sc.nextLine();
                    if (luong < 0) {
                        System.out.println("Luong khong duoc am, giu nguyen!");
                    } else {
                        nv.setLuongThang(luong);
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
        System.out.println("3. Theo khoang luong thang");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        int dem = 0;
        switch (opt) {
            case 1: {
                System.out.print("Nhap ma nhan vien: ");
                NhanVien nv = timTheoMa(sc.nextLine());
                if (nv != null) {
                    nv.xuat();
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
                System.out.print("Luong thang tu: ");
                double min = sc.nextDouble();
                sc.nextLine();
                System.out.print("Den: ");
                double max = sc.nextDouble();
                sc.nextLine();
                for (int i = 0; i < soLuong; i++) {
                    if (danhSach[i].getLuongThang() >= min && danhSach[i].getLuongThang() <= max) {
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

    // ===== g. THỐNG KÊ NHIỀU KHÓA NHÂN VIÊN (in ra màn hình) =====
    // Menu thống kê: mỗi lựa chọn là một khóa thống kê khác nhau, gọi hàm riêng bên
    // dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach nhan vien rong!");
            return;
        }
        System.out.println("1. Theo vi tri (so nhan vien, tong luong, luong trung binh)");
        System.out.println("2. Theo khoang luong (nhap luong tu - den)");
        System.out.println("3. Doanh thu cua tung nhan vien trong mot nam");
        System.out.println("0. Thoat");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeTheoViTri();
                break;
            case 2:
                thongKeTheoKhoangLuong();
                break;
            case 3:
                thongKeDoanhThuTheoNam();
                break;
            default:
                break;
        }
    }

    // 1. Theo vị trí: nhóm các nhân viên cùng vị trí
    public void thongKeTheoViTri() {
        System.out.println("Vi tri | So nhan vien | Tong luong | Luong trung binh");
        System.out.println("-----------------------------------------------------");
        for (int i = 0; i < soLuong; i++) {
            boolean daXet = false; // vị trí này đã được thống kê ở phía trước chưa?
            for (int j = 0; j < i; j++) {
                if (danhSach[j].getViTri().equals(danhSach[i].getViTri())) {
                    daXet = true;
                }
            }
            if (daXet) {
                continue;
            }
            int dem = 0;
            double tongLuong = 0;
            for (int k = 0; k < soLuong; k++) {
                if (danhSach[k].getViTri().equals(danhSach[i].getViTri())) {
                    dem++;
                    tongLuong += danhSach[k].getLuongThang();
                }
            }
            System.out.println(danhSach[i].getViTri() + " | " + dem + " | " + (long) tongLuong + " | "
                    + (long) (tongLuong / dem));
        }
    }

    // 2. Theo khoảng lương: liệt kê nhân viên có lương trong khoảng rồi tổng hợp
    public void thongKeTheoKhoangLuong() {
        System.out.print("Luong tu: ");
        double tu = sc.nextDouble();
        sc.nextLine();
        System.out.print("Den: ");
        double den = sc.nextDouble();
        sc.nextLine();
        int dem = 0;
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            double luong = danhSach[i].getLuongThang();
            if (luong >= tu && luong <= den) {
                danhSach[i].xuat();
                dem++;
                tong += luong;
            }
        }
        System.out.println("Co " + dem + " nhan vien | tong luong " + (long) tong);
        if (dem > 0) {
            System.out.println("Luong trung binh: " + (long) (tong / dem));
        }
    }

    // 3. Doanh thu của từng nhân viên trong một năm
    // (lấy từ danh sách hóa đơn dùng chung QLDSHD.dshd, lọc theo năm của ngày lập)
    public void thongKeDoanhThuTheoNam() {
        System.out.print("Nam: ");
        int nam = sc.nextInt();
        sc.nextLine();
        System.out.println("Ma NV | Ho ten | So hoa don | Doanh thu nam " + nam);
        System.out.println("---------------------------------------------");
        int tongHD = 0;
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            String ma = danhSach[i].getMaNV();
            int soHD = QLDSHD.dshd.soHoaDonTheoNVVaNam(ma, nam);
            double doanhThu = QLDSHD.dshd.tongTienTheoNVVaNam(ma, nam);
            System.out.println(ma + " | " + danhSach[i].getHoLot() + " " + danhSach[i].getTen() + " | " + soHD + " | "
                    + (long) doanhThu);
            tongHD += soHD;
            tong += doanhThu;
        }
        System.out.println("TONG | | " + tongHD + " | " + (long) tong);
    }

    // ===== ĐỌC / GHI FILE TXT (interface IFile) =====
    // Mỗi đối tượng 1 dòng, các thuộc tính ngăn cách nhau bằng dấu ;
    @Override
    public void docFile(String tenFile) {
        try {
            Scanner fs = new Scanner(new File(tenFile));
            while (fs.hasNextLine()) {
                NhanVien nv = new NhanVien();
                nv.fromFileString(fs.nextLine());
                them(nv);
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
