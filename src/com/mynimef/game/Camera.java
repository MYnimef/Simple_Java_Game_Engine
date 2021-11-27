package com.mynimef.game;

import java.awt.*;
import java.awt.event.*;

public class Camera implements KeyListener, MouseMotionListener {
    public double xPos, yPos, zPos;
    public double xDir, yDir, zDir;
    public double xPlane, yPlane, zPlane;

    private boolean left, right, forward, back;
    double ROTATION_SPEED_H = 0, ROTATION_SPEED_V = 0;
    private boolean fast = false;

    private Robot robot;
    private boolean pause = false;

    private final double xCenter;
    private final double yCenter;

    public Camera(double xPos, double yPos, double xDir, double yDir, double xPlane, double yPlane) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = 0;

        this.xDir = xDir;
        this.yDir = yDir;
        this.zDir = 0;

        this.xPlane = xPlane;
        this.yPlane = yPlane;
        this.zPlane = 0;

        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.xCenter = screenSize.getWidth() / 2;
        this.yCenter = screenSize.getHeight() / 2;
    }

    @Override
    public void keyPressed(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_W -> forward = true;
            case KeyEvent.VK_S -> back = true;
            case KeyEvent.VK_SHIFT -> fast = true;
            case KeyEvent.VK_ESCAPE -> pause = !pause;
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_D -> right = false;
            case KeyEvent.VK_W -> forward = false;
            case KeyEvent.VK_S -> back = false;
            case KeyEvent.VK_SHIFT -> fast = false;
            default -> {
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!pause) {
            int posX = e.getXOnScreen();
            if (posX != xCenter) {
                ROTATION_SPEED_H = -(posX - xCenter) * 0.005;
            }

            int posY = e.getYOnScreen();
            if (posY != yCenter) {
                ROTATION_SPEED_V = -(posY - yCenter) * 0.005;
            }

            robot.mouseMove((int) xCenter, (int) yCenter);
        }
    }

    public void update(int[][] map) {
        double speed;
        if (fast) {
            speed = 0.1;
        } else {
            speed = 0.04;
        }

        if(forward) {
            if(map[(int)(xPos + xDir * speed)][(int)yPos] == 0) {
                xPos += xDir * speed;
            }
            if(map[(int)xPos][(int)(yPos + yDir * speed)] == 0) {
                yPos += yDir * speed;
            }
        } else if(back) {
            if(map[(int)(xPos - xDir * speed)][(int)yPos] == 0) {
                xPos -= xDir * speed;
            }
            if(map[(int)xPos][(int)(yPos - yDir * speed)]== 0) {
                yPos -= yDir * speed;
            }
        }

        if(right) {
            if(map[(int)(xPos + yDir * speed)][(int)yPos] == 0) {
                xPos += yDir * speed;
            }
            if(map[(int)xPos][(int)(yPos - xDir * speed)]== 0) {
                yPos -= xDir * speed;
            }
        } else if(left) {
            if(map[(int)(xPos - yDir * speed)][(int)yPos] == 0) {
                xPos -= yDir * speed;
            }
            if(map[(int)xPos][(int)(yPos + xDir * speed)]== 0) {
                yPos += xDir * speed;
            }
        }

        if (ROTATION_SPEED_H != 0) {
            changeDirectionAndPlane_H(Math.cos(ROTATION_SPEED_H), Math.sin(ROTATION_SPEED_H), xDir, xPlane);
            ROTATION_SPEED_H = 0;
        }

        if (ROTATION_SPEED_V != 0) {
            changeDirectionAndPlane_V(Math.cos(ROTATION_SPEED_V), Math.sin(ROTATION_SPEED_V), yDir, yPlane);
            ROTATION_SPEED_V = 0;
        }
    }

    private void changeDirectionAndPlane_H(double cos, double sin, double xDir, double xPlane) {
        this.xDir = xDir * cos - yDir * sin;
        this.yDir = xDir * sin + yDir * cos;

        this.xPlane = xPlane * cos - yPlane * sin;
        this.yPlane = xPlane * sin + yPlane * cos;
    }

    private void changeDirectionAndPlane_V(double cos, double sin, double yDir, double yPlane) {
        //this.xDir = xDir * cos;
        //this.yDir = yDir * cos;

        //this.xPlane = xPlane * cos - yPlane * sin;
        //this.yPlane = xPlane * sin + yPlane * cos;
    }

    public void keyTyped(KeyEvent arg0) {}
}