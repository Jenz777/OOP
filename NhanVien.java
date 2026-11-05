import java.util.*;

public class NhanVien {
    private String maNV;
    private String hoLot;
    private String ten;
    private String viTri;
    private double luongThang;
    Scanner sc = new Scanner(System.in);

    public NhanVien() {
        this.maNV = "";
        this.hoLot = "";
        this.ten = "";
        this.viTri = "";
        this.luongThang = 0.0;
    }

    public NhanVien(NhanVien a) {
        this.maNV = a.maNV;
        this.hoLot = a.hoLot;
        this.ten = a.ten;
        this.viTri = a.viTri;
        this.luongThang = a.luongThang;
    }

    public void nhap() {
        System.out.print("Nhap ma nhan vien: ");
        maNV = sc.nextLine();
        System.out.print("Nhap ho lot: ");
        hoLot = sc.nextLine();
        System.out.print("Nhap ten: ");
        ten = sc.nextLine();
        System.out.print("Nhap chuc vu: ");
        viTri = sc.nextLine();
        System.out.print("Nhap luong thang: ");
        luongThang = sc.nextDouble();
        sc.nextLine();
    }

    public void xuat() {
        System.out.printf("%-8s %-15s %-10s %-12s %,15.0f%n",
                maNV, hoLot, ten, viTri, luongThang);
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String a) {
        this.maNV = a;
    }

    public String getHoLot() {
        return hoLot;
    }

    public void setHoLot(String a) {
        this.hoLot = a;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String a) {
        this.ten = a;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String a) {
        this.viTri = a;
    }

    public double getLuongThang() {
        return luongThang;
    }

    public void setLuongThang(double a) {
        this.luongThang = a;
    }
}
