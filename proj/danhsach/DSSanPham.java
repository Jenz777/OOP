package danhsach;

import interfaces.IFile;
import interfaces.IQuanLy;
import model.But;
import model.TapViet;
import model.VanPhongPham;

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
    public VanPhongPham taoSanPham() {
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
        if (soLuong > 0) {
            System.out.println("Dang co " + soLuong + " san pham, neu nhap moi se xoa het");
            System.out.println("Ban co muon xoa? Nhap 1 xac nhan, 2 de huy");
            int t = sc.nextInt();
            sc.nextLine();
            if (t != 1) {
                System.out.println("Da huy!");
                return;
            }
        }
        danhSach = new VanPhongPham[10];
        soLuong = 0;
        System.out.print("Ban muon nhap bao nhieu san pham ban dau? n = ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("--- Nhap san pham thu " + (i + 1) + " ---");
            them(taoSanPham());
        }

    }

    @Override
    public void themK() {
        System.out.print("Nhap k san pham muon them: ");
        int k = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < k; i++) {
            System.out.println("--- San pham them thu " + (i + 1) + " ---");
            them(taoSanPham());
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

    // Sửa cơ bản: chọn thuộc tính rồi gán giá trị mới (không sửa mã vì mã là khóa).
    // Thuộc tính riêng của từng loại sản phẩm do lớp con tự lo (đa hình).
    @Override
    public void suaTheoMa(String ma) {
        VanPhongPham sp = timTheoMa(ma);
        if (sp == null) {
            System.out.println("San pham khong ton tai");
            return;
        }
        System.out.println("Thong tin san pham");
        sp.xuat();
        String[] rieng = sp.tenThuocTinhRieng();
        System.out.println("1. Ten san pham");
        System.out.println("2. So luong ton");
        System.out.println("3. Don gia");
        System.out.println("4. Don vi tinh");
        System.out.println("5. " + rieng[0]);
        System.out.println("6. " + rieng[1]);
        System.out.println("Lua chon cua ban:");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                System.out.print("Ten san pham moi: ");
                sp.setTenSP(sc.nextLine());
                break;
            case 2:
                System.out.print("So luong ton moi: ");
                sp.setSoLuongTon(sc.nextInt());
                sc.nextLine();
                break;
            case 3:
                System.out.print("Don gia moi: ");
                sp.setDonGia(sc.nextDouble());
                sc.nextLine();
                break;
            case 4:
                System.out.print("Don vi tinh moi: ");
                sp.setDonViTinh(sc.nextLine());
                break;
            default:
                if (opt > 4 && opt < 7) {
                    sp.suaThuocTinhRieng(opt);
                } else {
                    System.out.println("Lua chon k hop le");
                }

        }
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
                System.out.println("Lua chon khong hop le!");
                return;
        }
        if (kq.getSoLuong() == 0) {
            System.out.println("Khong tim thay!");
        } else {
            kq.xuat();
        }
    }

    // ===== g. THỐNG KÊ NHIỀU KHÓA SẢN PHẨM (in ra màn hình) =====
    // Menu thống kê sản phẩm: mỗi lựa chọn là một cách thống kê (một khóa) khác
    // nhau,
    // mỗi cách được viết thành một hàm riêng bên dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach san pham rong!");
            return;
        }
        System.out.println("===== THONG KE SAN PHAM =====");
        System.out.println("1. Ton kho theo loai san pham (But / Tap viet): so san pham, tong ton, gia tri von");
        System.out.println("2. Liet ke san pham co gia ban nam trong khoang (tu ... den ...)");
        System.out.println("0. Quay lai");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeTonKhoTheoLoaiSanPham();
                break;
            case 2:
                thongKeSanPhamTheoKhoangGiaBan();
                break;
            case 0:
                break;
            default:
                System.out.println("Lua chon khong hop le!");
                break;
        }
    }

    // 1. TỒN KHO THEO LOẠI SẢN PHẨM (Bút / Tập viết).
    // Mỗi loại in ra: số sản phẩm, tổng số lượng tồn kho, giá trị vốn của hàng tồn
    // (giá trị vốn = giá vốn donGia x số lượng tồn). Dòng cuối là tổng của cả 2
    // loại.
    public void thongKeTonKhoTheoLoaiSanPham() {
        int soBut = 0;
        int soTap = 0;
        int tonBut = 0;
        int tonTap = 0;
        double vonBut = 0;
        double vonTap = 0;
        for (int i = 0; i < soLuong; i++) {
            double von = danhSach[i].getDonGia() * danhSach[i].getSoLuongTon();
            if (danhSach[i] instanceof But) {
                soBut++;
                tonBut += danhSach[i].getSoLuongTon();
                vonBut += von;
            } else if (danhSach[i] instanceof TapViet) {
                soTap++;
                tonTap += danhSach[i].getSoLuongTon();
                vonTap += von;
            }
        }
        System.out.println("Loai | So san pham | Tong ton | Gia tri von");
        System.out.println("--------------------------------------------------------------");
        System.out.println("But | " + soBut + " | " + tonBut + " | " + (long) vonBut);
        System.out.println("Tap viet | " + soTap + " | " + tonTap + " | " + (long) vonTap);
        System.out.println("TONG | " + soLuong + " | " + (tonBut + tonTap) + " | " + (long) (vonBut + vonTap));
    }

    // 2. SẢN PHẨM THEO KHOẢNG GIÁ BÁN.
    // Nhập khoảng giá bán [min, max], liệt kê các sản phẩm có giá bán nằm trong
    // khoảng
    // đó (giá bán lấy từ tinhGiaBan() - đa hình), rồi in tổng hợp: số sản phẩm,
    // tổng tồn, tổng giá trị hàng tồn TÍNH THEO GIÁ BÁN (giá bán x số lượng tồn).
    public void thongKeSanPhamTheoKhoangGiaBan() {
        System.out.print("Gia ban tu (VND): ");
        double min = sc.nextDouble();
        sc.nextLine();
        System.out.print("Gia ban den (VND): ");
        double max = sc.nextDouble();
        sc.nextLine();
        if (min > max) {
            System.out.println("Khoang gia khong hop le (gia tu phai nho hon hoac bang gia den)!");
            return;
        }
        System.out.println("SAN PHAM CO GIA BAN TU " + (long) min + " DEN " + (long) max + " VND");
        int dem = 0;
        int tongTon = 0;
        double giaTriTheoGiaBan = 0;
        for (int i = 0; i < soLuong; i++) {
            double gia = danhSach[i].tinhGiaBan();
            if (gia >= min && gia <= max) {
                danhSach[i].xuat();
                dem++;
                tongTon += danhSach[i].getSoLuongTon();
                giaTriTheoGiaBan += gia * danhSach[i].getSoLuongTon();
            }
        }
        if (dem == 0) {
            System.out.println("Khong co san pham nao co gia ban trong khoang nay.");
            return;
        }
        System.out.println("Tong ket: " + dem + " san pham | tong so luong ton " + tongTon
                + " | tong gia tri ton tinh theo gia ban " + (long) giaTriTheoGiaBan + " VND");
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
                    sp.fromFileString(dong);
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
