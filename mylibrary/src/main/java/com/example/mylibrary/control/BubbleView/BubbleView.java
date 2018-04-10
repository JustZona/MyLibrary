package com.example.mylibrary.control.BubbleView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.example.mylibrary.ViewUtil.AdaptationUtil;


/**
 * 用于控件角标提示.
 * @author Jeff Gilfelt
 */
public class BubbleView extends TextView {

    public static final int POSITION_TOP_LEFT = 1;
    public static final int POSITION_TOP_RIGHT = 2;
    public static final int POSITION_BOTTOM_LEFT = 3;
    public static final int POSITION_BOTTOM_RIGHT = 4;
    public static final int POSITION_CENTER = 5;

    private static final int DEFAULT_MARGIN_DIP = 5;
    private static final int DEFAULT_LR_PADDING_DIP = 5;
    private static final int DEFAULT_CORNER_RADIUS_DIP = 8;
    private static final int DEFAULT_POSITION = POSITION_TOP_RIGHT;
    private static final int DEFAULT_BADGE_COLOR = Color.parseColor("#CCFF0000"); //Color.RED;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private static Animation fadeIn;
    private static Animation fadeOut;

    private Context context;
    private View target;

    private int badgePosition;
    private int badgeMarginH;
    private int badgeMarginV;
    private int badgeColor;

    private boolean isShown;

    private int badgeBg = R.drawable.bubble_0;

    private int targetTabIndex;


    public BubbleView(Context context) {
        this(context, (AttributeSet) null, android.R.attr.textViewStyle);
    }

    public BubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    /**
     * Constructor -
     *
     * create a new BadgeView instance attached to a target {@link View}.
     *
     * @param context context for this view.
     * @param target the View to attach the badge to.
     */
    public BubbleView(Context context, View target) {
        this(context, null, android.R.attr.textViewStyle, target, 0);
    }

    /**
     * Constructor -
     *
     * create a new BadgeView instance attached to a target {@link TabWidget}
     * tab at a given index.
     *
     * @param context context for this view.
     * @param target the TabWidget to attach the badge to.
     * @param index the position of the tab within the target.
     */
    public BubbleView(Context context, TabWidget target, int index) {
        this(context, null, android.R.attr.textViewStyle, target, index);
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, null, 0);
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyle, View target, int tabIndex) {
        super(context, attrs, defStyle);
        init(context, target, tabIndex);
    }

    private void init(Context context, View target, int tabIndex) {

        this.context = context;
        this.target = target;
        this.targetTabIndex = tabIndex;
        setGravity(Gravity.CENTER);
        setElevation(target.getElevation()+target.getTranslationZ()+10);
        badgePosition = DEFAULT_POSITION;
        badgeMarginH = AdaptationUtil.dip2px(context,0);
        badgeMarginV = badgeMarginH;
        badgeColor = DEFAULT_BADGE_COLOR;

        setTypeface(Typeface.DEFAULT_BOLD);
        int paddingPixels = AdaptationUtil.dip2px(context,DEFAULT_LR_PADDING_DIP);
        setPadding(paddingPixels, 0, paddingPixels, 0);
        setTextColor(DEFAULT_TEXT_COLOR);

        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(200);

        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(200);

        isShown = false;

        applyTo(this.target);

    }

    private void applyTo(View target) {

        LayoutParams lp = target.getLayoutParams();
        ViewParent parent = target.getParent();
        FrameLayout container = new FrameLayout(context);
        container.setMeasureAllChildren(true);
        if (target instanceof TabWidget) {

            // set target to the relevant tab child container
            target = ((TabWidget) target).getChildTabViewAt(targetTabIndex);
            this.target = target;

            ((ViewGroup) target).addView(container,
                    new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

            this.setVisibility(View.GONE);
            container.addView(this);

        } else {
            ViewGroup group = (ViewGroup) parent;
            int index = group.indexOfChild(target);
            group.removeView(target);
            group.addView(container, index, lp);
            container.addView(target);
            container.addView(this);
            this.setVisibility(View.GONE);
            group.invalidate();

        }

    }

    /**
     * Make the badge visible in the UI.
     *
     */
    public void show() {
        show(false, null);
    }

    /**
     * Make the badge visible in the UI.
     *
     * @param animate flag to apply the default fade-in animation.
     */
    public void show(boolean animate) {
        show(animate, fadeIn);
    }

    /**
     * Make the badge visible in the UI.
     *
     * @param anim Animation to apply to the view when made visible.
     */
    public void show(Animation anim) {
        show(true, anim);
    }

    /**
     * Make the badge non-visible in the UI.
     *
     */
    public void hide() {
        hide(false, null);
    }

    /**
     * Make the badge non-visible in the UI.
     *
     * @param animate flag to apply the default fade-out animation.
     */
    public void hide(boolean animate) {
        hide(animate, fadeOut);
    }

    /**
     * Make the badge non-visible in the UI.
     *
     * @param anim Animation to apply to the view when made non-visible.
     */
    public void hide(Animation anim) {
        hide(true, anim);
    }

    /**
     * Toggle the badge visibility in the UI.
     *
     */
    public void toggle() {
        toggle(false, null, null);
    }

    /**
     * Toggle the badge visibility in the UI.
     *
     * @param animate flag to apply the default fade-in/out animation.
     */
    public void toggle(boolean animate) {
        toggle(animate, fadeIn, fadeOut);
    }

    /**
     * Toggle the badge visibility in the UI.
     *
     * @param animIn Animation to apply to the view when made visible.
     * @param animOut Animation to apply to the view when made non-visible.
     */
    public void toggle(Animation animIn, Animation animOut) {
        toggle(true, animIn, animOut);
    }

    private void show(boolean animate, Animation anim) {
        setBackgroundResource(badgeBg);
        applyLayoutParams();

        if (animate) {
            this.startAnimation(anim);
        }
        this.setVisibility(View.VISIBLE);
        isShown = true;
    }

    private void setBg(int bg){
        this.badgeBg = bg;
    }

    private void hide(boolean animate, Animation anim) {
        this.setVisibility(View.GONE);
        if (animate) {
            this.startAnimation(anim);
        }
        isShown = false;
    }

    private void toggle(boolean animate, Animation animIn, Animation animOut) {
        if (isShown) {
            hide(animate && (animOut != null), animOut);
        } else {
            show(animate && (animIn != null), animIn);
        }
    }

    private void applyLayoutParams() {

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        switch (badgePosition) {
            case POSITION_TOP_LEFT:
                lp.gravity = Gravity.LEFT | Gravity.TOP;
                lp.setMargins(badgeMarginH, badgeMarginV, 0, 0);
                break;
            case POSITION_TOP_RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.TOP;
                lp.setMargins(0, badgeMarginV, badgeMarginH, 0);
                break;
            case POSITION_BOTTOM_LEFT:
                lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
                lp.setMargins(badgeMarginH, 0, 0, badgeMarginV);
                break;
            case POSITION_BOTTOM_RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                lp.setMargins(0, 0, badgeMarginH, badgeMarginV);
                break;
            case POSITION_CENTER:
                lp.gravity = Gravity.CENTER;
                lp.setMargins(0, 0, 0, 0);
                break;
            default:
                break;
        }

        setLayoutParams(lp);
    }

    /**
     * Returns the target View this badge has been attached to.
     *
     */
    public View getTarget() {
        return target;
    }

    /**
     * Is this badge currently visible in the UI?
     *
     */
    @Override
    public boolean isShown() {
        return isShown;
    }

    /**
     * Returns the positioning of this badge.
     *
     * one of POSITION_TOP_LEFT, POSITION_TOP_RIGHT, POSITION_BOTTOM_LEFT, POSITION_BOTTOM_RIGHT, POSTION_CENTER.
     *
     */
    public int getBadgePosition() {
        return badgePosition;
    }

    /**
     * Set the positioning of this badge.
     * 设置出现位置.
     * @param layoutPosition one of POSITION_TOP_LEFT, POSITION_TOP_RIGHT, POSITION_BOTTOM_LEFT, POSITION_BOTTOM_RIGHT, POSTION_CENTER.
     *
     */
    public void setBadgePosition(int layoutPosition) {
        this.badgePosition = layoutPosition;
    }

    /**
     * Returns the horizontal margin from the target View that is applied to this badge.
     *
     */
    public int getHorizontalBadgeMargin() {
        return badgeMarginH;
    }

    /**
     * Returns the vertical margin from the target View that is applied to this badge.
     *
     */
    public int getVerticalBadgeMargin() {
        return badgeMarginV;
    }

    /**
     * Set the horizontal/vertical margin from the target View that is applied to this badge.
     *
     * @param badgeMargin the margin in pixels.
     */
    public void setBadgeMargin(int badgeMargin) {
        this.badgeMarginH = badgeMargin;
        this.badgeMarginV = badgeMargin;
    }

    /**
     * Set the horizontal/vertical margin from the target View that is applied to this badge.
     *
     * @param horizontal margin in pixels.
     * @param vertical margin in pixels.
     */
    public void setBadgeMargin(int horizontal, int vertical) {
        this.badgeMarginH = horizontal;
        this.badgeMarginV = vertical;
    }

    /**
     * Returns the color value of the badge background.
     *
     */
    public int getBadgeBackgroundColor() {
        return badgeColor;
    }

    /**
     * Set the color value of the badge background.
     *
     * @param badgeColor the badge background color.
     */
    public void setBadgeBackgroundColor(int badgeColor) {
        this.badgeColor = badgeColor;
    }

}
