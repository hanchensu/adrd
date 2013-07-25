import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes.Name;

import javax.xml.stream.events.Namespace;


public class Work20130624 {
	public static void main(String args[]) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File("D:\\worktmp\\video\\0624\\album-playcount-final")));
		Map<String, Integer> actors = new HashMap<String, Integer>();
		boolean append = false;
//		BufferedWriter wr1 = new BufferedWriter(new FileWriter(new File("D:\\worktmp\\video\\0624\\album-name"),append));
		
		BufferedWriter wr2 = new BufferedWriter(new FileWriter(new File("D:\\worktmp\\video\\0624\\actors-count"),append));
		
		String str;
		while ((str = br.readLine()) != null) {
			String[] items = str.split("\t");
			if(items.length > 3) { 
//			wr1.write(items[2].trim()+"\n");
			if(items[3].length()>1) {
				String names;
				if(items[3].startsWith("\"")) {
					names = items[3].substring(1, items[3].length()-1);
				} else {
					names = items[3];
				}
				for(String Name : names.split(","))
					if(actors.containsKey(Name)) {
						actors.put(Name, actors.get(Name)+1);
					} else {
						actors.put(Name, 1);
					}
				}
			}
			
		}
		for(String name:actors.keySet()) {
			wr2.write(name+"\t" + actors.get(name)+"\n");
		}
//		wr1.close();
		wr2.close();
		br.close();
		
	     
	}
}
