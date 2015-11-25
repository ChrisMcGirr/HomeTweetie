package inference;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import org.json.*;

import actions.turnOnHeat;

public class Parser {
	
	HashMap<String, String[]> words = new HashMap<String, String[]>();

	/*
	 * From the JSON object file create a Has Map of the states and their associated words. 
	 * Simply follows the format and returns the command as a type Task. 
	 */
	public Task createGraph(String dictionary){
		try{
			JSONObject obj = new JSONObject(readFile(dictionary));
			JSONObject command = obj.getJSONObject("command");
			JSONArray wordArray = command.getJSONArray("words");
			for(int i=0; i<wordArray.length(); i++){
				words.put(command.getJSONArray("words").getJSONObject(i).get("main").toString(), returnArrayString(wordArray.getJSONObject(i).getJSONArray("associated")));
			}
			Task Command = new Task(command.get("name").toString(), words, wordArray.getJSONObject(0).get("main").toString());
			return Command; 
		}
		catch(JSONException e){
			System.out.println("Execption in JSON Parser: Parser.java");
			return null;
		}
		
	}

	/*
	 * Converts a JSON array to a string Array
	 */
	public String[] returnArrayString(JSONArray array){
		String [] output = new String[array.length()];
		for(int i=0; i< output.length; i++){
			output[i] = array.get(i).toString();
		}
		return output;
	}
	
	/*Taken from https://blog.nraboy.com/2015/03/parse-json-file-java/ */
	/*So that we can easily read the JSON file*/
	public static String readFile(String filename) {
	    String result = "";
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(filename));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        result = sb.toString();
	        br.close();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	/*Prints the HashMap with states and their associated words*/
	public void printWords(HashMap<String, String[]> input){
		Object[] keys = input.keySet().toArray();
		int count = 0;
		for(String[] s: input.values()){
			System.out.print("Key: " +keys[count].toString() +" = {");
			for(int i=0; i<s.length; i++){
				System.out.print(s[i]+", ");
			}
			System.out.println(" }");
			count++;
		}
	}
}
