// abstract class TwoDShape - extends Shape
public abstract class TwoDShape extends Shape{
    public TwoDShape(String shapeType, String typeDescription){
        super(shapeType,typeDescription, 2);

    }

    public abstract double perimeter();

    public abstract String toString();

    public boolean perimeterCanFitInside(TwoDShape outer){
        if(outer.perimeter() > this.perimeter()){
            return true;
        } return false;
    }

}
