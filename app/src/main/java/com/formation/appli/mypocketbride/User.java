package com.formation.appli.mypocketbride;

/**
 * Created by student on 03-07-17.
 */

public class User {
    private String mail,pswd,dateOfBirth,nickname;
    private int sex; // Sex is 1 for male and 2 for female in all the app

    //region getter/setter
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    //endregion


    public User(String mail, String pswd, String dateOfBirth, String nickname, int sex) {
        this.mail = mail;
        this.pswd = pswd;
        this.dateOfBirth = dateOfBirth;
        this.nickname = nickname;
        this.sex = sex;
    }
}
