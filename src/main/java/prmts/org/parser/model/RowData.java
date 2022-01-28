package prmts.org.parser.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class RowData {
    @CsvBindByPosition(position = 0)
    private String seconds;
    @CsvBindByPosition(position = 1)
    private int x_origin;
    @CsvBindByPosition(position = 2)
    private int y_origin;
    @CsvBindByPosition(position = 3)
    private int floor;

    public RowData(AlarmLog log, Compartment compartment) {
        this.seconds = log.getSeconds();
        this.x_origin = compartment.getX_origin();
        this.y_origin = compartment.getY_origin();
        this.floor = compartment.getZ_origin() > 0 ? (compartment.getZ_origin() / 3 ) : (compartment.getZ_origin());
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public int getX_origin() {
        return x_origin;
    }

    public void setX_origin(int x_origin) {
        this.x_origin = x_origin;
    }

    public int getY_origin() {
        return y_origin;
    }

    public void setY_origin(int y_origin) {
        this.y_origin = y_origin;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
