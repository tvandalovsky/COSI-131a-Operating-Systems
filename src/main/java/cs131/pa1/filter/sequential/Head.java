/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;
//import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class Head extends SequentialFilter {
	
	/**
	 * creates counter parameter
	 */
	private int counter; 
	
	
	@Override
	
	/**
	 * Returns line
	 * 
	 * @param line
	 * @return line
	*/
	public String processLine(String line) {
		return line;
		
	}
	
	
	
	/**
	 * checks to make sure input is not null and if it is return out of method
	 * 
	 * makes sure num line <= 10 and adds that to output 
	 * 
	 * 
	 * similar to parent process method 
	 */
	public void process(){
		if(input == null) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("head"));
			return;
		}
		
		while (!input.isEmpty()){
			if(counter != 0) {
				String line = input.poll();
				String processedLine = processLine(line);
				if (processedLine != null){
					output.add(processedLine);
				}
				counter--;
			}
			
			else {
				return;
			}
			
		}
	}
	
	
	/**
	 * Creates Head object 
	 */
	public Head() { 
		
		this.counter = 10; 
		
		
		
	}
	
}