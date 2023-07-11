package com.app.mybaseframwork;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mybaseframwork.base.MyBaseDataBindingActivity;
import com.app.mybaseframwork.base.base_model.CommonViewModel;
import com.app.mybaseframwork.databinding.ActivityHistoryBinding;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dy.fastframework.util.MyUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.help.MyQuckAdapter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityHistory  extends MyBaseDataBindingActivity<CommonViewModel<ActivityHistoryBinding>, ActivityHistoryBinding> {
    private List<ConvertResult.TransResultBean> datas=new ArrayList<>();
    private ConvertResult historyData;
    private MyQuckAdapter<ConvertResult.TransResultBean> adapter;

    @Override
    protected CommonViewModel<ActivityHistoryBinding> createViewModel() {
        return new CommonViewModel<ActivityHistoryBinding>(this,binding);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_history;
    }

    @Override
    public void initFirst() {
        binding.smRoot.smRf.setEnableLoadMore(false);
        binding.smRoot.smRf.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getHistoryData();
            }
        });
        setActivityTitle("历史生成记录");
        binding.llTitleBar.tvRightBt.setVisibility(View.VISIBLE);
        binding.llTitleBar.tvRightBt.setTextColor(getResColor(R.color.red));
        binding.llTitleBar.tvRightBt.setText("删除全部");
        binding.llTitleBar.tvRightBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogToDeleteAll();
            }
        });
        viewModel.setLoadingRootView(binding.smRoot.smRf, new listener.SimpleOnStatusClickListener() {
            @Override
            public void onEmptyChildClick(View view) {
                viewModel.showLoadingLayout();
                getHistoryData();
            }
        });
        viewModel.showLoadingLayout();
        getHistoryData();
    }


    private void showDialogToDeleteAll() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("系统提示");
        builder.setMessage("要删除全部记录吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                getSpUtil().saveObject("ConvertResult",new ConvertResult());
                viewModel.showLoadingLayout();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getHistoryData();
                    }
                }, 20);
            }
        });
        builder.create().show();
    }

    public void getHistoryData(){
        historyData= (ConvertResult) getSpUtil().getObj("ConvertResult",ConvertResult.class);
        if(historyData==null) {
            viewModel.showEmptyLayout();
        }else{
            datas.clear();
            if(!MyUtils.isEmpty(historyData.getTrans_result())){
                datas.addAll(historyData.getTrans_result());
            }
            if(datas.isEmpty()){
                viewModel.showEmptyLayout();
            }else{
                setAdapter();
            }
        }
    }

    private void setAdapter() {
        binding.smRoot.smRf.finishRefresh();
        viewModel.showSuccessLayout();
        if(adapter==null) {
            LinearLayoutManager manager=new LinearLayoutManager(this);
            manager.setOrientation(RecyclerView.VERTICAL);
            binding.smRoot.rcView.setLayoutManager(manager);
            adapter = new MyQuckAdapter<ConvertResult.TransResultBean>(R.layout.item_history, datas, this) {
                @Override
                protected void convert(BaseViewHolder helper, ConvertResult.TransResultBean item) {
                    helper.setText(R.id.tv_promt,item.getDst());
                    helper.getView(R.id.tv_promt).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jumpToDetailsAc(item);
                        }
                    });
                    helper.getView(R.id.tv_promt).setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            showDialogToDelete(getCurrentPosition(helper));
                            return false;
                        }
                    });
                }
            };
            binding.smRoot.rcView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }




    private void showDialogToDelete(int currentPosition) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("系统提示");
        builder.setMessage("要删除该条记录吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                datas.remove(currentPosition);
                historyData.setTrans_result(datas);
                getSpUtil().saveObject("ConvertResult",historyData);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getHistoryData();
                    }
                }, 20);
            }
        });
        builder.create().show();
    }


    //跳转详情页面
    private void jumpToDetailsAc(ConvertResult.TransResultBean item) {
        Intent intent=new Intent(this,ActivityDetails.class);
        intent.putExtra("data", item);
        startActivity(intent);
    }
}
