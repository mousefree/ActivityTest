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

	private Button btnTestList;
	private Button btnTestSplash;
	private Button btnTestMenu;
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
				
			}
			
		});
		
		btnTestMenu = (Button)findViewById(R.id.btnTestMenu);
		btnTestMenu.setOnClickListener(new btnTestMenuClickListener());
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
	
	private class btnTestMenuClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
