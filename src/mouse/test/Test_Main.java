package mouse.test;

import mouse.test.asynctask.LoginAsyncTask;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
	private Button btnTestFrameLayout;
	private Button btnNavi;
	private Button btnNum;
	private ProgressBar pbtest;
	private int proNum = 0;
	private ImageView imgShow;
	private ImageView iv1Show;
	private Button btnRefresh;
	private AnimationDrawable animationDrawable; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_main_layout);
		
		pbtest = (ProgressBar)findViewById(R.id.progressBar1);
		imgShow = (ImageView)findViewById(R.id.imgShow);
		iv1Show = (ImageView)findViewById(R.id.imageView1);
		btnNum = (Button)findViewById(R.id.btnNum);
		btnNum.setText("数据" + proNum);
		btnNum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				proNum += 1;
				pbtest.setProgress(proNum);
				btnNum.setText("数据" + proNum);
		//		Animation rotateAnimation=new RotateAnimation(0, 45);

		//		rotateAnimation.setDuration(3000);//设置动画持续时间为3秒

		//		rotateAnimation.setFillAfter(true);//设置动画结束后保持当前的位置（即不返回到动画开始前的位置）

		//		imgShow.startAnimation(rotateAnimation); 
			}
			
		});
		
		tvShow = (TextView)findViewById(R.id.main_tvshow);
		
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
		
		btnRefresh = (Button)findViewById(R.id.btnRefresh);
		btnRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iv1Show.setImageResource(R.anim.animation_loading);         
				animationDrawable = (AnimationDrawable) iv1Show.getDrawable();  
				animationDrawable.start(); 
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
			pbtest.setProgress(0);
			LoginAsyncTask lat = new LoginAsyncTask(tvShow, pbtest);
			lat.execute("mouse", "master");
		}
		
	}
}
