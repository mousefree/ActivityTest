package mouse.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

public class Test_FrameLayout extends Activity {
    private   String TAG = "FramLayoutTestActivity";
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private List<ImageView> list;

    private int count=0;


        /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_frame_layout);
        image1=(ImageView)findViewById(R.id.image1);
        image2=(ImageView)findViewById(R.id.image2);
        image3=(ImageView)findViewById(R.id.image3);
        list=new ArrayList<ImageView>();
        list.add(image1);
        list.add(image2);
        list.add(image3);
    }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                        Log.i(TAG,"move---");
                        showImage();
                }
                
                return super.onTouchEvent(event);
        }
        private void showImage()
        {
                image1.setVisibility(View.VISIBLE);
                count=count%3;
                for(ImageView i:list)
                {
                        i.setVisibility(View.INVISIBLE);
                }
                list.get(count).setVisibility(View.VISIBLE);
                count++;
        }

}

