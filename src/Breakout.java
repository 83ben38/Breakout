import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Breakout extends GraphicsProgram {
    private Ball ball;
    public Paddle paddle;
    //comment
    public ArrayList<Brick> b = new ArrayList<>();
    public ArrayList<Ball> bonus = new ArrayList<>();
    int hits;
    GLabel l;
    public static void main(String[] args) {
        new Breakout().start();
    }
    @Override
    public void init(){
        ball = new Ball(10,getWidth()/2,350,this,false);
        l = new GLabel("Hits: 0");
        paddle = new Paddle(50,10,230,430);
        add(paddle);
        add(l,l.getWidth(),l.getHeight());
        addMouseListeners();
        int i = 4;
        while (true){
            level(i);
            i++;
            while(bonus.size() > 0){
                bonus.get(0).remove();
            }
            ball.setLocation(getWidth()/2,350);
        }
    }
    public void level(int k){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < k; j++) {
                Brick c = new Brick(k-j,this,60,20,(k-4)*2);
                this.add(c,(i*60)+30,40 + (j*20));
                b.add(c);
                pause(10);
            }
        }
        for (int i = 0; i < 3; i++){
            Dialog.showMessage("You have " + (3-i) + " lives left.");
            waitForClick();
            if (tick()){
                return;
            }
        }
        Dialog.showMessage("You lose");
        exit();
    }
    @Override
    public void run(){

    }
    @Override
    public void mouseMoved(MouseEvent e){
        //make sure that the paddle doesn't go off-screen
        if ((e.getX() < getWidth())&&(e.getX() > paddle.getWidth()/2)){
          paddle.setLocation(e.getX()-paddle.getWidth()/2,paddle.getY());
        }
    }
    private boolean tick(){
        while (true) {
            ball.handleMove();
            for (int i = 0; i < bonus.size(); i++) {
                bonus.get(i).handleMove();
            }
            if (ball.lost) {
                ball.lost = false;
                ball.setLocation(getWidth()/2,350);
                while(bonus.size() > 0){
                    bonus.get(0).remove();
                }
                return false;
            }
            if (b.size() < 1){
                Dialog.showMessage("You won");
                return true;
            }
            pause(5);
            l.setLabel("Hits: " + hits);
        }
    }
}
