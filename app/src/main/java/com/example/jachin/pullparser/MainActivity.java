package com.example.jachin.pullparser;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    List<City> cityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View v) {
        //获取到src文件夹下的资源文件
        InputStream is = null;
        try {
            is = getResources().getAssets().open("weather.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //拿到pull解析器对象
        XmlPullParser xp = Xml.newPullParser();
        //初始化
        try {
            xp.setInput(is, "utf-8");

            //获取当前节点的事件类型，通过事件类型的判断，我们可以知道当前节点是什么节点，从而确定我们应该做什么操作
            int type = xp.getEventType();
            City city = null;
            while(type != XmlPullParser.END_DOCUMENT){
                //根据节点的类型，要做不同的操作
                switch (type) {
                    case XmlPullParser.START_TAG:
                        //					获取当前节点的名字
                        if("weather".equals(xp.getName())){
                            //创建city集合对象，用于存放city的javabean
                            cityList = new ArrayList<City>();
                        }
                        else if("city".equals(xp.getName())){
                            //创建city的javabean对象
                            city = new City();
                        }
                        else if("name".equals(xp.getName())){
                            //				获取当前节点的下一个节点的文本
                            String name = xp.nextText();
                            city.setName(name);
                        }
                        else if("temp".equals(xp.getName())){
                            //				获取当前节点的下一个节点的文本
                            String temp = xp.nextText();
                            city.setTemp(temp);
                        }
                        else if("pm".equals(xp.getName())){
                            //				获取当前节点的下一个节点的文本
                            String pm = xp.nextText();
                            city.setPm(pm);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("city".equals(xp.getName())){
                            //把city的javabean放入集合中
                            cityList.add(city);
                        }
                        break;

                }

                //把指针移动到下一个节点，并返回该节点的事件类型
                type = xp.next();
            }

            for (City c : cityList) {
                TextView textView = (TextView)findViewById(R.id.tv);
                textView.setText(c.toString());
                Log.v("xxxx","oooo");
                Toast.makeText(this,c.toString(),Toast.LENGTH_SHORT).show();
                //System.out.println(c.toString());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
