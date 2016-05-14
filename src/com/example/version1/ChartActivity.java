package com.example.version1;
        
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import com.github.mikephil.charting.charts.PieChart;    
import com.github.mikephil.charting.components.Legend;    
import com.github.mikephil.charting.components.Legend.LegendPosition;    
import com.github.mikephil.charting.data.Entry;    
import com.github.mikephil.charting.data.PieData;    
import com.github.mikephil.charting.data.PieDataSet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;    
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.widget.TextView;
import android.widget.Toast;    
    
public class ChartActivity extends Activity {    
	private MyApp myApp;
    private PieChart mChart;  
    public CookieManager cookieManager = null;
    
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
		
        
        setContentView(R.layout.chart);  
        //设置标题为某个layout
      	//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            
        mChart = (PieChart) findViewById(R.id.spread_pie_chart);    
        PieData mPieData = getPieData(4, 100);    
        showChart(mChart, mPieData);    
                
    }    
    
    public void Theme(View view) {
		myApp = (MyApp)getApplication();  
	    
		if(myApp.getflag()==true)  myApp.setflag(false);
		else                  myApp.setflag(true);
		
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		setContentView(R.layout.chart);

	}
    
    private void showChart(PieChart pieChart, PieData pieData) {    
        pieChart.setHoleColorTransparent(true);    
    
        pieChart.setHoleRadius(60f);  //半径    
        pieChart.setTransparentCircleRadius(64f); // 半透明圈    
        //pieChart.setHoleRadius(0)  //实心圆    
    
        pieChart.setDescription("测试饼状图");    
    
        // mChart.setDrawYValues(true);    
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字    
    
        pieChart.setDrawHoleEnabled(true);    
    
        pieChart.setRotationAngle(90); // 初始旋转角度    
    
        // draws the corresponding description value into the slice    
        // mChart.setDrawXValues(true);    
    
        // enable rotation of the chart by touch    
        pieChart.setRotationEnabled(true); // 可以手动旋转    
    
        // display percentage values    
        pieChart.setUsePercentValues(true);  //显示成百分比    
        // mChart.setUnit(" €");    
        // mChart.setDrawUnitsInChart(true);    
    
        // add a selection listener    
//      mChart.setOnChartValueSelectedListener(this);    
        // mChart.setTouchEnabled(false);    
    
//      mChart.setOnAnimationListener(this);    
    
        pieChart.setCenterText("Quarterly Revenue");  //饼状图中间的文字    
    
        //设置数据    
        pieChart.setData(pieData);     
            
        // undo all highlights    
//      pieChart.highlightValues(null);    
//      pieChart.invalidate();    
    
        Legend mLegend = pieChart.getLegend();  //设置比例图    
        mLegend.setPosition(LegendPosition.RIGHT_OF_CHART);  //最右边显示    
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形    
        mLegend.setXEntrySpace(7f);    
        mLegend.setYEntrySpace(5f);    
            
        pieChart.animateXY(1000, 1000);  //设置动画    
        // mChart.spin(2000, 0, 360);    
    }    
    
    /**  
     *   
     * @param count 分成几部分  
     * @param range  
     */    
    private PieData getPieData(int count, float range) {    
            
        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容    
    
        for (int i = 0; i < count; i++) {    
            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4    
        }    
    
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据    
    
        // 饼图数据    
        /**  
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38  
         * 所以 14代表的百分比就是14%   
         */    
        float quarterly1 = 14;    
        float quarterly2 = 14;    
        float quarterly3 = 34;    
        float quarterly4 = 38;    
    
        yValues.add(new Entry(quarterly1, 0));    
        yValues.add(new Entry(quarterly2, 1));    
        yValues.add(new Entry(quarterly3, 2));    
        yValues.add(new Entry(quarterly4, 3));    
    
        //y轴的集合    
        PieDataSet pieDataSet = new PieDataSet(yValues, "Quarterly Revenue 2014"/*显示在比例图上*/);    
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离    
    
        ArrayList<Integer> colors = new ArrayList<Integer>();    
    
        // 饼图颜色    
        colors.add(Color.rgb(205, 205, 205));    
        colors.add(Color.rgb(114, 188, 223));    
        colors.add(Color.rgb(255, 123, 124));    
        colors.add(Color.rgb(57, 135, 200));    
    
        pieDataSet.setColors(colors);    
    
        DisplayMetrics metrics = getResources().getDisplayMetrics();    
        float px = 5 * (metrics.densityDpi / 160f);    
        pieDataSet.setSelectionShift(px); // 选中态多出的长度    
    
        PieData pieData = new PieData(xValues, pieDataSet);    
            
        return pieData;    
    } 
    
    public void onClick(View widget) {

    	new Thread(new Runnable() {  
            @Override  
            public void run() {  
                //调用：loginByHttpClientGet(userName, userPass);  
                //调用：loginByHttpClientPost(userName, userPass);  
                loginByHttpClientPost();//测试  
            }  
        }).start();  
      	
    }
    
    
    public void loginByHttpClientPost() {
	
		//1.创建 HttpClient 的实例  
		DefaultHttpClient client = new DefaultHttpClient();  
        //2. 创建某种连接方法的实例，在这里是HttpPost。在 HttpPost 的构造函数中传入待连接的地址  
        String url="http://115.28.147.177/server/check.php";  
        HttpPost httpPost = new HttpPost(url);  
        try {        	
            //封装传递参数的集合  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            //往这个集合中添加你要传递的参数
                    
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
				
				//解析下面格式的json
				//{"users":{"stateflag":"true","phonenumber":"18861821281"}}
				JSONObject jsonObject = new JSONObject(result);    
				JSONObject jsonObject2 = jsonObject.getJSONObject("users");  
				stateflag=jsonObject2.getString("stateflag");
				
				Looper.prepare();
				Toast.makeText(ChartActivity.this, stateflag, Toast.LENGTH_LONG).show();
				Looper.loop();
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
    
    
    public String getMD5(String info)
    {
      try
      {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(info.getBytes("UTF-8"));
        byte[] encryption = md5.digest();
          
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < encryption.length; i++)
        {
          if (Integer.toHexString(0xff & encryption[i]).length() == 1)
          {
            strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
          }
          else
          {
            strBuf.append(Integer.toHexString(0xff & encryption[i]));
          }
        }
          
        return strBuf.toString();
      }
      catch (NoSuchAlgorithmException e)
      {
        return "";
      }
      catch (UnsupportedEncodingException e)
      {
        return "";
      }
    }
}   	