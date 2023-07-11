package com.app.mybaseframwork.test;


import android.view.View;

import com.app.mybaseframwork.R;
import com.app.mybaseframwork.base.MyBaseDataBindingFragment;
import com.app.mybaseframwork.base.base_model.CommonViewModel;
import com.app.mybaseframwork.databinding.FragmentTestBinding;
import com.lzy.widget.HeaderScrollHelper;

public class PageFg extends MyBaseDataBindingFragment<CommonViewModel<FragmentTestBinding>,FragmentTestBinding> implements HeaderScrollHelper.ScrollableContainer {
    @Override
    protected CommonViewModel<FragmentTestBinding> createViewModel() {
        return new CommonViewModel<FragmentTestBinding>(this,binding);
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_test;
    }

    @Override
    public void init() {

    }

    @Override
    public View getScrollableView() {
        return binding.tvContent;
    }
}
