package yonky.fixpic;

import android.Manifest;
import android.app.ActionBar;
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

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY  |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Enum<String> Flag=new Enum<>(){
                "SHOUHUO","RC_COMMENT","GV_COMMENT1","GV_COMMENT2","GV_COMMENT3","MY_COMMENT"
        }
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();




        Button bt_shouhuo = (Button)findViewById(R.id.bt_shouhuo);
        Button bt_rc_comment = (Button)findViewById(R.id.bt_rc_comment);
        Button bt_gv_comment1 = (Button)findViewById(R.id.bt_gv_comment1);
        Button bt_gv_comment2 = (Button)findViewById(R.id.bt_gv_comment2);
        Button bt_gv_comment3 = (Button)findViewById(R.id.bt_gv_comment3);
        Button bt_mycomment=(Button)findViewById(R.id.bt_my_comment);
        checkPermission();

        bt_shouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int taoqi_x= 1920;
                int taoqi_y=525;
               ArrayList<String> texts=new ArrayList<String>();
                ArrayList<Integer>x = new ArrayList<Integer>();
                ArrayList<Integer>y = new ArrayList<Integer>();
                texts.add(getTime());
                x.add(taoqi_x);
                y.add(taoqi_y);

               Bitmap receivingBitmap =BitmapFactory.decodeResource(getResources(),R.drawable.receiving);
              paintSet();
                paint.setColor(Color.WHITE);
                texts.add("800");
                x.add((int)(receivingBitmap.getWidth() - paint.measureText(texts.get(0))) / 2);
                y.add(80);
             saveImage(receivingBitmap,texts);
//                paint.setFakeBoldText(false);
//                saveImage(receivingBitmap,texts,taoqi_x,taoqi_y);
                gcBitmap(receivingBitmap);

            }
        });
        bt_rc_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime();
                Bitmap commentBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.comment_js);
                paintSet();
                saveImage(commentBitmap,time);
                gcBitmap(commentBitmap);
            }
        });
        bt_gv_comment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime();
                Bitmap bitmap_Comment1 =BitmapFactory.decodeResource(getResources(),R.drawable.comment1);
                paintSet();
                saveImage( bitmap_Comment1,time);
                gcBitmap(bitmap_Comment1);
            }
        });
        bt_gv_comment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime();
                Bitmap bitmap_Comment2 =BitmapFactory.decodeResource(getResources(),R.drawable.comment2);
                paintSet();
                saveImage( bitmap_Comment2,time);
                gcBitmap(bitmap_Comment2);
            }
        });
        bt_gv_comment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime();
                Bitmap bitmap_Comment3 =BitmapFactory.decodeResource(getResources(),R.drawable.comment3);
                paintSet();
                saveImage( bitmap_Comment3,time);
                gcBitmap(bitmap_Comment3);
            }
        });

        bt_mycomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime();
                Bitmap bitmap_MyComment =BitmapFactory.decodeResource(getResources(),R.drawable.my_comment);
                paintSet();
                saveImage( bitmap_MyComment,time);
                gcBitmap(bitmap_MyComment);
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
    private  void saveImage(Bitmap beforeBitmap,String text){
        ArrayList<String> texts  = new ArrayList<>();
        ArrayList<Integer>x=new ArrayList<>();
        ArrayList<Integer>y = new ArrayList<>();
        texts.add(text);
        x.add((int)(beforeBitmap.getWidth() - paint.measureText(text)) / 2);
        y.add(80);
        saveImage(beforeBitmap,texts,x,y);
    }

    private  void saveImage(Bitmap beforeBitmap,ArrayList<String> texts,ArrayList<Integer> x,ArrayList<Integer> y){
//        imageView.setDrawingCacheEnabled(true);//开启catch，开启之后才能获取ImageView中的bitmap
        Bitmap bitmap = drawTextToBitmap(beforeBitmap,texts,x,y);
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "截图", "");
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        gcBitmap(bitmap);

//        imageView.setDrawingCacheEnabled(false);//关闭catch

    }
    public static  final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public void paintSet(){
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        paint.setFakeBoldText(true);
    }

    private  Bitmap drawTextToBitmap( Bitmap bitmap, ArrayList<String> texts,ArrayList<Integer> x,ArrayList<Integer> y) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.RGB_565;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        for(int i=0;i<texts.size();i++){
            canvas.drawText(texts.get(i), x.get(i), y.get(i), paint);
        }

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
