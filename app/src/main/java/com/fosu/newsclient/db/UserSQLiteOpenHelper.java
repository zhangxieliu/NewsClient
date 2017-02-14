package com.fosu.newsclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fosu.newsclient.bean.User;

/**
 * Created by Administrator on 2016/10/10.
 */

public class UserSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE user_table " +
            "(user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_name VARCHAR(20) UNIQUE NOT NULL," +
            "user_password VARCHAR(20) NOT NULL," +
            "user_phone INTEGER NOT NULL," +
            "user_email VARCHAR(20) NOT NULL," +
            "user_type INTEGER DEFAULT 0)";

    public UserSQLiteOpenHelper(Context context) {
        super(context, "user_table", null, 1);
    }

    public UserSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        ContentValues values = new ContentValues();
        values.put("user_id", 1);
        values.put("user_name", "scott");
        values.put("user_password", "tiger");
        values.put("user_phone", "15627236834");
        values.put("user_email", "930168863@qq.com");
        values.put("user_type", User.NORMAL_USER);
        db.insert("user_table", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
