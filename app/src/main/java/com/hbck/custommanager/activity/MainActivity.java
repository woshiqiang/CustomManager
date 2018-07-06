package com.hbck.custommanager.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbck.custommanager.R;
import com.hbck.custommanager.adapter.UserAdapter;
import com.hbck.custommanager.bean.User;
import com.hbck.custommanager.util.SqliteHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private List<User> userList = new ArrayList<>();
    private ListView listView;
    private UserAdapter adapter;
    private TextView tv_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        getData();

    }

    private void getData() {
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        int id = sp.getInt("id", 1);
        userList.clear();
        userList.addAll(SqliteHelper.getUsers(new SqliteHelper(this),id));
        adapter.notifyDataSetChanged();
    }


    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        adapter = new UserAdapter(this, userList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        tv_me = (TextView) findViewById(R.id.tv_me);
        tv_me.setOnClickListener(this);
    }

    private String[] strings = null;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        int type = sp.getInt("type", 1);

        int userType = userList.get(position).getType();
        if (type == 0) {//0:超级管理员
            if (userType == 1) { //客户
                strings = new String[]{"修改", "删除", "设为普通管理员"};
            } else if (userType == 2) {
                strings = new String[]{"修改", "删除", "取消管理员权限"};
            }

        } else if (type == 1) {//客户
            strings = new String[]{};
        } else if (type == 2) {//普通管理员
            if (userType == 2) {
                strings = new String[]{};
            } else {
                strings = new String[]{"修改", "删除"};
            }
        }


        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("操作");
        ad.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                    intent.putExtra("user", userList.get(position));
                    startActivity(intent);
                } else if (which == 1) {
                    SqliteHelper.deleteUserbyId(new SqliteHelper(MainActivity.this), userList.get(position).getId());
                    Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    getData();
                } else if (which == 2) {
                    if (strings[2].equals("设为普通管理员")) {
                        SqliteHelper.updateUserTypeById(new SqliteHelper(MainActivity.this), userList.get(position).getId(), 2);
                        Toast.makeText(MainActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                        getData();
                    } else if (strings[2].equals("取消管理员权限")) {
                        SqliteHelper.updateUserTypeById(new SqliteHelper(MainActivity.this), userList.get(position).getId(), 1);
                        Toast.makeText(MainActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                        getData();
                    }
                }

            }
        }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onClick(View v) {
        //我的
        Intent intent = new Intent(this, MeActivity.class);
        startActivity(intent);
    }
}
