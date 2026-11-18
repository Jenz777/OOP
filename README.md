# Kịch bản demo – Quản lý cửa hàng văn phòng phẩm (nhóm 8)

Mỗi module là 1 lượt chạy: nhập số module ở menu chính, rồi các dòng bên dưới (mỗi dòng là một lần Enter), cuối cùng `0` để quay lại và `0` để thoát.

**Lưu ý:** thoát bằng `0` sẽ tự ghi đè các file trong `data/` – hãy sao lưu thư mục `data` trước khi demo.

## 1. Quản lý sản phẩm
Xem DS (đa hình Bút/Tập viết) → thêm 1 → sửa → tìm theo mã/tên/khoảng giá → thêm k → thống kê (tồn kho theo loại, theo khoảng giá bán) → xóa

```
1 | 2 | 3 | 1 | B21 | But demo | 50 | 5000 | cay | Do | Bi | 5 | B21 | 3 | 6000 | 7 | 1 | B21 | 4 | 1 | 2 | T17 | Tap demo | 30 | 9000 | quyen | 80 | Ke ngang | 8 | 1 | 8 | 2 | 5000 | 10000 | 6 | B21 | 6 | T17 | 0 | 0
```

## 2. Quản lý khách hàng
Xem → thêm → sửa → tìm (mã, tên gần đúng, khoảng tiền) → thống kê (từ mức tối thiểu, Top N) → xóa

```
2 | 2 | 3 | K99 | Demo | Khach | 0 | 5 | K99 | 1 | Hoang Van | 7 | 1 | K99 | 7 | 2 | an | 7 | 3 | 100000 | 500000 | 8 | 1 | 100000 | 8 | 2 | 3 | 6 | K99 | 2 | 0 | 0
```

## 3. Quản lý nhân viên
Xem → thêm → sửa → tìm (mã, tên, khoảng lương) → thống kê (khoảng lương, doanh thu từng NV theo năm) → xóa

```
3 | 2 | 3 | NV99 | Demo | Nv | Thu ngan | 6000000 | 5 | NV99 | 3 | Quan ly kho | 7 | 1 | NV99 | 7 | 2 | mai | 7 | 3 | 7000000 | 8500000 | 8 | 1 | 7000000 | 9000000 | 8 | 2 | 2026 | 6 | NV99 | 0 | 0
```

## 4. Quản lý nhà cung cấp
Xem → thêm → sửa → tìm (mã, tên, địa chỉ) → thống kê (số phiếu và tiền nhập từng NCC, NCC chưa có phiếu) → xóa

```
4 | 2 | 3 | N99 | Demo | Can Tho | 5 | N99 | 1 | Demo Moi | 7 | 1 | N99 | 7 | 2 | long | 7 | 3 | Ha | 8 | 1 | 8 | 2 | 6 | N99 | 0 | 0
```

## 5. Quản lý hóa đơn
Lập hóa đơn 2 dòng (trừ tồn kho, cộng tiền khách) → xem chi tiết HĐ → tìm → thống kê thu-chi-lợi nhuận (theo ngày, theo quý) → sửa → xóa

```
5 | 3 | HD99 | NV01 | K01 | 2 | B01 | 2 | T01 | 1 | 9 | HD99 | 7 | 1 | HD99 | 7 | 2 | an | 7 | 3 | 20000 | 100000 | 8 | 1 | 2026-10-01 | 2026-11-30 | 8 | 2 | 2026 | 5 | HD99 | 4 | 100000 | 6 | HD99 | 0 | 0
```

## 6. Quản lý phiếu nhập hàng
Lập phiếu nhập (cộng tồn kho) → tìm → thống kê chi nhập hàng (theo ngày, theo quý) → sửa → xóa

```
6 | 3 | PN98 | N01 | NV03 | 1 | B01 | 10 | 3000 | 7 | 1 | PN98 | 7 | 2 | thien | 7 | 3 | 500000 | 700000 | 8 | 1 | 2026-10-01 | 2026-11-30 | 8 | 2 | 2026 | 5 | PN98 | 4 | 123 | 6 | PN98 | 0 | 0
```

## 7. Chi tiết hóa đơn
Xem → thêm dòng → sửa số lượng → tìm (mã HĐ+SP, tên SP, khoảng thành tiền) → thống kê theo loại SP / bán chạy → xóa

```
7 | 2 | 3 | HD02 | B05 | 9900 | 4 | 5 | HD02 | B05 | 1 | 10 | 7 | 1 | HD02 | B05 | 7 | 2 | but | 7 | 3 | 40000 | 100000 | 8 | 1 | 8 | 2 | 6 | HD02 | B05 | 0 | 0
```

## 8. Chi tiết phiếu nhập
Tương tự mục 7 cho phiếu nhập

```
8 | 2 | 3 | PN01 | B05 | 8000 | 20 | 5 | PN01 | B05 | 1 | 30 | 7 | 1 | PN01 | B05 | 7 | 2 | but | 7 | 3 | 200000 | 500000 | 8 | 1 | 8 | 2 | 6 | PN01 | B05 | 0 | 0
```
