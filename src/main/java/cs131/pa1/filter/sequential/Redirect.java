/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Filter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;


import cs131.pa1.filter.Message;

public class Redirect extends SequentialFilter {
	
	/**
	 * 
	 * Constructs rewrite, toRed, printer, and cwd
	 */
	private File rewrite;
	private String toRed;
	private PrintStream printer;
	private String cwd = SequentialREPL.currentWorkingDirectory;;
	
	
	
	/**
	 * Creates a Redirect object 
	 * 
	 * @param toRed is where the reDirect should happen and the "printer" PrintStreams it onto the file 
	 * 
	 * 
	 */
	public Redirect(String toRed) { 
		this.toRed = toRed;
		String rewriteTo = cwd + Filter.FILE_SEPARATOR + toRed;
		rewrite = new File(rewriteTo);
		
		
		try {
			this.printer = new PrintStream(rewrite);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * process method checks to see if input is null and if it is returns a error message
	 *
	 * No return except for error 
	 */
	public void process() {    //no output so change process
		if(this.input == null) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter(">" + " " + toRed));
			 return;
		}
		
		//if(this.output != null) { 
		//	System.out.print( Message.CANNOT_HAVE_OUTPUT.with_parameter(">" + toRed));
		//}
		while (!input.isEmpty()){
			String line = input.poll();
			String processedLine = processLine(line);
		}	
	}
	
	@Override
	public String processLine(String line) {
		printer.println(line);
		return null;
		
	} 
	
}