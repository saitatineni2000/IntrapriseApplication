package com.catapult.android.mca;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class LineDrawable extends Drawable {
    private Paint mPaint;

    public LineDrawable() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(3);
    }

    @Override
    public void draw(Canvas canvas) {
        int lvl = getLevel();
        Rect b = getBounds();
        float x = b.width() * lvl / 10000.0f;
        float y = (b.height() - mPaint.getStrokeWidth()) / 2;
        mPaint.setColor(0xffff0000);
        canvas.drawLine(0, y, x, y, mPaint);
        mPaint.setColor(0xff00ff00);
        
        canvas.drawLine(x, y, b.width(), y, mPaint);
    }

    @Override
    public boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
