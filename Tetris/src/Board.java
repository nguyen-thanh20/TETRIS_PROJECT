import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener {

    private static final long serialversionUID = 1L;

    private Clip music;

    private BufferedImage blocks, background, pause, refresh;

    // Block Size
    private final int blockSize = 30;

    // Board Dimensions
    private final int boardWidth = 10, boardHeight = 20;

    // Field
    private int[][] board = new int[boardHeight][boardWidth];

    // All possible shapes
    private Shape[] shapes = new Shape[7];

    // Current Shape
    private Shape currentShape, nextShape;

    // Game Loop
    private Timer timer;
    private final int FPS = 60;
    private final int delay = 1000 / FPS;
	
    // Mouse event variables
    private int mouseX, mouseY;
    private boolean leftClick = false;
    private Rectangle stopBounds,refreshBounds;
    private boolean gamePaused = false;
    private boolean gameOver = false;


    // Buttons press lapse

    private Timer buttonLapse = new Timer(300, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonLapse.stop();
        }
    });

    // Score
    private int score = 0;


    public Board () {
            // Load Assets
            blocks = ImageLoader.loadImage("/Blocks.png");

            background = ImageLoader.loadImage("/background.png");
            pause = ImageLoader.loadImage("/Pause.png");
            refresh = ImageLoader.loadImage("/refresh.png");

            music = ImageLoader.LoadSound("/music.wav");

            music.loop(Clip.LOOP_CONTINUOUSLY);

            mouseX = 0;
            mouseY = 0;

            stopBounds = new Rectangle(350,350,pause.getWidth(),pause.getHeight() + pause.getHeight()/2);
            refreshBounds = new Rectangle(350, 500 - refresh.getHeight() - 20, refresh.getWidth(),refresh.getHeight() + refresh.getHeight()/2);

        // Create game looper

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });

        timer.start();

        //Make 7 Shapes

        //I-Shape
        shapes[0] = new Shape(blocks.getSubimage(0,0,blockSize,blockSize), new int[][] {
                {1,1,1,1}
        },this, 1);

        //Z-Shape
        shapes[1] = new Shape(blocks.getSubimage(blockSize,0,blockSize,blockSize), new int[][] {
                {1,1,0},
                {0,1,1}
        }, this, 2);

        //S-Shape
        shapes[2] = new Shape(blocks.getSubimage(blockSize*2,0,blockSize,blockSize), new int[][] {
                {0,1,1},
                {1,1,0}
        },this, 3);

        //J-Shape
        shapes[3] = new Shape(blocks.getSubimage(blockSize*3,0,blockSize,blockSize), new int[][]{
                {1, 1, 1},
                {0,0,1}
        },this, 4);

        //L-Shape
        shapes[4] = new Shape(blocks.getSubimage(blockSize*4,0,blockSize,blockSize), new int[][] {
                {1,1,1},
                {1,1,0}
        },this, 5);

        //T-Shape
        shapes[5] = new Shape(blocks.getSubimage(blockSize*5,0,blockSize,blockSize), new int[][] {
                {1,1,1},
                {0,1,0}
        },this, 6);

        //O-Shapes
        shapes[6] = new Shape(blocks.getSubimage(blockSize*6,0,blockSize,blockSize), new int[][] {
                {1,1},
                {1,1}
        },this, 7);
	
        //setNextShape();
    }

    public void update() {

        if (stopBounds.contains(mouseX,mouseY) && leftClick && !buttonLapse.isRunning() && !gameOver) {
            buttonLapse.start();
            gamePaused =! gamePaused;
        }

        if (refreshBounds.contains(mouseX,mouseY) && leftClick )
            startGame();

        if (gamePaused || gameOver) {
            return;
        }
        currentShape.update();
//	    if(gameOver)
//		    timer.stop();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(background,0,0,null);


        for(int row = 0; row < board.length; row++)
        	for(int col = 0; col < board[row].length; col++)
        		if(board[row][col] != 0)
        			g.drawImage(blocks.getSubimage((board[row][col]-1)*blockSize, 0, blockSize, blockSize), col*blockSize, row*blockSize, null);

        for (int row = 0; row < nextShape.getCoords().length; row ++)
            for (int col = 0; col < nextShape.getCoords()[0].length; col ++)
                if (nextShape.getCoords()[row][col] != 0) {
                    g.drawImage(nextShape.getBlock(), col*30 + 320, row*30 + 50,null);
                }

        currentShape.render(g);

        if (stopBounds.contains(mouseX,mouseY)) {
            g.drawImage(pause.getScaledInstance(pause.getWidth() + 3, pause.getHeight() + 3, BufferedImage.SCALE_DEFAULT), stopBounds.x + 3, stopBounds.y + 3, null);
            } else {
                g.drawImage(pause, stopBounds.x, stopBounds.y, null);
        }

        if (gamePaused)
        {

            String gamePauseString = "GAME PAUSED";
            g.setColor(Color.WHITE);
            g.setFont(new Font("Georgia", Font.BOLD, 30));
            g.drawString(gamePauseString,35, Window.HEIGHT/2);
        }

        if (gameOver)
        {
            String gameOverString = "GAME OVER";
            g.setColor(Color.WHITE);
            g.setFont(new Font ("Georgia", Font.BOLD, 30));
            g.drawString(gameOverString, 50, Window.HEIGHT/2);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.BOLD, 20));

        g.drawString("SCORE" , Window.WIDTH - 125, Window.HEIGHT/2);
        g.drawString(score+"", Window.WIDTH - 125, Window.HEIGHT/2 + 30);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(0,0,0,100));


        // Draw a Matrix of Board
        for (int i = 0; i <= boardHeight; i++) {
            g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
        }

        for (int j = 0; j <= boardWidth; j++) {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * blockSize);
        }

    }
    
    public void setNextShape() {
        int index = (int) (Math.random() * shapes.length);

        nextShape = new Shape(shapes[index].getBlock(), shapes[index].getCoords(), this, shapes[index].getColor());
    }

    public void setCurrentShape () {
        currentShape = nextShape ;
        setNextShape();

        for(int row = 0; row < currentShape.getCoords().length; row++)

	        for(int col = 0; col < currentShape.getCoords()[row].length; col++)

                if(currentShape.getCoords()[row][col] != 0)
                {
                    if( board[currentShape.getY() + row ][currentShape.getX() + col] != 0)
                    gameOver = true;
                }
    }

    public int getBlockSize() {
        return blockSize;
    }
    
    public int[][] getBoard(){
    	return board;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            currentShape.setDeltaX(-1);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            currentShape.setDeltaX(1);
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            currentShape.speedDown();
        if(e.getKeyCode() == KeyEvent.VK_UP)
            currentShape.rotate();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            currentShape.normalSpeed();;

    }

    @Override
    public void keyTyped(KeyEvent e) {
        

    }

    public void startGame() {
        stopGame();
        setNextShape();
        setCurrentShape();
        gameOver = false;
        timer.start();
    }

    public void stopGame() {
        score = 0;

        for (int row = 0; row < board.length; row ++)
        {
            for (int col = 0; col < board[row].length; col ++)
            {
                board[row][col] = 0;
            }
        }
        timer.stop();
    }
   
}
