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
    	//�Զ������
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);  
        
        myApp = (MyApp)getApplication();  		
		if(myApp.getflag()){  
            this.setTheme(R.style.nightTheme);  
        }else{  
            this.setTheme(R.style.normalTheme);  
        }  
		
        
        setContentView(R.layout.chart);  
        //���ñ���Ϊĳ��layout
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
    
        pieChart.setHoleRadius(60f);  //�뾶    
        pieChart.setTransparentCircleRadius(64f); // ��͸��Ȧ    
        //pieChart.setHoleRadius(0)  //ʵ��Բ    
    
        pieChart.setDescription("���Ա�״ͼ");    
    
        // mChart.setDrawYValues(true);    
        pieChart.setDrawCenterText(true);  //��״ͼ�м�����������    
    
        pieChart.setDrawHoleEnabled(true);    
    
        pieChart.setRotationAngle(90); // ��ʼ��ת�Ƕ�    
    
        // draws the corresponding description value into the slice    
        // mChart.setDrawXValues(true);    
    
        // enable rotation of the chart by touch    
        pieChart.setRotationEnabled(true); // �����ֶ���ת    
    
        // display percentage values    
        pieChart.setUsePercentValues(true);  //��ʾ�ɰٷֱ�    
        // mChart.setUnit(" ��");    
        // mChart.setDrawUnitsInChart(true);    
    
        // add a selection listener    
//      mChart.setOnChartValueSelectedListener(this);    
        // mChart.setTouchEnabled(false);    
    
//      mChart.setOnAnimationListener(this);    
    
        pieChart.setCenterText("Quarterly Revenue");  //��״ͼ�м������    
    
        //��������    
        pieChart.setData(pieData);     
            
        // undo all highlights    
//      pieChart.highlightValues(null);    
//      pieChart.invalidate();    
    
        Legend mLegend = pieChart.getLegend();  //���ñ���ͼ    
        mLegend.setPosition(LegendPosition.RIGHT_OF_CHART);  //���ұ���ʾ    
//      mLegend.setForm(LegendForm.LINE);  //���ñ���ͼ����״��Ĭ���Ƿ���    
        mLegend.setXEntrySpace(7f);    
        mLegend.setYEntrySpace(5f);    
            
        pieChart.animateXY(1000, 1000);  //���ö���    
        // mChart.spin(2000, 0, 360);    
    }    
    
    /**  
     *   
     * @param count �ֳɼ�����  
     * @param range  
     */    
    private PieData getPieData(int count, float range) {    
            
        ArrayList<String> xValues = new ArrayList<String>();  //xVals������ʾÿ�������ϵ�����    
    
        for (int i = 0; i < count; i++) {    
            xValues.add("Quarterly" + (i + 1));  //��������ʾ��Quarterly1, Quarterly2, Quarterly3, Quarterly4    
        }    
    
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals������ʾ��װÿ�������ʵ������    
    
        // ��ͼ����    
        /**  
         * ��һ������ͼ�ֳ��Ĳ��֣� �Ĳ��ֵ���ֵ����Ϊ14:14:34:38  
         * ���� 14����İٷֱȾ���14%   
         */    
        float quarterly1 = 14;    
        float quarterly2 = 14;    
        float quarterly3 = 34;    
        float quarterly4 = 38;    
    
        yValues.add(new Entry(quarterly1, 0));    
        yValues.add(new Entry(quarterly2, 1));    
        yValues.add(new Entry(quarterly3, 2));    
        yValues.add(new Entry(quarterly4, 3));    
    
        //y��ļ���    
        PieDataSet pieDataSet = new PieDataSet(yValues, "Quarterly Revenue 2014"/*��ʾ�ڱ���ͼ��*/);    
        pieDataSet.setSliceSpace(0f); //���ø���״ͼ֮��ľ���    
    
        ArrayList<Integer> colors = new ArrayList<Integer>();    
    
        // ��ͼ��ɫ    
        colors.add(Color.rgb(205, 205, 205));    
        colors.add(Color.rgb(114, 188, 223));    
        colors.add(Color.rgb(255, 123, 124));    
        colors.add(Color.rgb(57, 135, 200));    
    
        pieDataSet.setColors(colors);    
    
        DisplayMetrics metrics = getResources().getDisplayMetrics();    
        float px = 5 * (metrics.densityDpi / 160f);    
        pieDataSet.setSelectionShift(px); // ѡ��̬����ĳ���    
    
        PieData pieData = new PieData(xValues, pieDataSet);    
            
        return pieData;    
    } 
    
    public void onClick(View widget) {

    	new Thread(new Runnable() {  
            @Override  
            public void run() {  
                //���ã�loginByHttpClientGet(userName, userPass);  
                //���ã�loginByHttpClientPost(userName, userPass);  
                loginByHttpClientPost();//����  
            }  
        }).start();  
      	
    }
    
    
    public void loginByHttpClientPost() {
	
		//1.���� HttpClient ��ʵ��  
		DefaultHttpClient client = new DefaultHttpClient();  
        //2. ����ĳ�����ӷ�����ʵ������������HttpPost���� HttpPost �Ĺ��캯���д�������ӵĵ�ַ  
        String url="http://115.28.147.177/server/check.php";  
        HttpPost httpPost = new HttpPost(url);  
        try {        	
            //��װ���ݲ����ļ���  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            //����������������Ҫ���ݵĲ���
                    
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
            	
            	String stateflag;
				String result = EntityUtils.toString(response.getEntity());	
				
				//���������ʽ��json
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
            //6. �ͷ����ӡ�����ִ�з����Ƿ�ɹ����������ͷ�����  
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