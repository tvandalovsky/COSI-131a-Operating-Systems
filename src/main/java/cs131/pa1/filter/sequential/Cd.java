/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;
import java.io.File;
import java.util.List;

public class Cd extends SequentialFilter {
	
	/**
	 * @param cwd parameter for the changing and checker string 
	 */
	private String cwd; 
	private String checker;
	
	@Override 
	/**
	 * 
	 * processline is not used since process is overwritten
	 */
	protected String processLine(String line) { // are changing process so processLine is not needed
		// TODO Auto-generated method stub
		return null;
		
	}
	
	/**
	 * Creates CD object 
	 * 
	 * @param String toDo which will be checked then will change the cwd
	 * 
	 */
	public Cd (String toDo)  {
		checker = toDo;
		
		
		String tester = "cd " + toDo;
		cwd= SequentialREPL.currentWorkingDirectory; 
		
		if(toDo.equals(null) || toDo.equals("") ) {
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cd"));
		}
		
		if(toDo.equals("..")) {
			cwd = cwd.substring(0, cwd.lastIndexOf(Filter.FILE_SEPARATOR));
		}
		else if(toDo.equals(".")) {
			cwd = SequentialREPL.currentWorkingDirectory;
		}
		else { 
			String newCwd = cwd + Filter.FILE_SEPARATOR + toDo;
			File checker = new File(newCwd);
			 
			if(!checker.isDirectory()) {  //checks to see if new directory is a directory
				System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter(tester));
				cwd = SequentialREPL.currentWorkingDirectory;
				}
			else {
				cwd = newCwd; // sets currentWorkingDirectory as new currentWorkingDirectory
			}
		}
		
		
		 
	}
	
	@Override 
	
	/**
	 * Prints error if input is not null or sets currentWorkingDirectory to cwd
	 */
	public void process() { 
		if(input != null) { 
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("cd" + " " + checker));
			
		}	
		SequentialREPL.currentWorkingDirectory = cwd;	
	}
	
	
	
	
}
	
	
	
	
	
	
	
	

