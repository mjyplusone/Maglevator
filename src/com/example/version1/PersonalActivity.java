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
    	//�Զ������
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);    
        
        //Application�������ݣ��õ��Ƿ���ҹ��ģʽ
        myApp = (MyApp)getApplication();  
        //��������ģʽ��ʼ����ɫ
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		
        setContentView(R.layout.personal);  
        
      //�����ı��������   
		TextView muser = (TextView) findViewById(R.id.user);
		muser.setText("  " + myApp.getName());
		TextView mcontact = (TextView) findViewById(R.id.contact);
		mcontact.setText("  " + myApp.getContact());
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
		setContentView(R.layout.personal);

	}
       
}   	