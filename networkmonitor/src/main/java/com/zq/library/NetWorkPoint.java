package com.zq.library;

import com.zq.type.NetWork;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :Created by zhangqing on 2019/02/19 15:08
 * @description :
 * @email :1423118197@qq.com
 * @classpath : com.zq.library.NetWorkPoint
 */
public class NetWorkPoint
{
    String packageName;
    String className;
    String targetClass;
    List<Method> mMethods;

    public void addMethod(String methodName, NetWork netWork)
    {
        if (mMethods == null)
        {
            mMethods = new ArrayList<>();
        }
        mMethods.add(new Method(methodName, netWork));
    }

    public String getFqcn()
    {
        return packageName + "." + className;
    }


    public String brewJava()
    {
        StringBuilder stringBuilder = new StringBuilder();
        appened(stringBuilder, "package  " + packageName + ";");

        appened(stringBuilder, "import android.content.BroadcastReceiver;");
        appened(stringBuilder, "import android.content.Context;");


        appened(stringBuilder, "import com.zq.type.NetWork;");
        appened(stringBuilder, "import com.zq.library.Register;");

        appened(stringBuilder, "import android.content.Intent;");
        appened(stringBuilder, "import android.content.IntentFilter;");
        appened(stringBuilder, "import android.net.ConnectivityManager;");


        appened(stringBuilder, "import com.zq.library.NetWorkStatusManager;");

        appened(stringBuilder, "import android.net.Network;");
        appened(stringBuilder, "import android.net.NetworkCapabilities;");

        appened(stringBuilder, "import com.zq.uitls.NetWorkUtils;");

        appened(stringBuilder, "import android.net.NetworkRequest;");
        appened(stringBuilder, "import android.net.wifi.WifiManager;");

        appened(stringBuilder, "public  class " + className + "<T extends " + targetClass + ">  implements " + "Register<T>");
        appened(stringBuilder, "{");
        appened(stringBuilder, "ConnectivityManager mConnectivityManager;");
        appened(stringBuilder, "ConnectivityManager.NetworkCallback networkCallback;");
        appened(stringBuilder, "BroadcastReceiver mBroadcastReceiver;");
        appened(stringBuilder, "Context mContext;");
        appened(stringBuilder, "public " + className + "(NetWorkStatusManager.Finder finder,Object source)");
        appened(stringBuilder, "{");
        appened(stringBuilder, "mContext=finder.getContext(source);");
        appened(stringBuilder, "}");
        appened(stringBuilder, "@Override");
        appened(stringBuilder, "public void register(T target)");
        appened(stringBuilder, "{");
        ///////////////////////////////////////////////////
        appened(stringBuilder, "mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);");
        appened(stringBuilder, "if(android.os.Build.VERSION.SDK_INT >= 21)");
        appened(stringBuilder, "{");
        appened(stringBuilder, "NetworkRequest.Builder builder = new NetworkRequest.Builder();");
        appened(stringBuilder, "builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);");
        appened(stringBuilder, "builder.addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED);");
        appened(stringBuilder, "builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);");
        appened(stringBuilder, "builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);");
        appened(stringBuilder, "NetworkRequest request = builder.build();");
        appened(stringBuilder, "networkCallback = new ConnectivityManager.NetworkCallback()");
        appened(stringBuilder, "{");
        appened(stringBuilder, "@Override");
        appened(stringBuilder, "public void onAvailable(Network network)");
        appened(stringBuilder, "{");
        appened(stringBuilder, "super.onAvailable(network);");
        appened(stringBuilder, "NetWork netWork = NetWorkUtils.GetNetworkType(mContext);");
        for (Method method : mMethods)
        {
            NetWork tempNetwork = method.mNetWork;
            if (tempNetwork != NetWork.NETWORK_NO)
            {
                if (tempNetwork == NetWork.AUTO)
                {
                    appened(stringBuilder, "target." + method.methodName + "(netWork);");
                }
                else
                {
                    appened(stringBuilder, "if(netWork==com.zq.type.NetWork." + tempNetwork + ")");
                    {
                        appened(stringBuilder, "{");
                        appened(stringBuilder, "target." + method.methodName + "(com.zq.type.NetWork." + tempNetwork + ");");
                        appened(stringBuilder, "}");
                    }
                }
            }
        }
        appened(stringBuilder, "}");
        appened(stringBuilder, "@Override");
        appened(stringBuilder, "public void onLost(Network network)");
        appened(stringBuilder, "{");
        appened(stringBuilder, "super.onLost(network);");
        appened(stringBuilder, "if (!NetWorkUtils.isNetworkConnected(mContext))");
        appened(stringBuilder, "{");
        for (Method method : mMethods)
        {

            appened(stringBuilder, "target." + method.methodName + "(com.zq.type.NetWork." + NetWork.NETWORK_NO + ");");
        }
        appened(stringBuilder, "}");
        appened(stringBuilder, "}");
        appened(stringBuilder, "@Override");
        appened(stringBuilder, "public void onLosing(Network network, int maxMsToLive)");
        appened(stringBuilder, "{");
        appened(stringBuilder, "super.onLosing(network, maxMsToLive);");
        appened(stringBuilder, "}");
        appened(stringBuilder, "};");

        appened(stringBuilder, "mConnectivityManager.registerNetworkCallback(request, networkCallback);");
        appened(stringBuilder, "}");

        appened(stringBuilder, "else");
        appened(stringBuilder, "{");
        appened(stringBuilder, "mBroadcastReceiver=new  BroadcastReceiver()");
        appened(stringBuilder, "{");
        appened(stringBuilder, "@Override");
        appened(stringBuilder, "public void onReceive(Context context, Intent intent)");
        appened(stringBuilder, "{");

        appened(stringBuilder, "if(!NetWorkUtils.isNetworkConnected(mContext))");
        appened(stringBuilder, "{");
        for (Method method : mMethods)
        {
            appened(stringBuilder, "target." + method.methodName + "(com.zq.type.NetWork." + NetWork.NETWORK_NO + ");");
        }
        appened(stringBuilder, "}");
        appened(stringBuilder, "else");
        appened(stringBuilder, "{");
        appened(stringBuilder, "NetWork netWork = NetWorkUtils.GetNetworkType(mContext);");
        for (Method method : mMethods)
        {
            NetWork tempNetwork = method.mNetWork;
            if (tempNetwork != NetWork.NETWORK_NO)
            {
                if (tempNetwork == NetWork.AUTO)
                {
                    appened(stringBuilder, "target." + method.methodName + "(netWork);");
                }
                else
                {
                    appened(stringBuilder, "if(netWork==com.zq.type.NetWork." + tempNetwork + ")");
                    {
                        appened(stringBuilder, "{");
                        appened(stringBuilder, "target." + method.methodName + "(com.zq.type.NetWork." + tempNetwork + ");");
                        appened(stringBuilder, "}");
                    }
                }
            }
        }
        appened(stringBuilder, "}");
        appened(stringBuilder, "}");
        appened(stringBuilder, "};");
        appened(stringBuilder, "IntentFilter intentFilter=new IntentFilter();");
        appened(stringBuilder, "intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);");
        appened(stringBuilder, "intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);");
        appened(stringBuilder, "intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);");
        appened(stringBuilder, "mContext.registerReceiver(mBroadcastReceiver,intentFilter);");
        appened(stringBuilder, "}");
        appened(stringBuilder, "}");
        ///////////////////////////////////////////////////////////////////////
        appened(stringBuilder, "@Override");
        appened(stringBuilder, "public void unregister()");
        appened(stringBuilder, "{");
        appened(stringBuilder, "if (mConnectivityManager != null && networkCallback != null)");
        appened(stringBuilder, "{");
        appened(stringBuilder, "mConnectivityManager.unregisterNetworkCallback(networkCallback);");
        appened(stringBuilder, "mConnectivityManager=null;");
        appened(stringBuilder, "}");
        appened(stringBuilder, "if (mBroadcastReceiver!=null)");
        appened(stringBuilder, "{");
        appened(stringBuilder, "mContext.unregisterReceiver(mBroadcastReceiver);");
        appened(stringBuilder, "mBroadcastReceiver=null;");
        appened(stringBuilder, "}");
        appened(stringBuilder, "}");
        appened(stringBuilder, "}");
        return stringBuilder.toString();
    }

    class Method
    {
        String methodName;
        NetWork mNetWork;

        public Method(String methodName, NetWork netWork)
        {
            this.methodName = methodName;
            mNetWork = netWork;
        }
    }

    private void appened(StringBuilder stringBuilder, String str)
    {
        stringBuilder.append(str + "\n");
    }


}
