package com.example.osmadpj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



public class ItemDetailActivity extends AppCompatActivity {

    private TextView item_name_text, item_type_text, item_price_text, item_detail_text, seller_name_text;
    private ImageView imageView;
    private Button Back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        item_name_text = findViewById(R.id.item_name_text);
        item_type_text = findViewById(R.id.item_type_text);
        item_price_text = findViewById(R.id.item_price_text);
        item_detail_text = findViewById(R.id.item_detail_text);
        seller_name_text = findViewById(R.id.seller_name_text);

        imageView = findViewById(R.id.imageView);

        Back = findViewById(R.id.btn_back);

        Intent showItem_detail = getIntent();
        String item_name = showItem_detail.getStringExtra("item_name");
        String item_type = showItem_detail.getStringExtra("item_type");
        String photo_url = showItem_detail.getStringExtra("photo_url");
        String item_price = showItem_detail.getStringExtra("item_price");
        String item_detail = showItem_detail.getStringExtra("item_detail");
        String seller_name = showItem_detail.getStringExtra("user_name");

        item_name_text.setText(item_name);
        item_type_text.setText(item_type);
        item_price_text.setText(item_price);
        item_detail_text.setText(item_detail);
        seller_name_text.setText(seller_name);

        loadImageFromUrl(photo_url);

        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ItemDetailActivity.this.finish();
            }
        });


    }
    private void loadImageFromUrl(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {

                    @Override
                    public void onSuccess(){



                    }

                    @Override
                    public void onError() {

                    }
                });
    }

}


