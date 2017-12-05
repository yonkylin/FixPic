package yonky.fixpic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
//        final Bitmap commentBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.comment_js);

//        final Bitmap receivingBitmap =BitmapFactory.decodeResource(getResources(),R.drawable.receiving);
//        final Bitmap bitmap_Comment1 =BitmapFactory.decodeResource(getResources(),R.drawable.comment1);
//        final Bitmap bitmap_Comment2 =BitmapFactory.decodeResource(getResources(),R.drawable.comment2);
//        final Bitmap bitmap_Comment3 =BitmapFactory.decodeResource(getResources(),R.drawable.comment3);


        final ImageView image = (ImageView)findViewById(R.id.image_view);
        Button bt_shouhuo = (Button)findViewById(R.id.bt_shouhuo);
        Button bt_rc_comment = (Button)findViewById(R.id.bt_rc_comment);
        Button bt_gv_comment1 = (Button)findViewById(R.id.bt_gv_comment1);
        Button bt_gv_comment2 = (Button)findViewById(R.id.bt_gv_comment2);
        Button bt_gv_comment3 = (Button)findViewById(R.id.bt_gv_comment3);
        checkPermission();

        bt_shouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String time = getTime();
               Bitmap receivingBitmap =BitmapFactory.decodeResource(getResources(),R.drawable.receiving);
             saveImage(receivingBitmap,time,Color.WHITE);
                gcBitmap(receivingBitmap);

            }
        });
        bt_rc_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime();
                Bitmap commentBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.comment_js);
                saveImage(commentBitmap,time,Color.BLACK);
                gcBitmap(commentBitmap);
            }
        });
        bt_gv_comment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime();
                Bitmap bitmap_Comment1 =BitmapFactory.decodeResource(getResources(),R.drawable.comment1);
                saveImage( bitmap_Comment1,time,Color.BLACK);
                gcBitmap(bitmap_Comment1);
            }
        });
        bt_gv_comment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime();
                Bitmap bitmap_Comment2 =BitmapFactory.decodeResource(getResources(),R.drawable.comment2);
                saveImage( bitmap_Comment2,time,Color.BLACK);
                gcBitmap(bitmap_Comment2);
            }
        });
        bt_gv_comment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime();
                Bitmap bitmap_Comment3 =BitmapFactory.decodeResource(getResources(),R.drawable.comment3);
                saveImage( bitmap_Comment3,time,Color.BLACK);
                gcBitmap(bitmap_Comment3);
            }
        });

    }
    public void checkPermission(){
        if(Build.VERSION.SDK_INT>=23) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        }
    }
    private  void saveImage(Bitmap beforeBitmap,String time,int color){
//        imageView.setDrawingCacheEnabled(true);//开启catch，开启之后才能获取ImageView中的bitmap
        Bitmap bitmap = drawTextToBitmap(this,beforeBitmap,time, color);
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "截图", "");
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
       gcBitmap(bitmap);

//        imageView.setDrawingCacheEnabled(false);//关闭catch

    }


    private  Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFakeBoldText(true);
        paint.setTextSize(60);
        paint.setColor(color);
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, (int)(bitmap.getWidth() - paint.measureText(text)) / 2, 80, paint);
        return bitmap;
    }

    public String getTime(){
        String time = DateFormat.format("HH:mm",new Date().getTime()+1000*60).toString();
        return time;
    }
    public void gcBitmap(Bitmap bitmap){
        if(!bitmap.isRecycled()){
            bitmap.recycle();
            System.gc();
        }
    }
}
