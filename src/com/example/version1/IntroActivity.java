package com.example.version1;
       

import java.util.ArrayList;

import android.app.Activity; 
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

    
public class IntroActivity extends Activity {    
	private MyApp myApp;
	private ViewPager viewPager;
	private ArrayList<View> pageview;
    
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
		     
        setContentView(R.layout.intro);  
        
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        
        //查找布局文件用LayoutInflater.inflate
        LayoutInflater inflater =getLayoutInflater();
        View view1 = inflater.inflate(R.layout.intro_item1, null);
        View view2 = inflater.inflate(R.layout.intro_item2, null);
        
        //将view装入数组
        pageview =new ArrayList<View>();
        pageview.add(view1);
        pageview.add(view2);
        
        
        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter(){

            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }

            @Override
            //断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0==arg1;
            }
            //是从ViewGroup中移出当前View
             public void destroyItem(View arg0, int arg1, Object arg2) {  
                    ((ViewPager) arg0).removeView(pageview.get(arg1));  
                }  
            
            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1){
                ((ViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);                
            }
            
            
        };
        
        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);

                
    }    

}   	