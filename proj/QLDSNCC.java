import java.util.Scanner;

public class QLDSNCC {
    static Scanner sc = new Scanner(System.in);
    public static DSNhaCungCap dsncc = new DSNhaCungCap();

    public static void menu() {
        int opt;
        do {
            System.out.println("\n--- QUAN LY NHA CUNG CAP ---");
            System.out.println("1. Nhap n nha cung cap dau tien");
            System.out.println("2. Xem danh sach");
            System.out.println("3. Them 1 nha cung cap");
            System.out.println("4. Them k nha cung cap");
            System.out.println("5. Sua nha cung cap");
            System.out.println("6. Xoa nha cung cap");
            System.out.println("7. Tim kiem");
            System.out.println("8. Thong ke");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    dsncc.nhapN();
                    break;
                case 2:
                    dsncc.xuat();
                    break;
                case 3:
                    dsncc.them(dsncc.taoNhaCungCap());
                    break;
                case 4:
                    dsncc.themK();
                    break;
                case 5:
                    System.out.print("Ma nha cung cap can sua: ");
                    dsncc.suaTheoMa(sc.nextLine());
                    break;
                case 6: {
                    System.out.print("Ma nha cung cap can xoa: ");
                    String ma = sc.nextLine();
                    if (QLDSPNH.dspnh.soPhieuTheoNCC(ma) > 0) {
                        System.out.println("Nha cung cap nay da co phieu nhap, khong the xoa!");
                    } else if (dsncc.xoaTheoMa(ma)) {
                        System.out.println("Da xoa!");
                    } else {
                        System.out.println("Khong tim thay!");
                    }
                    break;
                }
                case 7:
                    dsncc.timKiem();
                    break;
                case 8:
                    dsncc.thongKe();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (opt != 0);
    }
}
