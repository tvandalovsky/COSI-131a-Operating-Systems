/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Cat extends SequentialFilter {
	
	/**
	 * creates scanner parameter 
	 */
	private Scanner scan;
	@Override
	
	/**
	 * do not use this method since we are overriding process()
	 */
	public String processLine(String line) { // are changing process so processLine is not needed
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * creates Cat object 
	 *prints to terminal all of the lines in file by creating catFile and scanning it
	 * 
	 * @param Array of strings called commands
	 */
	public Cat(String[] command) throws FileNotFoundException {
		super();
	
		if(command[1] != null) {
			String catcm = command[1];
			File catFile = new File(catcm);
			scan = new Scanner(catFile);
		}
		else {
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cat"));
			return;
			
		}
		
	}
	
	/**
	 *process method 
	 *
	 * adds catted lines to output using scanner
	 */
	public void process() {
		
		//if(input == null) {
		//	System.out.print(Message.REQUIRES_INPUT.with_parameter("cat " + "helloword.txt"));
		//}
		while(scan.hasNextLine()) { 
			String catprinter = scan.nextLine();
			output.add(catprinter);
		}
	
	}
	
	
	
	
	
	
}
