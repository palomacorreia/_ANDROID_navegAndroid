package syssatelite.navegandroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class CircleView extends View {
    private int circleColor;  // cor do círculo
    private int textColor;    // cor do texto
    private String text;      // texto
    private Rect rectangle;
    private Paint paint;

    // construtor
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.custom_attributes, 0, 0);
        try {
            //get the text and colors specified using the names in attrs.xml
            text = a.getString(R.styleable.custom_attributes_circleView_text);
            textColor = a.getInteger(R.styleable.custom_attributes_circleView_textColor, 0);
            circleColor = a.getInteger(R.styleable.custom_attributes_circleView_circleColor, 0);

        } finally {
            a.recycle();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint=new Paint();


        super.onDraw(canvas);

        int x = getWidth();
        int y = getHeight();
        int radius;
        radius = 100;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));
        canvas.drawCircle(x / 2, y / 2, radius, paint);
        canvas.drawCircle(x / 2, y / 2, radius*2, paint);
        canvas.drawCircle(x / 2, y / 2, radius/6, paint);

    }

    public void SatInfo(Satelite arraySat)
    {
        double Xc, Yc, Xs, Ys;
        int radius = 8;
        int W = getWidth();
        int H = getHeight();
        Paint paint=new Paint();
        Canvas canvas =  new Canvas();

        Xs = radius * Math.cos(arraySat.getELEV()* Math.sin(arraySat.getAZIM()));
        Ys = radius * Math.cos(arraySat.getELEV()) * Math.cos(arraySat.getAZIM());

        Xc = Xs + W/2;
        Yc = -Ys + H/2;

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);

        canvas.drawPaint(paint);
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#5ccd5c"));

        canvas.drawCircle((float) Xc, (float)Yc, radius, paint);
        System.out.println("DESENHEI O SATELITE MEU AMOR!");
    }

}
