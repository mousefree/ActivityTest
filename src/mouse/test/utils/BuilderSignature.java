package mouse.test.utils;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BuilderSignature {
	
	private static BuilderSignature instance = new BuilderSignature();
	
	public static BuilderSignature getInstance() {
		return instance;
	}
	
	public Map<String, String> genSignature() {
		Timestamp d = new Timestamp(System.currentTimeMillis()); 
		String timestamp = d.toString();
		String token = "mouse";
		Random random1 = new Random(100);
		String nonce = String.valueOf(Math.abs(random1.nextInt()));
		String sortvalue = StringSort.DictSort(token, timestamp, nonce);
		String signature = EncoderHandler.encode("SHA1", sortvalue);
		Map<String, String> urlparams = new HashMap<String, String>();
		urlparams.put("timestamp", URLEncoder.encode(timestamp));
		urlparams.put("token", "mouse");
		urlparams.put("nonce", nonce);
		urlparams.put("signature", signature);
		return urlparams;
	}
}
