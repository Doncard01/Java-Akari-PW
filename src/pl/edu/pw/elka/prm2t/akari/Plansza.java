package pl.edu.pw.elka.prm2t.akari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Plansza {

    private int rozmiar;
    private final Pole[][] plansza;
    private String[][] wybranaPlansza;

    private final static String[][] PLANSZA_LATWA = new String[][]{
            {"b", "b", "0", "b", "1", "b", "b"},
            {"b", "b", "b", "b", "1", "b", "b"},
            {"c", "c", "b", "b", "b", "b", "c"},
            {"b", "b", "b", "b", "b", "b", "b"},
            {"0", "b", "b", "b", "b", "1", "2"},
            {"b", "b", "c", "b", "b", "b", "b"},
            {"b", "b", "0", "b", "c", "b", "b"},
    };
    /* b - biale; c - czarne; z - zarowka; x - brak zarowki; p - pole podswietlone przez zarowke;
    0,1,2,3,4 - pola czarne z liczbami zarowek przylegajacych
     */
    private final static String[][] PLANSZA_SREDNIA = new String[][]{
            {"b", "b", "b", "b", "c", "b", "b", "b", "2", "b"},
            {"c", "b", "1", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "c", "b", "c", "b"},
            {"b", "b", "c", "b", "2", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "3", "b", "b", "c"},
            {"c", "b", "b", "0", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "2", "b", "2", "b", "b"},
            {"b", "2", "b", "2", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "c", "b", "0"},
            {"b", "1", "b", "b", "b", "c", "b", "b", "b", "b"}
    };

    private final static String[][] PLANSZA_TRUDNA = new String[][]{
            {"b", "b", "b", "b", "c", "b", "b", "c", "b", "b"},
            {"b", "b", "1", "b", "b", "b", "b", "b", "b", "b"},
            {"0", "b", "b", "b", "b", "b", "1", "b", "1", "b"},
            {"b", "b", "1", "b", "b", "1", "b", "b", "b", "b"},
            {"b", "b", "b", "0", "b", "b", "b", "b", "b", "2"},
            {"1", "b", "b", "b", "b", "b", "1", "b", "b", "b"},
            {"b", "b", "b", "b", "c", "b", "b", "2", "b", "b"},
            {"b", "0", "b", "1", "b", "b", "b", "b", "b", "1"},
            {"b", "b", "b", "b", "b", "b", "b", "c", "b", "b"},
            {"b", "b", "0", "b", "b", "c", "b", "b", "b", "b"}
    };

    public Plansza(int poziom) {
        if (poziom == 1) {
            this.rozmiar = 7;
            wybranaPlansza = PLANSZA_LATWA;
        }
        if (poziom == 2) {
            this.rozmiar = 10;
            wybranaPlansza = PLANSZA_SREDNIA;
        }
        if (poziom == 3) {
            this.rozmiar = 10;
            wybranaPlansza = PLANSZA_TRUDNA;
        }
        plansza = new Pole[rozmiar][rozmiar];

    }

    public JPanel generujPlansze() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rozmiar, rozmiar, 1, 1));

        for (int i = 0; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {
                plansza[i][j] = new Pole(i, j, wybranaPlansza[i][j]);
                addActionListener(plansza[i][j]);
                panel.add(plansza[i][j]);
            }
        }
        return panel;
    }

    private void addActionListener(Pole pole) {

        pole.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (Objects.equals(pole.getStan(), "b")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("z");
                     //   Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna())+1);
                    } else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("x");
                    }
                } else if (Objects.equals(pole.getStan(), "z")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("b");
                    } else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("x");
                    }
                } else if (Objects.equals(pole.getStan(), "x")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("z");
                    } else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("b");
                    }
                }

            }
        });
    }

    public int getRozmiar() {
        return rozmiar;
    }

    public Pole[][] getPlansza() {
        return plansza;
    }

    public Pole getPole(int wiersz, int kolumna){
        if (wiersz>rozmiar || kolumna>rozmiar){
            return null;
        }
        return plansza[wiersz][kolumna];

    }
    public void wypisanieNaKonsole(){
        for (int i = 0; i < rozmiar; i++) {
            System.out.println();
            for (int j = 0; j < rozmiar; j++) {
                System.out.print(plansza[i][j].getStan() + " ");
            }
        }

    }

}
