import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.tree.FixedHeightLayoutCache;


public class Work20130621 {
	public static void main(String args[]) throws Exception{
		String abc = "outcome=lm(train$V1~";
		for(int i = 2; i <= 131; i++) {
			abc += "train$V" + i+"+";
		}
		System.out.print(abc);
	}
	
	
}
