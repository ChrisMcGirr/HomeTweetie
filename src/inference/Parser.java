package inference;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import org.json.*;

public class Parser {
	
	 HashMap<String, String[]> words = new HashMap<String, String[]>();

	public Task createGraph(){
		JSONObject obj = new JSONObject(readFile("dictionary.json"));
		JSONObject command = obj.getJSONObject("command");
		JSONArray wordArray = command.getJSONArray("words");
		words.put(command.getJSONArray("words").getJSONObject(0).get("main").toString(), returnArrayString(wordArray.getJSONObject(0).getJSONArray("associated")));
		words.put(command.getJSONArray("words").getJSONObject(1).get("main").toString(), returnArrayString(wordArray.getJSONObject(1).getJSONArray("associated")));
		words.put(command.getJSONArray("words").getJSONObject(2).get("main").toString(), returnArrayString(wordArray.getJSONObject(2).getJSONArray("associated")));
		TurnOnHeat turnOnHeat = new TurnOnHeat(command.get("name").toString(), words, wordArray.getJSONObject(0).get("main").toString());
		return turnOnHeat; 
	}


	public String[] returnArrayString(JSONArray array){
		String [] output = new String[array.length()];
		for(int i=0; i< output.length; i++){
			output[i] = array.get(i).toString();
		}
		return output;
	}
	
	/*Taken from https://blog.nraboy.com/2015/03/parse-json-file-java/ */
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
