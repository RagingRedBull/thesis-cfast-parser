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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class RowDataHelper {
    private final Path prmtsPath = Paths.get("C:\\prmts");

    public void generateCSV(List<AlarmLog> alarmLogs, List<Compartment> compartmentList) {
        List<RowData> rows = new ArrayList<>();
        try {
            Files.createDirectories(prmtsPath);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        List<AlarmLog> smokeLogs = alarmLogs.stream()
                .filter(alarmLog -> alarmLog.getSensorType() == SensorType.SMOKE)
                .collect(Collectors.toList());
        List<AlarmLog> heatLogs = alarmLogs.stream()
                .filter(alarmLog -> alarmLog.getSensorType() == SensorType.HEAT)
                .collect(Collectors.toList());
        for (AlarmLog log : smokeLogs) {
            RowData row = new RowData(log, compartmentList.get(log.getCompartmentId() - 1));
            rows.add(row);
        }
        generateCsvFile(parseLogs(smokeLogs, compartmentList), false);
        generateCsvFile(parseLogs(heatLogs, compartmentList), true);
    }

    private void generateCsvFile(List<RowData> row, boolean isHeat) {
        LocalDateTime timeStamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd HH-mm-ss");
        Path path;
        if (isHeat) {
            path = Paths.get(prmtsPath + "\\" + timeStamp.format(formatter) + "-heatLogs.csv");
        } else {
            path = Paths.get(prmtsPath + "\\" + timeStamp.format(formatter) + "-smokeLogs.csv");
        }
        try {
            Writer writer = new FileWriter(path.toString());
            StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withApplyQuotesToAll(false)
                    .build();

            sbc.write(row);
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private List<RowData> parseLogs(List<AlarmLog> alarmLogs, List<Compartment> compartmentList) {
        int highestHeight = compartmentList.stream()
                .map(Compartment::getHeight)
                .mapToInt(i -> i)
                .max()
                .orElseThrow(NoSuchElementException::new);
        System.out.println(highestHeight);
        ArrayList<RowData> rowDatas = new ArrayList<>();
        for (int i = 0; i < alarmLogs.size(); i++) {
            AlarmLog log = alarmLogs.get(i);
            RowData row = new RowData(log, compartmentList.get(log.getCompartmentId() - 1));
            // Lowest Z --> 0
            // Highest height --> max
            if (i > 0) {
                RowData prevRowData = rowDatas.get(i - 1);
                row.setxStart(Math.min(row.getxOrigin(), prevRowData.getxStart()));
                row.setyStart(Math.min(row.getyOrigin(), prevRowData.getyStart()));
                row.setFloorStart(Math.min(row.getFloorOrigin(), prevRowData.getFloorStart()));
                row.setxEnd(Math.max(row.getxOrigin() + row.getWidth(), prevRowData.getxEnd()));
                row.setyEnd(Math.max(row.getyOrigin() + row.getDepth(), prevRowData.getyEnd()));
                row.setFloorEnd(Math.max(row.getFloorOrigin(), prevRowData.getFloorEnd()));
                row.setxOrigin(prevRowData.getxOrigin());
                row.setyOrigin(prevRowData.getyOrigin());
                row.setFloorOrigin(prevRowData.getFloorOrigin());
            } else {
                row.setxStart(row.getxOrigin());
                row.setyStart(row.getyOrigin());
                row.setFloorStart(row.getFloorOrigin());
                row.setxEnd(row.getxOrigin() + row.getWidth());
                row.setyEnd(row.getyOrigin() + row.getDepth());
                if (row.getFloorOrigin() != 0 && row.getHeight() != highestHeight) {
                    row.setFloorEnd(row.getFloorOrigin());
                }
            }
            rowDatas.add(row);
        }
        return rowDatas;
    }
}
