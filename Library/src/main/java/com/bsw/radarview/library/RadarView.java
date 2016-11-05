package com.bsw.radarview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bsw on 2016/11/5.
 */

public class RadarView extends View {
    private static final String TAG = "RadarView";
    private int count = 6;
    private float angle = 0;
    private float radius;
    private int centerX;
    private int centerY;
    private float maxValue = 100;
    private Paint mainPaint;
    private Paint valuePaint;
    private Paint textPaint;
    private int lineColor;
    private int textColor;
    private int textSize;
    private float pointRadius;
    private int shadowColor;
    private int defaultColor=0x000000;
    private float scale;
    private List<RadarValue> datas=new ArrayList<>();

    public RadarView(Context context) {
        this(context,null,0);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.RadarView);
        lineColor=typedArray.getColor(R.styleable.RadarView_lineColor,defaultColor);
        textColor=typedArray.getColor(R.styleable.RadarView_textColor,defaultColor);
        textSize=typedArray.getInt(R.styleable.RadarView_textSize,20);
        pointRadius=typedArray.getDimension(R.styleable.RadarView_pointRadius,5);
        shadowColor=typedArray.getColor(R.styleable.RadarView_shadowColor,defaultColor);
        scale=typedArray.getFloat(R.styleable.RadarView_scale,0.7f);
        maxValue=typedArray.getFloat(R.styleable.RadarView_maxValue,100);
        typedArray.recycle();
        init();
    }
    public void setDatas(List<RadarValue> lists){
        if(lists.size()<4){
            throw new IllegalArgumentException("数据源长度必须大于等于4");
        }
        datas.clear();
        datas.addAll(lists);
        count=datas.size();
        angle=(float) (Math.PI * 2 / count);
        postInvalidate();
    }

    private void init() {
        mainPaint = new Paint();
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(lineColor);
        valuePaint = new Paint();
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        valuePaint.setColor(shadowColor);
        valuePaint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w, h) / 2 * scale;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!datas.isEmpty()){
            drawPolygon(canvas);
            drawLine(canvas);
            drawText(canvas);
            drawRegion(canvas);
        }
    }
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / (count - 1);
        for (int i = 1; i < count; i++) {
            float curR = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + curR, centerY);
                } else {
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, mainPaint);
        }
    }
    private void drawLine(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            path.lineTo(x, y);
            canvas.drawPath(path, mainPaint);
        }
    }
    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
            if(angle*i>=0&&angle*i<=Math.PI/2){
                canvas.drawText(datas.get(i).getName(), x,y,textPaint);
            }else if(angle*i>=3*Math.PI/2&&angle*i<=Math.PI*2){
                canvas.drawText(datas.get(i).getName(), x,y,textPaint);
            }else if(angle*i>Math.PI/2&&angle*i<=Math.PI){
                float dis = textPaint.measureText(datas.get(i).getName());
                canvas.drawText(datas.get(i).getName(), x-dis,y,textPaint);
            }else if(angle*i>=Math.PI&&angle*i<3*Math.PI/2){
                float dis = textPaint.measureText(datas.get(i).getName());
                canvas.drawText(datas.get(i).getName(), x-dis,y,textPaint);
            }
        }
    }
    private void drawRegion(Canvas canvas){
        Path path=new Path();
        valuePaint.setAlpha(255);
        for (int i = 0; i < count; i++) {
            double percent =datas.get(i).getValue()>=maxValue?1: datas.get(i).getValue()/maxValue;
            float x = (float) (centerX+radius*Math.cos(angle*i)*percent);
            float y = (float) (centerY+radius*Math.sin(angle*i)*percent);
            if(i==0){
                path.moveTo(x, centerY);
            }else{
                path.lineTo(x,y);
            }
            canvas.drawCircle(x,y,pointRadius,valuePaint);
        }
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);
        valuePaint.setAlpha(127);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }

}
