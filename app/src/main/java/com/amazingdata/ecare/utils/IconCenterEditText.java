package com.amazingdata.ecare.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * @author Xiong
 * @date 2019/1/24 - 11:32
 */

// 偷来的图片居中的EditTex控件
public class IconCenterEditText extends AppCompatEditText {

    public IconCenterEditText(Context context) {
        super(context);
    }

    public IconCenterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IconCenterEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getMeasuredWidth() - bodyWidth) / 2 - 2 * bodyWidth, 0);
            }
        }
        super.onDraw(canvas);
    }
}