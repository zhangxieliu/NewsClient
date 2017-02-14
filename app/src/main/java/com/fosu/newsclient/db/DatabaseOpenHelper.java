package com.fosu.newsclient.db;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by Administrator on 2016/10/10.
 */

public class DatabaseOpenHelper {
    private static final String DB_NAME = "news_info.db";
    private static final int VERSION = 1;
    private static DbManager db;

    private DatabaseOpenHelper() {
        db = x.getDb(new DbManager.DaoConfig()
                .setDbName(DB_NAME)
                .setDbVersion(VERSION)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // 数据库升级操作
                    }
                }));
    }

    public static DbManager getInstance() {
        if (db == null) {
            DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper();
        }
        return db;
    }
}
