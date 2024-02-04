/*
* Vincent Hsiao and Kasey Gallegos
* CIS 255 - October 8, 2023
* Shape.java
*
* This is the parent abstract class for every shape; includes variables like PI, shapeType, typeDescription, and dimensions that every shape has
*
* */
import java.lang.Math;
public abstract class Shape {
    /*
    PI = 3.14159...
    shapeType: Type of Shape in String (ex: "cube", "square")
    typeDescription: Short description of said shape type
    dimensions: Dimensions of Shape (2 or 3)
     */
    public double PI = Math.PI;
    public String shapeType;
    public String typeDescription;
    int dimensions;
    public Shape(String shapeType, String typeDescription, int dimensions){
        this.shapeType = shapeType;
        this.typeDescription = typeDescription;
        this.dimensions = dimensions;
    }

    public String getShapeType() {
        return shapeType;
    }
    public int getDimensions(){
        return dimensions;
    }
    public String getDescription(){
        return typeDescription;
    }
    // abstract class implemented in class or child abstract class
    public abstract double area();

    public String round(double n, int places){
        return String.format("%." + places + "f", n);
    }

    public void setTypeDescription(String typeDescription){
        this.typeDescription = typeDescription;
    }

    // Checks equality by comparing shape type and area
    public boolean equals(Shape shape2){
        if(this.getShapeType().equals(shape2.getShapeType()) && this != shape2){
            if(this.area() == shape2.area()){
                return true;
            }
        }
        // put code here - empty
        return false;
    }

}
