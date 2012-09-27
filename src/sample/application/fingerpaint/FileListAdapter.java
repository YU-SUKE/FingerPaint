package sample.application.fingerpaint;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileListAdapter extends ArrayAdapter<Object>{
	
	static final int text1 = android.R.id.text1;
	static final int text2 = android.R.id.text2;
	static final int icon = android.R.id.icon;
	File[] fc;
	LayoutInflater mInflater;
	
	public FileListAdapter(Context context, Object[] objects){
		super(context, text1, objects);
		fc = (File[])objects;
		mInflater = (LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.list_item_with_icon, null);
				}
			TextView fName = (TextView) convertView.findViewById(text1);
			TextView fTime = (TextView) convertView.findViewById(text2);
			ImageView fIcon = (ImageView) convertView.findViewById(icon);
			
			fName.setText(fc[position].getName());
			fTime.setText(DateFormat.getDateTimeInstance().format(
					new Date(fc[position].lastModified())));
			if(fc[position].isDirectory()){
				fIcon.setImageResource(R.drawable.folder);
			}else{
				fIcon.setImageResource(R.drawable.text);
			}
		return convertView;
	}
	
}
