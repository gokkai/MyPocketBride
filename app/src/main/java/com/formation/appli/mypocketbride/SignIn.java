package com.formation.appli.mypocketbride;

import android.location.*;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.formation.appli.mypocketbride.DB.DAO.AddressDAO;
import com.formation.appli.mypocketbride.DB.DAO.CompanionDAO;
import com.formation.appli.mypocketbride.DB.DAO.InteractDAO;
import com.formation.appli.mypocketbride.DB.DAO.LocationDAO;
import com.formation.appli.mypocketbride.DB.DAO.UserDAO;
import com.formation.appli.mypocketbride.GPS.Position;
import com.formation.appli.mypocketbride.Geocodage.RequestGeocodage;

import java.util.List;
import java.util.Locale;

public class SignIn extends AppCompatActivity implements View.OnClickListener{

    Button btn_validate, btn_reinitiate;
    EditText et_mail, et_psw,et_psw2,et_companionName, et_nickname,et_addHome,et_addWork,et_dateOfBirth;
    RadioButton male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();

    }
    private void initView(){
        et_mail=(EditText)findViewById(R.id.et_sign_mail);
        et_psw=(EditText)findViewById(R.id.et_sign_psw1);
        et_psw2=(EditText)findViewById(R.id.et_sign_psw2);
        et_dateOfBirth=(EditText)findViewById(R.id.et_dateOfBirth);
        et_companionName=(EditText)findViewById(R.id.et_sign_companionName);
        et_nickname=(EditText)findViewById(R.id.et_sign_yourName);
        et_addHome=(EditText)findViewById(R.id.et_sign_addHome);
        et_addWork=(EditText)findViewById(R.id.et_sign_addWork);

        male=(RadioButton)findViewById(R.id.radio_male);
        female=(RadioButton)findViewById(R.id.radio_female);

        btn_validate=(Button)findViewById(R.id.btn_signIn_validate);
        btn_reinitiate=(Button)findViewById(R.id.btn_signIn_reinitiate);
        btn_validate.setOnClickListener(this);
        btn_reinitiate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signIn_validate:
                createAccount();
                break;
            case R.id.btn_signIn_reinitiate:
                empty();
                break;

        }
    }

    private void empty(){
        et_mail.setText("");
        clearPwd();
        et_companionName.setText("");
        et_nickname.setText("");
        et_addHome.setText("");
        et_addWork.setText("");

    }

    private void clearPwd(){
        et_psw.setText("");
        et_psw2.setText("");
    }
    private void createAccount() {
        String userPwd1 = this.et_psw.getText().toString();
        String userPwd2 = this.et_psw2.getText().toString();
        String userMail=this.et_mail.getText().toString();
        String userCompanion=this.et_companionName.getText().toString();
        String userNickname=this.et_nickname.getText().toString();
        String userDateOfBirth=this.et_dateOfBirth.getText().toString();
        String userHome=this.et_addHome.getText().toString();
        String userWork=this.et_addWork.getText().toString();
        int userSex;

        //region empty
        if(userMail==null){
            Toast.makeText(this, R.string.empty_mail, Toast.LENGTH_SHORT).show();
            et_mail.requestFocus();
            return;
        }
        if(userPwd1==null){
            Toast.makeText(this, R.string.empty_pwd, Toast.LENGTH_SHORT).show();
            et_psw.requestFocus();
            return;
        }
        if(userPwd2==null){
            Toast.makeText(this, R.string.empty_pwd, Toast.LENGTH_SHORT).show();
            et_psw2.requestFocus();
            return;
        }
        if(userCompanion==null){
            Toast.makeText(this, R.string.empty_companion, Toast.LENGTH_SHORT).show();
            et_companionName.requestFocus();
            return;
        }
        if(userNickname==null){
            Toast.makeText(this, R.string.empty_nickname, Toast.LENGTH_SHORT).show();
            et_nickname.requestFocus();
            return;
        }
        if(userDateOfBirth==null){
            Toast.makeText(this, R.string.empty_date, Toast.LENGTH_SHORT).show();
            et_dateOfBirth.requestFocus();
            return;
        }
        if(userHome==null){
            Toast.makeText(this, R.string.empty_home, Toast.LENGTH_SHORT).show();
            et_addHome.requestFocus();
            return;
        }
        if(userWork==null){
            Toast.makeText(this, R.string.empty_work, Toast.LENGTH_SHORT).show();
            et_addWork.requestFocus();
            return;
        }

        //endregion
        if(male.isChecked()){
            userSex=1;
        }else if(female.isChecked()){
            userSex=2;
        }else{
            Toast.makeText(this, R.string.no_sex, Toast.LENGTH_SHORT).show();
            male.requestFocus();
            return;
        }
        if (!userPwd1.equals(userPwd2)){
            clearPwd();
            Toast.makeText(this, R.string.bad_pwd, Toast.LENGTH_SHORT).show();
            et_psw.requestFocus();
            return;
        }
        User user=new User(userMail,userPwd1,userDateOfBirth,userNickname,userSex);
        //region DB usage
        UserDAO userDao = new UserDAO(this);
        userDao.openWritable();
        userDao.insert(user);
        userDao.close();
        //endregion
        int userID=user.getId();
        Companion companion=new Companion(1,0);
        //region DB usage
        CompanionDAO companionDao = new CompanionDAO(this);
        companionDao.openWritable();
        companionDao.insert(companion);
        companionDao.close();
        //endregion
        int companionID=companion.getId();
        Interact interact=new Interact(userCompanion,companionID,userID);
        //region DB usage
        InteractDAO interactDao = new InteractDAO(this);
        interactDao.openWritable();
        interactDao.insert(interact);
        interactDao.close();
        //endregion
        Position p;
        p=getPosition(userHome);
        Address home=new Address(p.getX(),p.getY());
        //region DB usage
        AddressDAO addressDao = new AddressDAO(this);
        addressDao.openWritable();
        addressDao.insert(home);
        addressDao.close();
        //endregion
        int homeId=home.getId();
        Location locationH=new Location(1,userID,homeId);
        //region DB usage
        LocationDAO locationHome = new LocationDAO(this);
        locationHome.openWritable();
        locationHome.insert(locationH);
        locationHome.close();
        //endregion
        p=getPosition(userWork);
        Address work=new Address(p.getX(),p.getY());
        //region DB usage
        AddressDAO addressWDao = new AddressDAO(this);
        addressWDao.openWritable();
        addressWDao.insert(work);
        addressWDao.close();
        //endregion
        int workId=work.getId();
        Location locationW=new Location(2,userID,workId);
        //region DB usage
        LocationDAO locationWork = new LocationDAO(this);
        locationWork.openWritable();
        locationWork.insert(locationW);
        locationWork.close();
        //endregion

    }

    public Position getPosition(String address) {
        Position p=null;
        Geocoder geocoder=new Geocoder(this, Locale.getDefault());
        try{
            List<android.location.Address> addresses =geocoder.getFromLocationName(address,1);
            if(addresses.size()>0){
                p=new Position(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                return p;
            }

        }
        catch(Exception e){
            Toast.makeText(this, R.string.exceptionAddress, Toast.LENGTH_SHORT).show();
        }
        return p;
    }
}
