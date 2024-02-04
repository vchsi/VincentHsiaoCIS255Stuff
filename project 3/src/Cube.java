// cube.java - extends 3dshape, shape
public class Cube extends ThreeDShape{
    // cube has same side length across every side
    int sidelength;
    public Cube(int sideLength){
        super(new Square(sideLength), "cube", "A 3d shape with a square as a its base. Six sides");
        this.sidelength = sideLength;
    }
    public double area(){
        return 6 * this.getBase().area();

    } public double volume(){
        return this.sidelength * this.sidelength * this.sidelength;
    }

    @Override
    public String toString(){
        return "Cube with side length " + this.sidelength;
    }

    public boolean isTopOrBottom(TwoDShape baseP){
        if(baseP.getShapeType().equals("square")){
            if(baseP.area() == super.getBase().area()){
                return true;
            }
        }
        return false;
    }
}
