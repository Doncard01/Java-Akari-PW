package pl.edu.pw.elka.prm2t.akari;

import javax.swing.*;
import java.awt.*;

public class Main {

    public Main() {
        JLabel label = new JLabel("AKARI", SwingConstants.CENTER);
        label.setFont(new Font("defaultFont", Font.PLAIN, 32));

        JLabel label2 = new JLabel("Mateusz Mięgoć-Kowalski", SwingConstants.CENTER);
        JLabel label3 = new JLabel("Mateusz Dybicz", SwingConstants.CENTER);
        JLabel label4 = new JLabel("Mateusz Mięgoć Kowalski", SwingConstants.CENTER);
        JLabel label5 = new JLabel("Mateusz Mięgoć Kowalski", SwingConstants.CENTER);

        JFrame frame = new JFrame();
        JFrame frame2 = new JFrame();

        JPanel panel = new JPanel();
        JPanel panelBot = new JPanel();
        JPanel panel2 = new JPanel();

        JButton nowaGra = new JButton("Nowa gra");
        JButton wczytaj = new JButton("Wczytaj");
        JButton zamknij = new JButton("Zamknij");

        panelBot.setBorder(BorderFactory.createEmptyBorder(30, 130, 30, 130));
        panelBot.setLayout(new GridLayout(4, 0, 20, 20));
        panelBot.add(label2);
        panelBot.add(label3);
        panelBot.add(label4);
        panelBot.add(label5);

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.PAGE_START);
        panel.add(wczytaj, BorderLayout.LINE_START);
        panel.add(nowaGra, BorderLayout.CENTER);
        panel.add(zamknij, BorderLayout.LINE_END);
        panel.add(panelBot, BorderLayout.PAGE_END);


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Gra Akari");
        frame.setSize(600, 500);
        //frame.pack();
        frame.setVisible(true);


    }




    public static void main(String[] args) {
        new Main();
    }
}
