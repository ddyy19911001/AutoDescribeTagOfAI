package com.app.mybaseframwork;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dy.fastframework.util.ScreenUtils;
import com.dy.fastframework.view.DialogFragmentBase;

public class TestDialogFragment extends DialogFragmentBase {
    private TextView tvTitle;
    private String currentStr;

    public TestDialogFragment(Bundle data) {
        super(data);
    }


    @Override
    public int setLayoutId() {
        return R.layout.dialog_test;
    }

    @Override
    public void bindViewWithId(View view) {
        tvTitle= view.findViewById(R.id.tv_title);
    }

    @Override
    public boolean setWindowLayoutParams(WindowManager.LayoutParams lp, Window window) {
        lp.width= ScreenUtils.dipTopx(getContext(),300);
        lp.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity= Gravity.CENTER;
        window.setAttributes(lp);
        return true;
    }

    @Override
    protected void init() {
        currentStr = getBundleData().getString("data");
        tvTitle.setText(currentStr);
        currentStr="本次数据内部变更了";
    }

} 
