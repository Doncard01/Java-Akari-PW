package pl.edu.pw.elka.prm2t.akari;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

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
        Image temp2 = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        icon = new ImageIcon(temp2);
        label.setIcon(icon);

        JLabel label2 = new JLabel("Mateusz Mięgoć-Kowalski", SwingConstants.CENTER);
        JLabel label3 = new JLabel("Mateusz Dybicz", SwingConstants.CENTER);
        JLabel label4 = new JLabel("Radosław Szawłowski", SwingConstants.CENTER);
        JLabel label5 = new JLabel("Adam Staciwa", SwingConstants.CENTER);

        JLabel label6 = new JLabel("Wybierz poziom trudności", SwingConstants.CENTER);

        JFrame frame = new JFrame();
        JFrame frame2 = new JFrame();
        JFrame ramkawyboru = new JFrame();

        JPanel panel = new JPanel();
        JPanel panelBot = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panelwyboru = new JPanel();

        JButton easy = new JButton("Poziom łatwy");
        JButton mid = new JButton("Poziom średni");
        JButton hard = new JButton("Poziom trudny");

        JButton zapisGry = new JButton("Zapis gry");
        JButton zapisPNG = new JButton("Zapisz obrazek");
        JButton hint = new JButton("Hint");
        JButton sprawdzenie = new JButton("Sprawdzenie");

        JPanel panelPrzyciskow = new JPanel();
        panelPrzyciskow.setLayout(new GridLayout(2, 2));
        panelPrzyciskow.add(zapisGry);
        panelPrzyciskow.add(zapisPNG);
        panelPrzyciskow.add(hint);
        panelPrzyciskow.add(sprawdzenie);



        JPanel panelPaneli = new JPanel();
        panelPaneli.setLayout(new BorderLayout());
        panelPaneli.add(panelPrzyciskow, BorderLayout.PAGE_START);

        JButton nowaGra = new JButton("Nowa gra");
        nowaGra.addActionListener(e->{
            closeFrame(frame);

            panelwyboru.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            panelwyboru.setLayout(new GridLayout(5,1,20,20));
            panelwyboru.add(label6);
            panelwyboru.add(easy);
            panelwyboru.add(mid);
            panelwyboru.add(hard);

            ramkawyboru.add(panelwyboru);
            ramkawyboru.setDefaultCloseOperation(EXIT_ON_CLOSE);
            ramkawyboru.setTitle("Wybór poziomu trudności");
            ramkawyboru.setSize(600,600);
            ramkawyboru.setLocationRelativeTo(null);
            ramkawyboru.setVisible(true);

            easy.addActionListener(e1 -> {
                Plansza plansza = new Plansza(1);
                JPanel panelPlanszy = plansza.generujPlansze();
                panelPlanszy.setSize(600, 600);
                plansza.wypisanieNaKonsole();
                frame.getContentPane().removeAll();
                panelPaneli.add(panelPlanszy, BorderLayout.CENTER);
                frame.getContentPane().add(panelPaneli);
                frame.setSize(600, 650);
                frame.revalidate();
                closeFrame(ramkawyboru);
                openFrame(frame);

                zapisGry.addActionListener(e2 -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Zapisz grę");
                    int userSelection = fileChooser.showSaveDialog(frame);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        System.out.println("Plik zapisany jako: " + filePath);

                        zapisCSV(filePath, plansza.getRozmiar(), plansza.getPlansza());

                    } else {
                        System.out.println("Plik nie został zapisany");
                    }
                });
            });
            mid.addActionListener(e1 -> {
                Plansza plansza = new Plansza(2);
                JPanel panelPlanszy = plansza.generujPlansze();
                panelPlanszy.setSize(600, 600);
                plansza.wypisanieNaKonsole();
                frame.getContentPane().removeAll();
                panelPaneli.add(panelPlanszy, BorderLayout.CENTER);
                frame.getContentPane().add(panelPaneli);
                frame.setSize(600, 650);
                frame.revalidate();
                closeFrame(ramkawyboru);
                openFrame(frame);

                zapisGry.addActionListener(e2 -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Zapisz grę");
                    int userSelection = fileChooser.showSaveDialog(frame);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        System.out.println("Plik zapisany jako: " + filePath);

                        zapisCSV(filePath, plansza.getRozmiar(), plansza.getPlansza());

                    } else {
                        System.out.println("Plik nie został zapisany");
                    }
                });
            });
            hard.addActionListener(e1 -> {
                Plansza plansza = new Plansza(3);
                JPanel panelPlanszy = plansza.generujPlansze();
                panelPlanszy.setSize(600, 600);
                plansza.wypisanieNaKonsole();
                frame.getContentPane().removeAll();
                panelPaneli.add(panelPlanszy, BorderLayout.CENTER);
                frame.getContentPane().add(panelPaneli);
                frame.setSize(600, 650);
                frame.revalidate();
                closeFrame(ramkawyboru);
                openFrame(frame);

                zapisGry.addActionListener(e2 -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Zapisz grę");
                    int userSelection = fileChooser.showSaveDialog(frame);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        System.out.println("Plik zapisany jako: " + filePath);

                        zapisCSV(filePath, plansza.getRozmiar(), plansza.getPlansza());

                    } else {
                        System.out.println("Plik nie został zapisany");
                    }
                });
            });
        });

        JButton wczytaj = new JButton("Wczytaj");
        wczytaj.addActionListener(e -> {
            List<String[]> csv = new ArrayList<>();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Wczytaj grę");
            int userSelection = fileChooser.showOpenDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                System.out.println("Wczytany plik: " + filePath);

                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(" ");
                        csv.add(values);
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }


                Plansza plansza = new Plansza(csv);
                JPanel panelPlanszy = plansza.generujPlansze();
                panelPlanszy.setSize(600, 600);
                plansza.wypisanieNaKonsole();
                frame.getContentPane().removeAll();
                panelPaneli.add(panelPlanszy, BorderLayout.CENTER);
                frame.getContentPane().add(panelPaneli);
                frame.setSize(600, 650);
                frame.revalidate();
                closeFrame(ramkawyboru);
                openFrame(frame);

                zapisGry.addActionListener(e2 -> {
                    JFileChooser fileChooser1 = new JFileChooser();
                    fileChooser.setDialogTitle("Zapisz grę");
                    int userSelection1 = fileChooser1.showSaveDialog(frame);
                    if (userSelection1 == JFileChooser.APPROVE_OPTION) {
                        String filePath1 = fileChooser1.getSelectedFile().getAbsolutePath();
                        System.out.println("Plik zapisany jako: " + filePath1);

                        zapisCSV(filePath1, plansza.getRozmiar(), plansza.getPlansza());

                    } else {
                        System.out.println("Plik nie został zapisany");
                    }
                });
            }
        });

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
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setTitle("Gra Akari");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        openFrame(frame);


    }

    public void zapisCSV(String filepath, int rozmiar, Pole[][] plansza) {
        try (FileWriter writer = new FileWriter(filepath);) {
            writer.append(String.valueOf(rozmiar)).append("\n");
            for (int i = 0; i < rozmiar; i++) {
                for (int j = 0; j < rozmiar; j++) {
                   writer.append(plansza[i][j].getStan()).append(" ");
                }
                writer.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
