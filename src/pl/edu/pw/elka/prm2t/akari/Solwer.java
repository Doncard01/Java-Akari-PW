package pl.edu.pw.elka.prm2t.akari;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Solwer extends JFrame {

    JPanel panelPaneli = new JPanel();
    JButton zapisGry = new JButton("Zapis gry");
    JButton zapisPNG = new JButton("Zapisz obrazek");
    JButton hint = new JButton("Hint");
    JButton sprawdzenie = new JButton("Sprawdzenie");

    JPanel panelPrzyciskow = new JPanel();
    JPanel panelPodprzyciskow = new JPanel();

    JTextField info = new JTextField();

    public Solwer(Plansza plansza) {

        panelPaneli.setLayout(new BorderLayout());
        panelPaneli.add(panelPrzyciskow, BorderLayout.PAGE_START);

        panelPodprzyciskow.setLayout(new GridLayout(2, 2));
        panelPodprzyciskow.add(zapisGry);
        panelPodprzyciskow.add(zapisPNG);
        panelPodprzyciskow.add(hint);
        panelPodprzyciskow.add(sprawdzenie);
        panelPrzyciskow.setLayout(new GridLayout(2, 1));
        panelPrzyciskow.add(info);
        panelPrzyciskow.add(panelPodprzyciskow);

        info.setEditable(false);
        info.setHorizontalAlignment(JTextField.CENTER);
        info.setBackground(Color.WHITE);
        info.setFont(new Font("Tahoma", Font. BOLD, 14));

        JPanel panelPlanszy = plansza.generujPlansze();
        panelPlanszy.setSize(600, 600);
        plansza.wypisanieNaKonsole();
        getContentPane().removeAll();
        panelPaneli.add(panelPlanszy, BorderLayout.CENTER);
        getContentPane().add(panelPaneli);
        setSize(600, 700);
        revalidate();

        sprawdzenie.addActionListener(e3 -> {
            boolean blad = plansza.petleSprawdzajace();
            if (blad) {
                pokazanieTekstu(info, "Na planszy jest błąd! Znajdź go i napraw!", Color.red, 2500);
            }
            else {
                pokazanieTekstu(info, "Plansza poprawnie rozwiazana! Gratulacje!", new Color(56,166,78), 10000);
            }
        });

        zapisGry.addActionListener(e2 -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Zapisz grę");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                System.out.println("Plik zapisany jako: " + filePath);

                zapisTXT(filePath, plansza.getRozmiar(), plansza.getPlansza());

            } else {
                System.out.println("Plik nie został zapisany");
            }
        });
        zapisPNG.addActionListener(e2 -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Zapisz obrazek");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".png";
                System.out.println("Plik zapisany jako: " + filePath);

                Container contentPane = getContentPane();
                BufferedImage image = new BufferedImage(contentPane.getWidth(), contentPane.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = image.createGraphics();
                contentPane.printAll(g2d);
                g2d.dispose();
                try {
                    ImageIO.write(image, "png", new File(filePath));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Obraz nie został zapisany");
            }
        });

        hint.addActionListener(e33 -> {
            ArrayList<String> hinty = new ArrayList<>();
            hinty.add("Wszystkie białe pola muszą być oświetlone!");
            hinty.add("Żarówki nie mogą oświetlać siebie nawzajem!");
            hinty.add("Cyfry na czarnych polach oznaczają ile ma stać żarówek przy ich ścianach!");
            int random_int = (int) (Math.random()*3+1);
            String Tekst = hinty.get(random_int - 1);
            pokazanieTekstu(info, Tekst, Color.BLACK, 3000);
        });

        setVisible(true);
    }


    public void zapisTXT(String filepath, int rozmiar, Pole[][] plansza) {
        try (FileWriter writer = new FileWriter(filepath)) {
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

    public void pokazanieTekstu(JTextField info, String text, Color color, int czas){
        ActionListener clearTask = e12 ->
                info.setText("");

        ActionListener task = e22 -> {
            info.setText(text);
            info.setForeground(color);
        };
        boolean ready = Objects.equals(info.getText(), "");

        if(ready) {
            Timer timer = new Timer(0, task);
            Timer timerClear = new Timer(czas, clearTask);
            timer.start();
            timer.setRepeats(false);
            timerClear.start();
            timerClear.setRepeats(false);
        }
    }
}
