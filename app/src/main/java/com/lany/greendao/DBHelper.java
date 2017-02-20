package com.lany.greendao;

import com.lany.greendao.dao.DaoMaster;
import com.lany.greendao.dao.DaoSession;
import com.lany.greendao.dao.UserDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DBHelper {
    private volatile static DBHelper instance;
    private DaoSession daoSession;

    public static DBHelper getInstance() {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper();
                }
            }
        }
        return instance;
    }

    private DBHelper() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(MyApp.appContext, "test_db");
        Database database = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    /**
     * 插入一条记录
     */
    public void insertUser(User user) {
        UserDao userDao = daoSession.getUserDao();
        userDao.insert(user);
    }

    /**
     * 插入用户集合
     */
    public void insertUserList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        UserDao userDao = daoSession.getUserDao();
        userDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     */
    public void deleteUser(User user) {
        UserDao userDao = daoSession.getUserDao();
        userDao.delete(user);
    }

    /**
     * 更新一条记录
     */
    public void updateUser(User user) {
        UserDao userDao = daoSession.getUserDao();
        userDao.update(user);
    }

    /**
     * 查询用户列表
     */
    public List<User> queryUserList() {
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        List<User> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<User> queryUserList(int age) {
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Age.gt(age)).orderAsc(UserDao.Properties.Age);
        List<User> list = qb.list();
        return list;
    }
}