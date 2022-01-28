package prmts.org.parser.utility;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import prmts.org.parser.model.AlarmLog;
import prmts.org.parser.model.Compartment;
import prmts.org.parser.model.RowData;
import prmts.org.parser.model.SensorType;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RowDataHelper {
    private final Path prmtsPath = Paths.get("C:\\prmts");

    public void generateCSV(List<AlarmLog> alarmLogs, List<Compartment> compartmentList) {
        List<RowData> rows = new ArrayList<>();
        try {
            Files.createDirectories(prmtsPath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        List<AlarmLog> smokeLogs = alarmLogs.stream()
                .filter(alarmLog -> alarmLog.getSensorType() == SensorType.SMOKE)
                .collect(Collectors.toList());
        List<AlarmLog> heatLogs = alarmLogs.stream()
                .filter(alarmLog -> alarmLog.getSensorType() == SensorType.HEAT)
                .collect(Collectors.toList());
        for (AlarmLog log: smokeLogs) {
            RowData row = new RowData(log, compartmentList.get(log.getCompartmentId()-1));
            rows.add(row);
        }

        generateSmokeLogsCSV(rows);
//        generateHeatLogsCSV(heatLogs);
    }

    private void generateSmokeLogsCSV(List<RowData> row) {
        Path path = Paths.get(prmtsPath + "\\smokeLogs.csv");
        try {
            Writer writer = new FileWriter(path.toString());
            StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withApplyQuotesToAll(false)
                    .build();

            sbc.write(row);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
