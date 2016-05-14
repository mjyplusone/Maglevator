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
    	//�Զ������
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
        
        //���Ҳ����ļ���LayoutInflater.inflate
        LayoutInflater inflater =getLayoutInflater();
        View view1 = inflater.inflate(R.layout.intro_item1, null);
        View view2 = inflater.inflate(R.layout.intro_item2, null);
        
        //��viewװ������
        pageview =new ArrayList<View>();
        pageview.add(view1);
        pageview.add(view2);
        
        
        //����������
        PagerAdapter mPagerAdapter = new PagerAdapter(){

            @Override
            //��ȡ��ǰ���������
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }

            @Override
            //���Ƿ��ɶ������ɽ���
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0==arg1;
            }
            //�Ǵ�ViewGroup���Ƴ���ǰView
             public void destroyItem(View arg0, int arg1, Object arg2) {  
                    ((ViewPager) arg0).removeView(pageview.get(arg1));  
                }  
            
            //����һ������������������PagerAdapter������ѡ���ĸ�������ڵ�ǰ��ViewPager��
            public Object instantiateItem(View arg0, int arg1){
                ((ViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);                
            }
            
            
        };
        
        //��������
        viewPager.setAdapter(mPagerAdapter);

                
    }    

}   	