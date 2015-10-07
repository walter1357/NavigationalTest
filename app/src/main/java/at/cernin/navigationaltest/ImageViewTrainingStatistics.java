package at.cernin.navigationaltest;

/**
 * Created by Walter on 21.09.2015.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import at.cernin.mathstack.model.MathTrainingStatistics;
import at.cernin.navigationaltest.R;


public class ImageViewTrainingStatistics
        extends View {

    private Paint myRedPaint;
    private Paint myGreenPaint;
    private Paint myGrayPaint;

    int heightFactor;       // Höhenfaktor der gezeichneten Grafik
                            // wie hoch soll die Grafik in dp sein
    float chartWidth;       // Breite der gezeichneten Grafik
    private Rect redRect;   // Position des roten Rechtecks
                            //   die rechte Flanke floatet
    private Rect greenRect; // Position des grünen Rechtecks im Chart
                            //   die linke Flanke floatet
    private Rect grayRect;  // Position des Rechtecks des gesamten Charts

    private int maxSize = 0;
    private int greenSize = 0;
    private int redSize = 0;


    public ImageViewTrainingStatistics(Context context, AttributeSet attrs) {
        super(context, attrs);

//        maxSize = 1000;
//        redSize = (int) (Math.random()*(maxSize*0.8f));
//        greenSize = (int) (Math.random()*(maxSize*0.8f));
//        if (redSize+greenSize > maxSize) {
//            redSize = maxSize-greenSize;
//        }

        myGrayPaint = new Paint();
        myGrayPaint.setColor(getResources().getColor(R.color.mathe_darkgray));
        myGrayPaint.setStyle(Style.STROKE);
        myGrayPaint.setStrokeWidth(dp2px(4));

        myRedPaint = new Paint();
        myRedPaint.setStyle(Style.FILL);
        myRedPaint.setColor(getResources().getColor(R.color.mathe_red));

        myGreenPaint = new Paint();
        myGreenPaint.setStyle(Style.FILL);
        myGreenPaint.setColor(getResources().getColor(R.color.mathe_green));

        DisplayMetrics dm = getResources().getDisplayMetrics();
        heightFactor = (int) (dm.ydpi/160*20);

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        this.invalidate();
    }

    public int getGreenSize() {
        return greenSize;
    }

    public void setGreenSize(int greenSize) {
        this.greenSize = greenSize;
        this.invalidate();
    }

    public int getRedSize() {
        return redSize;
    }

    public void setRedSize(int redSize) {
        this.redSize = redSize;
        this.invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        chartWidth = w - 1 - getPaddingRight() - getPaddingLeft();

        redRect = new Rect(getPaddingLeft(), getPaddingTop(),
                getPaddingLeft(), h - 1 - getPaddingBottom());
        greenRect = new Rect(w - getPaddingRight(), getPaddingTop(),
                w - 1 - getPaddingRight(), h - 1 - getPaddingBottom());
        grayRect = new Rect(getPaddingLeft(), getPaddingTop(),
                w - 1 - getPaddingRight(), h - 1 - getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect;

        super.onDraw(canvas);

        if (maxSize > 0) {

            int lMaxSize = maxSize;
            int lGreenSize = greenSize;
            int lRedSize = redSize;
            if (redSize+greenSize > maxSize) {
                lRedSize = maxSize-greenSize;
            }

            //rect = new Rect(getPaddingLeft(), getPaddingTop(),
            //        (int) (w / lMaxSize * lRedSize + 0.5f), canvas.getHeight() - 1 - getPaddingBottom());
            rect = new Rect( redRect );
            rect.right += (int) (chartWidth / lMaxSize * lRedSize + 0.5f);
            myRedPaint.setShader(new LinearGradient(rect.left, redRect.top, rect.right, rect.bottom, getResources().getColor(R.color.mathe_red),
                    getResources().getColor(R.color.mathe_lightred), Shader.TileMode.CLAMP));
            canvas.drawRect(rect, myRedPaint);

            //rect = new Rect(canvas.getWidth() - getPaddingRight() - (int) (w / lMaxSize * lGreenSize + 0.5f), getPaddingTop(),
            //        canvas.getWidth() - 1 - getPaddingRight(), canvas.getHeight() - 1 - getPaddingBottom());
            rect.set(greenRect);
            rect.left -= (int) (chartWidth / lMaxSize * lGreenSize + 0.5f);
            myGreenPaint.setShader(new LinearGradient(rect.left, rect.bottom, rect.right, rect.top, getResources().getColor(R.color.mathe_lightgreen),
                    getResources().getColor(R.color.mathe_green), Shader.TileMode.CLAMP));
            canvas.drawRect(rect, myGreenPaint);
        }

        // rect = new Rect(getPaddingLeft(), getPaddingTop(),
        //        canvas.getWidth() - 1 - getPaddingRight(), canvas.getHeight() - 1 - getPaddingBottom());
        canvas.drawRect(grayRect, myGrayPaint);
        //canvas.drawLine(grayRect.left, grayRect.top, grayRect.right, grayRect.bottom, myGrayPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int x = MeasureSpec.getSize(widthMeasureSpec);
        int xMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = (new DisplayMetrics()).widthPixels / 2;

        if (xMode == MeasureSpec.EXACTLY) {
            width = x;
        }
        else if (xMode == MeasureSpec.AT_MOST) {
            width = Math.min( width, x);
        }
        else {
            // width = width;
        }

        // Height should be 10% of width
        int y  = MeasureSpec.getSize(heightMeasureSpec);
        int yMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = heightFactor;

        if (yMode == MeasureSpec.EXACTLY) {
            height = y;
        }
        else if (yMode == MeasureSpec.AT_MOST) {
            height = Math.min( height, y);
        }
        else {
            // height = height;
        }

        // Test for a width/Height based on our minimum
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        if (width < minw) {
            //width |= MEASURED_STATE_TOO_SMALL;
        }
        int minh = getPaddingBottom() + getPaddingTop() + getSuggestedMinimumHeight();;
        if (height < minh) {
            //height |= MEASURED_STATE_TOO_SMALL;
        }

        setMeasuredDimension(width, height);
        //super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

}
