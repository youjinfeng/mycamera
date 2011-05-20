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
		 /* ��Ƭ���� */  
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
        
        /* ��Ƭ�������浽  SD ����Ŀ¼�£��ļ���Ϊϵͳʱ�䣬��׺��Ϊ".jpg" */  
        File file = new File(Environment.getExternalStorageState(), System.currentTimeMillis() + ".jpg");   
        try {   
            FileOutputStream fos = new FileOutputStream(file);   
               
            /* λͼ��ʽΪJPEG  
             * ������λ 0-100 ����ֵ��100Ϊ���ֵ����ʾ����ѹ��   
             * ����������һ����������󣬽�ͼƬ�������������   
             */  
            bitmap.compress(CompressFormat.JPEG, 100, fos);   
            fos.close();   
               
            /* �����պ�ص�Ԥ��״̬������ȡ�� */  
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