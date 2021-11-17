/**
	 * Thomas Vandalovsky 
	 * OS
	 */

package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;
import java.io.File;
import java.util.List;

public class Pwd extends SequentialFilter {
	
	/**
	 * Creates pwd object
	 */
	public Pwd() { 
		
	}

	@Override
	/**
	 * returns null and is never called since process is over rode
	 */
	protected String processLine(String line) { // are changing process so processLine is not needed
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * makes sure input is null and adds to output the current working directory
	 * 
	 * if input is not null returns nothing and sends error
	 */
	public void process(){
		if(input == null) {
			//System.out.println(1233);
			this.output.add(SequentialREPL.currentWorkingDirectory);
			
		}
		else {
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("pwd"));
			return;
		}
		
	}
	
	
	
	
	
}