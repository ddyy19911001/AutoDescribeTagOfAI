package listener;

import android.view.View;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;

public abstract class SimpleOnStatusClickListener implements OnStatusChildClickListener {
    /**
     * 空数据布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    public abstract void onEmptyChildClick(View view);

    /**
     * 出错布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    public void onErrorChildClick(View view){}

    /**
     * 自定义状态布局布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    public void onCustomerChildClick(View view){}
} 
