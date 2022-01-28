package prmts.org.parser.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class RowData {
    @CsvBindByPosition(position = 0)
    private String seconds; // seconds need to be int or at least without the 's' at the end.
    @CsvBindByPosition(position = 1)
    private int x_origin;
    @CsvBindByPosition(position = 2)
    private int y_origin;
    @CsvBindByPosition(position = 3)
    private int floor;

    /*
    * x_origin, y_origin, and floor should never change beyond the first read.
    *
    * Additional needed variables:
    * - int x_start
    * - int y_start
    * - int floor_start
    * - int x_end
    * - int y_end
    * - int floor_end
    *
    *  In order to log all start and end variables, you need to have the previous row data except on the first read.
    *
    *  On first read:
    *    x_start = x_origin
    *    y_start = y_origin
    *    floor_start = floor_origin
    *    x_end = x_origin + width
    *    y_end = y_origin + depth
    *    floor_end = floor_origin
    *
    *  On second read and beyond:
    *    x_start = { newX_origin < prevX_start ? newX_origin : prevX_start }
    *    y_start = { newY_origin < prevY_start ? newY_origin : prevY_start }
    *    floor_start = { newFloor_origin < prevFloor_start ? newFloor_origin : prevFloor_start }
    *    x_end = { newX_origin + width > prevX_end ? newX_origin + width : prevX_end }
    *    y_end = { newY_origin + depth > prevY_end ? newY_origin + depth : prevY_end }
    *    floor_end = { newFloor_origin > prevFloor_end ? newFloor_origin : prevFloor_end }
    * */

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
