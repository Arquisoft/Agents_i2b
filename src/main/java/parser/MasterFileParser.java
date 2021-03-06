package parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface MasterFileParser {
	
	public String getKindNameOf(int kind) throws FileNotFoundException, IOException;
	public int getKindCodeOf(String kindName) throws FileNotFoundException, IOException;
	public List<String> getKindNames() throws IOException;
}
