import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.sound.sampled.Line;


public class Tester {
	public static void main(String[] args) throws IOException {
		Map<String, Double> mfCnt = new HashMap<String, Double>();
		
		BufferedReader br = new BufferedReader(new FileReader(new File("./sorted")));
		String str;
		while ((str = br.readLine()) != null) {
			String[] splits = str.split("\\p{Blank}+");
			String pageId = splits[0];
			double rate = 1;
			Double mNum = 0.0, fNum = 0.0;
			for (String seg : splits[1].split(",")) {
				String[] kv = seg.split(":");
				if (kv.length != 2) continue;
				if("M".equals(kv[0])) mNum = Double.parseDouble(kv[1]);
				else fNum = Double.parseDouble(kv[1]);
				
			}
			
			rate = mNum / fNum;
//			if(mNum > 0 && fNum>0 && mNum + fNum >= THRESHOLD) {
				mfCnt.put(pageId, rate);
//			}
		}
		br.close();
		Double mfRate = mfCnt.get("All");
		for(String pageId : mfCnt.keySet()) {
			if(!"All".equals(pageId)) {
				mfCnt.put(pageId, mfCnt.get(pageId) / mfRate);
			}
		}
		double x = 47431, y = 25468;
		System.out.println(mfCnt.get("56")+" "+(x/y) / ((double) 1378718/456591));
		
		br = new BufferedReader(new FileReader(new File("./sorted")));

		String line = "0       11520:1 56:1    12231:1";
		String[] splits = line.split("\\p{Blank}+");
		String gender = splits[0];
		gender = "0".equals(gender) ? "M" : "F";
		Double rate = mfCnt.get("All");
		int changed = 0;
		for (String seg : splits) {
			String[] kv = seg.split(":");
			if (kv.length != 2)
				continue;
			if (mfCnt.containsKey(kv[0])) {
				changed++;
				rate *= mfCnt.get(kv[0]);
			}
		}
		Double frate = 1.0 / (rate + 1.0);
		System.out.println(frate.toString()+" "+gender);
	}

}
