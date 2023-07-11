package com.app.mybaseframwork.test;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.mybaseframwork.R;
import com.app.mybaseframwork.base.MyBaseDataBindingActivity;
import com.app.mybaseframwork.base.base_model.CommonViewModel;
import com.app.mybaseframwork.databinding.DefaultHeadViewLayoutBinding;
import com.dy.fastframework.fragment.BasePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试头部悬停底部viewpager的实现方式
 */
public class CanScrollTopVpActivity extends MyBaseDataBindingActivity<CommonViewModel<DefaultHeadViewLayoutBinding>,DefaultHeadViewLayoutBinding> {
    @Override
    protected CommonViewModel<DefaultHeadViewLayoutBinding> createViewModel() {
        return new CommonViewModel<DefaultHeadViewLayoutBinding>(this,binding);
    }

    @Override
    public int setLayout() {
        return R.layout.default_head_view_layout;
    }

    @Override
    public void initFirst() {
        List<Fragment> fgs=new ArrayList<>();
        for(int i=0;i<3;i++) {
            PageFg fg=new PageFg();
            fgs.add(fg);
        }
        binding.scrollableLayout.setCurrentScrollableContainer((PageFg)fgs.get(0));
        binding.vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.scrollableLayout.setCurrentScrollableContainer((PageFg)fgs.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        BasePagerAdapter basePagerAdapter=new BasePagerAdapter(getSupportFragmentManager(),fgs);
        binding.vpContent.setAdapter(basePagerAdapter);
    }
}
