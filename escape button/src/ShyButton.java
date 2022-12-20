import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ShyButton extends JFrame implements MouseMotionListener {
    JButton tButton = new JButton("can't touch this");
    ShyButton(){
        setLayout(new FlowLayout());
        this.add(tButton);
       tButton.addMouseMotionListener(this);

    }



    public static void main(String[] args) {

        ShyButton myGui = new ShyButton();
        myGui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        myGui.setTitle("click the button");
        myGui.setSize(500,500);
        myGui.setVisible(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
            Random rand= new Random();
            int a = rand.nextInt(300);
            int b = rand.nextInt(300);
            tButton.setBounds(a, b, 100,40);

    }
}
