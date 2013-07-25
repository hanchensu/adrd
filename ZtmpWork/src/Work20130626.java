import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes.Name;

import javax.xml.stream.events.Namespace;

import org.apache.hadoop.io.Text;


public class Work20130626 {
	public static void main(String args[]) throws Exception{
////		BufferedReader br = new BufferedReader(new FileReader(new File("D:\\worktmp\\video\\0624\\album-name")));
//		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\worktmp\\video\\0624\\album-name"), "GBK"));
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
		String str="º®í×";
		System.out.println(str.contains("º®"));
		System.out.println(str.getBytes().length);
		System.out.println(new Text(str.getBytes("utf-8")));
	     
	}
}
