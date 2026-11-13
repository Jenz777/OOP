import java.io.*;
import java.util.*;

// Danh sách TẤT CẢ các dòng chi tiết của mọi hóa đơn (đủ chức năng IQuanLy + IFile).
// Khóa của một dòng là cặp (maHD, maSP) -> dùng chuỗi "maHD|maSP" làm "mã" cho các hàm của IQuanLy.
// Thêm / sửa / xóa ở đây chỉ làm cơ bản trên chính danh sách này (không đụng tới tồn kho, tổng tiền hóa đơn,
// tiền mua của khách). Phần nghiệp vụ nằm ở DSHoaDon.taoHoaDon (lập hóa đơn).
public class DSChiTietHoaDon implements IQuanLy<ChiTietHoaDon>, IFile {
    Scanner sc = new Scanner(System.in);
    private ChiTietHoaDon[] danhSach;
    private int soLuong;

    public DSChiTietHoaDon() {
        danhSach = new ChiTietHoaDon[10];
        soLuong = 0;
    }

    public int getSoLuong() {
        return soLuong;
    }

    // static: ghép mã hóa đơn + mã sản phẩm thành khóa của một dòng
    public static String taoKhoa(String maHD, String maSP) {
        return maHD + "|" + maSP;
    }

    // Thêm THÔ vào cuối mảng (không kiểm tra gì).
    // Dùng khi đọc file và khi DSHoaDon.taoHoaDon đang lập hóa đơn.
    public void themDong(ChiTietHoaDon ct) {
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = ct;
        soLuong++;
    }

    // ===== c. THÊM =====
    // Thêm cơ bản 1 dòng: chỉ kiểm tra số hợp lệ và không trùng (mã hóa đơn + mã
    // sản phẩm).
    @Override
    public void them(ChiTietHoaDon ct) {
        if (ct == null) {
            return;
        }
        if (ct.getSoLuong() <= 0 || ct.getGiaBan() <= 0) {
            System.out.println("So luong va gia ban phai > 0!");
            return;
        }
        if (timViTri(ct.getKhoa()) != -1) {
            System.out.println("Dong " + ct.getKhoa() + " da ton tai!");
            return;
        }
        themDong(ct);
    }

    // Nhập 1 dòng chi tiết từ bàn phím
    public ChiTietHoaDon taoChiTiet() {
        ChiTietHoaDon ct = new ChiTietHoaDon();
        System.out.print("Ma hoa don: ");
        ct.setMaHD(sc.nextLine());
        System.out.print("Ma san pham: ");
        ct.setMaSP(sc.nextLine());
        System.out.print("Gia ban: ");
        ct.setGiaBan(sc.nextDouble());
        sc.nextLine();
        System.out.print("So luong: ");
        ct.setSoLuong(sc.nextInt());
        sc.nextLine();
        return ct;
    }

    // ===== a. NHẬP N =====
    @Override
    public void nhapN() {
        System.out.print("Ban muon nhap bao nhieu dong chi tiet? n = ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("--- Nhap dong chi tiet thu " + (i + 1) + " ---");
            them(taoChiTiet());
        }
    }

    @Override
    public void themK() {
        System.out.print("Nhap k dong chi tiet muon them: ");
        int k = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < k; i++) {
            System.out.println("--- Them dong chi tiet thu " + (i + 1) + " ---");
            them(taoChiTiet());
        }
    }

    // ===== b. XUẤT =====
    @Override
    public void xuat() {
        if (soLuong == 0) {
            System.out.println("Chua co chi tiet hoa don nao!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat();
        }
    }

    // In các dòng chi tiết của một hóa đơn, trả về số dòng
    public int xuatTheoMaHD(String maHD) {
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaHD().equals(maHD)) {
                danhSach[i].xuat();
                dem++;
            }
        }
        return dem;
    }

    // ===== TÌM THEO MÃ (mã = "maHD|maSP") =====
    @Override
    public ChiTietHoaDon timTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return null;
        }
        return danhSach[vt];
    }

    private int timViTri(String khoa) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getKhoa().equals(khoa)) {
                return i;
            }
        }
        return -1;
    }

    // ===== d. SỬA THEO MÃ =====
    // Sửa cơ bản: chọn thuộc tính bằng switch-case rồi gán giá trị mới.
    // Chỉ đổi dữ liệu của dòng này (thành tiền tự tính lại = số lượng x giá bán).
    @Override
    public void suaTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            System.out.println("Khong tim thay dong chi tiet!");
            return;
        }
        ChiTietHoaDon ct = danhSach[vt];
        int opt;
        do {
            System.out.println("\n--- SUA CHI TIET HOA DON ---");
            ct.xuat();
            System.out.println("1. Ma hoa don");
            System.out.println("2. Ma san pham");
            System.out.println("3. So luong ban");
            System.out.println("4. Gia ban");
            System.out.println("5. Thanh tien");
            System.out.println("0. Xong");
            System.out.print("Chon thong tin can sua: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1: {
                    System.out.print("Ma hoa don moi: ");
                    String maMoi = sc.nextLine();
                    if (timViTri(taoKhoa(maMoi, ct.getMaSP())) != -1) {
                        System.out.println("Da co dong nay roi!");
                    } else {
                        ct.setMaHD(maMoi);
                    }
                    break;
                }
                case 2: {
                    System.out.print("Ma san pham moi: ");
                    String maMoi = sc.nextLine();
                    if (timViTri(taoKhoa(ct.getMaHD(), maMoi)) != -1) {
                        System.out.println("Da co dong nay roi!");
                    } else {
                        ct.setMaSP(maMoi);
                    }
                    break;
                }
                case 3:
                    System.out.print("So luong moi: ");
                    ct.setSoLuong(sc.nextInt());
                    sc.nextLine();
                    break;
                case 4:
                    System.out.print("Gia ban moi: ");
                    ct.setGiaBan(sc.nextDouble());
                    sc.nextLine();
                    break;
                case 5:
                    System.out.print("Thanh tien moi: ");
                    ct.setGiaBan(sc.nextDouble() / ct.getSoLuong()); // giữ thành tiền = số lượng x giá bán
                    sc.nextLine();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (opt != 0);
        System.out.println("Da sua xong!");
    }

    // ===== e. XÓA THEO MÃ =====
    // Xóa cơ bản: bỏ dòng khỏi mảng rồi dồn các dòng sau lên.
    @Override
    public boolean xoaTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return false;
        }
        for (int i = vt; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1];
        }
        danhSach[soLuong - 1] = null;
        soLuong--;
        return true;
    }

    // ===== f. TÌM KIẾM NHIỀU KHÓA =====
    @Override
    public void timKiem() {
        System.out.println("1. Theo ma hoa don + ma san pham (chinh xac)");
        System.out.println("2. Theo ten san pham (gan dung)");
        System.out.println("3. Theo khoang thanh tien");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        int dem = 0;
        switch (opt) {
            case 1: {
                System.out.print("Ma hoa don: ");
                String maHD = sc.nextLine();
                System.out.print("Ma san pham: ");
                ChiTietHoaDon ct = timTheoMa(taoKhoa(maHD, sc.nextLine()));
                if (ct != null) {
                    ct.xuat();
                    dem = 1;
                }
                break;
            }
            case 2: {
                System.out.print("Nhap tu khoa (ten san pham): ");
                String tuKhoa = sc.nextLine().toLowerCase();
                for (int i = 0; i < soLuong; i++) {
                    VanPhongPham sp = QLDSSP.dssp.timTheoMa(danhSach[i].getMaSP());
                    if (sp != null && sp.getTenSP().toLowerCase().contains(tuKhoa)) {
                        danhSach[i].xuat();
                        dem++;
                    }
                }
                break;
            }
            case 3: {
                System.out.print("Thanh tien tu: ");
                double min = sc.nextDouble();
                sc.nextLine();
                System.out.print("Den: ");
                double max = sc.nextDouble();
                sc.nextLine();
                for (int i = 0; i < soLuong; i++) {
                    if (danhSach[i].getThanhTien() >= min && danhSach[i].getThanhTien() <= max) {
                        danhSach[i].xuat();
                        dem++;
                    }
                }
                break;
            }
            default:
                System.out.println("Lua chon khong hop le!");
                return;
        }
        if (dem == 0) {
            System.out.println("Khong tim thay ket qua nao!");
        }
    }

    // ===== g. THỐNG KÊ NHIỀU KHÓA CHI TIẾT HÓA ĐƠN (in ra màn hình) =====
    // Menu thống kê: mỗi lựa chọn là một khóa thống kê khác nhau, gọi hàm riêng bên
    // dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach chi tiet hoa don rong!");
            return;
        }
        System.out.println("1. Theo tung san pham (so luong ban, doanh thu)");
        System.out.println("2. Theo loai san pham (But / Tap viet)");
        System.out.println("3. San pham ban chay nhat");
        System.out.println("0. Thoat");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeTheoTungSP();
                break;
            case 2:
                thongKeTheoLoaiSP();
                break;
            case 3:
                thongKeBanChayNhat();
                break;
            default:
                break;
        }
    }

    // 3. Sản phẩm bán được nhiều nhất (tính theo tổng số lượng trên mọi hóa đơn)
    public void thongKeBanChayNhat() {
        if (soLuong == 0) {
            System.out.println("Chua co hoa don nao!");
            return;
        }
        String maBanChay = "";
        int slMax = 0;
        for (int i = 0; i < soLuong; i++) {
            int tong = 0;
            for (int j = 0; j < soLuong; j++) {
                if (danhSach[j].getMaSP().equals(danhSach[i].getMaSP())) {
                    tong += danhSach[j].getSoLuong();
                }
            }
            if (tong > slMax) {
                slMax = tong;
                maBanChay = danhSach[i].getMaSP();
            }
        }
        System.out.println("San pham ban chay nhat: " + maBanChay + " (da ban " + slMax + ")");
    }

    // ===== HÀM DÙNG CHUNG =====
    // Sản phẩm đã từng được bán chưa? (dùng để chặn xóa sản phẩm)
    public boolean coSanPham(String maSP) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaSP().equals(maSP)) {
                return true;
            }
        }
        return false;
    }

    // ===== Dùng khi XÓA hóa đơn =====
    // Trả hàng về kho: cộng lại số lượng đã bán của mọi dòng thuộc hóa đơn này
    public void hoanKhoTheoMaHD(String maHD) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaHD().equals(maHD)) {
                QLDSSP.dssp.nhapKho(danhSach[i].getMaSP(), danhSach[i].getSoLuong());
            }
        }
    }

    // Xóa mọi dòng chi tiết của một hóa đơn (dồn các dòng còn lại lên)
    public void xoaTheoMaHD(String maHD) {
        int j = 0;
        for (int i = 0; i < soLuong; i++) {
            if (!danhSach[i].getMaHD().equals(maHD)) {
                danhSach[j] = danhSach[i];
                j++;
            }
        }
        for (int i = j; i < soLuong; i++) {
            danhSach[i] = null;
        }
        soLuong = j;
    }

    // ===== Dùng khi ĐỔI MÃ hóa đơn / sản phẩm: đổi luôn mã trong mọi dòng chi tiết
    // =====
    public void doiMaHD(String maCu, String maMoi) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaHD().equals(maCu)) {
                danhSach[i].setMaHD(maMoi);
            }
        }
    }

    public void doiMaSP(String maCu, String maMoi) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaSP().equals(maCu)) {
                danhSach[i].setMaSP(maMoi);
            }
        }
    }

    // 1. Theo từng sản phẩm: nhóm các dòng chi tiết cùng mã SP, tính số lượng đã
    // bán và doanh thu
    public void thongKeTheoTungSP() {
        System.out.println("Ma SP | Ten | So luong ban | Doanh thu");
        System.out.println("--------------------------------------");
        int tongSL = 0;
        double tongTien = 0;
        for (int i = 0; i < soLuong; i++) {
            String ma = danhSach[i].getMaSP();
            boolean daXet = false; // sản phẩm này đã được thống kê ở dòng phía trước chưa?
            for (int j = 0; j < i; j++) {
                if (danhSach[j].getMaSP().equals(ma)) {
                    daXet = true;
                }
            }
            if (daXet) {
                continue;
            }
            int sl = 0;
            double tien = 0;
            for (int k = 0; k < soLuong; k++) {
                if (danhSach[k].getMaSP().equals(ma)) {
                    sl += danhSach[k].getSoLuong();
                    tien += danhSach[k].getThanhTien();
                }
            }
            String ten = "";
            VanPhongPham sp = QLDSSP.dssp.timTheoMa(ma);
            if (sp != null) {
                ten = sp.getTenSP();
            }
            System.out.println(ma + " | " + ten + " | " + sl + " | " + (long) tien);
            tongSL += sl;
            tongTien += tien;
        }
        System.out.println("TONG | | " + tongSL + " | " + (long) tongTien);
    }

    // 2. Theo loại sản phẩm: tra loại của từng dòng qua danh sách sản phẩm dùng
    // chung QLDSSP.dssp
    public void thongKeTheoLoaiSP() {
        int slBut = 0;
        int slTap = 0;
        double tienBut = 0;
        double tienTap = 0;
        for (int i = 0; i < soLuong; i++) {
            VanPhongPham sp = QLDSSP.dssp.timTheoMa(danhSach[i].getMaSP());
            if (sp instanceof But) {
                slBut += danhSach[i].getSoLuong();
                tienBut += danhSach[i].getThanhTien();
            } else if (sp instanceof TapViet) {
                slTap += danhSach[i].getSoLuong();
                tienTap += danhSach[i].getThanhTien();
            }
        }
        System.out.println("Loai | So luong ban | Doanh thu");
        System.out.println("-------------------------------");
        System.out.println("But | " + slBut + " | " + (long) tienBut);
        System.out.println("Tap viet | " + slTap + " | " + (long) tienTap);
        System.out.println("TONG | " + (slBut + slTap) + " | " + (long) (tienBut + tienTap));
    }

    // ===== ĐỌC / GHI FILE TXT (interface IFile) =====
    // Mỗi đối tượng 1 dòng, các thuộc tính ngăn cách nhau bằng dấu ;
    @Override
    public void docFile(String tenFile) {
        try {
            Scanner fs = new Scanner(new File(tenFile));
            while (fs.hasNextLine()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.fromFileString(fs.nextLine());
                themDong(ct); // đọc file: chỉ nạp dữ liệu, không trừ tồn kho / không cộng tiền lần nữa
            }
            fs.close();
        } catch (Exception e) {
            System.out.println("Khong doc duoc file ");
        }
    }

    @Override
    public void ghiFile(String tenFile) {
        try {
            PrintWriter pw = new PrintWriter(tenFile);
            for (int i = 0; i < soLuong; i++) {
                pw.println(danhSach[i].toFileString());
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("Khong ghi duoc file " + tenFile);
        }
    }
}
