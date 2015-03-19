package mouse.test;

import mouse.test.asynctask.LoginAsyncTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Test_Main extends Activity {
/*
 * 目前想到的需要测试的内容，
 * 1，还需要增加在自定义adapter中传递参数出来
 * 2、增加异步调用数据的使用及参数传递
 * 3、可以考虑把dj中，关于http操作的类，移进来。
 */
	private Button btnTestList;
	private Button btnTestSplash;
	private Button btnTestAsync;
	private Button btnTestCustomLayout;
	private TextView tvShow;
	private ProgressBar pbShow;
	private Button btnTestFrameLayout;
	private Button btnNavi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_main_layout);
		
		tvShow = (TextView)findViewById(R.id.main_tvshow);
		pbShow = (ProgressBar)findViewById(R.id.progressBar2);
		
		btnTestList = (Button)findViewById(R.id.btnTestList);
		btnTestList.setOnClickListener(btnTestListClick);
		
		btnTestSplash = (Button)findViewById(R.id.btnTestSplash);
		btnTestSplash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
		        Intent intent = new Intent();

		        Bundle dl = new Bundle();
		        dl.putString("name","LeiPei");    
		        intent.putExtras(dl);
		
		        intent.setClass(Test_Main.this, TestSplash.class);

		        startActivity(intent);

//		        this.finish();				
			}
			
		});
		
		btnTestAsync = (Button)findViewById(R.id.btnTestAsync);
		btnTestAsync.setOnClickListener(new btnTestAsyncClickListener());
		btnTestCustomLayout = (Button)findViewById(R.id.btnTestCustomLayout);
		btnTestCustomLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

		        Intent intent = new Intent();

		        Bundle dl = new Bundle();
		        dl.putString("name","LeiPei");    
		        intent.putExtras(dl);

		        intent.setClass(Test_Main.this, Test_CustomLayout.class);

		        startActivity(intent);

//		        this.finish();
				
			}
			
		});
		
		btnTestFrameLayout = (Button)findViewById(R.id.btnTestFrameLayout);
		btnTestFrameLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        Intent intent = new Intent();

		        Bundle dl = new Bundle();
		        dl.putString("name","LeiPei");    
		        intent.putExtras(dl);
				/* 调用一个新的Activity */ 
		    	//		startActivityForResult(intent, RESULT_OK);  使用一种可以返回的方法

		        intent.setClass(Test_Main.this, Test_FrameLayout.class);

		        startActivity(intent);
			}
			
		});
		
		btnNavi = (Button)findViewById(R.id.btnNavi);
		btnNavi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        Intent intent = new Intent();

		        Bundle dl = new Bundle();
		        dl.putString("name","LeiPei");    
		        intent.putExtras(dl);
				/* 调用一个新的Activity */ 
		    	//		startActivityForResult(intent, RESULT_OK);  使用一种可以返回的方法

		        intent.setClass(Test_Main.this, Test_Navi.class);

		        startActivity(intent);
			}
			
		});
		
	}
	
	private OnClickListener btnTestListClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

	        Intent intent = new Intent();

	        Bundle dl = new Bundle();
	        dl.putString("name","LeiPei");    
	        intent.putExtras(dl);
			/* 调用一个新的Activity */ 
	    	//		startActivityForResult(intent, RESULT_OK);  使用一种可以返回的方法

	        intent.setClass(Test_Main.this, Test_List.class);

	        startActivity(intent);

//	        this.finish();

		}
			
	};
	
	private class btnTestAsyncClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pbShow.setProgress(0);
			LoginAsyncTask lat = new LoginAsyncTask(tvShow, pbShow);
			lat.execute("mouse", "master");
		}
		
	}
}
