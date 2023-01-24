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
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

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

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Cactus cacti;
    private Cactus cacti2;
    private Cactus cacti3;
    private Cactus cacti4;
    private Cactus cacti5;
    private Cactus cacti6;
    private Cactus coyote;
    private Cactus cottontail;

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

        //variable and objects
        //create (construct) the objects needed for the game and load up
        sun = Toolkit.getDefaultToolkit().getImage("sun.png"); //load the picture
        cactusPic = Toolkit.getDefaultToolkit().getImage("cactus.jpeg");
        basketballPic = Toolkit.getDefaultToolkit().getImage("basketball.jpg");
        background = Toolkit.getDefaultToolkit().getImage("sahara-desert.jpg");
        coyotePic = Toolkit.getDefaultToolkit().getImage("coyote.jpeg");
        cottontailPic = Toolkit.getDefaultToolkit().getImage("cottontail.jpeg");
        cacti = new Cactus(25, 550, 2, 4);
        cacti2 = new Cactus(25, 550, 2, 4);
        cacti3 = new Cactus(25, 550, 2, 4);
        cacti4 = new Cactus(25, 550, 2, 4);
        cacti5 = new Cactus(25, 550, 2, 4);
        cacti6 = new Cactus(25, 550, 2, 4);
        coyote = new Cactus(300, 350, 1, 3);
        cottontail = new Cactus(200, 150, 2, 2);
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

//    public void prey()
//    {
//        if (cottontail.rec.intersects(coyote.rec)) {
//            cottontail.isAlive = false;
//        }
//    }

    public void reproduce()
    {
        if (cottontail.rec.intersects(cacti.rec)) {
            CPrinterGraphics g = null;
            g.draw(new Rectangle(cottontail.xpos, cottontail.ypos, cottontail.width, cottontail.height));
            g.drawImage(cottontailPic, cottontail.xpos, cottontail.ypos, cottontail.width, cottontail.height, null);
        }
    }

    public void moveThings()
    {
        //calls the move( ) code in the objects
        coyote.bounce();
        cottontail.bounce();
//        prey();
        reproduce();
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
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the image of the astronaut
        g.drawImage(background, 0, 0, WIDTH,HEIGHT, null);
        g.drawImage(sun, 940, 0, 60,60, null);
        g.draw(new Rectangle(cacti.xpos, cacti.ypos, 75, 150));
        g.drawImage(cactusPic, 25, 550, 75,150, null);
        g.drawImage(cactusPic, 200, 550, 75, 150, null);
        g.drawImage(cactusPic, 375, 550, 75, 150, null);
        g.drawImage(cactusPic, 550, 550, 75, 150, null);
        g.drawImage(cactusPic, 725, 550, 75, 150, null);
        g.drawImage(cactusPic, 900, 550, 75, 150, null);
        g.draw(new Rectangle(coyote.xpos, coyote.ypos, 100, 50));
        g.drawImage(coyotePic, coyote.xpos, coyote.ypos, 100, 50, null);
        if (cottontail.isAlive == true) {
            g.draw(new Rectangle(cottontail.xpos, cottontail.ypos, cottontail.width, cottontail.height));
            g.drawImage(cottontailPic, cottontail.xpos, cottontail.ypos, cottontail.width, cottontail.height, null);
        }
        g.dispose();

        bufferStrategy.show();
    }
}