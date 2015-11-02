package inference;

import java.util.HashMap;

public class TurnOnHeat implements Task {

	private String name = "";
	private HashMap<String, String[]> associatedWords = null;
	private String rootWord = "";
	
	public TurnOnHeat(String command,HashMap<String, String[]> array, String root){
		name = command;
		associatedWords = array;
		rootWord = root;
	}
	@Override
	public String getname() {
		return name;
	}

	@Override
	public HashMap<String, String[]> getAssociatedWords() {
		return associatedWords;
	}

	@Override
	public String getRootWord() {
		return rootWord;
	}

}
