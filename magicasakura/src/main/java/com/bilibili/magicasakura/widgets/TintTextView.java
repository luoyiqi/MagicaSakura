package com.bilibili.magicasakura.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.TintManager;

/**
 * @author xyczero617@gmail.com
 * @time 15/9/14
 */
public class TintTextView extends TextView implements Tintable, AppCompatBackgroundHelper.BackgroundExtensible,
        AppCompatCompoundDrawableHelper.CompoundDrawableExtensible, com.bilibili.magicasakura.widgets.AppCompatTextHelper.TextExtensible {
    private com.bilibili.magicasakura.widgets.AppCompatTextHelper mTextHelper;
    private AppCompatBackgroundHelper mBackgroundHelper;
    private AppCompatCompoundDrawableHelper mCompoundDrawableHelper;

    public TintTextView(Context context) {
        this(context, null);
    }

    public TintTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public TintTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        TintManager tintManager = TintManager.get(getContext());

        mTextHelper = new AppCompatTextHelper(this, tintManager);
        mTextHelper.loadFromAttribute(attrs, defStyleAttr);

        mBackgroundHelper = new AppCompatBackgroundHelper(this, tintManager);
        mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);

        mCompoundDrawableHelper = new AppCompatCompoundDrawableHelper(this, tintManager);
        mCompoundDrawableHelper.loadFromAttribute(attrs, defStyleAttr);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (getBackground() != null) {
            invalidateDrawable(getBackground());
        }
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        if (mTextHelper != null) {
            mTextHelper.setTextColor();
        }
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
        if (mTextHelper != null) {
            mTextHelper.setTextColor();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void setTextAppearance(int resId) {
        super.setTextAppearance(resId);
        if (mTextHelper != null) {
            mTextHelper.setTextAppearanceForTextColor(resId);
        }
    }

    @Override
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        if (mTextHelper != null) {
            mTextHelper.setTextAppearanceForTextColor(resId);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundDrawableExternal(background);
        }
    }

    @Override
    public void setBackgroundResource(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundResId(resId);
        } else {
            super.setBackgroundResource(resId);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundColor(color);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        if (mCompoundDrawableHelper != null) {
            mCompoundDrawableHelper.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } else {
            super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (mCompoundDrawableHelper != null) {
            mCompoundDrawableHelper.setCompoundDrawablesWithIntrinsicBounds();
        }
    }

    @Override
    public void setBackgroundTintList(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, null);
        }
    }

    @Override
    public void setBackgroundTintList(int resId, PorterDuff.Mode mode) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, mode);
        }
    }

    @Override
    public void setCompoundDrawableTintList(int leftResId, int topResId, int rightResId, int bottomResId) {
        if (mCompoundDrawableHelper != null) {
            mCompoundDrawableHelper.setCompoundDrawablesTintList(leftResId, topResId, rightResId, bottomResId);
        }
    }

    @Override
    public void setTextColorById(@ColorRes int colorId) {
        if (mTextHelper != null) {
            mTextHelper.setTextColorById(colorId);
        }
    }


    @Override
    public void tint() {
        if (mTextHelper != null) {
            mTextHelper.tint();
        }
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
        if (mCompoundDrawableHelper != null) {
            mCompoundDrawableHelper.tint();
        }
    }
}
