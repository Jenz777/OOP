// Một dòng chi tiết của hóa đơn: bán sản phẩm nào, bao nhiêu, giá bao nhiêu.
// giaBan được CHÉP từ sản phẩm lúc bán, nên sau này giá sản phẩm đổi thì hóa đơn cũ không đổi.
public class ChiTietHoaDon {
    private String maHD;
    private String maSP;
    private int soLuong;
    private double giaBan;
    private double thanhTien; // = soLuong * giaBan

    public ChiTietHoaDon() {
        this.maHD = "";
        this.maSP = "";
        this.soLuong = 0;
        this.giaBan = 0;
        this.thanhTien = 0;
    }

    public ChiTietHoaDon(ChiTietHoaDon a) {
        this.maHD = a.maHD;
        this.maSP = a.maSP;
        this.soLuong = a.soLuong;
        this.giaBan = a.giaBan;
        this.thanhTien = a.thanhTien;
    }

    public void xuat() {
        System.out.println("  " + maHD + " | " + maSP + " | SL: " + soLuong + " | Gia ban: " + (long) giaBan
                + " | Thanh tien: " + (long) thanhTien);
    }

    // Khóa của dòng chi tiết = cặp (maHD, maSP), viết thành 1 chuỗi "maHD|maSP"
    // (dùng làm "mã" cho các hàm của IQuanLy ở DSChiTietHoaDon)
    public String getKhoa() {
        return maHD + "|" + maSP;
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
        this.thanhTien = this.soLuong * this.giaBan; // giữ thành tiền luôn khớp
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double a) {
        this.giaBan = a;
        this.thanhTien = this.soLuong * this.giaBan; // giữ thành tiền luôn khớp
    }

    public double getThanhTien() {
        return thanhTien;
    }

    // Một dòng trong file: maHD;maSP;soLuong;giaBan;thanhTien
    public String toFileString() {
        return maHD + ";" + maSP + ";" + soLuong + ";" + giaBan + ";" + thanhTien;
    }

    // Ngược lại với toFileString: đọc 1 dòng trong file rồi gán vào các thuộc tính
    public void fromFileString(String dong) {
        String[] p = dong.split(";", -1);
        maHD = p[0];
        maSP = p[1];
        soLuong = Integer.parseInt(p[2]);
        giaBan = Double.parseDouble(p[3]);
        thanhTien = Double.parseDouble(p[4]);
    }
}
