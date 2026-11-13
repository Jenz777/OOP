import java.util.Scanner;

// Quản lý chi tiết hóa đơn: giữ BIẾN STATIC dscthd (dùng chung cho cả chương trình) + menu.
// Làm giống QLDSSP / QLDSKH... Một dòng chi tiết được xác định bằng (mã hóa đơn, mã sản phẩm).
public class QLDSCTHD {
    static Scanner sc = new Scanner(System.in);
    public static DSChiTietHoaDon dscthd = new DSChiTietHoaDon();

    // Hỏi mã hóa đơn + mã sản phẩm rồi ghép thành khóa của dòng chi tiết
    private static String nhapKhoa() {
        System.out.print("Ma hoa don: ");
        String maHD = sc.nextLine();
        System.out.print("Ma san pham: ");
        String maSP = sc.nextLine();
        return DSChiTietHoaDon.taoKhoa(maHD, maSP);
    }

    public static void menu() {
        int opt;
        do {
            System.out.println("\n--- QUAN LY CHI TIET HOA DON ---");
            System.out.println("1. Nhap n dong chi tiet dau tien");
            System.out.println("2. Xem danh sach");
            System.out.println("3. Them 1 dong chi tiet (vao hoa don da co)");
            System.out.println("4. Them k dong chi tiet");
            System.out.println("5. Sua dong chi tiet");
            System.out.println("6. Xoa dong chi tiet");
            System.out.println("7. Tim kiem");
            System.out.println("8. Thong ke");
            System.out.println("9. Xem chi tiet cua mot hoa don");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    dscthd.nhapN();
                    break;
                case 2:
                    dscthd.xuat();
                    break;
                case 3:
                    dscthd.them(dscthd.taoChiTiet());
                    break;
                case 4:
                    dscthd.themK();
                    break;
                case 5:
                    dscthd.suaTheoMa(nhapKhoa());
                    break;
                case 6:
                    if (dscthd.xoaTheoMa(nhapKhoa())) {
                        System.out.println("Da xoa dong chi tiet!");
                    } else {
                        System.out.println("Khong tim thay dong chi tiet!");
                    }
                    break;
                case 7:
                    dscthd.timKiem();
                    break;
                case 8:
                    dscthd.thongKe();
                    break;
                case 9: {
                    System.out.print("Ma hoa don: ");
                    String maHD = sc.nextLine();
                    if (dscthd.xuatTheoMaHD(maHD) == 0) {
                        System.out.println("Hoa don nay khong co dong chi tiet nao!");
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
