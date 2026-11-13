import java.util.Scanner;

public class NhaCungCap {
    Scanner sc = new Scanner(System.in);
    private String maNCC;
    private String tenNCC;
    private String diaChi;

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
        System.out.print("Ma nha cung cap: ");
        maNCC = sc.nextLine();
        System.out.print("Ten nha cung cap: ");
        tenNCC = sc.nextLine();
        System.out.print("Dia chi: ");
        diaChi = sc.nextLine();
    }

    public void xuat() {
        System.out.println(maNCC + " | " + tenNCC + " | " + diaChi);
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

    // Một dòng trong file: maNCC;tenNCC;diaChi
    public String toFileString() {
        return maNCC + ";" + tenNCC + ";" + diaChi;
    }

    // Ngược lại với toFileString: đọc 1 dòng trong file rồi gán vào các thuộc tính
    public void fromFileString(String dong) {
        String[] p = dong.split(";", -1);
        maNCC = p[0];
        tenNCC = p[1];
        diaChi = p[2];
    }
}
