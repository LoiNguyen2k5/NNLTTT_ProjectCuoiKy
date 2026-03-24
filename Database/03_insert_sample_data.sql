USE cosmetics_management;

-- Dữ liệu mẫu: Danh mục (categories)
INSERT INTO categories (name, description) VALUES
('Skincare', 'Sản phẩm chăm sóc da mặt'),
('Makeup', 'Sản phẩm trang điểm'),
('Bodycare', 'Chăm sóc cơ thể'),
('Haircare', 'Chăm sóc tóc'),
('Fragrance', 'Nước hoa và xịt thơm');

-- Dữ liệu mẫu: Thương hiệu (brands)
INSERT INTO brands (name, description) VALUES
('MAC', 'Thương hiệu trang điểm chuyên nghiệp'),
('La Roche-Posay', 'Dược mỹ phẩm chăm sóc da'),
('L''Oréal', 'Thương hiệu mỹ phẩm quốc dân'),
('Innisfree', 'Mỹ phẩm thiên nhiên Hàn Quốc'),
('The Ordinary', 'Sản phẩm chăm sóc da đặc trị'),
('Dior', 'Thương hiệu mỹ phẩm cao cấp');

-- Dữ liệu mẫu: Nhà cung cấp (suppliers)
INSERT INTO suppliers (name, address, phone) VALUES
('Công ty TNHH L''Oreal VN', 'Quận 1, TP.HCM', '0901234567'),
('Nhà phân phối AmorePacific', 'Quận 3, TP.HCM', '0912345678'),
('Công ty CP Mỹ phẩm Cao cấp', 'Quận Cầu Giấy, Hà Nội', '0987654321');

-- Dữ liệu mẫu: 15 Sản phẩm (products)
INSERT INTO products (barcode, name, price, quantity, category_id, brand_id) VALUES
('SP001', 'Son thỏi MAC Ruby Woo', 550000, 50, 2, 1),
('SP002', 'Kem nền MAC Studio Fix Fluid', 850000, 30, 2, 1),
('SP003', 'Kem chống nắng La Roche-Posay Anthelios', 450000, 100, 1, 2),
('SP004', 'Sữa rửa mặt La Roche-Posay Effaclar', 350000, 80, 1, 2),
('SP005', 'Nước tẩy trang L''Oréal Micellar', 150000, 150, 1, 3),
('SP006', 'Kem dưỡng tóc L''Oréal Elseve', 200000, 60, 4, 3),
('SP007', 'Mặt nạ đất sét Innisfree Volcanic', 280000, 70, 1, 4),
('SP008', 'Kem dưỡng ẩm Innisfree Green Tea', 320000, 90, 1, 4),
('SP009', 'Serum The Ordinary Niacinamide 10% + Zinc 1%', 250000, 120, 1, 5),
('SP010', 'Tẩy da chết The Ordinary AHA 30% + BHA 2%', 290000, 40, 1, 5),
('SP011', 'Nước hoa Dior Sauvage 100ml', 3500000, 15, 5, 6),
('SP012', 'Son dưỡng Dior Lip Glow', 800000, 45, 2, 6),
('SP013', 'Sữa tắm L''Oréal Men Expert', 180000, 65, 3, 3),
('SP014', 'Phấn phủ MAC Prep + Prime', 750000, 25, 2, 1),
('SP015', 'Kem dưỡng phục hồi La Roche-Posay B5', 380000, 110, 1, 2);

-- Dữ liệu mẫu: 15 Khách hàng (customers)
INSERT INTO customers (customer_code, full_name, gender, phone, tier, points, status) VALUES
('KH001', 'Nguyễn Thị Hoa', 'FEMALE', '0901111111', 'SILVER', 150, 'ACTIVE'),
('KH002', 'Trần Văn Nam', 'MALE', '0902222222', 'MEMBER', 50, 'ACTIVE'),
('KH003', 'Lê Ngọc Yến', 'FEMALE', '0903333333', 'GOLD', 500, 'ACTIVE'),
('KH004', 'Phạm Quỳnh Anh', 'FEMALE', '0904444444', 'VIP', 1200, 'ACTIVE'),
('KH005', 'Hoàng Bảo Sơn', 'MALE', '0905555555', 'MEMBER', 10, 'ACTIVE'),
('KH006', 'Đỗ Mai Linh', 'FEMALE', '0906666666', 'SILVER', 200, 'ACTIVE'),
('KH007', 'Vũ Hà Phương', 'FEMALE', '0907777777', 'MEMBER', 80, 'ACTIVE'),
('KH008', 'Ngô Quốc Khánh', 'MALE', '0908888888', 'GOLD', 650, 'ACTIVE'),
('KH009', 'Bùi Thanh Trúc', 'FEMALE', '0909999999', 'VIP', 1500, 'ACTIVE'),
('KH010', 'Lý Tấn Phát', 'MALE', '0910101010', 'SILVER', 250, 'BLOCKED'),
('KH011', 'Đặng Thùy Chi', 'FEMALE', '0911222333', 'MEMBER', 0, 'ACTIVE'),
('KH012', 'Vương Hải Đăng', 'MALE', '0912333444', 'MEMBER', 30, 'ACTIVE'),
('KH013', 'Mai Kim Ngân', 'FEMALE', '0913444555', 'GOLD', 800, 'ACTIVE'),
('KH014', 'Trịnh Yến Nhi', 'FEMALE', '0914555666', 'SILVER', 300, 'ACTIVE'),
('KH015', 'Đoàn Nhật Minh', 'MALE', '0915666777', 'MEMBER', 5, 'ACTIVE');

-- Dữ liệu mẫu: Nhân viên (staffs)
INSERT INTO staffs (staff_code, full_name, username, password, role) VALUES
('NV001', 'Quản trị viên', 'admin', '12345', 'ADMIN'),
('NV002', 'Nhân viên Bán hàng 1', 'staff1', '12345', 'STAFF'),
('NV003', 'Nhân viên Bán hàng 2', 'staff2', '12345', 'STAFF');

-- Dữ liệu mẫu: 2 Phiếu nhập kho (import_receipts)
INSERT INTO import_receipts (receipt_code, supplier_id, staff_id, import_date, total_amount) VALUES
('PN001', 1, 1, '2026-03-10', 5000000),
('PN002', 2, 1, '2026-03-15', 3000000);

-- Dữ liệu mẫu: Chi tiết phiếu nhập
INSERT INTO import_receipt_details (receipt_id, product_id, quantity, import_price) VALUES
(1, 1, 10, 350000),
(1, 2, 5, 500000),
(2, 3, 20, 250000);

-- Dữ liệu mẫu: 2 Hóa đơn bán hàng (invoices)
INSERT INTO invoices (invoice_code, customer_id, staff_id, invoice_date, total_amount) VALUES
('HD001', 1, 2, '2026-03-20 10:30:00', 1000000),
('HD002', 3, 3, '2026-03-21 14:15:00', 3500000);

-- Dữ liệu mẫu: Chi tiết hóa đơn
INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price) VALUES
(1, 1, 1, 550000),
(1, 3, 1, 450000),
(2, 11, 1, 3500000);