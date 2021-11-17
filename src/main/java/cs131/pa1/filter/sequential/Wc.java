/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;
//import java.io.File;
//import java.util.List;

public class Wc extends SequentialFilter {
	
	private int numLines;
	private int numWords; 
	private int numChars;
	
	
	/**
	 *Creates WC object with number of Lines, number of Words, and Number of Chars 
	 * 
	 */
	public Wc() { 
		
		numLines = 0;
		numWords = 0; 
		numChars = 0; 
		
	}
	
	@Override
	
	/**
	 * ProcessLine method looks at the individual line of the input and 
	 * incriments num of words, num of chars, and num of lines 
	 * 
	 * @param String line 
	 * @return num Lines x numWords y numChars z
	 */
	public String processLine(String line) {
		// TODO Auto-generated method stub

		for (String word: line.split(" ")) {
			if(word.length() > 0) { 
				numWords += 1;
			}
		}
		numLines++; 
		numChars += line.length(); //is the number of Unicode units (chars) in the String 
		
		if(this.isDone()) { 
			String wcString = numLines + " " + numWords + " " + numChars;
			return wcString;
		}
		 return null;
	}	
	
	/**
	 * process method checks to make sure the input is not null and if brings to output 0 0 0  or num Lines, num Words, and num Chars from processLine method
	 * 
	 * Does a similar action to base process method 
	 * @return null 
	 */
	public void process(){
		if(input == null) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("wc"));
			return;
		}
		if(input.isEmpty() && input != null) {
			output.add(numLines + " " + numWords + " " + numChars);
		}
		while (!input.isEmpty()){
			String line = input.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
			
		}
	}
	
	
}
