package inference;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Inferrer {
	
	private Task turnOnHeat = null;
	private HashMap<String, Integer> states = new HashMap<String, Integer>();
	private String[] keyStates = null;
	private float[] initialProb = null;
	private float[][] transitionProb = null;
	private int[] previousTweet = null;
	private int[] currentTweet = null;
	
	public Inferrer(){
		Parser test = new Parser();
		turnOnHeat = test.createGraph();
		keyStates = turnOnHeat.getAssociatedWords().keySet().toArray(new String[turnOnHeat.getAssociatedWords().keySet().size()]);
	}
	
	public void initialProbability(){
		String[] lines = parseTextFile("example.txt");
		transitionProb = new float[keyStates.length][keyStates.length];
		initialProb = new float[keyStates.length];
		currentTweet = new int[keyStates.length];
		previousTweet = new int[keyStates.length];
		Arrays.fill(previousTweet, 0);
		
		for(int i=0; i<lines.length; i++){
			inferTask(lines[i]);
			if(i>0){
				calculateTransitionCount();
			}
			previousTweet = currentTweet.clone();
		}
		
		calculateInitialProb();
		calculateTransitionProb();
		printTransMatrix();
	}
	
	public void nextProbability(String tweet){
		Arrays.fill(currentTweet, 0); 
		inferTask(tweet);
		calculateTransitionCount();
		calculateTransitionProb();
		predictNextState(currentTweet, transitionProb);
		previousTweet = currentTweet.clone();
		printTransMatrix();
	}
	
	private void predictNextState(int[] tweet, float[][] tMatrix){
		float[] nextState = new float[currentTweet.length];
		Arrays.fill(nextState, 0.0f);
				
		float[] currentProb = calculateTweetProb(tweet);
		for(int i=0; i<currentTweet.length; i++){
			for(int j=0; j<previousTweet.length; j++){
				nextState[i] += currentProb[j]*tMatrix[i][j];
			}
		}
		/*Debug Code*/
		System.out.print("States = ");
		for(int i=0; i<keyStates.length; i++){
			System.out.print(keyStates[i]+", ");
		}
		System.out.println("");
		System.out.print("Next State Prob = ");
		for(int i=0; i<nextState.length; i++){
			System.out.print(nextState[i]+", ");
		}
		System.out.println("");
		System.out.print("Current Tweet = ");
		for(int i=0; i<tweet.length; i++){
			System.out.print(tweet[i]+", ");
		}
		System.out.println("");
		
	}
	
	private float[] calculateTweetProb(int[] tweet){
		float sum=0;
		float[] out = new float[tweet.length];
		for(int i=0; i<tweet.length; i++){
			sum += tweet[i];
		}
		for(int i=0; i<tweet.length; i++){
			if(tweet[i]>0){
				out[i] = ((float)tweet[i])/sum;
				//out[i] = tweet[i];
			}
		}
		return out;
	}
	private void calculateTransitionProb(){
		for(int i=0; i<currentTweet.length; i++){
			float sum = 0;
			for(int j=0; j<previousTweet.length; j++){
				sum += transitionProb[i][j];
			}
			for(int j=0; j<previousTweet.length; j++){
				transitionProb[i][j] = transitionProb[i][j]/sum;
			}
		}
	}
	private void calculateTransitionCount(){
		for(int i=0; i<currentTweet.length; i++){
			for(int j=0; j<previousTweet.length; j++){
				if(currentTweet[i]>0){
					if(previousTweet[j]>0){
						transitionProb[i][j] += 1;
					}
				}
			}
		}
	}
	
	private void printTransMatrix(){
		System.out.println("Transition Matrix");
		for(int i=0; i<currentTweet.length; i++){
			for(int j=0; j<previousTweet.length; j++){
				System.out.print(transitionProb[i][j]+", ");
			}
			System.out.println("");
		}
	}
	private void calculateInitialProb(){
		String[] keys = states.keySet().toArray(new String[states.keySet().size()]);
		Iterator<Integer> values = states.values().iterator();
		int total = 0;
		while(values.hasNext()){
			total += values.next();
		}
		if(total > 0){
			for(int i=0; i<keys.length; i++){
				initialProb[i] = ((float)states.get(keys[i])/total);
			}
		}else{
			for(int i=0; i<keys.length; i++){
				initialProb[i] = (float)states.get(keys[i]);
			}
		}
	}
	
	public void inferTask(String text){
		Arrays.fill(currentTweet, 0); 
		String[] words = text.split(" ");
		for(int i=0; i<words.length; i++){
			/*Only look at words that are large*/
			if(words[i].length() >= 3){
				for(int j=0; j<keyStates.length; j++){
					if(turnOnHeat.getAssociatedWords().containsKey(keyStates[j])){
						String[] list = turnOnHeat.getAssociatedWords().get(keyStates[j]);
						for(int k=0; k<list.length; k++){
							if(list[k].equals(words[i].toLowerCase().replaceAll("[^a-zA-Z ]", ""))){
								if(!states.containsKey(keyStates[j])){
									//states.put(keyStates[j], 1);
									currentTweet[j] += 1;
								}
								else{
									//states.put(keyStates[j], states.get(keyStates[j])+1);
									currentTweet[j] += 1;
								}	
							}
						}
					}
				}
			}
		}
	}
	
	public String[] parseTextFile(String path){
		ArrayList<String> out = new ArrayList<String>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        out.add(line);
		    }
		    // line is not visible here.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return out.toArray(new String[out.size()]);
	}
	
	public void printStates(){
		System.out.println("States and Counts");
		String[] keys = states.keySet().toArray(new String[states.keySet().size()]);
		Iterator<Integer> values = states.values().iterator();
		int total = 0;
		while(values.hasNext()){
			total += values.next();
		}
		if(total > 0){
			for(int i=0; i<keys.length; i++){
				System.out.println(keys[i]+": "+((float)states.get(keys[i])/total));
			}
		}else{
			for(int i=0; i<keys.length; i++){
				System.out.println(keys[i]+": "+states.get(keys[i]));
			}
		}

	}
}
