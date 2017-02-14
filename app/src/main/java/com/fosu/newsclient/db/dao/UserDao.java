package com.fosu.newsclient.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fosu.newsclient.bean.User;
import com.fosu.newsclient.db.UserSQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/10.
 */

public class UserDao {
    private UserSQLiteOpenHelper helper;

    public UserDao(Context context) {
        helper = new UserSQLiteOpenHelper(context);
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", user.getUserName());
        values.put("user_password", user.getUserPassword());
        values.put("user_phone", user.getUserPhone());
        values.put("user_email", user.getUserEmail());
        values.put("user_type", user.getUserType());
        long rowId = db.insert("user_table", null, values);
        db.close();
        return rowId != -1 ? true : false;
    }

    public boolean deleteUserByName(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        int line = db.delete("user_table", "id=?", new String[]{name});
        db.close();
        return line > 0 ? true : false;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", user.getUserName());
        values.put("user_password", user.getUserPassword());
        values.put("user_phone", user.getUserPhone());
        values.put("user_email", user.getUserEmail());
        int line = db.update("user_table", values, "user_id=?", new String[]{String.valueOf(user.getUserId())});
        db.close();
        return line > 0 ? true : false;
    }

    public User findUser(User user) {
        User result = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("user_table", null, "user_name=? AND user_password=?", new String[]{user.getUserName(), user.getUserPassword()}, null, null, null);
        while (cursor.moveToNext()) {
            result = new User();
            result.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            result.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
            result.setUserPassword(cursor.getString(cursor.getColumnIndex("user_password")));
            result.setUserPhone(cursor.getString(cursor.getColumnIndex("user_phone")));
            result.setUserEmail(cursor.getString(cursor.getColumnIndex("user_email")));
            result.setUserType(cursor.getInt(cursor.getColumnIndex("user_type")));
        }
        db.close();
        return result;
    }
}
