public interface IQuanLy<T> {
    void nhapN(); // a. nhập n phần tử đầu tiên

    void xuat(); // b. xuất danh sách

    void them(T x); // c. thêm 1 phần tử đã có sẵn

    void themK(); // c. thêm k phần tử (hỏi k, nhập từng cái)

    T timTheoMa(String ma); // dùng chung cho sửa, xóa; trả về null nếu không thấy

    void suaTheoMa(String ma); // d. sửa theo mã

    boolean xoaTheoMa(String ma); // e. xóa theo mã, true nếu xóa được

    void timKiem(); // f. tìm kiếm nhiều khóa

    void thongKe(); // g. thống kê nhiều khóa
}
