import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Window;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Title extends JPanel implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    private int mouseX, mouseY;
    private Rectangle bounds;
    private boolean leftClick = false;
    private BufferedImage title, instructions, play;
    private Window window;
    private BufferedImage[] playButton = new BufferedImage[2];
    private Timer timer;

    public Title (Window window) {

        try {
            title = ImageIO.read(Board.class.getResource("/Title.png"));
            instructions = ImageIO.read(Board.class.getResource("/arrow.png"));
            play = ImageIO.read(Board.class.getResource("/play.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
        mouseX = 0;
        mouseY = 0;

        playButton[0] = play.getSubimage(0,0,100,80);
        playButton[1] = play.getSubimage(100,0,100,80);

        bounds = new Rectangle(Window.WIDTH/2 - 50, Window.HEIGHT/2 - 100, 100,80 );
        this.window = window;
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        if (leftClick = bounds.contains(mouseX,mouseY)){
            window.startTetris();


        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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

    }
}
