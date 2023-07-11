package com.app.mybaseframwork.base.base_model;

import android.app.Activity;
import android.view.View;


import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * 用于简单的页面无需分离model类的地方
 * @param <T>
 */
public class CommonViewModel<T extends ViewDataBinding> extends MyBaseViewModel<T> {

    public CommonViewModel(Activity activity, T binding) {
        super(activity, binding);
    }

    public CommonViewModel(Fragment fragment, T binding) {
        super(fragment, binding);
    }

    @Override
    public void init() {

    }

    @Override
    public void onViewClick(View v) {

    }
}
