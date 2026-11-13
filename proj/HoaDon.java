import java.time.LocalDate;

// Hóa đơn: chỉ giữ thông tin chung. Các dòng chi tiết nằm ở lớp ChiTietHoaDon (danh sách riêng).
public class HoaDon {
    private String maHD;
    private String maNV;
    private String maKH;
    private LocalDate ngayLap;
    private double tongTien;

    // static: đếm số hóa đơn đã tạo

    public HoaDon() {
        this.maHD = "";
        this.maNV = "";
        this.maKH = "";
        this.ngayLap = LocalDate.now();
        this.tongTien = 0;
    }

    public HoaDon(HoaDon a) {
        this.maHD = a.maHD;
        this.maNV = a.maNV;
        this.maKH = a.maKH;
        this.ngayLap = a.ngayLap;
        this.tongTien = a.tongTien;
    }

    public void xuat() {
        System.out.println("Hoa don: " + maHD + " | Ngay: " + ngayLap + " | NV: " + maNV + " | KH: " + maKH
                + " | Tong tien: " + (long) tongTien);
    }


    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String a) {
        this.maHD = a;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String a) {
        this.maNV = a;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String a) {
        this.maKH = a;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate a) {
        this.ngayLap = a;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double a) {
        this.tongTien = a;
    }

    // Một dòng trong file: maHD;maNV;maKH;ngay(yyyy-MM-dd);tongTien
    public String toFileString() {
        return maHD + ";" + maNV + ";" + maKH + ";" + ngayLap + ";" + tongTien;
    }

    // Ngược lại với toFileString: đọc 1 dòng trong file rồi gán vào các thuộc tính
    public void fromFileString(String dong) {
        String[] p = dong.split(";", -1);
        maHD = p[0];
        maNV = p[1];
        maKH = p[2];
        ngayLap = LocalDate.parse(p[3]);
        tongTien = Double.parseDouble(p[4]);
    }
}
