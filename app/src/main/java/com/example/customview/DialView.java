package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DialView extends View {

    private static int SELECTION_COUNT = 4;

    private float width;
    private float height;

    private Paint textPaint;
    private Paint dialPaint;

    private float radius;
    private int activeSelection;

    private final StringBuffer tempLabel = new StringBuffer(8);
    private final float[] tempResult = new float[2];

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40f);

        dialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dialPaint.setColor(Color.GRAY);

        activeSelection = 0;

        setOnClickListener(view -> {
            activeSelection = (activeSelection + 1) % SELECTION_COUNT;

            if (activeSelection >= 1 ) {
                dialPaint.setColor(Color.GREEN);
            } else {
                dialPaint.setColor(Color.GRAY);
            }

            invalidate();
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        radius = (float) (Math.min(width, height/2*0.8));
    }

    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private float[] computeXYPosition(final int pos, final float radius) {
        float[] result = tempResult;
        Double startAngle = Math.PI * (9 / 8d);
        Double angle = startAngle + (pos * (Math.PI / 4));
        result[0] = (float) (radius * Math.cos(angle) + (width / 2));
        result[1] = (float) (radius * Math.sin(angle) + (height / 2));
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width/2, height/2, radius, dialPaint);

        // draw label
        final float labelRadius = radius + 20;
        StringBuffer label = tempLabel;

        for (int i = 0; i < SELECTION_COUNT; i++) {
            float[] xyData = computeXYPosition(i, labelRadius);
            float x = xyData[0];
            float y = xyData[1];
            label.setLength(0);
            label.append(i);
            canvas.drawText(label, 0, label.length(), x, y, textPaint);
        }

        // draw small indicator circle
        final float markerRadius = radius - 35;
        float[] xyData = computeXYPosition(activeSelection, markerRadius);
        float x = xyData[0];
        float y = xyData[1];

        canvas.drawCircle(x, y, 20, textPaint);
    }
}
