package com.app.mybaseframwork;

import android.app.Activity;
import android.view.View;

import com.app.mybaseframwork.base.base_model.MyBaseViewModel;
import com.app.mybaseframwork.databinding.ActivityMainBinding;

public class MainActivityViewModel extends MyBaseViewModel<ActivityMainBinding> {
    public MainActivityViewModel(Activity activity,ActivityMainBinding binding) {
        super(activity,binding);
    }

    @Override
    public void init() {

    }





    @Override
    public void onViewClick(View v) {

    }


    public void getSystemData(){

    }
}
