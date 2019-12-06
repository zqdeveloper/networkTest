package com.example.networktest;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.zq.annotations.NetWorkType;
import com.zq.library.NetWorkStatusManager;
import com.zq.library.Register;
import com.zq.type.NetWork;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    Register register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = NetWorkStatusManager.register(this);
    }


    @NetWorkType(NetWork.NETWORK_2G)
    public void Test(NetWork netWork)
    {
        Log.i(TAG, "Test: " + netWork.name());
    }

    @NetWorkType(NetWork.NETWORK_WIFI)
    public void TestWifi(NetWork netWork)
    {
        Log.i(TAG, "Test: " + netWork.name());
    }

    @NetWorkType(NetWork.NETWORK_NO)
    public void TestWifiNO(NetWork netWork)
    {
        Log.i(TAG, "Test: " + netWork.name());
    }

    @Override
    protected void onDestroy()
    {
        register.unregister();
        super.onDestroy();
    }
}
