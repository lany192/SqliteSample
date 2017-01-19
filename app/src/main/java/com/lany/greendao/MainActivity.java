package com.lany.greendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBManager dbManager;
    private TextView mShowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = DBManager.getInstance(this);
        mShowText = (TextView) findViewById(R.id.show_text);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> userList = dbManager.queryUserList();
                int size = userList.size();
                for (int i = 0; i < 5; i++) {
                    int j = i + size;
                    User user = new User();
                    user.setId(Long.valueOf(j));
                    user.setAge(j * 3);
                    user.setUsername("第" + j + "人");
                    user.setNickname("天外飞仙" + j);
                    dbManager.insertUser(user);
                }
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }
}
