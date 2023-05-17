package com.example.savemoneytime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.savemoneytime.MainApplication.Models.CatelogyModel;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }else{
            CatelogyModel catelogyModel=(CatelogyModel) bundle.get("object_cate");
            TextView textView = findViewById(R.id.tv_show_cate);
            textView.setText(catelogyModel.getTitleCatelogy());

        }
    }
}