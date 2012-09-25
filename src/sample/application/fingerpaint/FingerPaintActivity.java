package sample.application.fingerpaint;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import android.os.Environment;
import android.content.SharedPreferences;
import android.graphics.Bitmap.CompressFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.media.MediaScannerConnection;
import android.net.Uri;

public class FingerPaintActivity extends Activity implements OnTouchListener{

	public Canvas canvas;
	public Paint paint;
	public Path path;
	public Bitmap bitmap;
	public Float x1;
	public Float y1;
	public Integer w;
	public Integer h;
	
	protected void onCreate (Bundle savedInstanceState){
		ImageView iv = (ImageView) this.findViewById(R.id.imageView1);
		Display disp = ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		w = disp.getWidth();
		h = disp.getHeight();
		bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		paint = new Paint();
		path = new Path();
		canvas = new Canvas(bitmap);
		
		paint.setStrokeWidth(5);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		canvas.drawColor(Color.WHITE);
		iv.setImageBitmap(bitmap);
		iv.setOnTouchListener(this);
	}
	
	public boolean onTouch (View v, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction()){
		case MotionEvent.ACTION_DOWN:
			path.reset();
			path.moveTo(x, y);
			x1 = x;
			y1 = y;
			break;
		case MotionEvent.ACTION_MOVE:
			path.quadTo(x1, y1, x, y);
			x1 = x;
			y1 = y;
			canvas.drawPath(path, paint);
			path.reset();
			path.moveTo(x, y);
			break;
		case MotionEvent.ACTION_UP:
			if(x==x1&&y==y1)y1 = y1+1;
			path.quadTo(x1, y1, x, y);
			canvas.drawPath(path, paint);
			path.reset();
			break;
		}
		ImageView iv = (ImageView)this.findViewById(R.id.imageView1);
		iv.setImageBitmap(bitmap);
		return true;
	}

	public boolean onCreateOptinMenu(Menu menu){
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.menu_save:
				save();
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	void save(){
		SharedPreferences prefs = getSharedPreferences(
				"FingerPaintPreferences",MODE_PRIVATE);
		int imageNumber = prefs.getInt("imageNumber", 1);
		File file = null;
		
		if(externalMediaChecker()){
			DecimalFormat form = new DecimalFormat("0000");
			String path = Environment.getExternalStorageDirectory()+"/mypaint";
			File outDir = new File(path);
			if(!outDir.exists())outDir.mkdir();
			
			do{
				file = new File(path+"img"+form.format(imageNumber)+".png");
				imageNumber++;
			}while(file.exists());
			if(writeImage(file)){
				scanMedia(file.getPath());
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt("imageNumber", imageNumber);
				editor.commit();
			}
		}
	}
	
	boolean writeImage(File file){
		try{
			FileOutputStream fo = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, fo);
			fo.flush();
			fo.close();
		}catch(Exception e){
			System.out.println(e.getLocalizedMessage());
			return false;
			
		}
		return true;
	}
	
	boolean externalMediaChecker(){
		boolean result = false;
		String status = Environment.getExternalStorageState();
		if(status.equals(Environment.MEDIA_MOUNTED))result = true;
		return result;
	}
	
	MediaScannerConnection mc;
	void scanMedia(final String fp){
		mc = new MediaScannerConnection(this,
				new MediaScannerConnection.MediaScannerConnectionClient(){
			public void onScanCompleted(String path, Uri uri){
				disconnect();
			}
			public void onMediaScannerConnected(){
				scanFile(fp);
			}
		});
		mc.connect();
	}
	
	void scanFile(String fp){mc.scanFile(fp, "image/png");}
	void disconnect(){mc.disconnect();}
	
}