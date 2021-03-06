package com.example.splash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GamesDB.db";

    //Users Table keys
    private final String USER_TABLE = "USERS";
    private final String USER_ID = "_id";
    private final String USER_NAME = "username";
    private final String USER_PASSWORD = "password";

    String USER_CREATE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "(" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USER_NAME + " TEXT UNIQUE," +
            USER_PASSWORD + " TEXT)";

    //PEG Table keys
    private final String PEG_TABLE = "PEG";
    private final String PEG_ID = "_id";
    private final String PEG_USER = "username";
    private final String PEG_SCORE = "score";
    private final String PEG_MOVES = "moves";
    private final String PEG_TIME = "time";

    String PEG_CREATE = "CREATE TABLE IF NOT EXISTS " + PEG_TABLE + "(" +
            PEG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PEG_USER + " TEXT," +
            PEG_SCORE + " INTEGER," +
            PEG_MOVES + " TEXT," +
            PEG_TIME + " TEXT)";

    //2048 Table keys
    private final String TABLE_2048 = "A2048";
    private final String ID_2048 = "_id";
    private final String USER_2048 = "username";
    private final String SCORE_2048 = "score";
    private final String MOVES_2048 = "moves";
    private final String TIME_2048 = "time";

    String CREATE_2048 = "CREATE TABLE IF NOT EXISTS " + TABLE_2048 + "(" +
            ID_2048 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USER_2048 + " TEXT," +
            SCORE_2048 + " INTEGER," +
            MOVES_2048 + " TEXT," +
            TIME_2048 + " TEXT)";


    SQLiteDatabase database;
    String selectedId;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_CREATE);
        db.execSQL(PEG_CREATE);
        db.execSQL(CREATE_2048);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PEG_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2048);
        onCreate(db);
    }

    public void insertDataPeg(String newUser, int newScore, String newMoves, String newTime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PEG_USER, newUser);
        values.put(PEG_SCORE, newScore);
        values.put(PEG_MOVES, newMoves);
        values.put(PEG_TIME, newTime);

        db.insert(PEG_TABLE, null, values);
        db.close();
    }

    public void insertData2048(String newUser, int newScore, String newMoves, String newTime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_2048, newUser);
        values.put(SCORE_2048, newScore);
        values.put(MOVES_2048, newMoves);
        values.put(TIME_2048, newTime);

        db.insert(TABLE_2048, null, values);
        db.close();
    }

    public String getBestPeg(String username) {
        String selectQuery = "SELECT " + PEG_SCORE +
                " FROM " + PEG_TABLE +
                " WHERE " + PEG_USER + " = '" + username +
                "' ORDER BY " + PEG_SCORE + " DESC";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            String score = cursor.getString(0);
            cursor.close();
            return score;
        } else {
            cursor.close();
            return "0";
        }
    }

    public String getBest2048(String username) {
        String selectQuery = "SELECT " + SCORE_2048 +
                " FROM " + TABLE_2048 +
                " WHERE " + USER_2048 + " = '" + username +
                "' ORDER BY " + SCORE_2048 + " DESC";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            String score = cursor.getString(0);
            cursor.close();
            return score;
        } else {
            cursor.close();
            return "0";
        }
    }

    public Cursor getAll2048(String orderBy, String asc_desc) {
        String selectQuery = "SELECT * " +
                "FROM " + TABLE_2048 +
                " ORDER BY " + orderBy + asc_desc;

        return database.rawQuery(selectQuery, null);
    }

    public Cursor getAllPeg(String orderBy, String asc_desc) {
        String selectQuery = "SELECT * " +
                "FROM " + PEG_TABLE +
                " ORDER BY " + orderBy + asc_desc;

        return database.rawQuery(selectQuery, null);
    }

    public void deleteScore(String selectedId, String game) {
        if (game.equals("peg")) {
            database.delete(PEG_TABLE, PEG_ID + "=" + selectedId, null);
        } else {
            database.delete(TABLE_2048, ID_2048 + "=" + selectedId, null);
        }
    }


    public boolean checkUsername(String username) {
        String selectQuery = "SELECT " + USER_NAME + "  FROM "
                + USER_TABLE + " WHERE " + USER_NAME + " = '" + username + "'";

        Cursor cursor = database.rawQuery(selectQuery, null);

        boolean available = cursor.getCount() == 0;

        cursor.close();
        return available;
    }

    public void insertUser(String username, String password) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME, username);
        values.put(USER_PASSWORD, password);

        database.insert(USER_TABLE, null, values);
    }

    public boolean checkLogin(String username, String password) {
        String selectQuery = "SELECT " + USER_PASSWORD + "  FROM "
                + USER_TABLE + " WHERE " + USER_NAME + " = '" + username + "'";


        Cursor cursor = database.rawQuery(selectQuery, null);

        boolean success = false;

        if (cursor.getCount() != 0) {
            cursor.moveToNext();

            String dbPassword = cursor.getString(0);
            if (password.equals(dbPassword)) success = true;
        }

        cursor.close();

        return success;
    }

    public class Users {

        private String username;
        private String password;

        public Users() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
