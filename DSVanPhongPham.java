import java.util.*;

public class DSVanPhongPham implements IQuanLy<VanPhongPham> {
    private VanPhongPham[] danhSach; // chua lan But va TapViet (da hinh)
    private int soLuong;
    private static Scanner sc = new Scanner(System.in);

    public DSVanPhongPham() {
        danhSach = new VanPhongPham[10];
        soLuong = 0;
    }

    public int getSoLuong() {
        return soLuong;
    }

    @Override
    public void them(VanPhongPham sp) {
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong++] = sp;
    }

    // Hoi loai roi tao dung doi tuong: DA HINH
    public VanPhongPham taoSanPham() {
        System.out.print("Loai (1. But, 2. Tap viet): ");
        int loai = Integer.parseInt(sc.nextLine());
        VanPhongPham sp;
        if (loai == 1)
            sp = new But();
        else
            sp = new TapViet();
        sp.nhap();
        return sp;
    }

    @Override
    public void nhapN() {
        System.out.print("Nhap n: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.println("--- San pham thu " + (i + 1) + " ---");
            them(taoSanPham());
        }
    }

    @Override
    public void themK() {
        System.out.print("Nhap k: ");
        int k = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < k; i++) {
            System.out.println("--- San pham them thu " + (i + 1) + " ---");
            them(taoSanPham());
        }
    }

    @Override
    public void xuat() {
        for (int i = 0; i < soLuong; i++) {
            danhSach[i].xuat();
        }
    }

    @Override
    public VanPhongPham timTheoMa(String ma) {
        for (int i = 0; i < soLuong; i++)
            if (danhSach[i].getMaSP().equalsIgnoreCase(ma))
                return danhSach[i];
        return null;
    }

    private int timViTri(String ma) {
        for (int i = 0; i < soLuong; i++)
            if (danhSach[i].getMaSP().equalsIgnoreCase(ma))
                return i;
        return -1;
    }

    @Override
    public void suaTheoMa(String ma) {
        VanPhongPham sp = timTheoMa(ma);
        if (sp == null) {
            System.out.println("Khong tim thay!");
            return;
        }
        int opt;
        do {
            System.out.println("1. Ten  2. So luong ton  3. Don gia  4. Don vi tinh");
            System.out.println("5. Thuoc tinh rieng cua loai nay  0. Thoat");
            System.out.print("Chon: ");
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    System.out.print("Ten moi: ");
                    sp.setTenSP(sc.nextLine());
                    break;
                case 2:
                    System.out.print("SL moi: ");
                    sp.setSoLuongTon(Integer.parseInt(sc.nextLine()));
                    break;
                case 3:
                    System.out.print("Gia moi: ");
                    sp.setDonGia(Integer.parseInt(sc.nextLine()));
                    break;
                case 4:
                    System.out.print("DVT moi: ");
                    sp.setDonViTinh(sc.nextLine());
                    break;
                case 5:
                    if (sp instanceof But) {
                        But b = (But) sp;
                        System.out.print("Mau sac moi: ");
                        b.setMauSac(sc.nextLine());
                        System.out.print("Loai but moi: ");
                        b.setLoaiBut(sc.nextLine());
                    } else if (sp instanceof TapViet) {
                        TapViet t = (TapViet) sp;
                        System.out.print("So trang moi: ");
                        t.setSoTrang(Integer.parseInt(sc.nextLine()));
                        System.out.print("Loai giay moi: ");
                        t.setLoaiGiay(sc.nextLine());
                    }
                    break;
                default:
                    break;
            }
        } while (opt != 0);
    }

    @Override
    public boolean xoaTheoMa(String ma) {
        int idx = timViTri(ma);
        if (idx == -1)
            return false;
        for (int i = idx; i < soLuong - 1; i++)
            danhSach[i] = danhSach[i + 1];
        danhSach[--soLuong] = null;
        return true;
    }

    // ===== f. TIM KIEM =====
    public DSVanPhongPham timTheoTen(String tuKhoa) {
        DSVanPhongPham kq = new DSVanPhongPham();
        String k = tuKhoa.toLowerCase();
        for (int i = 0; i < soLuong; i++)
            if (danhSach[i].getTenSP().toLowerCase().contains(k))
                kq.them(danhSach[i]);
        return kq;
    }

    public DSVanPhongPham timTheoKhoangGia(double min, double max) {
        DSVanPhongPham kq = new DSVanPhongPham();
        for (int i = 0; i < soLuong; i++) {
            double g = danhSach[i].tinhGiaBan(); // da hinh
            if (g >= min && g <= max)
                kq.them(danhSach[i]);
        }
        return kq;
    }

    public DSVanPhongPham timTheoLoai(String maLoai) {
        DSVanPhongPham kq = new DSVanPhongPham();
        for (int i = 0; i < soLuong; i++)
            if (danhSach[i].getMaLoai().equalsIgnoreCase(maLoai))
                kq.them(danhSach[i]);
        return kq;
    }

    @Override
    public void timKiem() {
        System.out.println("1. Ma (chinh xac)  2. Ten (gan dung)");
        System.out.println("3. Khoang gia ban  4. Ma loai");
        System.out.print("Chon: ");
        int opt = Integer.parseInt(sc.nextLine());
        DSVanPhongPham kq = new DSVanPhongPham();
        switch (opt) {
            case 1:
                System.out.print("Ma: ");
                VanPhongPham sp = timTheoMa(sc.nextLine());
                if (sp != null)
                    kq.them(sp);
                break;
            case 2:
                System.out.print("Tu khoa: ");
                kq = timTheoTen(sc.nextLine());
                break;
            case 3:
                System.out.print("Tu: ");
                double a = Double.parseDouble(sc.nextLine());
                System.out.print("Den: ");
                double b = Double.parseDouble(sc.nextLine());
                kq = timTheoKhoangGia(a, b);
                break;
            case 4:
                System.out.print("Ma loai: ");
                kq = timTheoLoai(sc.nextLine());
                break;
            default:
                return;
        }
        if (kq.getSoLuong() == 0)
            System.out.println("Khong tim thay!");
        else
            kq.xuat();
    }

    // ===== g. THONG KE =====
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach rong!");
            return;
        }
        int soBut = 0, soTap = 0;
        double tongGiaTri = 0;
        VanPhongPham dat = danhSach[0], re = danhSach[0];
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i] instanceof But)
                soBut++;
            else if (danhSach[i] instanceof TapViet)
                soTap++;
            tongGiaTri += danhSach[i].tinhGiaBan() * danhSach[i].getSoLuongTon();
            if (danhSach[i].tinhGiaBan() > dat.tinhGiaBan())
                dat = danhSach[i];
            if (danhSach[i].tinhGiaBan() < re.tinhGiaBan())
                re = danhSach[i];
        }
        System.out.println("Tong so san pham: " + soLuong);
        System.out.println("So But: " + soBut + " | So Tap viet: " + soTap);
        System.out.println("Tong gia tri ton kho (theo gia ban): " + (long) tongGiaTri);
        System.out.println("San pham gia ban cao nhat: " + dat.getTenSP());
        System.out.println("San pham gia ban thap nhat: " + re.getTenSP());
    }

    public void thongKeSapHet(int nguong) {
        System.out.println("San pham ton <= " + nguong + ":");
        for (int i = 0; i < soLuong; i++)
            if (danhSach[i].getSoLuongTon() <= nguong)
                danhSach[i].xuat();
    }

    // ===== h. NGHIEP VU KHAC =====
    public boolean banHang(String ma, int sl) {
        VanPhongPham sp = timTheoMa(ma);
        if (sp == null || sp.getSoLuongTon() < sl)
            return false;
        sp.setSoLuongTon(sp.getSoLuongTon() - sl);
        return true;
    }

    public void nhapKho(String ma, int sl) {
        VanPhongPham sp = timTheoMa(ma);
        if (sp != null)
            sp.setSoLuongTon(sp.getSoLuongTon() + sl);
    }

    public void sapXepTheoGiaBan() {
        for (int i = 0; i < soLuong - 1; i++)
            for (int j = i + 1; j < soLuong; j++)
                if (danhSach[i].tinhGiaBan() > danhSach[j].tinhGiaBan()) {
                    VanPhongPham tmp = danhSach[i];
                    danhSach[i] = danhSach[j];
                    danhSach[j] = tmp;
                }
    }
}