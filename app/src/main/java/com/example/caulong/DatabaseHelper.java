package com.example.caulong; // Thay đổi theo package của bạn

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Booking;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "booking.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_FIELD = "field";
    private static final String COLUMN_TIME = "time";

    // Lệnh SQL để tạo bảng
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_FIELD + " TEXT NOT NULL, " +
                    COLUMN_TIME + " TEXT NOT NULL" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    // Phương thức để thêm đặt sân
    public void addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, booking.getDate());
        values.put(COLUMN_FIELD, booking.getField());
        values.put(COLUMN_TIME, booking.getTime());

        db.insert(TABLE_BOOKINGS, null, values);
        db.close();
    }

    // Phương thức để lấy tất cả đặt sân
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKINGS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                int fieldIndex = cursor.getColumnIndex(COLUMN_FIELD);
                int timeIndex = cursor.getColumnIndex(COLUMN_TIME);

                // Kiểm tra xem chỉ số cột có hợp lệ không
                if (idIndex != -1 && dateIndex != -1 && fieldIndex != -1 && timeIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String date = cursor.getString(dateIndex);
                    String field = cursor.getString(fieldIndex);
                    String time = cursor.getString(timeIndex);
                    bookings.add(new Booking(id, date, field, time));
                } else {
                    // Xử lý lỗi nếu có cột không hợp lệ
                    Log.e("DatabaseError", "Invalid column index");
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookings;
    }

    // Phương thức kiểm tra xem khung giờ đã được đặt chưa
    public boolean isTimeSlotAvailable(String date, String field, String time) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOKINGS +
                " WHERE " + COLUMN_DATE + " = ? AND " +
                COLUMN_FIELD + " = ? AND " +
                COLUMN_TIME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date, field, time});
        boolean isAvailable = cursor.getCount() == 0;
        cursor.close();
        db.close();
        return isAvailable;
    }
}
