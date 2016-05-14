package com.example.version1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.example.version1.ForgetActivity.TimeCountUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class SignActivity extends Activity {
	private EditText  register_user=null;  
    private EditText  register_pass=null;  
    private EditText  register_phone=null;  
    private MyApp myApp; 
    private Button button_send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//自定义标题
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page1);
		//设置标题为某个layout
      	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		
        find_and_modify_button() ;
        
        register_user=(EditText)super.findViewById(R.id.user);  
        register_pass=(EditText)super.findViewById(R.id.pass);  
        register_phone=(EditText)super.findViewById(R.id.phone);  
        final String contact=register_phone.getText().toString();
        //myApp = (MyApp)getApplication();  
	    //myApp.setContact(contact); 
        //注册OnClick、OnFocusChange监听器  
        //register_user.setOnFocusChangeListener(new UserOnFocusChanageListener()); 
        //register_pass.setOnFocusChangeListener(new PassOnFocusChanageListener()); 
    
    }
	//按键初始化
	private void find_and_modify_button() 
	{
		Button button_sign = (Button) findViewById(R.id.sign_btn);
		button_sign.setOnClickListener(btn1_listener);
		
		button_send = (Button) findViewById(R.id.send);
		button_send.setOnClickListener(btn2_listener);
	}
	
	
	//发送验证码按键监听事件
	private Button.OnClickListener btn2_listener = new Button.OnClickListener() 
	{
		
		public void onClick(View v)
		{
			//检查输入信息的合理性
			if(!checkEdit()){
				return;
			}
			
			//获取编辑框的值
			EditText verify_user=(EditText)findViewById(R.id.user);
			EditText verify_pass=(EditText)findViewById(R.id.pass);
			EditText verify_phone=(EditText)findViewById(R.id.phone);
			final String name=verify_user.getText().toString();
			final String pass=verify_pass.getText().toString();
			final String phone=verify_phone.getText().toString();
			
			//button倒计时
			TimeCountUtil timeCountUtil = new TimeCountUtil(SignActivity.this, 120000, 1000, button_send);
			timeCountUtil.start();
			
			 new Thread(new Runnable() {  
	                @Override  
	                public void run() {  
	                    //调用：loginByHttpClientGet(userName, userPass);  
	                    //调用：loginByHttpClientPost(userName, userPass);  
	                    VerifyHttpClientPost(name, pass, phone);//测试  
	                }  
	            }).start(); 
			
		}		
		
	};
		
		
	//注册按键监听事件
	private Button.OnClickListener btn1_listener = new Button.OnClickListener() 
	{
		public void onClick(View v)
		{
			if(!checkEdit()){
				return;
			}
			
			Log.i("SignActivity", "POST request");
			//获取编辑框的值
			EditText register_user=(EditText)findViewById(R.id.user);
			EditText register_pass=(EditText)findViewById(R.id.pass);
			EditText register_phone=(EditText)findViewById(R.id.phone);
			EditText register_code=(EditText)findViewById(R.id.verify);
			final String name=register_user.getText().toString();
			final String pass=register_pass.getText().toString();
			final String phone=register_phone.getText().toString();
			final String code=register_code.getText().toString();

			
			 // 开启线程处理  
            new Thread(new Runnable() {  
                @Override  
                public void run() {  
                    //调用：loginByHttpClientGet(userName, userPass);  
                    //调用：loginByHttpClientPost(userName, userPass);  
                    SignByHttpClientPost(name, pass, phone, code);//测试  
                }  
            }).start(); 
		}
	};
	
	
	//post请求
	public void VerifyHttpClientPost(String userName, String userPass, String userPhone) {
			
			//注册 服务器端还没写
			// Dialog dialog;
			// dialog = ProgressDialog.show(SignActivity.this, "Isee",  "正在注册 …", true, true);
		    HttpClient client = new DefaultHttpClient();  
			String httpUrl = "http://115.28.147.177/server/register.php"; 		  
			//HttpPost连接对象 
			HttpPost httpRequest = new HttpPost(httpUrl); 
			try {  
	            //封装传递参数的集合  
	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
	            //往这个集合中添加你要传递的参数
	            parameters.add(new BasicNameValuePair("username", userName));  
	            parameters.add(new BasicNameValuePair("userpassword", userPass));  
	            parameters.add(new BasicNameValuePair("phonenumber", userPhone)); 
	            parameters.add(new BasicNameValuePair("code", ""));  
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
						Looper.prepare();
						Toast.makeText(SignActivity.this, "已向手机号"+userPhone+"发送验证码", Toast.LENGTH_SHORT).show();
						Looper.loop();						
					}
					else        
					{
						Looper.prepare();
						Toast.makeText(SignActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
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
	
	
	//post请求
	public void SignByHttpClientPost(String userName, String userPass, String userPhone, String userCode) {
			
			if(userCode.equals(""))
			{
				userCode="error";
			}
			//注册 服务器端还没写
			// Dialog dialog;
			// dialog = ProgressDialog.show(SignActivity.this, "Isee",  "正在注册 …", true, true);
			 HttpClient client = new DefaultHttpClient();  
			 String httpUrl = "http://115.28.147.177/server/register.php"; 		  
			//HttpPost连接对象 
			HttpPost httpRequest = new HttpPost(httpUrl); 
			try {  
	            //封装传递参数的集合  
	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
	            //往这个集合中添加你要传递的参数
	            parameters.add(new BasicNameValuePair("username", userName));  
	            parameters.add(new BasicNameValuePair("userpassword", userPass));  
	            parameters.add(new BasicNameValuePair("phonenumber", userPhone)); 
	            parameters.add(new BasicNameValuePair("code", userCode));  
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
						//dialog.dismiss();
						//注册成功跳转到登陆
						//Looper.prepare();
						//Toast.makeText(SignActivity.this, "注册成功 ！", Toast.LENGTH_SHORT).show();
						//Looper.loop();
						
						myApp = (MyApp)getApplication();  
						myApp.setContact(userPhone); 
						myApp.setName(userName); 
						
						Intent intent = new Intent();
						intent.setClass(SignActivity.this, LoginActivity.class);
						startActivity(intent);
						SignActivity.this.finish();
						
					}
					else        
					{
						Looper.prepare();
						Toast.makeText(SignActivity.this, "注册失败！验证码错误", Toast.LENGTH_SHORT).show();
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
			
	//输入框合理性检测（包括用户名不能因为空，两次密码要一致等）
	private boolean checkEdit(){
		EditText register_user=(EditText)findViewById(R.id.user);
		EditText register_pass=(EditText)findViewById(R.id.pass);
		EditText register_pass2=(EditText)findViewById(R.id.pass2);
		if(register_user.getText().toString().trim().equals("")){
			Toast.makeText(SignActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
		}else if(register_pass.getText().toString().trim().equals("")){
			Toast.makeText(SignActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
		}else if(!register_pass.getText().toString().trim().equals(register_pass2.getText().toString().trim())){
			Toast.makeText(SignActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
		}else{
			return true;
		}
		return false;
	}
	
	
	public class TimeCountUtil extends CountDownTimer {
		private Activity mActivity;
		private Button btn;//按钮

		// 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个是，就把这个按钮传过来就可以了
		public TimeCountUtil( Activity mActivity,long millisInFuture, long countDownInterval,Button btn) {
		super(millisInFuture, countDownInterval);
		this.mActivity = mActivity;
		this.btn =btn;
		}


		@SuppressLint("NewApi")
		@Override
		public void onTick(long millisUntilFinished) {
		btn.setClickable(false);//设置不能点击
		btn.setText(millisUntilFinished / 1000 + "秒后重发");//设置倒计时时间

		//设置按钮为灰色，这时是不能点击的
		btn.setBackground(mActivity.getResources().getDrawable(R.drawable.login_button_selector));
		Spannable span = new SpannableString(btn.getText().toString());//获取按钮的文字
		span.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色
		btn.setText(span);

		}


		@SuppressLint("NewApi")
		@Override
		public void onFinish() {
		btn.setText("重新获取");
		btn.setClickable(true);//重新获得点击
		btn.setBackground(mActivity.getResources().getDrawable(R.drawable.login_button_selector));//还原背景色

		}


	}
	
}







