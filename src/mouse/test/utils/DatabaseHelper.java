package mouse.test.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//建立无害化记录主表.
		String create_whhrecord = "CREATE TABLE [WHHRecord] (";
		create_whhrecord += "[FID] VARCHAR(36),";
		create_whhrecord += "[FDATE] DATE,";
		create_whhrecord += "[FGoodsName] VARCHAR(16),";
		create_whhrecord += "[FQuantity] DECIMAL(15, 2),";
		create_whhrecord += "[FState] VARCHAR(8),";
		create_whhrecord += "[FFileType] VARCHAR(16));";
		db.execSQL(create_whhrecord);
		
		String create_whhrecorditem = "CREATE TABLE [WHHRecordItem] (";
				  create_whhrecorditem += "[FID] VARCHAR(36),"; 
				  create_whhrecorditem += "[FPicName] VARCHAR(32),"; 
				  create_whhrecorditem += "[FPicData] BLOB);";
		db.execSQL(create_whhrecorditem);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
