import acm.graphics.GRect;

public class Paddle extends GRect {
    public Paddle(double width, double height, double x, double y){
        super(x,y,width,height);
        setFilled(true);
    }

}
