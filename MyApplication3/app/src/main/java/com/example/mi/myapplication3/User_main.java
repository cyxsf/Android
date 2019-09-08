package com.example.mi.myapplication3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.leon.lib.settingview.LSettingItem;



public class User_main extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);

        LSettingItem orders = (LSettingItem) findViewById(R.id.item_one);
        LSettingItem collect =(LSettingItem) findViewById(R.id.item_two);

        orders.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Toast.makeText(getApplicationContext(), "我的订单", Toast.LENGTH_SHORT).show();
            }

        });

        collect.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Toast.makeText(getApplicationContext(), "我的收藏", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
