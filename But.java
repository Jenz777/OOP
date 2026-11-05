import java.util.*;

public class But extends VanPhongPham {
    private String mauSac;
    private String loaiBut;
    private static final double LOI_NHUAN = 0.1;
    Scanner sc = new Scanner(System.in);

    public But() {
        super();
        this.mauSac = "";
        this.loaiBut = "";
    }

    public But(But a) {
        super((VanPhongPham) a);
        this.mauSac = a.mauSac;
        this.loaiBut = a.loaiBut;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String a) {
        this.mauSac = a;
    }

    public String getLoaiBut() {
        return loaiBut;
    }

    public void setLoaiBut(String a) {
        this.loaiBut = a;
    }

    @Override
    public void nhap() {
        super.nhap();
        System.out.print("Nhap mau sac: ");
        mauSac = sc.nextLine();
        System.out.print("Nhap loai but: ");
        loaiBut = sc.nextLine();
    }

    @Override
    public double tinhGiaBan() {
        return this.getDonGia() * (1 + LOI_NHUAN);
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" | mau " + mauSac + " | loai " + loaiBut + " | gia ban: " + (long) tinhGiaBan());
    }
}