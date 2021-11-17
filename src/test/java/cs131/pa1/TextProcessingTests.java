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

public class TextProcessingTests {

	// Tests for cat command
	@Test
	public void testCat() throws IOException {
		testInput("cat hello-world.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "hello\nworld\n");
	}

	@Test
	public void testCatLargerFile() throws IOException {
		testInput("cat ascii.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + " \n!\n\"\n#\n$\n%\n&\n'\n(\n)\n*\n+\n,"
				+ "\n-\n.\n/\n0\n1\n2\n3\n4\n5\n6\n7\n8\n9\n:\n;\n<\n=\n?\n@\nA\n"
				+ "B\nC\nD\nE\nF\nG\nH\nI\nJ\nK\nL\nM\nN\nO\nP\nQ\nR\nS\nT\nU\nV\n"
				+ "W\nX\nY\nZ\n[\n\\\n]\n^\n_\n`\na\nb\nc\nd\ne\nf\ng\nh\ni\nj\n"
				+ "k\nl\nm\nn\no\np\nq\nr\ns\nt\nu\nv\nw\nx\ny\nz\n{\n}\n~\n");
	}

	// Test for redirection command
	@Test
	public void testReadWrittenFile() throws IOException {
		testInput("cat ascii.txt > ascii10.txt\ncat ascii10.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(
				Message.NEWCOMMAND.toString() + Message.NEWCOMMAND.toString() + " \n!\n\"\n#\n$\n%\n&\n'\n(\n)\n*\n+\n,"
						+ "\n-\n.\n/\n0\n1\n2\n3\n4\n5\n6\n7\n8\n9\n:\n;\n<\n=\n?\n@\nA\n"
						+ "B\nC\nD\nE\nF\nG\nH\nI\nJ\nK\nL\nM\nN\nO\nP\nQ\nR\nS\nT\nU\nV\n"
						+ "W\nX\nY\nZ\n[\n\\\n]\n^\n_\n`\na\nb\nc\nd\ne\nf\ng\nh\ni\nj\n"
						+ "k\nl\nm\nn\no\np\nq\nr\ns\nt\nu\nv\nw\nx\ny\nz\n{\n}\n~\n");
		AllSequentialTests.destroyFile("ascii10.txt");
	}

	// Basic tests for grep command
	@Test
	public void testGrep() throws IOException {
		testInput("cat fizz-buzz-10000.txt | grep 111\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "1111\n1112\n1114\n1117\n1118\n2111\n4111\n5111\n7111\n8111\n");
	}

	@Test
	public void testGrepCaseSensitive() throws IOException {
		testInput("cat ascii.txt | grep a\ncat ascii.txt | grep A\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "a\n" + Message.NEWCOMMAND + "A\n");
	}

	@Test
	public void testGrepSpecialCharacter() throws IOException {
		testInput("cat ascii.txt | grep -\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "-\n");
	}

	// Basic test for wc (word count)

	@Test
	public void testWcFizzBuzz() throws IOException {
		testInput("cat fizz-buzz-10000.txt | wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "10001 10001 42081\n");
	}

	@Test
	public void testWcAscii() throws IOException {
		testInput("cat ascii.txt | wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "93 92 93\n");
	}

	@Test
	public void testWcEmpty() throws IOException {
		testInput("cat empty.txt | wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "0 0 0\n");
	}

	// Basic test for uniq
	@Test
	public void testUniqSame() throws IOException {
		testInput("cat hello-world.txt | uniq\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "hello\nworld\n");
	}

	@Test
	public void testUniqFizzBuzz10() throws IOException {
		testInput("cat fizz-buzz-10.txt | uniq\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "FizzBuzz\n1\n2\nFizz\n4\nBuzz\n7\n8\n");
	}

	// Tests for head command

	/**
	 * Tests the behavior of head when provided an empty input - it should produce
	 * nothing
	 * 
	 * @author Chami Lamelas
	 */
	@Test
	public void testHeadEmpty() throws IOException {
		testInput("cat empty.txt | head\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "");
	}

	/**
	 * Tests the behavior of head when provided an input with < 10 lines - it should
	 * produce the entire input
	 * 
	 * @author ChamiLamelas
	 */
	@Test
	public void testHeadLessThan10() throws IOException {
		testInput("cat hello-world.txt | head\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "hello\nworld\n");
	}

	/**
	 * Tests the behavior of head when provided an input with exactly 10 lines -
	 * again it should produce the entire input
	 * 
	 * @author Chami Lamelas
	 */
	@Test
	public void testHead10() throws IOException {
		testInput("cat fizz-buzz-9.txt | head\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "FizzBuzz\n1\n2\nFizz\n4\nBuzz\nFizz\n7\n8\nFizz\n");
	}

	/**
	 * Tests the behavior of head when provided an input with 100 lines - it should
	 * produce the first 10 lines
	 * 
	 * @author Chami Lamelas
	 */
	@Test
	public void testHead100() throws IOException {
		testInput("cat fizz-buzz-100.txt | head\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "FizzBuzz\n1\n2\nFizz\n4\nBuzz\nFizz\n7\n8\nFizz\n");
	}

	// Tests for tail command
	
	/**
	 * Tests the behavior of tail when provided an empty input - it should produce
	 * nothing
	 * 
	 * @author Chami Lamelas
	 */
	@Test
	public void testTailEmpty() throws IOException {
		testInput("cat empty.txt | tail\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "");
	}

	/**
	 * Tests the behavior of tail when provided an input with < 10 lines - it should
	 * produce the entire input
	 * 
	 * @author ChamiLamelas
	 */
	@Test
	public void testTailLessThan10() throws IOException {
		testInput("cat hello-world.txt | tail\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "hello\nworld\n");
	}

	/**
	 * Tests the behavior of tail when provided an input with exactly 10 lines -
	 * again it should produce the entire input
	 * 
	 * @author Chami Lamelas
	 */
	@Test
	public void testTail10() throws IOException {
		testInput("cat fizz-buzz-9.txt | tail\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "FizzBuzz\n1\n2\nFizz\n4\nBuzz\nFizz\n7\n8\nFizz\n");
	}

	/**
	 * Tests the behavior of tail when provided an input with 100 lines - it should
	 * produce the last 10 lines
	 * 
	 * @author Chami Lamelas
	 */
	@Test
	public void testTail100() throws IOException {
		testInput("cat fizz-buzz-100.txt | tail\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "91\n92\nFizz\n94\nBuzz\nFizz\n97\n98\nFizz\nBuzz\n");
	}

	// 3.14159265359
	@Test
	public void testUniqPi() throws IOException {
		testInput("cat pi.txt | uniq\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "Pi\nis\n3\n.\n1\n4\n5\n9\n2\n6\n");
	}

	// Boilerplate, standard across test case files.

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
