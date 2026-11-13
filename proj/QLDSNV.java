import java.util.Scanner;

public class QLDSNV {
    static Scanner sc = new Scanner(System.in);
    public static DSNhanVien dsnv = new DSNhanVien();

    public static void menu() {
        int opt;
        do {
            System.out.println("\n--- QUAN LY NHAN VIEN ---");
            System.out.println("1. Nhap n nhan vien dau tien");
            System.out.println("2. Xem danh sach");
            System.out.println("3. Them 1 nhan vien");
            System.out.println("4. Them k nhan vien");
            System.out.println("5. Sua nhan vien");
            System.out.println("6. Xoa nhan vien");
            System.out.println("7. Tim kiem");
            System.out.println("8. Thong ke");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    dsnv.nhapN();
                    break;
                case 2:
                    dsnv.xuat();
                    break;
                case 3:
                    dsnv.them(dsnv.taoNhanVien());
                    break;
                case 4:
                    dsnv.themK();
                    break;
                case 5:
                    System.out.print("Ma nhan vien can sua: ");
                    dsnv.suaTheoMa(sc.nextLine());
                    break;
                case 6: {
                    System.out.print("Ma nhan vien can xoa: ");
                    String ma = sc.nextLine();
                    if (QLDSHD.dshd.coHoaDonCuaNV(ma) || QLDSPNH.dspnh.coPhieuCuaNV(ma)) {
                        System.out.println("Nhan vien nay da lap hoa don hoac phieu nhap, khong the xoa!");
                    } else if (dsnv.xoaTheoMa(ma)) {
                        System.out.println("Da xoa!");
                    } else {
                        System.out.println("Khong tim thay!");
                    }
                    break;
                }
                case 7:
                    dsnv.timKiem();
                    break;
                case 8:
                    dsnv.thongKe();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (opt != 0);
    }
}
