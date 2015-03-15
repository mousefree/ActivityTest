package mouse.test.asynctask;

import java.util.Map;

import mouse.test.utils.BuilderSignature;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginAsyncTask extends AsyncTask<String, String, String> {
	
	private TextView textView;  
    private ProgressBar progressBar; 
	
    /*
     * 在构造函数里面，可以直接引用外面的显示控件，显示查询结果。
     */
	public LoginAsyncTask(TextView textView, ProgressBar progressBar) {  
        super();  
        this.textView = textView;  
        this.progressBar = progressBar;  
    } 
	
	
	/**  
     * 这里的String参数对应AsyncTask中的第一个参数   
     * 这里的String返回值对应AsyncTask的第三个参数  
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改  
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作  
     */ 
	@Override
	protected String doInBackground(String... params) {
		// TODO: attempt authentication against a network service.
		String getString = "";
		Map<String, String> urlparams = BuilderSignature.getInstance().genSignature();
		urlparams.put("servicename", "terminalvalidate");
		urlparams.put("username", params[0]);
		urlparams.put("password", params[1]);
		try {
			publishProgress("1", "准备查询中，请稍等。。");
			Thread.sleep(2000);
			// Simulate network access.
			// timestamp=2015-02-10,token=mouse,nonce=123456
			//4422a349629fbb71963a3c206131bbe19d4afe66
	//		getString = HttpConnector.getInstance().doGet(queryServiceAddress, urlparams);
			getString = "OK";
			publishProgress("5", "查询返回结果中，请稍等。。");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			return null;
		}


		// TODO: register the new account here.
		if(!getString.equals("OK"))
			return null;
		else
			return getString;
	}
	
	 /**  
     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）  
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置  
     */
	@Override
	protected void onPostExecute(String s) {
        
		if (s.equals("OK")) {
			progressBar.setProgress(10);
	        textView.setText("查询完成。提交数据。"); 
		}
	}
	
    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置  
    @Override  
    protected void onPreExecute() {  
        textView.setText("查询完成。准备提交界面操作。");  
    }  
  
  
    /**  
     * 这里的Intege参数对应AsyncTask中的第二个参数  
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行  
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作  
     */  
    @Override  
    protected void onProgressUpdate(String... values) {  
        String progressvalue = values[0];  
        String progressmessage = values[1];
        progressBar.setProgress(Integer.parseInt(progressvalue));
        textView.setText(progressmessage);
    } 

	@Override
	protected void onCancelled() {
		
	}

}
