<<<<<<< HEAD
public class Board {
=======
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Board extends JPanel {

    private final int blockSize = 30;
    private final int boardWidth = 10, boardHeight = 20;
    private int[][] board = new int[boardWidth][boardHeight];
    private Shape[] shapes = new Shape[7];
    private Shape currentShape;

    private BufferedImage blocks;

    public Board () {
        try {
            blocks = ImageIO.read(Board.class.getResource("/Blocks.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Make 7 Shapes

        //I-Shape
        shapes[0] = new Shape(blocks.getSubimage(0,0,blockSize,blockSize), new int[][] {
                {1,1,1,1}
        },this);

        //Z-Shape
        shapes[1] = new Shape(blocks.getSubimage(blockSize,0,blockSize,blockSize), new int[][] {
                {1,1,0},
                {0,1,1}
        }, this);

        //S-Shape
        shapes[2] = new Shape(blocks.getSubimage(blockSize*2,0,blockSize,blockSize), new int[][] {
                {0,1,1},
                {1,1,0}
        },this);

        //J-Shape
        shapes[3] = new Shape(blocks.getSubimage(blockSize*3,0,blockSize,blockSize), new int[][]{
                {1, 1, 1},
                {0,0,1}
        },this);

        //L-Shape
        shapes[4] = new Shape(blocks.getSubimage(blockSize*4,0,blockSize,blockSize), new int[][] {
                {1,1,1},
                {1,1,0}
        },this);

        //T-Shape
        shapes[5] = new Shape(blocks.getSubimage(blockSize*5,0,blockSize,blockSize), new int[][] {
                {1,1,1},
                {0,1,0}
        },this);

        //O-Shapes
        shapes[6] = new Shape(blocks.getSubimage(blockSize*6,0,blockSize,blockSize), new int[][] {
                {1,1},
                {1,1}
        },this);

        currentShape = shapes[5];
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        currentShape.render(g);

        //Draw a Matrix of Board
        for (int i = 0; i < boardHeight; i++) {
            g.drawLine(0,i*blockSize, boardWidth*blockSize, i*blockSize);
        }

        for (int j = 0; j < boardWidth; j++) {
            g.drawLine(j*blockSize, 0, j*blockSize, boardHeight*blockSize);
        }

    }
    public int getBlockSize(){
        return blockSize;
    }
   
>>>>>>> 7f62ae659c18490ae78f4e483d3bd498ae80c2ab
}
