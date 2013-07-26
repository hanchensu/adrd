package sessionlog.mapreduce;

import java.util.List;

public interface Formator {
	
	public FormatResult format(String str);
	
	public String getMark();
	
}
