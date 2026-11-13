public class But extends VanPhongPham {
    private String mauSac;
    private String loaiBut;

    public But() {
        super();
        this.mauSac = "";
        this.loaiBut = "";
    }

    public But(But a) {
        super(a);
        this.mauSac = a.mauSac;
        this.loaiBut = a.loaiBut;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String a) {
        this.mauSac = a;
    }

    public String getLoaiBut() {
        return loaiBut;
    }

    public void setLoaiBut(String a) {
        this.loaiBut = a;
    }

    @Override
    public void nhap() {
        super.nhap();
        System.out.print("Mau sac: ");
        mauSac = sc.nextLine();
        System.out.print("Loai but: ");
        loaiBut = sc.nextLine();
    }

    @Override
    public String[] tenThuocTinhRieng() {
        return new String[] { "Mau sac", "Loai but" };
    }

    @Override
    public void suaThuocTinhRieng(int viTri) {
        switch (viTri) {
            case 0:
                System.out.print("Mau sac moi: ");
                mauSac = sc.nextLine();
                break;
            case 1:
                System.out.print("Loai but moi: ");
                loaiBut = sc.nextLine();
                break;
            default:
                System.out.println("Lua chon khong hop le!");
        }
    }

    @Override
    public double tinhGiaBan() {
        // làm tròn đến đồng để tránh sai số số thực (vd 6000*1.1 = 6600.000000000001)
        return Math.round(this.getDonGia() * 1.1); // giá bán = giá gốc + 10%
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" | mau: " + mauSac + " | loai: " + loaiBut + " | gia ban: " + tinhGiaBan());
    }

    @Override
    public String toFileString() {
        return "BUT;" + thuocTinhChung() + ";" + mauSac + ";" + loaiBut;
    }

    @Override
    public void fromFileString(String dong) {
        String[] p = dong.split(";", -1);
        docPhanChung(p); // 5 thuộc tính chung
        mauSac = p[6];
        loaiBut = p[7];
    }
}
