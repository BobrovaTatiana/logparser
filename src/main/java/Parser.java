import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Tanusha on 30/03/2017.
 */
public class Parser {
    private static Logger logger = Logger.getLogger(Parser.class);

    public static String openReadFile(String path) throws IOException {
        String dataFile = new String();
        FileInputStream file = new FileInputStream(path);
        byte[] buffer = new byte[file.available()];
        file.read(buffer, 0, file.available());
        dataFile = new String(buffer);
        return dataFile;
    }

    public static List<String> readLinesFromFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        //String [] linesAsArray = lines.toArray(new String[lines.size()]);
        return lines;
    }

    public static List<Record> pushingRecords(List<String> content) throws ParseException {
        //List<String> content =  Parser.readLinesFromFile(path);
        List<Record> records = new ArrayList<Record>();

        for (String item: content) {
            String[] arrrayTemp = item.trim().split(" ");

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS");
            Date date = format.parse(arrrayTemp[0]);

            Pattern pattern = Pattern.compile(":");
            String[] idAndMethod = pattern.split(arrrayTemp[5]);

            int id = Integer.parseInt((String) idAndMethod[1].subSequence(0, idAndMethod[1].length()-1));

            records.add(new Record(date, arrrayTemp[3], idAndMethod[0].substring(1), id));

        }
        return records;
    }

    public static List<RecordSorted> sortRecord(List<Record> records) throws NoSuchFieldException {
        List<RecordSorted> recordSorteds = new ArrayList<RecordSorted>();
        ArrayList<Record> recordsEntry = new ArrayList<Record>();
        ArrayList<Record> recordsExit = new ArrayList<Record>();

        for (Record item: records) {
            if (item.getType().equals("entry")) {
                recordsEntry.add(item);
            } else {
                recordsExit.add(item);
            }}

            Collections.sort(recordsEntry);
            Collections.sort(recordsExit);


        for (int i = 0; i < (recordsExit.size()-1); i++) {
            Boolean is_break = false;

            if ((recordsExit.get(i).getMethod().equals(recordsEntry.get(i).getMethod())) &&
                    (recordsExit.get(i).getId() != recordsEntry.get(i).getId()) && is_break==false){
                recordsEntry.remove(i);
                is_break = true;
            }
            recordsEntry.trimToSize();
            is_break = false;
        }


        for (int i = 0; i < (recordsExit.size()-1); i++) {
            long time =  (long) (recordsExit.get(i).getTimeR().getTime() - recordsEntry.get(i).getTimeR().getTime())/1000;
            recordSorteds.add(new RecordSorted(recordsEntry.get(i).getMethod(), recordsEntry.get(i).getId(), time, false));
        }

//            String tempMethod = "";
//            int tempId=0;
//            if (item.getType().equals("entry")) {
//                tempMethod = item.getMethod();
//                tempId = item.getId();
//            }
//
//            for (Record element: records) {
//                if ((item.getType().equals("exit")) && (element.getMethod()== tempMethod) &&
//                        (element.getId()==tempId)){
//                    int time = (int) (element.getTime().getSeconds() - item.getTime().getSeconds());
//                    recordSorteds.add(new RecordSorted(tempMethod, tempId, time));
//                }
//            }



        return recordSorteds;
    }

    public static void calculate(ArrayList<RecordSorted> recordSorteds){


        for (RecordSorted item: recordSorteds) {
        if (!item.isIs_checked()) {
            String tempMethod = item.getMethod();
            int min = (int) item.getTime();
            int max = (int) item.getTime();
            int max_id = 0;
            int count = 0;
            long times = 0;

            for (int i = 0; i < recordSorteds.size(); i++) {

                if ((recordSorteds.get(i).getMethod().equals(tempMethod))) {
                    if (recordSorteds.get(i).getTime()>max) {
                        max = (int) recordSorteds.get(i).getTime();
                        max_id = recordSorteds.get(i).getId();
                    }
                    if (recordSorteds.get(i).getTime()<min) min = (int) recordSorteds.get(i).getTime();
                    count++;
                    times = times + recordSorteds.get(i).getTime();
                    recordSorteds.get(i).setIs_checked(true);
                }
            }
            int avg = (int) (times/count);
            System.out.println("OperationsImpl:" + tempMethod + " min " + min + ", max " + max + ", avg " + avg +
            ", max id " + max_id + ", count " + count);
        } }
    }

}

//    Имя метода, минимальное время, максимальное время, среднее время, количество вызовов, ID самого долгого вызова
//        Если в лог-файле отсутствует запись о выходе из метода, игнорируем вызов.
//        Формат вывода должен быть такой:
//
//
//        OperationsImpl:getData min 123, max 846, avg 315, max id 22, count 333
