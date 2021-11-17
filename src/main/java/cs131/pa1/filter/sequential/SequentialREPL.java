/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
//import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
//import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * The main implementation of the REPL loop (read-eval-print loop). It reads
 * commands from the user, parses them, executes them and displays the result
 * 
 * @author cs131a
 *
 */
public class SequentialREPL {
	/**
	 * the path of the current working directory
	 * and scanner  and hasSet of all possible commands 
	 */
	private static HashSet<String> commandSet = new HashSet<String>(Arrays.asList(new String[] {"pwd", "ls", "cd", "cat", "grep", "wc", "uniq", "head", "tail", ">"}));
	static String currentWorkingDirectory;
	private static Scanner scan;
	//public static Boolean runner; 

	/**
	 * The main method that will execute the REPL loop
	 * 
	 * @param args not used
	 * @throws IOException 
	 */

	public static void main(String[] args) throws IOException {
		boolean checker = true;
		System.out.print(Message.WELCOME);
		System.out.print(Message.NEWCOMMAND);
		currentWorkingDirectory = System.getProperty("user.dir");  // getting CurrentWorkingDirectory
		scan = new Scanner(System.in);
		
		while(scan.hasNextLine()) { // REPL Loop 
			String input = scan.nextLine(); 
			
			if(input.equals("exit")) {  //first check is if exit then quit 
				System.out.print(Message.GOODBYE);
				break;
			}
			//System.out.println(12);
			List<SequentialFilter> commander = SequentialCommandBuilder.createFiltersFromCommand(input);
			//System.out.println(13);
			if (commander != null) {
				Iterator<SequentialFilter> iter = commander.iterator();
				while(iter.hasNext()) { 
					SequentialFilter nextCommand = iter.next();
					if (nextCommand != null) { 
						nextCommand.process();
					}
					else { 
						checker = false;
					}
				}
				
				if(checker == true) {
					int commanderIndex = commander.size() -1;
					Queue<String> output = commander.get(commanderIndex).output;
					while(!output.isEmpty()) {
						String finalSystem = output.poll();
						System.out.println(finalSystem);
						
					}
				}
			}
			
				
			
			//String input = scan.nextLine(); 
			System.out.print(Message.NEWCOMMAND);
		}
		
		scan.close();
		
	}

}
