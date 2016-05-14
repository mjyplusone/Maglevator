package com.example.version1;
       

import android.app.Activity;    
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;    
    
public class PersonalActivity extends Activity {  
	private MyApp myApp;
	
    @Override    
    protected void onCreate(Bundle savedInstanceState) {   
    	//自定义标题
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);    
        
        //Application传输数据，得到是否处于夜间模式
        myApp = (MyApp)getApplication();  
        //根据所处模式初始化配色
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		
        setContentView(R.layout.personal);  
        
      //设置文本框的数据   
		TextView muser = (TextView) findViewById(R.id.user);
		muser.setText("  " + myApp.getName());
		TextView mcontact = (TextView) findViewById(R.id.contact);
		mcontact.setText("  " + myApp.getContact());
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
		setContentView(R.layout.personal);

	}
       
}   	