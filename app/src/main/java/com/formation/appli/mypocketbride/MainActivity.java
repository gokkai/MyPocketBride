package com.formation.appli.mypocketbride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_main_start, btn_main_signIn, btn_main_logIn;
   //private final static Address home=new Address(50.806784,4.343913,1);
    //private final static Address work=new Address(50.837803,4353648,2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        btn_main_start=(Button)findViewById(R.id.btn_start);
        btn_main_signIn=(Button)findViewById(R.id.btn__main_signIn);
        btn_main_logIn=(Button)findViewById(R.id.btn__main_logIn);

        btn_main_start.setOnClickListener(this);
        btn_main_signIn.setOnClickListener(this);
        btn_main_logIn.setOnClickListener(this);

    }

    private void start(){
        Intent intent=new Intent(this, LivingActivity.class);
        startActivity(intent);
        finish();
    }
    private void signIn(){
        Intent intent=new Intent(this, SignIn.class);
        startActivity(intent);
        finish();
    }
    private void logIn(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_start:
                start();
                break;
            case R.id.btn__main_signIn:
                signIn();
                break;
            case R.id.btn__main_logIn:
                break;
        }
    }
}
