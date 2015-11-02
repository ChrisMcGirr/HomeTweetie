package inference;

import java.util.HashMap;

public class Inferrer {
	
	Task turnOnHeat = null;
	HashMap<String, Integer> states = new HashMap<String, Integer>();
	
	public Inferrer(){
		Parser test = new Parser();
		turnOnHeat = test.createGraph();
	}
	
	public void inferTask(String text){
		String[] words = text.split(" ");
		for(int i=0; i<words.length; i++){
			/*Only look at words that are large*/
			if(words[i].length() > 3){
				if(turnOnHeat.getAssociatedWords().containsKey(words[i])){
					if(!states.containsKey(turnOnHeat.getRootWord())){
						states.put(turnOnHeat.getRootWord(), 1);
					}
					else{
						states.put(turnOnHeat.getRootWord(), states.get(turnOnHeat.getRootWord())+1 );
					}
				}
			}
		}
	}

}
