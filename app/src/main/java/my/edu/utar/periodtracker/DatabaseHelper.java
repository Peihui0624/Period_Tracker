package my.edu.utar.periodtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PeriodTrackerDB.db";
    private static final int DATABASE_VERSION = 2;

    // Table name and column names
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    //Period table name and column name
    private static final String TABLE_PERIOD = "period";
    private static final String COLUMN_PERIOD_ID = "period_id";
    private static final String COLUMN_START_PERIOD = "startPeriod";
    private static final String COLUMN_END_PERIOD = "endPeriod";
    private static final String COLUMN_TOTAL_PERIOD_DAY = "totalDay";
    private static final String COLUMN_PERIOD_TIMES = "periodTimes";

    // SQL statement to create the users table
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL);";

    //Period table
    private static final String CREATE_TABLE_PERIOD =
            "CREATE TABLE " + TABLE_PERIOD + " (" +
                    COLUMN_PERIOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_START_PERIOD + " TEXT NOT NULL, " +
                    COLUMN_END_PERIOD + " TEXT, " +
                    COLUMN_TOTAL_PERIOD_DAY + " INTEGER NOT NULL," +
                    COLUMN_PERIOD_TIMES + " INTEGER NOT NULL," +
                    COLUMN_EMAIL + " TEXT NOT NULL," +
                    "FOREIGN KEY(" + COLUMN_EMAIL + ") REFERENCES "+ TABLE_USERS +"("+ COLUMN_EMAIL +"));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PERIOD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIOD);
        onCreate(db);
    }

    public boolean insertUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);

        return result != -1;
    }

    public boolean insertPeriod(String startPeriod, int totalDay, int times, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_START_PERIOD, startPeriod);
        values.put(COLUMN_TOTAL_PERIOD_DAY, totalDay);
        values.put(COLUMN_PERIOD_TIMES, times);
        values.put(COLUMN_EMAIL, email);

        long result = db.insert(TABLE_PERIOD, null, values);

        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password});

        boolean userExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return userExists;
    }

    public boolean checkNewUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PERIOD + " WHERE " + COLUMN_EMAIL + "=?", new String[]{email});

        boolean newUser = cursor.moveToFirst();

        cursor.close();
        db.close();

        return newUser;
    }

    public HashMap<String, String> selectPreviousPeriod(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> previousPeriod = new HashMap<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PERIOD + " WHERE " + COLUMN_EMAIL +
                "=? ORDER BY " + COLUMN_PERIOD_TIMES + " DESC ", new String[]{email});

        if (cursor.moveToFirst()) {
            // get values from cursor
            String startPeriod = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_PERIOD));
            String endPeriod = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_PERIOD));
            String periodDay = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PERIOD_DAY));
            String periodTimes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PERIOD_TIMES));

            previousPeriod.put("startPeriod", startPeriod);
            previousPeriod.put("endPeriod", endPeriod);
            previousPeriod.put("periodDay", periodDay);
            previousPeriod.put("periodTimes", periodTimes);

        }

        return previousPeriod;
    }

    public boolean updateEndPeriod(String endDate, int totalDay, String email, String times){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_END_PERIOD, endDate);
        values.put(COLUMN_TOTAL_PERIOD_DAY, totalDay);

        String selection1 = COLUMN_EMAIL + " = ?";
        String selection2 = COLUMN_PERIOD_TIMES + " = ?";
        String[] selectionArgs = { email, times };

        long result = db.update(TABLE_PERIOD, values, selection1 + " = ? AND " + selection2 + " = ?", selectionArgs);

        return result != -1;

    }

}