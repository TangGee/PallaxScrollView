package com.rizhi.discrollview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


/**
 * Created by tangtang on 15/5/27.
 */
public class ParallaxView extends FrameLayout {


    private static final int TOP=0x01;
    private static final int BOTTOM=0x02;
    private static final int LEFT=0x04;
    private static final int RIGHT=0x08;



    private boolean aphal;
    private   boolean scaleX;
    private   boolean scaleY;
    private   float threshold;
    private int fromBgColor;
    private int toBgColor;
    private int mTranslation;


    private static ArgbEvaluator argbEvaluator=new ArgbEvaluator();

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        onResetParallaxView();
    }




    /**
     * 进行动画计算
     * @只有ratio 只有ratio 超过阀值才会进行计算
     */
    public void onScrollParallaxView(float ratio) {

        int width=getWidth();
        int height=getHeight();

        if (ratio>threshold){
            if (aphal)
                setAlpha(ratio);

            if(scaleX)
                setScaleX(ratio);

            if(scaleY)
                setScaleY(ratio);

            float transRatio=1-ratio;



           if (mTranslation !=-1&&(mTranslation & TOP)!=0)
               setTranslationY(-height*transRatio);


            if (mTranslation !=-1&&(mTranslation & BOTTOM)!=0)
                setTranslationY(height*transRatio);

            if(mTranslation !=-1&&(mTranslation & LEFT)!=0)
                setTranslationX(-width*transRatio);

            if(mTranslation !=-1&&(mTranslation & RIGHT)!=0)
                setTranslationX(width*transRatio);

            if(fromBgColor!=-1&&toBgColor!=-1)
                setBackgroundColor((Integer) argbEvaluator.evaluate(ratio,fromBgColor,toBgColor));
        }


    }

    public ParallaxView(Context context) {
        super(context);
    }

    public ParallaxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setParallaxAphal(boolean aphal) {
        this.aphal = aphal;
    }

    public void setParallaxScaleX(boolean scaleX) {
        this.scaleX = scaleX;
    }

    public void setParallaxScaleY(boolean scaleY) {
        this.scaleY = scaleY;
    }

    public void setParallaxThreshold(float threshold) {

        if(threshold<0 || threshold>1)
        {
            threshold= (float) (Math.abs(threshold)-Math.floor(Math.abs(threshold)));
        }

        this.threshold = threshold;
    }

    public void setParallaxFormBgColor(int formBgColor) {
        this.fromBgColor = formBgColor;
    }

    public void setParallaxToBgColor(int toBgColor) {
        this.toBgColor = toBgColor;
    }

    public void setParallaxTranslation(int translation) {
        if (!canTranlation(translation))
            throw  new IllegalArgumentException("不能进行相反方向的平移动画");
        this.mTranslation = translation;

    }

    public  boolean canTranlation(int translation)
    {

        if (translation==-1){
            return true;
        }

        if(((translation&LEFT)!=0&&(translation&RIGHT)!=0)||((translation&TOP)!=0&&(translation&BOTTOM)!=0)){
            return false;
        }

       return true;
    }


    /**
     * 复位
     */
    public void onResetParallaxView() {

        int height=getHeight();
        int width=getWidth();

        if(aphal) {
            setAlpha(0.0f);
        }
        if(mTranslation !=-1&&(mTranslation &BOTTOM)!=0) {
            setTranslationY(height);
        }
        if(mTranslation !=-1&&(mTranslation &TOP)!=0) {
            setTranslationY(-height);
        }
        if(mTranslation !=-1&&(mTranslation &LEFT)!=0) {
            setTranslationX(-width);
        }
        if(mTranslation !=-1&&(mTranslation &RIGHT)!=0) {
            setTranslationX(width);
        }
        if(scaleX) {
            setScaleX(0.0f);
        }
        if(scaleX) {
            setScaleY(0.0f);
        }
        if(fromBgColor != -1 && toBgColor != -1) {
            setBackgroundColor(fromBgColor);
        }

    }
}
