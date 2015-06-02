package mouse.test;

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
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Test_Sqllite extends Activity {

	private Button btnCreateDatabase;
	private Button btnOpenCamera;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db = null;
	private Context c = null;
	private String BillID = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_sqllite_layout);
		c = this.getBaseContext();
		dbHelper = new DatabaseHelper(c, "WHH.db", null, 1);
		db = dbHelper.getWritableDatabase();
		btnCreateDatabase  = (Button)findViewById(R.id.btnCreateDatabase);
		btnCreateDatabase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String sql = "insert into WHHRecord values(?, ?, ?, ?, ?, ?)";
				BillID = getGuid();
				db.execSQL(sql, new Object[]{BillID, GetNowDate(), "猪", "30", "未报", "照片"});
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
			// bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
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
