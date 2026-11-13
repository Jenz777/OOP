import java.util.Scanner;

// Lớp quản lý đồ án: menu chính, gọi các lớp quản lý danh sách, và đọc/ghi file.
public class QLDA {
    static Scanner sc = new Scanner(System.in);

    // Đọc dữ liệu từ các file txt vào các danh sách (làm 1 lần khi chương trình bắt
    // đầu)
    public static void docTatCa() {
        QLDSSP.dssp.docFile("sanpham.txt");
        QLDSKH.dskh.docFile("khachhang.txt");
        QLDSNV.dsnv.docFile("nhanvien.txt");
        QLDSNCC.dsncc.docFile("nhacungcap.txt");
        QLDSHD.dshd.docFile("hoadon.txt");
        QLDSCTHD.dscthd.docFile("chitiethoadon.txt");
        QLDSPNH.dspnh.docFile("phieunhap.txt");
        QLDSCTPNH.dsctpnh.docFile("chitietphieunhap.txt");
    }

    // Ghi tất cả danh sách ra các file txt
    public static void ghiTatCa() {
        QLDSSP.dssp.ghiFile("sanpham.txt");
        QLDSKH.dskh.ghiFile("khachhang.txt");
        QLDSNV.dsnv.ghiFile("nhanvien.txt");
        QLDSNCC.dsncc.ghiFile("nhacungcap.txt");
        QLDSHD.dshd.ghiFile("hoadon.txt");
        QLDSCTHD.dscthd.ghiFile("chitiethoadon.txt");
        QLDSPNH.dspnh.ghiFile("phieunhap.txt");
        QLDSCTPNH.dsctpnh.ghiFile("chitietphieunhap.txt");
    }

    public static void menu() {
        docTatCa(); // bắt đầu chương trình: nạp dữ liệu từ file
        int opt;
        do {
            System.out.println("===== QUAN LY CUA HANG VAN PHONG PHAM =====");
            System.out.println("1. Quan ly san pham");
            System.out.println("2. Quan ly khach hang");
            System.out.println("3. Quan ly nhan vien");
            System.out.println("4. Quan ly nha cung cap");
            System.out.println("5. Quan ly hoa don");
            System.out.println("6. Quan ly phieu nhap hang");
            System.out.println("7. Quan ly chi tiet hoa don");
            System.out.println("8. Quan ly chi tiet phieu nhap");
            System.out.println("9. Luu du lieu ra file");
            System.out.println("0. Thoat (tu dong luu)");
            System.out.print("Chon: ");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    QLDSSP.menu();
                    break;
                case 2:
                    QLDSKH.menu();
                    break;
                case 3:
                    QLDSNV.menu();
                    break;
                case 4:
                    QLDSNCC.menu();
                    break;
                case 5:
                    QLDSHD.menu();
                    break;
                case 6:
                    QLDSPNH.menu();
                    break;
                case 7:
                    QLDSCTHD.menu();
                    break;
                case 8:
                    QLDSCTPNH.menu();
                    break;
                case 9:
                    ghiTatCa();
                    System.out.println("Da luu du lieu ra file.");
                    break;
                case 0:
                    ghiTatCa(); // thoát chương trình: ghi dữ liệu ra file
                    System.out.println("Da luu du lieu!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (opt != 0);
    }
}
