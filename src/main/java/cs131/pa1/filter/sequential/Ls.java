/**
	 * Thomas Vandalovsky 
	 * OS
	 */
package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;
import java.io.File;
import java.util.List;

public class Ls extends SequentialFilter {
	
	public Ls() { 
		super();
	}

	@Override
	protected String processLine(String line) { // are changing process so processLine is not needed
		// TODO Auto-generated method stub
		return null;
	}

	
	public void process() {
		//String currentWorkingDirectory = SequentialREPL.currentWorkingDirectory;
		if(input == null) {
			File currDir = new File(SequentialREPL.currentWorkingDirectory);
			String[] currDirChildren = currDir.list();
			
			int lsLength = currDirChildren.length;
			for(int i = 0; i < lsLength; i++) {
				output.add(currDirChildren[i]);
			}
		
		}
		else { 
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("ls"));
		}
		 
		 
	}
	
	
	
	
	
	
}
