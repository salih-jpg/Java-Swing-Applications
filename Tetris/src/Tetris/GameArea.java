package Tetris;

import BlockShapes.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;

public class GameArea extends JPanel {
    Border blackline;

    private int gridRows;
    private int gridColumns;
    private int gridCellSize;

    private Color[][] backGround;

    TetrisBlock[] blocks = {new IShape(), new TShape(), new LShape(), new OShape(), new ZShape(), new JShape(), new SShape()};
    TetrisBlock block;
    GameArea(int gridColumns){

        this.setBounds(20,0,400,600);
        blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);
        this.gridColumns = gridColumns;
        gridCellSize = this.getBounds().width/gridColumns;
        this.gridRows = this.getBounds().height/gridCellSize;
        this.backGround = new Color[gridRows][gridColumns];
    }

    public void spawnBlock(){
        Random r =new Random();
        block = blocks[r.nextInt(blocks.length)];
        block.spawn(gridColumns);
    }

    public boolean isBlockOutOfBounds(){
        if (block.getY() < 0){
            return true;
        }
        return false;
    }
    public boolean moveBlockDown(){

        if (checkBottom() == false ) {
            return false;
        }

        block.moveDown();
        repaint();
        return true;
    }

    public void moveBlockRight(){
          if (!checkRight()) return;

          block.moveRight();
          repaint();
    }

    public void moveBlockLeft(){
       if (!checkLeft()) return;

       block.moveLeft();
       repaint();
    }

    public void dropBlock(){
        while (checkBottom()){
            block.moveDown();
        }
        repaint();
    }
    public void rotateBlock(){
        if (checkBottom()){
            block.rotate();
            if (block.getLeftEdge() < 0) block.setX(0);
            if (block.getRightEdge() >  gridColumns) block.setX(gridColumns - block.getWidth());
            if (block.getBottomEdge() > gridRows) block.setY(gridRows - block.getHeight());
            repaint();
        }
    }

    public int clearLines(){
        boolean filled;
        int clearedLines = 0;
        for (int r= gridRows-1; r>=0; r--){
            filled = true;
            for (int c=0; c<gridColumns; c++){
                if (backGround[r][c] == null){
                    filled = false;
                    break;
                }
            }
            if (filled == true){
                clearedLines++;
                clearLine(r);
                clearLine(0);
                lineShifted(r);
                r++;
                repaint();
            }
        }
        return clearedLines;
    }
    public void clearLine(int r){
        for (int i=0; i<gridColumns; i++){
            backGround[r][i] = null;
        }
    }
    public void lineShifted(int r){
        for (int row=r; row >0; row--){
            for (int col=0; col<gridColumns; col++) {
               backGround[row][col] = backGround[row-1][col];
            }
        }
    }
    public boolean checkBottom(){
        if (block.getBottomEdge() == gridRows){
            return false;
        }

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int col=0; col<w; col++){
            for (int row= h-1; row>=0; row--){
                if (shape[row][col] != 0){
                    int x= col + block.getX();
                    int y= row + block.getY() +1;
                    if (y<0) break;
                    if (backGround[y][x] != null) return false;
                    break;
                }
            }
        }

        return true;
    }

   public boolean checkRight(){
        if (block.getRightEdge() == gridColumns)
            return false;
       int[][] shape = block.getShape();
       int w = block.getWidth();
       int h = block.getHeight();

       for (int row=0; row<h; row++){
           for (int col= w-1; col >= 0; col--){
               if (shape[row][col] != 0){
                   int x= col + block.getX() +1;
                   int y= row + block.getY();
                   if (y<0) break;
                   if (backGround[y][x] != null || isBlockOutOfBounds()) return false;
                   break;
               }
           }
       }
        return true;

    }

    public boolean checkLeft(){
        if (block.getLeftEdge() == 0)
            return false;
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int row=0; row<h; row++){
            for (int col= 0; col < w; col++){
                if (shape[row][col] != 0){
                    int x= col + block.getX()-1;
                    int y= row + block.getY();
                    if (y<0) break;
                    if (backGround[y][x] != null || isBlockOutOfBounds()) return false;
                    break;
                }
            }
        }

        return true;
    }

   public void moveBlockToBackGround(){
        int h = block.getHeight();
        int w = block.getWidth();
        Color c = block.getColor();
        int[][] shape= block.getShape();

        int xPos = block.getX();
        int yPos = block.getY();

        for (int rows=0; rows<h; rows++){
            for (int columns=0; columns<w; columns++){
                if (shape[rows][columns] == 1){
                    backGround[yPos+ rows][xPos+ columns] = c;
                }
            }
        }
    }

    public void drawBlock(Graphics g){
        int h = block.getHeight();
        int w = block.getWidth();
        Color c = block.getColor();
        int[][] shape= block.getShape();
        for (int rows=0; rows<h; rows++){
            for (int columns=0; columns<w; columns++){
               if (shape[rows][columns] == 1){
                   int x = (block.getX() + columns)* gridCellSize;
                   int y = (block.getY() + rows)* gridCellSize ;
                   drawGridSquare(g,c,x,y);
               }
            }
        }
    }

    public void drawBackground(Graphics g){
        Color color;
        for (int r=0; r< gridRows; r++){
            for (int c=0; c<gridColumns; c++){
                color = backGround[r][c];

                if (color != null){
                    int x = c*gridCellSize;
                    int y = r*gridCellSize;
                    drawGridSquare(g,color,x,y);
                }
            }
        }
    }

    public void drawGridSquare(Graphics g, Color color, int x, int y){
        g.setColor(color);
        g.fillRect(x, y, gridCellSize, gridCellSize);
        g.setColor(Color.black);
        g.drawRect(x, y, gridCellSize, gridCellSize);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        drawBlock(g);
        drawBackground(g);
    }
}
