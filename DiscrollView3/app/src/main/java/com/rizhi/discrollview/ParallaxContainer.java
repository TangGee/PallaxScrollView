package com.rizhi.discrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by tangtang on 15/5/27.
 * 其实这里有更好的办法  只需要把动画属性做成成员变量就可以了
 * 这里为了学习LayoutParamter属性 所以 放在LayoutParamter里边啦 哈哈哈哈哈哈哈哈哈哈啊好
 *
 */
public class ParallaxContainer extends LinearLayout {

    public ParallaxContainer(Context context) {
        super(context);

        setOrientation(VERTICAL);
    }

    public ParallaxContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);

    }

    public ParallaxContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(asParallaxView(child,(LayoutParams)params), index, params);
    }

    private View asParallaxView(View child,ViewGroup.LayoutParams layoutParams) {

        if(!(layoutParams instanceof LayoutParams)){
            return child;
        }

        LayoutParams params= (LayoutParams) layoutParams;
        /**
         * 初始化
         */
        ParallaxView parallaxView=new ParallaxView(getContext());

        parallaxView.setParallaxAphal(params.aphal);
        parallaxView.setParallaxScaleX(params.scaleX);
        parallaxView.setParallaxScaleY(params.scaleX);
        parallaxView.setParallaxThreshold(params.threshold);
        parallaxView.setParallaxFormBgColor(params.fromBgColor);
        parallaxView.setParallaxToBgColor(params.toBgColor);
        parallaxView.setParallaxTranslation(params.translation);

        parallaxView.addView(child);

        return parallaxView;
    }

    /**
     * 这里 应该重写所有的构造方法  不过我更新了22的api 没有更新源码  所以手头没有 以后更改
     *
     */
    public static class  LayoutParams extends  LinearLayout.LayoutParams{



        public boolean aphal;
        public  boolean scaleX;
        public  boolean scaleY;
        public  float threshold;
        public int fromBgColor;
        public int toBgColor;
        public int translation;
        /**
         * 阀值  用于设置平移动画的阀值
         */
        private float gateValue;


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray array=c.obtainStyledAttributes(attrs,R.styleable.ParallaxParamster);

            aphal= array.getBoolean(R.styleable.ParallaxParamster_parallax_aphal,false);
            scaleX=array.getBoolean(R.styleable.ParallaxParamster_parallax_scaleX,false);
            scaleY=array.getBoolean(R.styleable.ParallaxParamster_parallax_scaleY,false);
            threshold= array.getFloat(R.styleable.ParallaxParamster_parallax_threshold,.0f);
            fromBgColor=array.getColor(R.styleable.ParallaxParamster_parallax_fromBgColor, -1);
            toBgColor= array.getColor(R.styleable.ParallaxParamster_parallax_toBgColor,-1);
            translation=array.getInt(R.styleable.ParallaxParamster_parallax_translation,-1);

            array.recycle();



        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }



    }


    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LinearLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }



}
