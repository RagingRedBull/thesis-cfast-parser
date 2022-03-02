package prmts.org.parser.utility;

import prmts.org.parser.model.AlarmLog;
import prmts.org.parser.model.Compartment;
import prmts.org.parser.model.SensorType;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CompartmentHelper {
    public List<String> readFile(String fileLocation) throws IOException {
        List<String> compartmentsToParseList = new ArrayList<>();
        StringBuilder compartmentPartial = new StringBuilder();
        String line;
        Path path = Paths.get(fileLocation);
        BufferedReader reader = Files.newBufferedReader(path);
        while ((line = reader.readLine()) != null && ! line.contains("!! Wall Vents")) {
            if (line.contains("!! Scenario") || line.contains("!! Material") || line.contains(",  ")
            || line.contains("&HEAD") || line.contains("&TIME") || line.contains("&INIT") || line.contains("&MATL")
            || line.contains("CONDUCTIVITY")) {
                continue;
            }
            if(line.contains("!! Compartments")){
                compartmentPartial.append("\n");
                continue;
            }
            compartmentPartial.append(line);
            if (line.contains("/")) {
                compartmentPartial.append("\n");
                compartmentsToParseList.add(compartmentPartial.toString().trim());
                compartmentPartial = new StringBuilder();
            }
        }
        return compartmentsToParseList;
    }

    public List<Compartment> parseCompartmentList(List<String> compartmentsToParseList) {
        List<Compartment> compartmentList = new ArrayList<>();
        for (String line: compartmentsToParseList) {
            Compartment compartment = new Compartment();
            String[] dimensions = line.substring(22,54).split(" ");
            compartment.setDepth(Integer.parseInt(dimensions[2]));
            compartment.setHeight(Integer.parseInt(dimensions[5]));
            compartment.setWidth(Integer.parseInt(dimensions[8]));
            String[] origins = ((line.split("ORIGIN = ")[1]).split("GRID")[0]).split(",");
            compartment.setX_origin(Integer.parseInt(origins[0].trim()));
            compartment.setY_origin(Integer.parseInt(origins[1].trim()));
            compartment.setZ_origin(Integer.parseInt(origins[2].trim()));
            compartmentList.add(compartment);
        }
        return compartmentList;
    }
}
