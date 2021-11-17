/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
//import java.util.Iterator;
import java.util.List;
//import java.util.Queue;
//import java.util.Stack;

import cs131.pa1.filter.Message;

import java.util.LinkedList;

/**
 * This class manages the parsing and execution of a command. It splits the raw
 * input into separated subcommands, creates subcommand filters, and links them
 * into a list.
 * 
 * @author cs131a
 *
 */


public class SequentialCommandBuilder {
	
	/**
	 *constructs hashset of all possible commands called commandSet
	 */
 	private static HashSet<String> commandSet = new HashSet<String>(Arrays.asList(new String[] {"pwd", "ls", "cd", "cat", "grep", "wc", "uniq", "head", "tail", ">"}));
	
	/**
	 * Creates and returns a list of filters from the specified command
	 * 
	 * @param command the command to create filters from
	 * @return the list of SequentialFilter that represent the specified command
	 * @throws IOException 
	 */
	public static List<SequentialFilter> createFiltersFromCommand(String command) {
		char[] redChecker = command.toCharArray();
		for(int i =0; i < redChecker.length; i++) {
			if(redChecker[i] == '>') {
				command = checkForRedirect(command, i).trim();
			}
		}
		
		//System.out.println("hello");
		List<SequentialFilter> listFilters = new LinkedList<>();
		//Queue<SequentialFilter> queueFilters2 = new LinkedList<>();
		
		while(command.length() > 0) {
			/**
			after finding the last filter and pushing it the list with determine from last to first... 
			adjust... removes the final filter to 
			 **/
			//Queuefilters.add(determineFinalFilter(command));
			listFilters.add(0, determineFinalFilter(command));  
			command = adjustCommandToRemoveFinalFilter(command);
			
		}
		
		//System.out.println("World");
		
		if(linkFilters(listFilters)) {	
			//System.out.println("Pass");
			return listFilters; 
		}
		else { 
			//System.out.println("Fail");
			return null;
		}
	} 

	/**
	 * Returns the filter that appears last in the specified command
	 * 
	 * @param command the command to search from
	 * @return the SequentialFilter that appears last in the specified command
	 * @throws IOException 
	 */
	private static SequentialFilter determineFinalFilter(String command)  {
		String[] piper = command.trim().split("\\|");
		int pipLength = piper.length-1;
		String finalFilter = piper[pipLength].trim();
		SequentialFilter filter = constructFilterFromSubCommand(finalFilter); 
		return filter;
	}

	/**
	 * Returns a string that contains the specified command without the final filter
	 * 
	 * @param command the command to parse and remove the final filter from
	 * @return the adjusted command that does not contain the final filter
	 */
	private static String adjustCommandToRemoveFinalFilter(String command) {
		String[] breaker = command.trim().split("\\|");
		int newLength = breaker.length -1;
		int counter = 0;
		String newCommand = "";
		
		if(breaker.length == 1) { 
			return newCommand;
		}
		
		while(counter < newLength) {
			newCommand += breaker[counter] + "|";
			counter++;
		}
		
		int newCommandLength = newCommand.length()-1;
		newCommand = newCommand.substring(0,newCommandLength).trim();
		
		return newCommand;
	}
	
	/**
	 * links the given filters with the order they appear in the list
	 * 
	 * @param filters the given filters to link
	 * @return true if the link was successful, false if there were errors
	 *         encountered. Any error should be displayed by using the Message enum.
	 *         
	 *         
	 *         
	 *         was unable to add way to display messages if error occurs 
	 */
	private static boolean linkFilters(List<SequentialFilter> filters) {
		
		int filterSize = filters.size() -1;
		int counter = 0; 
		while (counter < filterSize) {
			SequentialFilter current = filters.get(counter);
			if (current == null || filters.get(counter+1) == null){
				return false;
			}
			current.setNextFilter(filters.get(counter+1));
			
			counter ++;
		}
		SequentialFilter tail = filters.get(filterSize);
		if (tail != null) {
			tail.output = new LinkedList<String>();
			if (tail.output == null) {
				return false;
			}
			return true;
		}
		else {
			return false;
		}
		//SequentialFilter tail = null;
		//while(filters.peek() != null) {
			
		//	SequentialFilter current = filters.poll();
		//	if(filters.peek() == null) {
		//		tail = current;
		//	}
		//	else {
		//		current.setNextFilter(filters.peek());
		//	}
			
		//	counter ++;
			
		//}
		
		//if (tail != null) {
		//		tail.output = new LinkedList<String>();
		//		if (tail.output == null) {
		//			return false;
		//		}
		//		return true;
		//	}
		//	else {
		//		return false;
		//	}
		
	}
	

	/**
	 * Creates a single filter from the specified subCommand
	 * 
	 * @param subCommand the command to create a filter from
	 * @return the SequentialFilter created from the given subCommand
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	private static SequentialFilter constructFilterFromSubCommand(String subCommand){
		String[] indSubCom = subCommand.split("\\s", -1); 
		String read = indSubCom[0];

		
		if(!commandSet.contains(read)) { 
			String base = "";
			for(String s : indSubCom) {
				base += s + " ";
			}
			System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(base));
			return null;
		}
		
		
		switch(read) {
		case "pwd": //completed
			return new Pwd();
			//break;
		case "ls": //completed
			return new Ls();
			//break;
		case "cd": //completed
			if( indSubCom.length < 2 ) {
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cd"));
				return null;
			}
			else {
				return new Cd(indSubCom[1]);
			}
			//break; 
		case "cat": // completed
			if(indSubCom.length > 1) { 
				
			}
			try {
				return new Cat(indSubCom); //completed ish
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.print(Message.FILE_NOT_FOUND.with_parameter(indSubCom[0] + " " + indSubCom[1]));
				return null;
				//e.printStackTrace();
			}
			//break;
		case "grep": //completed
			if(indSubCom.length < 2 ) {
				System.out.print( Message.REQUIRES_PARAMETER.with_parameter("grep"));
				return null;
			}
			return new Grep(indSubCom[1]);
			//break;
		case "wc": //completed
			return new Wc(); 
			//break; 
		case "uniq": //completed
			return new Uniq();
			//break; 
		case "head": //completed
			return new Head();
			//break;
		case "tail":  //completed
			//return new Tail();
			return new Tail();
			//break;
		case ">":  //completed
			if(indSubCom.length < 2 ) {
				System.out.print( Message.REQUIRES_PARAMETER.with_parameter(">"));
				return null;
			}
			return new Redirect(indSubCom[1]);
			
		default: //no default case needed
			String base = "";
			for(String s : indSubCom) {
				base += s;
			}
			System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(base));
			return null;
		}
	}
		
	/**
	 * Helper method to put pipes before >
	 * 
	 * @param String command and index of >
	 * @return String that puts pipe before >
	 */
	public static String checkForRedirect(String command, int index) { //inserts pip at index of > 
		String preRedirect = command.substring(0, index);   
		String postRedirect = command.substring(index);
		String newPipeAdded = (preRedirect + "|" + postRedirect).trim(); 
		return newPipeAdded;
	}

}
