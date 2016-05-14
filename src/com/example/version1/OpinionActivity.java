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
    	//自定义标题
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
    	//获取意见主题
    	sp = (Spinner) findViewById(R.id.feedback_type_spinner);  
    	str = "电机故障";
    	sp.setOnItemSelectedListener(new OnItemSelectedListener() {  
	  
            @Override  
            public void onItemSelected(AdapterView<?> parent, View view,  
                    int position, long id) {  
                
                //拿到被选择项的值  
                str = (String) sp.getSelectedItem();  
                //把该值传给 TextView  
                //tv.setText(str);  
            }  
  
            @Override  
            public void onNothingSelected(AdapterView<?> parent) {  
                // TODO Auto-generated method stub  
                  
            }  
        });  
    	
    	//获取意见
    	EditText feedback_content=(EditText)findViewById(R.id.feedback_content);
		final String feedback=feedback_content.getText().toString();
    	
		new Thread(new Runnable() {  
            @Override  
            public void run() {  
                //调用：loginByHttpClientGet(userName, userPass);  
                //调用：loginByHttpClientPost(userName, userPass);  
                loginByHttpClientPost("标题："+str+"\n"+"内容:"+feedback);//测试  
            }  
        }).start();  
    }
    
    
    
    
  //发post请求
  	public void loginByHttpClientPost(String content) {
  		//CookieSyncManager.createInstance(MainActivity.this);
  		// 每次登录操作的时候先清除cookie
  		//removeAllCookie();
  	
  		//1.创建 HttpClient 的实例  
  		DefaultHttpClient client = new DefaultHttpClient();  
          //2. 创建某种连接方法的实例，在这里是HttpPost。在 HttpPost 的构造函数中传入待连接的地址  
          String url="http://115.28.147.177/server/feedback.php";  
          HttpPost httpPost = new HttpPost(url);  
          try {  
              //封装传递参数的集合  
              List<NameValuePair> parameters = new ArrayList<NameValuePair>(); 
              
              myApp = (MyApp)getApplication();                         
              String user = myApp.getName();
              
              //往这个集合中添加你要传递的参数
              //parameters.add(new BasicNameValuePair("username", user));  
              parameters.add(new BasicNameValuePair("content", content));  
              
              //创建传递参数封装 实体对象 
              UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//设置传递参数的编码  
              //把实体对象存入到httpPost对象中  
              httpPost.setEntity(entity);  
              
              String cookieString;
	          myApp = (MyApp)getApplication();  
	          cookieString = myApp.getCookie();
	          //将cookies加到http头部
              httpPost.addHeader("Cookie", cookieString);
              
              //3. 调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例  
              HttpResponse response = client.execute(httpPost); //HttpUriRequest的后代对象 //在浏览器中敲一下回车  
              
              
              if (response.getStatusLine().getStatusCode() == 200)
  			  {      
  				
              	String stateflag;
  				String result = EntityUtils.toString(response.getEntity());	
  				Log.i("debug", result); //获取响应内容
  				
  				//解析下面格式的json
  				//{"users":{"stateflag":"true"}}
  				JSONObject jsonObject = new JSONObject(result);    
  				JSONObject jsonObject2 = jsonObject.getJSONObject("users");  
  				stateflag=jsonObject2.getString("stateflag");
  				if(stateflag.equals("true"))
  				{
  					Looper.prepare();
  					Toast.makeText(OpinionActivity.this, "调试记录已发送，我们会尽快处理！", Toast.LENGTH_LONG).show();
  					Looper.loop();
  				}
  				else
  				{
  					Looper.prepare();
  					Toast.makeText(OpinionActivity.this, "调试记录发送失败！", Toast.LENGTH_LONG).show();
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