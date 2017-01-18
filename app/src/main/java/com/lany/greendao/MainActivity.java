package com.lany.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBManager dbManager = DBManager.getInstance(this);
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setAge(i * 3);
            user.setUsername("第" + i + "人");
            user.setNickname("天外飞仙" + i);
            dbManager.insertUser(user);
        }
        List<User> userList = dbManager.queryUserList();
        for (User user : userList) {
            Log.e("TAG", "queryUserList--before-->" + user.toString());
            if (user.getId() == 0) {
                dbManager.deleteUser(user);
            }
            if (user.getId() == 3) {
                user.setAge(10);
                dbManager.updateUser(user);
            }
        }
        userList = dbManager.queryUserList();
        for (User user : userList) {
            Log.e("TAG", "queryUserList--after--->" + user.toString());
        }
    }
}
