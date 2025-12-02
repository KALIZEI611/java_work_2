package HW_Module_08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ShapeCalculatorTest {

    private Triangle triangleBaseHeight;
    private Triangle triangleThreeSides;
    private Rectangle rectangle;
    private Square square;
    private Rhombus rhombusDiagonals;
    private Rhombus rhombusSideHeight;

    @BeforeEach
    void setUp() {
        triangleBaseHeight = new Triangle(10, 5);
        triangleThreeSides = new Triangle(3, 4, 5);
        rectangle = new Rectangle(6, 4);
        square = new Square(5);
        rhombusDiagonals = new Rhombus(8, 6);
        rhombusSideHeight = new Rhombus(5, 4, true);
    }

    @Test
    void testTriangleBaseHeightArea() {
        assertEquals(25.0, triangleBaseHeight.calculateArea(), 0.001);
    }

    @Test
    void testTriangleThreeSidesArea() {
        assertEquals(6.0, triangleThreeSides.calculateArea(), 0.001);
    }

    @Test
    void testTriangleInvalidBaseHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Triangle(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(10, -1));
    }

    @Test
    void testTriangleInvalidSides() {
        assertThrows(IllegalArgumentException.class, () -> new Triangle(1, 1, 3));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(0, 4, 5));
    }

    @Test
    void testRectangleArea() {
        assertEquals(24.0, rectangle.calculateArea(), 0.001);
    }

    @Test
    void testRectangleInvalidDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(10, -2));
    }

    @Test
    void testSquareArea() {
        assertEquals(25.0, square.calculateArea(), 0.001);
    }

    @Test
    void testSquareInvalidSide() {
        assertThrows(IllegalArgumentException.class, () -> new Square(0));
        assertThrows(IllegalArgumentException.class, () -> new Square(-5));
    }

    @Test
    void testRhombusDiagonalsArea() {
        assertEquals(24.0, rhombusDiagonals.calculateArea(), 0.001);
    }

    @Test
    void testRhombusSideHeightArea() {
        assertEquals(20.0, rhombusSideHeight.calculateArea(), 0.001);
    }

    @Test
    void testRhombusInvalidDiagonals() {
        assertThrows(IllegalArgumentException.class, () -> new Rhombus(0, 6));
        assertThrows(IllegalArgumentException.class, () -> new Rhombus(8, -2));
    }

    @Test
    void testRhombusInvalidSideHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Rhombus(0, 4, true));
        assertThrows(IllegalArgumentException.class, () -> new Rhombus(5, -1, true));
    }

    @Test
    void testShapeFactory() {
        Triangle triangle = ShapeFactory.createTriangleByBaseHeight(10, 5);
        assertNotNull(triangle);
        assertEquals(25.0, triangle.calculateArea(), 0.001);

        Triangle triangle2 = ShapeFactory.createTriangleBySides(3, 4, 5);
        assertNotNull(triangle2);
        assertEquals(6.0, triangle2.calculateArea(), 0.001);

        Rectangle rect = ShapeFactory.createRectangle(6, 4);
        assertNotNull(rect);
        assertEquals(24.0, rect.calculateArea(), 0.001);

        Square sq = ShapeFactory.createSquare(5);
        assertNotNull(sq);
        assertEquals(25.0, sq.calculateArea(), 0.001);

        Rhombus rhombus1 = ShapeFactory.createRhombusByDiagonals(8, 6);
        assertNotNull(rhombus1);
        assertEquals(24.0, rhombus1.calculateArea(), 0.001);

        Rhombus rhombus2 = ShapeFactory.createRhombusBySideHeight(5, 4);
        assertNotNull(rhombus2);
        assertEquals(20.0, rhombus2.calculateArea(), 0.001);
    }

    @Test
    void testPolymorphism() {
        Shape[] shapes = {
                new Triangle(10, 5),
                new Rectangle(6, 4),
                new Square(5),
                new Rhombus(8, 6)
        };

        double[] expectedAreas = {25.0, 24.0, 25.0, 24.0};

        for (int i = 0; i < shapes.length; i++) {
            assertEquals(expectedAreas[i], shapes[i].calculateArea(), 0.001);
        }
    }
}