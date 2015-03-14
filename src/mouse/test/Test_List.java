package mouse.test;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import mouse.test.adapter.*;

public class Test_List extends Activity {

	private ListView lv1;
	private List<String> lv1_data;
	
	private ListView lv2;
	private List<Map<String, Object>>lv2_data;
	
	private ListView lv3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_list_layout);
		
		lv1 = (ListView)findViewById(R.id.lv1);
		ListAdapter la1 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, get_lv1_Data());
		lv1.setAdapter(la1);
		
//		��������ʾ�����ݾ���˵��Ҫ����AdapterView.OnItemClickListener��
//		�����ṩ�Ĳ��ǣ���������������OnItemClickListener�ϣ���֪������ʵ����һ��view��OnItemClickListener��
//		����Ϊ�˱���������ֱ��дnew AdapterView.OnItemClickListener(){...}��
/*
 *    new AdapterView.OnItemClickListener()    ����Ľ����������,ʹ������AdapterView��,�����Ĳ���ʹ��onItemClick�¼�
 *    �������lv1�ǵ�һ����򵥵��б��չʾ,ֻ�ǵ�����������һ���ַ�����չʾ��Ϣ. ArrayAdapter
 */
		lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), lv1_data.get(position),
					     Toast.LENGTH_SHORT).show();				
			}			
		});
		
		/*
		 *  lv2 ������һЩ�������ݵ���չ,�����ڽ�����ͬʱ����ʾ����ı���ͼƬ������,ֻ�ǵ�����չʾ,��û��Item�����¼��������,ʹ�ñȽ��ʺ�
		 *  ����OnItemClick�������¼�,������ʹ��lv2���ַ�ʽ. SimpleAdapter
		 */
		lv2 = (ListView)findViewById(R.id.lv2);
		SimpleAdapter sa1 = new SimpleAdapter(this, get_lv2_Data(), R.layout.custom_simpleadapter, 
				new String[] {"head", "phone", "date"}, new int[] {R.id.iv_head, R.id.tvPhone, R.id.tvDate});
		lv2.setAdapter(sa1);
		
		lv3 = (ListView)findViewById(R.id.lv3);
		
		TempBaseAdapter tba1 = new TempBaseAdapter(this, get_lv2_Data());
		lv3.setAdapter(tba1);
	}
	
	private List<String> get_lv1_Data() {
		lv1_data = new ArrayList<String>(); 
		lv1_data.add("��������1"); 
		lv1_data.add("��������2"); 
		lv1_data.add("��������3"); 
		lv1_data.add("��������4");           
        return lv1_data;
	}
	
	private List<Map<String, Object>> get_lv2_Data() {
		lv2_data = new ArrayList<Map<String, Object>>();
		for(int i = 1; i < 5; i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("head", R.drawable.test_head);
			map.put("phone", "1500434000" + String.valueOf(i));
			long time=System.currentTimeMillis();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");  
			String t=format.format(new Date(time));
			map.put("date", t);
			lv2_data.add(map);
		}
		return lv2_data;
	}
	
	
}
