package com.example.version1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG="MainActivity";
	private ImageView mImage1;
	private ImageView mImage2;
	private ImageView xy_img;
	private EditText user_img;
	private EditText pass_img;
	private Button log_img;
	private ImageView num1;
	private ImageView num2;
	private ImageView strut;
	private RelativeLayout layout;
	public CookieManager cookieManager = null;
	public static String cookies;
	
	TextView mTextView1 = null;     
    SpannableString msp1 = null; 
    TextView mTextView2 = null;     
    SpannableString msp2 = null; 
	
	boolean hasMeasured = false;
	
	private MyApp myApp;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		SharedPreferences userInfo1 = getSharedPreferences("user_info",0);
		final String aaa =userInfo1.getString("info", ""); 
		new Thread(new Runnable() {  
            @Override  
            public void run() {  
                //调用：loginByHttpClientGet(userName, userPass);  
                //调用：loginByHttpClientPost(userName, userPass);  
                CookiesGet(aaa);//测试  
            }  
        }).start();  
		
		
		//自定义标题
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		//设置标题为某个layout
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		
		//登录界面圆形背景透明度设置
		mImage1 = (ImageView) findViewById(R.id.circle1);
		mImage1.getBackground().setAlpha(20);  //0~255透明度值
		
		mImage2 = (ImageView) findViewById(R.id.circle2);
		mImage2.getBackground().setAlpha(25);  //0~255透明度值
		
		layout = (RelativeLayout)findViewById(R.id.circle);
		xy_img = (ImageView)findViewById(R.id.xy);
		user_img = (EditText)findViewById(R.id.user);
		pass_img = (EditText)findViewById(R.id.pass);
		log_img = (Button)findViewById(R.id.login);
		num1 = (ImageView)findViewById(R.id.num1);
		num2 = (ImageView)findViewById(R.id.num2);
		strut = (ImageView)findViewById(R.id.strut);

	    ViewTreeObserver vto = layout.getViewTreeObserver();
	    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
	      @Override
	      public boolean onPreDraw() {
	        if (hasMeasured == false)
	      {	
	          //获取homelayout的宽度和高度
	        int height = layout.getMeasuredHeight();  
	        //int width = homelayout.getMeasuredWidth();
	        
	        //登录界面按比例排版
	        android.view.ViewGroup.LayoutParams lp1 =mImage1.getLayoutParams();
	        lp1.height=height;
	        lp1.width=height;
	        
	        android.view.ViewGroup.LayoutParams lp2 =mImage2.getLayoutParams();
	        lp2.height=height*5/6;
	        lp2.width=height*5/6;
	        
	        android.view.ViewGroup.LayoutParams lp3 =xy_img.getLayoutParams();
	        lp3.height=height*25/213;
	        lp3.width=height*90/213;
	        
	        android.view.ViewGroup.LayoutParams lp4 =user_img.getLayoutParams();
	        lp4.height=height*23/213;
	        lp4.width=height*120/213;
	        
	        android.view.ViewGroup.LayoutParams lp5 =pass_img.getLayoutParams();
	        lp5.height=height*23/213;
	        lp5.width=height*120/213;
	        
	        android.view.ViewGroup.LayoutParams lp6 =log_img.getLayoutParams();
	        lp6.height=height*20/213;
	        lp6.width=height*50/213;
	        
	        android.view.ViewGroup.LayoutParams lp7 =num1.getLayoutParams();
	        lp7.height=height*20/213;
	        lp7.width=height*50/213;
	        
	        android.view.ViewGroup.LayoutParams lp8 =strut.getLayoutParams();
	        lp8.height=height*12/213;
	        lp8.width=height*50/213;
	        
	        android.view.ViewGroup.LayoutParams lp9 =num2.getLayoutParams();
	        lp9.height=height*20/213;
	        lp9.width=height*50/213;
	       
	        hasMeasured = true;
	      }
	        return true;
	      }
	    });
		
		find_and_modify_button(); 
		
		//文字链接 跳转activity
		String vo = null;
		mTextView1 = (TextView)findViewById(R.id.edit_text1);  
		msp1 = new SpannableString("注册账号");
		NoLineClickSpan clickSpan = new NoLineClickSpan(vo);
		msp1.setSpan(clickSpan, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		/*另一种方法
		msp1.setSpan(new ClickableSpan() {  
            @Override  
            public void onClick(View view) {  
                Intent intent=new Intent(MainActivity.this,SignActivity.class);  
                startActivity(intent);  
                  
            }  
        }, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  */	
		mTextView1.setText(msp1);  
        mTextView1.setMovementMethod(LinkMovementMethod.getInstance());   
        
      //文字链接 跳转activity
        mTextView2 = (TextView)findViewById(R.id.edit_text2);
        msp2 = new SpannableString("忘记密码");
		NoLineClickSpan clickSpan2 = new NoLineClickSpan(vo);
		msp2.setSpan(clickSpan2, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		mTextView2.setText(msp2);  
        mTextView2.setMovementMethod(LinkMovementMethod.getInstance());  
        
		
	}
	
	//初始化按键
	private void find_and_modify_button() 
	{
		Button button_login = (Button) findViewById(R.id.login);
		button_login.setOnClickListener(btn1_listener);
	
		/*
		Button button_signin = (Button) findViewById(R.id.btn2);
		button_signin.setOnClickListener(btn2_listener);*/
	}
	
	//登录按键监听事件
	private Button.OnClickListener btn1_listener = new Button.OnClickListener() 
	{
		public void onClick(View v)
		{
			Log.i(TAG, "POST request");
					
			//获取填写的用户名和密码
			EditText unameEt=(EditText)findViewById(R.id.user);
			EditText upassEt=(EditText)findViewById(R.id.pass);
			final String name=unameEt.getText().toString();
			final String pass=upassEt.getText().toString();
			myApp = (MyApp)getApplication();  
		    myApp.setName(name); 
		    myApp.setPass(pass);
			Log.i(TAG, name+pass);
			//密码不为空
			if (TextUtils.isEmpty(name.trim())  || TextUtils.isEmpty(pass.trim())) {  
                Toast.makeText(MainActivity.this, "用户名或者密码不能为空", Toast.LENGTH_LONG).show();  
            } else {  
                // 开启线程处理  
                new Thread(new Runnable() {  
                    @Override  
                    public void run() {  
                        //调用：loginByHttpClientGet(userName, userPass);  
                        //调用：loginByHttpClientPost(userName, userPass);  
                        loginByHttpClientPost(name, pass);//测试  
                    }  
                }).start();  
            }          
		}		
	};
	
	
	//Get
	public void CookiesGet(String cookie){  
        // HttpClient 发请求 GET方式处理  
        // 1.创建 HttpClient 的实例 打开一个浏览器  
        HttpClient client = new DefaultHttpClient(); // DefaultHttpClient extends AbstractHttpClient  
        try {  
  
            // 2. 创建某种连接方法的实例，在这里是HttpGet。在 HttpGet  
            // 的构造函数中传入待连接的地址     
            String uri = "http://115.28.147.177/server/checkwithfeedback.php";
            //强调 地址不能够出现 localhost:操作  
            HttpGet httpGet = new HttpGet(uri);  
            
            String cookieString;
        	myApp = (MyApp)getApplication();  
        	cookieString = myApp.getCookie();
        	//将cookies加到http头部
            httpGet.addHeader("Cookie", cookieString);
            
            // 3. 调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例  
            HttpResponse response = client.execute(httpGet); // 在浏览器中敲了一下回车  
            // 4. 读 response  
            if (response.getStatusLine().getStatusCode() == 200)
			{      

            	String stateflag;
            	String username = null;
				String result = EntityUtils.toString(response.getEntity());	
				Log.i(TAG, result); //获取响应内容
				
				//解析下面格式的json
				//{"users":{"stateflag":"true","username":"mjy"}}
				JSONObject jsonObject = new JSONObject(result);    
				JSONObject jsonObject2 = jsonObject.getJSONObject("users");  
				stateflag=jsonObject2.getString("stateflag");
				if(stateflag.equals("true"))
				{
					username=jsonObject2.getString("username");
				}
				//通过Application传递数据				
				myApp = (MyApp)getApplication();  
			    
				if(stateflag.equals("true"))
		        {
					Log.i(TAG, "login success!");
					//电话号码放入application
					myApp.setName(username);
					//myApp.setContact(phonenumber); 
					//跳转界面
		        	Intent intent = new Intent();
					intent.setClass(MainActivity.this, LoginActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
		        }
				else
				{
					Log.i(TAG, "cookies fail!");
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
	
	
	//发post请求
	public void loginByHttpClientPost(String userName, String userPass) {
		//CookieSyncManager.createInstance(MainActivity.this);
		// 每次登录操作的时候先清除cookie
		//removeAllCookie();
	
		//1.创建 HttpClient 的实例  
		DefaultHttpClient client = new DefaultHttpClient();  
        //2. 创建某种连接方法的实例，在这里是HttpPost。在 HttpPost 的构造函数中传入待连接的地址  
        String url="http://115.28.147.177/server/login.php";  
        HttpPost httpPost = new HttpPost(url);  
        try {  
            //封装传递参数的集合  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            
            //往这个集合中添加你要传递的参数
            parameters.add(new BasicNameValuePair("username", userName));  
            parameters.add(new BasicNameValuePair("userpassword", userPass)); 
            parameters.add(new BasicNameValuePair("autoflag", "true")); 
            
            //创建传递参数封装 实体对象 
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//设置传递参数的编码  
            //把实体对象存入到httpPost对象中  
            httpPost.setEntity(entity);  
            //3. 调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例  
            HttpResponse response = client.execute(httpPost); //HttpUriRequest的后代对象 //在浏览器中敲一下回车  
            
            
            if (response.getStatusLine().getStatusCode() == 200)
			{      
				//每次登录操作的时候先清除cookie
				//removeAllCookie();
				String cookieString = "";
                // 取得Cookie
                CookieStore mCookieStore = client.getCookieStore();
                List<Cookie> cookies = mCookieStore.getCookies();
                if (cookies.isEmpty()) {
                	Log.i(TAG, "-------Cookie NONE---------"); 
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        // 保存cookie
                        Cookie cookie = cookies.get(i);
                        Log.d("Cookie", cookies.get(i).getName() + "=" + cookies.get(i).getValue());
                        cookieManager = CookieManager.getInstance();
                        String cookieString1 = cookie.getName() + "=" + cookie.getValue() + "; domain=" + cookie.getDomain();
                        cookieManager.setCookie(url, cookieString1);
                        cookieString = cookieString+cookies.get(i).getName() + "=" + cookies.get(i).getValue() + ";";
                        
                    }
                    myApp = (MyApp)getApplication();                         
                    myApp.setCookie (cookieString);

					SharedPreferences userInfo = getSharedPreferences("user_info",0);
					SharedPreferences.Editor editor = userInfo.edit();
					editor.putString("info", cookieString);
					editor.commit();
					
                }
            	
            	
            	String stateflag;
            	String phonenumber = null;
				String result = EntityUtils.toString(response.getEntity());	
				Log.i(TAG, result); //获取响应内容
				
				//解析下面格式的json
				//{"users":{"stateflag":"true","phonenumber":"18861821281"}}
				JSONObject jsonObject = new JSONObject(result);    
				JSONObject jsonObject2 = jsonObject.getJSONObject("users");  
				stateflag=jsonObject2.getString("stateflag");
				if(stateflag.equals("true"))
				{
					phonenumber=jsonObject2.getString("phonenumber");
				}
				//通过Application传递数据				
				myApp = (MyApp)getApplication();  
			    
				if(stateflag.equals("true"))
		        {
					Log.i(TAG, "login success!");
					//电话号码放入application
					myApp.setName(userName);
					myApp.setContact(phonenumber); 
					//跳转界面
		        	Intent intent = new Intent();
					intent.setClass(MainActivity.this, LoginActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
		        }
				else
				{
					Log.i(TAG, "login fail!");
					//提示信息
					Looper.prepare();
					Toast.makeText(MainActivity.this, "用户名或者密码不正确", Toast.LENGTH_LONG).show();
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
	//清除所有cookies
	private void removeAllCookie() {
		  cookieManager = CookieManager.getInstance();
		  cookieManager.removeAllCookie();
		  CookieSyncManager.getInstance().sync();  
		}

	//文字连接跳转注册界面
	private Button.OnClickListener btn2_listener = new Button.OnClickListener() 
	{
		public void onClick(View v)
		{
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, SignActivity.class);
			startActivity(intent);
		}
	};
	

	//重写clickspan类
	private class NoLineClickSpan extends ClickableSpan {
		String text;

		public NoLineClickSpan(String text) {
		    super();
		    this.text = text;
		}

		@Override
		public void updateDrawState(TextPaint ds) {
		    ds.setColor(Color.WHITE);   //字体颜色
		    ds.setUnderlineText(false); //去掉下划线
		}

		
		@Override
		//重写clickspan类的监听事件
		public void onClick(View widget) {
			switch (widget.getId())
			{
				case R.id.edit_text1: 
					//跳转注册界面
					Intent intent=new Intent(MainActivity.this,SignActivity.class);  
			        startActivity(intent); 
			        break;
				case R.id.edit_text2: 
					//跳转忘记密码界面
					Intent intent2=new Intent(MainActivity.this,ForgetActivity.class);  
			        startActivity(intent2); 
			        break;
			}
			/*
			Intent intent=new Intent(MainActivity.this,SignActivity.class);  
	        startActivity(intent); 
	        */
		}
		
	}
	
}



