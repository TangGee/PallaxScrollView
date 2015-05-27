package com.rizhi.discrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import java.util.Map;

/**
 * Created by tangtang on 15/5/27.
 */
public class ParallaxScrollView extends ScrollView {



    private ParallaxContainer container;

    public ParallaxScrollView(Context context) {
        super(context);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(getChildCount()!=1)
            throw new IllegalArgumentException("错了，山炮 我们的scrollview 只能有一个孩子");
        View view=getChildAt(0);

        if(view instanceof  ParallaxContainer)
        {
            container= (ParallaxContainer) view;
        }else{
            throw  new IllegalArgumentException("必须包含一个类型为ParallaxContiner的子孩子");
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        onScroll(t);
//        onScrollChanged(t);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    /**
     * 对滚动事件进行监听  妈妈咪啊
     * 这里有两种情况
     * 一. 当continer的孩子比较大的时候  大于屏幕的1/2 我们设置该孩子出现在屏幕就进行动画变化 这里又有两种请款
     *      1 当自孩子 漏出屏幕  设置动画
     *      2 没有漏出屏幕 则复位
     * 二, 当continer的孩子比较小 小于屏幕二分之一，这时候我们要求当孩子滚动到父控件一半的时候再进行动画 这里分为两种请款
     *      1 滚动到屏幕一般  开启动画
     *      2 未滚动到一般 复位
     *
     *
     *
     */
    private void onScroll(int top) {

        /**
         * 高度 也就是屏幕的高度
         */
        int height=getHeight();

        /**
         *
         */
        int halPosition=getHeight()/2;

        /**
         * 底部
         */
        int absBottom=container.getBottom();

        for (int i=0;i<container.getChildCount();i++)
        {
            View view=container.getChildAt(i);

            if(!(view instanceof  ParallaxView))
                continue;

            ParallaxView child= (ParallaxView) view;

            int childTop=view.getTop();

            /**
             * 距离顶端的距离
             */
            int distanceTop=childTop-top;

            int childHeight=child.getHeight();

            /**
             * 如果底部胜于小于从中间位置向上滚动到child底部 则就从底部开始执行动画
             */
            if(absBottom-child.getBottom()<childHeight+halPosition)
            {
                if(distanceTop<=height){
                    int visibleGap = height - distanceTop;
                    child.onScrollParallaxView(clamp(visibleGap / (float) childHeight, 0.0f, 1.0f));
                }else{
                    child.onResetParallaxView();
                }
            }else{

                if(distanceTop<=halPosition)
                {
                    int visibleGap = halPosition - distanceTop;
                    child.onScrollParallaxView(clamp(visibleGap / (float) childHeight, 0.0f, 1.0f));
                }else {
                    child.onResetParallaxView();
                }

            }
        }



    }


    private int getAbsoluteBottom() {
        View last = getChildAt(getChildCount() - 1);
        if(last == null) {
            return 0;
        }
        return last.getBottom();
    }


    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }



}
