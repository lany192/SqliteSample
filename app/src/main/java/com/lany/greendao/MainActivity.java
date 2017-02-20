package com.lany.greendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mShowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowText = (TextView) findViewById(R.id.show_text);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> userList = DBHelper.getInstance().queryUserList();
                int size = userList.size();
                for (int i = 0; i < 5; i++) {
                    int j = i + size;
                    User user = new User();
                    user.setId(Long.valueOf(j));
                    user.setAge(j * 3);
                    user.setUsername("第" + j + "人");
                    user.setNickname("天外飞仙" + j);
                    DBHelper.getInstance().insertUser(user);
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
                List<User> userList = DBHelper.getInstance().queryUserList();
                for (User user : userList) {
                    showPrintln("queryUserList--before-->" + user.toString());
                    if (user.getId() == 0) {
                        DBHelper.getInstance().deleteUser(user);
                    }
                    if (user.getId() == 3) {
                        user.setAge(10);
                        DBHelper.getInstance().updateUser(user);
                    }
                }
                userList = DBHelper.getInstance().queryUserList();
                for (User user : userList) {
                    showPrintln("queryUserList--after--->" + user.toString());
                }
            }
        });
    }

    private void showPrintln(String str) {
        String old = mShowText.getText().toString();
        mShowText.setText(old + "\n" + str);
    }
}
