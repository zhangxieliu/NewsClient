package com.fosu.newsclient.db.dao;

import com.fosu.newsclient.bean.NewsBean;
import com.fosu.newsclient.db.DatabaseOpenHelper;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class NewsDao {
    private static NewsDao newsDao;
    private static DbManager db;

    private NewsDao() {
        db = DatabaseOpenHelper.getInstance();
    }

    public synchronized static NewsDao getInstance() {
        if (newsDao == null) {
            newsDao = new NewsDao();
        }
        return newsDao;
    }

    public void save(List<NewsBean> list) throws DbException {
        db.save(list);
    }

    public void deleteByType(String type) throws DbException {
        db.delete(NewsBean.class, WhereBuilder.b("typeId", "=", type));
    }

    public List<NewsBean> find(String type) throws DbException {
        return db.selector(NewsBean.class)
                .where("typeId", "=", type)
                .orderBy("id")
                .findAll();
    }
}
