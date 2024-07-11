package com.TicTacToeProject.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SignLog.db";
    public static final int DATABASE_VERSION = 3;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE users (nickname TEXT, username TEXT PRIMARY KEY, password TEXT)");
            db.execSQL("CREATE TABLE results (id INTEGER PRIMARY KEY AUTOINCREMENT, player_one TEXT, player_two TEXT, winner TEXT)");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error creating database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            try {
                db.execSQL("ALTER TABLE users RENAME TO old_users");
                db.execSQL("CREATE TABLE users (nickname TEXT, username TEXT PRIMARY KEY, password TEXT)");
                db.execSQL("INSERT INTO users (nickname, username, password) SELECT email, email, password FROM old_users");
                db.execSQL("DROP TABLE old_users");
            } catch (Exception e) {
                Log.e("DatabaseHelper", "Error upgrading database", e);
            }
        }
        if (oldVersion < 3) {
            try {
                db.execSQL("CREATE TABLE results (id INTEGER PRIMARY KEY AUTOINCREMENT, player_one TEXT, player_two TEXT, winner TEXT)");
            } catch (Exception e) {
                Log.e("DatabaseHelper", "Error upgrading database to version 3", e);
            }
        }
    }

    public Boolean insertData(String nickname, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nickname", nickname);
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = db.insert("users", null, contentValues);
        return result != -1;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    public Boolean updateUserData(String oldUsername, String newNickname, String newUsername, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nickname", newNickname);
        contentValues.put("username", newUsername);
        contentValues.put("password", newPassword);

        long result = db.update("users", contentValues, "username=?", new String[]{oldUsername});
        return result != -1;
    }

    public Cursor getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
    }

    public Boolean insertResult(String playerOne, String playerTwo, String winner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("player_one", playerOne);
        contentValues.put("player_two", playerTwo);
        contentValues.put("winner", winner);
        long result = db.insert("results", null, contentValues);
        return result != -1;
    }

    public Cursor getAllResults() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM results", null);
    }

    public boolean updateResult(int id, String playerOne, String playerTwo, String winner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("player_one", playerOne);
        contentValues.put("player_two", playerTwo);
        contentValues.put("winner", winner);
        long result = db.update("results", contentValues, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean deleteResult(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("results", "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
