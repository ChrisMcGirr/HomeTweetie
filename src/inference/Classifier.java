package inference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import actions.Command;
import main.Weather;
import main.geoLocation;

public class Classifier {
	
	private Inferrer infer = null;
	private Command list = null;
	private String[] states = null;
	private Command command = null;
	
	public Classifier(Inferrer input, Command inputCommand){
		command = inputCommand;
		infer = input;
		states = infer.getStateNames();
	}
	
	/*For the heating command*/
	public boolean isValidCommand(String tweet, boolean inferring){
		infer.nextProbability(tweet);
		boolean result = false;
		int[] currentState = infer.getCurrentState();
		float[] currentProb = infer.getCurrentStateProb();
		int[] top = findTopStates(command.getStates().length, currentProb);
		float commandProb = 0;
		
		/*Check the number of top states matches the number needed by the command*/
		/*Might be a redundant piece of code. I'll check later*/
		String[] commandStates = command.getStates();
		int matchNum = 0;
		if(top.length >= commandStates.length ){
			for(int i=0; i<top.length; i++){
				for(int j=0; j<commandStates.length; j++){
					if(states[top[i]].equals(commandStates[j])){
						matchNum++;
					}
				}
			}
		}
		int currentStateNum = 0;
		if(matchNum==commandStates.length){
			for(int i=0; i< currentState.length; i++){
				if(currentState[i]>0){
					for(int j=0; j<top.length; j++){
						if(i == top[j]){
							currentStateNum++;
						}
					}
				}
			}
		}
		if(currentStateNum==commandStates.length){
			for(int i=0; i<commandStates.length; i++){
				if(commandProb==0){
					commandProb = currentProb[top[i]];
				}
				else{
					commandProb = commandProb+currentProb[top[i]];
				}
			}
			if(inferring){
				if(geoLocation.isAtWork()){
					if(Weather.isCold()){
						/*If its both cold and we're at work we give an equal probability*/
						System.out.println("Total Prob is "+ commandProb);
						if(commandProb > 0.5){
							result = true;
						}
					}
				}
			}
			else{
				if(commandProb >= 1){
					result = true;
				}
			}

		}
		return result;
	}
	
	/*Finds the top two states*/
	private int[] findTopStates(int commandStateNum, float[] currentProb){
		Map<Float, List<Integer>> top = new HashMap<Float, List<Integer>>();
		for(int i=0; i<currentProb.length; i++){
			if(top.containsKey(currentProb[i])){
				List<Integer> A = top.get(currentProb[i]);
				List<Integer> B = Arrays.asList(i);
				List<Integer> combined = new ArrayList<Integer>();
				combined.addAll(A);
				combined.addAll(B);
				top.put(currentProb[i], combined);
			}
			else{
				top.put(currentProb[i], Arrays.asList(i));
			}
			
		}
		/*Modified Map taken from http://www.mkyong.com/java/how-to-sort-a-map-in-java/*/
		Map<Float, List<Integer>> sorted = new TreeMap<Float, List<Integer>>(
				new Comparator<Float>() {

				@Override
				public int compare(Float o1, Float o2) {
					return o2.compareTo(o1);
				}

			});
		sorted.putAll(top);
		//printMap(sorted);
		
		List<Integer> list = new ArrayList<Integer>();
		Iterator queue = sorted.values().iterator();
		if(list.size() >= commandStateNum){
			for(int i=0; i<commandStateNum; i++){
				list.addAll((List<Integer>)queue.next());
			}
		}
		else{
			while(queue.hasNext()){
				list.addAll((List<Integer>)queue.next());
			}
		}

		Integer[] out = list.toArray(new Integer[list.size()]);
		int[] result = new int[out.length];
		for(int i=0; i<out.length; i++){
			result[i] = (int)out[i];
		}
		return result;
	}
	
	private void printMap(Map<Float, List<Integer>> input){
		for (Map.Entry<Float, List<Integer>> entry : input.entrySet()) {
			System.out.println("Key : " + entry.getKey() 
                                      + " Value : " + entry.getValue());
		}
	}

}
