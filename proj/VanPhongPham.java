import java.util.Scanner;

public abstract class VanPhongPham {
    Scanner sc = new Scanner(System.in);
    private String maSP;
    private String tenSP;
    private int soLuongTon;
    private double donGia;
    private String donViTinh;

    public VanPhongPham() {
        this.maSP = "";
        this.tenSP = "";
        this.soLuongTon = 0;
        this.donGia = 0;
        this.donViTinh = "";
    }

    // Constructor sao chép
    public VanPhongPham(VanPhongPham a) {
        this.maSP = a.maSP;
        this.tenSP = a.tenSP;
        this.soLuongTon = a.soLuongTon;
        this.donGia = a.donGia;
        this.donViTinh = a.donViTinh;
    }

    // Hàm trừu tượng: mỗi loại sản phẩm tự tính giá bán (đa hình)
    public abstract double tinhGiaBan();

    // Phục vụ chức năng sửa (ĐA HÌNH): mỗi loại sản phẩm tự nói mình có những thuộc
    // tính riêng nào
    // và tự hỏi - tự gán khi được chọn sửa (vị trí tính từ 0).
    public abstract String[] tenThuocTinhRieng();

    public abstract void suaThuocTinhRieng(int viTri);

    // Mỗi loại sản phẩm tự tạo dòng chữ để ghi vào file (đa hình, giống tinhGiaBan)
    public abstract String toFileString();

    // Ngược lại với toFileString: mỗi lớp con tự đọc dòng của mình (ĐA HÌNH)
    public abstract void fromFileString(String dong);

    // Đọc 5 thuộc tính chung: p[0] là chữ BUT/TAP nên bắt đầu từ p[1]
    protected void docPhanChung(String[] p) {
        maSP = p[1];
        tenSP = p[2];
        soLuongTon = Integer.parseInt(p[3]);
        donGia = Double.parseDouble(p[4]);
        donViTinh = p[5];
    }

    // Các thuộc tính chung, ngăn cách nhau bằng dấu ;
    protected String thuocTinhChung() {
        return maSP + ";" + tenSP + ";" + soLuongTon + ";" + donGia + ";" + donViTinh;
    }

    public String getMaSP() {
        return this.maSP;
    }

    public String getTenSP() {
        return this.tenSP;
    }

    public int getSoLuongTon() {
        return this.soLuongTon;
    }

    public double getDonGia() {
        return this.donGia;
    }

    public String getDonViTinh() {
        return this.donViTinh;
    }

    public void setMaSP(String a) {
        this.maSP = a;
    }

    public void setTenSP(String a) {
        this.tenSP = a;
    }

    public void setSoLuongTon(int a) {
        this.soLuongTon = a;
    }

    public void setDonGia(double a) {
        this.donGia = a;
    }

    public void setDonViTinh(String a) {
        this.donViTinh = a;
    }

    public void nhap() {
        System.out.print("Ma san pham: ");
        maSP = sc.nextLine();
        System.out.print("Ten san pham: ");
        tenSP = sc.nextLine();
        soLuongTon = -1;
        while (soLuongTon < 0) { // không cho nhập số âm
            System.out.print("So luong ton (>= 0): ");
            soLuongTon = sc.nextInt();
            sc.nextLine();
        }
        donGia = -1;
        while (donGia < 0) {
            System.out.print("Don gia (>= 0): ");
            donGia = sc.nextDouble();
            sc.nextLine();
        }
        System.out.print("Don vi tinh: ");
        donViTinh = sc.nextLine();
    }

    // Không xuống dòng: lớp con in tiếp phần riêng rồi mới xuống dòng
    public void xuat() {
        System.out.print(maSP + " | " + tenSP + " | SL: " + soLuongTon + " | Gia: " + donGia + " | DVT: " + donViTinh);
    }
}
