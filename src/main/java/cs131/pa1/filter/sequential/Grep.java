/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;
import java.io.IOException;

import cs131.pa1.filter.Message;


public class Grep extends SequentialFilter {
	private String word; 
	
	
	@Override
	
	/**
	 *
	 * @return returns the line if it contains the key word and if not return null
	 * 
	 */
	public String processLine(String line) {
		if(line.contains(word)) { 
			return line; 
		}
		else { 
			return null;
		}
	}
	
	/**
	 * 
	 * 
	 * @return retunrs null if error and adds grepped words to output if not 
	 * 
	 * similar to base process method
	 */
	public void process(){
		String error = "grep" + " " +  word;
		if(input == null) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter(error));
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
	 * creates Grep object 
	 * 
	 * @param word which will be grepped
	 */
	public Grep(String word) { 
		this.word = word;
	}

	
	
}