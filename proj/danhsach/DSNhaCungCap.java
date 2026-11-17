package danhsach;

import interfaces.IFile;
import interfaces.IQuanLy;
import model.NhaCungCap;
import quanly.QLDSPNH;

import java.io.*;
import java.util.*;

public class DSNhaCungCap implements IQuanLy<NhaCungCap>, IFile {
    Scanner sc = new Scanner(System.in);
    private NhaCungCap[] danhSach;
    private int soLuong;

    public DSNhaCungCap() {
        danhSach = new NhaCungCap[10];
        soLuong = 0;
    }

    public DSNhaCungCap(DSNhaCungCap a) {
        this.danhSach = new NhaCungCap[10];
        this.soLuong = 0;
        for (int i = 0; i < a.soLuong; i++) {
            this.them(new NhaCungCap(a.danhSach[i]));
        }
    }

    public int getSoLuong() {
        return soLuong;
    }

    @Override
    public void them(NhaCungCap ncc) {
        if (timTheoMa(ncc.getMaNCC()) != null) {
            System.out.println("Ma nha cung cap " + ncc.getMaNCC() + " da ton tai, khong them!");
            return;
        }
        if (soLuong == danhSach.length) {
            danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
        }
        danhSach[soLuong] = ncc;
        soLuong++;
    }

    public NhaCungCap taoNhaCungCap() {
        NhaCungCap ncc = new NhaCungCap();
        ncc.nhap();
        while (timTheoMa(ncc.getMaNCC()) != null) {
            System.out.println("Ma nha cung cap da ton tai, nhap lai!");
            ncc.nhap();
        }
        return ncc;
    }

    @Override
    public void nhapN() {
        if (soLuong > 0) {
            System.out.println("Dang co " + soLuong + " nha cung cap, neu nhap moi se xoa het");
            System.out.println("Ban co muon xoa? Nhap 1 xac nhan, 2 de huy");
            int t = sc.nextInt();
            sc.nextLine();
            if (t != 1) {
                System.out.println("Da huy!");
                return;
            }
        }
        danhSach = new NhaCungCap[10];
        soLuong = 0;
        System.out.print("Ban muon nhap bao nhieu nha cung cap ban dau? n = ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("--- Nhap nha cung cap thu " + (i + 1) + " ---");
            them(taoNhaCungCap());
        }
    }

    @Override
    public void themK() {
        System.out.print("Nhap k nha cung cap muon them: ");
        int k = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < k; i++) {
            System.out.println("--- Them nha cung cap thu " + (i + 1) + " ---");
            them(taoNhaCungCap());
        }
    }

    @Override
    public void xuat() {
        if (soLuong == 0) {
            System.out.println("Danh sach nha cung cap rong!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print((i + 1) + ". ");
            danhSach[i].xuat();
        }
    }

    @Override
    public NhaCungCap timTheoMa(String ma) {
        int vt = timViTri(ma);
        if (vt == -1) {
            return null;
        }
        return danhSach[vt];
    }

    private int timViTri(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaNCC().equals(ma)) {
                return i;
            }
        }
        return -1;
    }

    // Sửa cơ bản: chọn thuộc tính rồi gán giá trị mới (không sửa mã vì mã là khóa).
    @Override
    public void suaTheoMa(String ma) {
        NhaCungCap ncc = timTheoMa(ma);
        if (ncc == null) {
            System.out.println("Khong tim thay nha cung cap!");
            return;
        }
        System.out.println("Thong tin nha cung cap:");
        ncc.xuat();
        System.out.println("1. Ten nha cung cap");
        System.out.println("2. Dia chi");
        System.out.print("Chon thong tin can sua: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                System.out.print("Ten nha cung cap moi: ");
                ncc.setTenNCC(sc.nextLine());
                break;
            case 2:
                System.out.print("Dia chi moi: ");
                ncc.setDiaChi(sc.nextLine());
                break;
            default:
                System.out.println("Lua chon khong hop le!");
        }
    }

    @Override
    public boolean xoaTheoMa(String ma) {
        int index = timViTri(ma);
        if (index == -1) {
            return false;
        }
        for (int i = index; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1];
        }
        danhSach[soLuong - 1] = null;
        soLuong--;
        return true;
    }

    // Tìm kiếm 2 khóa: theo mã (chính xác) và theo tên (gần đúng)
    // Tim gan dung theo ten nha cung cap, tra ve danh sach moi
    public DSNhaCungCap timTheoTen(String tuKhoa) {
        DSNhaCungCap kq = new DSNhaCungCap();
        String k = tuKhoa.toLowerCase();
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getTenNCC().toLowerCase().contains(k)) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    // Tim gan dung theo dia chi, tra ve danh sach moi
    public DSNhaCungCap timTheoDiaChi(String tuKhoa) {
        DSNhaCungCap kq = new DSNhaCungCap();
        String k = tuKhoa.toLowerCase();
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getDiaChi().toLowerCase().contains(k)) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    @Override
    public void timKiem() {
        System.out.println("1. Theo ma (chinh xac)  2. Theo ten (gan dung)");
        System.out.println("3. Theo dia chi (gan dung)");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        DSNhaCungCap kq = new DSNhaCungCap();
        switch (opt) {
            case 1: {
                System.out.print("Nhap ma nha cung cap: ");
                NhaCungCap ncc = timTheoMa(sc.nextLine());
                if (ncc != null) {
                    kq.them(ncc);
                }
                break;
            }
            case 2: {
                System.out.print("Nhap tu khoa: ");
                kq = timTheoTen(sc.nextLine());
                break;
            }
            case 3: {
                System.out.print("Nhap tu khoa (dia chi): ");
                kq = timTheoDiaChi(sc.nextLine());
                break;
            }
            default:
                System.out.println("Lua chon khong hop le!");
                return;
        }
        if (kq.getSoLuong() == 0) {
            System.out.println("Khong tim thay!");
        } else {
            kq.xuat();
        }
    }

    // ===== g. THỐNG KÊ NHIỀU KHÓA NHÀ CUNG CẤP (in ra màn hình) =====
    // Menu thống kê nhà cung cấp: mỗi lựa chọn là một cách thống kê (một khóa) khác nhau,
    // mỗi cách được viết thành một hàm riêng bên dưới
    @Override
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Danh sach nha cung cap rong!");
            return;
        }
        System.out.println("===== THONG KE NHA CUNG CAP =====");
        System.out.println("1. Tung nha cung cap: so phieu nhap va tong tien da nhap hang");
        System.out.println("2. Liet ke nha cung cap chua co phieu nhap nao");
        System.out.println("0. Quay lai");
        System.out.print("Chon: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                thongKeSoPhieuVaTienNhapTungNCC();
                break;
            case 2:
                thongKeNCCChuaCoPhieuNhap();
                break;
            case 0:
                break;
            default:
                System.out.println("Lua chon khong hop le!");
                break;
        }
    }

    // 1. SỐ PHIẾU NHẬP VÀ TỔNG TIỀN NHẬP CỦA TỪNG NHÀ CUNG CẤP.
    // Với MỖI nhà cung cấp in ra số phiếu nhập và tổng tiền đã nhập hàng
    // (lấy số liệu từ danh sách phiếu nhập dùng chung QLDSPNH.dspnh).
    public void thongKeSoPhieuVaTienNhapTungNCC() {
        System.out.println("SO PHIEU NHAP VA TONG TIEN NHAP CUA TUNG NHA CUNG CAP");
        System.out.println("Ma NCC | Ten NCC | So phieu nhap | Tong tien nhap (VND)");
        System.out.println("-----------------------------------------------------------");
        for (int i = 0; i < soLuong; i++) {
            String ma = danhSach[i].getMaNCC();
            System.out.println(ma + " | " + danhSach[i].getTenNCC() + " | " + QLDSPNH.dspnh.soPhieuTheoNCC(ma) + " | "
                    + (long) QLDSPNH.dspnh.tongTienTheoNCC(ma));
        }
    }

    // 2. NHÀ CUNG CẤP CHƯA CÓ PHIẾU NHẬP NÀO.
    // Liệt kê các nhà cung cấp chưa từng nhập hàng (số phiếu nhập = 0) và in số lượng.
    public void thongKeNCCChuaCoPhieuNhap() {
        System.out.println("NHA CUNG CAP CHUA CO PHIEU NHAP NAO");
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (QLDSPNH.dspnh.soPhieuTheoNCC(danhSach[i].getMaNCC()) == 0) {
                danhSach[i].xuat();
                dem++;
            }
        }
        System.out.println("Co " + dem + " nha cung cap chua co phieu nhap.");
    }

    // ===== ĐỌC / GHI FILE TXT (interface IFile) =====
    // Mỗi đối tượng 1 dòng, các thuộc tính ngăn cách nhau bằng dấu ;
    @Override
    public void docFile(String tenFile) {
        try {
            Scanner fs = new Scanner(new File(tenFile));
            while (fs.hasNextLine()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.fromFileString(fs.nextLine());
                them(ncc);
            }
            fs.close();
        } catch (Exception e) {
            System.out.println("Khong doc duoc file ");
        }
    }

    @Override
    public void ghiFile(String tenFile) {
        try {
            PrintWriter pw = new PrintWriter(tenFile);
            for (int i = 0; i < soLuong; i++) {
                pw.println(danhSach[i].toFileString());
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("Khong ghi duoc file " + tenFile);
        }
    }
}