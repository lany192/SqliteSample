package com.github.lany192.sqlite.greendao;

import android.app.Application;
import android.content.Context;

import com.lany.greendao.DaoMaster;
import com.lany.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

public class MyApp extends Application {
    public static Context appContext;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();

        // encrypted SQLCipher database
        // note: you need to add SQLCipher to your dependencies, check the build.gradle file
        // DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db-encrypted");
        // Database db = helper.getEncryptedWritableDb("encryption-key");

        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
