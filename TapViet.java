import java.util.*;

public class TapViet extends VanPhongPham {
    private int soTrang;
    private String loaiGiay;
    private static final double LOI_NHUAN = 0.15; // 15%
    Scanner sc = new Scanner(System.in);

    public TapViet() {
        super();
        this.soTrang = 0;
        this.loaiGiay = "";
    }

    public TapViet(TapViet a) {
        super((VanPhongPham) a);
        this.soTrang = a.soTrang;
        this.loaiGiay = a.loaiGiay;
    }

    public int getSoTrang() {
        return this.soTrang;
    }

    public void setSoTrang(int a) {
        this.soTrang = a;
    }

    public String getLoaiGiay() {
        return this.loaiGiay;
    }

    public void setLoaiGiay(String a) {
        this.loaiGiay = a;
    }

    @Override
    public void nhap() {
        super.nhap();
        System.out.println("Nhap so trang");
        this.soTrang = sc.nextInt();
        sc.nextLine();
        System.out.println("Nhap loai giay");
        this.loaiGiay = sc.nextLine();
    }

    @Override
    public double tinhGiaBan() {
        return getDonGia() * (1 + LOI_NHUAN);
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" | " + soTrang + " trang | giay " + loaiGiay + " | gia ban: " + (long) tinhGiaBan());
    }
}