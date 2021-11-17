/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import cs131.pa1.filter.Message;

public class Tail extends SequentialFilter { 
	
	
	/**
	 * 
	 * @param Queue to store up to 10 lines and counter which is a const set at 10
	 */
	Queue<String> lastTen = new LinkedList<>();
	private int counter; 


	@Override
	
	
	/**
	 * Returns line
	 * 
	 * @param line
	 * @return line
	 */
	public String processLine(String line) {
		// TODO Auto-generated method stub
		return line;
	}
	
	/**
	 * returns empty return and prints error if input is equal to null
	 * 
	 * Brute forces last ten lines with a queue and everytime a new line gets added over 10 it is FIFO with the prev 10 lines
	 * 
	 * @return empty return
	 */
	public void process() {
		if(input == null) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("tail"));
			return;
		}
		
		while (!input.isEmpty()){
				String line = input.poll();
				String processedLine = processLine(line);
				if (processedLine != null){
					if(lastTen.size() >= counter) { 
						lastTen.remove();
					
					}
					lastTen.add(processedLine);
				}
		}
		
		for(String s: lastTen) {
			output.add(s);
		}
			
	}
		
	
	
	/**
	 * creates Tail object
	 */
	public Tail() { 
		this.counter = 10;
	}
	
}