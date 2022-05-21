package pl.edu.pw.elka.prm2t.akari;

import javax.swing.*;
import java.awt.*;

public class Main {

    public Main() {
        JLabel label = new JLabel("AKARI");

        JFrame frame = new JFrame();
        JFrame frame2 = new JFrame();

        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();

        JButton nowaGra = new JButton("Nowa gra");
        JButton wczytaj = new JButton("Wczytaj");
        JButton zamknij = new JButton("Zamknij");

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.PAGE_START);
        panel.add(wczytaj, BorderLayout.LINE_START);
        panel.add(nowaGra, BorderLayout.CENTER);
        panel.add(zamknij, BorderLayout.LINE_END);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Gra Akari");
        frame.pack();
        frame.setVisible(true);


    }




    public static void main(String[] args) {
        new Main();
    }
}
