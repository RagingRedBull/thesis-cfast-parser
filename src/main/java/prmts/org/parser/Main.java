package prmts.org.parser;

import prmts.org.parser.model.AlarmLog;
import prmts.org.parser.model.Compartment;
import prmts.org.parser.utility.CompartmentHelper;
import prmts.org.parser.utility.LogHelper;
import prmts.org.parser.utility.RowDataHelper;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Compartment> compartments = null;
        List<AlarmLog> logs = null;
        LogHelper logHelper = new LogHelper();
        CompartmentHelper compartmentHelper = new CompartmentHelper();
        RowDataHelper rowDataHelper = new RowDataHelper();
        try {
            logs = logHelper.readFile(args[0]);
            compartments = compartmentHelper.parseCompartmentList(compartmentHelper.readFile(args[1]));
            rowDataHelper.generateCSV(logs,compartments);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
