import java.awt.*;
import java.awt.image.BufferedImage;

public class Shape {

    private BufferedImage block;
    private int[][] coords;
    private Board board;

    public Shape (BufferedImage block, int[][] coords, Board board){
        this.block = block;
        this.coords = coords;
        this.board = board;
    }

    public void update (){}

    public void render (Graphics g) {

    }

 }
