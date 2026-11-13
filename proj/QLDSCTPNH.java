import java.util.Scanner;

// Quản lý chi tiết phiếu nhập hàng: giữ BIẾN STATIC dsctpnh (dùng chung cho cả chương trình) + menu.
// Làm giống QLDSCTHD. Một dòng chi tiết được xác định bằng (mã phiếu nhập, mã sản phẩm).
public class QLDSCTPNH {
    static Scanner sc = new Scanner(System.in);
    public static DSChiTietPhieuNhap dsctpnh = new DSChiTietPhieuNhap();

    // Hỏi mã phiếu nhập + mã sản phẩm rồi ghép thành khóa của dòng chi tiết
    private static String nhapKhoa() {
        System.out.print("Ma phieu nhap: ");
        String maPNH = sc.nextLine();
        System.out.print("Ma san pham: ");
        String maSP = sc.nextLine();
        return DSChiTietPhieuNhap.taoKhoa(maPNH, maSP);
    }

    public static void menu() {
        int opt;
        do {
            System.out.println("\n--- QUAN LY CHI TIET PHIEU NHAP ---");
            System.out.println("1. Nhap n dong chi tiet dau tien");
            System.out.println("2. Xem danh sach");
            System.out.println("3. Them 1 dong chi tiet (vao phieu nhap da co)");
            System.out.println("4. Them k dong chi tiet");
            System.out.println("5. Sua dong chi tiet");
            System.out.println("6. Xoa dong chi tiet");
            System.out.println("7. Tim kiem");
            System.out.println("8. Thong ke");
            System.out.println("9. Xem chi tiet cua mot phieu nhap");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    dsctpnh.nhapN();
                    break;
                case 2:
                    dsctpnh.xuat();
                    break;
                case 3:
                    dsctpnh.them(dsctpnh.taoChiTiet());
                    break;
                case 4:
                    dsctpnh.themK();
                    break;
                case 5:
                    dsctpnh.suaTheoMa(nhapKhoa());
                    break;
                case 6:
                    if (dsctpnh.xoaTheoMa(nhapKhoa())) {
                        System.out.println("Da xoa dong chi tiet!");
                    } else {
                        System.out.println("Khong tim thay dong chi tiet!");
                    }
                    break;
                case 7:
                    dsctpnh.timKiem();
                    break;
                case 8:
                    dsctpnh.thongKe();
                    break;
                case 9: {
                    System.out.print("Ma phieu nhap: ");
                    String maPNH = sc.nextLine();
                    if (dsctpnh.xuatTheoMaPNH(maPNH) == 0) {
                        System.out.println("Phieu nhap nay khong co dong chi tiet nao!");
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
