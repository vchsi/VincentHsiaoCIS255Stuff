// square.java - extends TwoDShape and Shape
public class Square extends TwoDShape{
    int side;
    public Square(int side) {
        super("square", "A 2 dimensional shape with four equal sides and four 90 degree angles");
        this.side = side;
    }
    public double perimeter(){
        return 4*side;
    }
    public int getSide() {
        return this.side;
    }
    public double area(){
        return side*side;
    }
    public String toString(){
        return String.format("Square with side length %d",getSide());
    }
}
