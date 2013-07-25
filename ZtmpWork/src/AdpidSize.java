import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


public class AdpidSize {
	public static void main(String args[]) throws Exception {
		Map<String, String> adpid = new HashMap<String, String>();
		Map<String, String> adpidSize = new HashMap<String, String>();
		Map<String, Long> sizeCount = new HashMap<String, Long>();
		BufferedReader br = new BufferedReader(new FileReader(new File("D:/adpid/adpid0605")));
		String str;
		while ((str = br.readLine()) != null) {
			String key = "";
			if(str.startsWith("beans_")) {
				key = str.split("\\p{Blank}+")[0].split("_")[1];
				
			}
			
			String value = str.split("\\p{Blank}+")[1];
			adpid.put(key, value);	
		}
		br.close();
		
		br = new BufferedReader(new FileReader(new File("D:/adpid/adpidSize")));
		while ((str = br.readLine()) != null) {
			String key = "";
		
			key = str.split("\\p{Blank}+",2)[0];
				
			
			
			String value = str.split("\\p{Blank}+",2)[1];
			adpidSize.put(key, value);	
		}
		br.close();
		
//		for(Map.Entry<String, String> entry : adpid.entrySet()) {
//			System.out.println(entry.getKey() +"\t"+ entry.getValue());
//		}
		
		for(Map.Entry<String, String> entry : adpidSize.entrySet()) {
//			System.out.println(entry.getKey()+"__"+entry.getValue());
			if(sizeCount.containsKey(entry.getValue())) {
				if(adpid.containsKey(entry.getKey())) {
				     sizeCount.put(entry.getValue(), sizeCount.get(entry.getValue())+Long.parseLong(adpid.get(entry.getKey())));
				} 
			} else {
				if(adpid.containsKey(entry.getKey())) {
					sizeCount.put(entry.getValue(), 0L+Long.parseLong(adpid.get(entry.getKey())));
				} 
			}
		} 
		
//		for(Map.Entry<String, String> entry : adpid.entrySet()) {
//			if(adpidSize.containsKey(entry.getKey())) {
//				
//				System.out.println(entry.getKey()+"\t"+entry.getValue()+"\t"+adpidSize.get(entry.getKey()));
//			}
//		}
		int index = 0;
//		for(Map.Entry<String, Long> entry : sizeCount.entrySet()) {
////			System.out.println(index++);
//			System.out.println(entry.getKey()+"\t"+entry.getValue());
//		} 
		
//		for(Map.Entry<String, String> entry : adpid.entrySet()) {
//			System.out.println(entry.getKey()+"\t"+entry.getValue());
//		} 
		
		for(Map.Entry<String, String> entry : adpidSize.entrySet()) {
		    System.out.println(entry.getKey()+"\t"+entry.getValue());
	    } 
	
		
	}

}
