# Ứng dụng Đặt Sân Cầu Lông (Android)

Ứng dụng Android hỗ trợ **đặt sân cầu lông**, quản lý dịch vụ đi kèm và theo dõi lịch sử đặt sân, với hai nhóm chức năng chính cho **người dùng** và **quản trị viên**.

## 1. Mô tả nhanh

Dự án được phát triển bằng Java trên Android (View system + XML layout), tổ chức theo mô hình nhiều màn hình Activity/Fragment:

- Nhóm người dùng: đăng ký, đăng nhập, cập nhật thông tin, đặt sân, xem lịch sử.
- Nhóm quản trị: quản lý đơn đặt sân, khách hàng, dịch vụ, giải đấu.

## 2. Công nghệ sử dụng

- Ngôn ngữ: **Java**
- Build system: **Gradle (Kotlin DSL)**
- Android SDK:
- `compileSdk = 34`
- `targetSdk = 34`
- `minSdk = 24`
- UI: Android XML, Material Components, RecyclerView, ConstraintLayout

## 3. Chức năng chính

### Người dùng

- Đăng ký tài khoản, đăng nhập, quên mật khẩu.
- Xem danh sách dịch vụ/sân theo loại.
- Đặt sân và xem chi tiết đặt sân.
- Quản lý thông tin cá nhân.
- Xem lịch sử đặt sân, thông báo, phản hồi/hỗ trợ.

### Quản trị viên

- Quản lý booking sân theo trạng thái.
- Quản lý khách hàng (thêm/sửa/xem chi tiết).
- Quản lý dịch vụ (thêm/sửa/danh sách).
- Quản lý giải đấu (thêm/sửa/danh sách).
- Xem đánh giá/phản hồi.

## 4. Cấu trúc thư mục chính

```text
app/src/main/java/com/example/caulong
|- admin/        # Màn hình và adapter cho quản trị
|- booking/      # Luồng đặt sân, giỏ, chi tiết dịch vụ
|- entities/     # Model dữ liệu
|- menubottom/   # Fragment tab dưới (Home/History/Notification/Account...)
|- menuleft/     # Màn hình hỗ trợ, chia sẻ, feedback
|- user/         # Đăng nhập/đăng ký/thông tin tài khoản
|- data/         # Dữ liệu mẫu / lớp dữ liệu
```

Tài nguyên giao diện nằm tại `app/src/main/res` (layout, drawable, menu, anim, font...).

## 5. Yêu cầu môi trường

- Android Studio (khuyến nghị bản mới ổn định)
- JDK 8+ (dự án đang cấu hình Java 8)
- Android SDK Platform 34
- Thiết bị thật hoặc máy ảo Android từ API 24 trở lên

## 6. Hướng dẫn chạy dự án

### Cách 1: Android Studio

1. Mở Android Studio.
2. Chọn **Open** và trỏ đến thư mục dự án `caulong`.
3. Đợi Gradle sync hoàn tất.
4. Chọn thiết bị/emulator.
5. Nhấn **Run** để cài và chạy ứng dụng.

### Cách 2: Dòng lệnh (Windows)

```bash
gradlew.bat assembleDebug
```

File APK debug sau khi build thường nằm tại:

`app/build/outputs/apk/debug/`

## 7. Kiểm thử

- Unit test: `app/src/test/...`
- Instrumented test: `app/src/androidTest/...`

Chạy test bằng lệnh:

```bash
gradlew.bat test
gradlew.bat connectedAndroidTest
```

## 8. Ghi chú

- Hiện tại phiên bản ứng dụng trong cấu hình Gradle là `versionCode = 1`, `versionName = "1.0"`.
- Điểm vào ứng dụng (launcher activity): `IntroActivity`.

---

Nếu bạn muốn, mình có thể cập nhật tiếp README theo hướng:

- Thêm ảnh chụp màn hình từng chức năng.
- Viết hướng dẫn nghiệp vụ chi tiết cho user/admin.
- Bổ sung sơ đồ luồng đặt sân và phân quyền.
