import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class HoaDon {
    private String maHD;
    private LocalDate ngayLap;
    private String maNV;
    private String maKH;
    Scanner sc = new Scanner(System.in);
    // Mảng các dòng chi tiết của hóa đơn
    private ChiTietHoaDon[] dsCT;
    private int soCT;

    // static: đếm số hóa đơn đã tạo
    private static int soHD = 0;

    public HoaDon() {
        this.maHD = "";
        this.ngayLap = LocalDate.now();
        this.maKH = "";
        this.maNV = "";
        this.soCT = 0;
        this.dsCT = new ChiTietHoaDon[10];
    }

    // Constructor sao chép (sao chép cả các dòng chi tiết)
    public HoaDon(HoaDon a) {
        this.maHD = a.maHD;
        this.ngayLap = a.ngayLap;
        this.maKH = a.maKH;
        this.maNV = a.maNV;
        soCT = 0;
        this.dsCT = new ChiTietHoaDon[soCT];
        for (int i = 0; i < a.soCT; i++) {
            ChiTietHoaDon b = new ChiTietHoaDon(a.dsCT[i]);
            themChiTiet(b);
        }
    }

    // Thêm 1 dòng chi tiết va` gan maHD
    public void themChiTiet(ChiTietHoaDon ct) {
        if (soCT == dsCT.length) {
            dsCT = Arrays.copyOf(dsCT, dsCT.length * 2);
        }
        ct.setMaHD(this.maHD);
        dsCT[soCT] = ct;
        soCT++;
    }

    public void nhap() {
        System.out.print("Ma hoa don: ");
        maHD = sc.nextLine();
        System.out.print("Thong tin ngay lap hoa don:");
        System.out.print("Nhap ngay:");
        int ngay = sc.nextInt();
        sc.nextLine();
        System.out.print("Nhap thang:");
        int thang = sc.nextInt();
        sc.nextLine();
        System.out.print("Nhap nam:");
        int nam = sc.nextInt();
        sc.nextLine();
        ngayLap = LocalDate.of(nam, thang, ngay);
        System.out.print("Ma nhan vien: ");
        maNV = sc.nextLine();
        System.out.print("Ma khach hang: ");
        maKH = sc.nextLine();
        System.out.print("So san pham trong hoa don: ");
        int n = sc.nextInt();
        sc.nextLine();
        soCT = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("San pham thu " + (i + 1));
            ChiTietHoaDon a = new ChiTietHoaDon();
            a.nhap();
            themChiTiet(a);
        }
    }

    // Tổng tiền tính từ các dòng chi tiết, không cần lưu riêng
    public double tinhTongTien() {
        double tong = 0;
        for (int i = 0; i < soCT; i++) {
            tong += dsCT[i].thanhTien();
        }
        return tong;
    }

    public void xuat() {
        System.out.printf("Hoa don %s | Ngay: %s | NV: %s | KH: %s%n",
                maHD, ngayLap.getDayOfMonth() + "/" + ngayLap.getMonthValue() + "/" + ngayLap.getYear(), maNV, maKH);
        for (int i = 0; i < soCT; i++) {
            dsCT[i].xuat();
        }
        System.out.printf("Tong tien: %,.0f%n", tinhTongTien());
    }

    // ----- static -----
    public static int getSoHD() {
        return soHD;
    }

    // ----- getter / setter -----
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String a) {
        this.maHD = a;
        for (int i = 0; i < soCT; i++) {
            dsCT[i].setMaHD(a); // giữ khóa ngoại của chi tiết đồng bộ
        }
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate a) {
        this.ngayLap = a;
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

    public int getSoCT() {
        return soCT;
    }
}