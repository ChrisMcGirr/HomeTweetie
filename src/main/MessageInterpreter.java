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
	 * [Object] = Temperature, Lights, Webcam
	 * [Settings] = on, off
	 */
	private String text = "";
	private String[] partitions = null;
	private String result = "Not a Command";
	
	/*Used to construct the command tree to determine if a message matches*/
	private HashMap<String, HashMap<String, HashMap<String, Command>>> objects = new HashMap<String, HashMap<String, HashMap<String, Command>>>();
	private HashMap<String, HashMap<String, Command>> action = new HashMap<String, HashMap<String, Command>>();
	private HashMap<String, Command> setting = new HashMap<String, Command>();
	
	/*Used to store the list of commands available*/
	private ArrayList<Command> commands = null;
	private Command command = null;
	
	/*Array of all the inferrers and classifiers associated with each command*/
	private ArrayList<Inferrer> inferrers = new ArrayList<Inferrer>();
	private ArrayList<Classifier> classifiers = new ArrayList<Classifier>();
	
	/*Folder where the JSON objects are stored for each command that contains the states and words*/
	private String commandFolder = "commands/";
	
	
	/*Constructor Method Builds the tree given the list of commands.
	 *This is later used to see if a message matches any of the commands in the
	 *list.  
	 */
	public MessageInterpreter(ArrayList<Command> input){
		commands = input;
		System.out.println("Starting to create ArrayLists for commands");
		
		/*Create inferrers and classifiers for each command to be used for natural language commands*/
		for(int i =0; i<commands.size(); i++){
			inferrers.add(new Inferrer(commandFolder+commands.get(i).getCommandName()+".json"));
			classifiers.add(new Classifier(inferrers.get(i), commands.get(i)));
			inferrers.get(i).initialProbability(null, false);
		}
		System.out.println("Finished create ArrayLists for commands");
		
		/*Construct the tree for direct command default pattern matching*/
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
	
	/*This methods searches the list of commands and checks whether this message matches
	 * any of the commands. If one does we return true and break the loop. 
	 * */
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
	/*
	 * Given a direct message check first to see if it first the default command pattern. If so traverse the
	 * tree until a leaf is reached at which point a valid signal is given back. If not the command does not match.
	 * If the message does not fit the pattern check to see if it is a natural language command and check each command
	 * in the list by calling the Valid Command Function
	 */
	public String getType(DirectMessage dm){
		text = dm.getText().toLowerCase();
		String value = null;
		if(text.startsWith("hometweetie")){
			partitions = text.split(" ");
			if(partitions.length > 4){
				if(validCommand(text)){
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
						if(validCommand(text)){
							return "Valid Command";
						}
						else{
							return "Invalid Command";
						}
					}
				}
				else{
					if(validCommand(text)){
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
			if(validCommand(text)){
				return "Valid Command";
			}
			else{
				return "Invalid Command";
			}
		}
	}
	/*
	 * Same as function above except it takes a string instead of a direct message object.
	 */
	public String getType(String dm){
		text = dm.toLowerCase();
		String value = null;
		if(text.startsWith("hometweetie")){
			partitions = text.split(" ");
			if(partitions.length > 4){
				if(validCommand(text)){
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
						if(validCommand(text)){
							return "Valid Command";
						}
						else{
							return "Invalid Command";
						}
					}
				}
				else{
					if(validCommand(text)){
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
			if(validCommand(text)){
				return "Valid Command";
			}
			else{
				return "Invalid Command";
			}
		}
	}
	
	public void printObjects(){
		System.out.println(objects.toString());
	}

}
