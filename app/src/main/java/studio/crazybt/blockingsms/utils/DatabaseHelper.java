package studio.crazybt.blockingsms.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import studio.crazybt.blockingsms.models.SmsBlocked;

/**
 * Created by Brucelee Thanh on 09/10/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "SMSBLOCKED_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SMS = "SMS";
    private static final String ID_COLUMN = "Id";
    private static final String PHONE_NUMBER_COLUMN = "PhoneNumber";
    private static final String CONTENT_COLUMN = "Content";
    private static final String TIME_COLUMN = "Time";
    private static final String CREATE_SMS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_SMS + " (" +
            ID_COLUMN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            PHONE_NUMBER_COLUMN + " TEXT NOT NULL, " +
            CONTENT_COLUMN + " TEXT NOT NULL, " +
            TIME_COLUMN + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
            ")";

    private static DatabaseHelper sInstance;

    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e(TAG, "DatabaseHelper: ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate: ");
        db.execSQL(CREATE_SMS_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "onUpgrade: ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
        onCreate(db);
    }

    public boolean insertSms(SmsBlocked smsBlocked) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHONE_NUMBER_COLUMN, smsBlocked.getPhoneNumber());
        values.put(CONTENT_COLUMN, smsBlocked.getContent());
        values.put(TIME_COLUMN, smsBlocked.getTime());
        long rowId = db.insert(TABLE_SMS, null, values);
        db.close();
        if (rowId != -1)
            return true;
        return false;
    }

    public List<SmsBlocked> getAllSms() {
        SQLiteDatabase db = getReadableDatabase();
        List<SmsBlocked> smsBlockedList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_SMS;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                smsBlockedList.add(new SmsBlocked(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return smsBlockedList;
    }

}
