package com.example.william.my.core.widget.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 能够自动修正的TextView，解决android自带TextView右侧文字参差不齐的问题
 */
public class AlignTextView extends AppCompatTextView {

    public AlignTextView(Context context) {
        this(context, null);
    }

    public AlignTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlignTextView(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onDraw(Canvas canvas) {
        CharSequence content = getText();
        if (!(content instanceof String)) {
            super.onDraw(canvas);
            return;
        }
        String text = (String) content;
        Layout layout = getLayout();

        for (int i = 0; i < layout.getLineCount(); ++i) {
            int lineBaseline = layout.getLineBaseline(i) + getPaddingTop();
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);

            if (i == layout.getLineCount() - 1) {//最后一行
                canvas.drawText(text.substring(lineStart), getPaddingLeft(), lineBaseline, getPaint());
                break;
            } else {//中间行
                String line = text.substring(lineStart, lineEnd);
                float width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint());
                this.drawScaledText(canvas, line, lineBaseline, width);
            }
        }

    }

    private void drawScaledText(Canvas canvas, String line, float baseLineY, float lineWidth) {
        if (line.length() < 1) {
            return;
        }
        float x = getPaddingLeft();
        boolean forceNextLine = line.charAt(line.length() - 1) == 10;
        int length = line.length() - 1;
        if (forceNextLine || length == 0) {
            canvas.drawText(line, x, baseLineY, getPaint());
            return;
        }

        float d = (getMeasuredWidth() - lineWidth - getPaddingLeft() - getPaddingRight()) / length;

        for (int i = 0; i < line.length(); ++i) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, this.getPaint());
            canvas.drawText(c, x, baseLineY, this.getPaint());
            x += cw + d;
        }
    }
}