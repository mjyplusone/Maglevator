package com.example.version1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

    
public class OpinionActivity extends Activity {    
	private MyApp myApp;
	private Spinner  sp;  
	private String str;
    
    @Override    
    protected void onCreate(Bundle savedInstanceState) {   
    	//�Զ������
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);  
        
        myApp = (MyApp)getApplication();  		
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		        
        setContentView(R.layout.opinion);  
                
    }    
  
    
    public void onClick(View widget) {
    	//��ȡ�������
    	sp = (Spinner) findViewById(R.id.feedback_type_spinner);  
    	str = "�������";
    	sp.setOnItemSelectedListener(new OnItemSelectedListener() {  
	  
            @Override  
            public void onItemSelected(AdapterView<?> parent, View view,  
                    int position, long id) {  
                
                //�õ���ѡ�����ֵ  
                str = (String) sp.getSelectedItem();  
                //�Ѹ�ֵ���� TextView  
                //tv.setText(str);  
            }  
  
            @Override  
            public void onNothingSelected(AdapterView<?> parent) {  
                // TODO Auto-generated method stub  
                  
            }  
        });  
    	
    	//��ȡ���
    	EditText feedback_content=(EditText)findViewById(R.id.feedback_content);
		final String feedback=feedback_content.getText().toString();
    	
		new Thread(new Runnable() {  
            @Override  
            public void run() {  
                //���ã�loginByHttpClientGet(userName, userPass);  
                //���ã�loginByHttpClientPost(userName, userPass);  
                loginByHttpClientPost("���⣺"+str+"\n"+"����:"+feedback);//����  
            }  
        }).start();  
    }
    
    
    
    
  //��post����
  	public void loginByHttpClientPost(String content) {
  		//CookieSyncManager.createInstance(MainActivity.this);
  		// ÿ�ε�¼������ʱ�������cookie
  		//removeAllCookie();
  	
  		//1.���� HttpClient ��ʵ��  
  		DefaultHttpClient client = new DefaultHttpClient();  
          //2. ����ĳ�����ӷ�����ʵ������������HttpPost���� HttpPost �Ĺ��캯���д�������ӵĵ�ַ  
          String url="http://115.28.147.177/server/feedback.php";  
          HttpPost httpPost = new HttpPost(url);  
          try {  
              //��װ���ݲ����ļ���  
              List<NameValuePair> parameters = new ArrayList<NameValuePair>(); 
              
              myApp = (MyApp)getApplication();                         
              String user = myApp.getName();
              
              //����������������Ҫ���ݵĲ���
              //parameters.add(new BasicNameValuePair("username", user));  
              parameters.add(new BasicNameValuePair("content", content));  
              
              //�������ݲ�����װ ʵ����� 
              UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//���ô��ݲ����ı���  
              //��ʵ�������뵽httpPost������  
              httpPost.setEntity(entity);  
              
              String cookieString;
	          myApp = (MyApp)getApplication();  
	          cookieString = myApp.getCookie();
	          //��cookies�ӵ�httpͷ��
              httpPost.addHeader("Cookie", cookieString);
              
              //3. ���õ�һ���д����õ�ʵ���� execute ������ִ�еڶ����д����õ� method ʵ��  
              HttpResponse response = client.execute(httpPost); //HttpUriRequest�ĺ������ //�����������һ�»س�  
              
              
              if (response.getStatusLine().getStatusCode() == 200)
  			  {      
  				
              	String stateflag;
  				String result = EntityUtils.toString(response.getEntity());	
  				Log.i("debug", result); //��ȡ��Ӧ����
  				
  				//���������ʽ��json
  				//{"users":{"stateflag":"true"}}
  				JSONObject jsonObject = new JSONObject(result);    
  				JSONObject jsonObject2 = jsonObject.getJSONObject("users");  
  				stateflag=jsonObject2.getString("stateflag");
  				if(stateflag.equals("true"))
  				{
  					Looper.prepare();
  					Toast.makeText(OpinionActivity.this, "���Լ�¼�ѷ��ͣ����ǻᾡ�촦��", Toast.LENGTH_LONG).show();
  					Looper.loop();
  				}
  				else
  				{
  					Looper.prepare();
  					Toast.makeText(OpinionActivity.this, "���Լ�¼����ʧ�ܣ�", Toast.LENGTH_LONG).show();
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