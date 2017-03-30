import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanusha on 30/03/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger(Main.class);

        String path =  "testlog.log";

        try {
            List<Record> records = new ArrayList<Record>();
            ArrayList<RecordSorted> recordSorteds = new ArrayList<RecordSorted>();
            List<String> content =  Parser.readLinesFromFile(path);
            records = Parser.pushingRecords(content);
            recordSorteds = (ArrayList<RecordSorted>) Parser.sortRecord(records);
            Parser.calculate(recordSorteds);


        } catch (FileNotFoundException ex) {
            logger.error("File not found " + ex.getMessage().toString());
            ex.printStackTrace();
        }
        catch (IOException ex) {
            logger.error("Input/Output error " + ex.getMessage().toString());
            ex.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
