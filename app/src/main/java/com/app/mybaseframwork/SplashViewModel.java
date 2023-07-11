package com.app.mybaseframwork;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.app.mybaseframwork.base.base_model.MyBaseViewModel;
import com.app.mybaseframwork.databinding.ActivitySplashBinding;
import com.dy.fastframework.util.DownTimer;


public class SplashViewModel extends MyBaseViewModel<ActivitySplashBinding> {


    public SplashViewModel(Activity activity, ActivitySplashBinding binding) {
        super(activity, binding);
    }

    @Override
    public void init() {

    }

    public void jump(){
        DownTimer timer=new DownTimer();
        timer.setIntervalTime(500);
        timer.setTotalTime(1500);
        timer.setTimerLiener(new DownTimer.TimeListener() {
            @Override
            public void onFinish() {
                startActivity(new Intent(context,MainActivity.class));
                finish();
            }

            @Override
            public void onInterval(long remainTime) {

            }
        });
        timer.start();
    }

    @Override
    public void onViewClick(View v) {

    }
}
