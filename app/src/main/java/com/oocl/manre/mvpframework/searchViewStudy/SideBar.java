package com.oocl.manre.mvpframework.searchViewStudy;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.view.View;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * Created by manre on 2/13/17.
 */

public class SideBar extends View {

    public static String[] INDEX_STRING = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    private Paint paint = new Paint();
    private OnTouchLetterChangeListenner listenner;
    // 是否画出背景
    private boolean showBg = false;
    // 选中的项
    private int choose = -1;
    // 准备好的A~Z的字母数组
    public static List<String> letters ;

    // 构造方法
    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        letters = Arrays.asList(INDEX_STRING);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取宽和高
        int width = getWidth();
        //int height = getHeight();
        int height= getScreamHeight();
        // 每个字母的高度
        int singleHeight = height / letters.size();
        if (showBg) {
            // 画出背景
            canvas.drawColor(Color.parseColor("#55000000"));
        }
        // 画字母
        for (int i = 0; i < letters.size(); i++) {
            paint.setColor(Color.BLACK);
            // 设置字体格式
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20f);
            // 如果这一项被选中，则换一种颜色画
            if (i == choose) {
                paint.setColor(Color.parseColor("#F88701"));
                paint.setFakeBoldText(true);
            }
            // 要画的字母的x,y坐标
            float posX = width / 2 - paint.measureText(letters.get(i)) / 2;
            float posY = i * singleHeight + singleHeight+getMarginTop();
            // 画出字母
            canvas.drawText(letters.get(i), posX, posY, paint);
            // 重新设置画笔
            paint.reset();
        }
    }

    private int getMarginTop()
    {
        return (getHeight()-getScreamHeight())/2;
    }

    private int getScreamHeight()
    {
        return (int) (25f*letters.size());
    }
    /**
     * 处理SlideBar的状态
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        // 算出点击的字母的索引
        //final int index = (int) (y / getScreamHeight() * letters.size());
        final int index=(int)((y-getMarginTop())/25f);
        // 保存上次点击的字母的索引到oldChoose
        final int oldChoose = choose;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != index && listenner != null && index > 0
                        && index < letters.size()) {
                    choose = index;
                    listenner.onTouchLetterChange(showBg, letters.get(index));
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (oldChoose != index && listenner != null && index > 0
                        && index < letters.size()) {
                    choose = index;
                    listenner.onTouchLetterChange(showBg, letters.get(index));
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1;
                if (listenner != null) {
                    if (index <= 0) {
                        listenner.onTouchLetterChange(showBg, "A");
                    } else if (index > 0 && index < letters.size()) {
                        listenner.onTouchLetterChange(showBg, letters.get(index));
                    } else if (index >= letters.size()) {
                        listenner.onTouchLetterChange(showBg, "Z");
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 回调方法，注册监听器
     *
     * @param listenner
     */
    public void setOnTouchLetterChangeListenner(
            OnTouchLetterChangeListenner listenner) {
        this.listenner = listenner;
    }

    public void setLetters(Set<String> letters)
    {
        this.letters=new ArrayList<>();
        for (String letter:letters
             ) {
            this.letters.add(letter.toUpperCase());
        }
        Comparator<Object> cmp= Collator.getInstance(Locale.ENGLISH);
        Collections.sort(this.letters,cmp);
        invalidate();
    }
    public void setIndexText(ArrayList<String> indexStrings) {
        this.letters = indexStrings;
        invalidate();
    }
    /**
     * SlideBar 的监听器接口
     *
     * @author Folyd
     *
     */
    public interface OnTouchLetterChangeListenner {

        void onTouchLetterChange(boolean isTouched, String s);
    }

}
