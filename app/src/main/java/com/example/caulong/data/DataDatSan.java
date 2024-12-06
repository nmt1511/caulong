package com.example.caulong.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.caulong.R;
import com.example.caulong.booking.ServiceDetailAdapter;
import com.example.caulong.entities.Service;

import java.util.ArrayList;
import java.util.List;

public class DataDatSan extends SQLiteOpenHelper {
    // Tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "booking_management.db";
    private static final int DATABASE_VERSION = 2;

    public DataDatSan(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        // Tạo bảng Court
        db.execSQL("CREATE TABLE IF NOT EXISTS Court (" +
                "court_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "court_name TEXT NOT NULL, " +
                "price REAL, " +
                "status TEXT)");
        sql = "INSERT INTO Court (court_name, price, status) " +
                "VALUES ('Sân 1', 25000.0, 'available')," +
                "('Sân 2', 25000.0, 'available'),"+
                "('Sân 3', 25000.0, 'available')," +
                "('Sân 4', 25000.0, 'available'),"+
                "('Sân 5', 25000.0, 'available')";
        db.execSQL(sql);

        // Tạo bảng User
        db.execSQL("CREATE TABLE IF NOT EXISTS User (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL, " +
                "role INTEGER NOT NULL)");
        sql = "INSERT INTO User (username, password, role) " +
                "VALUES ('hoanghm','123456', 1)," +
                "('thuannm','123456', 0)" ;
        db.execSQL(sql);

        // Tạo bảng Customer
        db.execSQL("CREATE TABLE IF NOT EXISTS Customer (" +
                "customer_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_name TEXT NOT NULL, " +
                "gender REAL, " +
                "phone_number TEXT, " +
                "email TEXT, " +
                "avatar TEXT," +
                "user_id INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES User(user_id))");
        sql = "INSERT INTO Customer(customer_name,phone_number,email,user_id)"+
                "VALUES ('Nguyễn Minh Thuận','0347023834','thuannm@gmail.com',2)";
        db.execSQL(sql);

        // Tạo bảng Admin
        db.execSQL("CREATE TABLE IF NOT EXISTS Admin (" +
                "admin_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "admin_name TEXT NOT NULL, " +
                "email TEXT, " +
                "user_id INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES User(user_id))");
        sql = "INSERT INTO Admin(admin_name,email,user_id)"+
                "VALUES ('Hoàng','hoanghm4869@gmail.com',1)";
        db.execSQL(sql);

        // Tạo bảng Time
        db.execSQL("CREATE TABLE IF NOT EXISTS Time (" +
                "time_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time_name TEXT, " +
                "value REAL)");
        sql = "INSERT INTO Time(time_name, value)"+
                "VALUES ('7:00',0.5),('7:30',0.5)," +
                "('8:00',0.5),('8:30',0.5)," +
                "('9:00',0.5),('9:30',0.5)," +
                "('10:00',0.5),('10:30',0.5)," +
                "('11:00',0.5),('11:30',0.5)," +
                "('12:00',0.5),('12:30',0.5)," +
                "('13:00',0.5),('13:30',0.5)," +
                "('14:00',0.5),('14:30',0.5)," +
                "('15:00',0.5),('15:30',0.5)," +
                "('16:00',0.5),('16:30',0.5)," +
                "('17:00',0.5),('17:30',0.5)," +
                "('18:00',0.5),('18:30',0.5)," +
                "('19:00',0.5),('19:30',0.5)," +
                "('20:00',0.5),('20:30',0.5)," +
                "('21:00',0.5),('21:30',0.5)," +
                "('22:00',0.5),('22:30',0.5)";
        db.execSQL(sql);

        // Tạo bảng Booking
        db.execSQL("CREATE TABLE IF NOT EXISTS Booking (" +
                "booking_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_id INTEGER, " +
                "court_id INTEGER, " +
                "present_date TEXT, " +
                "booking_date TEXT, " +
                "total_time REAL, " +
                "total_item REAL, " +
                "status TEXT, " +
                "FOREIGN KEY(customer_id) REFERENCES Customer(customer_id), " +
                "FOREIGN KEY(court_id) REFERENCES Court(court_id))");

        // Tạo bảng Service_type
        db.execSQL("CREATE TABLE IF NOT EXISTS Service_type (" +
                "type_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type_name TEXT)");
        sql = "INSERT INTO Service_type(type_name)"+
                "VALUES ('Thức ăn'),('Nước uống'),('Dịch vụ khác')";
        db.execSQL(sql);

        // Tạo bảng Service
        db.execSQL("CREATE TABLE IF NOT EXISTS Service (" +
                "service_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "service_name TEXT NOT NULL, " +
                "quantity INTEGER, " +
                "type_id INTEGER NOT NULL, " +
                "service_price REAL,"+
                "FOREIGN KEY(type_id) REFERENCES Service_type(type_id))");
        sql = "INSERT INTO Service(service_name,quantity,type_id, service_price)"+
                "VALUES ('Bánh mì',50,1,8000),('Mì ly Modern',20,1,12000)," +
                "('Nước suối',150,2,5000),('Revive',150,2,10000)," +
                "('Revive chanh muối',150,2,10000),('Cầu',300,3,20000)" ;
        db.execSQL(sql);

        // Tạo bảng Booking_time
        db.execSQL("CREATE TABLE IF NOT EXISTS Booking_time (" +
                "booking_time_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time_id INTEGER, " +
                "booking_id INTEGER, " +
                "FOREIGN KEY(time_id) REFERENCES Time(time_id), " +
                "FOREIGN KEY(booking_id) REFERENCES Booking(booking_id))");

        // Tạo bảng Booking_service
        db.execSQL("CREATE TABLE IF NOT EXISTS Booking_service (" +
                "booking_service_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "booking_id INTEGER, " +
                "service_id INTEGER, " +
                "quantity INTEGER, " +
                "total_price REAL, " +
                "FOREIGN KEY(booking_id) REFERENCES Booking(booking_id), " +
                "FOREIGN KEY(service_id) REFERENCES Service(service_id))");

        db.execSQL("CREATE TABLE IF NOT EXISTS Tournament (" +
                "tournament_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "start_date TEXT, " +
                "end_date TEXT, " +
                "description TEXT)");


        db.execSQL("CREATE TABLE IF NOT EXISTS Feedback (" +
                "feedback_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "star REAL, " +
                "feedback_text TEXT, " +
                "feedback_date TEXT, " +
                "customer_id INTEGER," +
                "FOREIGN KEY(customer_id) REFERENCES Customer(customer_id))");

        db.execSQL("CREATE TABLE IF NOT EXISTS Latest_update (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, " +
                "updated TEXT) ");

        // Tạo bảng Payment
        db.execSQL("CREATE TABLE IF NOT EXISTS Payment (" +
                "payment_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "booking_id INTEGER, " +
                "payment_method TEXT, " +
                "payment_status TEXT, " +
                "FOREIGN KEY(booking_id) REFERENCES Booking(booking_id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu cơ sở dữ liệu được nâng cấp
        db.execSQL("DROP TABLE IF EXISTS Court");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Customer");
        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS Time");
        db.execSQL("DROP TABLE IF EXISTS Booking_time");
        db.execSQL("DROP TABLE IF EXISTS Booking");
        db.execSQL("DROP TABLE IF EXISTS Booking_service");
        db.execSQL("DROP TABLE IF EXISTS Service");
        db.execSQL("DROP TABLE IF EXISTS Payment");
        onCreate(db);
    }

    public boolean updateBookingStatus(long bookingId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        int rows = db.update("Booking", values, "booking_id = ?", new String[]{String.valueOf(bookingId)});
        db.close();
        return rows > 0;
    }

    public String getCustomerName(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String customerName = null;
        String query = "SELECT customer_name FROM Customer WHERE customer_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(customerId)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                customerName = cursor.getString(0);
            }
            cursor.close();
        }

        db.close();
        return customerName;
    }
    // Lấy danh sách thời gian đã đặt cho một booking cụ thể
    public List<String> getBookingTimes(long bookingId) {
        List<String> bookingTimes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT t.time_name FROM Booking_time bt " +
                "JOIN Time t ON bt.time_id = t.time_id " +
                "WHERE bt.booking_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bookingId)});

        if (cursor.moveToFirst()) {
            do {
                String timeName = cursor.getString(0);
                bookingTimes.add(timeName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bookingTimes;
    }

    public String getCourtName(int courtId) {
        String courtName = "";
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT court_name FROM Court WHERE court_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courtId)});

        if (cursor.moveToFirst()) {
            courtName = cursor.getString(cursor.getColumnIndexOrThrow("court_name"));
        }
        cursor.close();
        db.close();

        return courtName;
    }

    public ArrayList<Service> loadBookingServices(long bookingId) {
        ArrayList<Service> serviceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT s.service_name, bs.quantity,s.service_price,bs.total_price " +
                        "FROM Booking_service bs " +
                        "JOIN Service s ON bs.service_id = s.service_id " +
                        "WHERE bs.booking_id = ?", new String[]{String.valueOf(bookingId)});

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("service_name"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                double price = cursor.getInt(cursor.getColumnIndexOrThrow("service_price"));
                double total = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));
                serviceList.add(new Service(name, quantity, price, total));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return  serviceList;
    }

    public void loadAvatarFromDatabase(ImageView img, int CustomerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT avatar FROM Customer WHERE customer_id = ?", new String[]{String.valueOf(CustomerId)});
        if (cursor != null && cursor.moveToFirst()) {
            String avatarPath = cursor.getString(cursor.getColumnIndexOrThrow("avatar"));
            if (avatarPath != null) {
                Bitmap avatar = BitmapFactory.decodeFile(avatarPath);
                // Hiển thị ảnh từ đường dẫn
                img.setImageBitmap(avatar);
            }
            else{
                img.setImageResource(R.drawable.ic_profile);
            }
        }
        else{
            cursor.close();
        }
        db.close();
    }

    public long savePayment(long booking_id, Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String method = "Thanh toán bằng tiền mặt";
        String status = "Hoàn tất";
        try{
            values.put("booking_id",booking_id);
            values.put("payment_method",method);
            values.put("payment_status",status);
            return (db.insert("Payment",null,values));
        }catch (Exception e){
            Toast.makeText(context, "Lỗi lưu vào Payment"+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return -1;
    }


}
