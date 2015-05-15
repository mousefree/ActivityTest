package mouse.test;

import mouse.test.customview.CustomListView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Test_CustomView extends Activity {

	private CustomListView clv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_custom_view);
		clv =(CustomListView)findViewById(R.id.clv);
		clv.setIsShowRefreshHeadView(true);
	}
}
