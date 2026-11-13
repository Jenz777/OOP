public class TapViet extends VanPhongPham {
    private int soTrang;
    private String loaiGiay;

    public TapViet() {
        super();
        this.soTrang = 0;
        this.loaiGiay = "";
    }

    public TapViet(TapViet a) {
        super(a);
        this.soTrang = a.soTrang;
        this.loaiGiay = a.loaiGiay;
    }

    public int getSoTrang() {
        return this.soTrang;
    }

    public void setSoTrang(int a) {
        this.soTrang = a;
    }

    public String getLoaiGiay() {
        return this.loaiGiay;
    }

    public void setLoaiGiay(String a) {
        this.loaiGiay = a;
    }

    @Override
    public void nhap() {
        super.nhap();
        System.out.print("So trang: ");
        soTrang = sc.nextInt();
        sc.nextLine();
        System.out.print("Loai giay: ");
        loaiGiay = sc.nextLine();
    }

    @Override
    public String[] tenThuocTinhRieng() {
        return new String[] { "So trang", "Loai giay" };
    }

    @Override
    public void suaThuocTinhRieng(int viTri) {
        switch (viTri) {
            case 0: {
                System.out.print("So trang moi (> 0): ");
                int st = sc.nextInt();
                sc.nextLine();
                if (st <= 0) {
                    System.out.println("So trang phai > 0, giu nguyen!");
                } else {
                    soTrang = st;
                }
                break;
            }
            case 1:
                System.out.print("Loai giay moi: ");
                loaiGiay = sc.nextLine();
                break;
            default:
                System.out.println("Lua chon khong hop le!");
        }
    }

    @Override
    public double tinhGiaBan() {
        // làm tròn đến đồng để tránh sai số số thực
        return Math.round(getDonGia() * 1.15); // giá bán = giá gốc + 15%
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" | So trang: " + soTrang + " | giay:" + loaiGiay + " | gia ban: " + tinhGiaBan());
    }

    @Override
    public String toFileString() {
        return "TAP;" + thuocTinhChung() + ";" + soTrang + ";" + loaiGiay;
    }

    @Override
    public void fromFileString(String dong) {
        String[] p = dong.split(";", -1);
        docPhanChung(p); // 5 thuộc tính chung
        soTrang = Integer.parseInt(p[6]);
        loaiGiay = p[7];
    }
}
