import java.time.LocalDate;

// Phiếu nhập hàng: làm giống hóa đơn. Chi tiết nằm ở lớp ChiTietPhieuNhap (danh sách riêng).
public class PhieuNhapHang {
    private String maPNH;
    private String maNCC;
    private String maNV;
    private LocalDate ngayNhap;
    private double tongTien;

    public PhieuNhapHang() {
        this.maPNH = "";
        this.maNCC = "";
        this.maNV = "";
        this.ngayNhap = LocalDate.now();
        this.tongTien = 0;
    }

    public PhieuNhapHang(PhieuNhapHang a) {
        this.maPNH = a.maPNH;
        this.maNCC = a.maNCC;
        this.maNV = a.maNV;
        this.ngayNhap = a.ngayNhap;
        this.tongTien = a.tongTien;
    }

    public void xuat() {
        System.out.println("Phieu nhap: " + maPNH + " | Ngay: " + ngayNhap + " | NCC: " + maNCC + " | NV: " + maNV
                + " | Tong tien: " + (long) tongTien);
    }

    public String getMaPNH() {
        return maPNH;
    }

    public void setMaPNH(String a) {
        this.maPNH = a;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String a) {
        this.maNCC = a;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String a) {
        this.maNV = a;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate a) {
        this.ngayNhap = a;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double a) {
        this.tongTien = a;
    }

    // Một dòng trong file: maPNH;maNCC;maNV;ngay(yyyy-MM-dd);tongTien
    public String toFileString() {
        return maPNH + ";" + maNCC + ";" + maNV + ";" + ngayNhap + ";" + tongTien;
    }

    // Ngược lại với toFileString: đọc 1 dòng trong file rồi gán vào các thuộc tính
    public void fromFileString(String dong) {
        String[] p = dong.split(";", -1);
        maPNH = p[0];
        maNCC = p[1];
        maNV = p[2];
        ngayNhap = LocalDate.parse(p[3]);
        tongTien = Double.parseDouble(p[4]);
    }
}
