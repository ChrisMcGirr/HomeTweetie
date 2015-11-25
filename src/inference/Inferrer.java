package inference;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;


public class Inferrer {
	
	private Task command = null;
	private HashMap<String, Integer> states = new HashMap<String, Integer>();
	private String[] keyStates = null;
	private float[] initialProb = null;
	private float[][] transitionProb = null;
	private int[] previousTweet = null;
	private int[] currentTweet = null;
	private float[] nextState = null;
	private float[] currentState = null;
	
	public Inferrer(String dictionary){
		Parser test = new Parser();
		command = test.createGraph(dictionary);
		keyStates = command.getAssociatedWords().keySet().toArray(new String[command.getAssociatedWords().keySet().size()]);
	}
	/*
	 * Mode true = inferrer from past tweets, mode false = not inferrence
	 */
	public void initialProbability(String tweets[], boolean mode){
		//For testing purposes
		//String[] lines = parseTextFile("example.txt");
		
		transitionProb = new float[keyStates.length][keyStates.length];
		initialProb = new float[keyStates.length];
		currentTweet = new int[keyStates.length];
		previousTweet = new int[keyStates.length];
		Arrays.fill(previousTweet, 0);
		
		/*Calculate the matrices and vectors using past information*/
		if(mode){
			for(int i=0; i<tweets.length; i++){
				inferTask(tweets[i]);
				if(i>0){
					calculateTransitionCount();
				}
				previousTweet = currentTweet.clone();
			}
			calculateInitialProb();
			calculateTransitionProb();
		}
		/*Use an Identity Matrix to represent past information
		 * Set initial prob to uniform distribution over all states.
		 * */
		else{
			/*Create Identity Matrix*/
			for(int i=0; i<keyStates.length; i++){
				transitionProb[i][i] = 1;
				initialProb[i] = 1.0f/((float)keyStates.length);
			}
		}

		//System.out.println("Stochastic Transistion Matrix");
		//printTransMatrix();
	}
	
	/*Calculate the next state probability based on the current
	 * and the transition matrix*/
	public void nextProbability(String tweet){
		Arrays.fill(currentTweet, 0); 
		inferTask(tweet);
		
		//was used to continuously update the transition matrix
		//calculateTransitionCount();
		//calculateTransitionProb();
		
		predictNextState(currentTweet, transitionProb);
		currentState = nextState.clone();
		previousTweet = currentTweet.clone();
	}
	
	public float[] getCurrentStateProb(){
		return currentState;
	}
	public int[] getCurrentState(){
		return currentTweet;
	}
	public String[] getStateNames(){
		return keyStates;
	}
	
	/*
	 * Actual computation of the next state probability with respect to the current tweet.
	 * Simply multiple the transistion matrix and the current probability vector. 
	 * */
	private void predictNextState(int[] tweet, float[][] tMatrix){
		nextState = new float[currentTweet.length];
		Arrays.fill(nextState, 0.0f);
				
		float[] currentProb = calculateTweetProb(tweet);
		for(int i=0; i<currentTweet.length; i++){
			for(int j=0; j<previousTweet.length; j++){
				nextState[i] += currentProb[j]*tMatrix[i][j];
			}
		}
	}
	
	/*
	 * Given a state vector from a tweet, which is the number of times a state
	 * occurs in that tweet calculate the probability from that. 
	 */
	private float[] calculateTweetProb(int[] tweet){
		float sum=0;
		float[] out = new float[tweet.length];
		for(int i=0; i<tweet.length; i++){
			sum += tweet[i];
		}
		for(int i=0; i<tweet.length; i++){
			if(tweet[i]>0){
				out[i] = ((float)tweet[i])/sum;
			}
		}
		return out;
	}
	/*
	 * Given the transition matrix as a state transition convert it into a stochastic matrix
	 * by simple dividing each element by the sum of the row. 
	 */
	private void calculateTransitionProb(){
		for(int i=0; i<currentTweet.length; i++){
			float sum = 0;
			for(int j=0; j<previousTweet.length; j++){
				sum += transitionProb[i][j];
			}
			for(int j=0; j<previousTweet.length; j++){
				if(transitionProb[i][j] == 0){
					transitionProb[i][j] = 0;
				}
				else{
					transitionProb[i][j] = transitionProb[i][j]/sum;
				}
			}
		}
	}
	/*
	 * This creates the state transition matrix which contains integer values.
	 */
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
	
	/*
	 * Print the transition matrix for debugging purposes.
	 */
	private void printTransMatrix(){
		System.out.println("Transition Matrix");
		for(int i=0; i<currentTweet.length; i++){
			for(int j=0; j<previousTweet.length; j++){
				System.out.print(transitionProb[i][j]+", ");
			}
			System.out.println("");
		}
	}
	/*
	 * Calculates the initial probability
	 */
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
	
	/*
	 * Given a tweet count the amount of times a state occurs in that tweet and save it as a state vector.
	 */
	public void inferTask(String text){
		Arrays.fill(currentTweet, 0); 
		String[] words = text.split(" ");
		for(int i=0; i<words.length; i++){
			/*Only look at words that are large*/
			if(words[i].length() >= 3){
				for(int j=0; j<keyStates.length; j++){
					if(command.getAssociatedWords().containsKey(keyStates[j])){
						String[] list = command.getAssociatedWords().get(keyStates[j]);
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
	
	/*
	 * Used for debugging, rather then submitting real tweets, text files with tweets per line
	 * were used for testing. Parses a file and returns the lines as a string Array
	 */
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
	
	/*
	 * Print all the states occurring and their count
	 */
	public void printStates(){
		System.out.println("States and Counts");
		System.out.print("States = {");
		for(int i=0; i<keyStates.length; i++){
			System.out.print(keyStates[i]+", ");
		}
		System.out.println(" }");
	}
}
