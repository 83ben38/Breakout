import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;

import java.awt.*;

public class Brick extends GCompound {
    boolean bomb = false;
    boolean ball = false;
    boolean wider = false;
    boolean shielder = false;
    boolean freezer = false;
    boolean bricker = false;
    int health;
    GRect g;
    GObject p;
    Breakout screen;
    GRect shield;
    public void collide(Ball hitter){
        if (shield == null) {
            health--;
            screen.hits++;
            if (health == 0) {
                screen.remove(this);
                screen.b.remove(this);
                if (bomb) {
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (screen.getElementAt(getX() + (i * getWidth()) + getWidth() / 2, getY() + (j * getHeight()) + getHeight() / 2) instanceof Brick) {
                                ((Brick) screen.getElementAt(getX() + (i * getWidth()) + getWidth() / 2, getY() + (j * getHeight()) + getHeight() / 2)).collide(hitter);
                            }
                        }
                    }
                }
                if (ball) {
                    screen.bonus.add(new Ball(10, this.getX() + p.getX(), this.getY() + p.getY(), screen, true));
                }
                if (wider) {
                    screen.paddle.setSize(screen.paddle.getWidth() + 5, screen.paddle.getHeight());
                    new Thread(this::goBack).start();
                }
                if (shielder){
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (screen.getElementAt(getX() + (i * getWidth()) + getWidth() / 2, getY() + (j * getHeight()) + getHeight() / 2) instanceof Brick) {
                                ((Brick) screen.getElementAt(getX() + (i * getWidth()) + getWidth() / 2, getY() + (j * getHeight()) + getHeight() / 2)).addShield();
                            }
                        }
                    }
                }
                if (freezer){
                    new Thread(hitter::getFrozen).start();
                }
                if (bricker){
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (!(screen.getElementAt(getX() + (i * getWidth()) + getWidth() / 2, getY() + (j * getHeight()) + getHeight() / 2) instanceof Brick)) {
                                Brick brick = new Brick(randomInt(2,4),screen,(int)g.getWidth(),(int)g.getHeight(),0);
                            }
                        }
                    }
                }
            }
            Image();
        }
    }
    public Brick(int health, Breakout screen, int width, int height, int chance){
        if (randomInt(1,5+chance)==1) {
            bomb = true;
        }
        else if(randomInt(1,5+chance) == 1){
            ball = true;
        }
        else if (randomInt(1,5+chance) == 1){
            wider = true;
        }
        else if (randomInt(1,13+chance) <= (chance/2)+1){
            shielder = true;
        }
        else if (randomInt(1,13+chance) <= (chance/2)+1){
            freezer = true;
        }
        else if (randomInt(1,13+chance) <= (chance/2)+1){
            bricker = true;
        }
        this.health = health;
        this.screen = screen;
        g = new GRect(width,height);
        g.setFilled(true);
        if (bomb || ball || freezer) {
            p = new GOval(width / 6, height / 2);
            ((GOval)p).setFilled(true);
        }
        if (wider || shielder || bricker){
          p = new GRect(width/3,height/2);
          ((GRect)p).setFilled(true);
        }
        Image();
        add(g);
        if (bomb || ball || wider || shielder || freezer){
            add(p,getWidth()/2-p.getWidth()/2,getHeight()/2-p.getHeight()/2);
            p.sendToFront();
        }
    }
    public void Image(){
        if (health < 10){
            g.setFillColor(new Color(255-(health*25),0,health*25));
        }
        else if (health < 20){
            g.setFillColor(new Color(0,(health-10)*25,255-((health-10)*25)));
        }
        else{
            g.setFillColor(new Color((health-20)*25,255-((health-20)*25),0));
        }
        if (bomb || ball || wider){
            p.setVisible(true);
        }
        if (bomb){
            ((GOval)p).setFillColor(Color.black);
        }
        else if (ball){
            ((GOval)p).setFillColor(Color.yellow);
        }
        else if (shielder){
            ((GRect)p).setFillColor(Color.CYAN);
        }
        else if (bricker){
            ((GRect)p).setFillColor(Color.RED);
        }
        else if (freezer){
            ((GOval)p).setFillColor(Color.CYAN);
        }
        else if (wider){
            ((GRect)p).setFillColor(Color.black);
        }
    }
    private int randomInt(int from, int to){
        return (int)(Math.random()*(to-from+1))+1;
    }
    private void goBack(){
        pause(30000);
        screen.paddle.setSize(screen.paddle.getWidth()-5,screen.paddle.getHeight());
    }
    public void addShield(){
        if (shield == null) {
            shield = new GRect(getWidth(), getHeight());
            shield.setFilled(true);
            shield.setFillColor(new Color(0, 205, 200, 255));
            add(shield);
            new Thread(this::removeShield).start();
        }
    }
    private void removeShield(){
        for (int i = 0; i < 100; i++) {
            shield.setFillColor(new Color(0, 205, 200, 255-(int)2.55*i));
            pause(100);
        }
       remove(shield);
       shield = null;
    }
}
