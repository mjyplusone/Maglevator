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
    	//自定义标题
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
			//获取编辑框的值
			EditText verify=(EditText)findViewById(R.id.verify);
			final String verify_num=verify.getText().toString();
			 // 开启线程处理  
            new Thread(new Runnable() {  
                @Override  
                public void run() {  
                    //调用：loginByHttpClientGet(userName, userPass);  
                    //调用：loginByHttpClientPost(userName, userPass);  
                    VerByHttpClientPost(verify_num);//测试  
                }  
            }).start(); 
		}
	};
	
	//post请求
	public void VerByHttpClientPost(String userVerify) {
			
			//注册 服务器端还没写
			// Dialog dialog;
			// dialog = ProgressDialog.show(SignActivity.this, "Isee",  "正在注册 …", true, true);
			HttpClient client = new DefaultHttpClient();  
			String httpUrl = "http://115.28.147.177/server/register.php"; 		  
			//HttpPost连接对象 
			HttpPost httpRequest = new HttpPost(httpUrl); 
			try {  
				myApp = (MyApp)getApplication();  
				String userPhone = myApp.getContact(); 
				String userName = myApp.getName(); 
				String userPass = myApp.getPass();
	            //封装传递参数的集合  
	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
	            //往这个集合中添加你要传递的参数
	            parameters.add(new BasicNameValuePair("username", userName));  
	            parameters.add(new BasicNameValuePair("userpassword", userPass));  
	            parameters.add(new BasicNameValuePair("phonenumber", userPhone)); 
	            parameters.add(new BasicNameValuePair("code", userVerify));  
	            //创建传递参数封装 实体对象  
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//设置传递参数的编码  
	            //把实体对象存入到httpPost对象中  
	            httpRequest.setEntity(entity);  
	            //3. 调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例  
	            HttpResponse httpResponse = client.execute(httpRequest); //HttpUriRequest的后代对象 //在浏览器中敲一下回车  
	            
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
				{ 
					//取得返回的字符串 
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
						Toast.makeText(VerifyActivity.this, "验证码不正确或超出时间！", Toast.LENGTH_SHORT).show();
						Looper.loop();
					}
				}		
	            
			}
			catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }finally{  
	            //6. 释放连接。无论执行方法是否成功，都必须释放连接  
	            client.getConnectionManager().shutdown();  
	        } 
	}
    
  
}   	