package com.zq.uitls;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.zq.type.NetWork;

/**
 * @author :Created by zhangqing on 2019/03/15 10:34
 * @description :
 * @email :1423118197@qq.com
 * @classpath : com.zq.uitls.NetWorkUtils
 */
public class NetWorkUtils
{
    public static NetWork GetNetworkType(Context context)
    {
        NetWork netWork = null;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            {
                netWork = NetWork.NETWORK_WIFI;
            }
            else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType)
                {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        //api<8 : replace by 11
                        netWork = NetWork.NETWORK_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                        //api<13 : replace by 15
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netWork = NetWork.NETWORK_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        //api<11 : replace by 13
                        netWork = NetWork.NETWORK_4G;
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000"))
                        {
                            netWork = NetWork.NETWORK_3G;
                        }
                        else
                        {
                            netWork = NetWork.NETWORK_UNKNOWN;
                        }

                        break;
                }
            }
        }
        return netWork;
    }

    public static boolean isNetworkConnected(Context context)
    {
        if (context != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null)
            {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
