import java.awt.*;
import java.awt.image.BufferedImage;

public class Shape {

    private BufferedImage block;
    private int[][] coords;
    private Board board;
    private int deltaX = 0;
    private int x, y;
    private int normalSpeed = 600, speedDown = 60, currentSpeed ;
    private long time, lastTime;

    public Shape (BufferedImage block, int[][] coords, Board board){
        this.block = block;
        this.coords = coords;
        this.board = board;

        currentSpeed = normalSpeed;
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
        if(!(y + 1 +coords.length > 20)){
            if(time > currentSpeed){
                y++;
                time = 0;
            }
        }
        deltaX = 0;
    }

    public void render (Graphics g) {

        for (int row = 0; row < coords.length; row ++) {
            for (int col = 0; col < coords[row].length; col ++) {
                if (coords[row][col] != 0)
                    g.drawImage(block, col*board.getBlockSize() + x*board.getBlockSize(),row*board.getBlockSize() + y*board.getBlockSize(), null );
            }
        }

    }


    public void rotate(){

        int[][] rotatedMatrix;
        rotatedMatrix = getTranspose(coords);

        rotatedMatrix = getReverseMatrix(rotatedMatrix);

        if(x + rotatedMatrix[0].length > 10 || y + rotatedMatrix.length > 20)
            return;


        coords = rotatedMatrix;

    }

    private int[][] getTranspose(int[][] matrix){
        int[][] newMatrix = new int[matrix[0].length][matrix.length];

        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[0].length; j++) {
                newMatrix[j][i] = matrix[i][j];
            }

        return  newMatrix;

    }

    private int[][] getReverseMatrix(int[][] matrix){
        int middle = matrix.length / 2;

        for(int i = 0; i < middle; i++){
            int[] m = matrix[i];
            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = m;
        }
        return matrix;
    }

    public void setDeltaX(int deltaX){
        this.deltaX = deltaX;
    }
    public void normalSpeed(){
        currentSpeed = normalSpeed;
    }
    public void speedDown(){
        currentSpeed = speedDown;
    }

 }
