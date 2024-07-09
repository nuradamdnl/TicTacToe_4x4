package com.TicTacToeProject.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "SignLog.db";
    public static final int databaseVersion = 2; // Increment version for upgrade

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE users (nickname TEXT, username TEXT PRIMARY KEY, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            MyDB.execSQL("ALTER TABLE users RENAME TO old_users");
            MyDB.execSQL("CREATE TABLE users (nickname TEXT, username TEXT PRIMARY KEY, password TEXT)");
            MyDB.execSQL("INSERT INTO users (nickname, username, password) SELECT email, email, password FROM old_users");
            MyDB.execSQL("DROP TABLE old_users");
        }
    }

    public Boolean insertData(String nickname, String username, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nickname", nickname);
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);
        return result != -1;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    public Cursor getUserData(String username) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
    }

    public Boolean updateUserData(String oldUsername, String newNickname, String newUsername, String newPassword) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nickname", newNickname);
        contentValues.put("username", newUsername);
        contentValues.put("password", newPassword);

        long result = MyDatabase.update("users", contentValues, "username=?", new String[]{oldUsername});
        return result != -1;
    }

    // Method to fetch user details by username
    public Cursor getUserDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("users", null, "username=?", new String[]{username}, null, null, null);
        return cursor;
    }


}
