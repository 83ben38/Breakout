import acm.graphics.GCanvas;
import acm.graphics.GObject;
import acm.graphics.GOval;

import java.awt.*;
import java.util.ArrayList;

public class Ball extends GOval {
    private double deltaX = 1;
    private double deltaY= -1;
    private Breakout screen;
    public boolean lost = false;
    boolean frozen = false;
    public Ball(double size, double x, double y, Breakout screen, boolean bonus){
        super(x,y,size,size);
        setFilled(true);
        if (bonus){
            setFillColor(Color.yellow);
        }
        else {
            setFillColor(Color.white);
        }
        this.screen = screen;
        screen.add(this);
    }
    public void handleMove(){
        if (!frozen) {
            if (lost) {
                remove();
            }
            //move the ball
            move(deltaX, -deltaY);
            //check to see if the ball is too high
            if (getY() <= 0) {
                deltaY *= -1;
            }
            //check to see if the ball is too low
            if (getY() >= screen.getHeight()) {
                lost = true;
                deltaY = -1 * Math.abs(deltaY);
                deltaX = 1 * Math.abs(deltaX);
            }
            //check to see if the ball is too right or left
            if (getX() <= 0 || getX() >= screen.getWidth() - getWidth()) {
                deltaX *= -1;
            }
            // obj can store what we hit
            GObject obj = null;

            // check to see if the ball is about to hit something

            if (obj == null) {
                // check the top right corner
                obj = screen.getElementAt(getX() + getWidth(), getY());
            }

            if (obj == null) {
                // check the top left corner
                obj = screen.getElementAt(getX(), getY());
            }
            if (obj == null) {
                // check the bottom right corner
                obj = screen.getElementAt(getX() + getWidth(), getY() + getHeight());
            }
            if (obj == null) {
                // check the bottom left corner
                obj = screen.getElementAt(getX(), getY() + getHeight());
            }
            // if by the end of the method obj is still null, we hit nothing
            if (obj != null) {
                if (obj instanceof Paddle) {
                    deltaY = 1 * Math.abs(deltaY);
                } else if (obj instanceof Brick) {
                    ((Brick) obj).collide(this);
                    if (getX() + getWidth() <= obj.getX() || getX() >= obj.getX() + obj.getWidth()) {
                        deltaX *= -1;
                    } else {
                        deltaY *= -1;
                    }
                }
            }
        }

    }
    public void remove(){
        screen.remove(this);
        screen.bonus.remove(this);
    }
    public void getFrozen(){
        Color c = getFillColor();
        frozen = true;
        setFillColor(new Color(0, 255, 255));
        for (int i = 0; i < 100; i++) {
            setFillColor(new Color((int)(i*2.55), 255, 255));
            pause(100);
        }
        frozen = false;
        setFillColor(c);
    }
}
