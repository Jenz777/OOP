import java.io.*;
import java.util.*;

// Danh sách TẤT CẢ các dòng chi tiết của mọi phiếu nhập (đủ chức năng IQuanLy + IFile) - làm giống DSChiTietHoaDon.
// Khóa của một dòng là cặp (maPNH, maSP) -> dùng chuỗi "maPNH|maSP" làm "mã" cho các hàm của IQuanLy.
// Thêm / sửa / xóa ở đây chỉ làm cơ bản trên chính danh sách này (không đụng tới tồn kho, tổng tiền phiếu nhập).
// Phần nghiệp vụ nằm ở DSPhieuNhapHang.taoPhieuNhap (lập phiếu nhập).
public class DSChiTietPhieuNhap implements IQuanLy<ChiTietPhieuNhap>, IFile {
    Scanner sc = new Scanner(System.in);
    private ChiTietPhieuNhap[] danhSach;
    private int soLuong;

    public DSChiTietPhieuNhap() {
        danhSach = new ChiTietPhieuNhap[10];
        soLuong = 0;
    }

    public int getSoLuong() {
        return soLuong;
    }

    // static: ghép mã phiếu nhập + mã sản phẩm thành khóa của một dòng
    public static String taoKhoa(String maPNH, String maSP) {
        return maPNH + "|" + maSP;
    }

    // Thêm THÔ vào cuối mảng (không kiểm tra, không đụng tồn kho / tổng tiền).
    // Dùng khi đọc file và khi DSPhieuNhapHang.taoPhieuNhap đang lập phiếu (lúc đó
    // tồn kho đã cộng sẵn).
    public void themDong(ChiTietPhieuNhap ct) {
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = ct;
        soLuong++;
    }

    // ===== c. THÊM =====
    // Thêm cơ bản 1 dòng: chỉ kiểm tra số hợp lệ và không trùng (mã phiếu nhập + mã
    // sản phẩm).
    @Override
    public void them(ChiTietPhieuNhap ct) {
        if (ct == null) {
            return;
        }
        if (ct.getSoLuongNhap() <= 0 || ct.getDonGiaNhap() <= 0) {
            System.out.println("So luong va don gia nhap phai > 0!");
            return;
        }
        if (timViTri(ct.getKhoa()) != -1) {
            System.out.println("Dong " + ct.getKhoa() + " da ton tai!");
            return;
        }
        themDong(ct);
    }

    // Nhập 1 dòng chi tiết từ bàn phím
    public ChiTietPhieuNhap taoChiTiet() {
        ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
        System.out.print("Ma phieu nhap: ");
        ct.setMaPNH(sc.nextLine());
        System.out.print("Ma san pham: ");
        ct.setMaSP(sc.nextLine());
        System.out.print("Don gia nhap: ");
        ct.setDonGiaNhap(sc.nextDouble());
        sc.nextLine();
        System.out.print("So luong nhap: ");
        ct.setSoLuongNhap(sc.nextInt());
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
            System.out.println("Chua co chi tiet phieu nhap nao!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat();
        }
    }

    // In các dòng chi tiết của một phiếu nhập, trả về số dòng
    public int xuatTheoMaPNH(String maPNH) {
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaPNH().equals(maPNH)) {
                danhSach[i].xuat();
                dem++;
            }
        }
        return dem;
    }

    // ===== TÌM THEO MÃ (mã = "maPNH|maSP") =====
    @Override
    public ChiTietPhieuNhap timTheoMa(String ma) {
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
    // Chỉ đổi dữ liệu của dòng này (thành tiền tự tính lại = số lượng nhập x đơn
    // giá nhập).
    @Override
    public void suaTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            System.out.println("Khong tim thay dong chi tiet!");
            return;
        }
        ChiTietPhieuNhap ct = danhSach[vt];
        int opt;
        do {
            System.out.println("\n--- SUA CHI TIET PHIEU NHAP ---");
            ct.xuat();
            System.out.println("1. Ma phieu nhap");
            System.out.println("2. Ma san pham");
            System.out.println("3. So luong nhap");
            System.out.println("4. Don gia nhap");
            System.out.println("5. Thanh tien");
            System.out.println("0. Xong");
            System.out.print("Chon thong tin can sua: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1: {
                    System.out.print("Ma phieu nhap moi: ");
                    String maMoi = sc.nextLine();
                    if (timViTri(taoKhoa(maMoi, ct.getMaSP())) != -1) {
                        System.out.println("Da co dong nay roi!");
                    } else {
                        ct.setMaPNH(maMoi);
                    }
                    break;
                }
                case 2: {
                    System.out.print("Ma san pham moi: ");
                    String maMoi = sc.nextLine();
                    if (timViTri(taoKhoa(ct.getMaPNH(), maMoi)) != -1) {
                        System.out.println("Da co dong nay roi!");
                    } else {
                        ct.setMaSP(maMoi);
                    }
                    break;
                }
                case 3:
                    System.out.print("So luong nhap moi: ");
                    ct.setSoLuongNhap(sc.nextInt());
                    sc.nextLine();
                    break;
                case 4:
                    System.out.print("Don gia nhap moi: ");
                    ct.setDonGiaNhap(sc.nextDouble());
                    sc.nextLine();
                    break;
                case 5:
                    System.out.print("Thanh tien moi: ");
                    ct.setDonGiaNhap(sc.nextDouble() / ct.getSoLuongNhap()); // giữ thành tiền = số lượng x đơn giá
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
        System.out.println("1. Theo ma phieu nhap + ma san pham (chinh xac)");
        System.out.println("2. Theo ten san pham (gan dung)");
        System.out.println("3. Theo khoang thanh tien");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        int dem = 0;
        switch (opt) {
            case 1: {
                System.out.print("Ma phieu nhap: ");
                String maPNH = sc.nextLine();
                System.out.print("Ma san pham: ");
                ChiTietPhieuNhap ct = timTheoMa(taoKhoa(maPNH, sc.nextLine()));
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

    // ===== g. THỐNG KÊ NHIỀU KHÓA CHI TIẾT PHIẾU NHẬP (in ra màn hình) =====
    // Menu thống kê: mỗi lựa chọn là một khóa thống kê khác nhau, gọi hàm riêng bên
    // dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach chi tiet phieu nhap rong!");
            return;
        }
        System.out.println("1. Theo tung san pham (so luong nhap, tien nhap)");
        System.out.println("2. Theo loai san pham (But / Tap viet)");
        System.out.println("3. San pham nhap nhieu nhat");
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
                thongKeNhapNhieuNhat();
                break;
            default:
                break;
        }
    }

    // 3. Sản phẩm được nhập nhiều nhất (tính theo tổng số lượng trên mọi phiếu
    // nhập)
    public void thongKeNhapNhieuNhat() {
        if (soLuong == 0) {
            System.out.println("Chua co phieu nhap nao!");
            return;
        }
        String maNhapNhieu = "";
        int slMax = 0;
        for (int i = 0; i < soLuong; i++) {
            int tong = 0;
            for (int j = 0; j < soLuong; j++) {
                if (danhSach[j].getMaSP().equals(danhSach[i].getMaSP())) {
                    tong += danhSach[j].getSoLuongNhap();
                }
            }
            if (tong > slMax) {
                slMax = tong;
                maNhapNhieu = danhSach[i].getMaSP();
            }
        }
        System.out.println("San pham nhap nhieu nhat: " + maNhapNhieu + " (da nhap " + slMax + ")");
    }

    // ===== HÀM DÙNG CHUNG =====
    // Sản phẩm đã từng được nhập chưa? (dùng để chặn xóa sản phẩm)
    public boolean coSanPham(String maSP) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaSP().equals(maSP)) {
                return true;
            }
        }
        return false;
    }

    // ===== Dùng khi XÓA phiếu nhập =====
    // Xóa phiếu nhập = trừ lại hàng đã nhập. Chỉ làm được khi mỗi sản phẩm còn tồn
    // >= số đã nhập
    // qua phiếu này (nếu hàng đã bán bớt thì tồn không đủ để trừ).
    public boolean coTheHoanTac(String maPNH) {
        for (int i = 0; i < soLuong; i++) {
            if (!danhSach[i].getMaPNH().equals(maPNH)) {
                continue;
            }
            String maSP = danhSach[i].getMaSP();
            int tongNhap = 0; // một sản phẩm có thể nằm ở nhiều dòng của cùng phiếu
            for (int j = 0; j < soLuong; j++) {
                if (danhSach[j].getMaPNH().equals(maPNH) && danhSach[j].getMaSP().equals(maSP)) {
                    tongNhap += danhSach[j].getSoLuongNhap();
                }
            }
            VanPhongPham sp = QLDSSP.dssp.timTheoMa(maSP);
            if (sp == null || sp.getSoLuongTon() < tongNhap) {
                return false;
            }
        }
        return true;
    }

    // Trừ tồn kho theo các dòng của phiếu (gọi sau khi coTheHoanTac trả về true)
    public void hoanTacKho(String maPNH) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaPNH().equals(maPNH)) {
                QLDSSP.dssp.banHang(danhSach[i].getMaSP(), danhSach[i].getSoLuongNhap());
            }
        }
    }

    // Xóa mọi dòng chi tiết của một phiếu nhập (dồn các dòng còn lại lên)
    public void xoaTheoMaPNH(String maPNH) {
        int j = 0;
        for (int i = 0; i < soLuong; i++) {
            if (!danhSach[i].getMaPNH().equals(maPNH)) {
                danhSach[j] = danhSach[i];
                j++;
            }
        }
        for (int i = j; i < soLuong; i++) {
            danhSach[i] = null;
        }
        soLuong = j;
    }

    // ===== Dùng khi ĐỔI MÃ phiếu nhập / sản phẩm: đổi luôn mã trong mọi dòng chi
    // tiết =====
    public void doiMaPNH(String maCu, String maMoi) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaPNH().equals(maCu)) {
                danhSach[i].setMaPNH(maMoi);
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
    // nhập và tiền nhập
    public void thongKeTheoTungSP() {
        System.out.println("Ma SP | Ten | So luong nhap | Tien nhap");
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
                    sl += danhSach[k].getSoLuongNhap();
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
                slBut += danhSach[i].getSoLuongNhap();
                tienBut += danhSach[i].getThanhTien();
            } else if (sp instanceof TapViet) {
                slTap += danhSach[i].getSoLuongNhap();
                tienTap += danhSach[i].getThanhTien();
            }
        }
        System.out.println("Loai | So luong nhap | Tien nhap");
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
                ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
                ct.fromFileString(fs.nextLine());
                themDong(ct); // đọc file: chỉ nạp dữ liệu, không cộng tồn kho lần nữa
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
