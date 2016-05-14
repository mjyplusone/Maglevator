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
    	//自定义标题
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);    
        //根据所处模式初始化配色
        myApp = (MyApp)getApplication();  		
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		
        setContentView(R.layout.pass);  
    
        
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
				 // 开启线程处理  
	            new Thread(new Runnable() {  
	                @Override  
	                public void run() {  
	                    //调用：loginByHttpClientGet(userName, userPass);  
	                    //调用：loginByHttpClientPost(userName, userPass);  
	                    SignByHttpClientPost(myApp.getName(), user, nuser);//测试  
	                }  
	            }).start(); 
			}
			else
			{
				Toast.makeText(PassActivity.this, "新密码为空或两次输入不一致！", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(PassActivity.this, "原密码为空！", Toast.LENGTH_SHORT).show();
		}
			
	}
	//发送post请求修改密码
	public void SignByHttpClientPost(String userName, String userPass, String userNewPass) {
		
		//注册 服务器端还没写
		// Dialog dialog;
		// dialog = ProgressDialog.show(SignActivity.this, "Isee",  "正在注册 …", true, true);
		 HttpClient client = new DefaultHttpClient();  
		 String httpUrl = "http://115.28.147.177/server/changePassword.php"; 		  
		//HttpPost连接对象 
		HttpPost httpRequest = new HttpPost(httpUrl); 
		try {  
            //封装传递参数的集合  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            //往这个集合中添加你要传递的参数
            parameters.add(new BasicNameValuePair("username", userName));  
            parameters.add(new BasicNameValuePair("userpassword", userPass)); 
            parameters.add(new BasicNameValuePair("userpassword_new", userNewPass)); 
            //创建传递参数封装 实体对象  
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//设置传递参数的编码  
            //把实体对象存入到httpPost对象中  
            httpRequest.setEntity(entity); 
            
            String cookieString;
        	myApp = (MyApp)getApplication();  
        	cookieString = myApp.getCookie();
        	//将cookies加到http头部
        	httpRequest.addHeader("Cookie", cookieString);
            
            //3. 调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例  
            HttpResponse httpResponse = client.execute(httpRequest); //HttpUriRequest的后代对象 //在浏览器中敲一下回车  
            
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
			{ 
				//取得返回的字符串 
				String strResult = EntityUtils.toString(httpResponse.getEntity()); 
				String stateflag;
				
				Log.i("debug",strResult);
				
				JSONObject jsonObject = new JSONObject(strResult);    
				JSONObject jsonObject2 = jsonObject.getJSONObject("users");  
				stateflag=jsonObject2.getString("stateflag");
				
				if(stateflag.equals("true"))
				{
					//dialog.dismiss();
					//注册成功跳转到登陆
					Looper.prepare();
					Toast.makeText(PassActivity.this, "修改成功 ！", Toast.LENGTH_SHORT).show();
					Looper.loop();					
				}
				else        
				{
					Looper.prepare();
					Toast.makeText(PassActivity.this, "修改失败！用户名或密码不正确", Toast.LENGTH_SHORT).show();
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