package mouse.test.fragment;

import java.util.ArrayList;
import java.util.Map;

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

public class Child3_NaviFragment_article extends Fragment {
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
			View convertView  = inflater.inflate(R.layout.article_child3_layout, container, false);
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
