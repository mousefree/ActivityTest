package mouse.test;

/*
 * 消息机制url
 * http://blog.csdn.net/liuhe688/article/details/6407225
 */

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mouse.test.customview.CustomListView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

public class Test_CustomView extends Activity {

	private CustomListView clv;
	private List<Map<String, Object>>lv1_data;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_custom_view);
		clv =(CustomListView)findViewById(R.id.clv);
		clv.setIsShowRefreshHeadView(true);
		clv.setIsShowLoadingFootView(true);
		clv.setIsShowNaviImgHeadView(true);
		clv.SetNaviImg(new int[] {R.drawable.car1, R.drawable.car2, 
				R.drawable.car3, R.drawable.car4, R.drawable.car5});
		SimpleAdapter sa1 = new SimpleAdapter(this.getBaseContext(), get_lv1_Data(), R.layout.article_child1_simpleadapter, 
				new String[] {"head", "title", "date", "reply"}, new int[] {R.id.iv_head, R.id.tvTitle, R.id.tvDate, R.id.tvReply});
		clv.setAdapter(sa1);		
	}
	
	private List<Map<String, Object>> get_lv1_Data() {
		lv1_data = new ArrayList<Map<String, Object>>();
		for(int i = 1; i < 10; i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("head", R.drawable.test_head);
			map.put("title", "1500434000" + String.valueOf(i));
			long time=System.currentTimeMillis();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");  
			String t=format.format(new Date(time));
			map.put("date", t);
			map.put("reply", 50);
			lv1_data.add(map);
		}
		return lv1_data;
	}
}
