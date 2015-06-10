package mouse.test.fragment;
/*
 * http://ask.csdn.net/questions/1751  DialogFragment传递参数
 * http://blog.csdn.net/lmj623565791/article/details/37815413/  DialogFragment的各类应用
 * http://www.tuicool.com/articles/VFv2ea 自定义标题栏
 */

import java.util.ArrayList;
import java.util.Map;

import mouse.test.R;
import mouse.test.Test_Navi;
import mouse.test.adapter.Article_ChildFragment_Adapter;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Test_DialogFragment extends DialogFragment {
	
	private EditText showData;
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {  
        View view = inflater.inflate(R.layout.test_dialogfragment, container);  
        showData = (EditText)view.findViewById(R.id.editText1);
        String s = getArguments().getString("FID");
        showData.setText(s);
        return view;  
    } 
}
