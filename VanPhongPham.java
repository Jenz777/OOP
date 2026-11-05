import java.util.Scanner;

public abstract class VanPhongPham {
    private String maSP;
    private String tenSP;
    private int soLuongTon;
    private double donGia;
    private String donViTinh;
    private String maLoai;
    Scanner sc = new Scanner(System.in);

    public VanPhongPham() {
        this.maSP = "";
        this.tenSP = "";
        this.soLuongTon = 0;
        this.donGia = 0;
        this.donViTinh = "";
        this.maLoai = "";
    }

    public VanPhongPham(VanPhongPham a) {
        this.maSP = a.getMaSP();
        this.tenSP = a.getTenSP();
        this.soLuongTon = a.getSoLuongTon();
        this.donGia = a.getDonGia();
        this.donViTinh = a.getDonViTinh();
        this.maLoai = a.getMaLoai();
    }

    public abstract double tinhGiaBan();

    public String getMaSP() {
        return this.maSP;
    }

    public String getTenSP() {
        return this.tenSP;
    }

    public int getSoLuongTon() {
        return this.soLuongTon;
    }

    public double getDonGia() {
        return this.donGia;
    }

    public String getDonViTinh() {
        return this.donViTinh;
    }

    public void setMaSP(String a) {
        this.maSP = a;
    }

    public void setTenSP(String a) {
        this.tenSP = a;
    }

    public void setSoLuongTon(int a) {
        this.soLuongTon = a;
    }

    public void setDonGia(int a) {
        this.donGia = a;
    }

    public void setDonViTinh(String a) {
        this.donViTinh = a;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String a) {
        this.maLoai = a;
    }

    public void nhap() {
        System.out.println("Nhap ma san pham");
        this.maSP = sc.nextLine();
        System.out.println("Nhap ten san pham");
        this.tenSP = sc.nextLine();
        System.out.println("Nhap so luong ton san pham");
        this.soLuongTon = sc.nextInt();
        sc.nextLine();
        System.out.println("Nhap don gia san pham");
        this.donGia = sc.nextInt();
        sc.nextLine();
        System.out.println("Nhap don vi tinh san pham");
        this.donViTinh = sc.nextLine();
        System.out.print("Ma loai: ");
        this.maLoai = sc.nextLine();
    }

    public void xuat() {
        System.out.printf("%-8s %-20s %5d %,12.0f %-8s %-6s",
                maSP, tenSP, soLuongTon, donGia, donViTinh, maLoai);
    }

}