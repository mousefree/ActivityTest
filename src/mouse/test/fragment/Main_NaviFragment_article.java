package mouse.test.fragment;

import mouse.test.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main_NaviFragment_article extends Fragment {
	
	private HorizontalScrollView article_fragment_hsv1;
	private LinearLayout article_fragment_hsv_ll1;
	private TextView article_fragment_tv1;
	//三个一般必须重载的方法
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        System.out.println("ExampleFragment--onCreate");
        
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
			        // Inflate the layout for this fragment
		View convertView  = inflater.inflate(R.layout.test_navi_layout1, container, false);
		article_fragment_hsv_ll1 = (LinearLayout)convertView.findViewById(R.id.article_fragment_hsv_ll1);
		article_fragment_tv1 = (TextView)convertView.findViewById(R.id.article_fragment_tv1);
		article_fragment_tv1.setText(this.getArguments().getString("data"));
		return convertView;
    }
	
    @Override
    public void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        System.out.println("ExampleFragment--onPause");
    }    

    @Override
    public void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        System.out.println("ExampleFragment--onResume");
    }

    @Override
    public void onStop()
    {
        // TODO Auto-generated method stub
        super.onStop();
        System.out.println("ExampleFragment--onStop");
    }	
}