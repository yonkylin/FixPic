package yonky.fixpic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.format.DateFormat;
import android.util.AttributeSet;



import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/28.
 */

public class MyView extends AppCompatImageView {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    String text = DateFormat.format("HH:mm",new Date().getTime()+1000*60).toString();
    {
        paint.setTextSize(28);
        paint.setFakeBoldText(true);
        paint.setColor(Color.parseColor("#E0E0E0"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float textwidth = paint.measureText(text);
        int width = getMeasuredWidth();
        canvas.drawText(text,(width-textwidth)/2,35,paint);

        canvas.drawText("800",965,285,paint);
    }
}
