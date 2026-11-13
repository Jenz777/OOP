import java.util.Scanner;

// Quản lý phiếu nhập hàng: làm giống QLDSHD.
// Danh sách chi tiết phiếu nhập nằm ở QLDSCTPNH.dsctpnh (có menu riêng).
// Việc lập / sửa / xóa phiếu nằm trong DSPhieuNhapHang (vì phải cộng / trừ tồn kho sản phẩm).
public class QLDSPNH {
    static Scanner sc = new Scanner(System.in);
    public static DSPhieuNhapHang dspnh = new DSPhieuNhapHang();

    public static void menu() {
        int opt;
        do {
            System.out.println("\n--- QUAN LY PHIEU NHAP HANG ---");
            System.out.println("1. Nhap n phieu nhap dau tien");
            System.out.println("2. Xem danh sach");
            System.out.println("3. Them 1 phieu nhap");
            System.out.println("4. Them k phieu nhap");
            System.out.println("5. Sua phieu nhap");
            System.out.println("6. Xoa phieu nhap");
            System.out.println("7. Tim kiem");
            System.out.println("8. Thong ke");
            System.out.println("9. Xem chi tiet mot phieu nhap");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    dspnh.nhapN();
                    break;
                case 2:
                    dspnh.xuat();
                    break;
                case 3:
                    dspnh.them(dspnh.taoPhieuNhap());
                    break;
                case 4:
                    dspnh.themK();
                    break;
                case 5:
                    System.out.print("Ma phieu nhap can sua: ");
                    dspnh.suaTheoMa(sc.nextLine());
                    break;
                case 6:
                    System.out.print("Ma phieu nhap can xoa: ");
                    if (dspnh.xoaTheoMa(sc.nextLine())) {
                        System.out.println("Da xoa phieu nhap (da tru lai ton kho)!");
                    }
                    break;
                case 7:
                    dspnh.timKiem();
                    break;
                case 8:
                    dspnh.thongKe();
                    break;
                case 9: {
                    System.out.print("Ma phieu nhap: ");
                    PhieuNhapHang p = dspnh.timTheoMa(sc.nextLine());
                    if (p == null) {
                        System.out.println("Khong tim thay phieu nhap!");
                    } else {
                        p.xuat();
                        QLDSCTPNH.dsctpnh.xuatTheoMaPNH(p.getMaPNH());
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
