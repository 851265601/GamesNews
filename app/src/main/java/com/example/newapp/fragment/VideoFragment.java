package com.example.newapp.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.TitleView;
import com.example.newapp.R;
import com.example.newapp.activity.LoginActivity;
import com.example.newapp.adapter.VideoAdapter;
import com.example.newapp.api.Api;
import com.example.newapp.api.ApiConfig;
import com.example.newapp.api.TtitCallBack;
import com.example.newapp.entity.VideoEntity;
import com.example.newapp.entity.VideoListResponse;
import com.example.newapp.listener.OnItemChildClickListener;
import com.example.newapp.util.StringUtils;
import com.example.newapp.util.Tag;
import com.example.newapp.util.Utils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends BaseFragment  implements OnItemChildClickListener {


    private String title;
    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;
    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */

    protected  int categoryId;
    protected int mLastPos = mCurPos;
    RecyclerView recyclerView;
    VideoAdapter videoAdapter;
    RefreshLayout refreshLayout;
    LinearLayoutManager layoutManager;
    private List<VideoEntity> datas = new ArrayList<>();
    private int pageNum = 1;
    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;
    List<VideoEntity> list;
    protected  Handler mHandler= new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
           switch (msg.what){
               case 0:
                   videoAdapter.setDatas(list);
                   videoAdapter.notifyDataSetChanged();
                   break;


           }


        }
    };

    public VideoFragment() {
        // Required empty public constructor
    }

    public static VideoFragment newInstance(int CategoryId) {
        VideoFragment fragment = new VideoFragment();
        //单例模式，上面实例化完成fragment之后, 要对本地的变量进行赋值，不能直接进行赋值，需要使用当前fragment 进行变量的赋值
        fragment.categoryId = CategoryId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    protected int initLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        initVideoView();
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);

    }

    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        videoAdapter = new VideoAdapter(getActivity(), datas);
        videoAdapter.setOnItemChildClickListener(this);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                GetVideoList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
                GetVideoList(false);
            }
        });


        recyclerView.setAdapter(videoAdapter);

        //当itemView 消失的时候，把VideoView 暂停并且释放。
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                View v = playerContainer.getChildAt(0);
                if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
                    releaseVideoView();
                }
            }
        });

        GetVideoList(true);
    }

    protected void initVideoView() {
        mVideoView = new VideoView(getActivity());
        mVideoView.setOnStateChangeListener(new com.dueeeke.videoplayer.player.VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //监听VideoViewManager释放，重置状态
                if (playState == com.dueeeke.videoplayer.player.VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new StandardVideoController(getActivity());
        mErrorView = new ErrorView(getActivity());
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(getActivity());
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(getActivity());
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(getActivity()));
        mController.addControlComponent(new GestureView(getActivity()));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }

    private void GetVideoList(final boolean isRefresh) {
        String token = getStringFromSp("token");
        // List<VideoEntity> videoEntityList = new ArrayList<VideoEntity>();
        if (!StringUtils.IsStringEmpty(token)) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("token", token);
            hashMap.put("page", pageNum);
            hashMap.put("limit", ApiConfig.PAGE_SIZE);
            hashMap.put("categoryId", categoryId);
            Api.config(ApiConfig.VIDEO_LIST_BY_CATEGORY, hashMap).GetRequest(getActivity(), new TtitCallBack() {
                @Override
                public void OnSuccess(String res) {
                    if (isRefresh) {
                        refreshLayout.finishRefresh(true);
                    } else {
                        refreshLayout.finishLoadMore(true);
                    }

                    //showToastSync(res);
                    //转化JSon 数据
                    VideoListResponse videoListResponse = new Gson().fromJson(res, VideoListResponse.class);
                    if (videoListResponse != null && videoListResponse.getCode() == 0) {
                        //不对空， 并且Code 为0 说明请求成功
                        list = videoListResponse.getPage().getList();
                        if (list != null && list.size() > 0) {
                            if (isRefresh) {
                                datas = list;
                            } else {
                                datas.addAll(list);
                            }
                            //通知主线程更新listview
                        mHandler.sendEmptyMessage(0);
                        } else {
                            if (isRefresh) {

                                showToastSync("暂时无数据");
                            } else {
                                showToastSync("没有更多数据");
                            }
                        }


                    }
                }
                @Override
                public void onFailure(Exception exception) {
                    //关闭加载动画
                    if (isRefresh) {
                        refreshLayout.finishRefresh(true);
                    } else {
                        refreshLayout.finishLoadMore(true);
                    }
                }
            });


        } else {
            Navigate(LoginActivity.class);

        }
        // return videoEntityList;

    }
    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    /**
     * 由于onPause必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onPause的逻辑
     */
    protected void pause() {
        releaseVideoView();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    /**
     * 由于onResume必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onResume的逻辑
     */
    protected void resume() {
        if (mLastPos == -1)
            return;
        //恢复上次播放的位置
        startPlay(mLastPos);
    }

    @Override
    public void onItemChildClick(int position) {
        startPlay(position);
    }
    /**
     * 开始播放
     *
     * @param position 列表位置
     */
    protected void startPlay(int position) {
        if (mCurPos == position) return;
        if (mCurPos != -1) {
            releaseVideoView();
        }
        VideoEntity videoEntity = datas.get(position);
        //边播边存
//        String proxyUrl = ProxyVideoCacheManager.getProxy(getActivity()).getProxyUrl(videoBean.getUrl());
//        mVideoView.setUrl(proxyUrl);

        mVideoView.setUrl(videoEntity.getPlayurl());
        mTitleView.setTitle(videoEntity.getVtitle());
        View itemView = layoutManager.findViewByPosition(position);
        if (itemView == null) return;
        VideoAdapter.ViewHolder viewHolder = (VideoAdapter.ViewHolder) itemView.getTag();
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.mPrepareView, true);
        Utils.removeViewFormParent(mVideoView);
        viewHolder.mPlayerContainer.addView(mVideoView, 0);
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        getVideoViewManager().add(mVideoView, Tag.LIST);
        mVideoView.start();
        mCurPos = position;

    }

    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }

}