// circle.java - extends TwoDShape, Shape
import java.lang.Math;
public class Circle extends TwoDShape{
    int radius;
    public Circle(int radius){
        super("circle", "A 2-dimensional round shape with a set radius (distance from center to any point on circle)");
        this.radius = radius;
    }

    public double perimeter(){
        return Math.PI * 2 * radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }

    public String toString(){
        return "Circle with radius " + radius;
    }
}
