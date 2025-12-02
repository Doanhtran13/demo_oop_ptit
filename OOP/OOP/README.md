# Ứng dụng Quản lý Quán Cafe - Java Swing

Ứng dụng Java Swing đơn giản để quản lý quán cafe với 2 loại tài khoản: Khách hàng và Admin.
Link báo cáo: https://drive.google.com/file/d/1M4AsFWIqUaCvnBN4EKqlRGJQUsZevsC5/view?usp=sharing

## Mô tả

Ứng dụng cho phép:
- **Khách hàng**: Đăng ký, đăng nhập, xem menu, thêm món vào giỏ hàng, đặt hàng, xem lịch sử đơn hàng
- **Admin**: Quản lý menu (thêm/sửa/xóa món), xem tất cả đơn hàng, xem thống kê doanh thu

## Cấu trúc dự án

```
src/coffeeshop/
├── Main.java                 # Điểm khởi đầu ứng dụng
├── LoginScreen.java          # Màn hình đăng nhập
├── RegisterScreen.java       # Màn hình đăng ký
├── CustomerMenuScreen.java   # Màn hình menu khách hàng
├── AdminMenuScreen.java      # Màn hình quản lý admin
├── FileDatabase.java         # Xử lý đọc/ghi file
├── User.java                 # Class quản lý người dùng
├── MenuItemSimple.java       # Class quản lý món ăn
├── OrderSimple.java          # Class quản lý đơn hàng
└── OrderItemSimple.java      # Class quản lý chi tiết đơn hàng
```

## Cách chạy chương trình

### Yêu cầu
- Java JDK 8 trở lên
- Không cần thư viện ngoài, chỉ dùng core Java

### Cách chạy

**Cách 1: Sử dụng javac và java**
```bash
# Biên dịch
javac -d build/classes src/coffeeshop/*.java

# Chạy ứng dụng
java -cp build/classes coffeeshop.Main
```

**Cách 2: Sử dụng IDE (NetBeans, IntelliJ, Eclipse)**
1. Mở dự án trong IDE
2. Chạy file `Main.java`

## Dữ liệu lưu trữ

Ứng dụng lưu dữ liệu vào các file text (CSV format) trong thư mục gốc:

- **users.txt**: Danh sách người dùng (username,password,role)
- **menu.txt**: Danh sách món ăn (id,name,price)
- **orders.txt**: Danh sách đơn hàng

Các file này sẽ tự động được tạo khi chạy lần đầu.

## Tài khoản mặc định

Khi chạy lần đầu, hệ thống tự động tạo tài khoản admin:
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: `ADMIN`

## Chức năng

### Khách hàng

1. **Đăng ký**: Tạo tài khoản mới (role: CUSTOMER)
2. **Đăng nhập**: Đăng nhập bằng username và password
3. **Xem menu**: Xem danh sách tất cả món ăn với giá
4. **Giỏ hàng**: 
   - Thêm món vào giỏ hàng
   - Xóa món khỏi giỏ hàng
   - Xem tổng tiền
5. **Đặt hàng**: Xác nhận và lưu đơn hàng
6. **Lịch sử đơn hàng**: Xem tất cả đơn hàng đã đặt

### Admin

1. **Quản lý Menu**:
   - Thêm món mới
   - Sửa thông tin món (tên, giá)
   - Xóa món
   - Làm mới danh sách

2. **Quản lý Đơn hàng**:
   - Xem tất cả đơn hàng
   - Xem chi tiết từng đơn hàng
   - Làm mới danh sách

3. **Thống kê**:
   - Tổng số đơn hàng
   - Tổng doanh thu

## Công nghệ sử dụng
- **Java Swing**: Giao diện người dùng
- **Java I/O**: Đọc/ghi file text
- **Core Java**: Không sử dụng thư viện ngoài

## Lưu ý

- Mật khẩu được lưu dạng text (không mã hóa) để đơn giản
- Dữ liệu lưu dạng CSV trong file text
- Ứng dụng đơn giản, phù hợp cho mục đích học tập
