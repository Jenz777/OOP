import java.util.Scanner;

public class NhanVien {
    Scanner sc = new Scanner(System.in);
    private String maNV;
    private String hoLot;
    private String ten;
    private String viTri;
    private double luongThang;

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
        System.out.print("Ma nhan vien: ");
        maNV = sc.nextLine();
        System.out.print("Ho lot: ");
        hoLot = sc.nextLine();
        System.out.print("Ten: ");
        ten = sc.nextLine();
        System.out.print("Vi tri (chuc vu): ");
        viTri = sc.nextLine();
        System.out.print("Luong thang: ");
        luongThang = sc.nextDouble();
        sc.nextLine();
    }

    public void xuat() {
        System.out.println(maNV + " | " + hoLot + " " + ten + " | " + viTri + " | Luong: " + (long) luongThang);
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

    // Một dòng trong file: maNV;hoLot;ten;viTri;luongThang
    public String toFileString() {
        return maNV + ";" + hoLot + ";" + ten + ";" + viTri + ";" + luongThang;
    }

    // Ngược lại với toFileString: đọc 1 dòng trong file rồi gán vào các thuộc tính
    public void fromFileString(String dong) {
        String[] p = dong.split(";", -1);
        maNV = p[0];
        hoLot = p[1];
        ten = p[2];
        viTri = p[3];
        luongThang = Double.parseDouble(p[4]);
    }
}
