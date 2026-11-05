import java.util.Scanner;

public class ChiTietHoaDon {
    private String maHD;
    private String maSP;
    private int soLuong;
    private double giaBan;
    Scanner sc = new Scanner(System.in);

    public ChiTietHoaDon() {
        this.maHD = "";
        this.maSP = "";
        this.soLuong = 0;
        this.giaBan = 0.0;
    }

    public ChiTietHoaDon(ChiTietHoaDon ct) {
        this.maHD = ct.maHD;
        this.maSP = ct.maSP;
        this.soLuong = ct.soLuong;
        this.giaBan = ct.giaBan;
    }

    // maHD do lớp HoaDon tự gán, nên không nhập ở đây
    public void nhap() {
        System.out.print("Nhap ma san pham: ");
        maSP = sc.nextLine();
        System.out.print("Nhap so luong: ");
        soLuong = sc.nextInt();
        sc.nextLine();
        System.out.print("Nhap gia ban: ");
        giaBan = sc.nextDouble();
        sc.nextLine();
    }

    public double thanhTien() {
        return soLuong * giaBan;
    }

    public void xuat() {
        System.out.printf("  %-8s %5d %,12.0f %,14.0f%n", maSP, soLuong, giaBan, thanhTien());
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String a) {
        this.maHD = a;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String a) {
        this.maSP = a;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int a) {
        this.soLuong = a;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double a) {
        this.giaBan = a;
    }
}