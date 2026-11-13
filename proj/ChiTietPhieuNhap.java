// Một dòng chi tiết của phiếu nhập: nhập sản phẩm nào, bao nhiêu, giá nhập bao nhiêu.
public class ChiTietPhieuNhap {
    private String maPNH;
    private String maSP;
    private int soLuongNhap;
    private double donGiaNhap;
    private double thanhTien; // = soLuongNhap * donGiaNhap

    public ChiTietPhieuNhap() {
        this.maPNH = "";
        this.maSP = "";
        this.soLuongNhap = 0;
        this.donGiaNhap = 0;
        this.thanhTien = 0;
    }

    public ChiTietPhieuNhap(ChiTietPhieuNhap a) {
        this.maPNH = a.maPNH;
        this.maSP = a.maSP;
        this.soLuongNhap = a.soLuongNhap;
        this.donGiaNhap = a.donGiaNhap;
        this.thanhTien = a.thanhTien;
    }

    public void xuat() {
        System.out.println("  " + maPNH + " | " + maSP + " | SL nhap: " + soLuongNhap + " | Gia nhap: " + (long) donGiaNhap
                + " | Thanh tien: " + (long) thanhTien);
    }

    // Khóa của dòng chi tiết = cặp (maPNH, maSP), viết thành 1 chuỗi "maPNH|maSP"
    // (dùng làm "mã" cho các hàm của IQuanLy ở DSChiTietPhieuNhap)
    public String getKhoa() {
        return maPNH + "|" + maSP;
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
        this.thanhTien = this.soLuongNhap * this.donGiaNhap;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double a) {
        this.donGiaNhap = a;
        this.thanhTien = this.soLuongNhap * this.donGiaNhap;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    // Một dòng trong file: maPNH;maSP;soLuongNhap;donGiaNhap;thanhTien
    public String toFileString() {
        return maPNH + ";" + maSP + ";" + soLuongNhap + ";" + donGiaNhap + ";" + thanhTien;
    }

    // Ngược lại với toFileString: đọc 1 dòng trong file rồi gán vào các thuộc tính
    public void fromFileString(String dong) {
        String[] p = dong.split(";", -1);
        maPNH = p[0];
        maSP = p[1];
        soLuongNhap = Integer.parseInt(p[2]);
        donGiaNhap = Double.parseDouble(p[3]);
        thanhTien = Double.parseDouble(p[4]);
    }
}
