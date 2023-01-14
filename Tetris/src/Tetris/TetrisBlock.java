package Tetris;

import java.awt.*;
import java.util.Random;

public class TetrisBlock {

    private int[][] shape;
    private Color color;
    private int x,y;

    private int shapes[][][];
    private int currentRotation;
    private Color[] availableColor = {  Color.GREEN, Color.blue, Color.CYAN, Color.ORANGE,
                                        Color.YELLOW, Color.red, Color.pink,Color.magenta};

    public TetrisBlock(int[][] shape){
        this.shape = shape;
        initShapes();
    }

    private void initShapes(){
        shapes = new int[4][][];
        for (int i=0; i<4; i++){
            int row= shape[0].length;
            int col= shape.length;

            shapes[i] = new int[row][col];

            for (int y=0; y<row; y++){
                for (int x=0; x<col; x++){
                    shapes[i][y][x] = shape[col -x- 1][y];
                }
            }
            shape = shapes[i];
        }

    }

    public void spawn(int gridWith){
        Random r = new Random();

        currentRotation = r.nextInt(4);
        shape = shapes[currentRotation];
        y = -getHeight();
        x = (gridWith-getWidth())/2;
        color = availableColor[r.nextInt(availableColor.length)];
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth(){
        return shape[0].length;
    }

    public int getHeight(){
        return shape.length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public void moveDown(){
        y++;
    }
    public void moveRight(){
        x++;
    }
    public void moveLeft(){
        x--;
    }

    public void rotate(){
        currentRotation++;
        if (currentRotation > 3)
            currentRotation =0;
        shape = shapes[currentRotation];

    }
    public int getBottomEdge(){
        return y + getHeight();
    }

    public int getRightEdge(){
        return x+ getWidth();
    }

    public int getLeftEdge(){return x;}

}
