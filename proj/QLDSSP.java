import java.util.Scanner;

// Tầng quản lý danh sách sản phẩm: giữ BIẾN STATIC dssp (dùng chung cho cả chương trình) + menu
public class QLDSSP {
    static Scanner sc = new Scanner(System.in);
    public static DSSanPham dssp = new DSSanPham();

    public static void menu() {
        int opt;
        do {
            System.out.println("\n--- QUAN LY SAN PHAM ---");
            System.out.println("1. Nhap n san pham dau tien");
            System.out.println("2. Xem danh sach");
            System.out.println("3. Them 1 san pham");
            System.out.println("4. Them k san pham");
            System.out.println("5. Sua san pham");
            System.out.println("6. Xoa san pham");
            System.out.println("7. Tim kiem");
            System.out.println("8. Thong ke");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    dssp.nhapN();
                    break;
                case 2:
                    dssp.xuat();
                    break;
                case 3:
                    dssp.them(dssp.themSanPham());
                    break;
                case 4:
                    dssp.themK();
                    break;
                case 5:
                    System.out.print("Ma san pham can sua: ");
                    dssp.suaTheoMa(sc.nextLine());
                    break;
                case 6: {
                    System.out.print("Ma san pham can xoa: ");
                    String ma = sc.nextLine();
                    if (QLDSCTHD.dscthd.coSanPham(ma) || QLDSCTPNH.dsctpnh.coSanPham(ma)) {
                        System.out.println("San pham nay da co trong hoa don hoac phieu nhap, khong the xoa!");
                    } else if (dssp.xoaTheoMa(ma)) {
                        System.out.println("Da xoa!");
                    } else {
                        System.out.println("Khong tim thay!");
                    }
                    break;
                }
                case 7:
                    dssp.timKiem();
                    break;
                case 8:
                    dssp.thongKe();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (opt != 0);
    }
}
