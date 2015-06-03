package mouse.test;
//http://blog.sina.com.cn/s/blog_8cfbb99201012oqn.html
//http://www.cnblogs.com/Excellent/archive/2011/11/19/2254888.html
//http://www.cnblogs.com/snake-hand/p/3177865.html
//http://www.cnblogs.com/kgb250/archive/2012/08/28/sqlitedatabase.html
//http://www.cnblogs.com/over140/archive/2011/01/27/1945964.html
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import mouse.test.utils.DatabaseHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Test_Sqllite extends Activity {

	private Button btnCreateDatabase;
	private Button btnOpenCamera;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db = null;
	private Context c = null;
	private String BillID = "";
	private TextView tvRecordText;
	private Button btnShowImage;
	private ImageView ivShowImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_sqllite_layout);
		c = this.getBaseContext();
		
		tvRecordText = (TextView)findViewById(R.id.tvRecordText);
		ivShowImage = (ImageView)findViewById(R.id.ivShowImage);
		btnShowImage = (Button)findViewById(R.id.btnShowImage);
		btnShowImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cursor c = db.rawQuery("select * from WHHRecord where FFileType=?",new String[]{"照片"});
				if(c.moveToFirst()) {
					for(int i = 0; i < c.getCount(); i++) {
						String FGoodsName = c.getString(c.getColumnIndex("FGoodsName"));
						tvRecordText.setText(FGoodsName);
						c.moveToNext();
					}
				}
				
				int j = 0;
				c = db.rawQuery("select * from WHHRecordItem", new String[] {});
				if(c.moveToFirst()) {
					while(c.move(j)) {
						j++;
						String FPicName = c.getString(c.getColumnIndex("FPicName"));
						tvRecordText.setText(FPicName);
						byte[] imagequery=null;
						//将Blob数据转化为字节数组
						imagequery=c.getBlob(c.getColumnIndex("FPicData"));
						//将字节数组转化为位图
						Bitmap imagebitmap=BitmapFactory.decodeByteArray(imagequery, 0, imagequery.length);
						ivShowImage.setImageBitmap(imagebitmap);
						//ByteArrayInputStream stream = new ByteArrayInputStream(c.getBlob(c.getColumnIndex("FPicData")));   
						//ivShowImage.setImageDrawable(Drawable.createFromStream(stream, "img"));
						j++;
					}
				}
				Log.i("mouse.test", String.valueOf(c.getCount()));
			}
		});

		String sdcardPath = android.os.Environment
		.getExternalStorageDirectory().getAbsolutePath();
		
		dbHelper = new DatabaseHelper(c, sdcardPath + "/mouse.test/WHH.db", null, 1);
		db = dbHelper.getWritableDatabase();
		//下面这种方式,可以建立一个自定义位置的数据库文件.
	/*
		db = this.openOrCreateDatabase(sdcardPath+"/mouse.test/WHH.db",  MODE_PRIVATE, null);
		
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
*/		
		btnCreateDatabase  = (Button)findViewById(R.id.btnCreateDatabase);
		btnCreateDatabase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sql = "delete from WHHRecord";
				db.execSQL(sql);
				sql = "delete from WHHRecordItem";
				db.execSQL(sql);
				sql = "insert into WHHRecord values(?, ?, ?, ?, ?, ?)";
				BillID = getGuid();
				for(int i = 0; i < 10; i++) {
					db.execSQL(sql, new Object[]{BillID, GetNowDate(), "猪", "30", "未报", "照片"});
				}
			}
		});
		
		btnOpenCamera = (Button)findViewById(R.id.btnOpenCamera);
		btnOpenCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
			}			
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
			int size = bitmap.getWidth() * bitmap.getHeight() * 4;
			// 创建一个字节数组输出流,流的大小为size
			ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
			// 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			// 将字节数组输出流转化为字节数组byte[]
			byte[] imagedata1 = baos.toByteArray();

			// 将字节数组保存到数据库中
			ContentValues cv = new ContentValues();
			cv.put("FID", BillID);
			cv.put("FPicName", "第一张照片");
			cv.put("FPicData", imagedata1);
			db.insert("WHHRecordItem", null, cv);
			// 关闭字节数组输出流
			try {
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String getGuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	private String GetNowDate(){  
	    String temp_str="";  
	    Date dt = new Date();  
	    //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    temp_str=sdf.format(dt);  
	    return temp_str;  
	} 
}
