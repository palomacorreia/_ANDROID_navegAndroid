package syssatelite.navegandroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Rectangle extends View {

    private int circleColor;  // cor do círculo
    private int textColor;    // cor do texto
    private String text;      // texto
    private Rect rectangle;
    private Paint paint;
    // construtor

    public Rectangle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.custom_attributes, 0, 0);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = new Paint();

        paint.setColor(Color.parseColor("#00bfff"));
        paint.setStrokeWidth(3);
        canvas.drawRect(10, 20, 300, 500, paint);

    }


}
