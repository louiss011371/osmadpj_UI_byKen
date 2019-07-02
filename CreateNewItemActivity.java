package com.example.osmadpj;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


// Updated at 28/06/2019 03:45 by Louis   //

public class CreateNewItemActivity extends AppCompatActivity {
    Bitmap bitmap;
    boolean check = true;
    Button SelectImageGallery, UploadImageServer, agree_text;
    ImageView imageView;
    RadioButton agree;

    ProgressDialog progressDialog ;

    String ImagePath = "photo_url" ;
    String ServerUploadPath = "http://10.0.2.2/item_upload/postItem.php";



    // ==== add new ==//
    TextView user_id;
    String GetUserIdTextView;
    String UserId= "user_id" ;

    TextView user_name;
    String GetUserNameTextView;
    String UserName  = "user_name" ;

    EditText item_mame;
    String GetEditItemNameEditText;
    String ItemName = "item_name";


    EditText item_type;
    String GetEditItemTypeEditText;
    String ItemType = "item_type";


    EditText item_price;
    String GetEditItemPriceEditText;
    String ItemPrice = "item_price";

    EditText item_detail;
    String GetEditItemDetailEditEext;
    String ItemDetail = "item_detail";







    // ==============//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_item);

        imageView = findViewById(R.id.imageView);
        SelectImageGallery = findViewById(R.id.buttonSelect);
        UploadImageServer = findViewById(R.id.buttonUpload);
        agree = findViewById(R.id.btn_agree);


        // =========== add new  ======= //
        user_id = findViewById(R.id.user_id);
        user_name = findViewById(R.id.user_name);

        item_mame = findViewById(R.id.item_name_text);
        item_type = findViewById(R.id.item_type_text);
        item_price = findViewById(R.id.item_price_text);
        item_detail = findViewById(R.id.item_detail_text);

        agree_text = findViewById(R.id.agree_text);

        Intent userInfo = getIntent();
        String extraId = userInfo.getStringExtra("user_id");
        String extraName = userInfo.getStringExtra("user_name");


        user_id.setText(extraId);

        user_name.setText(extraName);


    // ===================== //


        agree_text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent login_page = new Intent(CreateNewItemActivity.this, PrivacyAndTerms.class);
                startActivity(login_page);
            }
        });

        SelectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });


        UploadImageServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // ==== add new ==//

                GetUserIdTextView  = user_id.getText().toString();

                GetUserNameTextView = user_name.getText().toString();

                GetEditItemNameEditText = item_mame.getText().toString();

                GetEditItemTypeEditText = item_type.getText().toString();

                GetEditItemPriceEditText = item_price.getText().toString();

                GetEditItemDetailEditEext = item_detail.getText().toString();


                // ==============//

                if (agree.isChecked()){
                    ImageUploadToServerFunction();

                    Intent showItemListView = new Intent(CreateNewItemActivity.this, ShowAllItemActivity.class);
                    startActivity(showItemListView);
                }else {
                    Toast.makeText(getApplicationContext(), "Please agree the terms after you read", Toast.LENGTH_LONG).show();
                    Log.d("QAOD", "Please agree the terms after you read");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(CreateNewItemActivity.this,"Image is Uploading","Please Wait",false,false);

            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(CreateNewItemActivity.this,string1,Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                imageView.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();


                // ==== add new ==//


                HashMapParams.put(ItemName, GetEditItemNameEditText);

                HashMapParams.put(ItemType, GetEditItemTypeEditText);

                HashMapParams.put(ItemPrice, GetEditItemPriceEditText);

                HashMapParams.put(ImagePath, ConvertImage);

                HashMapParams.put(ItemDetail, GetEditItemDetailEditEext);


                HashMapParams.put(UserId, GetUserIdTextView);

                HashMapParams.put(UserName, GetUserNameTextView);


                // ==============//

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, StandardCharsets.UTF_8));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));


            }

            return stringBuilderObject.toString();
        }

    }
}


