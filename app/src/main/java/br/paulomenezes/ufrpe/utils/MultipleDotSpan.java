package br.paulomenezes.ufrpe.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

/**
 * Created by paulo on 30/10/2016.
 */

public class MultipleDotSpan implements LineBackgroundSpan {

    public static final float DEFAULT_RADIUS = 3;

    private final float radius;
    private final int[] color;

    public MultipleDotSpan(float radius, int[] color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int oldColor = paint.getColor();
        int pair = color.length % 2;
        float middle = color.length / 2;
        float width = radius * 2 + 4;
        for (int i = 0; i < color.length; i++) {
            if (color[i] != 0) {
                paint.setColor(color[i]);
            }
            float x = ((left + right) / 2 - width * middle) + (width * i) + (pair == 0 ? width / 2 : 0);
            canvas.drawCircle(x, bottom + radius, radius, paint);
        }
        paint.setColor(oldColor);
    }
}