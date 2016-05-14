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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;    
   
    
public class VerifyActivity extends Activity {    
	private MyApp myApp;
	
    @Override    
    protected void onCreate(Bundle savedInstanceState) {   
    	//�Զ������
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);  
        
        myApp = (MyApp)getApplication();  
        
        setContentView(R.layout.verify);  
        
        EditText muser = (EditText) findViewById(R.id.user);
		muser.setText(myApp.getVerifyContact());
		
		Button tijiao = (Button) findViewById(R.id.tijiao);
		tijiao.setOnClickListener(tijiao_listener);
    }    
    
    
    private Button.OnClickListener tijiao_listener = new Button.OnClickListener() 
	{
		public void onClick(View v)
		{
			
			Log.i("VerifyActivity", "POST request");
			//��ȡ�༭���ֵ
			EditText verify=(EditText)findViewById(R.id.verify);
			final String verify_num=verify.getText().toString();
			 // �����̴߳���  
            new Thread(new Runnable() {  
                @Override  
                public void run() {  
                    //���ã�loginByHttpClientGet(userName, userPass);  
                    //���ã�loginByHttpClientPost(userName, userPass);  
                    VerByHttpClientPost(verify_num);//����  
                }  
            }).start(); 
		}
	};
	
	//post����
	public void VerByHttpClientPost(String userVerify) {
			
			//ע�� �������˻�ûд
			// Dialog dialog;
			// dialog = ProgressDialog.show(SignActivity.this, "Isee",  "����ע�� ��", true, true);
			HttpClient client = new DefaultHttpClient();  
			String httpUrl = "http://115.28.147.177/server/register.php"; 		  
			//HttpPost���Ӷ��� 
			HttpPost httpRequest = new HttpPost(httpUrl); 
			try {  
				myApp = (MyApp)getApplication();  
				String userPhone = myApp.getContact(); 
				String userName = myApp.getName(); 
				String userPass = myApp.getPass();
	            //��װ���ݲ����ļ���  
	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
	            //����������������Ҫ���ݵĲ���
	            parameters.add(new BasicNameValuePair("username", userName));  
	            parameters.add(new BasicNameValuePair("userpassword", userPass));  
	            parameters.add(new BasicNameValuePair("phonenumber", userPhone)); 
	            parameters.add(new BasicNameValuePair("code", userVerify));  
	            //�������ݲ�����װ ʵ�����  
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//���ô��ݲ����ı���  
	            //��ʵ�������뵽httpPost������  
	            httpRequest.setEntity(entity);  
	            //3. ���õ�һ���д����õ�ʵ���� execute ������ִ�еڶ����д����õ� method ʵ��  
	            HttpResponse httpResponse = client.execute(httpRequest); //HttpUriRequest�ĺ������ //�����������һ�»س�  
	            
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
				{ 
					//ȡ�÷��ص��ַ��� 
					String stateflag;
					String strResult = EntityUtils.toString(httpResponse.getEntity()); 
					
					Log.i("debug",strResult);
					
					JSONObject jsonObject = new JSONObject(strResult);    
					JSONObject jsonObject2 = jsonObject.getJSONObject("users");  
					stateflag=jsonObject2.getString("stateflag");
					
					if(stateflag.equals("true"))
					{
						myApp = (MyApp)getApplication();  
						myApp.setContact(userPhone); 
						myApp.setName(userName); 
						
						Intent intent = new Intent();
						intent.setClass(VerifyActivity.this, LoginActivity.class);
						startActivity(intent);
						VerifyActivity.this.finish();						
					}
					else        
					{
						Looper.prepare();
						Toast.makeText(VerifyActivity.this, "��֤�벻��ȷ�򳬳�ʱ�䣡", Toast.LENGTH_SHORT).show();
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