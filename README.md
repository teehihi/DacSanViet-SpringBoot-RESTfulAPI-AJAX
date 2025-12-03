# 🍜 Đặc Sản Việt - Spring Boot Project

## 📌 Giới thiệu

Hệ thống quản lý đặc sản truyền thống Việt Nam được xây dựng bằng **Spring Boot + Thymeleaf + Spring Security + JPA Hibernate**.

Dự án cung cấp các chức năng:
- ✅ Quản lý người dùng (User Management)
- ✅ Quản lý danh mục đặc sản (Category Management)
- ✅ Quản lý video giới thiệu (Video Management)
- ✅ Phân quyền Admin/User với Spring Security
- ✅ Upload và quản lý hình ảnh
- ✅ Giao diện admin hiện đại với Thymeleaf

---

## 📂 Cấu trúc thư mục

```plaintext
src/main/java/LapTrinhWeb/SpringBoot/
├── config/              # Cấu hình Spring Security, Password Encoder, Web Config
├── controller/          # Controllers xử lý request
├── entity/             # Entity classes (User, Category, Video)
├── model/              # Model classes (DTO)
├── repository/         # JPA Repositories
├── service/            # Business logic services
└── SpringBootWithThymeLeafApplication.java

src/main/resources/
├── static/
│   ├── css/           # CSS files
│   ├── images/        # Static images
│   └── js/            # JavaScript files
├── templates/
│   ├── admin/         # Admin pages (dashboard, users, categories, videos)
│   ├── auth/          # Authentication pages (login, register)
│   ├── layouts/       # Layout templates
│   └── index.html     # Home page
└── application.properties

uploads/                # Uploaded images (auto-created)
```

---

## 🚀 Hướng dẫn sử dụng

### 1. Cài đặt và chạy dự án

**Clone project từ GitHub:**
```bash
git clone https://github.com/teehihi/DacSanVietSpringBoot.git
cd DacSanVietSpringBoot
```

**Cấu hình database trong `src/main/resources/application.properties`:**
```properties
server.port=8088

# Database Configuration
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=QuanLySVSpringDB;trustServerCertificate=true;encrypt=true;
spring.datasource.username=sa
spring.datasource.password=YourPassword

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

⚠️ **Lưu ý:** Đảm bảo đã tạo database `QuanLySVSpringDB` trong SQL Server trước khi chạy.

**Chạy dự án:**
```bash
mvn spring-boot:run
```

Hoặc sử dụng **Spring Tool Suite (STS)**:
1. Mở Boot Dashboard
2. Chuột phải vào project → Chọn **(Re)start**
3. Truy cập: `http://localhost:8088`

---

## 🔐 Cấu hình tài khoản Admin

### Cách 1: Sử dụng Utility Endpoint (Khuyến nghị)

Truy cập endpoint sau để hash password cho user:
```
http://localhost:8088/utility/hash-password/{username}/{plainPassword}
```

**Ví dụ:**
```
http://localhost:8088/utility/hash-password/admin/admin123
```

### Cách 2: Đăng ký và cập nhật qua Database

1. **Đăng ký tài khoản mới** qua trang `/register`
2. **Kết nối SQL Server** và chạy:
```sql
UPDATE users SET admin = 1 WHERE username = 'your_username';
```

### Cách 3: Tạo user admin trực tiếp trong Database

```sql
INSERT INTO users (username, password, fullname, email, phone, admin, active, images)
VALUES (
    'admin',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', -- password: admin123
    'Administrator',
    'admin@example.com',
    '0123456789',
    1, -- admin = true
    1, -- active = true
    NULL
);
```

**Thông tin đăng nhập:**
- Username: `admin`
- Password: `admin123`

---

## 🎨 Tính năng nổi bật

### 🔒 Bảo mật
- ✅ Password được mã hóa bằng **BCrypt**
- ✅ Phân quyền với **Spring Security**
- ✅ CSRF protection
- ✅ Session management

### 📸 Quản lý File
- ✅ Upload ảnh với **Multipart**
- ✅ Lưu trữ file ngoài project (không cần restart)
- ✅ Hỗ trợ URL và file upload
- ✅ Tự động tạo thư mục `uploads/`

### 🎯 Giao diện
- ✅ Admin dashboard hiện đại
- ✅ Responsive design
- ✅ Dark theme với gradient đẹp
- ✅ Modal xác nhận tùy chỉnh
- ✅ Alert messages đẹp mắt
- ✅ Font chữ đẹp (Playfair Display + Inter)

### 📊 Dashboard
- ✅ Thống kê real-time (Users, Categories, Videos)
- ✅ Biểu đồ Chart.js
- ✅ Hoạt động gần đây
- ✅ Thao tác nhanh

---

## 🌐 Các trang chính

| Trang | URL | Mô tả |
|-------|-----|-------|
| Trang chủ | `/` | Trang chủ giới thiệu đặc sản |
| Đăng nhập | `/login` | Đăng nhập hệ thống |
| Đăng ký | `/register` | Đăng ký tài khoản mới |
| Dashboard | `/admin/dashboard` | Tổng quan hệ thống |
| Quản lý Users | `/admin/users` | CRUD người dùng |
| Quản lý Categories | `/admin/categories` | CRUD danh mục |
| Quản lý Videos | `/admin/videos` | CRUD video |

---

## 🛠️ Công nghệ sử dụng

- **Backend:** Spring Boot 3.5.8
- **Template Engine:** Thymeleaf
- **Security:** Spring Security 6
- **Database:** SQL Server
- **ORM:** JPA Hibernate
- **Build Tool:** Maven
- **Frontend:** Bootstrap 5.3.3, Font Awesome 6.5.1, Chart.js 4.4.0
- **Fonts:** Google Fonts (Playfair Display, Inter)

---

## 📸 Screenshots

### Trang chủ
![Trang Chủ](image-4.png)

### Admin Dashboard
![Admin Dashboard](image-2.png)
![Admin Dashboard 2](image-3.png)

*Dashboard với thống kê real-time và biểu đồ*

### Quản lý Users
![Quản lý User](image-5.png)

*Giao diện quản lý người dùng với search và pagination*

---

## 🔧 Cấu hình nâng cao

### Upload File Configuration

Ảnh được lưu vào thư mục `uploads/` ở root project:
```
project-root/
├── uploads/           # Ảnh upload (tự động tạo)
│   ├── abc-123.jpg
│   └── xyz-456.png
└── src/
```

Truy cập ảnh qua URL: `/uploads/{filename}`

### Database Schema

Hệ thống tự động tạo bảng với `spring.jpa.hibernate.ddl-auto=update`:
- `users` - Thông tin người dùng
- `categories` - Danh mục đặc sản
- `videos` - Video giới thiệu

---

## 🐛 Troubleshooting

### Lỗi kết nối database
```
Kiểm tra:
- SQL Server đã chạy chưa?
- Database QuanLySVSpringDB đã tạo chưa?
- Username/password đúng chưa?
- Port 1433 có bị block không?
```

### Ảnh không hiển thị
```
Kiểm tra:
- Thư mục uploads/ đã được tạo chưa?
- WebConfig đã cấu hình đúng chưa?
- Đường dẫn ảnh trong DB có đúng không?
```

### Không đăng nhập được
```
Kiểm tra:
- Password đã được hash chưa?
- User có active = 1 không?
- Có lỗi trong console không?
```

---

## 📝 TODO

- [ ] Thêm chức năng tìm kiếm nâng cao
- [ ] Export dữ liệu ra Excel/PDF
- [ ] Thêm email notification
- [ ] Tích hợp payment gateway
- [ ] Mobile app với React Native
- [ ] API documentation với Swagger

---

## 🧑‍💻 Tác giả

**Nguyễn Nhật Thiên (TEE)**

- 📧 Email: teeforwork21@gmail.com
- 🔗 GitHub: [github.com/teehihi](https://github.com/teehihi)
- 🌐 Linktree: [linktr.ee/nkqt.tee](https://linktr.ee/nkqt.tee)

---

## 📄 License

Dự án này được phát triển cho mục đích học tập tại **Trường Đại học Công Nghệ Thông Tin - ĐHQG TP.HCM**.

---

## 🙏 Acknowledgments

- Spring Boot Documentation
- Thymeleaf Documentation
- Bootstrap Team
- Font Awesome
- Chart.js Team

---

**⭐ Nếu thấy project hữu ích, hãy cho một star nhé! ⭐**
