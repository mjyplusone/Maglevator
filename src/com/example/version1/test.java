package com.example.version1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;    
import com.github.mikephil.charting.components.Legend;    
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.data.Entry;    
import com.github.mikephil.charting.data.LineData;    
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;  


public class test extends Activity implements Runnable{
	private MyApp myApp;
	
	private LayoutInflater inflater = null;
	
	PopupWindow menuWindow;

	private Boolean flag1=false,flag2=false;
	private Boolean state1,state2;
	
	private LineChart mLineChart; 
	private LineChart mLineChart2; 
    private LineData data;
    private LineData data2;
    private ArrayList<String> xVals;
    private ArrayList<String> xVals2;
    private LineDataSet dataSet;
    private LineDataSet dataSet2;
    private LineDataSet dataSet21;
    private LineDataSet dataSet22;
    private ArrayList<Entry> yVals1;
    private ArrayList<Entry> yVals2;
    private ArrayList<Entry> yVals21;
    private ArrayList<Entry> yVals22;

    Handler mHandler; 
    JSONArray jsonArray;
    int[] data_id1;
	float[] acceleration1;
	float[] speed1;
	float[] frequence1;
	float[] voltage1;
	int[] floor1;
	String[] speed;
	String[] frequence;
	String[] floor;
	String pasttime;
	String lasttime;
	String time;
	int count=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		myApp = (MyApp)getApplication();  
		
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		
		setContentView(R.layout.test);
		inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		
		
		mLineChart = (LineChart) findViewById(R.id.chart1); 
		mLineChart2 = (LineChart) findViewById(R.id.chart2); 
		 
	
		 
		mLineChart.setDescription("");// ��������    
	    // ���û�����ݵ�ʱ�򣬻���ʾ���������listview��emtpyview    
		mLineChart.setNoDataTextDescription("�������ݴ�����Ҫ�ϳ�ʱ�䣬�����ĵȴ�.");    
	            
	    // enable / disable grid background    
		//mLineChart.setDrawGridBackground(false); // �Ƿ���ʾ�����ɫ    
		mLineChart.setGridBackgroundColor(Color.WHITE & 0x00FFFFFF); // ���ĵ���ɫ�����������Ǹ���ɫ����һ��͸����    
	    
	     // enable touch gestures    
	    mLineChart.setTouchEnabled(true); // �����Ƿ���Դ���    
	    
	     // enable scaling and dragging    
	    mLineChart.setDragEnabled(true);// �Ƿ������ק    
	    mLineChart.setScaleEnabled(true);// �Ƿ��������    
	    
	     // if disabled, scaling can be done on x- and y-axis separately    
	    mLineChart.setPinchZoom(false);// 
	    //�����ұ�������
	    mLineChart.getAxisLeft().setTextColor(0xff4e82f8);
	    mLineChart.getAxisRight().setEnabled(false);
	    mLineChart.getXAxis().setPosition(XAxisPosition.BOTTOM);
	    mLineChart.getHighlighted();
	    
	    mLineChart2.setDescription("");// ��������    
	    // ���û�����ݵ�ʱ�򣬻���ʾ���������listview��emtpyview    
		mLineChart2.setNoDataTextDescription("�������ݴ�����Ҫ�ϳ�ʱ�䣬�����ĵȴ�.");    
	            
	    // enable / disable grid background    
		//mLineChart.setDrawGridBackground(false); // �Ƿ���ʾ�����ɫ    
		mLineChart2.setGridBackgroundColor(Color.WHITE & 0x00FFFFFF); // ���ĵ���ɫ�����������Ǹ���ɫ����һ��͸����    
	    
	     // enable touch gestures    
	    mLineChart2.setTouchEnabled(true); // �����Ƿ���Դ���    
	    
	     // enable scaling and dragging    
	    mLineChart2.setDragEnabled(true);// �Ƿ������ק    
	    mLineChart2.setScaleEnabled(true);// �Ƿ��������    
	    
	     // if disabled, scaling can be done on x- and y-axis separately    
	    mLineChart2.setPinchZoom(false);// 
	    //�����ұ�������
	    mLineChart2.getAxisLeft().setTextColor(0xff4e82f8);
	    mLineChart2.getAxisRight().setEnabled(false);
	    mLineChart2.getXAxis().setPosition(XAxisPosition.BOTTOM);
	    
	    new Thread(this).start();  
			
         
		mHandler = new Handler();//����Handler 
	
	}


	/*
	public void forData(View v)
	{
		
		
			new Thread(new Runnable() {  
                @Override  
			public void run() {  
				AllHttpClientPost();//����  
            }  
        	}).start();  			
		
		   
	}
	*/
	
 
    public void AllHttpClientPost() {
		//1.���� HttpClient ��ʵ��  
		DefaultHttpClient client = new DefaultHttpClient();  
        //2. ����ĳ�����ӷ�����ʵ������������HttpPost���� HttpPost �Ĺ��캯���д�������ӵĵ�ַ  
        String url="http://115.28.147.177/server/search.php";  
        HttpPost httpPost = new HttpPost(url); 
        httpPost.addHeader("accept-encoding","gzip, deflate");  
        try {  
            //��װ���ݲ����ļ���  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            
            //����������������Ҫ���ݵĲ���
            parameters.add(new BasicNameValuePair("timeFrom", lasttime));  
            parameters.add(new BasicNameValuePair("toNow", "true")); 
            
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
            	//String data_id,sensor_id,acceleration,speed,frequence,voltage;
				//String result = EntityUtils.toString(response.getEntity());	
				//Log.i("debug", result); //��ȡ��Ӧ����

//              Looper.prepare();
//        		Toast.makeText(test.this, "aaaaa", Toast.LENGTH_LONG).show();
//        		Looper.loop();
				
            	ByteArrayBuffer bt= new ByteArrayBuffer(4096);  

	            //ִ��Get����     
	            HttpEntity he = response.getEntity();  
	            //�����ǽ�ѹ���Ĺ���  
	            GZIPInputStream gis = new GZIPInputStream(he.getContent());  
	            int l;  
	            byte[] tmp = new byte[4096];  
	            while ((l=gis.read(tmp))!=-1){  
	                bt.append(tmp, 0, l);  
	            }  
	              
	            String result = new String(bt.toByteArray(),"utf-8");   
	            //����Ĳ���������վ�ı���һ����˵����UTF-8  
				
				
				//���������ʽ��json
				//{"datalist":[{"data_id":1,"sensor_id":"1307","acceleration":0.9,"speed":2.442,"frequence":50.328,"voltage":247.258,"data_time":"2016-03-12 00:00:00"},.....
				JSONObject jsonObject = new JSONObject(result);    
				jsonArray = jsonObject.getJSONArray("datalist");  
				String[] data_id = new String[jsonArray.length()];
				String[] sensor_id = new String[jsonArray.length()];
				String[] acceleration = new String[jsonArray.length()];
				//String[] 
						speed = new String[jsonArray.length()];
				//String[] 
						frequence = new String[jsonArray.length()];
				String[] voltage = new String[jsonArray.length()];
				//String[] 
						floor = new String[jsonArray.length()];
				data_id1 = new int[jsonArray.length()];
				//int[] sensor_id1 = new int[jsonArray.length()];
				acceleration1 = new float[jsonArray.length()];
				speed1 = new float[jsonArray.length()];
				frequence1 = new float[jsonArray.length()];
				voltage1 = new float[jsonArray.length()];
				floor1 = new int[jsonArray.length()];
			    for (int i=0;i<jsonArray.length();i++)
		        {
		            JSONObject jsonObjectSon= (JSONObject)jsonArray.opt(i);
		            data_id[i] = jsonObjectSon.getString("data_id");
		            data_id1[i] = Integer.parseInt(data_id[i]);
		            sensor_id[i] = jsonObjectSon.getString("sensor_id");
		            acceleration[i] = jsonObjectSon.getString("acceleration");
		            acceleration1[i] = Float.parseFloat(acceleration[i]);
		            speed[i] = jsonObjectSon.getString("speed");
		            speed1[i] = Float.parseFloat(speed[i]);
		            frequence[i] = jsonObjectSon.getString("frequence");
		            frequence1[i] = Float.parseFloat(frequence[i]);
		            voltage[i] = jsonObjectSon.getString("voltage");
		            voltage1[i] = Float.parseFloat(voltage[i]);
		            floor[i] = jsonObjectSon.getString("floor");
		            floor1[i] = Integer.parseInt(floor[i]);
		            time = jsonObjectSon.getString("data_time");
		        }
			    count++;
			    if(count==700)
			    {
			    	count = 0;
			    	lasttime = time;
			    }
			    
			    mHandler.post(mRunnable0);
		
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
    
    
    
    public void RangePost() {
		//1.���� HttpClient ��ʵ��  
		DefaultHttpClient client = new DefaultHttpClient();  
        //2. ����ĳ�����ӷ�����ʵ������������HttpPost���� HttpPost �Ĺ��캯���д�������ӵĵ�ַ  
        String url="http://115.28.147.177/server/search.php";  
        HttpPost httpPost = new HttpPost(url); 
        try {  
            //��װ���ݲ����ļ���  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            
            //����������������Ҫ���ݵĲ���
            parameters.add(new BasicNameValuePair("timeRange", "true"));  
            
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
            	//String data_id,sensor_id,acceleration,speed,frequence,voltage;
				String result = EntityUtils.toString(response.getEntity());	
				Log.i("debug", result); //��ȡ��Ӧ����

				
				//���������ʽ��json
				//{"timeRange":{"pasttime":"","lasttime":""}}
				JSONObject jsonObject = new JSONObject(result);    
				JSONObject jsonObject2 = jsonObject.getJSONObject("timeRange");  
				pasttime=jsonObject2.getString("pasttime");
				lasttime=jsonObject2.getString("lasttime");
				
//                Looper.prepare();
//        		Toast.makeText(test.this, pasttime+" "+lasttime, Toast.LENGTH_LONG).show();
//        		Looper.loop();
				
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
    
    
    //��̬��������
	public void run() { 
		RangePost();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
        //���ã�loginByHttpClientGet(userName, userPass);  
        //���ã�loginByHttpClientPost(userName, userPass);  
		try {
			Thread.sleep(200); 
			AllHttpClientPost();   
			//handler.sendMessage(handler.obtainMessage());    
		} catch (InterruptedException e) {   
             // TODO Auto-generated catch block   
             e.printStackTrace();   
        }   
		}   
    }  
	

    Runnable mRunnable0=new Runnable()
    {
		@Override
		public void run(){
			
		//TODOAuto-generatedmethodstub
			
			TextView lc= (TextView) findViewById(R.id.louceng);
			lc.setText(floor[jsonArray.length()-1]);
			
			
		    data = new LineData();
			xVals=new ArrayList<String>();
	        yVals1=new ArrayList<Entry>();
	        
	        int i1;
	        if(jsonArray.length()-20<0)
	        	i1 = 0;
	        else
	        	i1=jsonArray.length()-20;
	        for(int j=0;i1<jsonArray.length();i1++,j++){
	            float profix=acceleration1[i1];
	            yVals1.add(new Entry(profix,j));
	            xVals.add("");
	        }
	        dataSet=new LineDataSet(yVals1,"���ٶ�");
	        //dataSet.setColor(Color.BLUE);
		    dataSet.setColor(Color.parseColor("#00A918"));
		    // �߿�
		    dataSet.setLineWidth(2.0f);
		    // Ȧ��ɫ
		    dataSet.setCircleColor(Color.parseColor("#00A918"));
		    // Բ�Ĵ�С
		    dataSet.setCircleSize(3.0f);
		    // �����ɫ
		    dataSet.setFillColor(Color.parseColor("#00A918"));
		    // ��������
		    dataSet.setDrawCubic(true);
		    // ��������
		    dataSet.setDrawValues(false);
		    // �����С
		    //dataSet.setValueTextSize(7f);
		    // ������ɫ
		    //dataSet.setValueTextColor(Color.parseColor("#FFFFFF"));
	        
	        ArrayList<LineDataSet> LineDataSets = new ArrayList<LineDataSet>();
	        LineDataSets.add(dataSet);
	        
	              
	        
	        yVals2=new ArrayList<Entry>();
	        
	        int i2;
	        if(jsonArray.length()-20<0)
	        	i2 = 0;
	        else
	        	i2=jsonArray.length()-20;
	        for(int j=0;i2<jsonArray.length();i2++,j++){
	            float profix=speed1[i2];
	            yVals2.add(new Entry(profix,j));
	        }
	        dataSet2 = new LineDataSet(yVals2, "�ٶ�");
	        dataSet2.setColor(Color.parseColor("#4e82f8"));
		    // �߿�
		    dataSet2.setLineWidth(2.0f);
		    // Ȧ��ɫ
		    dataSet2.setCircleColor(Color.parseColor("#4e82f8"));
		    // Բ�Ĵ�С
		    dataSet2.setCircleSize(3.0f);
		    // �����ɫ
		    dataSet2.setFillColor(Color.parseColor("#4e82f8"));
		    // ��������
		    dataSet2.setDrawCubic(true);
		    // ��������
		    dataSet2.setDrawValues(false);
	       
	             
	        LineDataSets.add(dataSet2);
	        
	        data = new LineData(xVals, LineDataSets);
	        
	        mLineChart.setData(data);
	        //updata
	        mLineChart.invalidate();
	        
	        
	      //-------------------pic2---------------------------
	        
	        
	        data2 = new LineData();
			xVals2=new ArrayList<String>();
	        yVals21=new ArrayList<Entry>();
	        
	        
	        int i3;
	        if(jsonArray.length()-20<0)
	        	i3 = 0;
	        else
	        	i3=jsonArray.length()-20;
	        for(int j=0;i3<jsonArray.length();i3++,j++){
	            float profix=frequence1[i3];
	            yVals21.add(new Entry(profix,j));
	            xVals2.add("");
	        }
	        dataSet21=new LineDataSet(yVals21,"Ƶ��");
	        //dataSet.setColor(Color.BLUE);
		    dataSet21.setColor(Color.parseColor("#00A918"));
		    // �߿�
		    dataSet21.setLineWidth(2.0f);
		    // Ȧ��ɫ
		    dataSet21.setCircleColor(Color.parseColor("#00A918"));
		    // Բ�Ĵ�С
		    dataSet21.setCircleSize(3.0f);
		    // �����ɫ
		    dataSet21.setFillColor(Color.parseColor("#00A918"));
		    // ��������
		    dataSet21.setDrawCubic(true);
		    // ��������
		    dataSet21.setDrawValues(false);
		    // �����С
		    //dataSet.setValueTextSize(7f);
		    // ������ɫ
		    //dataSet.setValueTextColor(Color.parseColor("#FFFFFF"));
	        
	        ArrayList<LineDataSet> LineDataSets2 = new ArrayList<LineDataSet>();
	        LineDataSets2.add(dataSet21);
	        
	              
	        
	        yVals22=new ArrayList<Entry>();
	        
	        int i4;
	        if(jsonArray.length()-20<0)
	        	i4 = 0;
	        else
	        	i4=jsonArray.length()-20;
	        for(int j=0;i4<jsonArray.length();i4++,j++){
	            float profix=voltage1[i4];
	            yVals22.add(new Entry(profix,j));
	        }
	        dataSet22 = new LineDataSet(yVals22, "��ѹ");
	        dataSet22.setColor(Color.parseColor("#4e82f8"));
		    // �߿�
		    dataSet22.setLineWidth(2.0f);
		    // Ȧ��ɫ
		    dataSet22.setCircleColor(Color.parseColor("#4e82f8"));
		    // Բ�Ĵ�С
		    dataSet22.setCircleSize(3.0f);
		    // �����ɫ
		    dataSet22.setFillColor(Color.parseColor("#4e82f8"));
		    // ��������
		    dataSet22.setDrawCubic(true);
		    // ��������
		    dataSet22.setDrawValues(false);
	       
	             
	        LineDataSets2.add(dataSet22);
	        
	        data2 = new LineData(xVals2, LineDataSets2);
	        
	        mLineChart2.setData(data2);
	        //updata
	        mLineChart2.invalidate(); 
	        
		}
    };
    
    
   
}
