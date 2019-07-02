package com.example.osmadpj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private TextView id,name, email;
    private Button btn_logout, btn_chat;
    private Button btn_addGoods;
    private Button btn_showAllGoods;
    SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        // checklogin method is SessionManage.java
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        id = findViewById(R.id.user_id);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);



        btn_addGoods = findViewById(R.id.btn_addGoods);
        btn_showAllGoods = findViewById(R.id.btn_showAllGoods);
        btn_logout = findViewById(R.id.btn_logout);
        btn_chat = findViewById(R.id.btn_chat);


        HashMap<String, String> user = sessionManager.getUserDetail();
        final  String mId = user.get(sessionManager.ID);
        final String mName = user.get(sessionManager.NAME);
        String mEmail = user.get(sessionManager.EMAIL);

        id.setText(mId);
        name.setText(mName);
        email.setText(mEmail);

        btn_addGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passUserInfo = new Intent(HomeActivity.this, CreateNewItemActivity.class);
                passUserInfo.putExtra("user_id",mId);
                passUserInfo.putExtra("user_name", mName);
                System.out.println(mId);
                System.out.println(mName);
                startActivity(passUserInfo);

            }
        });


        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passUserInfo = new Intent(HomeActivity.this,ChatListActivity.class);
                passUserInfo.putExtra("name", mName);
                System.out.println(mName);
                startActivity(passUserInfo);

            }
        });


        btn_showAllGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showAllGoods = new Intent(HomeActivity.this, ShowAllItemActivity.class);
                startActivity(showAllGoods);
            }
        });



// Pass the login success current username in home page
//        Intent userInfo = getIntent();
//        String extraName = userInfo.getStringExtra("name");
//        String extraEmail = userInfo.getStringExtra("email");

//        name.setText(extraName);
//        email.setText(extraEmail);


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                sessionManager.logout();
            }
        });



    }
}
