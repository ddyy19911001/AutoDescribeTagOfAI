package com.app.mybaseframwork;

import android.view.View;

import com.app.mybaseframwork.base.MyBaseDataBindingActivity;
import com.app.mybaseframwork.base.base_model.CommonViewModel;
import com.app.mybaseframwork.databinding.ActivityDetailsBinding;

public class ActivityDetails extends MyBaseDataBindingActivity<CommonViewModel<ActivityDetailsBinding>, ActivityDetailsBinding> {
    @Override
    protected CommonViewModel<ActivityDetailsBinding> createViewModel() {
        return new CommonViewModel<ActivityDetailsBinding>(this,binding);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_details;
    }

    @Override
    public void initFirst() {
        setActivityTitle("描述词详情");
        ConvertResult.TransResultBean data= (ConvertResult.TransResultBean) getIntent().getSerializableExtra("data");
        binding.etFan.setText(data.getDst());
        binding.etPromt.setText(data.getSrc());
        binding.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.copy(data.getSrc());
            }
        });
    }
}
