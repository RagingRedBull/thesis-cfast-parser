package prmts.org.parser.model;

import com.opencsv.bean.CsvIgnore;

public class AlarmLog {
    private SensorType sensorType;
    private int seconds;
    private int compartmentId;

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getCompartmentId() {
        return compartmentId;
    }

    public void setCompartmentId(int compartmentId) {
        this.compartmentId = compartmentId;
    }

    @Override
    public String toString() {
        return "AlarmLog{" +
                "sensorType=" + sensorType + "\n" +
                "seconds='" + seconds + "\n" +
                "compartment='" + compartmentId + "\n" +
                "}\n";
    }
}
