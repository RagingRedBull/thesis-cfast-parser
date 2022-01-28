package prmts.org.parser.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;

public class RowData {
    @CsvIgnore
    private int width;
    @CsvIgnore
    private int depth;
    @CsvIgnore
    private int height;
    @CsvBindByPosition(position = 0)
    private int seconds; // seconds need to be int or at least without the 's' at the end.
    @CsvBindByPosition(position = 1)
    private int xOrigin;
    @CsvBindByPosition(position = 2)
    private int yOrigin;
    @CsvBindByPosition(position = 3)
    private int floorOrigin;
    @CsvBindByPosition(position = 4)
    private int xStart;
    @CsvBindByPosition(position = 5)
    private int yStart;
    @CsvBindByPosition(position = 6)
    private int floorStart;
    @CsvBindByPosition(position = 7)
    private int xEnd;
    @CsvBindByPosition(position = 8)
    private int yEnd;
    @CsvBindByPosition(position = 9)
    private int floorEnd;
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
        this.xOrigin = compartment.getX_origin();
        this.yOrigin = compartment.getY_origin();
        this.floorOrigin = compartment.getZ_origin() > 0 ? (compartment.getZ_origin() / 3 ) : (compartment.getZ_origin());
        this.width = compartment.getWidth();
        this.depth = compartment.getDepth();
        this.height = compartment.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getxOrigin() {
        return xOrigin;
    }

    public void setxOrigin(int xOrigin) {
        this.xOrigin = xOrigin;
    }

    public int getyOrigin() {
        return yOrigin;
    }

    public void setyOrigin(int yOrigin) {
        this.yOrigin = yOrigin;
    }

    public int getFloorOrigin() {
        return floorOrigin;
    }

    public void setFloorOrigin(int floorOrigin) {
        this.floorOrigin = floorOrigin;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getFloorStart() {
        return floorStart;
    }

    public void setFloorStart(int floorStart) {
        this.floorStart = floorStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getyEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }

    public int getFloorEnd() {
        return floorEnd;
    }

    public void setFloorEnd(int floorEnd) {
        this.floorEnd = floorEnd;
    }
}
