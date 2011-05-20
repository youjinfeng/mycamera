package com.sony.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class MyCamera extends Activity {
	
	 private Camera camera;
	 private boolean isPreview = false;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(320,480);
        surfaceView.getHolder().addCallback(new SurfaceViewCallback());
    }
   @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
	   if (camera != null) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_CENTER :
			 	camera.takePicture(null, null, new TakePictureCallback());
			break;

		default:
			break;
		}
		// on master
	}
	   //on new branch
		return super.onKeyDown(keyCode, event);
		//on master-test branch
	}
private final class SurfaceViewCallback implements Callback{

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		camera = Camera.open();
		Camera.Parameters parameters = camera.getParameters();
		WindowManager wManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		Display display = wManager.getDefaultDisplay();
		parameters.setPreviewSize(display.getWidth(), display.getHeight());
		
		parameters.setPreviewFrameRate(5);
		parameters.setPictureFormat(PixelFormat.JPEG);
		 /* 照片质量 */  
        parameters.set("jpeg-quality", 85);   
        
		camera.setParameters(parameters);
		isPreview = true;
	
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (isPreview) {
			camera.stopPreview();
			isPreview = false;
		}
		// release camera
		camera.release();
	}
	   
   }
   
   private final class TakePictureCallback implements PictureCallback{

	public void onPictureTaken(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);   
        
        /* 照片将被保存到  SD 卡跟目录下，文件名为系统时间，后缀名为".jpg" */  
        File file = new File(Environment.getExternalStorageState(), System.currentTimeMillis() + ".jpg");   
        try {   
            FileOutputStream fos = new FileOutputStream(file);   
               
            /* 位图格式为JPEG  
             * 参数二位 0-100 的数值，100为最大值，表示无损压缩   
             * 参数三传入一个输出流对象，将图片数据输出到流中   
             */  
            bitmap.compress(CompressFormat.JPEG, 100, fos);   
            fos.close();   
               
            /* 拍完照后回到预览状态，继续取景 */  
            camera.startPreview();   
        } catch (IOException e) {   
            e.printStackTrace();   
            //Modify on new branch (1)

        }   
// Modify on master branch (1)
        //Modify on master branch (2)
      //Modify on master branch (3)
        //Modify on master branch (4)
	}
	   //This is my first time on master-test branch.
	 //This is my second time on master-test branch (2).
	
   }
}