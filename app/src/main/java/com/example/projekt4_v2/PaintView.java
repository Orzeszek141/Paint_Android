package com.example.projekt4_v2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PaintView extends View {

    private Path mPath = null;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    private int kolor;

    private float mX,mY;

    private ArrayList<Draw> paths = new ArrayList<>();


    public PaintView(Context context){
        super(context, null);
    }
    public PaintView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(4);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void CreateCanvas(DisplayMetrics displayMetrics){
        int hight = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        mBitmap = Bitmap.createBitmap(width,hight,Bitmap.Config.ARGB_8888);
        kolor = Color.BLACK;
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.save();
        mCanvas.drawARGB(255,255,255,255);
        for (Draw draw: paths){
            mPaint.setColor(draw.color);;
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(7);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mCanvas.drawPath((android.graphics.Path) draw.path, mPaint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();
    }

    private void touchStart (float x, float y){
        mPath = new Path();

        Draw draw = new Draw(kolor,mPath);
        paths.add(draw);
        mPath.reset();
        mPath.addCircle(x,y,10, Path.Direction.CCW);
        mPath.moveTo(x,y);
        mX=x;
        mY=y;

    }

    private void touchMove (float x, float y){
        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);

        if(dx >= 4 || dy>= 4){
            mPath.quadTo(mX,mY,(x+mX)/2,(y+mY)/2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp (){
        mPath.lineTo(mX,mY);
        mPath.addCircle(mX,mY,10, Path.Direction.CCW);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x =event.getX();
        float y =event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }

    public void clear(){
        paths.clear();
        invalidate();
    }

    public void setKolor(int kolor) {
        this.kolor = kolor;
    }

    public void save(){
        int count =0;
        File sdDirectory = Environment.getExternalStorageDirectory();
        File subDirectory = new File(sdDirectory.toString()+"/Pictures/Paint");
        if(subDirectory.exists()) {
            File[] existing = subDirectory.listFiles();
                for (File file : existing) {
                    if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
                        count++;
                    }
                }
        }
        else{
            subDirectory.mkdir();
        }
        File image = new File(subDirectory,"/rysunek" + (count + 1)+".png");
        FileOutputStream fileOutputStream;
        try{
            fileOutputStream = new FileOutputStream(image);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(getContext(), "Zapisaono w "+ subDirectory, Toast.LENGTH_LONG).show();
        }
        catch (FileNotFoundException e){

        }
        catch (IOException e)
        {

        }
    }

}
