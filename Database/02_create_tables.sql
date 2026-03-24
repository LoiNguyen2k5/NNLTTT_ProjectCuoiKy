USE cosmetics_management;

-- 1. Bảng Loại mỹ phẩm (categories)
CREATE TABLE categories (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT uk_categories_name UNIQUE (name)
) ENGINE=InnoDB;

-- 2. Bảng Thương hiệu (brands)
CREATE TABLE brands (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT uk_brands_name UNIQUE (name)
) ENGINE=InnoDB;

-- 3. Bảng Nhà cung cấp (suppliers)
CREATE TABLE suppliers (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    PRIMARY KEY (id),
    CONSTRAINT uk_suppliers_name UNIQUE (name)
) ENGINE=InnoDB;

-- 4. Bảng Sản phẩm mỹ phẩm (products)
CREATE TABLE products (
    id BIGINT NOT NULL AUTO_INCREMENT,
    barcode VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    quantity INT NOT NULL DEFAULT 0,
    category_id BIGINT NOT NULL,
    brand_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_products_barcode UNIQUE (barcode),
    CONSTRAINT ck_products_quantity CHECK (quantity >= 0),
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_products_brand FOREIGN KEY (brand_id) REFERENCES brands(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;
CREATE INDEX idx_products_name ON products(name);

-- 5. Bảng Khách hàng (customers)
CREATE TABLE customers (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_code VARCHAR(20) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    gender VARCHAR(10),
    phone VARCHAR(20),
    tier VARCHAR(20) NOT NULL DEFAULT 'MEMBER',
    points INT NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (id),
    CONSTRAINT uk_customers_code UNIQUE (customer_code),
    CONSTRAINT uk_customers_phone UNIQUE (phone),
    CONSTRAINT ck_customers_gender CHECK (gender IN ('MALE', 'FEMALE', 'OTHER') OR gender IS NULL),
    CONSTRAINT ck_customers_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'BLOCKED'))
) ENGINE=InnoDB;

-- 6. Bảng Nhân viên (staffs)
CREATE TABLE staffs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    staff_code VARCHAR(20) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'STAFF',
    PRIMARY KEY (id),
    CONSTRAINT uk_staffs_code UNIQUE (staff_code),
    CONSTRAINT uk_staffs_username UNIQUE (username),
    CONSTRAINT ck_staffs_role CHECK (role IN ('ADMIN', 'STAFF'))
) ENGINE=InnoDB;

-- 7. Bảng Phiếu nhập kho (import_receipts)
CREATE TABLE import_receipts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    receipt_code VARCHAR(20) NOT NULL,
    supplier_id BIGINT NOT NULL,
    staff_id BIGINT NOT NULL,
    import_date DATE NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (id),
    CONSTRAINT uk_imports_code UNIQUE (receipt_code),
    CONSTRAINT fk_imports_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_imports_staff FOREIGN KEY (staff_id) REFERENCES staffs(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

-- 8. Chi tiết phiếu nhập kho (import_receipt_details)
CREATE TABLE import_receipt_details (
    id BIGINT NOT NULL AUTO_INCREMENT,
    receipt_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    import_price DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ck_import_details_quantity CHECK (quantity > 0),
    CONSTRAINT fk_import_details_receipt FOREIGN KEY (receipt_id) REFERENCES import_receipts(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_import_details_product FOREIGN KEY (product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

-- 9. Bảng Hóa đơn bán hàng (invoices)
CREATE TABLE invoices (
    id BIGINT NOT NULL AUTO_INCREMENT,
    invoice_code VARCHAR(20) NOT NULL,
    customer_id BIGINT,
    staff_id BIGINT NOT NULL,
    invoice_date DATETIME NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (id),
    CONSTRAINT uk_invoices_code UNIQUE (invoice_code),
    CONSTRAINT fk_invoices_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_invoices_staff FOREIGN KEY (staff_id) REFERENCES staffs(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

-- 10. Chi tiết hóa đơn bán hàng (invoice_details)
CREATE TABLE invoice_details (
    id BIGINT NOT NULL AUTO_INCREMENT,
    invoice_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ck_invoice_details_quantity CHECK (quantity > 0),
    CONSTRAINT fk_invoice_details_invoice FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_invoice_details_product FOREIGN KEY (product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;