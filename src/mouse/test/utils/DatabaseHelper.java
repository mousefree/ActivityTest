package mouse.test.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

//这个类可以建立一个在私有空里面固定位置的数据库文件.受系统root权限保护,默认外部操作是看不到这个数据库文件的.
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
		String create_whhrecord = "CREATE TABLE if not exists [WHHRecord] (";
		create_whhrecord += "[FID] VARCHAR(36),";
		create_whhrecord += "[FDATE] DATE,";
		create_whhrecord += "[FGoodsName] VARCHAR(16),";
		create_whhrecord += "[FQuantity] DECIMAL(15, 2),";
		create_whhrecord += "[FState] VARCHAR(8),";
		create_whhrecord += "[FFileType] VARCHAR(16));";
		db.execSQL(create_whhrecord);
		
		String create_whhrecorditem = "CREATE TABLE if not exists [WHHRecordItem] (";
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
