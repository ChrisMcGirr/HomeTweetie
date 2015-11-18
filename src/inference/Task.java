package inference;

import java.util.HashMap;

public class Task {
	
	
	private String name = "";
	private HashMap<String, String[]> associatedWords = null;
	private String rootWord = "";
	
	public Task(String commandName,HashMap<String, String[]> array, String root){
		name = commandName;
		associatedWords = array;
		rootWord = root;
	}
	public String getname() {
		return name;
	}

	public HashMap<String, String[]> getAssociatedWords() {
		return associatedWords;
	}

	public String getRootWord() {
		return rootWord;
	}

}
