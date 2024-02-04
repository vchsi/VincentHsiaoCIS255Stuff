import java.util.*;

public class ShapeTester {

	public static void main(String[] args) {

		Rectangle rectangle1 = new Rectangle(3,4);
		Rectangle rectangle2 = new Rectangle(4,3);
		Rectangle rectangle3 = new Rectangle(5,6);
		Square square1 = new Square(2);
		Square square2 = new Square(4);
		Square square3 = new Square(4);
		Circle circle1 = new Circle(3);
		Circle circle2 = new Circle(5);

		Cylinder cylinder1 = new Cylinder(3, 5);
		Cylinder cylinder2 = new Cylinder(4, 6);
		Cylinder cylinder3 = new Cylinder(6, 4);

		Cube cube1 = new Cube(2);
		Cube cube2 = new Cube(3);

		List<Shape> shapeList = new ArrayList<>();
		shapeList.add(rectangle1);
		shapeList.add(rectangle2);
		shapeList.add(rectangle3);
		shapeList.add(square1);
		shapeList.add(square2);
		shapeList.add(square3);

		shapeList.add(circle1);
		shapeList.add(circle2);

		shapeList.add(cylinder1);
		shapeList.add(cylinder2);
		shapeList.add(cylinder3);

		shapeList.add(cube1);
		shapeList.add(cube2);

		System.out.println("*****PRINTING OUT THE TEXT REPRESENTATION, DESCRIPTION, AREA, AND PERIMETER/VOLUME OF EACH SHAPE");
		for(Shape shape : shapeList) {
			System.out.println(shape);
			System.out.println(shape.getDescription());
			System.out.println("\tArea: " + shape.area());
			
			// CODE MISSING: PRINT THE PERIMETER OF TWO-DIMENSIONAL SHAPES
			// if shape has two dimensions, typecast shape to TwoDShape to use TwoDShape-specific "perimeter" method
			if(shape instanceof TwoDShape){
				System.out.println("Perimeter of " + shape + ": " + ((TwoDShape) shape).perimeter());
			}
			// CODE MISSING: PRINT THE VOLUME OF THREE-DIMENSIONAL SHAPES
			// if shape has three dimensions, typecast shape to ThreeDShape to use ThreeDShape-specific "volume" method
			if(shape instanceof ThreeDShape){
				System.out.println("Volume of " + shape + ": " + ((ThreeDShape) shape).volume());
			}
			System.out.println("");
		}

		// .equals checks that the two shapes are not references, but have the same size (l and w can vary for rectangle)
		System.out.println("\n*****PRINTING ALL EQUAL, NON-ALIAS SHAPES");
		for(Shape firstShape : shapeList) {
			for(Shape secondShape : shapeList) {
				if(firstShape.equals(secondShape)) {
					System.out.printf("%s equals to \n\t%s\n", firstShape, secondShape);
				}
				
			}
		}

		// This tests both squares and circles to fit as bases of cylinders and cubes
		// typecasting should be fine because we checked that each object is of a specific second-level abstract class
		System.out.println("\n*****PRINTING ALL CUBE/SQUARE COMBINATIONS WHERE THE SQUARE IS A SIDE FOR THE CUBE");
		for(Shape firstShape : shapeList) {
			for(Shape secondShape : shapeList) {
				if((firstShape instanceof ThreeDShape) && (secondShape instanceof TwoDShape)){
					if(((ThreeDShape) firstShape).isTopOrBottom((TwoDShape) secondShape)){
						if(firstShape instanceof Cube && secondShape instanceof Square) {
							System.out.printf("Base found for: %s\n\t%s\n", firstShape, secondShape);
						}
					}
				}
				// CODE MISSING: TEST THE isTopOrBottom METHOD FOR SQUARE/CUBE COMBINATIONS. PRINT ANY MATCHES FOUND.

			}
		}

		System.out.println("\n*****PRINTING ALL COMBINATIONS OF TWO-DIMENSIONAL SHAPES THAT CAN FIT INSIDE ANOTHER");
		for(Shape firstShape : shapeList) {
			for(Shape secondShape : shapeList) {
				if(firstShape instanceof TwoDShape && secondShape instanceof TwoDShape){
					if(((TwoDShape) firstShape).perimeterCanFitInside((TwoDShape) secondShape)){
						System.out.println(firstShape + "\n\tcan fit inside: " + secondShape);
					}
				}
			}
		}
	}
}
