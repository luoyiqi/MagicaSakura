package com.bilibili.magicasakura.widgets;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.bilibili.magicasakura.R;
import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.utils.TintInfo;
import com.bilibili.magicasakura.utils.TintManager;

/**
 * @author xyczero617@gmail.com
 * @time 16/2/4
 */
public class AppCompatProgressBarHelper extends AppCompatBaseHelper {
    private static final int ATTR[] = new int[]{
            R.attr.progressTint,
            R.attr.progressIndeterminateTint
    };

    private int mProgressTintResId;
    private int mIndeterminateTintResId;

    private TintInfo mProgressTintInfo;
    private TintInfo mIndeterminateTintInfo;

    public AppCompatProgressBarHelper(View view, TintManager tintManager) {
        super(view, tintManager);
    }

    @SuppressWarnings("ResourceType")
    @Override
    void loadFromAttribute(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mView.getContext().obtainStyledAttributes(attrs, ATTR, defStyleAttr, 0);
        if (array.hasValue(0)) {
            mProgressTintResId = array.getResourceId(0, 0);
            setSupportProgressTint(array.getColorStateList(0));
        }
        if (array.hasValue(1)) {
            mIndeterminateTintResId = array.getResourceId(1, 0);
            setSupportIndeterminateTint(array.getColorStateList(1));
        }
        array.recycle();
    }

    private void setSupportProgressTint(ColorStateList tint) {
        if (tint != null) {
            if (mProgressTintInfo == null) {
                mProgressTintInfo = new TintInfo();
            }
            mProgressTintInfo.mHasTintList = true;
            mProgressTintInfo.mTintList = ColorStateList.valueOf(ThemeUtils.getColor(mView.getContext(), tint.getDefaultColor()));
        }
        applySupportProgressTint();
    }

    private void setSupportIndeterminateTint(ColorStateList tint) {
        if (tint != null) {
            if (mIndeterminateTintInfo == null) {
                mIndeterminateTintInfo = new TintInfo();
            }
            mIndeterminateTintInfo.mHasTintList = true;
            mIndeterminateTintInfo.mTintList = ColorStateList.valueOf(ThemeUtils.getColor(mView.getContext(), tint.getDefaultColor()));
        }
        applySupportIndeterminateTint();
    }

    private void applySupportProgressTint() {
        if (mProgressTintInfo != null
                && (mProgressTintInfo.mHasTintList || mProgressTintInfo.mHasTintMode)) {
            final Drawable target = getTintTarget(android.R.id.progress, true);
            if (target != null) {
                TintManager.tintViewDrawable(mView, target, mProgressTintInfo);
                // The drawable (or one of its children) may not have been
                // stateful before applying the tint, so let's try again.
                if (target.isStateful()) {
                    target.setState(mView.getDrawableState());
                }
            }
        }
    }

    private void applySupportIndeterminateTint() {
        Drawable mIndeterminateDrawable = ((ProgressBar) mView).getIndeterminateDrawable();
        if (mIndeterminateDrawable != null && mIndeterminateTintInfo != null) {
            final TintInfo tintInfo = mIndeterminateTintInfo;
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                ((ProgressBar) mView).setIndeterminateDrawable(mIndeterminateDrawable = mIndeterminateDrawable.mutate());
                TintManager.tintViewDrawable(mView, mIndeterminateDrawable, mIndeterminateTintInfo);
                // The drawable (or one of its children) may not have been
                // stateful before applying the tint, so let's try again.
                if (mIndeterminateDrawable.isStateful()) {
                    mIndeterminateDrawable.setState(mView.getDrawableState());
                }
            }
        }
    }

    @Nullable
    private Drawable getTintTarget(int layerId, boolean shouldFallback) {
        Drawable layer = null;

        final Drawable d = ((ProgressBar) mView).getProgressDrawable();
        if (d != null) {
            ((ProgressBar) mView).setProgressDrawable(d.mutate());

            if (d instanceof LayerDrawable) {
                layer = ((LayerDrawable) d).findDrawableByLayerId(layerId);
            }

            if (shouldFallback && layer == null) {
                layer = d;
            }
        }

        return layer;
    }

    @Override
    public void tint() {
        if (mProgressTintResId != 0) {
            setSupportProgressTint(mView.getResources().getColorStateList(mProgressTintResId));
        }
        if (mIndeterminateTintResId != 0) {
            setSupportIndeterminateTint(mView.getResources().getColorStateList(mIndeterminateTintResId));
        }
    }
}
