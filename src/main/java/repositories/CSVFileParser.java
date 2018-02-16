package repositories;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;

/**
 * This class is in charge of parsing the master csv file
 * that contains the mapping between the different agent codes
 * and their name. It provides some methods to obtain the kind
 * name from an agent given the kind code, and also to obtain
 * the kind code given the kind name.
 * 
 * @author Alejandro González Hevia
 *
 */
@Repository
public class CSVFileParser implements MasterFileParser {
	
	private final static String MASTER_FILE_PATH = "master.csv";

	public String getKindNameOf(int kind) {
		Reader reader = null;
		
        try {
	        	reader = new FileReader(MASTER_FILE_PATH);
	        	Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
	        	for (CSVRecord record : records) {
	        		this.assertValidRecord(record);
	        		
	        	    int kindCode = Integer.valueOf(record.get(0));
	        	    if (kindCode == kind) return record.get(1);
	        	}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) { try { reader.close(); } catch (IOException e) { e.printStackTrace(); } }
        }
        
		return null;
	}

	public int getKindCodeOf(String kindName) {
		Reader reader = null;
		
        try {
	        	reader = new FileReader(MASTER_FILE_PATH);
	        	Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
	        	for (CSVRecord record : records) {
	        		this.assertValidRecord(record);
	        		
	        	    String name = record.get(0);
	        	    if (kindName.equals(name)) return Integer.valueOf(record.get(0));
	        	}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) { try { reader.close(); } catch (IOException e) { e.printStackTrace(); } }
        }
        
		return -1;
	}

	
	private void assertValidRecord(CSVRecord record) {
		assert (record.size() == 2) : "Invalid number of columns";
	}

}
