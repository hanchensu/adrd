import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.Text;


public class zTest2 {
	public static void main(String[] args) {
		String line = "305792804       {(123123,321193,1),(z8xa2-2a9QQ,7376712,1)}";
		String[] splits = line.split("\\p{Blank}+");
		String userid = splits[0];
		String pages="";
		System.out.println("splits[1]: "+splits[1].trim());
		
		String regex = "\\(([^,]+),(\\d+),(\\d+)\\)";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(splits[1]);
		while (match.find()) {
		    String key = match.group(2);
		    System.out.println(key);
		}
		
		
		
		if(splits[1].trim().length()<3) return;
		for (String seg : splits[1].trim().substring(1,splits[1].length()-1).trim().split(",")) {
//			System.out.println(seg);
			if (seg.length() < 3) continue; 
			seg = seg.trim().substring(1,seg.length()-1);
//			System.out.println(seg);
			String[] kv = seg.split(",");
			if (kv.length != 3)
				continue;
			pages+=kv[1]+",";
			
		}
		
//		context.write(new Text(userid+"______________________"), new Text(pages));
	}

}
