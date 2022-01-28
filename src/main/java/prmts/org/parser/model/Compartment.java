package prmts.org.parser.model;

public class Compartment {
    private int depth;
    private int height;
    private int width;
    private int x_origin;
    private int y_origin;
    private int z_origin;

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    public int getZ_origin() {
        return z_origin;
    }

    public void setZ_origin(int z_origin) {
        this.z_origin = z_origin;
    }

    @Override
    public String toString() {
        return "Compartment{\n" +
                "depth=" + depth +
                "\n height=" + height +
                "\n width=" + width +
                "\n x_origin=" + x_origin +
                "\n y_origin=" + y_origin +
                "\n z_origin=" + z_origin +
                "}\n";
    }
}
