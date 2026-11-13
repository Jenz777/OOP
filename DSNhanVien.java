import java.util.*;

public class DSNhanVien implements IQuanLy<NhanVien> {
    private NhanVien[] danhSach;
    private int soLuong;
    Scanner sc = new Scanner(System.in);

    public void setsoLuong(int n) {
        this.soLuong = n;
    }

    public int getsoLuong() {
        return this.soLuong;
    }

    public DSNhanVien() {
        danhSach = new NhanVien[10];
        soLuong = 0;
    }

    public DSNhanVien(DSNhanVien a) { // copy constructor
        this.soLuong = a.soLuong;
        this.danhSach = new NhanVien[soLuong];
        for (int i = 0; i < soLuong; i++) {
            this.danhSach[i] = new NhanVien(a.danhSach[i]);
        }
    }

    public void them(NhanVien nh) {
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = nh;
        soLuong++;
    }

    public void nhapN() {

        System.out.print("Ban muon nhap bao nhieu nhan vien ban dau ? n = ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Nhap nguoi thu " + (i + 1) + " ---");

            NhanVien nhMoi = new NhanVien();

            nhMoi.nhap();

            this.them(nhMoi);
        }
    }

    public void themK() {
        System.out.print("Nhap K nhan vien muon them:");
        int sl = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < sl; i++) {
            System.out.println("nhan vien thu " + (soLuong + 1) + " |");
            NhanVien nhmoi = new NhanVien();
            nhmoi.nhap();
            this.them(nhmoi);
        }
    }

    public void xuat() {
        for (int i = 0; i < soLuong; i++) {
            System.out.println("nhan vien thu " + (i + 1) + " |");
            danhSach[i].xuat();
        }
    }

    public NhanVien timTheoMa(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(ma)) {
                return danhSach[i];
            }
        }
        return null;
    }

    private int timViTri(String a) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNV().equals(a)) {
                return i;
            }
        }
        return -1;
    }

    public void suaTheoMa(String ma) {
        NhanVien nh = timTheoMa(ma);
        if (nh == null) {
            System.out.println("Khong tim thay nhan vien!");
            return;
        }

        int opt;
        do {
            System.out.println("\nThong tin nhan vien:");
            nh.xuat();
            System.out.println("Ban muon sua thong tin nao ?:");
            System.out.println("1. Ma nhan vien");
            System.out.println("2. Ho lot");
            System.out.println("3. Ten");
            System.out.println("4. Vi tri");
            System.out.println("5. Luong thang");
            System.out.println("0. Thoat");
            System.out.print("Lua chon cua ban: ");
            opt = sc.nextInt();
            sc.nextLine(); // đọc lại mỗi vòng

            switch (opt) {
                case 1:
                    System.out.print("Nhap ma nhan vien moi: ");
                    nh.setMaNH(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Nhap ho lot moi: ");
                    nh.setHoLot(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Nhap ten moi: ");
                    nh.setTen(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap vi tri moi: ");
                    nh.setViTri(sc.nextLine());
                    sc.nextLine();
                    break;
                case 5:
                    System.out.print("Nhap luong thang moi: ");
                    nh.setLuongThang(sc.nextDouble());
                    sc.nextLine();
                    break;
                case 0:
                    System.out.println("Da sua xong!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (opt != 0);
    }

    public boolean xoaTheoMa(String a) {
        int index = timViTri(a);
        if (index != -1) {
            for (int i = index; i < soLuong - 1; i++) {
                danhSach[i] = danhSach[i + 1];
            }
            danhSach[soLuong - 1] = null;
            soLuong--;
            return true;
        } else {
            return false;
        }
    }
}
