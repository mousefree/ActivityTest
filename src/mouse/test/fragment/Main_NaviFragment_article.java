package mouse.test.fragment;

//import mouse.test.R;
import java.util.ArrayList;
import java.util.List;
import mouse.test.R;
import mouse.test.adapter.Article_ChildFragment_Adapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * ViewPager 他实际上就是google提供的一种方便的fragment切换的控件，并且提供一些特殊的效果，
 * 比如自己手工去切换fragment要方便，并且效果好看。
 */


public class Main_NaviFragment_article extends Fragment {
	
	private LinearLayout article_fragment_hsv_child;
	private ViewPager vPager;
	private List<Fragment> fragmentList;
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
		View convertView  = inflater.inflate(R.layout.article_main_layout, container, false);
		article_fragment_hsv_child = (LinearLayout)convertView.findViewById(R.id.article_fragment_hsv_child);
		for(int i = 1; i <=5; i++ ) {
			TextView tv1 = new TextView(convertView.getContext());
			tv1.setPadding(15, 15, 15, 15);
			tv1.setText("我是选项目" + String.valueOf(i));
			article_fragment_hsv_child.addView(tv1);	
		}
		fragmentList = new ArrayList<Fragment>();
		Child1_NaviFragment_article child1fragment = new Child1_NaviFragment_article();
		Child2_NaviFragment_article child2fragment = new Child2_NaviFragment_article();
		Child3_NaviFragment_article child3fragment = new Child3_NaviFragment_article();
		Child4_NaviFragment_article child4fragment = new Child4_NaviFragment_article();
		Child5_NaviFragment_article child5fragment = new Child5_NaviFragment_article();
		
		fragmentList.add(child1fragment);
		fragmentList.add(child2fragment);
		fragmentList.add(child3fragment);
		fragmentList.add(child4fragment);
		fragmentList.add(child5fragment);
		
		vPager = (ViewPager) convertView.findViewById(R.id.viewpager);  
        vPager.setAdapter(new Article_ChildFragment_Adapter(getChildFragmentManager(), fragmentList)); 
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