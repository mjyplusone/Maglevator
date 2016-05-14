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
		//�Զ������
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		
		myApp = (MyApp)getApplication();  
		
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		setContentView(R.layout.page2);
		//���ñ���Ϊĳ��layout
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		find_and_modify_button();
		
		
        //�����ı��������   
		TextView muser = (TextView) findViewById(R.id.username);
		muser.setText("  " + myApp.getName());
				
	}
	
	//��ʼ������
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
	    //����Ƿ���ҹ��ģʽ
		if(myApp.getflag()==true)  myApp.setflag(false);
		else                  myApp.setflag(true);
		//��������
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		setContentView(R.layout.page2);
		
		//������Щ����Ҫ������һ�飬��Ȼ����activity�Ժ��޷���drawerlayout������
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);     
        //�����ı��������   
		TextView muser = (TextView) findViewById(R.id.username);
		muser.setText("  " + myApp.getName());
		//����������checkbox��״̬
		CheckBox checkBox1 =(CheckBox)findViewById(R.id.switch1);
		if(myApp.getState())  checkBox1.setChecked(true);
		else                  checkBox1.setChecked(false);
	}
	//ͼƬ���������¼�
	public void onChart1(View view) {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, test.class);
		startActivity(intent);
	}
	//ͼƬ���������¼�
	public void onChart2(View view) {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, Chart2Activity.class);
		startActivity(intent);
	}
	//ͼƬ���������¼�
	public void onChart3(View view) {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, OpinionActivity.class);
		startActivity(intent);
	}
	//ͼƬ���������¼�
	public void onChart4(View view) {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, IntroActivity.class);
		startActivity(intent);
	}
	//�໬�˵������¼�
	public void imagebtn(View view) {
		// ��ť���£��������
        mDrawerLayout.openDrawer(Gravity.RIGHT);       
	}
	//�໬�˵���ѡ������¼�
	public void personal(View view) {
		// ��ť���£��������
		mDrawerLayout.closeDrawers();
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, PersonalActivity.class);
		startActivity(intent);	
		//����Ч��
		overridePendingTransition(R.anim.fade_out, R.anim.fade_in);       
	}
	//�໬�˵���ѡ������¼�
	public void pass(View view) {
		// ��ť���£��������
		mDrawerLayout.closeDrawers();
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, PassActivity.class);
		startActivity(intent);	
		//����Ч��
		overridePendingTransition(R.anim.fade_out, R.anim.fade_in);       
	}
	//�໬�˵���ѡ������¼�
		public void logout(View view) {
            
            new AlertDialog.Builder(LoginActivity.this).setTitle("ע��")//���öԻ������             
            .setMessage("ȷ���˳���ǰ�˺��")//������ʾ������          
            .setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {//���ȷ����ť  
                @Override           
                public void onClick(DialogInterface dialog, int which) {//ȡ����ť����Ӧ�¼�           
                    // TODO Auto-generated method stub          
                }  
         
            })
            .setPositiveButton("ȷ���˳�",new DialogInterface.OnClickListener() {//���ȷ����ť  
              @Override  
              public void onClick(DialogInterface dialog, int which) {//��Ӧ�¼�       
            	//���߷��������cookies
      			LogoutPost();
      			//��������ļ���ֵ
      			myApp = (MyApp)getApplication();                         
                myApp.setCookie ("");
                mDrawerLayout.closeDrawers();
                  
                //��ת��¼����
                Intent intent = new Intent();
        		intent.setClass(LoginActivity.this, MainActivity.class);
        		startActivity(intent);	
        		//����login����
        		LoginActivity.this.finish();         
                }          
            }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���  
//            //��ת��¼����
//            Intent intent = new Intent();
//    		intent.setClass(LoginActivity.this, MainActivity.class);
//    		startActivity(intent);	
//    		//����login����
//    		LoginActivity.this.finish();
		}
	//�໬�˵���ѡ������¼�
	public void about(View view) {
		// ��ť���£��������
		mDrawerLayout.closeDrawers();
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, AboutActivity.class);
		startActivity(intent);	
		//����Ч��
		overridePendingTransition(R.anim.fade_out, R.anim.fade_in);       
	}
	
	
	
	//��post����
		public void LogoutPost() {
			//CookieSyncManager.createInstance(MainActivity.this);
			// ÿ�ε�¼������ʱ�������cookie
			//removeAllCookie();
		
			//1.���� HttpClient ��ʵ��  
			DefaultHttpClient client = new DefaultHttpClient();  
	        //2. ����ĳ�����ӷ�����ʵ������������HttpPost���� HttpPost �Ĺ��캯���д�������ӵĵ�ַ  
	        String url="http://115.28.147.177/server/logout.php";  
	        HttpPost httpPost = new HttpPost(url);  
	        try {  
	            //��װ���ݲ����ļ���  
	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  	           
	            
	            //�������ݲ�����װ ʵ����� 
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//���ô��ݲ����ı���  
	            //��ʵ�������뵽httpPost������  
	            httpPost.setEntity(entity);  
	            //3. ���õ�һ���д����õ�ʵ���� execute ������ִ�еڶ����д����õ� method ʵ��  
	            HttpResponse response = client.execute(httpPost); //HttpUriRequest�ĺ������ //�����������һ�»س�  
						
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

	