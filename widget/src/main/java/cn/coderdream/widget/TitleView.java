package cn.coderdream.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleView extends RelativeLayout {

    private Context mContext;
    private TextView titleView_content;
    private TextView titleView_menuText;
    private TextView titleView_line;
    private ImageView titleView_back;
    private ImageView titleView_menuIcon;

    public static final int LEFT = 10000;
    public static final int CENTER = 10001;

    private int backIconResourceId = R.mipmap.titleview_back;
    private int menuIcon = -1;
    private boolean isShowBackIcon = true;
    private String titleContent = "标题";
    private int titleContent_gravity = 10001;
    private float titleTextSize = sp2px(16);
    private int titleTextColor = Color.parseColor("#ffffff");
    private int titleBackgroundColor = Color.parseColor("#343439");
    private String menuText = "";
    private float menuTextSize = sp2px(14);
    private int menuTextColor = Color.parseColor("#ffffff");
    private int bottomLineColor = Color.parseColor("#ffffff");
    private int statusBarColor = Color.parseColor("#343439");
    private boolean withStatusBarHeight = true;
    private float bottomLineHeight = 1;
    private RelativeLayout titleView_container;
    private TextView titleView_status;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        getView();
        init(attrs, defStyleAttr);
    }

    public void setOnBackClickListener(OnBackClickListener backClickListener) {
        this.backClickListener = backClickListener;
    }

    public void setOnMenuClickListener(OnMenuClickListener menuClickListener) {
        this.menuClickListener = menuClickListener;
    }

    public void setOnMenuIconClickListener(OnMenuIconClickListener menuIconClickListener) {
        this.menuIconClickListener = menuIconClickListener;
    }

    private OnBackClickListener backClickListener;
    private OnMenuClickListener menuClickListener;
    private OnMenuIconClickListener menuIconClickListener;

    public interface OnBackClickListener {
        void onClickListener();
    }

    public interface OnMenuClickListener {
        void onClickListener();
    }

    public interface OnMenuIconClickListener {
        void onClickListener();
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleView, defStyleAttr, 0);
        try {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                if (index == R.styleable.TitleView_mTitleView_backIcon) {//标题的返回按钮图标
                    backIconResourceId = typedArray.getResourceId(index, R.mipmap.titleview_back);
                } else if (index == R.styleable.TitleView_mTitleView_isShowBackIcon) {//是否显示标题的返回按钮
                    isShowBackIcon = typedArray.getBoolean(index, true);
                } else if (index == R.styleable.TitleView_mTitleView_titleContent) {//标题内容
                    titleContent = typedArray.getString(index);
                } else if (index == R.styleable.TitleView_mTitleView_titleContent_gravity) {//标题内容的位置
                    titleContent_gravity = typedArray.getInt(index, CENTER);
                } else if (index == R.styleable.TitleView_mTitleView_titleTextSize) {//标题内容的字体大小
                    titleTextSize = typedArray.getDimension(index, sp2px(16));
                } else if (index == R.styleable.TitleView_mTitleView_titleTextColor) {//标题内容的字体颜色
                    titleTextColor = typedArray.getColor(index, Color.parseColor("#ffffff"));
                } else if (index == R.styleable.TitleView_mTitleView_menuIcon) {//标题的菜单按钮
                    menuIcon = typedArray.getResourceId(index, -1);
                } else if (index == R.styleable.TitleView_mTitleView_titleBackgroundColor) {//标题的背景颜色
                    titleBackgroundColor = typedArray.getColor(index, Color.parseColor("#343439"));
                } else if (index == R.styleable.TitleView_mTitleView_menuText) {//标题右边的菜单文字
                    menuText = typedArray.getString(index);
                } else if (index == R.styleable.TitleView_mTitleView_menuTextSize) {//标题右边的菜单文字大小
                    menuTextSize = typedArray.getDimension(index, sp2px(14));
                } else if (index == R.styleable.TitleView_mTitleView_menuTextColor) {//标题右边的菜单文字颜色
                    menuTextColor = typedArray.getColor(index, Color.parseColor("#ffffff"));
                } else if (index == R.styleable.TitleView_mTitleView_bottomLineHeight) {//标题底部的线的高度
                    bottomLineHeight = typedArray.getDimension(index, 1);
                } else if (index == R.styleable.TitleView_mTitleView_bottomLineColor) {//标题底部的线的颜色
                    bottomLineColor = typedArray.getColor(index, Color.parseColor("#ffffff"));
                } else if (index == R.styleable.TitleView_mTitleView_statusBarColor) {//标题底部的线的颜色
                    statusBarColor = typedArray.getColor(index, Color.parseColor("#343439"));
                } else if (index == R.styleable.TitleView_mTitleView_withStatusBarHeight) {//标题是否包含状态栏的高度
                    withStatusBarHeight = typedArray.getBoolean(index, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            initView();
            typedArray.recycle();
        }
    }

    private void initView() {
        if (isShowBackIcon) {
            titleView_back.setVisibility(View.VISIBLE);
        } else {
            titleView_back.setVisibility(View.GONE);
        }
        if (backIconResourceId != -1) {
            titleView_back.setImageResource(backIconResourceId);
        }
        titleView_content.setText(titleContent);
        titleView_content.getPaint().setTextSize(titleTextSize);
        titleView_content.setTextColor(titleTextColor);
        titleView_container.setBackgroundColor(titleBackgroundColor);

        if (titleContent_gravity == LEFT) {
            titleView_content.setGravity(Gravity.CENTER_VERTICAL);
            titleView_content.setPadding(dp2px(60), 0, 0, 0);
        } else if (titleContent_gravity == CENTER) {
            titleView_content.setGravity(Gravity.CENTER);
        }
        if (!TextUtils.isEmpty(menuText)) {
            titleView_menuText.setText(menuText);
            titleView_menuText.getPaint().setTextSize(menuTextSize);
            titleView_menuText.setTextColor(menuTextColor);
            titleView_menuIcon.setVisibility(GONE);
        } else if (menuIcon != -1) {
            titleView_menuIcon.setVisibility(VISIBLE);
            titleView_menuText.setVisibility(GONE);
            titleView_menuIcon.setImageResource(menuIcon);
        } else {
            titleView_menuIcon.setVisibility(VISIBLE);
            titleView_menuText.setVisibility(GONE);
        }

        titleView_line.setBackgroundColor(bottomLineColor);
        titleView_line.setHeight(((int) bottomLineHeight));
        ViewGroup.LayoutParams layoutParams = titleView_status.getLayoutParams();
        if (withStatusBarHeight) {
            layoutParams.height = StatusBarUtils.getStatusBarHeight(mContext);
            titleView_status.setLayoutParams(layoutParams);
            titleView_status.setBackgroundColor(statusBarColor);
        } else {
            layoutParams.height = 0;
            titleView_status.setLayoutParams(layoutParams);
        }


        titleView_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backClickListener == null) {
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).finish();
                    }
                } else {
                    backClickListener.onClickListener();
                }
            }
        });
        titleView_menuText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener != null) {
                    menuClickListener.onClickListener();
                }
            }
        });
        titleView_menuIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuIconClickListener != null) {
                    menuIconClickListener.onClickListener();
                }
            }
        });
    }

    private void getView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.titleview, this);
        titleView_container = view.findViewById(R.id.titleView_container);
        titleView_status = view.findViewById(R.id.titleView_status);
        titleView_content = view.findViewById(R.id.titleView_content);
        titleView_menuText = view.findViewById(R.id.titleView_menuText);
        titleView_line = view.findViewById(R.id.titleView_line);
        titleView_back = view.findViewById(R.id.titleView_back);
        titleView_menuIcon = view.findViewById(R.id.titleView_menuIcon);
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
}
