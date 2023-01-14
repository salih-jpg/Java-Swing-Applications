package Tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameForm extends JFrame {

    public JLabel scoreDisplay ;
    public JLabel levelDisplay;

    public GameArea ga;
    GameForm(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(750,750);
        this.setLayout(null);
        ga = new GameArea(16);
        this.add(ga);
        scoreDisplay = new JLabel("Score: " + 0);
        scoreDisplay.setBounds(430, 10, 100, 30);
        levelDisplay = new JLabel("Level: " + 1);
        levelDisplay.setBounds(430, 60, 100, 30);
        this.add(scoreDisplay);
        this.add(levelDisplay);

        initControls();
        startGame();

    }

    private void initControls(){
        InputMap im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");

        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockRight();
                repaint();
            }
        });

        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockLeft();
                repaint();
            }
        });

        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.rotateBlock();
            }
        });

        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.dropBlock();
            }
        });
    }

    public void updateScore(int score){
        scoreDisplay.setText("Score: " + score);
    }

    public void updateLevel(int level){
        levelDisplay.setText("Level: " + level);
    }

    public void startGame(){
        new GameThread(ga, this).start();
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameForm().setVisible(true);
            }
        });
    }

}