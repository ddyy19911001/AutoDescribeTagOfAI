package com.app.mybaseframwork.base.base_model;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.app.mybaseframwork.R;
import com.app.mybaseframwork.base.BaseApp;
import com.dy.fastframework.util.ActivityLoadUtil;
import com.dy.fastframework.util.NoDoubleClickListener;
import com.dy.fastframework.util.SharedPreferenceUtil;
import com.dy.fastframework.util.ToastUtil;
import com.dy.fastframework.view.CommonMsgDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;


/**
 * 视图模型基类
 * @param <T>
 */
public abstract class MyBaseViewModel<T extends ViewDataBinding> implements OnStatusChildClickListener {
    public Dialog loadingDialog;
    public  Context context;
    public StatusLayoutManager loadingManager;
    public Activity activity;
    public Fragment fragment;
    private ToastUtil toast;
    public T binding;//用于快速找到对应控件
    public OnStatusChildClickListener onStatusChildClickListener;



    public void setOnStatusChildClickListener(OnStatusChildClickListener onStatusChildClickListener) {
        this.onStatusChildClickListener = onStatusChildClickListener;
    }




    public MyBaseViewModel(Activity activity, T binding) {
        this.activity = activity;
        this.binding=binding;
        this.context=getContext();
        init();
    }

    public MyBaseViewModel(Fragment fragment, T binding) {
        this.fragment = fragment;
        this.binding=binding;
        this.context=getContext();
        init();
    }




    public void onNetErrShowNormal(){
       if(activity!=null){
           SmartRefreshLayout smRf = activity.findViewById(R.id.smRf);
           if(smRf!=null){
               smRf.finishRefresh();
               smRf.finishLoadMore();
           }
           if(loadingManager!=null&&loadingManager.isLoadingShow()){
               loadingManager.showEmptyLayout();
           }
       }
    }


    public void copy(String text){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }



    public void showMsgDialog(String msg, String sureBtText, NoDoubleClickListener clickListener){
        CommonMsgDialog commonMsgDialog=new CommonMsgDialog(getContext());
        commonMsgDialog.getHolder().tvSure.setText(sureBtText);
        commonMsgDialog.getHolder().tvContent.setText(msg);
        commonMsgDialog.getHolder().tvSure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                commonMsgDialog.dismiss();
                clickListener.onClick(v);
            }
        });
        commonMsgDialog.show();
    }

    public void showMsgDialog(String msg){
        CommonMsgDialog commonMsgDialog=new CommonMsgDialog(getContext());
        commonMsgDialog.getHolder().tvSure.setText("我知道了");
        commonMsgDialog.getHolder().tvContent.setText(msg);
        commonMsgDialog.getHolder().tvSure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                commonMsgDialog.dismiss();
            }
        });
        commonMsgDialog.show();
    }

    public void showMsgDialogCannotClose(String msg,String sureBtText,NoDoubleClickListener clickListener){
        CommonMsgDialog commonMsgDialog=new CommonMsgDialog(getContext());
        commonMsgDialog.getHolder().tvSure.setText(sureBtText);
        commonMsgDialog.getHolder().tvContent.setText(msg);
        commonMsgDialog.getHolder().tvSure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                commonMsgDialog.dismiss();
                clickListener.onClick(v);
            }
        });
        commonMsgDialog.setCancelable(false);
        commonMsgDialog.show();
    }


    public SharedPreferenceUtil getSpUtil(){
        return BaseApp.getSharedPreferenceUtil();
    }

    public Context getContext(){
        if(activity!=null){
            return activity;
        }
        if(fragment!=null){
            return fragment.getActivity();
        }
        return null;
    }


    public Activity getActivity(){
        if(activity!=null){
            return activity;
        }
        if(fragment!=null){
            return fragment.getActivity();
        }
        return null;
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


    public Resources getResources(){
        return getContext().getResources();
    }

    /**
     * 如果重写生命周期方法，需要在对应Activity的生命周期中进行viewModle.onResume()的调用才可执行
     */
    public void onResume() {

    }

    public void onPause() {

    }

    public void onRestart() {

    }

    public void onDestroy() {

    }

    //主动在Activity中对应位置调用
    public void onActivityResult(Intent data){

    }

    public void startActivityForResult(Intent intent,int requestCode){
        if(activity!=null){
            activity.startActivityForResult(intent,requestCode);
        }

        if(fragment!=null){
            fragment.getActivity().startActivityForResult(intent,requestCode);
        }

    }

    public void startActivity(Intent intent){
        if(activity!=null){
            activity.startActivity(intent);
        }

        if(fragment!=null){
            fragment.getActivity().startActivity(intent);
        }

    }

    public void finish(){
        if(activity!=null){
            activity.finish();
        }

        if(fragment!=null){
            fragment.getActivity().finish();
        }
    }






    public Intent getIntent(){
        if(fragment!=null){
            return fragment.getActivity().getIntent();
        }else if(activity!=null){
            return activity.getIntent();
        }
        return null;
    }


    public Bundle getArguments(){
        if(fragment!=null){
            return fragment.getArguments();
        }
        return null;
    }


    /**
     * 获取颜色资源
     * @param res
     * @return
     */
    public int getResColor(int res){
        return getResources().getColor(res);
    }



    /**
     * 获取尺寸资源
     * @param res
     * @return
     */
    public float getResDimens(int res){
        return getResources().getDimension(res);
    }


    /**
     * 获取文字资源
     * @param res
     * @return
     */
    public String getResString(int res){
        return getResources().getString(res);
    }


    public void setLoadingRootView(View view){
        if(view!=null) {
            loadingManager = ActivityLoadUtil.getInstance().useDefaultLoadLayout(view, this);
        }
    }

    public void setLoadingRootView(View view,OnStatusChildClickListener onStatusChildClickListener){
        if(view!=null) {
            this.onStatusChildClickListener=onStatusChildClickListener;
            loadingManager = ActivityLoadUtil.getInstance().useDefaultLoadLayout(view, this);
        }
    }

    public void showSuccessLayout(){
        if(loadingManager!=null){
            loadingManager.showSuccessLayout();
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


    public TextView getEmptyTv(){
        if(loadingManager!=null&&loadingManager.getEmptyLayout()!=null) {
            return loadingManager.getEmptyLayout().findViewById(R.id.re_try_bt);
        }else{
            return null;
        }
    }



    /**
     * 创建模型的时候执行，可设置view的各种监听事件
     */
    public abstract void init();

    public void setClickListener(View...views){
        for(View view:views) {
            view.setOnClickListener(noDoubleClickListener);
        }
    }

    NoDoubleClickListener noDoubleClickListener=new NoDoubleClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            onViewClick(v);
        }
    };

    //可以重写
    public abstract void onViewClick(View v);




    /**
     * 设置加载中布局之后，为空时的点击事件
     * @param view
     */
    @Override
    public void onEmptyChildClick(View view) {
        if(onStatusChildClickListener!=null){
            onStatusChildClickListener.onEmptyChildClick(view);
        }
    }

    @Override
    public void onErrorChildClick(View view) {
        if(onStatusChildClickListener!=null){
            onStatusChildClickListener.onEmptyChildClick(view);
        }
    }

    @Override
    public void onCustomerChildClick(View view) {
        if(onStatusChildClickListener!=null){
            onStatusChildClickListener.onCustomerChildClick(view);
        }
    }


    /**
     * 显示转圈的Dialog
     *
     * @return
     */
    public Dialog showLoadingDialog(String msg, boolean canDismiss) {
        closeDialog();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v
                .findViewById(R.id.dialog_loading_view);// 加载布局
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(canDismiss); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        loadingDialog.show();
        this.loadingDialog = loadingDialog;
        return loadingDialog;
    }


    /**
     * 关闭dialog
     */
    public void closeDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
            this.loadingDialog = null;
        }
    }



    /**
     * 获取版本号
     *
     * @return
     */
    public int getVersionNum() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }


    /**
     * 获取版本名称
     *
     * @return
     */
    public String getVersionName() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String versionName = info.versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0";
        }
    }


    /**
     * 获取应用名称
     *
     * @return
     */
    public String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }



    public void showTs(String msg) {
        if (toast == null) {
            toast = new ToastUtil(msg);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }



}
