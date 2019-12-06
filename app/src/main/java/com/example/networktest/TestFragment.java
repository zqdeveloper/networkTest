package com.example.networktest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zq.annotations.NetWorkType;
import com.zq.library.NetWorkStatusManager;
import com.zq.library.Register;
import com.zq.type.NetWork;

public class TestFragment extends Fragment
{
    Register register;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        register = NetWorkStatusManager.register(view);
    }

    @NetWorkType(NetWork.NETWORK_2G)
    public void Test(NetWork netWork)
    {

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        register.unregister();
    }
}
