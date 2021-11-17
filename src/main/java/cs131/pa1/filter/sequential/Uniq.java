/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;
import java.io.IOException;
//import cs131.pa1.filter.Filter;
//import cs131.pa1.filter.Message;
import java.util.HashSet;

import cs131.pa1.filter.Message;



public class Uniq extends SequentialFilter {
	
	/**
	 * creates HashSet uniqWords which will store every uniq word 
	 */
	private HashSet<String> uniqWords;  
	
	@Override
	
	/**
	 * Returns uniq word if it is not in hashset
	 * 
	 * @param line
	 * @return line
	 */
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		if(uniqWords.contains(line)) { 
			return null;
		}
		else { 
			uniqWords.add(line);
			return line;
		}
	} 
	
	
	/**
	 * Adds uniq words to output 
	 * @return null if input is null
	 * 
	 * similar to base parent method 
	 */
	public void process(){
		if(input == null) { 
			System.out.print( Message.REQUIRES_INPUT.with_parameter("uniq"));
			return;
		}
		
		while (!input.isEmpty()){
			String line = input.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
		}	
	}
	

	/**
	 * Creates Uniq object 
	 */
	public Uniq() { 
		//super(); 
		uniqWords = new HashSet<String>();
		//if(input == null) { 
		//	System.out.print(Message.NEWCOMMAND + Message.REQUIRES_INPUT.with_parameter("uniq"));
		//}
	}
	
	
}