package com.example.osmadpj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/* Changed key("id") to key("user_id") on 28/6 17:15 */
/* Changed key("name") to key("user_name") for matching  database attribute user_id, user_name */

public class LoginActivity extends AppCompatActivity {

    private RadioButton aaa;
    private EditText id,email, password;
    private static String URL_LOGIN = "http://10.0.2.2/android_register_login/login.php";
    private ProgressBar loading;
    private Button btn_login, btn_regist;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);


        loading = findViewById(R.id.loading);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        btn_login = findViewById(R.id.btn_login);
        btn_regist = findViewById(R.id.btn_regist);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString();
                String mPass = password.getText().toString();

//                if (mEmail.isEmpty() && mPass.isEmpty()){
//                    email.setError("Please insert email");
//                    password.setError("Please insert password");
//                }else if (mEmail.isEmpty()) {
//                    email.setError("Please insert email");
//
//                }else if(mPass.isEmpty()){
//                    password.setError("Please insert password");
//                }else {
                if (!mEmail.isEmpty() || !mPass.isEmpty()) {

                    Login(mEmail, mPass);
                } else {
                    email.setError("Please insert email");
                    password.setError("Please insert password");
                }
            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_page = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(register_page);

            }
        });
    }

//        bbb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (aaa.isChecked()){
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                }else {
//                    Toast.makeText(getApplicationContext(), "Please agree the terms after you read", Toast.LENGTH_LONG).show();
//                    Log.d("QAOD", "Please agree the terms after you read");
//                }
//            }
//        });








    private void Login(final String email, final String password){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if (success.equals("1")){
                        for (int i = 0 ; i <jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);


                            String id = object.getString("user_id");
                            String name = object.getString("user_name");
                            String email = object.getString("email");


                            sessionManager.createSession(id,name, email);


                            // Send user information to home page //
                            Intent userInfo = new Intent(LoginActivity.this, HomeActivity.class);
                            userInfo.putExtra("user_id", id);
                            userInfo.putExtra("user_name", name);
                            userInfo.putExtra("email", email);
                            startActivity(userInfo);



                            //====================================//

                            System.out.println("Login success : ,  Your id :"  +  id  +  "Your name : " + name + "Your email: " + email );

                            Toast.makeText(LoginActivity.this, "Login Success, " + "Your Id: " + id + "," +
                                    "Your username :"+
                                        name + " , " +
                                        "Your email:  " +
                                        email , Toast.LENGTH_SHORT).show();

                            loading.setVisibility(View.GONE);
                        }
                    }else{

                        Toast.makeText(LoginActivity.this, "Email and password not catch or current user is not exist " , Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Email and password not catch or current user is not exist " , Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginActivity.this, "Email and password not catch or current user is not exist " , Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}


