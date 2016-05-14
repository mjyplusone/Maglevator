package com.example.version1;
       

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;    
    
public class PassActivity extends Activity {  
	private MyApp myApp;
	private EditText  pass_user=null; 
	private EditText  pass_nuser=null; 
	private EditText  pass_again=null; 
	private String user;
	private String nuser;
	private String again;
	
	
    @Override    
    protected void onCreate(Bundle savedInstanceState) {   
    	//�Զ������
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);    
        //��������ģʽ��ʼ����ɫ
        myApp = (MyApp)getApplication();  		
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		
        setContentView(R.layout.pass);  
    
        
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
		setContentView(R.layout.pass);
	}
    
	public void newpass(View view) {
		pass_user=(EditText)super.findViewById(R.id.user);  
        pass_nuser=(EditText)super.findViewById(R.id.nuser);  
        pass_again=(EditText)super.findViewById(R.id.again); 
        user=pass_user.getText().toString();
        nuser=pass_nuser.getText().toString();
        again=pass_again.getText().toString();
		
		if(!TextUtils.isEmpty(user.trim())) 
		{
			if(nuser.equals(again)&&(!TextUtils.isEmpty(nuser.trim()))&&(!TextUtils.isEmpty(again.trim())))
			{
				 // �����̴߳���  
	            new Thread(new Runnable() {  
	                @Override  
	                public void run() {  
	                    //���ã�loginByHttpClientGet(userName, userPass);  
	                    //���ã�loginByHttpClientPost(userName, userPass);  
	                    SignByHttpClientPost(myApp.getName(), user, nuser);//����  
	                }  
	            }).start(); 
			}
			else
			{
				Toast.makeText(PassActivity.this, "������Ϊ�ջ��������벻һ�£�", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(PassActivity.this, "ԭ����Ϊ�գ�", Toast.LENGTH_SHORT).show();
		}
			
	}
	//����post�����޸�����
	public void SignByHttpClientPost(String userName, String userPass, String userNewPass) {
		
		//ע�� �������˻�ûд
		// Dialog dialog;
		// dialog = ProgressDialog.show(SignActivity.this, "Isee",  "����ע�� ��", true, true);
		 HttpClient client = new DefaultHttpClient();  
		 String httpUrl = "http://115.28.147.177/server/changePassword.php"; 		  
		//HttpPost���Ӷ��� 
		HttpPost httpRequest = new HttpPost(httpUrl); 
		try {  
            //��װ���ݲ����ļ���  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            //����������������Ҫ���ݵĲ���
            parameters.add(new BasicNameValuePair("username", userName));  
            parameters.add(new BasicNameValuePair("userpassword", userPass)); 
            parameters.add(new BasicNameValuePair("userpassword_new", userNewPass)); 
            //�������ݲ�����װ ʵ�����  
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//���ô��ݲ����ı���  
            //��ʵ�������뵽httpPost������  
            httpRequest.setEntity(entity); 
            
            String cookieString;
        	myApp = (MyApp)getApplication();  
        	cookieString = myApp.getCookie();
        	//��cookies�ӵ�httpͷ��
        	httpRequest.addHeader("Cookie", cookieString);
            
            //3. ���õ�һ���д����õ�ʵ���� execute ������ִ�еڶ����д����õ� method ʵ��  
            HttpResponse httpResponse = client.execute(httpRequest); //HttpUriRequest�ĺ������ //�����������һ�»س�  
            
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
			{ 
				//ȡ�÷��ص��ַ��� 
				String strResult = EntityUtils.toString(httpResponse.getEntity()); 
				String stateflag;
				
				Log.i("debug",strResult);
				
				JSONObject jsonObject = new JSONObject(strResult);    
				JSONObject jsonObject2 = jsonObject.getJSONObject("users");  
				stateflag=jsonObject2.getString("stateflag");
				
				if(stateflag.equals("true"))
				{
					//dialog.dismiss();
					//ע��ɹ���ת����½
					Looper.prepare();
					Toast.makeText(PassActivity.this, "�޸ĳɹ� ��", Toast.LENGTH_SHORT).show();
					Looper.loop();					
				}
				else        
				{
					Looper.prepare();
					Toast.makeText(PassActivity.this, "�޸�ʧ�ܣ��û��������벻��ȷ", Toast.LENGTH_SHORT).show();
					Looper.loop();
				}
			}		
            
		}
		catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
            //6. �ͷ����ӡ�����ִ�з����Ƿ�ɹ����������ͷ�����  
            client.getConnectionManager().shutdown();  
        } 
}
       
}   	