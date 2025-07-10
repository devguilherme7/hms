package devguilherme7.hms.ui.laf;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for high-quality graphics rendering for drawing operations.
 */
public final class GraphicsUtils {

    private GraphicsUtils() {
        // Prevent instantiation
    }

    /**
     * Context for managing graphics rendering state.
     * Implements {@link AutoCloseable} for use with try-with-resources
     */
    public static class RenderingContext implements AutoCloseable {

        private final Graphics2D graphics;
        private final Object[] oldHints;
        private final Color oldColor;
        private final Stroke oldStroke;
        private final Composite oldComposite;

        RenderingContext(Graphics2D graphics, Object[] oldHints, Color oldColor,
                Stroke oldStroke, Composite oldComposite) {
            this.graphics = graphics;
            this.oldHints = oldHints;
            this.oldColor = oldColor;
            this.oldStroke = oldStroke;
            this.oldComposite = oldComposite;
        }

        /**
         * Restores the graphics context to its original state.
         */
        @Override
        public void close() {
            if (oldHints != null && oldHints.length >= 2) {
                if (oldHints[0] != null) {
                    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldHints[0]);
                }
                if (oldHints[1] != null) {
                    graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, oldHints[1]);
                }
            }
            if (oldColor != null) graphics.setColor(oldColor);
            if (oldStroke != null) graphics.setStroke(oldStroke);
            if (oldComposite != null) graphics.setComposite(oldComposite);
        }
    }

    /**
     * Sets up high-quality rendering hints for the graphics context.
     *
     * <h3>Example Usage:</h3>
     * <pre>{@code
     * try(var _ = GraphicsUtils.setupHighQualityRendering(g2)) {
     *     // ...
     * }
     * }</pre>
     *
     * @param graphics the graphics context to configure
     * @return a {@link RenderingContext} that can restore the original state
     */
    public static RenderingContext setupHighQualityRendering(@NotNull Graphics2D graphics) {
        Object[] oldHints = {
                graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING),
                graphics.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL)
        };

        Color oldColor = graphics.getColor();
        Stroke oldStroke = graphics.getStroke();
        Composite oldComposite = graphics.getComposite();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        return new RenderingContext(graphics, oldHints, oldColor, oldStroke, oldComposite);
    }

    public static void fillRoundedRectangle(@NotNull Graphics2D graphics, float x, float y,
            float width, float height, int arcSize, Color bgColor) {

        try (RenderingContext ignored = setupHighQualityRendering(graphics)) {
            graphics.setColor(bgColor);
            graphics.fill(ShapeUtils.createRoundedRectangle(x, y, width, height, arcSize));
        }
    }

    public static void drawText(@NotNull Graphics2D graphics2D, @NotNull String text,
            float x, float y, @NotNull Color color) {

        try (RenderingContext ignored = setupHighQualityRendering(graphics2D)) {
            graphics2D.setColor(color);
            graphics2D.drawString(text, x, y);
        }
    }

    public static void drawRoundedRectangle(@NotNull Graphics2D graphics, float x, float y,
            float width, float height, float arcSize,
            Color color, float strokeWidth) {

        try (RenderingContext ignored = setupHighQualityRendering(graphics)) {
            graphics.setColor(color);
            graphics.setStroke(new BasicStroke(strokeWidth));
            graphics.draw(ShapeUtils.createRoundedRectangle(x, y, width, height, arcSize));
        }
    }
}
