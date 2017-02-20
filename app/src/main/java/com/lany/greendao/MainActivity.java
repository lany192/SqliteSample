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
        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowText.setText("");
                List<User> oldItems = DBHelper.getInstance().queryUserList();
                for (User user : oldItems) {
                    showPrintln("" + user);
                }
                int size = oldItems.size();
                showPrintln("新增之前数据量:" + size);

                int j = size + 1;
                User user = new User();
                user.setId(Long.valueOf(size + 1));
                user.setAge(j);
                user.setUsername("第" + j + "人");
                user.setNickname("item name" + j);
                showPrintln("插入的数据:" + user);
                DBHelper.getInstance().insertUser(user);

                List<User> newItems = DBHelper.getInstance().queryUserList();
                showPrintln("新增之后数据量:" + newItems.size());
                for (User item : newItems) {
                    showPrintln("" + item);
                }
            }
        });
        findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowText.setText("");
                List<User> oldItems = DBHelper.getInstance().queryUserList();
                if (oldItems.size() < 1) {
                    showPrintln("---------没有数据，不能删除-----------");
                    return;
                }
                for (User user : oldItems) {
                    showPrintln("" + user);
                }
                int size = oldItems.size();
                showPrintln("删除之前数据量:" + size);
                showPrintln("删除第一个数据");
                DBHelper.getInstance().deleteUser(oldItems.get(0));
                List<User> newItems = DBHelper.getInstance().queryUserList();
                showPrintln("删除之后数据:" + newItems.size());
                for (User item : newItems) {
                    showPrintln("" + item);
                }
            }
        });
        findViewById(R.id.query_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowText.setText("");
                showPrintln("查询年龄为2或者username为“第3人”的用户");
                List<User> newItems = DBHelper.getInstance().queryUserListByAge(2, "第3人");
                if (newItems.size() > 0) {
                    for (User item : newItems) {
                        showPrintln("" + item);
                    }
                } else {
                    showPrintln("找不到相关数据");
                }
            }
        });
        findViewById(R.id.delete_all_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowText.setText("");
                List<User> oldItems = DBHelper.getInstance().queryUserList();
                for (User user : oldItems) {
                    showPrintln("" + user);
                }
                int size = oldItems.size();
                showPrintln("之前数据量:" + size);
                showPrintln("删除所有数据");
                DBHelper.getInstance().deleteAllUser();
                List<User> newItems = DBHelper.getInstance().queryUserList();
                showPrintln("删除所有数据之后数据量:" + newItems.size());
            }
        });
        findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowText.setText("");
                showPrintln("所有用户的年龄后修改成1");
                showPrintln("修改之前--------------");
                List<User> oldItems = DBHelper.getInstance().queryUserList();
                for (User user : oldItems) {
                    showPrintln("" + user);
                }
                for (User user : oldItems) {
                    user.setAge(1);
                    DBHelper.getInstance().updateUser(user);
                }
                showPrintln("修改之后--------------");
                List<User> newItems = DBHelper.getInstance().queryUserList();
                for (User item : newItems) {
                    showPrintln("" + item);
                }
            }
        });
        findViewById(R.id.clear_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowText.setText("");
            }
        });
    }

    private void showPrintln(String str) {
        String old = mShowText.getText().toString();
        mShowText.setText(old + "\n" + str);
    }
}
