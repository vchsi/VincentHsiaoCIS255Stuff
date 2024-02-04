// rectangle.java - extends TwoDShape and Shape
public class Rectangle extends TwoDShape{
    int width;
    int length;
    public Rectangle(int width, int length) {
        super("rectangle", "A 2 dimensional shape with four 90 degree angles and equal lengths and width sizes");
        this.width = width;
        this.length = length;
        }
    public double perimeter(){
        return 2*width + 2*length;
    }
    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
    public double area(){
        return length*width;
    }
    public String toString(){
        return String.format("Rectangle with width %d and length %d",getWidth(),getLength());
    }
}
