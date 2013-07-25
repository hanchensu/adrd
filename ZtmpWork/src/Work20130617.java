import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.tree.FixedHeightLayoutCache;


public class Work20130617 {
	public static void main(String args[]) throws Exception{
//		BufferedReader br = new BufferedReader(new FileReader(new File("D:/worktmp/yingfu.txt")));
//		String str;
//		while ((str = br.readLine()) != null) {
//			System.out.print("mkey == '"+str+"' OR ");
//			
//		}
//		br.close();
		
	     
//	     System.out.println(getIntervalDays(1371118386,1370013386));
	     System.out.println(new Work20130617().fix("126"));
	}
	
	public static long getIntervalDays(long t1, long t2) throws ParseException {
		SimpleDateFormat format =   new SimpleDateFormat("yyyy-MM-dd");
		String d1 = format.format(t1*1000L);
	    String d2 = format.format(t2*1000L);
	    Date date1 = format.parse(d1);
	    Date date2 = format.parse(d2);
	    return (date1.getTime() - date2.getTime()) / (24*60*60*1000);
	}
	
	public static int fix (String value) {
		int amt = Integer.parseInt(value);
		if(amt % 5 != 0) {
			amt = (amt / 5) * 5 + 5;
		}
		return amt;
	}

}
