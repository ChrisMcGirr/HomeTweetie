package inference;

import java.util.HashMap;

public interface Task {
	
	
	public String getname();
	public HashMap<String, String[]> getAssociatedWords();
	public String getRootWord();

}
