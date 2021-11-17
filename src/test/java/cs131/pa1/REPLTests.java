package cs131.pa1;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialREPL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class REPLTests {

	@Test
	public void testExit() throws IOException {
		testInput("exit");
		SequentialREPL.main(null);
		assertOutput("");
	}

	@Test
	public void testNotACommand1() throws IOException {
		testInput("not-a-command\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "The command [not-a-command] was not recognized.\n");
	}

	@Test
	public void testNotACommand2() throws IOException {
		testInput("ls | gripe HELLO\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "The command [gripe HELLO] was not recognized.\n");
	}

	@Test
	public void testNotACommand3() throws IOException {
		testInput("cathello.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "The command [cathello.txt] was not recognized.\n");
	}

	@Test
	public void testNotACommand4() throws IOException {
		testInput("cdsrc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "The command [cdsrc] was not recognized.\n");
	}

	@Test
	public void testNotACommand5() throws IOException {
		testInput("pwd | grepunixish\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "The command [grepunixish] was not recognized.\n");
	}

	@Test
	public void testCanContinueAfterError1() throws IOException {
		testInput("cd dir1\n ls | gripe HELLO\nls | grep f1\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND.toString() + Message.NEWCOMMAND
				+ "The command [gripe HELLO] was not recognized.\n> f1.txt\n");
	}

	@Test
	public void testCanContinueAfterError2() throws IOException {
		testInput("cat fizz-buzz-100000.txt | grep 1 | wc\ncat fizz-buzz-10000.txt | grep 1 | wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.FILE_NOT_FOUND.with_parameter("cat fizz-buzz-100000.txt")
				+ "> 1931 1931 7555\n");
	}

	@Test
	public void testFileNotFound() throws IOException {
		testInput("cat doesnt-exist.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.FILE_NOT_FOUND.with_parameter("cat doesnt-exist.txt"));
	}

	@Test
	public void testDirectoryNotFound() throws IOException {
		testInput("cd mystery-dir\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.DIRECTORY_NOT_FOUND.with_parameter("cd mystery-dir"));
	}

	// ********** Input/Output Tests **********

	@Test
	public void testPwdCannotHaveInput() throws IOException {
		testInput("cat hello-world.txt | pwd\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.CANNOT_HAVE_INPUT.with_parameter("pwd"));
	}

	@Test
	public void testLsCannotHaveInput() throws IOException {
		testInput("cat hello-world.txt | ls\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.CANNOT_HAVE_INPUT.with_parameter("ls"));
	}

	@Test
	public void testCdCannotHaveInput() throws IOException {
		testInput("cat hello-world.txt | cd dir1\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.CANNOT_HAVE_INPUT.with_parameter("cd dir1"));
	}

	@Test
	public void testCdCannotHaveOutput1() throws IOException {
		testInput("cd dir1\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND.toString());
	}

	@Test
	public void testCdCannotHaveOutput2() throws IOException {
		testInput("cd dir1 | wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.CANNOT_HAVE_OUTPUT.with_parameter("cd dir1"));
	}

	@Test
	public void testCdRequiresParameter() throws IOException {
		testInput("cd\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_PARAMETER.with_parameter("cd"));
	}

	@Test
	public void testCatCannotHaveInput() throws IOException {
		testInput("pwd | cat hello-world.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.CANNOT_HAVE_INPUT.with_parameter("cat hello-world.txt"));
	}

	@Test
	public void testCatRequiresParameter1() throws IOException {
		testInput("cat\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_PARAMETER.with_parameter("cat"));
	}

	@Test
	public void testCatFileNotFound() throws IOException {
		testInput("cat iloveos-hello-world.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.FILE_NOT_FOUND.with_parameter("cat iloveos-hello-world.txt"));
	}

	/**
	 * Tests that cat works properly when users has cd into a directory. For
	 * example, if user cd's into dir1 then cat should handle filepaths relative to
	 * dir1. That is, if there's a file f1.txt in dir1, then cat f1.txt should
	 * produce the output of f1.txt. This is like
	 * {@link RedirectionTests#testDirectoryShiftedRedirection()}.
	 * 
	 * @author Chami Lamelas
	 */
	@Test
	public void testCatInDirectory() throws IOException {
		testInput("cd dir1\ncat f1.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "" + Message.NEWCOMMAND
				+ "FILE 1\nTHIS IS THE FIRST FILE.\nI HOPE YOU LIKE IT\n\n\nYOU DO????\n");
	}

	@Test
	public void testGrepRequiresInput() throws IOException {
		testInput("grep hahaha\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_INPUT.with_parameter("grep hahaha"));
	}

	@Test
	public void testGrepRequiresParameter() throws IOException {
		testInput("pwd | grep\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_PARAMETER.with_parameter("grep"));
	}

	@Test
	public void testWcRequiresInput() throws IOException {
		testInput("wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_INPUT.with_parameter("wc"));
	}

	/**
	 * Tests that user reports the appropriate error when you try to call head
	 * without any input
	 * 
	 * @author Chami Lamelas
	 */
	@Test
	public void testHeadRequiresInput() throws IOException {
		testInput("head\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_INPUT.with_parameter("head"));
	}

	/**
	 * Tests that user reports the appropriate error when you try to call tail
	 * without any input
	 * 
	 * @author Chami Lamelas
	 */
	@Test
	public void testTailRequiresInput() throws IOException {
		testInput("tail\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_INPUT.with_parameter("tail"));
	}

	/**
	 * Tests that user reports the appropriate error when you try to call uniq
	 * without any input
	 * 
	 * @author Chami Lamelas
	 */
	@Test
	public void testUniqRequiresInput() throws IOException {
		testInput("uniq\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_INPUT.with_parameter("uniq"));
	}

	@Test
	public void testRedirectionRequiresInput() throws IOException {
		testInput("> new-hello-world.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_INPUT.with_parameter("> new-hello-world.txt"));
	}

	@Test
	public void testRedirectionRequiresParameter() throws IOException {
		testInput("ls >\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_PARAMETER.with_parameter(">"));
	}

	@Test
	public void testRedirectionNoOutput1() throws IOException {
		testInput("cat hello-world.txt > new-hello-world.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND.toString());
		AllSequentialTests.destroyFile("new-hello-world.txt");
	}

	@Test
	public void testRedirectionNoOutput2() throws IOException {
		testInput("cat hello-world.txt > new-hello-world.txt|wc\nexit");
		SequentialREPL.main(null);
		assertOutput(
				Message.NEWCOMMAND.toString() + Message.CANNOT_HAVE_OUTPUT.with_parameter("> new-hello-world.txt"));
		AllSequentialTests.destroyFile("new-hello-world.txt");
	}

	private ByteArrayInputStream inContent;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	public void testInput(String s) {
		inContent = new ByteArrayInputStream(s.getBytes());
		System.setIn(inContent);
	}

	public void assertOutput(String expected) {
		AllSequentialTests.assertOutput(expected, outContent);
	}

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
		System.setIn(null);
		System.setOut(null);
		System.setErr(null);
	}
}
