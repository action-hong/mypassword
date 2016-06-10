package com.example.administrator.mypassword.model;

/**
 * Created by Administrator on 2016/3/29.
 */
import java.io.Serializable;


@SuppressWarnings("serial")
public class MyPassword implements Serializable{


    public MyPassword(String name, String account, String password,
                      String createDate, String lastInterviewDate, String other) {
        super();
        this.name = name;
        this.account = account;
        this.password = password;
        this.createDate = createDate;
        this.lastInterviewDate = lastInterviewDate;
        this.other = other;
    }

    public MyPassword(){}
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getLastInterviewDate() {
        return lastInterviewDate;
    }
    public void setLastInterviewDate(String lastInterviewDate) {
        this.lastInterviewDate = lastInterviewDate;
    }
    public String getOthers() {
        return other;
    }
    public void setOthers(String other) {
        this.other = other;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    private int id;
    private String name;
    private String account;
    private String password;
    private String createDate;
    private String lastInterviewDate;
    private String other;

    public boolean isEmpty() {
        return (name + account + password).equals("");
    }

}

