import java.io.*;
import java.util.*;

public class DSSanPham implements IQuanLy<VanPhongPham>, IFile {
    Scanner sc = new Scanner(System.in);
    private VanPhongPham[] danhSach; // chứa lẫn But và TapViet (đa hình)
    private int soLuong;

    public DSSanPham() {
        danhSach = new VanPhongPham[10];
        soLuong = 0;
    }

    public int getSoLuong() {
        return soLuong;
    }

    // Chỉ hàm này được tăng soLuong. Có kiểm tra trùng mã.
    @Override
    public void them(VanPhongPham sp) {
        if (timTheoMa(sp.getMaSP()) != null) {
            System.out.println("Ma san pham " + sp.getMaSP() + " da ton tai, khong them!");
            return;
        }
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = sp;
        soLuong++;
    }

    // Hỏi loại rồi tạo đúng đối tượng con: ĐA HÌNH (biến kiểu cha, đối tượng kiểu
    // con)
    public VanPhongPham themSanPham() {
        System.out.print("Loai (1. But, 2. Tap viet): ");
        int loai = sc.nextInt();
        sc.nextLine();
        while (loai != 1 && loai != 2) {
            System.out.println("Chi duoc nhap 1 hoac 2!");
            System.out.print("Loai (1. But, 2. Tap viet): ");
            loai = sc.nextInt();
            sc.nextLine();
        }
        VanPhongPham sp;
        if (loai == 1) {
            sp = new But();
        } else {
            sp = new TapViet();
        }
        sp.nhap(); // chạy nhap() của But hoặc TapViet tùy sp đang là gì(đa hình)
        while (timTheoMa(sp.getMaSP()) != null) {
            System.out.println("Ma san pham da ton tai, nhap lai!");
            sp.nhap();
        }
        return sp;
    }

    @Override
    public void nhapN() {
        System.out.print("Nhap n san pham dau tien: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("--- San pham thu " + (i + 1) + " ---");
            them(themSanPham());
        }
    }

    @Override
    public void themK() {
        System.out.print("Nhap k san pham muon them: ");
        int k = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < k; i++) {
            System.out.println("--- San pham them thu " + (i + 1) + " ---");
            them(themSanPham());
        }
    }

    @Override
    public void xuat() {
        if (soLuong == 0) {
            System.out.println("Danh sach san pham rong!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat(); // ĐA HÌNH: But in kiểu But, TapViet in kiểu TapViet
        }
    }

    @Override
    public VanPhongPham timTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return null;
        }
        return danhSach[vt];
    }

    private int timViTri(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaSP().equals(ma)) {
                return i;
            }
        }
        return -1;
    }

    // Sửa theo mã: chọn thuộc tính cần sửa bằng switch-case, sửa được TẤT CẢ thuộc
    // tính.
    // 5 thuộc tính chung (mã, tên, tồn, đơn giá, đơn vị tính) dùng switch trực
    // tiếp;
    // thuộc tính riêng của But / TapViet do chính lớp con tự liệt kê và tự sửa (ĐA
    // HÌNH),
    // nên ở đây không cần instanceof hay ép kiểu.
    // Riêng sửa mã: mã mới không được trống / trùng; các dòng chi tiết hóa đơn /
    // phiếu nhập tự đổi theo mã mới.
    @Override
    public void suaTheoMa(String ma) {
        VanPhongPham sp = timTheoMa(ma);
        if (sp == null) {
            System.out.println("Khong tim thay!");
            return;
        }
        int opt;
        do {
            String[] rieng = sp.tenThuocTinhRieng(); // đa hình: mỗi loại trả về danh sách khác nhau
            System.out.println("\n--- SUA SAN PHAM ---");
            sp.xuat();
            System.out.println("1. Ma san pham");
            System.out.println("2. Ten san pham");
            System.out.println("3. So luong ton");
            System.out.println("4. Don gia (gia von)");
            System.out.println("5. Don vi tinh");
            for (int i = 0; i < rieng.length; i++) {
                System.out.println((6 + i) + ". " + rieng[i]);
            }
            System.out.println("0. Xong");
            System.out.print("Chon thong tin can sua: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1: {
                    System.out.print("Ma san pham moi: ");
                    String maMoi = sc.nextLine();
                    if (maMoi.isEmpty()) {
                        System.out.println("Ma khong duoc de trong!");
                    } else if (maMoi.equals(sp.getMaSP())) {
                        System.out.println("Ma moi giong ma cu, khong doi.");
                    } else if (timTheoMa(maMoi) != null) {
                        System.out.println("Ma san pham da ton tai!");
                    } else {
                        QLDSCTHD.dscthd.doiMaSP(sp.getMaSP(), maMoi); // đổi luôn mã trong chi tiết hóa đơn
                        QLDSCTPNH.dsctpnh.doiMaSP(sp.getMaSP(), maMoi); // và chi tiết phiếu nhập
                        sp.setMaSP(maMoi);
                    }
                    break;
                }
                case 2:
                    System.out.print("Ten san pham moi: ");
                    sp.setTenSP(sc.nextLine());
                    break;
                case 3: {
                    System.out.print("So luong ton moi (>= 0): ");
                    int sl = sc.nextInt();
                    sc.nextLine();
                    if (sl < 0) {
                        System.out.println("So luong ton khong duoc am, giu nguyen!");
                    } else {
                        sp.setSoLuongTon(sl);
                    }
                    break;
                }
                case 4: {
                    System.out.print("Don gia (gia von) moi (>= 0): ");
                    double dg = sc.nextDouble();
                    sc.nextLine();
                    if (dg < 0) {
                        System.out.println("Don gia khong duoc am, giu nguyen gia cu!");
                    } else {
                        sp.setDonGia(dg);
                    }
                    break;
                }
                case 5:
                    System.out.print("Don vi tinh moi: ");
                    sp.setDonViTinh(sc.nextLine());
                    break;
                case 0:
                    break;
                default:
                    if (opt >= 6 && opt < 6 + rieng.length) {
                        sp.suaThuocTinhRieng(opt - 6); // đa hình: lớp con tự hỏi và tự gán
                    } else {
                        System.out.println("Lua chon khong hop le!");
                    }
            }
        } while (opt != 0);
        System.out.println("Da sua xong!");
    }

    @Override
    public boolean xoaTheoMa(String ma) {
        int idx = timViTri(ma);
        if (idx == -1) {
            return false;
        }
        for (int i = idx; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1]; // dồn các phần tử sau lên
        }
        danhSach[soLuong - 1] = null; // bỏ tham chiếu thừa ở cuối
        soLuong--;
        return true;
    }

    // ===== f. TÌM KIẾM =====
    // Các hàm tìm trả về một DSVanPhongPham mới chứa kết quả
    public DSSanPham timTheoTen(String tuKhoa) {
        DSSanPham kq = new DSSanPham();
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getTenSP().toLowerCase().contains(tuKhoa.toLowerCase())) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    public DSSanPham timTheoKhoangGia(double min, double max) {
        DSSanPham kq = new DSSanPham();
        for (int i = 0; i < soLuong; i++) {
            double g = danhSach[i].tinhGiaBan(); // ĐA HÌNH: mỗi loại tính giá riêng
            if (g >= min && g <= max) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    @Override
    public void timKiem() {
        System.out.println("1. Ma (chinh xac)  2. Ten (gan dung)");
        System.out.println("3. Khoang gia ban  0. Thoat");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        DSSanPham kq = new DSSanPham();
        switch (opt) {
            case 1: {
                System.out.print("Ma: ");
                VanPhongPham sp = timTheoMa(sc.nextLine());
                if (sp != null) {
                    kq.them(sp);
                }
                break;
            }
            case 2:
                System.out.print("Tu khoa: ");
                kq = timTheoTen(sc.nextLine());
                break;
            case 3: {
                System.out.print("Tu: ");
                double a = sc.nextDouble();
                sc.nextLine();
                System.out.print("Den: ");
                double b = sc.nextDouble();
                sc.nextLine();
                kq = timTheoKhoangGia(a, b);
                break;
            }
            default:
                return;
        }
        if (kq.getSoLuong() == 0) {
            System.out.println("Khong tim thay!");
        } else {
            kq.xuat();
        }
    }

    // ===== g. THỐNG KÊ NHIỀU KHÓA SẢN PHẨM (in ra màn hình) =====
    // Menu thống kê: mỗi lựa chọn là một khóa thống kê khác nhau, gọi hàm riêng bên
    // dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach san pham rong!");
            return;
        }
        System.out.println("1. Theo loai (But / Tap viet): so luong, ton kho, gia tri ton");
        System.out.println("2. Theo khoang gia ban (nhap gia tu - den)");
        System.out.println("0. Thoat");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeTheoLoai();
                break;
            case 2:
                thongKeTheoKhoangGia();
                break;
            default:
                break;
        }
    }

    // 1. Theo loại: số sản phẩm, tổng tồn, giá trị tồn (giá vốn), giá bán trung
    // bình
    public void thongKeTheoLoai() {
        int soBut = 0;
        int soTap = 0;
        int tonBut = 0;
        int tonTap = 0;
        double vonBut = 0;
        double vonTap = 0;
        double tongGiaBanBut = 0; // cộng giá bán để lát nữa chia ra trung bình
        double tongGiaBanTap = 0;
        for (int i = 0; i < soLuong; i++) {
            double von = danhSach[i].getDonGia() * danhSach[i].getSoLuongTon();
            if (danhSach[i] instanceof But) {
                soBut++;
                tonBut += danhSach[i].getSoLuongTon();
                vonBut += von;
                tongGiaBanBut += danhSach[i].tinhGiaBan();
            } else if (danhSach[i] instanceof TapViet) {
                soTap++;
                tonTap += danhSach[i].getSoLuongTon();
                vonTap += von;
                tongGiaBanTap += danhSach[i].tinhGiaBan();
            }
        }
        long tbBut = 0;
        long tbTap = 0;
        if (soBut > 0) {
            tbBut = (long) (tongGiaBanBut / soBut);
        }
        if (soTap > 0) {
            tbTap = (long) (tongGiaBanTap / soTap);
        }
        System.out.println("Loai | So san pham | Tong ton | Gia tri ton (von) | Gia ban TB");
        System.out.println("--------------------------------------------------------------");
        System.out.println("But | " + soBut + " | " + tonBut + " | " + (long) vonBut + " | " + tbBut);
        System.out.println("Tap viet | " + soTap + " | " + tonTap + " | " + (long) vonTap + " | " + tbTap);
        System.out.println("TONG | " + soLuong + " | " + (tonBut + tonTap) + " | " + (long) (vonBut + vonTap));
    }

    // ===== h. NGHIỆP VỤ KHÁC =====
    // Bán hàng: trừ tồn kho. false nếu không có mã, số lượng sai, hoặc không đủ
    // hàng.
    public boolean banHang(String ma, int sl) {
        VanPhongPham sp = timTheoMa(ma);
        if (sp == null || sl <= 0 || sp.getSoLuongTon() < sl) {
            return false;
        }
        sp.setSoLuongTon(sp.getSoLuongTon() - sl);
        return true;
    }

    // Nhập kho: cộng tồn kho. false nếu không có mã hoặc số lượng sai.
    public boolean nhapKho(String ma, int sl) {
        VanPhongPham sp = timTheoMa(ma);
        if (sp == null || sl <= 0) {
            return false;
        }
        sp.setSoLuongTon(sp.getSoLuongTon() + sl);
        return true;
    }

    // Sắp xếp theo giá bán tăng dần (hoán vị tham chiếu)
    public void sapXepTheoGiaBan() {
        for (int i = 0; i < soLuong - 1; i++) {
            for (int j = i + 1; j < soLuong; j++) {
                if (danhSach[i].tinhGiaBan() > danhSach[j].tinhGiaBan()) {
                    VanPhongPham tmp = danhSach[i];
                    danhSach[i] = danhSach[j];
                    danhSach[j] = tmp;
                }
            }
        }
    }

    // 3. Theo khoảng giá bán (đa hình: tinhGiaBan): liệt kê rồi tổng hợp
    public void thongKeTheoKhoangGia() {
        System.out.print("Gia ban tu: ");
        double min = sc.nextDouble();
        sc.nextLine();
        System.out.print("Den: ");
        double max = sc.nextDouble();
        sc.nextLine();
        int dem = 0;
        int tongTon = 0;
        double giaTri = 0;
        for (int i = 0; i < soLuong; i++) {
            double gia = danhSach[i].tinhGiaBan();
            if (gia >= min && gia <= max) {
                danhSach[i].xuat();
                dem++;
                tongTon += danhSach[i].getSoLuongTon();
                giaTri += gia * danhSach[i].getSoLuongTon();
            }
        }
        System.out.println(
                "Co " + dem + " san pham | tong ton " + tongTon + " | gia tri ton (theo gia ban) " + (long) giaTri);
    }

    // ===== ĐỌC / GHI FILE TXT (interface IFile) =====
    // Mỗi đối tượng 1 dòng, các thuộc tính ngăn cách nhau bằng dấu ;
    @Override
    public void docFile(String tenFile) {
        try {
            Scanner fs = new Scanner(new File(tenFile));
            while (fs.hasNextLine()) {
                String dong = fs.nextLine();
                VanPhongPham sp = null;
                if (dong.startsWith("BUT")) {
                    sp = new But();
                } else if (dong.startsWith("TAP")) {
                    sp = new TapViet();
                }
                if (sp != null) {
                    sp.fromFileString(dong); // đa hình: But hoặc TapViet tự đọc
                    them(sp);
                }
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
