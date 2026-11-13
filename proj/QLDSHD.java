import java.util.Scanner;

// Quản lý hóa đơn: giữ danh sách static hóa đơn (dshd) và menu.
// Danh sách chi tiết hóa đơn nằm ở QLDSCTHD.dscthd (có menu riêng).
// Việc lập / sửa / xóa hóa đơn nằm trong DSHoaDon (vì phải phối hợp sản phẩm, nhân viên, khách hàng).
public class QLDSHD {
    static Scanner sc = new Scanner(System.in);
    public static DSHoaDon dshd = new DSHoaDon();

    public static void menu() {
        int opt;
        do {
            System.out.println("\n--- QUAN LY HOA DON ---");
            System.out.println("1. Nhap n hoa don dau tien");
            System.out.println("2. Xem danh sach");
            System.out.println("3. Them 1 hoa don");
            System.out.println("4. Them k hoa don");
            System.out.println("5. Sua hoa don");
            System.out.println("6. Xoa hoa don");
            System.out.println("7. Tim kiem");
            System.out.println("8. Thong ke");
            System.out.println("9. Xem chi tiet mot hoa don");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    dshd.nhapN();
                    break;
                case 2:
                    dshd.xuat();
                    break;
                case 3:
                    dshd.them(dshd.taoHoaDon());
                    break;
                case 4:
                    dshd.themK();
                    break;
                case 5:
                    System.out.print("Ma hoa don can sua: ");
                    dshd.suaTheoMa(sc.nextLine());
                    break;
                case 6:
                    System.out.print("Ma hoa don can xoa: ");
                    if (dshd.xoaTheoMa(sc.nextLine())) {
                        System.out.println("Da xoa hoa don (da tra hang ve kho va tru tien mua cua khach)!");
                    }
                    break;
                case 7:
                    dshd.timKiem();
                    break;
                case 8:
                    dshd.thongKe();
                    break;
                case 9: {
                    System.out.print("Ma hoa don: ");
                    HoaDon hd = dshd.timTheoMa(sc.nextLine());
                    if (hd == null) {
                        System.out.println("Khong tim thay hoa don!");
                    } else {
                        hd.xuat();
                        QLDSCTHD.dscthd.xuatTheoMaHD(hd.getMaHD());
                    }
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (opt != 0);
    }
}
