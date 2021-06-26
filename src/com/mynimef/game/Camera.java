package com.mynimef.game;

import java.awt.*;
import java.awt.event.*;

public class Camera implements KeyListener, MouseMotionListener {
    public double xPos, yPos, zPos;
    public double xDir, yDir, zDir;
    public double xPlane, yPlane, zPlane;

    private boolean left, right, forward, back;
    private boolean turnRight, turnLeft, turnTop, turnBottom;
    public final double MOVE_SPEED = .08;
    public final double ROTATION_SPEED = .06;

    private Robot robot;
    private boolean pause = false;

    private final double xCenter;
    private final double yCenter;

    public Camera(double x, double y, double xd, double yd, double xp, double yp) {
        xPos = x;
        yPos = y;
        zPos = 0;

        xDir = xd;
        yDir = yd;
        zDir = 0;

        xPlane = xp;
        yPlane = yp;
        zPlane = 0;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        xCenter = screenSize.getWidth() / 2;
        yCenter = screenSize.getHeight() / 2;
    }

    @Override
    public void keyPressed(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_W:
                forward = true;
                break;
            case KeyEvent.VK_S:
                back = true;
                break;
            case KeyEvent.VK_ESCAPE:
                pause = !pause;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_W:
                forward = false;
                break;
            case KeyEvent.VK_S:
                back = false;
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!pause) {
            int posX = e.getXOnScreen();
            if (posX > xCenter) {
                turnRight = true;
            } else if (posX < xCenter) {
                turnLeft = true;
            }

            int posY = e.getYOnScreen();
            if (posY > yCenter) {
                turnTop = true;
            } else if (posY < yCenter) {
                turnBottom = true;
            }

            robot.mouseMove((int) xCenter, (int) yCenter);
        }
    }

    public void update(int[][] map) {
        if(forward) {
            if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos += xDir*MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 0) {
                yPos += yDir * MOVE_SPEED;
            }
        } else if(back) {
            if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos -= xDir * MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]== 0) {
                yPos -= yDir * MOVE_SPEED;
            }
        }

        if(right) {
        } else if(left) {
        }

        if(turnRight) {
            double oldxDir = xDir;
            xDir = oldxDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
            yDir = oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);

            double oldxPlane = xPlane;
            xPlane = xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
            yPlane = oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);

            turnRight = false;
        } else if(turnLeft) {
            double oldxDir=xDir;
            xDir = xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
            yDir = oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);

            double oldxPlane = xPlane;
            xPlane = xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
            yPlane = oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);

            turnLeft = false;
        }

        if(turnTop) {
            zDir = zDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
            zPlane = zPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);

            turnTop = false;
        } else if(turnBottom) {
            zDir = zDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
            zPlane = zPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);

            turnBottom = false;
        }
    }

    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }
}