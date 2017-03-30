package com.xiaoka.xksupportutils.click;

/**
 * Created by changping on 16/6/20.
 */
import android.view.View;

import java.util.Calendar;

public abstract class DoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_TIME = 1000;
    private long lastClickTime = 0;
    private int duringTime;

    public DoubleClickListener(int lastClickTime) {
        this.duringTime = lastClickTime;
    }

    public DoubleClickListener() {
        duringTime=MIN_CLICK_TIME;
    }

    @Override
    public void onClick(View v) {
//        if (NetWorkUtil.getNetworkState(v.getContext()) == NetWorkUtil.NETWORN_NONE) {
//            PromptUtil.showNormalToast(R.string.network_isnot_available);
//            return;
//        }

        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime < duringTime) {

            onDoubleClickListener(v);
        }else{
            lastClickTime = currentTime;
            onClickListener(v);
        }
    }

    protected abstract void onClickListener(View pV);

    protected abstract void onDoubleClickListener(View pV);


}