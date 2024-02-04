// ThreeDShape - extends Shape (provides a shell for all 3d shape classes)
public abstract class ThreeDShape extends Shape{
    // 2d shape base
    TwoDShape base;
    public ThreeDShape(TwoDShape base, String shapeType, String typeDescription){
        super(shapeType, typeDescription, 3);
        this.base = base;
    }
    public TwoDShape getBase(){
        return base;
    }

    public abstract double volume();

    // implement this method in Cylinder.java and Cube.java
    public abstract boolean isTopOrBottom(TwoDShape baseP);
}
