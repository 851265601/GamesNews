package com.example.newapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dueeeke.videoplayer.player.VideoViewManager;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView==null){
            mRootView=  inflater.inflate(initLayout(),container,false);
            initView();
        }

        return mRootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }



    public void ShowToast(String str) {

        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

    }

    public void saveStringToSp(String Name, String token) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Name, token);
        editor.commit();
    }

    public String getStringFromSp(String Name) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        return sharedPreferences.getString(Name, "");
    }

    public void showToastSync(String str) {
        Looper.prepare();
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void showToast(String str) {
        //Looper.prepare();
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        // Looper.loop();
    }

    public void Navigate(Class cls) {

        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);

    }

    protected VideoViewManager getVideoViewManager() {
        return VideoViewManager.instance();
    }
}
