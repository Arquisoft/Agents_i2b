package persistance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import repositories.CSVFileParser;

public class CSVParserTest {
	
	CSVFileParser parser;
	
	@Test
	public void getKindNameTest() throws FileNotFoundException, IOException {
		parser = new CSVFileParser("src/test/master_test.csv");
		assertEquals("Person", parser.getKindNameOf(1));
		assertEquals("Entity", parser.getKindNameOf(2));
		assertEquals("Sensor", parser.getKindNameOf(3));
		assertEquals(null, parser.getKindNameOf(0));
		assertEquals(null, parser.getKindNameOf(4));
	}
	
	@Test
	public void getKindCodeTest() throws FileNotFoundException, IOException {
		parser = new CSVFileParser("src/test/master_test.csv");
		assertEquals(1, parser.getKindCodeOf("Person"));
		assertEquals(2, parser.getKindCodeOf("Entity"));
		assertEquals(3, parser.getKindCodeOf("Sensor"));
		assertEquals(-1, parser.getKindCodeOf("Son"));
		assertEquals(-1, parser.getKindCodeOf(null));
	}
	
	@Test
	public void fileNotFoundTest() {
		parser = new CSVFileParser("src/test/not_a_file.csv");
		try {
			parser.getKindNameOf(1);
			assertTrue(false);
		} catch (FileNotFoundException fnfe) {
			assertTrue(true);
		} catch (IOException e) {
			assertTrue(false);
		}
		
		try {
			parser.getKindCodeOf("Person");
			assertTrue(false);
		} catch (FileNotFoundException fnfe) {
			assertTrue(true);
		} catch (IOException e) {
			assertTrue(false);
		}
	}

}