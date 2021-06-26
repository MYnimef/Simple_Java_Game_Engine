import java.awt.event.*;

public class Camera implements KeyListener, MouseMotionListener {
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double MOVE_SPEED = .08;
    public final double ROTATION_SPEED = .045;

    public Camera(double x, double y, double xd, double yd, double xp, double yp) {
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
    }

    @Override
    public void keyPressed(KeyEvent key) {
        int pressedKey = key.getKeyCode();

        if((pressedKey == KeyEvent.VK_LEFT || pressedKey == KeyEvent.VK_A)) {
            left = true;
        } else if((pressedKey == KeyEvent.VK_RIGHT || pressedKey == KeyEvent.VK_D)) {
            right = true;
        } else if((pressedKey == KeyEvent.VK_UP || pressedKey == KeyEvent.VK_W)) {
            forward = true;
        } else if((pressedKey == KeyEvent.VK_DOWN || pressedKey == KeyEvent.VK_S)) {
            back = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        int pressedKey = key.getKeyCode();

        if((pressedKey == KeyEvent.VK_LEFT || pressedKey == KeyEvent.VK_A)) {
            left = false;
        } else if((pressedKey == KeyEvent.VK_RIGHT || pressedKey == KeyEvent.VK_D)) {
            right = false;
        } else if((pressedKey == KeyEvent.VK_UP || pressedKey == KeyEvent.VK_W)) {
            forward = false;
        } else if((pressedKey == KeyEvent.VK_DOWN || pressedKey == KeyEvent.VK_S)) {
            back = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        /*
        int xDirection = e.getX();

        if (xDir >= xDirection / 2) {
            right = false;
            left = true;
        } else {
            left = false;
            right = true;
        }
        */
    }

    public void update(int[][] map) {
        if(forward) {
            if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos += xDir*MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 0)
                yPos += yDir*MOVE_SPEED;
        }
        if(back) {
            if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
                xPos-=xDir*MOVE_SPEED;
            if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]== 0)
                yPos-=yDir*MOVE_SPEED;
        }
        if(right) {
            double oldxDir = xDir;
            xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
            yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
        }
        if(left) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
            yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
        }
    }

    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }
}