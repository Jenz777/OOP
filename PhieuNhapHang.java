import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class PhieuNhapHang {
    private String maPNH;
    private LocalDate ngayNhap;
    private String maNCC;
    private String maNV;
    Scanner sc = new Scanner(System.in);
    // Mảng các dòng chi tiết của phiếu nhập
    private ChiTietPhieuNhap[] dsCT;
    private int soCT;

    // static: đếm số phiếu nhập đã tạo
    private static int soPhieu = 0;

    // Constructor mặc định
    public PhieuNhapHang() {
        this.maPNH = "";
        this.ngayNhap = LocalDate.now();
        this.maNCC = "";
        this.maNV = "";
        this.dsCT = new ChiTietPhieuNhap[10];
        this.soCT = 0;
    }

    // Constructor sao chép (sao chép cả các dòng chi tiết)
    public PhieuNhapHang(PhieuNhapHang p) {
        this.maPNH = p.maPNH;
        this.ngayNhap = p.ngayNhap;
        this.maNCC = p.maNCC;
        this.maNV = p.maNV;
        this.soCT = 0;
        this.dsCT = new ChiTietPhieuNhap[soCT];
        for (int i = 0; i < p.soCT; i++) {
            ChiTietPhieuNhap b = new ChiTietPhieuNhap(p.dsCT[i]);
            themChiTiet(b);
        }
    }

    // them chi tiet va` gan maPNH
    public void themChiTiet(ChiTietPhieuNhap ct) {
        if (soCT == dsCT.length) {
            dsCT = Arrays.copyOf(dsCT, dsCT.length * 2);
        }
        ct.setMaPNH(maPNH);
        dsCT[soCT] = ct;
        soCT++;
    }

    public double tinhTongTienNhap() {
        double tong = 0;
        for (int i = 0; i < soCT; i++) {
            tong += dsCT[i].thanhTien();
        }
        return tong;
    }

    public void nhap() {
        System.out.print("Nhap ma phieu nhap: ");
        maPNH = sc.nextLine();
        System.out.println("Nhap thong tin ngay nhap kho:");
        System.out.print("- Ngay:");
        int ngay = sc.nextInt();
        sc.nextLine();
        System.out.print("- Thang:");
        int thang = sc.nextInt();
        sc.nextLine();
        System.out.print("- Nam:");
        int nam = sc.nextInt();
        sc.nextLine();
        ngayNhap = LocalDate.of(nam, thang, ngay);
        System.out.print("Nhap ma nha cung cap: ");
        maNCC = sc.nextLine();
        System.out.print("Nhap ma nhan vien: ");
        maNV = sc.nextLine();

        System.out.print("So san pham trong phieu nhap: ");
        int n = sc.nextInt();
        sc.nextLine();
        this.soCT = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("San pham thu " + (i + 1));
            ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
            ct.nhap();
            themChiTiet(ct);
        }
    }

    public void xuat() {
        System.out.printf("Phieu nhap %s | Ngay: %s | NCC: %s | NV: %s%n",
                maPNH, ngayNhap.getDayOfMonth() + "/" + ngayNhap.getMonthValue() + "/" + ngayNhap.getYear(), maNCC,
                maNV);
        for (int i = 0; i < soCT; i++) {
            dsCT[i].xuat();
        }
        System.out.printf("Tong tien nhap: %,.0f%n", tinhTongTienNhap());
    }

    // ----- static -----
    public static int getSoPhieu() {
        return soPhieu;
    }

    // ----- getter / setter -----
    public String getMaPNH() {
        return maPNH;
    }

    public void setMaPNH(String a) {
        this.maPNH = a;
        for (int i = 0; i < soCT; i++) {
            dsCT[i].setMaPNH(a); // giữ khóa ngoại của chi tiết đồng bộ
        }
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate a) {
        this.ngayNhap = a;
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

    public int getSoCT() {
        return soCT;
    }
}