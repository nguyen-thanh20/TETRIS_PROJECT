import java.awt.*;
import java.awt.image.BufferedImage;

public class Shape {

    private BufferedImage block;
    private int[][] coords;
    private Board board;
    private int deltaX = 0;
    private int x, y;
    private int normalSpeed = 600, speedDown = 10;
    private long time, lastTime;

    public Shape (BufferedImage block, int[][] coords, Board board){
        this.block = block;
        this.coords = coords;
        this.board = board;

        time = 0;
        lastTime = System.currentTimeMillis();
        x = 3;
        y = 0;
    }

    public void update (){
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(!(x + deltaX + coords[0].length > 10) && !(x+deltaX < 0))
            x += deltaX;
        if(time > normalSpeed){
            y++;
        }
            y++;
        
    }

    public void render (Graphics g) {

        for (int row = 0; row < coords.length; row ++) {
            for (int col = 0; col < coords[row].length; col ++) {
                if (coords[row][col] != 0)
                    g.drawImage(block, col*board.getBlockSize() + x*board.getBlockSize(),row*board.getBlockSize() + y*board.getBlockSize(), null );
            }
        }

    }
    public void getDeltaX(int deltaX){
        this.deltaX = deltaX;
    }

 }
