package cn.coderdream.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * 创建：MengPeng
 * 时间：2019/4/9 - 上午11:00
 * 用途：进度条
 */
public class MProgressBar extends View {

    private Context mContext;
    private int background_color = Color.parseColor("#666666");
    private int progress_background_color = Color.parseColor("#336699");
    private float mProgressBar_stroke = 0;
    private float mProgressBar_corners = 0;
    private int currentSize = 0;
    private int totalSize = 100;
    private boolean isShowContent = false;

    private float content_textSize = 0;
    private int content_textColor = Color.parseColor("#ffffff");
    public static final int percent = 100;
    public static final int ratio = 101;
    private int content_textStyle = 101;
    private String content;
    private boolean isShowPercentContent = true;

    private Paint mPaint;

    private ProgressUpdateListener listener;

    public interface ProgressUpdateListener {
        //进度更新回调
        void onProgressUpdate(int currentSize, int totalSize);

        //进度条走完回调
        void onProgressOver();
    }

    public void onProgressUpdateListener(ProgressUpdateListener listener) {
        this.listener = listener;
    }


    public MProgressBar(Context context) {
        this(context, null);
    }

    public MProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs, defStyleAttr);
        initPaint();
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.MProgressBar, defStyleAttr, 0);
        try {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                if (index == R.styleable.MProgressBar_mProgressBar_background_color) {
                    background_color = typedArray.getColor(index, Color.parseColor("#666666"));
                } else if (index == R.styleable.MProgressBar_mProgressBar_progress_background_color) {
                    progress_background_color = typedArray.getColor(index, Color.parseColor("#336699"));
                } else if (index == R.styleable.MProgressBar_mProgressBar_stroke) {
                    mProgressBar_stroke = typedArray.getDimension(index, 0);
                } else if (index == R.styleable.MProgressBar_mProgressBar_corners) {
                    mProgressBar_corners = typedArray.getDimension(index, 0);
                } else if (index == R.styleable.MProgressBar_mProgressBar_currentSize) {
                    currentSize = typedArray.getInteger(index, 0);
                } else if (index == R.styleable.MProgressBar_mProgressBar_totalSize) {
                    totalSize = typedArray.getInteger(index, 0);
                } else if (index == R.styleable.MProgressBar_mProgressBar_content_textSize) {
                    content_textSize = typedArray.getDimension(index, 0);
                } else if (index == R.styleable.MProgressBar_mProgressBar_isShowContent) {
                    isShowContent = typedArray.getBoolean(index, false);
                } else if (index == R.styleable.MProgressBar_mProgressBar_content_textColor) {
                    content_textColor = typedArray.getColor(index, Color.parseColor("#ffffff"));
                } else if (index == R.styleable.MProgressBar_mProgressBar_content_textStyle) {
                    content_textStyle = typedArray.getInt(index, percent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }


    private void initPaint() {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防止抖动
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.UNSPECIFIED) {//未指定宽wrap_content
            try {
                throw new MProgressBarExcetion("MProgressBar的宽度不能为wrap_content");
            } catch (MProgressBarExcetion mProgressBarExcetion) {
                mProgressBarExcetion.printStackTrace();
            }
        }
        if (heightMode == MeasureSpec.UNSPECIFIED) {//未指定高wrap_content
            try {
                throw new MProgressBarExcetion("MProgressBar的高度不能为wrap_content");
            } catch (MProgressBarExcetion mProgressBarExcetion) {
                mProgressBarExcetion.printStackTrace();
            }
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0, 0);
        //1.绘制背景
        drawBackground(canvas);
        //2.绘制进度
        drawProgress(canvas);
        //3.绘制进度文字内容
        drawProgressContent(canvas);
    }

    /**
     * 第一步，绘制背景
     */
    private void drawBackground(Canvas canvas) {
        //1.先给画笔设置颜色
        mPaint.setColor(background_color);
        //2.获取控件的宽和高
        int width = getWidth();
        int height = getHeight();
        //3.设置绘制区域的左边
        RectF rectF = new RectF();
        rectF.left = 0;
        rectF.top = 0;
        rectF.right = width;
        rectF.bottom = height;
        //4.设置画笔的样式，Paint.Style.FILL填充 、Paint.Style.STROKE边框 、Paint.Style.FILL_AND_STROKE描边
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, mProgressBar_corners, mProgressBar_corners, mPaint);
    }

    /**
     * 第二步，绘制进度
     */
    private void drawProgress(Canvas canvas) {
        //1.先给画笔设置颜色
        mPaint.setColor(progress_background_color);
        //2.获取控件的宽和高
        int width = getWidth();
        int height = getHeight();
        //3.设置绘制区域的左边
        RectF rectF = new RectF();
        rectF.left = mProgressBar_stroke;
        rectF.top = mProgressBar_stroke;
        rectF.right = mProgressBar_stroke + ((width - 2 * mProgressBar_stroke) * ((float) currentSize / (float) totalSize));
        rectF.bottom = height - mProgressBar_stroke;
        //4.设置画笔的样式，Paint.Style.FILL填充 、Paint.Style.STROKE边框 、Paint.Style.FILL_AND_STROKE描边
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, mProgressBar_corners - mProgressBar_stroke, mProgressBar_corners - mProgressBar_stroke, mPaint);
    }

    /**
     * 第三步，绘制进度文字
     */
    private void drawProgressContent(Canvas canvas) {
        if (!isShowContent) return;
        mPaint.setColor(content_textColor);
        float textSize;
        if (content_textSize == 0) {
            textSize = sp2px(12);
        } else {
            textSize = content_textSize;
        }
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        if (isShowPercentContent) {
            if (content_textStyle == percent) {
                content = ((float) currentSize) * 100 / ((float) totalSize) + "%";
            } else {
                content = currentSize + "/" + totalSize;
            }
        }

        Rect bounds = new Rect();
        mPaint.getTextBounds(content, 0, content.length(), bounds);
        float offSet = ((float) (bounds.top + bounds.bottom)) / 2;

        canvas.drawText(content, ((float) getWidth()) / 2, ((float) getHeight()) / 2 - offSet, mPaint);
    }

    public void setBackground_color(int background_color) {
        this.background_color = background_color;
        postInvalidate();
    }

    public void setProgress_background_color(int progress_background_color) {
        this.progress_background_color = progress_background_color;
        postInvalidate();
    }

    public void setmProgressBar_stroke(float mProgressBar_stroke) {
        this.mProgressBar_stroke = mProgressBar_stroke;
        postInvalidate();
    }

    public void setmProgressBar_corners(float mProgressBar_corners) {
        this.mProgressBar_corners = mProgressBar_corners;
        postInvalidate();
    }

    public void setProgress(int currentSize, int totalSize) {
        this.currentSize = currentSize;
        this.totalSize = totalSize;
        if (currentSize < totalSize) {
            postInvalidate();
        }

        if (listener != null) {
            listener.onProgressUpdate(currentSize, totalSize);
            if (currentSize >= totalSize) {
                listener.onProgressOver();
            }
        }
    }

    public void setContent(String content) {
        isShowPercentContent = false;
        this.content = content;
        postInvalidate();
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
        postInvalidate();
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
        postInvalidate();
    }

    public void setShowContent(boolean showContent) {
        isShowContent = showContent;
        postInvalidate();
    }

    public void setContent_textSize(float content_textSize) {
        this.content_textSize = content_textSize;
        postInvalidate();
    }

    public void setContent_textColor(int content_textColor) {
        this.content_textColor = content_textColor;
        postInvalidate();
    }

    public void setContent_textStyle(int content_textStyle) {
        this.content_textStyle = content_textStyle;
        postInvalidate();
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public int sp2px(float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将dp值转换为px值
     */
    public int dp2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    private class MProgressBarExcetion extends Exception {

        public MProgressBarExcetion(String message) {
            super(message);
            printStackTrace();
        }
    }
}
