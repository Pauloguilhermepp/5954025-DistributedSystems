import java.awt.*;
import java.awt.geom.Path2D;
import javax.swing.*;

public class PolygonButton extends JButton {
  private int sides;

  public PolygonButton() { this.sides = 0; }

  public void setSides(int sides) {
    this.sides = sides;
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (sides < 3)
      return;

    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    int w = getWidth();
    int h = getHeight();
    int radius = Math.min(w, h) / 2 - 10;

    Path2D polygon = new Path2D.Double();
    for (int i = 0; i < sides; i++) {
      double angle = 2 * Math.PI / sides * i - Math.PI / 2;
      int x = (int)(w / 2 + radius * Math.cos(angle));
      int y = (int)(h / 2 + radius * Math.sin(angle));
      if (i == 0) {
        polygon.moveTo(x, y);
      } else {
        polygon.lineTo(x, y);
      }
    }
    polygon.closePath();

    g2.setColor(Color.BLACK);
    g2.setStroke(
        new BasicStroke(3)); // Set the stroke width for drawing the edges
    g2.draw(polygon);
  }
}
