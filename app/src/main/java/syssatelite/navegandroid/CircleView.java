package syssatelite.navegandroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.GpsSatellite;
import android.util.AttributeSet;
import android.util.Log;
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
    Iterable<GpsSatellite> satellites = null;
    public float Xcc, Ycc;


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


    public void setSats(Iterable<GpsSatellite> sats) {
        this.satellites = sats;
    }


    private void SatInfo(Canvas canvas ,GpsSatellite arraySat)
    {
        float Xc, Yc, Xs, Ys;
        int W = this.getWidth();
        int H = this.getHeight();
        int s = (W < H) ? W : H;
        float radius = s/4;
        Paint paint = new Paint();

        Log.d("SATELITES", arraySat.getAzimuth()+"");

         Xc = (float) (radius * Math.cos((arraySat.getElevation()) * Math.sin(arraySat.getAzimuth())));
         Yc = (float) (radius * Math.cos((arraySat.getElevation()) * Math.cos(arraySat.getAzimuth())));

         Xc = Xc + W / 2;
         Yc = -Yc + H / 2;

         paint.setStyle(Paint.Style.FILL);

         System.out.println("PRN"+ arraySat.getPrn());
        if (arraySat.getPrn() >= 1 && arraySat.getPrn() <= 32) { //GPS
            paint.setColor(Color.parseColor("#5ccd5c"));
            canvas.drawCircle(Xcc, Ycc, 20, paint);
        } if (arraySat.getPrn() >= 65 && arraySat.getPrn() <= 96) { //GLONASS
            paint.setColor(Color.parseColor("#DAA520"));
            canvas.drawRect(Xcc-20, Ycc-20, Xcc+20, Ycc+20, paint);
        } else if (arraySat.getPrn() >= 193 && arraySat.getPrn() <= 200) { //QZSS (Japão)
            paint.setColor(Color.parseColor("#BC8F8F"));
            canvas.drawCircle(Xcc, Ycc, 20, paint);
        } else if (arraySat.getPrn()>= 201 && arraySat.getPrn() <= 235) { //BEIDOU
            paint.setColor(Color.parseColor("#FF00FF"));
            canvas.drawCircle(Xcc, Ycc, 20, paint);
        } else if(arraySat.getPrn()>=301 && arraySat.getPrn() <= 336) { //GALILEO
            paint.setColor(Color.parseColor("#FF0000"));
            canvas.drawCircle(Xcc, Ycc, 20, paint);
        }
         //paint.setColor(Color.parseColor("#5ccd5c"));
         Xcc = Xc;
         Ycc = Yc;
         System.out.println("Xcc" + Xcc);
         System.out.println("Ycc" + Ycc);

         canvas.drawCircle(Xcc, Ycc, 20, paint);


    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint=new Paint();


        super.onDraw(canvas);

        int x = getWidth();
        int y = getHeight();
        int radius = (x < y) ? x/2 : y/2;

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        // Use Color
        // .parseColor to define HTML colors
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawCircle(x / 2, y / 2, radius, paint);
        canvas.drawCircle(x / 2, y / 2, radius-100, paint);
        canvas.drawCircle(x / 2, y / 2, radius-200, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setColor(Color.parseColor("#5ccd5c"));
        if(satellites != null) {
            for (GpsSatellite sat: satellites) {
                SatInfo(canvas, sat);
            }
        }
    }


}
