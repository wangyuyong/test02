package com.wyy.viewgroupdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.print.PrinterId;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RefreshableView extends LinearLayout implements View.OnTouchListener {

    public static final int STATUS_PULL_TO_REFRESH = 0;

    public static final int STATUS_RELEASE_TO_REFRESH = 1;

    public static final int STATUS_REFRESHING = 2;

    public static final int STATUS_REFRESH_FINISHED = 3;

    public static final int SCROLL_SPEED = -20;

    public static final long ONE_MINUTE = 60 * 1000;

    public static final long ONE_HOUR = 60 * ONE_MINUTE;

    public static final long ONE_DAY = 24 * ONE_HOUR;

    public static final long ONE_MONTH = 30 * ONE_DAY;

    public static final long ONE_YEAR = 12 * ONE_DAY;

    public static final String UPDATED_AT = "updated_at";

    private PullToRefreshLinstener mListener;

    private SharedPreferences preferences;

    private View header;

    private ListView listView;

    private ProgressBar progressBar;

    private ImageView arrow;

    private TextView description;

    private TextView updataAt;

    private MarginLayoutParams headerLayoutParams;

    private long lastUpdateTime;

    private int mId = -1;

    private int hideHeaderHeight;

    private int currentStatus = STATUS_REFRESH_FINISHED;

    private int lastStatus = currentStatus;

    private float yDown;

    private int touchSlop;

    private boolean loadOnce;

    private boolean ableToPull;

    public RefreshableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh,null,true);
        progressBar = header.findViewById(R.id.progress_bar);
        arrow = header.findViewById(R.id.arrow);
        description = header.findViewById(R.id.description);
        updataAt = header.findViewById(R.id.updated_at);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        refreshUpdatedAtValue();
        setOrientation(VERTICAL);
        addView(header,0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce){
            hideHeaderHeight = -header.getHeight();
            headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();
            headerLayoutParams.topMargin = hideHeaderHeight;
            listView = (ListView)getChildAt(1);
            listView.setOnTouchListener(this);
            loadOnce = true;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
