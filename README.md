# 🛍️ COSMETIC MANAGEMENT SYSTEM - Đồ án Quản lý Cửa hàng Mỹ phẩm

Đây là dự án desktop quản lý cửa hàng mỹ phẩm được xây dựng trên nền tảng Java Swing, mô phỏng quy trình quản lý kho, bán hàng và nhân sự với các vai trò quản lý (Admin) và nhân viên bán hàng (Staff) riêng biệt.

![Java](https://img.shields.io/badge/Java-17_|_21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/UI-Java_Swing-007396?style=flat-square&logo=java&logoColor=white)
![Hibernate](https://img.shields.io/badge/ORM-Hibernate_/_JPA-59666C?style=flat-square&logo=Hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/Database-MySQL_8-4479A1?style=flat-square&logo=mysql&logoColor=white)

---

## 📚 Mục Lục

* [✨ Tính năng Nổi bật](#-tính-năng-nổi-bật)
  * [Nhân viên bán hàng (Staff)](#nhân-viên-bán-hàng-staff)
  * [Quản trị viên (Admin)](#quản-trị-viên-admin)
  * [🌟 Tính năng Nâng cao](#-tính-năng-nâng-cao)
* [💻 Công nghệ sử dụng](#-công-nghệ-sử-dụng)
* [⚙️ Bắt đầu (Cài đặt)](#️-bắt-đầu-cài-đặt)
  * [Yêu cầu](#yêu-cầu)
  * [Cài đặt](#cài-đặt)
* [🔑 Tài khoản mẫu](#-tài-khoản-mẫu)

---

## ✨ Tính năng Nổi bật

Dự án được xây dựng với 2 vai trò chính (Admin, Staff), mỗi vai trò có một bộ chức năng chuyên biệt để vận hành cửa hàng mỹ phẩm.

### Nhân viên bán hàng (Staff)
* **Xác thực:** Đăng nhập hệ thống bằng tài khoản được cấp.
* **Nghiệp vụ Bán hàng:**
  * Lập hóa đơn: Chọn khách hàng, thêm mỹ phẩm vào giỏ hàng, tính tổng tiền.
  * Tồn kho tự động: Giảm số lượng mỹ phẩm trong kho ngay khi xuất hóa đơn thành công.
* **Quản lý danh mục:**
  * Xem, tìm kiếm và lọc danh sách sản phẩm mỹ phẩm hiện có.
* **Chăm sóc Khách hàng:**
  * Quản lý thông tin khách hàng, lưu trữ lịch sử mua hàng.

### Quản trị viên (Admin)
Kế thừa toàn bộ quyền hạn của Staff, đồng thời bổ sung các quyền quản lý cấp cao:
* **Quản lý Nhân sự:** Thêm, sửa, xóa tài khoản và phân quyền cho nhân viên.
* **Kiểm soát Hệ thống:**
  * Quản lý chuyên sâu danh mục Sản phẩm, Thương hiệu, Nhà cung cấp.
* **Dashboard & Thống kê:**
  * Xem tổng quan doanh thu, số lượng đơn hàng bán ra.
  * Thống kê top các sản phẩm bán chạy nhất trong tháng.

### 🌟 Tính năng Nâng cao
Hệ thống được tích hợp thêm các tính năng nâng cao nhằm tối ưu hóa trải nghiệm người dùng:
* **Tích Lũy Điểm Khách Hàng (Loyalty Points):** Hệ thống thông minh tự động cộng điểm cho người mua (1 điểm = 100.000đ) và cho phép dùng điểm để giảm giá trực tiếp vào các lần mua sau.
* **Quản Lý Hạn Sử Dụng (Expiration Date):** Hệ thống tự động kiểm tra chuyên sâu HSD của hóa mỹ phẩm. Hiển thị Bảng cảnh báo đỏ tại Nhựa điều khiển (Dashboard) và Bật Popup báo động tức thì chặn thu ngân bán hàng hết date.
* **Tìm Kiếm & Bộ Lọc Đa Năng:** Giao diện tích hợp thanh tìm kiếm đồng bộ trên mọi ngóc ngách của phần mềm (Sản phẩm, Khách hàng, Thương hiệu, Nhà cung cấp).
* **Giao diện Tùy biến (Dark Mode):** Hỗ trợ bật/tắt chế độ Sáng/Tối linh hoạt, giúp bảo vệ mắt và mang lại giao diện hiện đại thông qua thư viện FlatLaf.
* **Tích hợp Email:** Hệ thống tự động gửi thông báo qua Email (Gửi thẳng file Hóa Đơn điện tử đuôi PDF cho khách hàng sau khi thanh toán).
* **Trích xuất Dữ liệu (Export):** Cho phép xuất hóa đơn mua hàng và lưu các loại báo cáo doanh thu ra định dạng **PDF** và **Excel**.
* **Biểu đồ Trực quan:** Ứng dụng JFreeChart để vẽ trực tiếp biểu đồ minh họa Top Doanh Thu cực kỳ trực quan.

---

## 💻 Công nghệ sử dụng

* **Ngôn ngữ:** Java (JDK 17 hoặc 21).
* **Kiến trúc:** MVC (Model - View - Controller).
* **Giao diện:** Java Swing (có sử dụng thư viện FlatLaf cho Dark/Light mode).
* **Quản lý dependencies:** Maven.
* **Cơ sở dữ liệu:** MySQL 8.
* **ORM:** Hibernate Core 7.3.0 / Jakarta Persistence API 3.2.0.
* **Thư viện hỗ trợ:** JavaMail (Gửi email), iText/Apache POI (Xuất PDF/Excel).

---

## ⚙️ Bắt đầu (Cài đặt)

### Yêu cầu
* Môi trường: JDK 17+ cài đặt sẵn trên máy.
* IDE: VS Code (khuyên dùng) hoặc IntelliJ IDEA, Eclipse.
* Database: MySQL Workbench.

### Cài đặt

**1. Khởi tạo Cơ sở dữ liệu:**
Bạn cần chạy lần lượt các script SQL có sẵn trong thư mục `database/` theo thứ tự sau:
* `database/01_create_database.sql`
* `database/02_create_tables.sql`
* `database/03_insert_sample_data.sql`

**2. Cấu hình ứng dụng:**
Mở file cấu hình tại `src/main/resources/META-INF/persistence.xml` và sửa lại thông tin đăng nhập MySQL của máy bạn:
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/cosmetics_management?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="MAT_KHAU_CUA_BAN"/>


**3. Chạy dự án:**
* Mở project trong IDE.
* Đợi Maven tải xong các thư viện.
* Chạy file `AppLauncher.java` để khởi động ứng dụng.

---

## 🔑 Tài khoản mẫu

Bạn có thể sử dụng các tài khoản sau để test hệ thống ngay lập tức:

| Vai trò | Username | Password |
| :--- | :--- | :--- |
| **Admin** | `admin` | `123456` |
| **Staff** | `staff1` | `123456` |
| **Staff** | `staff2` | `123456` |