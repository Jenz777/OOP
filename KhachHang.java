import java.util.Scanner;

public class KhachHang {
    private String maKH;
    private String hoLot;
    private String ten;
    private double tongTienMuaHang;
    Scanner sc = new Scanner(System.in);

    // Constructor mặc định
    public KhachHang() {
        this.maKH = "";
        this.hoLot = "";
        this.ten = "";
        this.tongTienMuaHang = 0.0;
    }

    // Constructor đầy đủ

    // Constructor sao chép
    public KhachHang(KhachHang kh) {
        this.maKH = kh.maKH;
        this.hoLot = kh.hoLot;
        this.ten = kh.ten;
        this.tongTienMuaHang = kh.tongTienMuaHang;
    }

    public void nhap() {
        System.out.print("Ma khach hang: ");
        maKH = sc.nextLine();
        System.out.print("Ho lot: ");
        hoLot = sc.nextLine();
        System.out.print("Ten: ");
        ten = sc.nextLine();
        System.out.print("Tong tien mua hang: ");
        tongTienMuaHang = Double.parseDouble(sc.nextLine());
    }

    public void xuat() {
        System.out.printf("%-8s %-15s %-10s %,15.0f%n",
                maKH, hoLot, ten, tongTienMuaHang);
    }

    // Cộng dồn tiền khi khách mua thêm hàng
    public void congTienMuaHang(double soTien) {
        tongTienMuaHang += soTien;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoLot() {
        return hoLot;
    }

    public void setHoLot(String hoLot) {
        this.hoLot = hoLot;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getTongTienMuaHang() {
        return tongTienMuaHang;
    }

    public void setTongTienMuaHang(double tongTienMuaHang) {
        this.tongTienMuaHang = tongTienMuaHang;
    }
}