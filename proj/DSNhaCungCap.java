import java.io.*;
import java.util.*;

public class DSNhaCungCap implements IQuanLy<NhaCungCap>, IFile {
    Scanner sc = new Scanner(System.in);
    private NhaCungCap[] danhSach;
    private int soLuong;

    public DSNhaCungCap() {
        danhSach = new NhaCungCap[10];
        soLuong = 0;
    }

    public DSNhaCungCap(DSNhaCungCap a) {
        this.danhSach = new NhaCungCap[10];
        this.soLuong = 0;
        for (int i = 0; i < a.soLuong; i++) {
            this.them(new NhaCungCap(a.danhSach[i]));
        }
    }

    public int getSoLuong() {
        return soLuong;
    }

    @Override
    public void them(NhaCungCap ncc) {
        if (timTheoMa(ncc.getMaNCC()) != null) {
            System.out.println("Ma nha cung cap " + ncc.getMaNCC() + " da ton tai, khong them!");
            return;
        }
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = ncc;
        soLuong++;
    }

    public NhaCungCap taoNhaCungCap() {
        NhaCungCap ncc = new NhaCungCap();
        ncc.nhap();
        while (timTheoMa(ncc.getMaNCC()) != null) {
            System.out.println("Ma nha cung cap da ton tai, nhap lai!");
            ncc.nhap();
        }
        return ncc;
    }

    @Override
    public void nhapN() {
        System.out.print("Ban muon nhap bao nhieu nha cung cap ban dau? n = ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("--- Nhap nha cung cap thu " + (i + 1) + " ---");
            them(taoNhaCungCap());
        }
    }

    @Override
    public void themK() {
        System.out.print("Nhap k nha cung cap muon them: ");
        int k = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < k; i++) {
            System.out.println("--- Them nha cung cap thu " + (i + 1) + " ---");
            them(taoNhaCungCap());
        }
    }

    @Override
    public void xuat() {
        if (soLuong == 0) {
            System.out.println("Danh sach nha cung cap rong!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat();
        }
    }

    @Override
    public NhaCungCap timTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return null;
        }
        return danhSach[vt];
    }

    private int timViTri(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNCC().equals(ma)) {
                return i;
            }
        }
        return -1;
    }

    // Sửa theo mã: chọn thuộc tính cần sửa bằng switch-case, sửa được TẤT CẢ thuộc
    // tính.
    // Riêng sửa mã: mã mới không được trống / trùng; các phiếu nhập của nhà cung
    // cấp tự đổi theo mã mới.
    @Override
    public void suaTheoMa(String ma) {
        NhaCungCap ncc = timTheoMa(ma);
        if (ncc == null) {
            System.out.println("Khong tim thay nha cung cap!");
            return;
        }
        int opt;
        do {
            System.out.println("\n--- SUA NHA CUNG CAP ---");
            ncc.xuat();
            System.out.println("1. Ma nha cung cap");
            System.out.println("2. Ten nha cung cap");
            System.out.println("3. Dia chi");
            System.out.println("0. Xong");
            System.out.print("Chon thong tin can sua: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1: {
                    System.out.print("Ma nha cung cap moi: ");
                    String maMoi = sc.nextLine();
                    if (maMoi.isEmpty()) {
                        System.out.println("Ma khong duoc de trong!");
                    } else if (maMoi.equals(ncc.getMaNCC())) {
                        System.out.println("Ma moi giong ma cu, khong doi.");
                    } else if (timTheoMa(maMoi) != null) {
                        System.out.println("Ma nha cung cap da ton tai!");
                    } else {
                        QLDSPNH.dspnh.doiMaNCC(ncc.getMaNCC(), maMoi); // đổi luôn mã trong các phiếu nhập
                        ncc.setMaNCC(maMoi);
                    }
                    break;
                }
                case 2:
                    System.out.print("Ten nha cung cap moi: ");
                    ncc.setTenNCC(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Dia chi moi: ");
                    ncc.setDiaChi(sc.nextLine());
                    break;
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
        System.out.println("3. Theo dia chi (gan dung)");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        int dem = 0;
        switch (opt) {
            case 1: {
                System.out.print("Nhap ma nha cung cap: ");
                NhaCungCap ncc = timTheoMa(sc.nextLine());
                if (ncc != null) {
                    ncc.xuat();
                    dem = 1;
                }
                break;
            }
            case 2: {
                System.out.print("Nhap tu khoa: ");
                String tuKhoa = sc.nextLine().toLowerCase();
                for (int i = 0; i < soLuong; i++) {
                    if (danhSach[i].getTenNCC().toLowerCase().contains(tuKhoa)) {
                        danhSach[i].xuat();
                        dem++;
                    }
                }
                break;
            }
            case 3: {
                System.out.print("Nhap tu khoa (dia chi): ");
                String tuKhoa = sc.nextLine().toLowerCase();
                for (int i = 0; i < soLuong; i++) {
                    if (danhSach[i].getDiaChi().toLowerCase().contains(tuKhoa)) {
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

    // ===== g. THỐNG KÊ NHIỀU KHÓA NHÀ CUNG CẤP (in ra màn hình) =====
    // Menu thống kê: mỗi lựa chọn là một khóa thống kê khác nhau, gọi hàm riêng bên
    // dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach nha cung cap rong!");
            return;
        }
        System.out.println("1. Moi nha cung cap: so phieu nhap va tong tien");
        System.out.println("2. Nha cung cap chua co phieu nhap");
        System.out.println("0. Thoat");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeTheoNCC();
                break;
            case 2:
                thongKeChuaNhapHang();
                break;
            default:
                break;
        }
    }

    // 1. Với MỖI nhà cung cấp: số phiếu nhập và tổng tiền nhập
    // (lấy số liệu từ danh sách phiếu nhập dùng chung QLDSPNH.dspnh)
    public void thongKeTheoNCC() {
        for (int i = 0; i < soLuong; i++) {
            String ma = danhSach[i].getMaNCC();
            System.out.println(ma + " | " + danhSach[i].getTenNCC() + " | " + QLDSPNH.dspnh.soPhieuTheoNCC(ma)
                    + " phieu nhap | tong tien nhap: " + (long) QLDSPNH.dspnh.tongTienTheoNCC(ma));
        }
    }

    // 3. Liệt kê nhà cung cấp chưa có phiếu nhập nào
    public void thongKeChuaNhapHang() {
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (QLDSPNH.dspnh.soPhieuTheoNCC(danhSach[i].getMaNCC()) == 0) {
                danhSach[i].xuat();
                dem++;
            }
        }
        System.out.println("Co " + dem + " nha cung cap chua co phieu nhap.");
    }

    // ===== ĐỌC / GHI FILE TXT (interface IFile) =====
    // Mỗi đối tượng 1 dòng, các thuộc tính ngăn cách nhau bằng dấu ;
    @Override
    public void docFile(String tenFile) {
        try {
            Scanner fs = new Scanner(new File(tenFile));
            while (fs.hasNextLine()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.fromFileString(fs.nextLine());
                them(ncc);
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
