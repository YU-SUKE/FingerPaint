package sample.application.fingerpaint;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class FingerPaintActivity extends Activity implements OnTouchListener{

	public Canvas canvas;
	public Paint paint;
	public Path path;
	public Bitmap bitmap;
	public Float x1;
	public Float y1;
	public Integer w;
	public Integer h;
	
	public boolean onTouch (View v, MotionEvent event){
		
		
		
		return Boolean.valueOf(true);
		//return Boolean2.TRUE;
	}
}