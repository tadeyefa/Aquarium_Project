//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import sun.lwawt.macosx.CPrinterGraphics;

import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image sun;
    public Image cactusPic;
    public Image basketballPic;
    public Image background;
    public Image coyotePic;
    public Image cottontailPic;
    public Image vulturePic;
    public Image won_screen;

    public boolean winning = false;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Cactus cacti;
    private Cactus cacti2;
    private Cactus cacti3;
    private Cactus cacti4;
    private Cactus cacti5;
    private Cactus cacti6;
    private Cactus coyote;
    private Cactus[] cottontails;
    private Cactus vulture; // declare an array of vulture (step 1)

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() {

        setUpGraphics();
        canvas.addKeyListener(this);

        //variable and objects
        //create (construct) the objects needed for the game and load up
        sun = Toolkit.getDefaultToolkit().getImage("sun.png"); //load the picture
        cactusPic = Toolkit.getDefaultToolkit().getImage("cactus.jpeg");
        basketballPic = Toolkit.getDefaultToolkit().getImage("basketball.jpg");
        background = Toolkit.getDefaultToolkit().getImage("sahara-desert.jpg");
        coyotePic = Toolkit.getDefaultToolkit().getImage("coyote.jpeg");
        cottontailPic = Toolkit.getDefaultToolkit().getImage("cottontail.jpeg");
        vulturePic = Toolkit.getDefaultToolkit().getImage("vulture.png");
        cacti = new Cactus(25, 550, 2, 4);
        cacti2 = new Cactus(200, 550, 2, 4);
        cacti3 = new Cactus(375, 550, 2, 4);
        cacti4 = new Cactus(550, 550, 2, 4);
        cacti5 = new Cactus(725, 550, 2, 4);
        cacti6 = new Cactus(900, 550, 2, 4);
        coyote = new Cactus(300, 350, 3, 1);
        cottontails = new Cactus[5];
        for (int y=0;y<cottontails.length;y++) {
            cottontails[y] = new Cactus((int)(Math.random()*50) + 1,(int)(Math.random()*150) + 1,(int)(Math.random()*5) + 1,(int)(Math.random()*5) + 1);
            cottontails[y].isAlive = false;
        }
        cottontails[0].isAlive = true;
        vulture = new Cactus(25, 25, 1, 0); // construct the array to hold the vulture; it is empty (step 2)
//        for(int x=0;x< vulture.length;x++) {
//            vulture[x] = new Cactus(25*2*x, 25, 1, 0); // fill each slot (step 3)
//        }
        won_screen = Toolkit.getDefaultToolkit().getImage("game_won.jpeg");
        coyote.width = 100;
    }// BasicGameApp()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {

        //for the moment we will loop things forever.
        while (true) {

            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(20); // sleep for 10 ms
        }
    }

    public void prey()
    {
        if (cottontails[0].rec.intersects(coyote.rec)) {
            cottontails[0].isAlive = false;
        }
        if (cottontails[1].rec.intersects(coyote.rec)) {
            cottontails[1].isAlive = false;
        }
        if (cottontails[2].rec.intersects(coyote.rec)) {
            cottontails[2].isAlive = false;
        }
        if (cottontails[3].rec.intersects(coyote.rec)) {
            cottontails[3].isAlive = false;
        }
        if (cottontails[4].rec.intersects(coyote.rec)) {
            cottontails[4].isAlive = false;
        }
    }

    public void reproduce()
    {

        System.out.println("cactus width: " + cacti.width + "height: " + cacti.height + "x : " +cacti.xpos + " y :");

        if (cottontails[0].rec.intersects(cacti.rec)) {
            cottontails[1].isAlive = true;
        }
        if (cottontails[1].rec.intersects(cacti.rec)) {
            cottontails[2].isAlive = true;
        }
        if (cottontails[2].rec.intersects(cacti.rec)) {
            cottontails[3].isAlive = true;
        }
        if (cottontails[3].rec.intersects(cacti.rec)) {
            cottontails[4].isAlive = true;
        }
    }

    public void moveThings()
    {
        //calls the move( ) code in the objects
        coyote.bounce();
        for (int y=0;y< cottontails.length;y++) {
            cottontails[y].bounce();
        }
        vulture.wrap();
        reproduce();
        prey();
        checkWin();
    }
    public void checkWin(){
        if(cottontails[0].isAlive == false && cottontails[1].isAlive == false && cottontails[2].isAlive == false && cottontails[3].isAlive == false && cottontails[4].isAlive == false){
            winning = true;
        }
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ){
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        if(winning == false){
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the image of the astronaut
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
        g.drawImage(sun, 940, 0, 60, 60, null);
        g.draw(new Rectangle(cacti.xpos, cacti.ypos, 75, 150));
        g.drawImage(cactusPic, 25, 550, 75, 150, null);
        g.draw(new Rectangle(cacti2.xpos, cacti2.ypos, 75, 150));
        g.drawImage(cactusPic, 200, 550, 75, 150, null);
        g.draw(new Rectangle(cacti3.xpos, cacti2.ypos, 75, 150));
        g.drawImage(cactusPic, 375, 550, 75, 150, null);
        g.draw(new Rectangle(cacti4.xpos, cacti2.ypos, 75, 150));
        g.drawImage(cactusPic, 550, 550, 75, 150, null);
        g.draw(new Rectangle(cacti5.xpos, cacti2.ypos, 75, 150));
        g.drawImage(cactusPic, 725, 550, 75, 150, null);
        g.draw(new Rectangle(cacti6.xpos, cacti2.ypos, 75, 150));
        g.drawImage(cactusPic, 900, 550, 75, 150, null);
        g.draw(new Rectangle(coyote.xpos, coyote.ypos, 100, 50));
        g.drawImage(coyotePic, coyote.xpos, coyote.ypos, coyote.width, 50, null);
        if (cottontails[0].isAlive == true) {
            g.draw(new Rectangle(cottontails[0].xpos, cottontails[0].ypos, cottontails[0].width, cottontails[0].height));
            g.drawImage(cottontailPic, cottontails[0].xpos, cottontails[0].ypos, cottontails[0].width, cottontails[0].height, null);
        }
        if (cottontails[1].isAlive == true) {
            g.draw(new Rectangle(cottontails[1].xpos, cottontails[1].ypos, cottontails[1].width, cottontails[1].height));
            g.drawImage(cottontailPic, cottontails[1].xpos, cottontails[1].ypos, cottontails[1].width, cottontails[1].height, null);
        }
        if (cottontails[2].isAlive == true) {
            g.draw(new Rectangle(cottontails[2].xpos, cottontails[2].ypos, cottontails[2].width, cottontails[2].height));
            g.drawImage(cottontailPic, cottontails[2].xpos, cottontails[2].ypos, cottontails[2].width, cottontails[2].height, null);
        }
        if (cottontails[3].isAlive == true) {
            g.draw(new Rectangle(cottontails[3].xpos, cottontails[3].ypos, cottontails[3].width, cottontails[3].height));
            g.drawImage(cottontailPic, cottontails[3].xpos, cottontails[3].ypos, cottontails[3].width, cottontails[3].height, null);
        }
        if (cottontails[4].isAlive == true) {
            g.draw(new Rectangle(cottontails[4].xpos, cottontails[4].ypos, cottontails[4].width, cottontails[4].height));
            g.drawImage(cottontailPic, cottontails[4].xpos, cottontails[4].ypos, cottontails[4].width, cottontails[4].height, null);
        }
        g.draw(new Rectangle(vulture.xpos, vulture.ypos, 75, 150));
        g.drawImage(vulturePic, vulture.xpos, vulture.ypos, 75, 150, null);

    }
        else{
            g.drawImage(won_screen, 0, 0, WIDTH, HEIGHT, null);
        }
        g.dispose();

        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println(code);

        if(code == 83){
            coyote.dy = Math.abs(coyote.dy);
        }
        if(code == 65){
            coyote.dx = Math.abs(coyote.dx);
        }
        if(code == 87){
            coyote.dy = -Math.abs(coyote.dy);
        }
        if(code == 55){
            coyote.dx = -Math.abs(coyote.dx);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX());
        coyote.width = 2*coyote.width;
        //coyote.ypos = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX());
        coyote.width = 2*coyote.width;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(e.getX());
        coyote.width = 100;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        System.out.println(e.getX());
//        coyote.xpos = e.getX();
//        coyote.ypos = e.getY();
    }
}