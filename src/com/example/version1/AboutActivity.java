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
    	//�Զ������
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);    
        
        myApp = (MyApp)getApplication();  
      //��������ģʽ��ʼ����ɫ
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		
        setContentView(R.layout.about);  
        
    }    
    //��������ģʽ������ɫ
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
		//������Щ����Ҫ������һ�飬��Ȼ����activity�Ժ��޷���drawerlayout������
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);     
        //�����ı��������   
		myApp = (MyApp)getApplication();  
		TextView muser = (TextView) findViewById(R.id.username);
		muser.setText("  " + myApp.getName());
		//����������checkbox��״̬
		CheckBox checkBox1 =(CheckBox)findViewById(R.id.switch1);
		if(myApp.getState())  checkBox1.setChecked(true);
		else                  checkBox1.setChecked(false);
		*/
	}
       
}   	