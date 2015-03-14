package mouse.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_main_layout);
		
		btnTestList = (Button)findViewById(R.id.btnTestList);
		btnTestList.setOnClickListener(btnTestListClick);
		
		btnTestSplash = (Button)findViewById(R.id.btnTestSplash);
		btnTestSplash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/* �½�һ��Intent���� */
		        Intent intent = new Intent();

		        Bundle dl = new Bundle();
		        dl.putString("name","LeiPei");    
		        intent.putExtras(dl);
		        /* ָ��intentҪ�������� */
		        intent.setClass(Test_Main.this, TestSplash.class);
		        /* ����һ���µ�Activity */
		        startActivity(intent);
		        /* �رյ�ǰ��Activity */
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
				/* �½�һ��Intent���� */
		        Intent intent = new Intent();

		        Bundle dl = new Bundle();
		        dl.putString("name","LeiPei");    
		        intent.putExtras(dl);
		        /* ָ��intentҪ�������� */
		        intent.setClass(Test_Main.this, Test_CustomLayout.class);
		        /* ����һ���µ�Activity */
		        startActivity(intent);
		        /* �رյ�ǰ��Activity */
//		        this.finish();
				
			}
			
		});
		
	}
	
	private OnClickListener btnTestListClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/* �½�һ��Intent���� */
	        Intent intent = new Intent();

	        Bundle dl = new Bundle();
	        dl.putString("name","LeiPei");    
	        intent.putExtras(dl);
	        /* ָ��intentҪ�������� */
	        intent.setClass(Test_Main.this, Test_List.class);
	        /* ����һ���µ�Activity */
	        startActivity(intent);
	        /* �رյ�ǰ��Activity */
//	        this.finish();

		}
			
	};
	
	private class btnTestAsyncClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
