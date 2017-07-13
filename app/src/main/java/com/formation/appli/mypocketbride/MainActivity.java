package com.formation.appli.mypocketbride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.formation.appli.mypocketbride.DB.DAO.MessageDAO;


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
        initDBmessage();

    }

    private void start(){
        Intent intent=new Intent(this, LivingActivity.class);
        startActivity(intent);

    }
    private void signIn(){
        Intent intent=new Intent(this, SignIn.class);
        startActivity(intent);

    }
    private void logIn(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
    private void initDBmessage(){

        Messages[] messages=getMessages();
        if (messages.length!=0) {

            Messages home1 = new Messages("Welcome Home ", 1, 0);
            Messages home2 = new Messages("How was your day ", 1, 0);
            Messages home3 = new Messages("I miss you ", 1, 0);
            Messages home4 = new Messages("I'm so happy to see you ", 1, 0);
            //Messages Home5=new Messages(,1,0);

            Messages work1 = new Messages("Have a nice day at work ", 2, 0);
            Messages work2 = new Messages("Good luck for your work", 2, 0);
            Messages work3 = new Messages("I'm going to miss you", 2, 0);
            Messages work4 = new Messages("Don't forget to eat ", 2, 0);
            //Messages Work5=new Messages(,2,0);
            MessageDAO messageDao = new MessageDAO(this);
            messageDao.openWritable();
            messageDao.insert(home1);
            messageDao.insert(home2);
            messageDao.insert(home3);
            messageDao.insert(home4);
            messageDao.insert(work1);
            messageDao.insert(work2);
            messageDao.insert(work3);
            messageDao.insert(work4);
            messageDao.close();
        }
        else{
            return;
        }
    }
    private Messages[] getMessages(){

        MessageDAO dao=new MessageDAO(this);
        dao.openReadable();
        Messages[] messages=dao.getAll();
        dao.close();
        return messages;
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
                logIn();
                break;
        }
    }
}
