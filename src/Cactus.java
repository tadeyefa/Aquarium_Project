import java.awt.*;

public class Cactus {

    public String name;
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public boolean isAlive;
    public Rectangle rec;

    public Cactus(int pXpos, int pYpos, int pDx, int pDy) {
        xpos = pXpos;
        ypos = pYpos;
        dx = pDx;
        dy = pDy;
        width = 60;
        height = 60;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, height, width);
    }

    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
    }

    public void bounce() {
        if (xpos > 940) {
            dx = -dx;
        }
        if (ypos > 640) {
            dy = -dy;
        }
        if (xpos < 0) {
            dx = -dx;
        }
        if (ypos < 0) {
            dy = -dy;
        }
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle(xpos, ypos, height, width);
    }
}
