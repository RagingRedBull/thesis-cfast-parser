package prmts.org.parser.utility;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import prmts.org.parser.model.AlarmLog;
import prmts.org.parser.model.SensorType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogHelper {

    public List<AlarmLog> readFile(String fileLocation) throws IOException {
        List<AlarmLog> logs = new ArrayList<>();
        String line;
        Path path = Paths.get(fileLocation);
        BufferedReader reader = Files.newBufferedReader(path);
        while ((line = reader.readLine()) != null && !line.contains("Total execution time")) {
            if (line.contains("Alarm")) {
                String[] split = line.split(" ");
                AlarmLog log = new AlarmLog();
                if (split[0].equals("Smoke")) {
                    log.setSensorType(SensorType.SMOKE);
                } else {
                    log.setSensorType(SensorType.HEAT);
                }
                log.setSeconds(Integer.parseInt(split[7]));
                log.setCompartmentId(Integer.parseInt(split[11]));
                logs.add(log);
            }
        }
        return logs;
    }


}
