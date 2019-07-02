package com.example.osmadpj;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


/* Changed params key("name") to ("user_name") on 28/6 17:15 */

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private Button agree_text;
    private EditText name, email, password, c_password;
    private Button btn_regist, btn_login_page;
    private ProgressBar loading;
    private RadioButton btn_agree;
    private static String URL_REGIST =  "http://10.0.2.2/android_register_login/register.php";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password= findViewById(R.id.password);
        c_password =findViewById(R.id.c_password);
        btn_regist =  findViewById(R.id.btn_regist);
        btn_login_page = findViewById(R.id.btn_login_page);
        btn_agree = findViewById(R.id.btn_agree);
        agree_text = findViewById(R.id.agree_text);

//        drawerLayout = findViewById(R.id.drawer_layout);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
//
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//
//                        menuItem.setChecked(true);
//
//                        drawerLayout.closeDrawers();
//
//                        return true;
//                    }
//                }
//        );

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password1 = password.getText().toString();
                String password2 = c_password.getText().toString();


                if (!password1.equalsIgnoreCase(password2) ){
                    Toast.makeText(MainActivity.this,"password not match" , Toast.LENGTH_SHORT).show();
                }else if(btn_agree.isChecked()){
                    Regist();
                }else{
                    Toast.makeText(getApplicationContext(), "Please agree the terms after you read", Toast.LENGTH_LONG).show();
                }
            }
        });

//        if (agree.isChecked()){
//            ImageUploadToServerFunction();
//        }else {
//            Toast.makeText(getApplicationContext(), "Please agree the terms after you read", Toast.LENGTH_LONG).show();
//            Log.d("QAOD", "Please agree the terms after you read");
//        }

        btn_login_page.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent login_page = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login_page);

            }
        });

        agree_text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent login_page = new Intent(MainActivity.this, PrivacyAndTerms.class);
                startActivity(login_page);
            }
        });

    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case android.R.id.home:
//                drawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }
//        return  super.onOptionsItemSelected(item);
//    }

    private void Regist() {
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String c_password = this.c_password.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")){
                        Toast.makeText(MainActivity.this,"Register Success", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.INVISIBLE);
                    }

                }catch (JSONException e) {
                        e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Register Error" + e.toString() , Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_regist.setVisibility(View.VISIBLE);

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Register Error" + error.toString() , Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{


                Map<String, String> params = new HashMap<>();
                params.put("user_name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
