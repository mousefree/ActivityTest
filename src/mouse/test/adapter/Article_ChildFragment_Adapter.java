package mouse.test.adapter;

import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Article_ChildFragment_Adapter extends FragmentPagerAdapter {

	private List fragmentlist;
	
	public Article_ChildFragment_Adapter(FragmentManager fm, List list) {
		super(fm);
		// TODO Auto-generated constructor stub
		fragmentlist = list;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return (Fragment) fragmentlist.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentlist.size();
	}

}
