# TÓM TẮT DỰ ÁN SPRING BOOT WITH THYMELEAF

## Thông Tin Sinh Viên
- **Họ và tên**: Nguyễn Nhật Thiên
- **MSSV**: 23110153

## Tổng Quan Dự Án
Dự án Spring Boot sử dụng Thymeleaf để xây dựng hệ thống quản lý CRUD cho 3 bảng: Category, User, và Video với đầy đủ chức năng tìm kiếm và phân trang.

## Cấu Trúc Dự Án Hoàn Chỉnh

### 1. Backend (Lấy từ BaiTap5SpringBoot)

#### Entity (3 files)
- ✅ Category.java - Quản lý danh mục
- ✅ User.java - Quản lý người dùng
- ✅ Video.java - Quản lý video

#### Repository (3 files)
- ✅ CategoryRepository.java - CRUD + tìm kiếm Category
- ✅ UserRepository.java - CRUD + tìm kiếm User
- ✅ VideoRepository.java - CRUD + tìm kiếm Video

#### Service (7 files)
- ✅ CategoryService.java + CategoryServiceImpl.java
- ✅ UserService.java + UserServiceImpl.java
- ✅ VideoService.java + VideoServiceImpl.java
- ✅ FileStorageService.java - Upload/Delete files

#### Model (3 files)
- ✅ CategoryModel.java - DTO cho Category
- ✅ UserModel.java - DTO cho User
- ✅ VideoModel.java - DTO cho Video

#### Controller (4 files)
- ✅ HomeController.java - Trang chủ
- ✅ CategoryController.java - CRUD Categories
- ✅ UserController.java - CRUD Users
- ✅ VideoController.java - CRUD Videos

### 2. Frontend (Thymeleaf Templates)

#### Layout & Fragments
- ✅ layout-admin.html - Layout chính sử dụng Thymeleaf Layout Dialect
- ✅ fragments/header.html - Header với ảnh profile
- ✅ fragments/nav.html - Navigation menu
- ✅ fragments/footer.html - Footer với thông tin sinh viên

#### Templates cho Category
- ✅ categories/list.html - Danh sách + tìm kiếm + phân trang
- ✅ categories/addOrEdit.html - Form thêm/sửa

#### Templates cho User
- ✅ users/list.html - Danh sách + tìm kiếm + phân trang
- ✅ users/addOrEdit.html - Form thêm/sửa

#### Templates cho Video
- ✅ videos/list.html - Danh sách + tìm kiếm + phân trang
- ✅ videos/addOrEdit.html - Form thêm/sửa

#### Trang Chủ
- ✅ index.html - Trang chủ với menu điều hướng

### 3. Static Resources
- ✅ css/bootstrap.css - Bootstrap 5 CSS
- ✅ js/bootstrap.js - Bootstrap 5 JavaScript
- ✅ images/ - Thư mục chứa ảnh
- ✅ images/uploads/ - Thư mục upload (tự động tạo)

### 4. Configuration
- ✅ application.properties - Cấu hình database, Thymeleaf, upload
- ✅ pom.xml - Dependencies (Thymeleaf, JPA, SQL Server, Bootstrap)

### 5. Documentation
- ✅ README.md - Hướng dẫn tổng quan (English)
- ✅ HUONG_DAN_SU_DUNG.md - Hướng dẫn chi tiết (Vietnamese)
- ✅ HUONG_DAN_DAT_ANH.txt - Hướng dẫn đặt ảnh profile
- ✅ TOM_TAT_DU_AN.md - File này

## Quan Hệ Database
```
User (1) ──────> (N) Category
                      │
                      │ (1)
                      │
                      ↓
                    (N) Video
```

## Chức Năng Đã Hoàn Thành

### Category Management
- ✅ Xem danh sách categories
- ✅ Tìm kiếm theo tên category
- ✅ Phân trang danh sách
- ✅ Thêm mới category
- ✅ Sửa category
- ✅ Xóa category
- ✅ Upload ảnh cho category
- ✅ Gán user cho category

### User Management
- ✅ Xem danh sách users
- ✅ Tìm kiếm theo tên đầy đủ
- ✅ Phân trang danh sách
- ✅ Thêm mới user
- ✅ Sửa user
- ✅ Xóa user
- ✅ Upload avatar cho user
- ✅ Quản lý quyền admin
- ✅ Quản lý trạng thái active

### Video Management
- ✅ Xem danh sách videos
- ✅ Tìm kiếm theo tiêu đề
- ✅ Phân trang danh sách
- ✅ Thêm mới video
- ✅ Sửa video
- ✅ Xóa video
- ✅ Upload poster cho video
- ✅ Gán category cho video
- ✅ Quản lý lượt xem

## Giao Diện

### Bố Cục (Layout)
```
┌─────────────────────────────────────┐
│          HEADER                     │
│  [Profile Image]                    │
│  Quản Lý Hệ Thống                   │
├─────────────────────────────────────┤
│          NAVIGATION                 │
│  Home | Categories | Users | Videos │
├─────────────────────────────────────┤
│                                     │
│          CONTENT                    │
│  (Dynamic content from pages)       │
│                                     │
├─────────────────────────────────────┤
│          FOOTER                     │
│  Họ và tên: Nguyễn Nhật Thiên      │
│  MSSV: 23110153                     │
└─────────────────────────────────────┘
```

### Đặc Điểm Giao Diện
- ✅ Responsive design với Bootstrap 5
- ✅ Form validation
- ✅ Alert messages (success/error)
- ✅ Pagination controls
- ✅ Search functionality
- ✅ Image preview
- ✅ Confirm dialogs cho delete

## Công Nghệ Sử Dụng

### Backend
- Spring Boot 3.5.8
- Spring Data JPA
- Spring Web MVC
- Lombok

### Frontend
- Thymeleaf Template Engine
- Thymeleaf Layout Dialect
- Bootstrap 5
- HTML5/CSS3

### Database
- SQL Server
- Hibernate ORM

### Build Tool
- Maven

## Cách Chạy Dự Án

### Bước 1: Chuẩn Bị
```bash
# Đảm bảo Java 21 đã cài đặt
java -version

# Đảm bảo SQL Server đang chạy
# Tạo database: QuanLySVSpringDB
```

### Bước 2: Cấu Hình
```bash
# Kiểm tra application.properties
# Cập nhật username/password nếu cần
```

### Bước 3: Thêm Ảnh Profile
```bash
# Copy ảnh của bạn vào:
# src/main/resources/static/images/profile.jpg
```

### Bước 4: Chạy Ứng Dụng
```bash
cd SpringBootWithThymeLeaf
./mvnw spring-boot:run
```

### Bước 5: Truy Cập
```
http://localhost:8088/
```

## URLs Quan Trọng

| Module     | URL                              |
|------------|----------------------------------|
| Home       | http://localhost:8088/           |
| Categories | http://localhost:8088/admin/categories |
| Users      | http://localhost:8088/admin/users |
| Videos     | http://localhost:8088/admin/videos |

## Tính Năng Nổi Bật

1. **Thymeleaf Layout Dialect**: Sử dụng layout decorator để tái sử dụng code
2. **Fragment Templates**: Header, Nav, Footer được tách riêng
3. **File Upload**: Hỗ trợ upload ảnh với validation
4. **Search & Pagination**: Tìm kiếm và phân trang cho tất cả modules
5. **Responsive Design**: Giao diện tương thích mọi thiết bị
6. **Flash Messages**: Thông báo success/error sau mỗi action
7. **Confirm Dialogs**: Xác nhận trước khi xóa

## Lưu Ý Quan Trọng

1. **Database**: Đảm bảo SQL Server đang chạy và database đã được tạo
2. **Profile Image**: Đặt ảnh profile.jpg vào thư mục static/images
3. **Upload Folder**: Thư mục uploads sẽ tự động được tạo khi upload file đầu tiên
4. **Port**: Ứng dụng chạy trên port 8088 (có thể thay đổi trong application.properties)
5. **Auto DDL**: Hibernate sẽ tự động tạo/cập nhật bảng (ddl-auto=update)

## Kết Luận

Dự án đã hoàn thành đầy đủ các yêu cầu:
- ✅ CRUD cho 3 bảng (Category, User, Video)
- ✅ Tìm kiếm và phân trang
- ✅ Sử dụng Thymeleaf và Thymeleaf Layout Dialect
- ✅ Bố cục trang với Header (ảnh profile), Content, Footer (thông tin sinh viên)
- ✅ Backend lấy từ BaiTap5SpringBoot
- ✅ Giao diện Bootstrap 5 responsive

---
**Sinh viên thực hiện**: Nguyễn Nhật Thiên - MSSV: 23110153
