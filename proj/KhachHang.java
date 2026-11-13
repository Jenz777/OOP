import java.util.Scanner;

public class KhachHang {
    Scanner sc = new Scanner(System.in);
    private String maKH;
    private String hoLot;
    private String ten;
    private double tongTienMuaHang;

    // Constructor mặc định
    public KhachHang() {
        this.maKH = "";
        this.hoLot = "";
        this.ten = "";
        this.tongTienMuaHang = 0.0;
    }

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
        tongTienMuaHang = -1;
        while (tongTienMuaHang < 0) { // không cho nhập số âm
            System.out.print("Tong tien mua hang (>= 0): ");
            tongTienMuaHang = sc.nextDouble();
            sc.nextLine();
        }
    }

    public void xuat() {
        System.out.println(maKH + " | " + hoLot + " " + ten + " | Tong tien mua: " + (long) tongTienMuaHang);
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

    // Một dòng trong file: maKH;hoLot;ten;tongTienMuaHang
    public String toFileString() {
        return maKH + ";" + hoLot + ";" + ten + ";" + tongTienMuaHang;
    }

    // Ngược lại với toFileString: đọc 1 dòng trong file rồi gán vào các thuộc tính
    public void fromFileString(String dong) {
        String[] p = dong.split(";", -1);
        maKH = p[0];
        hoLot = p[1];
        ten = p[2];
        tongTienMuaHang = Double.parseDouble(p[3]);
    }
}
