package sample.application.memopad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MemoDBHelper extends SQLiteOpenHelper {

	public static String name = "memos.db";
	public static CursorFactory factory = null;
	public static Integer version  = 1;
	
	//public string name2 = "memos.db";
	
	public MemoDBHelper(Context context, String name, CursorFactory factory,
			Integer version) {
		super(context, name, factory, version);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public MemoDBHelper(Context context){
			
			super(context,name,factory,version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table memoDB ("
				+ android.provider.BaseColumns._ID
				+" integer primary key autoincrement, title text, memo TEXT);";
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
