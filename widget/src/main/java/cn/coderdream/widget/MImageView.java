package cn.coderdream.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class MImageView extends AppCompatImageView {

    private Context mContext;
    private float mImageView_corners = 0;
    private float mImageView_stroke = 0;
    private int mImageView_stroke_color = Color.parseColor("#ffffff");
    private Paint mPaint;

    public MImageView(Context context) {
        this(context, null);
    }

    public MImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initPaint();
        init(attrs, defStyleAttr);
        //防止onDraw方法不执行
        setWillNotDraw(false);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.MImageView, defStyleAttr, 0);
        try {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                if (index == R.styleable.MImageView_mImageView_corners) {
                    mImageView_corners = typedArray.getDimension(index, 0);
                } else if (index == R.styleable.MImageView_mImageView_stroke) {
                    mImageView_stroke = typedArray.getDimension(index, 0);
                } else if (index == R.styleable.MImageView_mImageView_stroke_color) {
                    mImageView_stroke_color = typedArray.getColor(index, Color.parseColor("#ffffff"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width <= 0) {
            try {
                throw new MImageViewExcetion("宽度不能为0");
            } catch (MImageViewExcetion mImageViewExcetion) {
                mImageViewExcetion.printStackTrace();
            }
        }
        if (height <= 0) {
            try {
                throw new MImageViewExcetion("高度不能为0");
            } catch (MImageViewExcetion mImageViewExcetion) {
                mImageViewExcetion.printStackTrace();
            }
        }
        setMeasuredDimension(width, height);
    }

    private void initPaint() {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防止抖动
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(0, 0);

        float width = ((float) getWidth());
        float height = ((float) getHeight());

        //绘制
        @SuppressLint("DrawAllocation") Path path = new Path();
        if (width == height && width == 2 * mImageView_corners) {
            path.addCircle((width / 2), height / 2, mImageView_corners, Path.Direction.CW);
            canvas.clipPath(path);
        } else {
            @SuppressLint("DrawAllocation") RectF rectF = new RectF();
            rectF.left = 0;
            rectF.top = 0;
            rectF.right = width;
            rectF.bottom = height;
            path.addRoundRect(rectF, mImageView_corners, mImageView_corners, Path.Direction.CW);
            canvas.clipPath(path);
        }

        super.onDraw(canvas);

        mPaint.setColor(mImageView_stroke_color);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mImageView_stroke);
        mPaint.setStyle(Paint.Style.STROKE);

        if (width == height && width == 2 * mImageView_corners) {
            canvas.drawCircle(width / 2, height / 2, mImageView_corners, mPaint);
        } else {
            @SuppressLint("DrawAllocation") RectF rectF = new RectF();
            rectF.left = 0;
            rectF.top = 0;
            rectF.right = width;
            rectF.bottom = height;
            canvas.drawRoundRect(rectF, mImageView_corners, mImageView_corners, mPaint);
        }
    }

    private class MImageViewExcetion extends Exception {

        public MImageViewExcetion(String message) {
            super(message);
            Log.e("MImageView", message);
        }
    }
}
