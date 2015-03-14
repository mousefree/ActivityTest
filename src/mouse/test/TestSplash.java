package mouse.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class TestSplash extends Activity {

	 private final int SPLASH_DELAY_TIME = 5000 ;  
	 private String Tag = "Welcome" ; 
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.test_splash);		
        new Handler().postDelayed(  
                new Runnable()  
                {  
                    @Override  
                    public void run() {  
                        // TODO Auto-generated method stub  
                        startActivity(new Intent(TestSplash.this , Test_Main.class));  
                        TestSplash.this.finish();  
                    }  
                      
                }  
        , SPLASH_DELAY_TIME);  
	}
}
