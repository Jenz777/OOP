import java.util.Scanner;

public class QLDSKH {
    static Scanner sc = new Scanner(System.in);
    public static DSKhachHang dskh = new DSKhachHang();

    public static void menu() {
        int opt;
        do {
            System.out.println("\n--- QUAN LY KHACH HANG ---");
            System.out.println("1. Nhap n khach hang dau tien");
            System.out.println("2. Xem danh sach");
            System.out.println("3. Them 1 khach hang");
            System.out.println("4. Them k khach hang");
            System.out.println("5. Sua khach hang");
            System.out.println("6. Xoa khach hang");
            System.out.println("7. Tim kiem");
            System.out.println("8. Thong ke");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    dskh.nhapN();
                    break;
                case 2:
                    dskh.xuat();
                    break;
                case 3:
                    dskh.them(dskh.taoKhachHang());
                    break;
                case 4:
                    dskh.themK();
                    break;
                case 5:
                    System.out.print("Ma khach hang can sua: ");
                    dskh.suaTheoMa(sc.nextLine());
                    break;
                case 6: {
                    System.out.print("Ma khach hang can xoa: ");
                    String ma = sc.nextLine();
                    if (QLDSHD.dshd.coHoaDonCuaKH(ma)) {
                        System.out.println("Khach hang nay da co hoa don, khong the xoa!");
                    } else if (dskh.xoaTheoMa(ma)) {
                        System.out.println("Da xoa!");
                    } else {
                        System.out.println("Khong tim thay!");
                    }
                    break;
                }
                case 7:
                    dskh.timKiem();
                    break;
                case 8:
                    dskh.thongKe();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (opt != 0);
    }
}
