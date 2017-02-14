package com.fosu.newsclient.db.dao;

import com.fosu.newsclient.bean.JokeEntity;
import com.fosu.newsclient.bean.NewsBean;
import com.fosu.newsclient.db.DatabaseOpenHelper;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class JokeDao {
    private static JokeDao newsDao;
    private static DbManager db;

    private JokeDao() {
        db = DatabaseOpenHelper.getInstance();
    }

    public synchronized static JokeDao getInstance() {
        if (newsDao == null) {
            newsDao = new JokeDao();
        }
        return newsDao;
    }

    public void save(List<NewsBean> list) throws DbException {
        db.save(list);
    }

    public void deleteByType(String type) throws DbException {
        db.delete(JokeEntity.class, WhereBuilder.b("typeId", "=", type));
    }

    public List<NewsBean> find(String type) throws DbException {
        return db.selector(NewsBean.class)
                .where("typeId", "=", type)
                .orderBy("id")
                .findAll();
    }
}
