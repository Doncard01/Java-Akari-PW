package pl.edu.pw.elka.prm2t.akari;

import javax.swing.*;
import java.awt.*;

public class Main {

    public void openFrame(JFrame frame) {
        frame.setVisible(true);
    }

    public void closeFrame(JFrame frame) {
        frame.setVisible(false);
    }

    public Main() {
        JLabel label = new JLabel("AKARI    ", SwingConstants.CENTER);
        label.setFont(new Font("defaultFont", Font.PLAIN, 32));
        ImageIcon icon = new ImageIcon("resources/light.png");
        Image temp = icon.getImage();
        Image temp2 = temp.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(temp2);
        label.setIcon(icon);

        JLabel label2 = new JLabel("Mateusz Mięgoć-Kowalski", SwingConstants.CENTER);
        JLabel label3 = new JLabel("Mateusz Dybicz", SwingConstants.CENTER);
        JLabel label4 = new JLabel("Radosław Szawłowski", SwingConstants.CENTER);
        JLabel label5 = new JLabel("Adam Staciwa", SwingConstants.CENTER);

        JFrame frame = new JFrame();
        JFrame frame2 = new JFrame();

        JPanel panel = new JPanel();
        JPanel panelBot = new JPanel();
        JPanel panel2 = new JPanel();

        JButton nowaGra = new JButton("Nowa gra");
        nowaGra.addActionListener(e->{

            Plansza plansza = new Plansza(3);
            JPanel panelPlanszy = plansza.generujPlansze();
            plansza.wypisanieNaKonsole();
            frame.getContentPane().removeAll();
            frame.getContentPane().add(panelPlanszy);
            frame.revalidate();
        });

        JButton wczytaj = new JButton("Wczytaj");

        JButton zamknij = new JButton("Zamknij");
        zamknij.addActionListener(e -> System.exit(1));

        panelBot.setBorder(BorderFactory.createEmptyBorder(15, 130, 0, 130));
        panelBot.setLayout(new GridLayout(4, 0, 20, 20));
        panelBot.add(label2);
        panelBot.add(label3);
        panelBot.add(label4);
        panelBot.add(label5);

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 15, 30));
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.PAGE_START);
        panel.add(wczytaj, BorderLayout.LINE_START);
        panel.add(nowaGra, BorderLayout.CENTER);
        panel.add(zamknij, BorderLayout.LINE_END);
        panel.add(panelBot, BorderLayout.PAGE_END);


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Gra Akari");
        frame.setSize(600, 600);
        openFrame(frame);


    }




    public static void main(String[] args) {
        new Main();
    }
}
