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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private DrawerLayout mDrawerLayout = null;
	private MyApp myApp;
	private CheckBox chk;
	private int themes;
	
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
		setContentView(R.layout.page2);
		//设置标题为某个layout
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		find_and_modify_button();
		
		
        //设置文本框的数据   
		TextView muser = (TextView) findViewById(R.id.username);
		muser.setText("  " + myApp.getName());
				
	}
	
	//初始化按键
	private void find_and_modify_button() 
	{
		//drawerlayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		//ViewGroup personal = (ViewGroup)findViewById(R.id.personal);
		//personal.setOnClickListener(personal_listener);
		
	}
	
	public void Theme(View view) {
		CheckBox checkBox =(CheckBox)findViewById(R.id.switch1);
		Boolean state = checkBox.isChecked();
		myApp = (MyApp)getApplication();  
	    myApp.setState(state); 
	    Log.i("Debug", state.toString());
	    //检测是否处于夜间模式
		if(myApp.getflag()==true)  myApp.setflag(false);
		else                  myApp.setflag(true);
		//定义主题
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		setContentView(R.layout.page2);
		
		//以下这些必须要再声明一遍，不然重启activity以后无法打开drawerlayout！！！
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);     
        //设置文本框的数据   
		TextView muser = (TextView) findViewById(R.id.username);
		muser.setText("  " + myApp.getName());
		//设置重启后checkbox的状态
		CheckBox checkBox1 =(CheckBox)findViewById(R.id.switch1);
		if(myApp.getState())  checkBox1.setChecked(true);
		else                  checkBox1.setChecked(false);
	}
	//图片按键监听事件
	public void onChart1(View view) {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, test.class);
		startActivity(intent);
	}
	//图片按键监听事件
	public void onChart2(View view) {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, Chart2Activity.class);
		startActivity(intent);
	}
	//图片按键监听事件
	public void onChart3(View view) {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, OpinionActivity.class);
		startActivity(intent);
	}
	//图片按键监听事件
	public void onChart4(View view) {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, IntroActivity.class);
		startActivity(intent);
	}
	//侧滑菜单监听事件
	public void imagebtn(View view) {
		// 按钮按下，将抽屉打开
        mDrawerLayout.openDrawer(Gravity.RIGHT);       
	}
	//侧滑菜单中选项监听事件
	public void personal(View view) {
		// 按钮按下，将抽屉打开
		mDrawerLayout.closeDrawers();
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, PersonalActivity.class);
		startActivity(intent);	
		//动画效果
		overridePendingTransition(R.anim.fade_out, R.anim.fade_in);       
	}
	//侧滑菜单中选项监听事件
	public void pass(View view) {
		// 按钮按下，将抽屉打开
		mDrawerLayout.closeDrawers();
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, PassActivity.class);
		startActivity(intent);	
		//动画效果
		overridePendingTransition(R.anim.fade_out, R.anim.fade_in);       
	}
	//侧滑菜单中选项监听事件
		public void logout(View view) {
            
            new AlertDialog.Builder(LoginActivity.this).setTitle("注销")//设置对话框标题             
            .setMessage("确定退出当前账号嘛？")//设置显示的内容          
            .setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加确定按钮  
                @Override           
                public void onClick(DialogInterface dialog, int which) {//取消按钮的响应事件           
                    // TODO Auto-generated method stub          
                }  
         
            })
            .setPositiveButton("确定退出",new DialogInterface.OnClickListener() {//添加确定按钮  
              @Override  
              public void onClick(DialogInterface dialog, int which) {//响应事件       
            	//告诉服务器清除cookies
      			LogoutPost();
      			//清除本地文件的值
      			myApp = (MyApp)getApplication();                         
                myApp.setCookie ("");
                mDrawerLayout.closeDrawers();
                  
                //跳转登录界面
                Intent intent = new Intent();
        		intent.setClass(LoginActivity.this, MainActivity.class);
        		startActivity(intent);	
        		//结束login界面
        		LoginActivity.this.finish();         
                }          
            }).show();//在按键响应事件中显示此对话框  
//            //跳转登录界面
//            Intent intent = new Intent();
//    		intent.setClass(LoginActivity.this, MainActivity.class);
//    		startActivity(intent);	
//    		//结束login界面
//    		LoginActivity.this.finish();
		}
	//侧滑菜单中选项监听事件
	public void about(View view) {
		// 按钮按下，将抽屉打开
		mDrawerLayout.closeDrawers();
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, AboutActivity.class);
		startActivity(intent);	
		//动画效果
		overridePendingTransition(R.anim.fade_out, R.anim.fade_in);       
	}
	
	
	
	//发post请求
		public void LogoutPost() {
			//CookieSyncManager.createInstance(MainActivity.this);
			// 每次登录操作的时候先清除cookie
			//removeAllCookie();
		
			//1.创建 HttpClient 的实例  
			DefaultHttpClient client = new DefaultHttpClient();  
	        //2. 创建某种连接方法的实例，在这里是HttpPost。在 HttpPost 的构造函数中传入待连接的地址  
	        String url="http://115.28.147.177/server/logout.php";  
	        HttpPost httpPost = new HttpPost(url);  
	        try {  
	            //封装传递参数的集合  
	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  	           
	            
	            //创建传递参数封装 实体对象 
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//设置传递参数的编码  
	            //把实体对象存入到httpPost对象中  
	            httpPost.setEntity(entity);  
	            //3. 调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例  
	            HttpResponse response = client.execute(httpPost); //HttpUriRequest的后代对象 //在浏览器中敲一下回车  
						
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

	