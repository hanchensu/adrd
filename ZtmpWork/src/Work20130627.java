import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes.Name;

import javax.xml.stream.events.Namespace;

import org.apache.hadoop.io.Text;


public class Work20130627 {
	public static void main(String args[]) throws Exception{
////		BufferedReader br = new BufferedReader(new FileReader(new File("D:\\worktmp\\video\\0624\\album-name")));
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\worktmp\\video\\0624\\album-name"), "GBK"));
//		Map<String, Integer> actors = new HashMap<String, Integer>();
//		boolean append = false;
////		BufferedWriter wr1 = new BufferedWriter(new FileWriter(new File("D:\\worktmp\\video\\0624\\album-name"),append));
//		
////		BufferedWriter wr2 = new BufferedWriter(new FileWriter(new File("D:\\worktmp\\video\\0624\\actors-name"),append));
//		
//		String str;
//		while ((str = br.readLine()) != null) {
//			System.out.println(new Text(str).toString());
//		}
//		
//		br.close();
		Set<String> abc = new HashSet<String>();
		abc.add("a");
		abc.add("b");
		abc.add("a");
		for(String str:abc) {
			System.out.println(str);
		}
	     
	}
	
	public static void traverseFiles(String dir) {
		File file = new File(dir);
		if(file.isDirectory()) {
			for(File subFile : file.listFiles()) {
				traverseFiles(subFile.getAbsolutePath());
			}
		} else {
			System.out.println();
		}
	}
}
