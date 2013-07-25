import java.util.Arrays;
import java.util.List;


public class Test {
	public static void main(String[] args) {
		String[] words = {"a","b","c","d"};
		List<String> wordList = Arrays.asList(words);
		for(String e:wordList) {
			System.out.println(wordList.indexOf("a"));
		}
	}
}
