﻿-- Tạo cơ sở dữ liệu
CREATE DATABASE QuanLyNhaThuoc;
GO

USE QuanLyNhaThuoc;
GO


CREATE TABLE ChucVu (
	maChucVu SMALLINT PRIMARY KEY NOT NULL,
	tenChucVu NVARCHAR(50) NOT NULL
);

-- Bảng NhanVien
CREATE TABLE NhanVien (
    maNV VARCHAR(10) NOT NULL PRIMARY KEY,
    hoNV NVARCHAR(10) NOT NULL,
    tenNV NVARCHAR(50) NOT NULL,
    ngaySinh DATE NOT NULL,
    SDT VARCHAR(15) NOT NULL, 
    email VARCHAR(50),
    diaChi NVARCHAR(255),
    gioiTinh BIT,
    vaiTro SMALLINT NOT NULL,
    trangThai BIT NOT NULL,
    FOREIGN KEY (vaiTro) REFERENCES ChucVu(maChucVu)
);


-- Bảng TaiKhoan
CREATE TABLE TaiKhoan (
    taiKhoan VARCHAR(10) NOT NULL PRIMARY KEY,
    matKhau VARCHAR(20) NOT NULL,
    ngayCapNhat DATE,
    FOREIGN KEY (taiKhoan) REFERENCES NhanVien(maNV)
);


-- Bảng DiemTichLuy 
CREATE TABLE DiemTichLuy (
	maDTL VARCHAR(10) PRIMARY KEY,
	xepHang NVARCHAR(50) NOT NULL,
	diemTong FLOAT NOT NULL,
	diemHienTai FLOAT NOT NULL
);

-- Bảng KhachHang
CREATE TABLE KhachHang (
    maKH VARCHAR(10) NOT NULL PRIMARY KEY,
	hoKH NVARCHAR(10) NOT NULL,
    tenKH NVARCHAR(50) NOT NULL,
    ngaySinh DATE,
	gioiTinh BIT,
    email VARCHAR(50),
    diaChi NVARCHAR(255),
    SDT INT,
    trangThai BIT,
	maDTL VARCHAR(10) NOT NULL,
	FOREIGN KEY (maDTL) REFERENCES DiemTichLuy(maDTL)
);


-- Bảng Thue
CREATE TABLE Thue (
    maThue VARCHAR(15) NOT NULL PRIMARY KEY,
    loaiThue NVARCHAR(50) NOT NULL,
    tyLeThue FLOAT(10) NOT NULL
);

-- Bảng NhaCungCap
CREATE TABLE NhaCungCap (
    maNCC VARCHAR(10) NOT NULL PRIMARY KEY,
    tenNCC NVARCHAR(50),
    diaChi NVARCHAR(255),
    email VARCHAR(50)
);

-- Bảng DanhMuc
CREATE TABLE DanhMuc (
    maDanhMuc VARCHAR(10) NOT NULL PRIMARY KEY,
    tenDanhMuc NVARCHAR(50)
);

-- Bảng NhaSanXuat
CREATE TABLE NhaSanXuat (
    maNhaSX VARCHAR(15) NOT NULL PRIMARY KEY,
    tenNhaSX NVARCHAR(50),
    diaChi NVARCHAR(255)
);

-- Bảng KeThuoc
CREATE TABLE KeThuoc (
    maKe VARCHAR(10) NOT NULL PRIMARY KEY,
    tenKe NVARCHAR(50),
    maNhanVien VARCHAR(10),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNV)
);

-- Bảng NuocSanXuat
CREATE TABLE NuocSanXuat (
    maNuoc VARCHAR(10) NOT NULL PRIMARY KEY,
	tenNuoc NVARCHAR(50) NOT NULL
);

-- Bảng Thuoc (Sản phẩm thuốc)
CREATE TABLE Thuoc (
    soHieuThuoc VARCHAR(10) NOT NULL,
    maThuoc VARCHAR(10) NOT NULL,
	tenThuoc NVARCHAR(50) NOT NULL,
    maDanhMuc VARCHAR(10),
    maNhaCungCap VARCHAR(10),
    maNhaSanXuat VARCHAR(15),
	maNuocSanXuat VARCHAR(10),
    maKe VARCHAR(10),
    ngaySX DATE,
    HSD INT, 
    donViTinh NVARCHAR(10) NOT NULL,
	soLuongCon int NOT NULL,
    cachDung NVARCHAR(255),
    thanhPhan NVARCHAR(255),
    baoQuan NVARCHAR(255),
    congDung NVARCHAR(255),
    chiDinh NVARCHAR(255),
	hinhAnh VARCHAR(255),
    giaNhap FLOAT(10),
    giaBan FLOAT(10),
	trangThai bit,
	PRIMARY KEY (soHieuThuoc, maThuoc),
    FOREIGN KEY (maDanhMuc) REFERENCES DanhMuc(maDanhMuc),
    FOREIGN KEY (maNhaCungCap) REFERENCES NhaCungCap(maNCC),
    FOREIGN KEY (maNhaSanXuat) REFERENCES NhaSanXuat(maNhaSX),
    FOREIGN KEY (maKe) REFERENCES KeThuoc(maKe),
	FOREIGN KEY (maNuocSanXuat) REFERENCES NuocSanXuat(maNuoc)
);

-- Bảng HoaDon
CREATE TABLE HoaDon (
    maHD VARCHAR(10) NOT NULL PRIMARY KEY,
    maKhachHang VARCHAR(10),
    maNhanVien VARCHAR(10) NOT NULL,
    maThue VARCHAR(15),
    ngayLap DATE NOT NULL,
    hinhThucThanhToan NVARCHAR(20),
	trangThai bit,
    tienThue FLOAT(10),
    tongTien FLOAT(10),
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKH),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNV),
    FOREIGN KEY (maThue) REFERENCES Thue(maThue)
);

-- Bảng ChiTietHoaDon
CREATE TABLE ChiTietHoaDon (
    maHD VARCHAR(10) NOT NULL,
    soHieuThuoc VARCHAR(10) NOT NULL,
	maThuoc VARCHAR(10) NOT NULL,
    donViTinh NVARCHAR(20),
    soLuong INT NOT NULL,
    thanhTien FLOAT(10),
    PRIMARY KEY (maHD, soHieuThuoc),
    FOREIGN KEY (maHD) REFERENCES HoaDon(maHD),
    FOREIGN KEY (soHieuThuoc, maThuoc) REFERENCES Thuoc(soHieuThuoc, maThuoc)
);

-- Bảng DoiTra
CREATE TABLE PhieuDoiTra (
	maPhieu VARCHAR(10) NOT NULL PRIMARY KEY,
	maNV VARCHAR(10) NOT NULL,
	loai bit NOT NULL,
    maHD VARCHAR(10) NOT NULL,
	lyDo VARCHAR(255),
	ngayDoiTra DATE NOT NULL,
	FOREIGN KEY (maHD) REFERENCES HoaDon(maHD),
	FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);
-- Bảng DonDatThuoc
CREATE TABLE DonDatThuoc (
    maDon VARCHAR(10) NOT NULL PRIMARY KEY,
    maKhachHang VARCHAR(10) NOT NULL,
    maNhanVien VARCHAR(10),
    thoiGianDat DATE,
    tongTien FLOAT(10),
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKH),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNV)
);

-- Bảng ChiTietDonDatThuoc
CREATE TABLE ChiTietDonDatThuoc (
    maDon VARCHAR(10) NOT NULL,
    soHieuThuoc VARCHAR(10) NOT NULL,
	maThuoc VARCHAR(10) NOT NULL,
    donViTinh NVARCHAR(20),
    soLuong INT NOT NULL,
    thanhTien FLOAT(10),
    PRIMARY KEY (maDon, soHieuThuoc),
    FOREIGN KEY (maDon) REFERENCES DonDatThuoc(maDon),
    FOREIGN KEY (soHieuThuoc, maThuoc) REFERENCES Thuoc(soHieuThuoc, maThuoc)
);

-- Bảng PhieuNhapThuoc
CREATE TABLE PhieuNhapThuoc (
    maPhieuNhap VARCHAR(10) NOT NULL PRIMARY KEY,
    maNhanVien VARCHAR(10),
    ngayLapPhieu DATE,
    maNhaCungCap VARCHAR(10),
    tongTien FLOAT(10),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNV),
    FOREIGN KEY (maNhaCungCap) REFERENCES NhaCungCap(maNCC)
);

-- Bảng ChiTietPhieuNhap
CREATE TABLE ChiTietPhieuNhap (
    maPhieuNhap VARCHAR(10) NOT NULL,
    soHieuThuoc VARCHAR(10) NOT NULL,
	maThuoc VARCHAR(10) NOT NULL,
    soLuong INT NOT NULL,
	donViTinh NVARCHAR(20) NOT NULL,
    donGiaNhap FLOAT(10),
    thanhTien FLOAT(10),
    PRIMARY KEY (maPhieuNhap, soHieuThuoc),
    FOREIGN KEY (maPhieuNhap) REFERENCES PhieuNhapThuoc(maPhieuNhap),
    FOREIGN KEY (soHieuThuoc, maThuoc) REFERENCES Thuoc(soHieuThuoc, maThuoc)
);

-- Bảng ChuongTrinhKhuyenMai
CREATE TABLE ChuongTrinhKhuyenMai (
    maCTKM VARCHAR(10) NOT NULL PRIMARY KEY,
    mota NVARCHAR(255),
    loaiKhuyenMai NVARCHAR(50),
    ngayBatDau DATE,
    ngayKetThuc DATE
);

-- Bảng ChiTietKhuyenMai
CREATE TABLE ChiTietKhuyenMai (
    maCTKM VARCHAR(10) NOT NULL,
    soHieuThuoc VARCHAR(10) NOT NULL,
	maThuoc VARCHAR(10) NOT NULL,
    tyLeKhuyenMai FLOAT(10),
    soLuongToiThieu INT,
    PRIMARY KEY (maCTKM, soHieuThuoc),
    FOREIGN KEY (maCTKM) REFERENCES ChuongTrinhKhuyenMai(maCTKM),
    FOREIGN KEY (soHieuThuoc, maThuoc) REFERENCES Thuoc(soHieuThuoc, maThuoc)
);

-- Bảng ChiTietPhieuDoiTra
CREATE TABLE ChiTietPhieuDoiTra (
	maPhieu VARCHAR(10) NOT NULL,
	soHieuThuoc VARCHAR(10) NOT NULL,
	maThuoc VARCHAR(10) NOT NULL,
	ghiChu NVARCHAR(255),
	PRIMARY KEY (maPhieu, soHieuThuoc),
	FOREIGN KEY (maPhieu) REFERENCES PhieuDoiTra(maPhieu),
    FOREIGN KEY (soHieuThuoc, maThuoc) REFERENCES Thuoc(soHieuThuoc, maThuoc)
);


---- Thêm dữ liệu

-- Bảng ChucVu
INSERT INTO ChucVu (maChucVu, tenChucVu)
VALUES
(0, N'Quản trị viên'),
(1, N'Nhân viên bán thuốc'),
(2, N'Nhân viên quản lý')

-- Bảng NhanVien
INSERT INTO NhanVien (maNV, hoNV, tenNV, email, ngaySinh, SDT, diaChi, gioiTinh, vaiTro, trangThai)
VALUES
('NV001', N'Nguyễn', N'Văn An', null, '1990-05-10', 123456789, N'123 Le Loi, TP.HCM', 1, 1, 1),
('NV002', N'Trần', N'Thị Bình', null , '1992-07-20', 987654321, N'456 Tran Hung Dao, TP.HCM', 0, 1, 1),
('NV003', N'Lê', N'Văn Cao', null , '1988-03-15', 456789123, N'789 Nguyen Trai, TP.HCM', 1, 1, 1),
('QL001', N'Cao', N'Thành Đông', null , '1995-08-25', 321654987, N'321 Hai Ba Trung, TP.HCM', 1, 2, 1),
('QL002', N'Võ', N'Thị Dung', null , '1985-12-11', 789123456, N'654 Cong Quynh, TP.HCM', 0, 2, 1);

-- Bảng TaiKhoan
INSERT INTO TaiKhoan (taiKhoan, matKhau)
VALUES
('NV001', '123'),
('NV002', '123'),
('NV003', '123'),
('QL001', '123'),
('QL002', '123')

-- Bảng DiemTichLuy
INSERT INTO DiemTichLuy(maDTL, xepHang, diemTong, diemHienTai)
VALUES
('DTL001', N'Vàng', 90000, 90000),
('DTL002', N'Đồng', 9000, 9000),
('DTL003', N'Bạc', 30000, 30000),
('DTL004', N'Kim cương', 5000000, 500000),
('DTL005', N'Bạch kim', 2000000, 200000)

-- Bảng KhachHang
INSERT INTO KhachHang (maKH, hoKH, tenKH, ngaySinh, gioiTinh, diaChi, SDT, trangThai, maDTL)
VALUES
('KH001', N'Lê', N'Thanh', '1991-04-10', 0, N'23 Nguyen Hue, TP.HCM', 100, 1, 'DTL001'),
('KH002', N'Phạm', N'Khánh Vân', '1980-11-22', 0, N'45 Dien Bien Phu, TP.HCM', 200, 1, 'DTL002'),
('KH003', N'Trần', N'Quốc Anh', '1993-02-18', 1, N'67 Phan Xich Long, TP.HCM', 300, 1, 'DTL003'),
('KH004', N'Nguyễn', N'Văn Hải', '1996-09-07', 1, N'89 Ton Duc Thang, TP.HCM', 400, 1, 'DTL004'),
('KH005', N'Hồ', N'Văn Minh', '1989-06-30', 1, N'12 Tran Cao Van, TP.HCM', 500, 1, 'DTL005')


-- Bảng NhaCungCap
INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, email)
VALUES
('NCC001', N'Công ty Dược A', N'123 Pham Van Dong, TP.HCM', 'duoc_a@example.com'),
('NCC002', N'Công ty Dược B', N'456 Nguyen Trai, TP.HCM', 'duoc_b@example.com'),
('NCC003', N'Công ty Dược C', N'789 Tran Hung Dao, TP.HCM', 'duoc_c@example.com'),
('NCC004', N'Công ty Dược D', N'321 Le Loi, TP.HCM', 'duoc_d@example.com'),
('NCC005', N'Công ty Dược E', N'654 Hai Ba Trung, TP.HCM', 'duoc_e@example.com');

-- Bảng NuocSanXuat
INSERT INTO NuocSanXuat (maNuoc, tenNuoc)
VALUES
('US', N'Mỹ'),
('CN', N'Trung Quốc'),
('EN', N'Anh'),
('RU', N'Nga')

-- Bảng KeThuoc
INSERT INTO KeThuoc (maKe, tenKe, maNhanVien)
VALUES
('K01', N'Kệ A', 'NV001'),
('K02', N'Kệ B', 'NV002'),
('K03', N'Kệ C', 'NV003')

-- Bảng NhaSanXuat
INSERT INTO NhaSanXuat (maNhaSX, tenNhaSX, diaChi)
VALUES
('NHSX001', N'Nhà sản xuất A', '123 Nguyen Hue, TP.HCM'),
('NHSX002', N'Nhà sản xuất B', '456 Le Loi, TP.HCM'),
('NHSX003', N'Nhà sản xuất C', '789 Dien Bien Phu, TP.HCM'),
('NHSX004', N'Nhà sản xuất D', '321 Hai Ba Trung, TP.HCM'),
('NHSX005', N'Nhà sản xuất E', '654 Phan Xich Long, TP.HCM');

-- Bảng DanhMuc
INSERT INTO DanhMuc (maDanhMuc, tenDanhMuc)
VALUES
('DM001', N'Thuốc giảm đau'),
('DM002', N'Thực phẩm chức năng'),
('DM003', N'Thuốc cảm sốt')

-- Bảng Thue
INSERT INTO Thue (maThue, loaiThue, tyLeThue)
VALUES
('THUE001', N'VAT', 0.1),
('THUE002', N'Thuế nhập khẩu', 0.05),
('THUE003', N'Thuế tiêu thụ đặc biệt', 0.2)

-- Bảng Thuoc
INSERT INTO Thuoc (soHieuThuoc, maThuoc, tenThuoc, donViTinh, maKe, HSD, giaBan, soLuongCon, maDanhMuc, maNhaCungCap, maNhaSanXuat, maNuocSanXuat, trangThai, hinhAnh)
VALUES
('S00001', 'T001', N'Paracetamol', N'Hộp','K01', 60, 50000, 50, 'DM001', 'NCC001', 'NHSX001', 'US', 1, 'images\\sample.png'),
('S00002' ,'T002', N'Aspirin', N'Hộp','K01', 36, 35000, 40, 'DM001', 'NCC001', 'NHSX003', 'CN', 1, 'images\\sample.png'),
('S00003', 'T003', N'Amoxicillin', N'Hộp','K02', 24, 150000, 50, 'DM003', 'NCC002', 'NHSX002', 'RU', 1, 'images\\sample.png'),
('S00004', 'T004', N'Ibuprofen', N'Hộp','K03', 24, 75000, 50, 'DM001', 'NCC003', 'NHSX001', 'EN', 1, 'images\\sample.png'),
('S00005', 'T005', N'Vitamin C', N'Viên','K03', 36, 5000, 300, 'DM002', 'NCC002', 'NHSX003', 'US', 1, 'images\\sample.png')


INSERT INTO Thuoc (soHieuThuoc, maThuoc, tenThuoc, donViTinh, maKe, HSD, giaBan, soLuongCon, maDanhMuc, maNhaCungCap, maNhaSanXuat, maNuocSanXuat, trangThai, hinhAnh)
VALUES
('S00006', 'T006', N'Paracetamol', N'Hộp','K01', 60, 50000, 50, 'DM001', 'NCC001', 'NHSX001', 'US', 1, 'images\\sample.png'),
('S00007' ,'T007', N'Aspirin', N'Hộp','K01', 36, 35000, 40, 'DM001', 'NCC001', 'NHSX003', 'CN', 1, 'images\\sample.png'),
('S00008', 'T008', N'Amoxicillin', N'Hộp','K02', 24, 150000, 50, 'DM003', 'NCC002', 'NHSX002', 'RU', 1, 'images\\sample.png'),
('S00009', 'T009', N'Ibuprofen', N'Hộp','K03', 24, 75000, 50, 'DM001', 'NCC003', 'NHSX001', 'EN', 1, 'images\\sample.png'),
('S00010', 'T0010', N'Vitamin C', N'Viên','K03', 36, 5000, 300, 'DM002', 'NCC002', 'NHSX003', 'US', 1, 'images\\sample.png')

-- Bảng HoaDon
INSERT INTO HoaDon (maHD, maKhachHang, maNhanVien, maThue, ngayLap, hinhThucThanhToan, tongTien)
VALUES
('HD001', 'KH001', 'NV001', 'THUE001', '2024-09-01', N'Tiền mặt', 187000),
('HD002', 'KH002', 'NV002', 'THUE001', '2024-09-02', N'Tiền mặt', 357500)


-- Bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon (maHD, soHieuThuoc, maThuoc, donViTinh, soLuong, thanhTien)
VALUES
('HD001', 'S00001', 'T001', N'Hộp', 1, 100000),
('HD001', 'S00002', 'T002', N'Hộp', 2, 35000),
('HD002', 'S00003', 'T003', N'Hộp', 1, 150000),
('HD002', 'S00004', 'T004', N'Hộp', 1, 75000),
('HD002', 'S00005', 'T005', N'Viên', 5, 100000)

-- Bảng DonDatThuoc
INSERT INTO DonDatThuoc (maDon, maKhachHang, maNhanVien, thoiGianDat, tongTien)
VALUES
('MD001', 'KH001', 'NV001', '2024-09-01', 85000),
('MD002', 'KH002', 'NV002', '2024-09-02', 300000),
('MD003', 'KH003', 'NV003', '2024-09-03', 250000)

-- Bảng ChiTietDonDatThuoc
INSERT INTO ChiTietDonDatThuoc (maDon, soHieuThuoc, maThuoc, donViTinh, soLuong, thanhTien)
VALUES
('MD001', 'S00001', 'T001','Hộp', 1, 50000),
('MD001', 'S00002', 'T002', 'Hộp', 1, 35000),
('MD002', 'S00003', 'T003', 'Hộp', 2, 300000),
('MD003', 'S00004', 'T004', 'Hộp', 2, 75000),
('MD003', 'S00005', 'T005', 'Viên', 20, 100000)

-- Bảng ChuongTrinhKhuyenMai
INSERT INTO ChuongTrinhKhuyenMai (maCTKM, moTa, loaiKhuyenMai, ngayBatDau, ngayKetThuc)
VALUES
('KM001', N'Giảm giá 30% cho thuốc cảm cúm', N'Khuyến mãi tháng 10','2024-09-01', '2024-09-30'),
('KM002', N'Giảm giá 10% cho đơn trên 500k', N'Khuyến mãi 9.9', '2024-10-01', '2024-10-31'),
('KM003', N'Giảm 5% cho tất cả các thuốc', N'Khuyến mãi cuối tuần', '2024-11-01', '2024-11-30'),
('KM004', N'Khuyến mãi 20% cho thành viên mới', N'Khuyến mãi khách hàng mới', '2024-12-01', '2024-12-31'),
('KM005', N'Giảm giá 50% cho sản phẩm cận hạn', N'Khuyến mãi cuối tháng', '2024-12-15', '2024-12-31');

INSERT INTO ChiTietKhuyenMai (maCTKM, soHieuThuoc, maThuoc, tyLeKhuyenMai, soLuongToiThieu)
VALUES
('KM001', 'S00001', 'T001', null, 5),
('KM002', 'S00002', 'T002', 0.1, 5),
('KM003', 'S00003', 'T003', null, 3),
('KM004', 'S00004', 'T004', 0.2, 5),
('KM005', 'S00005', 'T005', 0.5, 4);
GO



-- lấy danh sách đơn đặt thuốc
CREATE PROCEDURE getAllDonDatThuoc
AS
BEGIN
    SELECT * FROM DonDatThuoc;
END
GO

-- lấy danh sách chi tiết đơn đặt thuốc theo mã đơn 
CREATE PROCEDURE getChiTietDonDatThuocByMaDon @maDon VARCHAR(10)
AS
BEGIN
    SELECT * FROM ChiTietDonDatThuoc
    WHERE maDon = @maDon;
END
GO