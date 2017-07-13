package com.formation.appli.mypocketbride;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.formation.appli.mypocketbride.DB.DAO.UserDAO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String SHAHash;
    public static int NO_OPTIONS=0;
    public static final String KEY_MAIL = "mail";
    public static final String KEY_PWD = "pwd";
    public static final String KEY_SAVE = "remember";

    Button btn_logIn,btn_register;
    EditText et_mail,et_password;
    CheckBox ckRemember;
    //String userMail=this.et_mail.getText().toString();
    //String userPwd1 = this.et_psw.getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }
    private void initView(){
        et_mail=(EditText)findViewById(R.id.et_logIn_mail);
        et_password=(EditText)findViewById(R.id.et_logIn_password);

        btn_logIn=(Button)findViewById(R.id.btn_logIn_logIn);
        btn_register=(Button)findViewById(R.id.btn_logIn_register);

        ckRemember=(CheckBox)findViewById(R.id.ck_logIn_remember);

        btn_logIn.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_logIn_logIn:
                logIn();
                break;
            case R.id.btn_logIn_register:
                getToSignIn();
                break;
        }

    }

    private void logIn(){
        String mail=et_mail.getText().toString();
        String password=et_password.getText().toString();
        boolean save = ckRemember.isChecked();
        comparePSwd(mail,password);
        savePrefs(mail, password,save);
    }

    private void getToSignIn(){
        Intent intent= new Intent(this,SignIn.class);
        startActivity(intent);
        finish();
    }
    private void comparePSwd(String mail,String pswd ){
        User[] users=getUser(mail);
        if(users==null){
            Toast.makeText(this,R.string.error_unknow_mail,Toast.LENGTH_SHORT).show();
        }else for(User curUser:users){
            pswd=computeShaHash(pswd);
            String pswdRef=curUser.getPswd();
            if(pswd.equals(pswdRef)){
                int id=curUser.getId();
                Intent intent =new Intent(this,LivingActivity.class);
                intent.putExtra("idUser",id);
                startActivity(intent);
                return;
            }
            Toast.makeText(this,R.string.error_incorrect_password,Toast.LENGTH_SHORT).show();
        }
    }
    private User[] getUser(String mail){
        UserDAO dao=new UserDAO(this);
        dao.openReadable();
        User[] users=dao.getUser(mail);
        dao.close();
        return users;
    }
    private void savePrefs(String mail, String pwd, boolean save) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        if (save) {
            editor.putString(KEY_MAIL, mail);
            editor.putString(KEY_PWD, pwd);
        } else {
            editor.remove(KEY_MAIL);
            editor.remove(KEY_PWD);
        }
        editor.putBoolean(KEY_SAVE, save);

        editor.apply();
    }

    private static String convertToHex(byte[] data) throws java.io.IOException {


        StringBuffer sb = new StringBuffer();
        String hex;

        hex= Base64.encodeToString(data, 0, data.length, NO_OPTIONS);

        sb.append(hex);

        return sb.toString();
    }


    public String computeShaHash(String password){

        MessageDigest mdSha1 = null;
        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            Log.e("myapp", "Error initializing SHA1 message digest");
        }
        try {
            mdSha1.update(password.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] data = mdSha1.digest();
        try {
            SHAHash=convertToHex(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return SHAHash;
    }


}

