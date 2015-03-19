package mouse.test.adapter;

import java.util.List;
import java.util.Map;

import mouse.test.*;
import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TempBaseAdapter extends BaseAdapter {

	private class ShowViewItem {
		TextView phone;
		TextView customdate;
		Button demobutton;
		CheckBox democheckbox;
		ImageView demoimageview;
	}
	
	private List<Map<String, Object>> mData;
	private LayoutInflater mInflater;
	private Context c;
	private ShowViewItem holder;
	
	public TempBaseAdapter(Context context, List<Map<String, Object>> data) {
		mData = data;
		c = context;
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void removeItem(int position) {
		mData.remove(position);
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView  != null) {
			holder = (ShowViewItem)convertView.getTag();
		}
		else {
			convertView = mInflater.inflate(mouse.test.R.layout.custom_basedapter, null);
			holder = new ShowViewItem();
			holder.demoimageview = (ImageView)convertView.findViewById(mouse.test.R.id.imageView1);
			holder.phone = (TextView)convertView.findViewById(mouse.test.R.id.navi_tv3);
			holder.customdate = (TextView)convertView.findViewById(mouse.test.R.id.textView2);
			holder.demobutton = (Button)convertView.findViewById(mouse.test.R.id.listBtn);
			holder.democheckbox = (CheckBox)convertView.findViewById(mouse.test.R.id.listCheck);
			convertView.setTag(holder);
		}
		
		holder.demoimageview.setImageResource((Integer)mData.get(position).get("head"));
		holder.phone.setText((String)mData.get(position).get("phone"));
		holder.customdate.setText((String)mData.get(position).get("date"));
		
		holder.demobutton.setOnClickListener(new CustomOnClickListener(position));
		
		return convertView;
	}
	
	public class CustomOnClickListener implements OnClickListener {

		private int pos;
		
		public CustomOnClickListener(int position) {
			pos = position;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(c, (String)mData.get(pos).get("phone"),
				     Toast.LENGTH_SHORT).show();			

		}
		
	}

}
