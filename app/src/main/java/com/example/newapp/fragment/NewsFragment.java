package com.example.newapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapp.R;
import com.example.newapp.adapter.NewsAdapter;
import com.example.newapp.api.Api;
import com.example.newapp.api.ApiConfig;
import com.example.newapp.api.TtitCallBack;
import com.example.newapp.entity.NewsEntity;
import com.example.newapp.entity.NewsListResponse;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NewsFragment extends BaseFragment {


    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private NewsAdapter newsAdapter;
    private List<NewsEntity> datas = new ArrayList<NewsEntity>();
    private LinearLayoutManager linearLayoutManager;
    private int pageNum = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    newsAdapter.setDatas(datas);
                    newsAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    public NewsFragment() {

    }
    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();

        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(newsAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                getNewsList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
                getNewsList(false);
            }
        });
        getNewsList(true);
    }
    private void getNewsList(final boolean isRefresh) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("page", pageNum);
            params.put("limit", ApiConfig.PAGE_SIZE);
            Api.config(ApiConfig.NEWS_LIST, params).GetRequest(getActivity(), new TtitCallBack() {

                @Override
                public void OnSuccess(String res) {
                    if (isRefresh) {
                        refreshLayout.finishRefresh(true);
                    } else {
                        refreshLayout.finishLoadMore(true);
                    }
                    NewsListResponse response = new Gson().fromJson(res, NewsListResponse.class);
                    if (response != null && response.getCode() == 0) {
                        List<NewsEntity> list = response.getPage().getList();
                        if (list != null && list.size() > 0) {
                            if (isRefresh) {
                                datas = list;
                            } else {
                                datas.addAll(list);
                            }
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
                    if (isRefresh) {
                        refreshLayout.finishRefresh(true);
                    } else {
                        refreshLayout.finishLoadMore(true);
                    }
                }
            });
        }



}

