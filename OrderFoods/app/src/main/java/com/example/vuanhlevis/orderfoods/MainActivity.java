package com.example.vuanhlevis.orderfoods;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vuanhlevis.orderfoods.common.Common;
import com.example.vuanhlevis.orderfoods.login.SignIn;
import com.example.vuanhlevis.orderfoods.login.SignUp;

public class MainActivity extends AppCompatActivity {
    Button bt_signin;
    Button bt_signup;
    TextView tv_slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();


        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void setupUI() {
        bt_signin = findViewById(R.id.bt_signin);
        bt_signup = findViewById(R.id.bt_signup);
        tv_slogan = findViewById(R.id.tv_slogan);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");
        tv_slogan.setTypeface(typeface);
    }
}
