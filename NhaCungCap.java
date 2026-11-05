import java.util.*;

public class NhaCungCap {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    Scanner sc = new Scanner(System.in);

    public NhaCungCap() {
        this.maNCC = "";
        this.tenNCC = "";
        this.diaChi = "";
    }

    public NhaCungCap(NhaCungCap a) {
        this.maNCC = a.maNCC;
        this.tenNCC = a.tenNCC;
        this.diaChi = a.diaChi;
    }

    public void nhap() {
        System.out.print("Nhap ma nha cung cap: ");
        maNCC = sc.nextLine();
        System.out.print("Nhap ten nha cung cap: ");
        tenNCC = sc.nextLine();
        System.out.print("Nhap dia chi: ");
        diaChi = sc.nextLine();
    }

    public void xuat() {
        System.out.printf("%-8s %-25s %-30s%n", maNCC, tenNCC, diaChi);
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String a) {
        this.maNCC = a;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String a) {
        this.tenNCC = a;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String a) {
        this.diaChi = a;
    }
}
