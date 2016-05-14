package com.example.version1;

import android.app.Application;  

public class MyApp extends Application{  
    public String name; 
    public String pass; 
    public String contact; 
    public String cookie; 
    public String verifycontact;
    public Boolean state;
    public boolean theme_flag=false;
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
    
    public String getPass() {  
        return pass;  
    }  
  
    public void setPass(String pass) {  
        this.pass = pass;  
    }  
    
    public String getContact() {  
        return contact;  
    }  
  
    public void setContact(String contact) {  
        this.contact = contact;  
    } 
    
    public String getCookie() {  
        return cookie;  
    }  
  
    public void setCookie(String cookie) {  
        this.cookie = cookie;  
    } 
    
    public String getVerifyContact() {  
        return verifycontact;  
    }  
  
    public void setVerifyContact(String contact) {  
        this.verifycontact = contact;  
    }  
    
    public Boolean getState() {  
        return state;  
    }  
  
    public void setState(Boolean name) {  
        this.state = name;  
    }  
    
    public Boolean getflag() {  
        return theme_flag;  
    }  
  
    public void setflag(Boolean theme) {  
        this.theme_flag = theme;  
    }   
      
}  