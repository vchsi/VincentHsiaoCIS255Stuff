// cylinder.java - extends ThreeDShape, shape
public class Cylinder extends ThreeDShape{
    // height and radius
    int height;
    int radius;
    public Cylinder(int radius, int height){
        super(new Circle(radius), "cylinder", "A three-dimensional shape with a circular base and a set height");
        this.height = height;
        this.radius = radius;
    }
    public double volume(){
        return super.getBase().area() * height;
    }
    public double area(){
        // 2 pi r^2 + 2pi r h
        return (2 * super.getBase().area()) + (super.getBase().perimeter() * height);
    }

    @Override
    public String toString(){
        return "Cylinder with radius " + radius + " and height " + height;
    }

    public boolean isTopOrBottom(TwoDShape baseP) {
        if(baseP.getShapeType().equals("circle")){
            if(baseP.area() == this.getBase().area()){
                return true;
            }
        }
        return false;
    }
}
