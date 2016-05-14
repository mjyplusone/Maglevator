package com.example.version1;
       

import android.app.Activity;    
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;    
    
public class AboutActivity extends Activity {  
	private MyApp myApp;
	
    @Override    
    protected void onCreate(Bundle savedInstanceState) {   
    	//自定义标题
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);    
        
        myApp = (MyApp)getApplication();  
      //根据所处模式初始化配色
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		
        setContentView(R.layout.about);  
        
    }    
    //根据所处模式设置配色
    public void Theme(View view) {
		myApp = (MyApp)getApplication();  
	    
		if(myApp.getflag()==true)  myApp.setflag(false);
		else                  myApp.setflag(true);
		
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		setContentView(R.layout.about);
		
		/*
		//以下这些必须要再声明一遍，不然重启activity以后无法打开drawerlayout！！！
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);     
        //设置文本框的数据   
		myApp = (MyApp)getApplication();  
		TextView muser = (TextView) findViewById(R.id.username);
		muser.setText("  " + myApp.getName());
		//设置重启后checkbox的状态
		CheckBox checkBox1 =(CheckBox)findViewById(R.id.switch1);
		if(myApp.getState())  checkBox1.setChecked(true);
		else                  checkBox1.setChecked(false);
		*/
	}
       
}   	