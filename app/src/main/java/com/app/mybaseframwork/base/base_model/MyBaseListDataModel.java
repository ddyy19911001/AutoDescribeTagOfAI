package com.app.mybaseframwork.base.base_model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mybaseframwork.R;
import com.app.mybaseframwork.base.config.SystemConfig;
import com.dy.fastframework.util.ActivityLoadUtil;
import com.dy.fastframework.util.MyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.help.MyQuckAdapter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;


/**
 * 列表带刷新和加载更多的数据模型，简化重复代码
 * 需要加载前显示转圈的自己设置loadingRootView即可
 * @param <T> 数据源
 * @param <V> Adapter
 */
public abstract class MyBaseListDataModel<T,V extends MyQuckAdapter<T>>{
    public V adapter;
    public List<T> datas=new ArrayList<>();
    public SmartRefreshLayout smRf;
    public RecyclerView rcView;
    public int dataOldPosition;
    public int dataNewSize;
    public int page= SystemConfig.pageStart;
    public Context context;
    public StatusLayoutManager loadingManager;

    public Context getContext() {
        return context;
    }


    public MyBaseListDataModel(Context context, SmartRefreshLayout smRf, RecyclerView rcView) {
        this.context=context;
        this.smRf=smRf;
        this.rcView=rcView;
        onNotSetListener();
        setRefreshLoadMoreListener();
        this.adapter= createAdapter();
        setRecycleViewManagerAndAdapter(onCreateLayoutManager());
    }

    public void setLoadingRootView(MyBaseViewModel viewModel,View view, OnStatusChildClickListener onStatusChildClickListener){
        if(view!=null) {
            viewModel.setLoadingRootView(view,onStatusChildClickListener);
            loadingManager = viewModel.loadingManager;
        }
    }

    public void setLoadingRootView(MyBaseViewModel viewModel,View view){
        if(view!=null) {
            viewModel.setLoadingRootView(view, new OnStatusChildClickListener() {
                @Override
                public void onEmptyChildClick(View view) {
                    loadingManager.showLoadingLayout();
                    getNewData();
                }

                @Override
                public void onErrorChildClick(View view) {
                    loadingManager.showLoadingLayout();
                    getNewData();
                }

                @Override
                public void onCustomerChildClick(View view) {
                    loadingManager.showLoadingLayout();
                    getNewData();
                }
            });
            loadingManager = viewModel.loadingManager;
        }
    }

    public void showLoadingLayout(){
        if(loadingManager!=null){
            loadingManager.showLoadingLayout();
        }
    }

    public void showEmptyLayout(){
        if(loadingManager!=null){
            loadingManager.showEmptyLayout();
        }
    }

    public void showErrorLayout(){
        if(loadingManager!=null){
            loadingManager.showErrorLayout();
        }
    }


    /**
     * 创建加载中的布局管理器
     * @param view
     * @param childClickListener
     * @return
     */
    public StatusLayoutManager createStatusLayoutManager(View view, OnStatusChildClickListener childClickListener){
        int emptyLayoutId=getEmptyLayout();
        StatusLayoutManager manager=  ActivityLoadUtil.getInstance().useCustomLayout(view,getLoadingLayout(),emptyLayoutId,emptyLayoutId, R.id.re_try_bt,childClickListener);
        return manager;
    }


    /**
     * 设置当前显示的空布局
     * @return
     */
    public int getEmptyLayout() {
        return R.layout.defualt_empty_layout;
    }

    /**
     * 设置当前显示的加载中的布局
     * @return
     */
    public int getLoadingLayout() {
        return R.layout.default_loading_layout;
    }


    public void onNotSetListener() {

    }


    public void setEmptyText(String emptyText){
        if(loadingManager!=null){
            View emptyLayout = loadingManager.getEmptyLayout();
            if(emptyLayout!=null){
                TextView textView= emptyLayout.findViewById(R.id.tv_empty_text);
                if(textView!=null){
                    textView.setText(emptyText);
                }
            }
        }
    }

    public void setEmptyImage(int imgRes){
        if(loadingManager!=null){
            View emptyLayout = loadingManager.getEmptyLayout();
            if(emptyLayout!=null){
                ImageView imageView= emptyLayout.findViewById(R.id.iv_empty);
                if(imageView!=null){
                    imageView.setImageResource(imgRes);
                }
            }
        }
    }

    private void setRefreshLoadMoreListener() {
        smRf.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                page++;
                getNewData();
            }

            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                page=SystemConfig.pageStart;
                getNewData();
            }
        });
    }

    /**
     * 获取数据之后调用setNewData();
     */
    public abstract void getNewData();


    /**
     * 1.创建Adapter
     * @return
     */
    protected abstract V createAdapter();

    /**
     * 2.创建layoutManager,也可再此处添加recycleview的ItemDecoration
     * @return
     */
    protected abstract RecyclerView.LayoutManager onCreateLayoutManager();




    /**
     * 设置RecycleView的Manager和Adapter
     * @param manager
     */
    public void setRecycleViewManagerAndAdapter(RecyclerView.LayoutManager manager) {
        rcView.setLayoutManager(manager);
        rcView.setAdapter(adapter);
    }



    /**
     * 设置数据到原数据中，并关闭刷新和加载更多的转圈
     * @param newDatas
     */
    public void setNewData(List<T> newDatas){
        if(MyUtils.isEmpty(newDatas)){
            if(page== SystemConfig.pageStart){
                dataOldPosition =0;
                datas.clear();
                //刷新时数据为空
                smRf.finishRefresh();
            }else{
                //加载更多数据为空
                if(page>SystemConfig.pageStart) {
                    page--;
                }
                dataOldPosition =datas.size();
                smRf.finishLoadMoreWithNoMoreData();
            }
        }else{
            if(page== SystemConfig.pageStart){
                datas.clear();
                dataOldPosition =0;
                smRf.finishRefresh();
            }else{
                dataOldPosition =datas.size();
                smRf.finishLoadMore();
            }
            dataNewSize =newDatas.size();
            datas.addAll(newDatas);
        }
        onNotifyDataChanged();
    }


    /**
     * 数据改变时调用
     */
    public void onNotifyDataChanged(){
        if(datas.size()>0){
            if(onDataNotifyChangeListener!=null){
                onDataNotifyChangeListener.onNewDataEmpty();
            }
            if(loadingManager!=null){
                loadingManager.showSuccessLayout();
            }
        }else{
            if(onDataNotifyChangeListener!=null){
                onDataNotifyChangeListener.onNewDataChange();
            }
            if(loadingManager!=null){
                loadingManager.showEmptyLayout();
            }
        }
        dealWithNotifyData();
    }



    OnDataNotifyChangeListener onDataNotifyChangeListener;

    public void setOnDataNotifyChangeListener(OnDataNotifyChangeListener onDataNotifyChangeListener) {
        this.onDataNotifyChangeListener = onDataNotifyChangeListener;
    }

    public interface OnDataNotifyChangeListener{
        void onNewDataEmpty();
        void onNewDataChange();
    }

    /**
     * 需要自己处理刷新逻辑时重写此方法
     */
    public void dealWithNotifyData(){
        if(dataOldPosition<0){
            dataOldPosition=0;
        }
        if(dataOldPosition==0){
            adapter.notifyDataSetChanged();
        }else{
            adapter.notifyItemRangeChanged(dataOldPosition,dataNewSize);
        }
    }


}
