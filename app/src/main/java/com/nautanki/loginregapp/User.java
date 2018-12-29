package com.nautanki.loginregapp;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    Context context;
    String bkid,stdid;

    public void removeUser()
    {
        sharedPreferences.edit().clear().commit();
    }


    public String getName() {
        name=sharedPreferences.getString("userdata","");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("userdata",name).commit();

    }



    public String getBookId1() {
        bkid=sharedPreferences.getString("bookid1","");
        return bkid;
    }
    public void setBookId1(String bkid) {
        this.bkid=bkid;
        sharedPreferences.edit().putString("bookid1",bkid).commit();

    }



    public String getBookId2() {
        bkid=sharedPreferences.getString("bookid2","");
        return bkid;
    }
    public void setBookId2(String bkid) {
        this.bkid=bkid;
        sharedPreferences.edit().putString("bookid2",bkid).commit();

    }


    public String getBookId3() {
        bkid=sharedPreferences.getString("bookid3","");
        return bkid;
    }
    public void setBookId3(String bkid) {
        this.bkid=bkid;
        sharedPreferences.edit().putString("bookid3",bkid).commit();

    }
    public String getBookId4() {
        bkid=sharedPreferences.getString("bookid4","");
        return bkid;
    }
    public void setBookId4(String bkid) {
        this.bkid=bkid;
        sharedPreferences.edit().putString("bookid4",bkid).commit();

    }




   /* public String getBookId() {
        bkid=sharedPreferences.getString("bookid","");
        return bkid;
    }
    public void setBookId(String bkid) {
        this.bkid=bkid;
        sharedPreferences.edit().putString("bookid",bkid).commit();

    }*/

    public String getStudentId() {
        stdid=sharedPreferences.getString("studentid","");
        return stdid;
    }
    public void setStudentId(String stdid) {
        this.stdid=stdid;
        sharedPreferences.edit().putString("studentid",stdid).commit();

    }



    String name;
    SharedPreferences sharedPreferences;


    public User(Context context)
    {
        this.context=context;

        sharedPreferences= context.getSharedPreferences("userinfo",context.MODE_PRIVATE);

    }
}
