package com.example.newapp.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.newapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.img_header)
    ImageView imgHeader;

    public MyFragment() {

    }


    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.img_header, R.id.rl_collect, R.id.rl_skin, R.id.rl_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header:
                break;
            case R.id.rl_collect:
                break;
            case R.id.rl_skin:
                break;
            case R.id.rl_logout:
               // ShowToast("Logout");
                break;
        }
    }
}