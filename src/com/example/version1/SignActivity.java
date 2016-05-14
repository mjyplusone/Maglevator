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
		//�Զ������
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page1);
		//���ñ���Ϊĳ��layout
      	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		
        find_and_modify_button() ;
        
        register_user=(EditText)super.findViewById(R.id.user);  
        register_pass=(EditText)super.findViewById(R.id.pass);  
        register_phone=(EditText)super.findViewById(R.id.phone);  
        final String contact=register_phone.getText().toString();
        //myApp = (MyApp)getApplication();  
	    //myApp.setContact(contact); 
        //ע��OnClick��OnFocusChange������  
        //register_user.setOnFocusChangeListener(new UserOnFocusChanageListener()); 
        //register_pass.setOnFocusChangeListener(new PassOnFocusChanageListener()); 
    
    }
	//������ʼ��
	private void find_and_modify_button() 
	{
		Button button_sign = (Button) findViewById(R.id.sign_btn);
		button_sign.setOnClickListener(btn1_listener);
		
		button_send = (Button) findViewById(R.id.send);
		button_send.setOnClickListener(btn2_listener);
	}
	
	
	//������֤�밴�������¼�
	private Button.OnClickListener btn2_listener = new Button.OnClickListener() 
	{
		
		public void onClick(View v)
		{
			//���������Ϣ�ĺ�����
			if(!checkEdit()){
				return;
			}
			
			//��ȡ�༭���ֵ
			EditText verify_user=(EditText)findViewById(R.id.user);
			EditText verify_pass=(EditText)findViewById(R.id.pass);
			EditText verify_phone=(EditText)findViewById(R.id.phone);
			final String name=verify_user.getText().toString();
			final String pass=verify_pass.getText().toString();
			final String phone=verify_phone.getText().toString();
			
			//button����ʱ
			TimeCountUtil timeCountUtil = new TimeCountUtil(SignActivity.this, 120000, 1000, button_send);
			timeCountUtil.start();
			
			 new Thread(new Runnable() {  
	                @Override  
	                public void run() {  
	                    //���ã�loginByHttpClientGet(userName, userPass);  
	                    //���ã�loginByHttpClientPost(userName, userPass);  
	                    VerifyHttpClientPost(name, pass, phone);//����  
	                }  
	            }).start(); 
			
		}		
		
	};
		
		
	//ע�ᰴ�������¼�
	private Button.OnClickListener btn1_listener = new Button.OnClickListener() 
	{
		public void onClick(View v)
		{
			if(!checkEdit()){
				return;
			}
			
			Log.i("SignActivity", "POST request");
			//��ȡ�༭���ֵ
			EditText register_user=(EditText)findViewById(R.id.user);
			EditText register_pass=(EditText)findViewById(R.id.pass);
			EditText register_phone=(EditText)findViewById(R.id.phone);
			EditText register_code=(EditText)findViewById(R.id.verify);
			final String name=register_user.getText().toString();
			final String pass=register_pass.getText().toString();
			final String phone=register_phone.getText().toString();
			final String code=register_code.getText().toString();

			
			 // �����̴߳���  
            new Thread(new Runnable() {  
                @Override  
                public void run() {  
                    //���ã�loginByHttpClientGet(userName, userPass);  
                    //���ã�loginByHttpClientPost(userName, userPass);  
                    SignByHttpClientPost(name, pass, phone, code);//����  
                }  
            }).start(); 
		}
	};
	
	
	//post����
	public void VerifyHttpClientPost(String userName, String userPass, String userPhone) {
			
			//ע�� �������˻�ûд
			// Dialog dialog;
			// dialog = ProgressDialog.show(SignActivity.this, "Isee",  "����ע�� ��", true, true);
		    HttpClient client = new DefaultHttpClient();  
			String httpUrl = "http://115.28.147.177/server/register.php"; 		  
			//HttpPost���Ӷ��� 
			HttpPost httpRequest = new HttpPost(httpUrl); 
			try {  
	            //��װ���ݲ����ļ���  
	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
	            //����������������Ҫ���ݵĲ���
	            parameters.add(new BasicNameValuePair("username", userName));  
	            parameters.add(new BasicNameValuePair("userpassword", userPass));  
	            parameters.add(new BasicNameValuePair("phonenumber", userPhone)); 
	            parameters.add(new BasicNameValuePair("code", ""));  
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
						Looper.prepare();
						Toast.makeText(SignActivity.this, "�����ֻ���"+userPhone+"������֤��", Toast.LENGTH_SHORT).show();
						Looper.loop();						
					}
					else        
					{
						Looper.prepare();
						Toast.makeText(SignActivity.this, "��֤�뷢��ʧ��", Toast.LENGTH_SHORT).show();
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
	
	
	//post����
	public void SignByHttpClientPost(String userName, String userPass, String userPhone, String userCode) {
			
			if(userCode.equals(""))
			{
				userCode="error";
			}
			//ע�� �������˻�ûд
			// Dialog dialog;
			// dialog = ProgressDialog.show(SignActivity.this, "Isee",  "����ע�� ��", true, true);
			 HttpClient client = new DefaultHttpClient();  
			 String httpUrl = "http://115.28.147.177/server/register.php"; 		  
			//HttpPost���Ӷ��� 
			HttpPost httpRequest = new HttpPost(httpUrl); 
			try {  
	            //��װ���ݲ����ļ���  
	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
	            //����������������Ҫ���ݵĲ���
	            parameters.add(new BasicNameValuePair("username", userName));  
	            parameters.add(new BasicNameValuePair("userpassword", userPass));  
	            parameters.add(new BasicNameValuePair("phonenumber", userPhone)); 
	            parameters.add(new BasicNameValuePair("code", userCode));  
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
						//dialog.dismiss();
						//ע��ɹ���ת����½
						//Looper.prepare();
						//Toast.makeText(SignActivity.this, "ע��ɹ� ��", Toast.LENGTH_SHORT).show();
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
						Toast.makeText(SignActivity.this, "ע��ʧ�ܣ���֤�����", Toast.LENGTH_SHORT).show();
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
			
	//���������Լ�⣨�����û���������Ϊ�գ���������Ҫһ�µȣ�
	private boolean checkEdit(){
		EditText register_user=(EditText)findViewById(R.id.user);
		EditText register_pass=(EditText)findViewById(R.id.pass);
		EditText register_pass2=(EditText)findViewById(R.id.pass2);
		if(register_user.getText().toString().trim().equals("")){
			Toast.makeText(SignActivity.this, "�û�������Ϊ��", Toast.LENGTH_SHORT).show();
		}else if(register_pass.getText().toString().trim().equals("")){
			Toast.makeText(SignActivity.this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
		}else if(!register_pass.getText().toString().trim().equals(register_pass2.getText().toString().trim())){
			Toast.makeText(SignActivity.this, "�����������벻һ��", Toast.LENGTH_SHORT).show();
		}else{
			return true;
		}
		return false;
	}
	
	
	public class TimeCountUtil extends CountDownTimer {
		private Activity mActivity;
		private Button btn;//��ť

		// ��������췽������Ҫ��������������һ����Activity��һ�����ܵ�ʱ��millisInFuture��һ����countDownInterval��Ȼ����������ĸ���ť��������ǣ��Ͱ������ť�������Ϳ�����
		public TimeCountUtil( Activity mActivity,long millisInFuture, long countDownInterval,Button btn) {
		super(millisInFuture, countDownInterval);
		this.mActivity = mActivity;
		this.btn =btn;
		}


		@SuppressLint("NewApi")
		@Override
		public void onTick(long millisUntilFinished) {
		btn.setClickable(false);//���ò��ܵ��
		btn.setText(millisUntilFinished / 1000 + "����ط�");//���õ���ʱʱ��

		//���ð�ťΪ��ɫ����ʱ�ǲ��ܵ����
		btn.setBackground(mActivity.getResources().getDrawable(R.drawable.login_button_selector));
		Spannable span = new SpannableString(btn.getText().toString());//��ȡ��ť������
		span.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//������ʱʱ����ʾΪ��ɫ
		btn.setText(span);

		}


		@SuppressLint("NewApi")
		@Override
		public void onFinish() {
		btn.setText("���»�ȡ");
		btn.setClickable(true);//���»�õ��
		btn.setBackground(mActivity.getResources().getDrawable(R.drawable.login_button_selector));//��ԭ����ɫ

		}


	}
	
}







