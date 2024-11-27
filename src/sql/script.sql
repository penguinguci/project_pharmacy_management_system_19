USE master
-- Tạo cơ sở dữ liệu
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
    maNV VARCHAR(20) NOT NULL PRIMARY KEY,
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
    taiKhoan VARCHAR(20) NOT NULL PRIMARY KEY,
    matKhau VARCHAR(20) NOT NULL,
    ngayCapNhat DATE,
    FOREIGN KEY (taiKhoan) REFERENCES NhanVien(maNV)
);


-- Bảng DiemTichLuy 
CREATE TABLE DiemTichLuy (
	maDTL VARCHAR(20) PRIMARY KEY,
	xepHang NVARCHAR(50) NOT NULL,
	diemTong FLOAT NOT NULL,
	diemHienTai FLOAT NOT NULL
);

-- Bảng KhachHang
CREATE TABLE KhachHang (
    maKH VARCHAR(20) NOT NULL PRIMARY KEY,
	hoKH NVARCHAR(10) NOT NULL,
    tenKH NVARCHAR(50) NOT NULL,
    ngaySinh DATE,
	gioiTinh BIT,
    email VARCHAR(50),
    diaChi NVARCHAR(255),
    SDT VARCHAR(15),
    trangThai BIT,
	maDTL VARCHAR(20),
	FOREIGN KEY (maDTL) REFERENCES DiemTichLuy(maDTL)
);


-- Bảng Thue
CREATE TABLE Thue (
    maThue VARCHAR(20) NOT NULL PRIMARY KEY,
    loaiThue NVARCHAR(50) NOT NULL,
    tyLeThue FLOAT(10) NOT NULL
);

-- Bảng NhaCungCap
CREATE TABLE NhaCungCap (
    maNCC VARCHAR(20) NOT NULL PRIMARY KEY,
    tenNCC NVARCHAR(50),
    diaChi NVARCHAR(255),
    email VARCHAR(50)
);

-- Bảng DanhMuc
CREATE TABLE DanhMuc (
    maDanhMuc VARCHAR(20) NOT NULL PRIMARY KEY,
    tenDanhMuc NVARCHAR(50)
);

-- Bảng NhaSanXuat
CREATE TABLE NhaSanXuat (
    maNhaSX VARCHAR(20) NOT NULL PRIMARY KEY,
    tenNhaSX NVARCHAR(50),
    diaChi NVARCHAR(255)
);

-- Bảng KeThuoc
CREATE TABLE KeThuoc (
    maKe VARCHAR(20) NOT NULL PRIMARY KEY,
    tenKe NVARCHAR(50),
    maNhanVien VARCHAR(20),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNV)
);

-- Bảng NuocSanXuat
CREATE TABLE NuocSanXuat (
    maNuoc VARCHAR(20) NOT NULL PRIMARY KEY,
	tenNuoc NVARCHAR(50) NOT NULL
);

-- Bảng DonGiaThuoc
CREATE TABLE DonGiaThuoc (
	maDonGia VARCHAR(20) PRIMARY KEY NOT NULL,
	maThuoc VARCHAR(20) NOT NULL,
	donViTinh NVARCHAR(50) NOT NULL,
	donGia FLOAT(10)
)



-- Bảng Thuoc (Sản phẩm thuốc)
CREATE TABLE Thuoc (
    maThuoc VARCHAR(20) primary key NOT NULL,
	tenThuoc NVARCHAR(50) NOT NULL,
    maDanhMuc VARCHAR(20),
    maNhaCungCap VARCHAR(20),
    maNhaSanXuat VARCHAR(20),
	maNuocSanXuat VARCHAR(20),
    maKe VARCHAR(20),
	tongSoLuong INT NOT NULL,
    cachDung NVARCHAR(255),
    thanhPhan NVARCHAR(255),
    baoQuan NVARCHAR(255),
    congDung NVARCHAR(255),
    chiDinh NVARCHAR(255),
	moTa NVARCHAR(255),
	hamLuong NVARCHAR(255),
	dangBaoChe NVARCHAR(255),
	hinhAnh VARCHAR(255),
	trangThai BIT,
    FOREIGN KEY (maDanhMuc) REFERENCES DanhMuc(maDanhMuc),
    FOREIGN KEY (maNhaCungCap) REFERENCES NhaCungCap(maNCC),
    FOREIGN KEY (maNhaSanXuat) REFERENCES NhaSanXuat(maNhaSX),
    FOREIGN KEY (maKe) REFERENCES KeThuoc(maKe),
	FOREIGN KEY (maNuocSanXuat) REFERENCES NuocSanXuat(maNuoc),
);


-- Bảng PhieuNhapThuoc
CREATE TABLE PhieuNhapThuoc (
    maPhieuNhap VARCHAR(20) NOT NULL PRIMARY KEY,
    maNhanVien VARCHAR(20) NOT NULL, 
    ngayLapPhieu DATE NOT NULL,
    maNhaCungCap VARCHAR(20) NOT NULL,
    tongTien FLOAT(10) NOT NULL,
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNV),
    FOREIGN KEY (maNhaCungCap) REFERENCES NhaCungCap(maNCC)
);

-- Bảng ChiTietPhieuNhap
CREATE TABLE ChiTietPhieuNhap (
    maPhieuNhap VARCHAR(20) NOT NULL,
	maThuoc VARCHAR(20) NOT NULL,
	donViTinh NVARCHAR(20) NOT NULL,
	ngaySanXuat DATE NOT NULL,
	HSD DATE NOT NULL,
    donGiaNhap FLOAT(10) NOT NULL,
	soLuongNhap INT NOT NULL,
    thanhTien FLOAT(10) NOT NULL,
	PRIMARY KEY (maPhieuNhap, maThuoc, donViTinh),
    FOREIGN KEY (maPhieuNhap) REFERENCES PhieuNhapThuoc(maPhieuNhap),
    FOREIGN KEY (maThuoc) REFERENCES Thuoc(maThuoc)
);

-- Bảng LoThuoc
CREATE TABLE LoThuoc (
	maLoThuoc VARCHAR(20) primary key not null,
	maPhieuNhap VARCHAR(20) not null,
	ngayNhap DATE,
	tongTien FLOAT(10)
	FOREIGN KEY (maPhieuNhap) REFERENCES PhieuNhapThuoc(maPhieuNhap),
)

-- Bảng ChiTietLoThuoc
CREATE TABLE ChiTietLoThuoc (
	soHieuThuoc VARCHAR(20) primary key not null,
	maThuoc VARCHAR(20) not null,
	maLoThuoc VARCHAR(20) not null,
	ngaySX DATE not null,
	HSD DATE not null,
	soLuongCon INT not null,
	maDonGia VARCHAR(20),
	FOREIGN KEY (maThuoc) REFERENCES Thuoc(maThuoc),
	FOREIGN KEY (maDonGia) REFERENCES DonGiaThuoc(maDonGia),
	FOREIGN KEY (maLoThuoc) REFERENCES LoThuoc(maLoThuoc)
)


-- Bảng HoaDon
CREATE TABLE HoaDon (
    maHD VARCHAR(20) NOT NULL PRIMARY KEY,
    maKhachHang VARCHAR(20),
    maNhanVien VARCHAR(20) NOT NULL,
    maThue VARCHAR(20),
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
    maHD VARCHAR(20) NOT NULL,
    soHieuThuoc VARCHAR(20) NOT NULL,
	maThuoc VARCHAR(20) NOT NULL,
    donViTinh NVARCHAR(20),
    soLuong INT NOT NULL,
    thanhTien FLOAT(10),
    PRIMARY KEY (maHD, soHieuThuoc),
    FOREIGN KEY (maHD) REFERENCES HoaDon(maHD),
    FOREIGN KEY (soHieuThuoc) REFERENCES ChiTietLoThuoc(soHieuThuoc)
);

-- Bảng DoiTra
CREATE TABLE PhieuDoiTra (
	maPhieu VARCHAR(20) NOT NULL PRIMARY KEY,
	maNV VARCHAR(20) NOT NULL,
	loai bit NOT NULL,
    maHD VARCHAR(20) NOT NULL,
	lyDo VARCHAR(255),
	ngayDoiTra DATE NOT NULL,
	FOREIGN KEY (maHD) REFERENCES HoaDon(maHD),
	FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);
-- Bảng DonDatThuoc
CREATE TABLE DonDatThuoc (
    maDon VARCHAR(20) NOT NULL PRIMARY KEY,
    maKhachHang VARCHAR(20) NOT NULL,
    maNhanVien VARCHAR(20),
    thoiGianDat DATE,
    tongTien FLOAT(10),
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKH),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNV)
);

-- Bảng ChiTietDonDatThuoc
CREATE TABLE ChiTietDonDatThuoc (
    maDon VARCHAR(20) NOT NULL,
	maThuoc VARCHAR(20) NOT NULL,
    soHieuThuoc VARCHAR(20) NOT NULL,
    donViTinh NVARCHAR(20),
    soLuong INT NOT NULL,
    thanhTien FLOAT(10),
    PRIMARY KEY (maDon, soHieuThuoc),
    FOREIGN KEY (maDon) REFERENCES DonDatThuoc(maDon),
	FOREIGN KEY (maThuoc) REFERENCES Thuoc(maThuoc),
    FOREIGN KEY (soHieuThuoc) REFERENCES ChiTietLoThuoc(soHieuThuoc)
);



-- Bảng ChuongTrinhKhuyenMai
CREATE TABLE ChuongTrinhKhuyenMai (
    maCTKM VARCHAR(20) NOT NULL PRIMARY KEY,
    mota NVARCHAR(255),
    loaiKhuyenMai NVARCHAR(50),
    ngayBatDau DATE,
    ngayKetThuc DATE
);

-- Bảng ChiTietKhuyenMai
CREATE TABLE ChiTietKhuyenMai (
    maCTKM VARCHAR(20) NOT NULL,
    soHieuThuoc VARCHAR(20) NOT NULL,
	maThuoc VARCHAR(20) NOT NULL,
    tyLeKhuyenMai FLOAT(10),
    soLuongToiThieu INT,
    PRIMARY KEY (maCTKM, soHieuThuoc),
    FOREIGN KEY (maCTKM) REFERENCES ChuongTrinhKhuyenMai(maCTKM),
    FOREIGN KEY (soHieuThuoc) REFERENCES ChiTietLoThuoc(soHieuThuoc),
	FOREIGN KEY (maThuoc) REFERENCES Thuoc(maThuoc)
);

-- Bảng ChiTietPhieuDoiTra
CREATE TABLE ChiTietPhieuDoiTra (
	maPhieu VARCHAR(20) NOT NULL,
	soHieuThuoc VARCHAR(20) NOT NULL,
	maThuoc VARCHAR(20) NOT NULL,
	ghiChu NVARCHAR(255),
	PRIMARY KEY (maPhieu, soHieuThuoc),
	FOREIGN KEY (maPhieu) REFERENCES PhieuDoiTra(maPhieu),
    FOREIGN KEY (soHieuThuoc) REFERENCES ChiTietLoThuoc(soHieuThuoc),
	FOREIGN KEY (maThuoc) REFERENCES Thuoc(maThuoc)
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
('QL002', N'Võ', N'Thị Dung', null , '1985-12-11', 789123456, N'654 Cong Quynh, TP.HCM', 0, 2, 1),
('Admin1', N'Đặng', N'Gia Bão', null , '2004-06-09', 0123456789, N'101 Le Loi, TP.HCM', 1, 0, 1)

-- Bảng TaiKhoan
INSERT INTO TaiKhoan (taiKhoan, matKhau)
VALUES
('NV001', 'a665a45920422f9d'),
('NV002', 'a665a45920422f9d'),
('NV003', 'a665a45920422f9d'),
('QL001', 'a665a45920422f9d'),
('QL002', 'a665a45920422f9d'),
('Admin1', '6b86b273ff34fce1')

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

-- Bảng DonGiaThuoc
INSERT INTO DonGiaThuoc
VALUES
('DG001', 'T001', N'Hộp', 50000),
('DG002', 'T002', N'Hộp', 35000),
('DG003', 'T003', N'Hộp', 150000),
('DG004', 'T004', N'Hộp', 75000),
('DG005', 'T005', N'Viên', 5000),
('DG006', 'T005', N'Hộp', 40000)

INSERT INTO DonGiaThuoc
VALUES
('DG007', 'T006', N'Hộp', 50000),
('DG008', 'T007', N'Hộp', 35000),
('DG009', 'T008', N'Hộp', 150000),
('DG010', 'T009', N'Hộp', 75000),
('DG011', 'T010', N'Viên', 5000)


-- Bảng Thuoc
INSERT INTO Thuoc (maThuoc, tenThuoc, maKe, tongSoLuong, maDanhMuc, maNhaCungCap, maNhaSanXuat, maNuocSanXuat, trangThai, hinhAnh)
VALUES
('T001', N'Paracetamol','K01', 50, 'DM001', 'NCC001', 'NHSX001', 'US', 1, 'images\\sample.png'),
('T002', N'Aspirin','K01', 40, 'DM001', 'NCC001', 'NHSX003', 'CN', 1, 'images\\sample.png'),
('T003', N'Amoxicillin','K02', 50, 'DM003', 'NCC002', 'NHSX002', 'RU', 1, 'images\\sample.png'),
('T004', N'Ibuprofen','K03', 50, 'DM001', 'NCC003', 'NHSX001', 'EN', 1, 'images\\sample.png'),
('T005', N'Vitamin C','K03', 320, 'DM002', 'NCC002', 'NHSX003', 'US', 1, 'images\\sample.png')

INSERT INTO Thuoc (maThuoc, tenThuoc, maKe, tongSoLuong, maDanhMuc, maNhaCungCap, maNhaSanXuat, maNuocSanXuat, trangThai, hinhAnh)
VALUES
('T006', N'Paracetamol','K01', 50, 'DM001', 'NCC001', 'NHSX001', 'US', 1, 'images\\sample.png'),
('T007', N'Aspirin','K01', 40, 'DM001', 'NCC001', 'NHSX003', 'CN', 1, 'images\\sample.png'),
('T008', N'Amoxicillin','K02', 50, 'DM003', 'NCC002', 'NHSX002', 'RU', 1, 'images\\sample.png'),
('T009', N'Ibuprofen','K03', 50, 'DM001', 'NCC003', 'NHSX001', 'EN', 1, 'images\\sample.png'),
('T010', N'Vitamin C','K03', 320, 'DM002', 'NCC002', 'NHSX003', 'US', 1, 'images\\sample.png')


INSERT INTO PhieuNhapThuoc(maPhieuNhap, maNhanVien, ngayLapPhieu, maNhaCungCap, tongTien)
VALUES
('PN001', 'NV001', '2024-10-10', 'NCC001', 3000000),
('PN002', 'NV002', '2024-10-11', 'NCC002', 4000000),
('PN003', 'NV003', '2024-10-12', 'NCC003', 5000000)

INSERT INTO ChiTietPhieuNhap(maPhieuNhap, maThuoc, donViTinh, ngaySanXuat, HSD, donGiaNhap, soLuongNhap, thanhTien)
VALUES
('PN001', 'T001', 'Hộp', '2024-1-1', '2025-12-1', 40000, 50, 2000000),
('PN001', 'T002', 'Hộp', '2024-1-1', '2025-7-1', 25000, 40, 1000000),
('PN002', 'T004', 'Hộp', '2024-1-1', '2025-8-1', 50000, 50, 2500000),
('PN002', 'T005', 'Hộp', '2024-1-1', '2026-1-1', 30000, 20, 600000),
('PN002', 'T005', 'Viên', '2024-1-1', '2025-12-1', 3000, 300, 900000),
('PN003', 'T003', 'Hộp', '2024-1-1', '2026-3-1', 100000, 50, 5000000)


INSERT INTO LoThuoc(maLoThuoc, maPhieuNhap, ngayNhap, tongTien)
VALUES
('LO001', 'PN001', '2024-10-10', 3000000),
('LO002', 'PN002', '2024-10-11', 4000000),
('LO003', 'PN003', '2024-10-12', 5000000)

INSERT INTO ChiTietLoThuoc(soHieuThuoc, maThuoc, maLoThuoc, soLuongCon, maDonGia, ngaySX, HSD)
VALUES
('SH001', 'T001', 'LO001', 50, 'DG001', '2024-1-1', '2025-12-1'),
('SH002', 'T002', 'LO001', 40, 'DG002', '2024-1-1', '2025-7-1'),
('SH003', 'T003', 'LO003', 50, 'DG003', '2024-1-1', '2025-8-1'),
('SH004', 'T004', 'LO002', 50, 'DG004', '2024-1-1', '2026-3-1'),
('SH005', 'T005', 'LO002', 300, 'DG005', '2024-1-1', '2025-12-1'),
('SH006', 'T005', 'LO002', 20, 'DG006', '2024-1-1', '2026-1-1')

INSERT INTO ChiTietLoThuoc(soHieuThuoc, maThuoc, maLoThuoc, soLuongCon, maDonGia, ngaySX, HSD)
VALUES
('SH007', 'T006', 'LO001', 50, 'DG007', '2024-1-1', '2025-12-1'),
('SH008', 'T007', 'LO001', 40, 'DG008', '2024-1-1', '2025-12-1'),
('SH009', 'T008', 'LO003', 50, 'DG009', '2024-1-1', '2025-12-1'),
('SH0010', 'T009', 'LO002', 50, 'DG010', '2024-1-1', '2025-12-1'),
('SH0011', 'T010', 'LO002', 300, 'DG011', '2024-1-1', '2025-12-1')

INSERT INTO ChiTietLoThuoc(soHieuThuoc, maThuoc, maLoThuoc, soLuongCon, maDonGia, ngaySX, HSD)
VALUES
('SH0012', 'T006', 'LO001', 5, 'DG007', '2022-1-1', '2024-12-1'),
('SH0013', 'T007', 'LO001', 4, 'DG008', '2022-1-1', '2024-12-1'),
('SH0014', 'T008', 'LO003', 5, 'DG009', '2022-1-1', '2024-12-1'),
('SH0015', 'T009', 'LO002', 5, 'DG010', '2022-1-1', '2024-12-1'),
('SH0016', 'T010', 'LO002', 30, 'DG011', '2022-1-1', '2024-12-1')


-- Bảng HoaDon
INSERT INTO HoaDon (maHD, maKhachHang, maNhanVien, maThue, ngayLap, hinhThucThanhToan, tongTien, trangThai)
VALUES
('HD001', 'KH001', 'NV001', 'THUE001', '2024-09-01', N'Tiền mặt', 187000, 1),
('HD002', 'KH002', 'NV002', 'THUE001', '2024-09-02', N'Tiền mặt', 357500, 1)


-- Bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon (maHD, soHieuThuoc, maThuoc, donViTinh, soLuong, thanhTien)
VALUES
('HD001', 'SH001', 'T001', N'Hộp', 1, 100000),
('HD001', 'SH002', 'T002', N'Hộp', 2, 35000),
('HD002', 'SH003', 'T003', N'Hộp', 1, 150000),
('HD002', 'SH004', 'T004', N'Hộp', 1, 75000),
('HD002', 'SH005', 'T005', N'Viên', 5, 100000)


INSERT INTO HoaDon (maHD, maKhachHang, maNhanVien, maThue, ngayLap, hinhThucThanhToan, tongTien, trangThai)
VALUES
('HD003', 'KH001', 'NV001', 'THUE001', '2024-08-01', N'Tiền mặt', 187000, 1),
('HD004', 'KH002', 'NV002', 'THUE001', '2024-07-02', N'Tiền mặt', 357500, 1)


-- Bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon (maHD, soHieuThuoc, maThuoc, donViTinh, soLuong, thanhTien)
VALUES
('HD003', 'SH001', 'T001', N'Hộp', 1, 100000),
('HD003', 'SH002', 'T002', N'Hộp', 2, 35000),
('HD004', 'SH003', 'T003', N'Hộp', 1, 150000),
('HD004', 'SH004', 'T004', N'Hộp', 1, 75000),
('HD004', 'SH005', 'T005', N'Viên', 5, 100000)


INSERT INTO HoaDon (maHD, maKhachHang, maNhanVien, maThue, ngayLap, hinhThucThanhToan, tongTien, trangThai)
VALUES
('HD005', 'KH001', 'NV001', 'THUE001', '2024-09-01', N'Tiền mặt', 187000, 1),
('HD006', 'KH002', 'NV002', 'THUE001', '2024-09-02', N'Tiền mặt', 357500, 1)


-- Bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon (maHD, soHieuThuoc, maThuoc, donViTinh, soLuong, thanhTien)
VALUES
('HD005', 'SH001', 'T001', N'Hộp', 1, 100000),
('HD005', 'SH002', 'T002', N'Hộp', 2, 35000),
('HD006', 'SH003', 'T003', N'Hộp', 1, 150000),
('HD006', 'SH004', 'T004', N'Hộp', 1, 75000),
('HD006', 'SH005', 'T005', N'Viên', 5, 100000)


---- Bảng DonDatThuoc
--INSERT INTO DonDatThuoc (maDon, maKhachHang, maNhanVien, thoiGianDat, tongTien)
--VALUES
--('MD001', 'KH001', 'NV001', '2024-09-01', 85000),
--('MD002', 'KH002', 'NV002', '2024-09-02', 300000),
--('MD003', 'KH003', 'NV003', '2024-09-03', 250000)

---- Bảng ChiTietDonDatThuoc
--INSERT INTO ChiTietDonDatThuoc (maDon, soHieuThuoc, maThuoc, donViTinh, soLuong, thanhTien)
--VALUES
--('MD001', 'SH001', 'T001', N'Hộp', 1, 50000),
--('MD001', 'SH002', 'T002', N'Hộp', 1, 35000),
--('MD002', 'SH003', 'T003', N'Hộp', 2, 300000),
--('MD003', 'SH004', 'T004', N'Hộp', 2, 75000),
--('MD003', 'SH005', 'T005', N'Viên', 20, 100000)

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
('KM001', 'SH001', 'T001', 0.4, 5),
('KM002', 'SH002', 'T002', 0.1, 5),
('KM003', 'SH003', 'T003', 0.6, 3),
('KM004', 'SH004', 'T004', 0.2, 5),
('KM005', 'SH005', 'T005', 0.5, 4);
GO




------------- PROCEDURE

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

-- lấy danh sách chức vụ
CREATE PROCEDURE getAllChucVu
AS
BEGIN
	SELECT * FROM ChucVu
END
GO

-- lấy tài khoản theo mã nhân viên
CREATE PROCEDURE getTaiKhoanByMaNV @maNV VARCHAR(10)
AS
BEGIN
	SELECT *
	FROM TaiKhoan tk
	JOIN NhanVien nv ON tk.taiKhoan = nv.maNV
	WHERE nv.maNV = @maNV
END
GO


-- lấy danh sách chi tiết hóa đơn theo mã hóa đơn
CREATE PROCEDURE getDSChiTietHD @maHD VARCHAR(10)
AS
BEGIN
	SELECT *
	FROM ChiTietHoaDon cthd
	JOIN Thuoc t ON cthd.maThuoc = t.maThuoc
	JOIN DonGiaThuoc dgt ON t.maThuoc = dgt.maThuoc
	WHERE maHD = @maHD
END
GO

-- lấy thành tiền khi biết mã hóa đơn và mã thuốc
CREATE PROCEDURE getThanhTienByMHDVaMaThuoc @maHD VARCHAR(10), @maThuoc VARCHAR(10)
AS
BEGIN
	SELECT thanhTien
	FROM ChiTietHoaDon
	WHERE maHD = @maHD AND maThuoc = @maThuoc
END
GO


-- lấy thuốc khi biết mã thuốc
CREATE PROCEDURE getThuocByMaThuoc @maThuoc VARCHAR(10)
AS
BEGIN
	SELECT *
	FROM Thuoc
	WHERE maThuoc = @maThuoc
END
GO


-- danh sach doanh thu các tháng trong năm
CREATE PROCEDURE getDoanhThuThangTrongNam @nam INT
AS
BEGIN
	SELECT MONTH(hd.ngayLap) AS thang, doanhThu = SUM(hd.tongTien)
	FROM HoaDon hd
	WHERE YEAR(hd.ngayLap) = @nam
	GROUP BY MONTH(hd.ngayLap)
END
GO



-- danh sach doanh thu các tháng trong tháng
CREATE PROCEDURE getDoanhThuCacNgayTrongThang @nam INT, @thang INT
AS
BEGIN
	SELECT DAY(hd.ngayLap) AS ngay, doanhThu = SUM(hd.tongTien)
	FROM HoaDon hd
	WHERE YEAR(hd.ngayLap) = @nam AND MONTH(hd.ngayLap) = @thang
	GROUP BY DAY(hd.ngayLap)
	ORDER BY DAY(hd.ngayLap);
END
GO


-- lấy doanh thu các ngày trong tuần
CREATE PROCEDURE getDoanhThuCacNgayTrongTuan
    @nam INT,
    @thang INT,
    @tuan INT
AS
BEGIN
    -- tính ngày đầu tiên của tháng
    DECLARE @firstDayOfMonth DATE = DATEFROMPARTS(@nam, @thang, 1);

    -- tính ngày đầu tiên của tuần trong tháng
    DECLARE @firstDayOfWeek DATE = DATEADD(DAY, (1 - DATEPART(WEEKDAY, @firstDayOfMonth) + 7 * (@tuan - 1)), @firstDayOfMonth);

    -- tính ngày cuối cùng của tuần
    DECLARE @lastDayOfWeek DATE = DATEADD(DAY, 6, @firstDayOfWeek);

    -- lọc doanh thu cho các ngày trong tuần và tháng cụ thể
    SELECT
        DATENAME(WEEKDAY, hd.ngayLap) AS Ngay,
        SUM(hd.tongTien) AS DoanhThu
    FROM
        HoaDon hd
    WHERE
        hd.ngayLap >= @firstDayOfWeek AND hd.ngayLap <= @lastDayOfWeek
        AND MONTH(hd.ngayLap) = @thang
        AND YEAR(hd.ngayLap) = @nam
    GROUP BY
        DATENAME(WEEKDAY, hd.ngayLap)
    ORDER BY
        CASE DATENAME(WEEKDAY, hd.ngayLap)
            WHEN N'Thứ hai' THEN 1
            WHEN N'Thứ ba' THEN 2
            WHEN N'Thứ tư' THEN 3
            WHEN N'Thứ năm' THEN 4
            WHEN N'Thứ sáu' THEN 5
            WHEN N'Thứ bảy' THEN 6
            WHEN N'Chủ nhật' THEN 7
        END;
END
GO


-- lấy danh sách hóa đơn theo năm
CREATE PROCEDURE getDanhSachHoaDonByYear @nam INT
AS
BEGIN
	SELECT *
	FROM HoaDon hd
	WHERE YEAR(hd.ngayLap) = @nam
END
GO


-- lấy danh sách hóa đơn theo năm
CREATE PROCEDURE getDanhSachHoaDonTheoThangTrongNam @nam INT, @thang INT
AS
BEGIN
    SELECT *
    FROM HoaDon hd
    WHERE YEAR(hd.ngayLap) = @nam AND MONTH(hd.ngayLap) = @thang
END
GO


-- lấy danh sách hóa đơn theo năm
CREATE PROCEDURE getDanhSachHoaDonTheoTuanCuaThangTrongNam @nam INT, @thang INT, @tuan INT
AS
BEGIN
    -- tính ngày bắt đầu và ngày kết thúc của tuần
    DECLARE @ngayBatDau DATE, @ngayKetThuc DATE

    -- tìm ngày đầu tiên của tháng
    SET @ngayBatDau = DATEADD(WEEK, @tuan - 1, DATEFROMPARTS(@nam, @thang, 1))

    -- tìm ngày cuối cùng của tuần (thêm 6 ngày để hoàn thành tuần)
    SET @ngayKetThuc = DATEADD(DAY, 6, @ngayBatDau)

    -- lấy danh sách hóa đơn theo tuần
    SELECT *
    FROM HoaDon hd
    WHERE hd.ngayLap BETWEEN @ngayBatDau AND @ngayKetThuc
END
GO


-- trung bình doanh thu cho năm
CREATE PROCEDURE getTrungBinhDoanhThuTheoNam @nam INT
AS
BEGIN
	SELECT MONTH(hd.ngayLap) AS thang, trungBinhDoanhThu = AVG(hd.tongTien)
	FROM HoaDon hd
	WHERE YEAR(hd.ngayLap) = @nam
	GROUP BY MONTH(hd.ngayLap)
END
GO


-- trung bình doanh thu theo các ngày trong tháng
CREATE PROCEDURE getTrungBinhDoanhThuCacNgayTrongThang @nam INT, @thang INT
AS
BEGIN
	SELECT DAY(hd.ngayLap) AS ngay, doanhThu = AVG(hd.tongTien)
	FROM HoaDon hd
	WHERE YEAR(hd.ngayLap) = @nam AND MONTH(hd.ngayLap) = @thang
	GROUP BY DAY(hd.ngayLap)
	ORDER BY DAY(hd.ngayLap);
END
GO


-- trung bình doanh thu các ngày trong tuần
CREATE PROCEDURE getTrungBinhDoanhThuCacNgayTrongTuan
    @nam INT,
    @thang INT,
    @tuan INT
AS
BEGIN
    -- tính ngày đầu tiên của tháng
    DECLARE @firstDayOfMonth DATE = DATEFROMPARTS(@nam, @thang, 1);

    -- tính ngày đầu tiên của tuần trong tháng
    DECLARE @firstDayOfWeek DATE = DATEADD(DAY, (1 - DATEPART(WEEKDAY, @firstDayOfMonth) + 7 * (@tuan - 1)), @firstDayOfMonth);

    -- tính ngày cuối cùng của tuần
    DECLARE @lastDayOfWeek DATE = DATEADD(DAY, 6, @firstDayOfWeek);

    -- lọc doanh thu cho các ngày trong tuần và tháng cụ thể
    SELECT
        DATENAME(WEEKDAY, hd.ngayLap) AS Ngay,
        AVG(hd.tongTien) AS DoanhThu
    FROM
        HoaDon hd
    WHERE
        hd.ngayLap >= @firstDayOfWeek AND hd.ngayLap <= @lastDayOfWeek
        AND MONTH(hd.ngayLap) = @thang
        AND YEAR(hd.ngayLap) = @nam
    GROUP BY
        DATENAME(WEEKDAY, hd.ngayLap)
    ORDER BY
        CASE DATENAME(WEEKDAY, hd.ngayLap)
            WHEN N'Thứ hai' THEN 1
            WHEN N'Thứ ba' THEN 2
            WHEN N'Thứ tư' THEN 3
            WHEN N'Thứ năm' THEN 4
            WHEN N'Thứ sáu' THEN 5
            WHEN N'Thứ bảy' THEN 6
            WHEN N'Chủ nhật' THEN 7
        END;
END
GO


-- lấy doanh thu của nhân viên trong tháng và năm hiện tại
CREATE PROCEDURE getDoanhThuTheoNgayTrongThangHienTai @maNV VARCHAR(10)
AS
BEGIN
    DECLARE @nam INT = YEAR(GETDATE());
    DECLARE @thang INT = MONTH(GETDATE());

    SELECT
        DAY(hd.ngayLap) AS Ngay,
        SUM(hd.tongTien) AS DoanhThu
    FROM
        HoaDon hd
    WHERE
		hd.maNhanVien = @maNV
        AND YEAR(hd.ngayLap) = @nam
        AND MONTH(hd.ngayLap) = @thang
    GROUP BY
        DAY(hd.ngayLap)
    ORDER BY
        DAY(hd.ngayLap);
END
GO



-- lấy danh sách thuốc theo tên danh mục
CREATE PROCEDURE getDSThuocTheoTenDM @tenDM NVARCHAR(50)
AS
BEGIN
	SELECT *
	FROM Thuoc t
	JOIN DanhMuc dm ON t.maDanhMuc = dm.maDanhMuc
	WHERE dm.tenDanhMuc = @tenDM
END
GO


-- tìm kiếm thuốc theo mã thuốc, theo tên
CREATE PROCEDURE TimKiemThuocTheoKyTuTenVaMaThuoc
    @kyTu NVARCHAR(50)
AS
BEGIN
    SELECT *
    FROM Thuoc
    WHERE (tenThuoc LIKE '%' + @kyTu + '%') OR (maThuoc LIKE '%' + @kyTu + '%');
END;
GO


-- lấy các đơn vị tính và giá của thuốc
CREATE PROCEDURE layDonGiaThuocTheoMaThuoc @maThuoc VARCHAR(10)
AS
BEGIN
	SELECT *
	FROM DonGiaThuoc 
	WHERE maThuoc = @maThuoc
END
GO


-- lấy giá bán thuốc theo mã thuốc và đơn vị
CREATE PROCEDURE layGiaThuocTheoMaVaDV @maThuoc VARCHAR(10), @donViTinh NVARCHAR(50)
AS
BEGIN
	SELECT donGia
	FROM DonGiaThuoc
	WHERE maThuoc = @maThuoc AND donViTinh = @donViTinh
END
GO


-- xóa nhân viên (ẩn nhân viên theo trạng thái)
CREATE PROCEDURE xoaNhanVienTheoTrangThaiVaMaNV @maNV VARCHAR(10)
AS 
BEGIN
	DECLARE @trangThai BIT

	IF EXISTS (SELECT 1 FROM NhanVien WHERE maNV = @maNV)
	BEGIN
		SELECT @trangThai = trangThai
		FROM NhanVien
		WHERE maNV = @maNV

		IF @trangThai = 1
		BEGIN
			UPDATE NhanVien
			SET trangThai = 0
			WHERE maNV = @maNV
		END
		ELSE
		BEGIN
			PRINT 'Trạng thái hiện tại đã là 0';
		END
	END
	ELSE
	BEGIN
		PRINT 'Không tìm thấy nhân viên với mã này';
	END
END
GO


-- cập nhật nhân viên
CREATE PROCEDURE capNhatThongTinNhanVien
    @maNV VARCHAR(10),
    @hoNV NVARCHAR(10),
    @tenNV NVARCHAR(50),
    @ngaySinh DATE,
    @SDT VARCHAR(15),
    @email VARCHAR(50),
    @diaChi NVARCHAR(255),
    @gioiTinh BIT,
    @vaiTro SMALLINT,
    @trangThai BIT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM NhanVien WHERE maNV = @maNV)
    BEGIN
        UPDATE NhanVien
        SET 
            hoNV = @hoNV,
            tenNV = @tenNV,
            ngaySinh = @ngaySinh,
            SDT = @SDT,
            email = @email,
            diaChi = @diaChi,
            gioiTinh = @gioiTinh,
            vaiTro = @vaiTro,
            trangThai = @trangThai
        WHERE maNV = @maNV;
    END
    ELSE
    BEGIN
        PRINT 'Không tìm thấy nhân viên với mã này';
    END
END
GO


-- tìm kiếm nhân viên theo mã nhân viên, tên nhân viên, số điện thoại, email
CREATE PROCEDURE timKiemNhanVienTheoKyTu
    @kyTu NVARCHAR(50)
AS
BEGIN
    SELECT *
    FROM NhanVien nv
	JOIN ChucVu cv ON nv.vaiTro = cv.maChucVu
    WHERE (tenNV LIKE '%' + @kyTu + '%') OR (maNV LIKE '%' + @kyTu + '%')
			OR (SDT LIKE '%' + @kyTu + '%') OR (email LIKE '%' + @kyTu + '%');
END;
GO


-- lấy danh sách thuốc theo nhà cung cấp
CREATE PROCEDURE getDSThuocTheoNhaCC @tenNCC NVARCHAR(50)
AS
BEGIN
	SELECT DISTINCT t.*, ncc.*
	FROM Thuoc t
	JOIN NhaCungCap ncc ON t.maNhaCungCap = ncc.maNCC
	WHERE ncc.tenNCC = @tenNCC
END
GO 


-- lấy danh sách khuyến mãi
CREATE PROCEDURE getDSKhuyenMai
AS
BEGIN
	SELECT *
	FROM ChuongTrinhKhuyenMai
END
GO


-- lấy danh sách chi tiết khuyến mãi và thuốc
CREATE PROCEDURE getDSCTKhuyenMai
AS
BEGIN
	SELECT *
	FROM Thuoc t
	JOIN DonGiaThuoc dg ON t.maThuoc = dg.maThuoc
	LEFT JOIN ChiTietKhuyenMai ctkm ON t.maThuoc = ctkm.maThuoc
	LEFT JOIN ChiTietLoThuoc ctlt ON ctkm.soHieuThuoc = ctlt.soHieuThuoc
	LEFT JOIN ChuongTrinhKhuyenMai ct ON ctkm.maCTKM = ct.maCTKM
	ORDER BY ctkm.tyLeKhuyenMai DESC
END
GO


-- cập nhật khuyến mãi
CREATE PROCEDURE capNhatKhuyenMai
	@maCTKM VARCHAR(10),
    @mota NVARCHAR(255),
    @loaiKhuyenMai NVARCHAR(50),
    @ngayBatDau DATE,
    @ngayKetThuc DATE
AS
BEGIN
	IF EXISTS (SELECT 1 FROM ChuongTrinhKhuyenMai WHERE maCTKM = @maCTKM)
    BEGIN
        UPDATE ChuongTrinhKhuyenMai
        SET 
           mota = @mota,
		   loaiKhuyenMai = @loaiKhuyenMai,
		   ngayBatDau = @ngayBatDau,
		   ngayKetThuc = @ngayKetThuc
        WHERE maCTKM = @maCTKM;
    END
    ELSE
    BEGIN
        PRINT 'Không tìm thấy khuyến mãi với mã này';
    END
END
GO


-- tìm kiếm khuyến mãi theo loại khuyến mãi, mã khuyến mãi, mô tả
CREATE PROCEDURE timKiemKhuyenMaiTheoKyTu
    @kyTu NVARCHAR(255)
AS
BEGIN
    SELECT *
    FROM ChuongTrinhKhuyenMai 
    WHERE (maCTKM LIKE '%' + @kyTu + '%') OR (loaiKhuyenMai LIKE '%' + @kyTu + '%')
			OR (mota LIKE '%' + @kyTu + '%')
END;
GO


-- tìm kiếm chức vụ theo mã chức vụ, tên chức vụ 
CREATE PROCEDURE timKiemChucVuTheoKyTu
    @kyTu NVARCHAR(50)
AS
BEGIN
    SELECT *
    FROM ChucVu 
    WHERE (maChucVu LIKE '%' + @kyTu + '%') OR (tenChucVu LIKE '%' + @kyTu + '%')
END;
GO

-- cập nhật chức vụ
CREATE PROCEDURE capNhatChucVu
	@maChucVu SMALLINT,
    @tenChucVu NVARCHAR(50)
AS
BEGIN
	IF EXISTS (SELECT 1 FROM ChucVu WHERE maChucVu = @maChucVu)
    BEGIN
        UPDATE ChucVu
        SET 
           tenChucVu = @tenChucVu
        WHERE maChucVu = @maChucVu;
    END
    ELSE
    BEGIN
        PRINT 'Không tìm thấy chức vụ với mã này';
    END
END
GO


-- tìm kiếm nhà sản xuất theo mã và tên
CREATE PROCEDURE timKiemNhaSXTheoKyTu
    @kyTu NVARCHAR(50)
AS
BEGIN
    SELECT *
    FROM NhaSanXuat 
    WHERE (maNhaSX LIKE '%' + @kyTu + '%') OR (tenNhaSX LIKE '%' + @kyTu + '%')
END;
GO


-- cập nhật nhà sản xuất
CREATE PROCEDURE capNhatNhaSX
	@maNhaSX VARCHAR(15),
    @tenNhaSX NVARCHAR(50),
	@diaChi NVARCHAR(255)
AS
BEGIN
	IF EXISTS (SELECT 1 FROM NhaSanXuat WHERE maNhaSX = @maNhaSX)
    BEGIN
        UPDATE NhaSanXuat
        SET 
           tenNhaSX = @tenNhaSX,
		   diaChi = @diaChi
        WHERE maNhaSX = @maNhaSX;
    END
    ELSE
    BEGIN
        PRINT 'Không tìm thấy nhà sản xuất với mã này';
    END
END
GO



-- tìm kiếm danh mục theo mã và tên
CREATE PROCEDURE timKiemDanhMucTheoKyTu
    @kyTu NVARCHAR(50)
AS
BEGIN
    SELECT *
    FROM DanhMuc 
    WHERE (maDanhMuc LIKE '%' + @kyTu + '%') OR (tenDanhMuc LIKE '%' + @kyTu + '%')
END;
GO


-- cập nhật danh mục
CREATE PROCEDURE capNhatDM
	@maDM VARCHAR(10),
    @tenDM NVARCHAR(50)
AS
BEGIN
	IF EXISTS (SELECT 1 FROM DanhMuc WHERE maDanhMuc = @maDM)
    BEGIN
        UPDATE DanhMuc
        SET 
           tenDanhMuc = @tenDM
        WHERE maDanhMuc = @maDM;
    END
    ELSE
    BEGIN
        PRINT 'Không tìm thấy danh mục với mã này';
    END
END
GO


-- tìm kiếm nước sản xuất theo mã và tên
CREATE PROCEDURE timKiemNuocSXTheoKyTu
    @kyTu NVARCHAR(50)
AS
BEGIN
    SELECT *
    FROM NuocSanXuat 
    WHERE (maNuoc LIKE '%' + @kyTu + '%') OR (tenNuoc LIKE '%' + @kyTu + '%')
END;
GO


-- cập nhật nước sản xuất
CREATE PROCEDURE capNhatNuocSX
	@maNuoc VARCHAR(10),
    @tenNuoc NVARCHAR(50)
AS
BEGIN
	IF EXISTS (SELECT 1 FROM NuocSanXuat WHERE maNuoc = @maNuoc)
    BEGIN
        UPDATE NuocSanXuat
        SET 
           tenNuoc = @tenNuoc
        WHERE maNuoc = @maNuoc;
    END
    ELSE
    BEGIN
        PRINT 'Không tìm thấy nước sản xuất với mã này';
    END
END
GO


-- tìm kiếm nhà cung cấp  theo mã, tên. email
CREATE PROCEDURE timKiemNhaCCTheoKyTu
    @kyTu NVARCHAR(50)
AS
BEGIN
    SELECT *
    FROM NhaCungCap 
    WHERE (maNCC LIKE '%' + @kyTu + '%') OR (tenNCC LIKE '%' + @kyTu + '%')
		OR (email LIKE '%' + @kyTu + '%')
END;
GO


-- cập nhật nhà cung cấp
CREATE PROCEDURE capNhatNhaCC
	@maNCC VARCHAR(10),
    @tenNCC NVARCHAR(50),
	@email VARCHAR(50),
	@diaChi NVARCHAR(255)
AS
BEGIN
	IF EXISTS (SELECT 1 FROM NhaCungCap WHERE maNCC = @maNCC)
    BEGIN
        UPDATE NhaCungCap
        SET 
           tenNCC = @tenNCC,
		   diaChi = @diaChi,
		   email = @email
        WHERE maNCC = @maNCC;
    END
    ELSE
    BEGIN
        PRINT 'Không tìm thấy nhà cung cấp với mã này';
    END
END
GO


-- tìm kiếm khách hàng theo số điện thoại
CREATE PROCEDURE timKiemKhachHangTheoSDT
    @kyTu NVARCHAR(15)
AS
BEGIN
    SELECT *
    FROM KhachHang kh
	JOIN DiemTichLuy dtl ON kh.maDTL = dtl.maDTL
    WHERE (SDT LIKE '%' + @kyTu + '%')
END;
GO


-- cập nhật đơn đặt thuốc
CREATE PROCEDURE capNhatDonDatThuoc
	@maDon VARCHAR(10),
    @maKhachHang NVARCHAR(10),
	@maNhanVien VARCHAR(10),
	@thoiGianDat DATE,
	@tongTien FLOAT(10)
AS
BEGIN
	IF EXISTS (SELECT 1 FROM DonDatThuoc WHERE maDon = @maDon)
    BEGIN
        UPDATE DonDatThuoc
        SET 
			maKhachHang = @maKhachHang,
			maNhanVien = @maNhanVien,
			thoiGianDat = @thoiGianDat,
			tongTien = @tongTien
        WHERE maDon = @maDon;
    END
    ELSE
    BEGIN
        PRINT 'Không tìm thấy đơn đặt thuốc với mã này';
    END
END
GO


-- xóa các chi tiết
CREATE PROCEDURE capNhatTatCaChiTietDonDatThuoc
    @maDon VARCHAR(10)
AS
BEGIN
    DELETE FROM ChiTietDonDatThuoc WHERE maDon = @maDon;
END
GO


-- lấy chi tiết lô thuốc theo mã đơn giá
CREATE PROCEDURE getCTLoThuocTheoMaDGVaMaThuoc
	@maDG VARCHAR(20),
	@maThuoc VARCHAR(20)
AS 
BEGIN
	SELECT * 
	FROM ChiTietLoThuoc lt 
	JOIN DonGiaThuoc dg ON lt.maDonGia = dg.maDonGia
	WHERE dg.maDonGia = @maDG AND lt.maThuoc = @maThuoc
END
GO


-- lấy DS chi tiết phiếu nhập theo mã phiếu nhập
CREATE PROCEDURE getDSCTPNTheoMaPN @maPhieuNhap VARCHAR(20)
AS
BEGIN
	SELECT *
	FROM ChiTietPhieuNhap
	WHERE maPhieuNhap = @maPhieuNhap
END
GO


-- --------- TRIGGER
-- cập nhật điểm tích lũy sau khi thanh toán
CREATE TRIGGER trg_CapNhatDiemTichLuy
ON HoaDon
AFTER INSERT
AS
BEGIN
    -- Kiểm tra nếu hóa đơn có trạng thái đã thanh toán và có mã khách hàng
    IF EXISTS (
        SELECT 1
        FROM inserted
        WHERE trangThai = 1 AND maKhachHang IS NOT NULL
    )
    BEGIN
        UPDATE DiemTichLuy
        SET diemTong = diemTong + (hd.tongTien * 0.01),
            diemHienTai = diemHienTai + (hd.tongTien * 0.01)
        FROM DiemTichLuy dtl
        INNER JOIN KhachHang kh ON dtl.maDTL = kh.maDTL
        INNER JOIN inserted hd ON kh.maKH = hd.maKhachHang
        WHERE hd.maKhachHang IS NOT NULL AND kh.maDTL IS NOT NULL;

        -- Cập nhật xếp hạng dựa trên điểm tích lũy tổng
        UPDATE DiemTichLuy
        SET xepHang = CASE
            WHEN diemTong < 30000 THEN N'Đồng'
            WHEN diemTong < 50000 THEN N'Bạc'
            WHEN diemTong < 100000 THEN N'Vàng'
            WHEN diemTong < 300000 THEN N'Bạch kim'
            WHEN diemTong >= 500000 THEN N'Kim cương'
            ELSE xepHang -- Giữ nguyên hạng nếu không thuộc các điều kiện trên
        END
        FROM DiemTichLuy dtl
        INNER JOIN KhachHang kh ON dtl.maDTL = kh.maDTL
        INNER JOIN inserted hd ON kh.maKH = hd.maKhachHang
        WHERE hd.maKhachHang IS NOT NULL AND kh.maDTL IS NOT NULL;
    END
END;
GO


---- cập nhật số lượng thuốc sau khi thanh toán thành công
--CREATE TRIGGER trg_UpdateSoLuongThuoc
--ON HoaDon
--AFTER INSERT
--AS
--BEGIN
--    IF EXISTS (
--        SELECT 1 
--        FROM inserted i
--        WHERE i.trangThai = 1
--    )
--    BEGIN
--        UPDATE t
--        SET t.soLuongCon = t.soLuongCon - cthd.soLuong
--        FROM Thuoc t
--        INNER JOIN ChiTietHoaDon cthd ON t.soHieuThuoc = cthd.soHieuThuoc AND t.maThuoc = cthd.maThuoc
--        INNER JOIN HoaDon h ON h.maHD = cthd.maHD
--        INNER JOIN inserted i ON h.maHD = i.maHD
--        WHERE i.trangThai = 1 
--          AND t.soLuongCon >= cthd.soLuong; 
        
--        IF @@ROWCOUNT = 0
--        BEGIN
--            PRINT 'Không có bản ghi nào được cập nhật.';
--        END
--        ELSE
--        BEGIN
--            PRINT 'Số lượng thuốc đã được cập nhật thành công.';
--        END
--    END
--    ELSE
--    BEGIN
--        PRINT 'Hóa đơn không có trạng thái đã thanh toán.';
--    END
--END;
--GO




