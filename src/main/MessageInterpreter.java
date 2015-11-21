package main;

import java.util.ArrayList;
import java.util.HashMap;

import actions.Command;
import actions.Receiver;
import inference.Classifier;
import inference.Inferrer;
import twitter4j.DirectMessage;

public class MessageInterpreter {
	
	/*
	 * Direct Command Format
	 * HomeTweetie [Action] [Object] [Settings]
	 * [Actions] = get, set, status
	 * [Object] = Temperature, Lights, Webcam, Motion
	 * [Settings] = on, off
	 */
	private String text = "";
	private String[] partitions = null;
	private String result = "Not a Command";
	
	private HashMap<String, HashMap<String, HashMap<String, Command>>> objects = new HashMap<String, HashMap<String, HashMap<String, Command>>>();
	private HashMap<String, HashMap<String, Command>> action = new HashMap<String, HashMap<String, Command>>();
	private HashMap<String, Command> setting = new HashMap<String, Command>();
	
	private ArrayList<Command> commands = null;
	private Command command = null;
	
	private ArrayList<Inferrer> inferrers = new ArrayList<Inferrer>();
	private ArrayList<Classifier> classifiers = new ArrayList<Classifier>();
	private String commandFolder = "commands/";
	
	
	public MessageInterpreter(ArrayList<Command> input){
		commands = input;
		System.out.println("Starting to create ArrayLists for commands");
		for(int i =0; i<commands.size(); i++){
			inferrers.add(new Inferrer(commandFolder+commands.get(i).getCommandName()+".json"));
			classifiers.add(new Classifier(inferrers.get(i), commands.get(i)));
			inferrers.get(i).initialProbability(null, false);
		}
		System.out.println("Finished create ArrayLists for commands");
		
		for(int i=0; i<commands.size(); i++){
			Command temp = commands.get(i);
			String[] object = temp.getName();
			if(objects.containsKey(object[0])){
				if(objects.get(object[0]).containsKey(object[1])){
					objects.get(object[0]).get(object[1]).put(object[2],temp);
				}
				else{
					setting = new HashMap<String, Command>();
					setting.put(object[2], temp);
					objects.get(object[0]).put(object[1], setting);
				}
				
			}
			else{
				setting = new HashMap<String, Command>();
				setting.put(object[2], temp);
				action = new HashMap<String, HashMap<String, Command>>();
				action.put(object[1], setting);
				objects.put(object[0], action);
				
			}
		}
	}
	public boolean validCommand(String message){
		boolean result = false;
		for(int i=0; i< classifiers.size(); i++){
			System.out.println("Trying Command "+commands.get(i).getCommandName());
			result = classifiers.get(i).isValidCommand(message, false);
			System.out.println("Correctly Inferred the Command");
			if(result){
				System.out.println("Executing the Command");
				commands.get(i).execute();
				result = true;
				break;
			}
		}
		return result;
	}
	public String getType(DirectMessage dm){
		text = dm.getText().toLowerCase();
		String value = null;
		if(text.startsWith("hometweetie")){
			partitions = text.split(" ");
			if(partitions.length > 4){
				if(validCommand(dm.getText())){
					return "Valid Command";
				}
				else{
					return "Invalid Command";
				}
			}
			else{
				if(partitions.length == 4){
					value = partitions[3];
				}
				if(objects.containsKey(partitions[2])){
					action = objects.get(partitions[2]);
					if(action.containsKey(partitions[1])){
						setting = action.get(partitions[1]);
						command = setting.get(value);
						command.execute();
						return "Valid Command";
					}
					else{
						if(validCommand(dm.getText())){
							return "Valid Command";
						}
						else{
							return "Invalid Command";
						}
					}
				}
				else{
					if(validCommand(dm.getText())){
						return "Valid Command";
					}
					else{
						return "Invalid Command";
					}
				}
			}
			
		}
		else{
			System.out.println("Checking the Command since its not in correct format");
			if(validCommand(dm.getText())){
				return "Valid Command";
			}
			else{
				return "Invalid Command";
			}
		}
	}
	
	public String getType(String dm){
		text = dm.toLowerCase();
		String value = null;
		if(text.startsWith("hometweetie")){
			partitions = text.split(" ");
			if(partitions.length > 4){
				return "Invalid Command";
			}
			else{
				if(partitions.length == 4){
					value = partitions[3];
				}
				if(objects.containsKey(partitions[2])){
					action = objects.get(partitions[2]);
					if(action.containsKey(partitions[1])){
						setting = action.get(partitions[1]);
						command = setting.get(value);
						command.execute();
						return "Valid Command";
					}
					else{
						return "Invalid Object";
					}
				}
				else{
					return "Invalid Action";
				}
			}
			
		}
		
		return result;
	}
	
	public void printObjects(){
		System.out.println(objects.toString());
	}

}
