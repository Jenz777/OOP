import java.util.Scanner;

public class ChiTietPhieuNhap {
    private String maPNH;
    private String maSP;
    private int soLuongNhap;
    private double donGiaNhap;
    Scanner sc = new Scanner(System.in);

    public ChiTietPhieuNhap() {
        this.maPNH = "";
        this.maSP = "";
        this.soLuongNhap = 0;
        this.donGiaNhap = 0.0;
    }

    public ChiTietPhieuNhap(ChiTietPhieuNhap ct) {
        this.maPNH = ct.maPNH;
        this.maSP = ct.maSP;
        this.soLuongNhap = ct.soLuongNhap;
        this.donGiaNhap = ct.donGiaNhap;
    }

    // maPNH do lớp PhieuNhapHang tự gán, nên không nhập ở đây
    public void nhap() {
        System.out.print("Nhap ma san pham: ");
        maSP = sc.nextLine();
        System.out.print("Nhap so luong nhap: ");
        soLuongNhap = sc.nextInt();
        sc.nextLine();
        System.out.print("Nhap don gia nhap: ");
        donGiaNhap = sc.nextDouble();
        sc.nextLine();
    }

    public double thanhTien() {
        return soLuongNhap * donGiaNhap;
    }

    public void xuat() {
        System.out.printf("  %-8s %5d %,12.0f %,14.0f%n", maSP, soLuongNhap, donGiaNhap, thanhTien());
    }

    public String getMaPNH() {
        return maPNH;
    }

    public void setMaPNH(String a) {
        this.maPNH = a;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String a) {
        this.maSP = a;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int a) {
        this.soLuongNhap = a;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double a) {
        this.donGiaNhap = a;
    }
}