import java.util.*;

public class DSKhachHang implements IQuanLy<KhachHang> {
    private KhachHang[] danhSach;
    private int soLuong;
    Scanner sc = new Scanner(System.in);

    public void setsoLuong(int n) {
        this.soLuong = n;
    }

    public int getsoLuong() {
        return this.soLuong;
    }

    public DSKhachHang() {
        danhSach = new KhachHang[10];
        soLuong = 0;
    }

    public DSKhachHang(DSKhachHang a) { // copy constructor
        this.soLuong = a.soLuong;
        this.danhSach = new KhachHang[soLuong];
        for (int i = 0; i < soLuong; i++) {
            this.danhSach[i] = new KhachHang(a.danhSach[i]);
        }
    }

    public void them(KhachHang kh) {
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = kh;
        soLuong++;
    }

    public void nhapN() {

        System.out.print("Ban muon nhap bao nhieu khach hang ban dau ? n = ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Nhap nguoi thu " + (i + 1) + " ---");

            KhachHang khMoi = new KhachHang();

            khMoi.nhap();

            this.them(khMoi);
        }
    }

    public void themK() {
        System.out.print("Nhap K khach hang muon them:");
        int sl = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < sl; i++) {
            System.out.println("Khach hang thu " + (soLuong + 1) + " |");
            KhachHang khmoi = new KhachHang();
            khmoi.nhap();
            this.them(khmoi);
        }
    }

    public void xuat() {
        for (int i = 0; i < soLuong; i++) {
            System.out.println("Khach hang thu " + (i + 1) + " |");
            danhSach[i].xuat();
        }
    }

    public KhachHang timTheoMa(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaKH().equals(ma)) {
                return danhSach[i];
            }
        }
        return null;
    }

    private int timViTri(String a) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaKH().equals(a)) {
                return i;
            }
        }
        return -1;
    }

    public void suaTheoMa(String ma) {
        KhachHang kh = timTheoMa(ma);
        if (kh == null) {
            System.out.println("Khong tim thay khach hang!");
            return;
        }

        int opt;
        do {
            System.out.println("\nThong tin khach hang:");
            kh.xuat();
            System.out.println("Ban muon sua thong tin nao ?:");
            System.out.println("1. Ma khach hang");
            System.out.println("2. Ho lot");
            System.out.println("3. Ten");
            System.out.println("4. Tong tien mua hang");
            System.out.println("0. Thoat");
            System.out.print("Lua chon cua ban: ");
            opt = sc.nextInt();
            sc.nextLine(); // đọc lại mỗi vòng

            switch (opt) {
                case 1:
                    System.out.print("Nhap ma khach hang moi: ");
                    kh.setMaKH(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Nhap ho lot moi: ");
                    kh.setHoLot(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Nhap ten moi: ");
                    kh.setTen(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap tong tien mua hang moi: ");
                    kh.setTongTienMuaHang(sc.nextDouble());
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
