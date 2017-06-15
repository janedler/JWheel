package com.janedler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.janedler.domain.region.City;
import com.janedler.domain.region.County;
import com.janedler.domain.region.Province;
import com.janedler.manager.CustomTree;
import com.janedler.manager.RegionTree;
import com.janedler.manager.base.WheelTree;
import com.janedler.util.WheelUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    WheelUtil mRegionWheel;
    WheelUtil mTextWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Province> provinces = getProvinces();
        final WheelTree tree = new RegionTree(provinces);

        mRegionWheel = new WheelUtil(MainActivity.this, new WheelUtil.WeelUtilCallBack() {
            @Override
            public void onBackClick() {
                Toast.makeText(MainActivity.this,"BackClick",Toast.LENGTH_SHORT).show();
                mRegionWheel.dismiss();
            }

            @Override
            public void onSureClick(String reslult) {
                Toast.makeText(MainActivity.this,reslult,Toast.LENGTH_SHORT).show();
                mRegionWheel.dismiss();
            }

            @Override
            public void onDismiss() {
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegionWheel.showPicker(tree);
            }
        });


        List<String> texts = getTexts();
        final WheelTree treeText = new CustomTree(texts);

        mTextWheel = new WheelUtil(MainActivity.this, new WheelUtil.WeelUtilCallBack() {
            @Override
            public void onBackClick() {
                Toast.makeText(MainActivity.this,"BackClick",Toast.LENGTH_SHORT).show();
                mTextWheel.dismiss();
            }

            @Override
            public void onSureClick(String reslult) {
                Toast.makeText(MainActivity.this,reslult,Toast.LENGTH_SHORT).show();
                mTextWheel.dismiss();
            }

            @Override
            public void onDismiss() {
            }
        });

        findViewById(R.id.singlebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextWheel.showPicker(treeText);
            }
        });
    }


    private static List<String> getTexts(){
        List<String> texts = new ArrayList<>();
        texts.add("星期一");
        texts.add("星期二");
        texts.add("星期三");
        texts.add("星期四");
        texts.add("星期五");
        texts.add("星期六");
        texts.add("星期日");
        return texts;
    }


    private static String[] mProvinceNames = {
            "北京市山东省山东省山东省山东省山东省山东省山东省山东省",
            "广东省",
            "山东省",
    };

    private static String[][] mCityNames = {
            {"朝阳区朝阳区朝阳区朝阳区朝阳区朝阳区朝阳区朝阳区","海淀区","通州区","房山区","丰台区","昌平区"},
            {"东莞市","广州市","中山市","深圳市","惠州市","江门市","珠海市"},
            {"济南市","青岛市","临沂市"}
    };

    private static String[] mCountNames = {
            "城区城区城区城区城区城区城区","非城区"
    };


    private static List<Province> getProvinces(){

        List<Province> provinces = new ArrayList<>();

        for (int i = 0; i < mProvinceNames.length; i++) {
            Province province = new Province();
            province.provinceId = System.currentTimeMillis();
            province.provinceName = mProvinceNames[i];
            province.provinceCode = String.valueOf(UUID.randomUUID());

            String[] cityNames = mCityNames[i];
            List<City> cities = new ArrayList<>();
            for (int j = 0; j < cityNames.length; j++) {
                City city = new City();
                city.cityId= System.currentTimeMillis();
                city.cityName = cityNames[j];
                city.cityCode = String.valueOf(UUID.randomUUID());
                List<County> counties = new ArrayList<>();
                for (int k = 0; k < mCountNames.length; k++) {
                    County county = new County();
                    county.countyId  = System.currentTimeMillis();
                    county.countyName = mCountNames[k]+ new Random().nextInt(30);
                    county.countyCode = String.valueOf(UUID.randomUUID());
                    counties.add(county);
                }
                city.counties = counties;
                cities.add(city);
            }
            province.cities =cities;
            provinces.add(province);
        }
        return provinces;
    }


}
