package com.app.mybaseframwork.base;


import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.app.mybaseframwork.base.base_model.MyBaseViewModel;
import com.dy.fastframework.activity.BaseActivity;
import com.dy.fastframework.util.SharedPreferenceUtil;

import java.lang.reflect.ParameterizedType;



/**
 * 复制此Activity到自己的基类中
 * 默认顶到状态栏的
 * @param <T>
 */
public abstract class MyBaseDataBindingActivity<V extends MyBaseViewModel<T>,T extends ViewDataBinding> extends BaseActivity {
    public T binding;
    public V viewModel;



    @Override
    public void setLayoutVoid(int setLayout) {
        binding = DataBindingUtil.setContentView(this,setLayout);
        viewModel=createViewModel();
    }

    protected abstract V createViewModel();




    public SharedPreferenceUtil getSpUtil(){
        return BaseApp.getSharedPreferenceUtil();
    }





    @Override
    public void closeDialog() {
        super.closeDialog();
        if(viewModel!=null){
            viewModel.closeDialog();
        }
    }



}
