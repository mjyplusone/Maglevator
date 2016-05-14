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
		 
	
		 
		mLineChart.setDescription("");// 数据描述    
	    // 如果没有数据的时候，会显示这个，类似listview的emtpyview    
		mLineChart.setNoDataTextDescription("初次数据传输需要较长时间，请耐心等待.");    
	            
	    // enable / disable grid background    
		//mLineChart.setDrawGridBackground(false); // 是否显示表格颜色    
		mLineChart.setGridBackgroundColor(Color.WHITE & 0x00FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度    
	    
	     // enable touch gestures    
	    mLineChart.setTouchEnabled(true); // 设置是否可以触摸    
	    
	     // enable scaling and dragging    
	    mLineChart.setDragEnabled(true);// 是否可以拖拽    
	    mLineChart.setScaleEnabled(true);// 是否可以缩放    
	    
	     // if disabled, scaling can be done on x- and y-axis separately    
	    mLineChart.setPinchZoom(false);// 
	    //隐藏右边网格线
	    mLineChart.getAxisLeft().setTextColor(0xff4e82f8);
	    mLineChart.getAxisRight().setEnabled(false);
	    mLineChart.getXAxis().setPosition(XAxisPosition.BOTTOM);
	    mLineChart.getHighlighted();
	    
	    mLineChart2.setDescription("");// 数据描述    
	    // 如果没有数据的时候，会显示这个，类似listview的emtpyview    
		mLineChart2.setNoDataTextDescription("初次数据传输需要较长时间，请耐心等待.");    
	            
	    // enable / disable grid background    
		//mLineChart.setDrawGridBackground(false); // 是否显示表格颜色    
		mLineChart2.setGridBackgroundColor(Color.WHITE & 0x00FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度    
	    
	     // enable touch gestures    
	    mLineChart2.setTouchEnabled(true); // 设置是否可以触摸    
	    
	     // enable scaling and dragging    
	    mLineChart2.setDragEnabled(true);// 是否可以拖拽    
	    mLineChart2.setScaleEnabled(true);// 是否可以缩放    
	    
	     // if disabled, scaling can be done on x- and y-axis separately    
	    mLineChart2.setPinchZoom(false);// 
	    //隐藏右边网格线
	    mLineChart2.getAxisLeft().setTextColor(0xff4e82f8);
	    mLineChart2.getAxisRight().setEnabled(false);
	    mLineChart2.getXAxis().setPosition(XAxisPosition.BOTTOM);
	    
	    new Thread(this).start();  
			
         
		mHandler = new Handler();//创建Handler 
	
	}


	/*
	public void forData(View v)
	{
		
		
			new Thread(new Runnable() {  
                @Override  
			public void run() {  
				AllHttpClientPost();//测试  
            }  
        	}).start();  			
		
		   
	}
	*/
	
 
    public void AllHttpClientPost() {
		//1.创建 HttpClient 的实例  
		DefaultHttpClient client = new DefaultHttpClient();  
        //2. 创建某种连接方法的实例，在这里是HttpPost。在 HttpPost 的构造函数中传入待连接的地址  
        String url="http://115.28.147.177/server/search.php";  
        HttpPost httpPost = new HttpPost(url); 
        httpPost.addHeader("accept-encoding","gzip, deflate");  
        try {  
            //封装传递参数的集合  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            
            //往这个集合中添加你要传递的参数
            parameters.add(new BasicNameValuePair("timeFrom", lasttime));  
            parameters.add(new BasicNameValuePair("toNow", "true")); 
            
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
            	//String data_id,sensor_id,acceleration,speed,frequence,voltage;
				//String result = EntityUtils.toString(response.getEntity());	
				//Log.i("debug", result); //获取响应内容

//              Looper.prepare();
//        		Toast.makeText(test.this, "aaaaa", Toast.LENGTH_LONG).show();
//        		Looper.loop();
				
            	ByteArrayBuffer bt= new ByteArrayBuffer(4096);  

	            //执行Get方法     
	            HttpEntity he = response.getEntity();  
	            //以下是解压缩的过程  
	            GZIPInputStream gis = new GZIPInputStream(he.getContent());  
	            int l;  
	            byte[] tmp = new byte[4096];  
	            while ((l=gis.read(tmp))!=-1){  
	                bt.append(tmp, 0, l);  
	            }  
	              
	            String result = new String(bt.toByteArray(),"utf-8");   
	            //后面的参数换成网站的编码一般来说都是UTF-8  
				
				
				//解析下面格式的json
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
            //6. 释放连接。无论执行方法是否成功，都必须释放连接  
            client.getConnectionManager().shutdown();  
        }  
    }
    
    
    
    public void RangePost() {
		//1.创建 HttpClient 的实例  
		DefaultHttpClient client = new DefaultHttpClient();  
        //2. 创建某种连接方法的实例，在这里是HttpPost。在 HttpPost 的构造函数中传入待连接的地址  
        String url="http://115.28.147.177/server/search.php";  
        HttpPost httpPost = new HttpPost(url); 
        try {  
            //封装传递参数的集合  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            
            //往这个集合中添加你要传递的参数
            parameters.add(new BasicNameValuePair("timeRange", "true"));  
            
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
            	//String data_id,sensor_id,acceleration,speed,frequence,voltage;
				String result = EntityUtils.toString(response.getEntity());	
				Log.i("debug", result); //获取响应内容

				
				//解析下面格式的json
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
            //6. 释放连接。无论执行方法是否成功，都必须释放连接  
            client.getConnectionManager().shutdown();  
        }  
    }
    
    
    //动态更改数据
	public void run() { 
		RangePost();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
        //调用：loginByHttpClientGet(userName, userPass);  
        //调用：loginByHttpClientPost(userName, userPass);  
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
	        dataSet=new LineDataSet(yVals1,"加速度");
	        //dataSet.setColor(Color.BLUE);
		    dataSet.setColor(Color.parseColor("#00A918"));
		    // 线宽
		    dataSet.setLineWidth(2.0f);
		    // 圈颜色
		    dataSet.setCircleColor(Color.parseColor("#00A918"));
		    // 圆的大小
		    dataSet.setCircleSize(3.0f);
		    // 填充颜色
		    dataSet.setFillColor(Color.parseColor("#00A918"));
		    // 绘制立方
		    dataSet.setDrawCubic(true);
		    // 绘制数据
		    dataSet.setDrawValues(false);
		    // 字体大小
		    //dataSet.setValueTextSize(7f);
		    // 字体颜色
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
	        dataSet2 = new LineDataSet(yVals2, "速度");
	        dataSet2.setColor(Color.parseColor("#4e82f8"));
		    // 线宽
		    dataSet2.setLineWidth(2.0f);
		    // 圈颜色
		    dataSet2.setCircleColor(Color.parseColor("#4e82f8"));
		    // 圆的大小
		    dataSet2.setCircleSize(3.0f);
		    // 填充颜色
		    dataSet2.setFillColor(Color.parseColor("#4e82f8"));
		    // 绘制立方
		    dataSet2.setDrawCubic(true);
		    // 绘制数据
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
	        dataSet21=new LineDataSet(yVals21,"频率");
	        //dataSet.setColor(Color.BLUE);
		    dataSet21.setColor(Color.parseColor("#00A918"));
		    // 线宽
		    dataSet21.setLineWidth(2.0f);
		    // 圈颜色
		    dataSet21.setCircleColor(Color.parseColor("#00A918"));
		    // 圆的大小
		    dataSet21.setCircleSize(3.0f);
		    // 填充颜色
		    dataSet21.setFillColor(Color.parseColor("#00A918"));
		    // 绘制立方
		    dataSet21.setDrawCubic(true);
		    // 绘制数据
		    dataSet21.setDrawValues(false);
		    // 字体大小
		    //dataSet.setValueTextSize(7f);
		    // 字体颜色
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
	        dataSet22 = new LineDataSet(yVals22, "电压");
	        dataSet22.setColor(Color.parseColor("#4e82f8"));
		    // 线宽
		    dataSet22.setLineWidth(2.0f);
		    // 圈颜色
		    dataSet22.setCircleColor(Color.parseColor("#4e82f8"));
		    // 圆的大小
		    dataSet22.setCircleSize(3.0f);
		    // 填充颜色
		    dataSet22.setFillColor(Color.parseColor("#4e82f8"));
		    // 绘制立方
		    dataSet22.setDrawCubic(true);
		    // 绘制数据
		    dataSet22.setDrawValues(false);
	       
	             
	        LineDataSets2.add(dataSet22);
	        
	        data2 = new LineData(xVals2, LineDataSets2);
	        
	        mLineChart2.setData(data2);
	        //updata
	        mLineChart2.invalidate(); 
	        
		}
    };
    
    
   
}
