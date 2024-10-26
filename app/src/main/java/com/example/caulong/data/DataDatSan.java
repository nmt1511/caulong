package com.example.caulong.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataDatSan extends SQLiteOpenHelper {
    // Tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "booking_management.db";
    private static final int DATABASE_VERSION = 1;

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
                "role TEXT NOT NULL)");
        sql = "INSERT INTO User (username, password, role) " +
                "VALUES ('hoanghm','12345', 'guest')";
        db.execSQL(sql);

        // Tạo bảng Customer
        db.execSQL("CREATE TABLE IF NOT EXISTS Customer (" +
                "customer_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_name TEXT NOT NULL, " +
                "phone_number TEXT, " +
                "email TEXT, " +
                "user_id INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES User(user_id))");
        sql = "INSERT INTO Customer(customer_name,phone_number,email,user_id)"+
                "VALUES ('Hoàng','0337023824','hoanghm4869@gmail.com',1)";
        db.execSQL(sql);

        // Tạo bảng Admin
        db.execSQL("CREATE TABLE IF NOT EXISTS Admin (" +
                "admin_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "admin_name TEXT NOT NULL, " +
                "email TEXT, " +
                "user_id INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES User(user_id))");

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
                "booking_date TEXT, " +
                "total_time INTEGER, " +
                "total_item INTEGER, " +
                "FOREIGN KEY(customer_id) REFERENCES Customer(customer_id), " +
                "FOREIGN KEY(court_id) REFERENCES Court(court_id))");

        // Tạo bảng Service
        db.execSQL("CREATE TABLE IF NOT EXISTS Service (" +
                "service_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "service_name TEXT NOT NULL, " +
                "service_price REAL)");

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
}
