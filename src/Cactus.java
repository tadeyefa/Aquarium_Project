public class Cactus {

    public String name;
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public boolean isAlive;

    public Cactus(int pXpos, int pYpos, int pDx, int pDy) {
        xpos = pXpos;
        ypos = pYpos;
        dx = pDx;
        dy = pDy;
        width = 175;
        height = 50;
        isAlive = true;
    }

    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
    }
}
