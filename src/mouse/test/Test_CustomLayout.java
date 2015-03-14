package mouse.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/*
 *   �����Ԫ��ʾ������δ�����Ʋ��������һЩ�������,Ŀǰ��,ֻ�ܴ���һЩϸ�ڵĶ����ȽϺ�,���ȫ���ô�����������,�������ܴ�
 */
public class Test_CustomLayout extends Activity {

	private LinearLayout ll;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_customlayout);

		
		ll = (LinearLayout)findViewById(R.id.ll1);
		LinearLayout ll1 = new LinearLayout(this);

		
		LinearLayout.LayoutParams lp0 = 
				new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
		
		LinearLayout.LayoutParams lp1 = 
				new LinearLayout.LayoutParams(0,
						ViewGroup.LayoutParams.MATCH_PARENT, 1);
		
		RelativeLayout.LayoutParams lp2 = 
				new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);

		ll.addView(ll1, lp0);
		
		for(int i = 1; i < 5; i++) {
			LinearLayout ll2 = new LinearLayout(this);
			ll1.addView(ll2, lp1);
		
			RelativeLayout rl1 = new RelativeLayout(this);
			rl1.setPadding(0, 25, 0, 25);
			ll2.addView(rl1, lp2);
			
			Button bt1 = new Button(this);
			bt1.setId(i);
			bt1.setText("button");
			TextView tv1 = new TextView(this);
			tv1.setText("textView");
		
			RelativeLayout.LayoutParams buttonlp = 
					new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
			//ˮƽ����
			buttonlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			rl1.addView(bt1, buttonlp);
			
			RelativeLayout.LayoutParams tvlp = 
					new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
			//ˮƽ����
			tvlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			tvlp.addRule(RelativeLayout.BELOW, i);
			
			rl1.addView(tv1, tvlp);
		}
	}
}
