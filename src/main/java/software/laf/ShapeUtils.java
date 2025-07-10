package software.laf;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public final class ShapeUtils {

    private ShapeUtils() {
        // Prevent instantiation
    }

    public static Shape createRoundedRectangle(float x, float y, float width, float height, float arcSize) {
        if (arcSize <= 0) {
            return new Rectangle2D.Float(x, y, width, height);
        }

        // Create a circle if dimensions are equal and arc is large enough
        if (width == height && arcSize >= width) {
            return new Ellipse2D.Float(x, y, width, height);
        }

        return new RoundRectangle2D.Float(x, y, width, height, arcSize * 2, arcSize * 2);
    }

    public static Shape createOutlineShape(float x, float y, float width, float height,
            float outerArc, float lineWidth) {
        if (lineWidth <= 0 || width <= 0 || height <= 0) {
            return new Rectangle2D.Float(); // Empty shape
        }

        Path2D outline = new Path2D.Float(Path2D.WIND_EVEN_ODD);

        // Add outer shape
        outline.append(createRoundedRectangle(x, y, width, height, outerArc), false);

        // Add inner shape (to be subtracted)
        float innerX = x + lineWidth;
        float innerY = y + lineWidth;
        float innerWidth = width - (lineWidth * 2);
        float innerHeight = height - (lineWidth * 2);
        float innerArc = Math.max(0, outerArc - lineWidth);

        if (innerWidth > 0 && innerHeight > 0) {
            outline.append(createRoundedRectangle(innerX, innerY, innerWidth, innerHeight, innerArc), false);
        }

        return outline;
    }
}
