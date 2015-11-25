package inference;

import java.util.HashMap;

public class Task {
	
	
	private String name = "";
	private HashMap<String, String[]> associatedWords = null;
	private String rootWord = "";
	
	/*
	 * Task object is similar to the Command object, but here it is just used to store the results 
	 * of the JSON Parsing of the command. Here no function can be executed. In the future it might be
	 * cleaner to simply combine the two Classes into one to reduce the code clutter.
	 */
	public Task(String commandName,HashMap<String, String[]> array, String root){
		name = commandName;
		associatedWords = array;
		rootWord = root;
	}
	/*Name of the command or task. Usually same as root word.*/
	public String getname() {
		return name;
	}

	/*Associated words with the state*/
	public HashMap<String, String[]> getAssociatedWords() {
		return associatedWords;
	}
	
	/*In other words return the state name of the command (task)*/
	public String getRootWord() {
		return rootWord;
	}

}
