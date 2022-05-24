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
                        for(int i = 1; i<(rozmiar - pole.getKolumna()); i++){
                            Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna())+i);
                            if (Objects.equals(poleZNastepnejKolumny.getStan(), "c")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "0")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "1")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "2")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepnejKolumny.getStan()) {
                                case "x" -> poleZNastepnejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZNastepnejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZNastepnejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZNastepnejKolumny.zmienStan("xp4");
                                case "p1" -> poleZNastepnejKolumny.zmienStan("p2");
                                case "p2" -> poleZNastepnejKolumny.zmienStan("p3");
                                case "p3" -> poleZNastepnejKolumny.zmienStan("p4");
                                case "z" -> poleZNastepnejKolumny.zmienStan("z1");
                                case "z1" -> poleZNastepnejKolumny.zmienStan("z2");
                                case "z2" -> poleZNastepnejKolumny.zmienStan("z3");
                                case "z3" -> poleZNastepnejKolumny.zmienStan("z4");
                                default -> poleZNastepnejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getKolumna()-1); i>=0; i--){
                            Pole poleZPoprzedniejKolumny = getPole(pole.getWiersz(), (i));
                            if (Objects.equals(poleZPoprzedniejKolumny.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniejKolumny.getStan()) {
                                case "x" -> poleZPoprzedniejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniejKolumny.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniejKolumny.zmienStan("p2");
                                case "p2" -> poleZPoprzedniejKolumny.zmienStan("p3");
                                case "p3" -> poleZPoprzedniejKolumny.zmienStan("p4");
                                case "z" -> poleZPoprzedniejKolumny.zmienStan("z1");
                                case "z1" -> poleZPoprzedniejKolumny.zmienStan("z2");
                                case "z2" -> poleZPoprzedniejKolumny.zmienStan("z3");
                                case "z3" -> poleZPoprzedniejKolumny.zmienStan("z4");
                                default -> poleZPoprzedniejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getWiersz()-1); i>=0; i--){
                            Pole poleZPoprzedniegoWiersza = getPole(i, (pole.getKolumna()));
                            if (Objects.equals(poleZPoprzedniegoWiersza.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniegoWiersza.getStan()) {
                                case "x" -> poleZPoprzedniegoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniegoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniegoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniegoWiersza.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniegoWiersza.zmienStan("p2");
                                case "p2" -> poleZPoprzedniegoWiersza.zmienStan("p3");
                                case "p3" -> poleZPoprzedniegoWiersza.zmienStan("p4");
                                case "z" -> poleZPoprzedniegoWiersza.zmienStan("z1");
                                case "z1" -> poleZPoprzedniegoWiersza.zmienStan("z2");
                                case "z2" -> poleZPoprzedniegoWiersza.zmienStan("z3");
                                case "z3" -> poleZPoprzedniegoWiersza.zmienStan("z4");
                                default -> poleZPoprzedniegoWiersza.zmienStan("p1");
                            }
                        }
                        for(int i = 1; i<(rozmiar - pole.getWiersz()); i++){
                            Pole poleZNastepengoWiersza = getPole((pole.getWiersz()+i), (pole.getKolumna()));
                            if (Objects.equals(poleZNastepengoWiersza.getStan(), "c")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "0")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "1")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "2")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepengoWiersza.getStan()) {
                                case "x" -> poleZNastepengoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZNastepengoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZNastepengoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZNastepengoWiersza.zmienStan("xp4");
                                case "p1" -> poleZNastepengoWiersza.zmienStan("p2");
                                case "p2" -> poleZNastepengoWiersza.zmienStan("p3");
                                case "p3" -> poleZNastepengoWiersza.zmienStan("p4");
                                case "z" -> poleZNastepengoWiersza.zmienStan("z1");
                                case "z1" -> poleZNastepengoWiersza.zmienStan("z2");
                                case "z2" -> poleZNastepengoWiersza.zmienStan("z3");
                                case "z3" -> poleZNastepengoWiersza.zmienStan("z4");
                                default -> poleZNastepengoWiersza.zmienStan("p1");
                            }
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("x");
                    }
                }
                else if (Objects.equals(pole.getStan(), "p1")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("z1");
                        for(int i = 1; i<(rozmiar - pole.getKolumna()); i++){
                            Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna())+i);
                            if (Objects.equals(poleZNastepnejKolumny.getStan(), "c")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "0")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "1")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "2")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepnejKolumny.getStan()) {
                                case "x" -> poleZNastepnejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZNastepnejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZNastepnejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZNastepnejKolumny.zmienStan("xp4");
                                case "p1" -> poleZNastepnejKolumny.zmienStan("p2");
                                case "p2" -> poleZNastepnejKolumny.zmienStan("p3");
                                case "p3" -> poleZNastepnejKolumny.zmienStan("p4");
                                case "z" -> poleZNastepnejKolumny.zmienStan("z1");
                                case "z1" -> poleZNastepnejKolumny.zmienStan("z2");
                                case "z2" -> poleZNastepnejKolumny.zmienStan("z3");
                                case "z3" -> poleZNastepnejKolumny.zmienStan("z4");
                                default -> poleZNastepnejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getKolumna()-1); i>=0; i--){
                            Pole poleZPoprzedniejKolumny = getPole(pole.getWiersz(), (i));
                            if (Objects.equals(poleZPoprzedniejKolumny.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniejKolumny.getStan()) {
                                case "x" -> poleZPoprzedniejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniejKolumny.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniejKolumny.zmienStan("p2");
                                case "p2" -> poleZPoprzedniejKolumny.zmienStan("p3");
                                case "p3" -> poleZPoprzedniejKolumny.zmienStan("p4");
                                case "z" -> poleZPoprzedniejKolumny.zmienStan("z1");
                                case "z1" -> poleZPoprzedniejKolumny.zmienStan("z2");
                                case "z2" -> poleZPoprzedniejKolumny.zmienStan("z3");
                                case "z3" -> poleZPoprzedniejKolumny.zmienStan("z4");
                                default -> poleZPoprzedniejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getWiersz()-1); i>=0; i--){
                            Pole poleZPoprzedniegoWiersza = getPole(i, (pole.getKolumna()));
                            if (Objects.equals(poleZPoprzedniegoWiersza.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniegoWiersza.getStan()) {
                                case "x" -> poleZPoprzedniegoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniegoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniegoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniegoWiersza.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniegoWiersza.zmienStan("p2");
                                case "p2" -> poleZPoprzedniegoWiersza.zmienStan("p3");
                                case "p3" -> poleZPoprzedniegoWiersza.zmienStan("p4");
                                case "z" -> poleZPoprzedniegoWiersza.zmienStan("z1");
                                case "z1" -> poleZPoprzedniegoWiersza.zmienStan("z2");
                                case "z2" -> poleZPoprzedniegoWiersza.zmienStan("z3");
                                case "z3" -> poleZPoprzedniegoWiersza.zmienStan("z4");
                                default -> poleZPoprzedniegoWiersza.zmienStan("p1");
                            }
                        }
                        for(int i = 1; i<(rozmiar - pole.getWiersz()); i++){
                            Pole poleZNastepengoWiersza = getPole((pole.getWiersz()+i), (pole.getKolumna()));
                            if (Objects.equals(poleZNastepengoWiersza.getStan(), "c")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "0")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "1")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "2")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepengoWiersza.getStan()) {
                                case "x" -> poleZNastepengoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZNastepengoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZNastepengoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZNastepengoWiersza.zmienStan("xp4");
                                case "p1" -> poleZNastepengoWiersza.zmienStan("p2");
                                case "p2" -> poleZNastepengoWiersza.zmienStan("p3");
                                case "p3" -> poleZNastepengoWiersza.zmienStan("p4");
                                case "z" -> poleZNastepengoWiersza.zmienStan("z1");
                                case "z1" -> poleZNastepengoWiersza.zmienStan("z2");
                                case "z2" -> poleZNastepengoWiersza.zmienStan("z3");
                                case "z3" -> poleZNastepengoWiersza.zmienStan("z4");
                                default -> poleZNastepengoWiersza.zmienStan("p1");
                            }
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("xp1");
                    }
                }
                else if (Objects.equals(pole.getStan(), "p2")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("z2");
                        for(int i = 1; i<(rozmiar - pole.getKolumna()); i++){
                            Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna())+i);
                            if (Objects.equals(poleZNastepnejKolumny.getStan(), "c")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "0")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "1")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "2")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepnejKolumny.getStan()) {
                                case "x" -> poleZNastepnejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZNastepnejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZNastepnejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZNastepnejKolumny.zmienStan("xp4");
                                case "p1" -> poleZNastepnejKolumny.zmienStan("p2");
                                case "p2" -> poleZNastepnejKolumny.zmienStan("p3");
                                case "p3" -> poleZNastepnejKolumny.zmienStan("p4");
                                case "z" -> poleZNastepnejKolumny.zmienStan("z1");
                                case "z1" -> poleZNastepnejKolumny.zmienStan("z2");
                                case "z2" -> poleZNastepnejKolumny.zmienStan("z3");
                                case "z3" -> poleZNastepnejKolumny.zmienStan("z4");
                                default -> poleZNastepnejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getKolumna()-1); i>=0; i--){
                            Pole poleZPoprzedniejKolumny = getPole(pole.getWiersz(), (i));
                            if (Objects.equals(poleZPoprzedniejKolumny.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniejKolumny.getStan()) {
                                case "x" -> poleZPoprzedniejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniejKolumny.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniejKolumny.zmienStan("p2");
                                case "p2" -> poleZPoprzedniejKolumny.zmienStan("p3");
                                case "p3" -> poleZPoprzedniejKolumny.zmienStan("p4");
                                case "z" -> poleZPoprzedniejKolumny.zmienStan("z1");
                                case "z1" -> poleZPoprzedniejKolumny.zmienStan("z2");
                                case "z2" -> poleZPoprzedniejKolumny.zmienStan("z3");
                                case "z3" -> poleZPoprzedniejKolumny.zmienStan("z4");
                                default -> poleZPoprzedniejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getWiersz()-1); i>=0; i--){
                            Pole poleZPoprzedniegoWiersza = getPole(i, (pole.getKolumna()));
                            if (Objects.equals(poleZPoprzedniegoWiersza.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniegoWiersza.getStan()) {
                                case "x" -> poleZPoprzedniegoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniegoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniegoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniegoWiersza.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniegoWiersza.zmienStan("p2");
                                case "p2" -> poleZPoprzedniegoWiersza.zmienStan("p3");
                                case "p3" -> poleZPoprzedniegoWiersza.zmienStan("p4");
                                case "z" -> poleZPoprzedniegoWiersza.zmienStan("z1");
                                case "z1" -> poleZPoprzedniegoWiersza.zmienStan("z2");
                                case "z2" -> poleZPoprzedniegoWiersza.zmienStan("z3");
                                case "z3" -> poleZPoprzedniegoWiersza.zmienStan("z4");
                                default -> poleZPoprzedniegoWiersza.zmienStan("p1");
                            }
                        }
                        for(int i = 1; i<(rozmiar - pole.getWiersz()); i++){
                            Pole poleZNastepengoWiersza = getPole((pole.getWiersz()+i), (pole.getKolumna()));
                            if (Objects.equals(poleZNastepengoWiersza.getStan(), "c")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "0")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "1")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "2")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepengoWiersza.getStan()) {
                                case "x" -> poleZNastepengoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZNastepengoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZNastepengoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZNastepengoWiersza.zmienStan("xp4");
                                case "p1" -> poleZNastepengoWiersza.zmienStan("p2");
                                case "p2" -> poleZNastepengoWiersza.zmienStan("p3");
                                case "p3" -> poleZNastepengoWiersza.zmienStan("p4");
                                case "z" -> poleZNastepengoWiersza.zmienStan("z1");
                                case "z1" -> poleZNastepengoWiersza.zmienStan("z2");
                                case "z2" -> poleZNastepengoWiersza.zmienStan("z3");
                                case "z3" -> poleZNastepengoWiersza.zmienStan("z4");
                                default -> poleZNastepengoWiersza.zmienStan("p1");
                            }
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("xp2");
                    }
                }
                else if (Objects.equals(pole.getStan(), "p3")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("z3");
                        for(int i = 1; i<(rozmiar - pole.getKolumna()); i++){
                            Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna())+i);
                            if (Objects.equals(poleZNastepnejKolumny.getStan(), "c")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "0")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "1")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "2")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepnejKolumny.getStan()) {
                                case "x" -> poleZNastepnejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZNastepnejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZNastepnejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZNastepnejKolumny.zmienStan("xp4");
                                case "p1" -> poleZNastepnejKolumny.zmienStan("p2");
                                case "p2" -> poleZNastepnejKolumny.zmienStan("p3");
                                case "p3" -> poleZNastepnejKolumny.zmienStan("p4");
                                case "z" -> poleZNastepnejKolumny.zmienStan("z1");
                                case "z1" -> poleZNastepnejKolumny.zmienStan("z2");
                                case "z2" -> poleZNastepnejKolumny.zmienStan("z3");
                                case "z3" -> poleZNastepnejKolumny.zmienStan("z4");
                                default -> poleZNastepnejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getKolumna()-1); i>=0; i--){
                            Pole poleZPoprzedniejKolumny = getPole(pole.getWiersz(), (i));
                            if (Objects.equals(poleZPoprzedniejKolumny.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniejKolumny.getStan()) {
                                case "x" -> poleZPoprzedniejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniejKolumny.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniejKolumny.zmienStan("p2");
                                case "p2" -> poleZPoprzedniejKolumny.zmienStan("p3");
                                case "p3" -> poleZPoprzedniejKolumny.zmienStan("p4");
                                case "z" -> poleZPoprzedniejKolumny.zmienStan("z1");
                                case "z1" -> poleZPoprzedniejKolumny.zmienStan("z2");
                                case "z2" -> poleZPoprzedniejKolumny.zmienStan("z3");
                                case "z3" -> poleZPoprzedniejKolumny.zmienStan("z4");
                                default -> poleZPoprzedniejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getWiersz()-1); i>=0; i--){
                            Pole poleZPoprzedniegoWiersza = getPole(i, (pole.getKolumna()));
                            if (Objects.equals(poleZPoprzedniegoWiersza.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniegoWiersza.getStan()) {
                                case "x" -> poleZPoprzedniegoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniegoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniegoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniegoWiersza.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniegoWiersza.zmienStan("p2");
                                case "p2" -> poleZPoprzedniegoWiersza.zmienStan("p3");
                                case "p3" -> poleZPoprzedniegoWiersza.zmienStan("p4");
                                case "z" -> poleZPoprzedniegoWiersza.zmienStan("z1");
                                case "z1" -> poleZPoprzedniegoWiersza.zmienStan("z2");
                                case "z2" -> poleZPoprzedniegoWiersza.zmienStan("z3");
                                case "z3" -> poleZPoprzedniegoWiersza.zmienStan("z4");
                                default -> poleZPoprzedniegoWiersza.zmienStan("p1");
                            }
                        }
                        for(int i = 1; i<(rozmiar - pole.getWiersz()); i++){
                            Pole poleZNastepengoWiersza = getPole((pole.getWiersz()+i), (pole.getKolumna()));
                            if (Objects.equals(poleZNastepengoWiersza.getStan(), "c")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "0")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "1")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "2")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepengoWiersza.getStan()) {
                                case "x" -> poleZNastepengoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZNastepengoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZNastepengoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZNastepengoWiersza.zmienStan("xp4");
                                case "p1" -> poleZNastepengoWiersza.zmienStan("p2");
                                case "p2" -> poleZNastepengoWiersza.zmienStan("p3");
                                case "p3" -> poleZNastepengoWiersza.zmienStan("p4");
                                case "z" -> poleZNastepengoWiersza.zmienStan("z1");
                                case "z1" -> poleZNastepengoWiersza.zmienStan("z2");
                                case "z2" -> poleZNastepengoWiersza.zmienStan("z3");
                                case "z3" -> poleZNastepengoWiersza.zmienStan("z4");
                                default -> poleZNastepengoWiersza.zmienStan("p1");
                            }
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("xp3");
                    }
                }
                else if (Objects.equals(pole.getStan(), "p4")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("z4");
                        for(int i = 1; i<(rozmiar - pole.getKolumna()); i++){
                            Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna())+i);
                            if (Objects.equals(poleZNastepnejKolumny.getStan(), "c")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "0")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "1")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "2")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepnejKolumny.getStan()) {
                                case "x" -> poleZNastepnejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZNastepnejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZNastepnejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZNastepnejKolumny.zmienStan("xp4");
                                case "p1" -> poleZNastepnejKolumny.zmienStan("p2");
                                case "p2" -> poleZNastepnejKolumny.zmienStan("p3");
                                case "p3" -> poleZNastepnejKolumny.zmienStan("p4");
                                case "z" -> poleZNastepnejKolumny.zmienStan("z1");
                                case "z1" -> poleZNastepnejKolumny.zmienStan("z2");
                                case "z2" -> poleZNastepnejKolumny.zmienStan("z3");
                                case "z3" -> poleZNastepnejKolumny.zmienStan("z4");
                                default -> poleZNastepnejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getKolumna()-1); i>=0; i--){
                            Pole poleZPoprzedniejKolumny = getPole(pole.getWiersz(), (i));
                            if (Objects.equals(poleZPoprzedniejKolumny.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniejKolumny.getStan()) {
                                case "x" -> poleZPoprzedniejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniejKolumny.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniejKolumny.zmienStan("p2");
                                case "p2" -> poleZPoprzedniejKolumny.zmienStan("p3");
                                case "p3" -> poleZPoprzedniejKolumny.zmienStan("p4");
                                case "z" -> poleZPoprzedniejKolumny.zmienStan("z1");
                                case "z1" -> poleZPoprzedniejKolumny.zmienStan("z2");
                                case "z2" -> poleZPoprzedniejKolumny.zmienStan("z3");
                                case "z3" -> poleZPoprzedniejKolumny.zmienStan("z4");
                                default -> poleZPoprzedniejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getWiersz()-1); i>=0; i--){
                            Pole poleZPoprzedniegoWiersza = getPole(i, (pole.getKolumna()));
                            if (Objects.equals(poleZPoprzedniegoWiersza.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniegoWiersza.getStan()) {
                                case "x" -> poleZPoprzedniegoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniegoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniegoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniegoWiersza.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniegoWiersza.zmienStan("p2");
                                case "p2" -> poleZPoprzedniegoWiersza.zmienStan("p3");
                                case "p3" -> poleZPoprzedniegoWiersza.zmienStan("p4");
                                case "z" -> poleZPoprzedniegoWiersza.zmienStan("z1");
                                case "z1" -> poleZPoprzedniegoWiersza.zmienStan("z2");
                                case "z2" -> poleZPoprzedniegoWiersza.zmienStan("z3");
                                case "z3" -> poleZPoprzedniegoWiersza.zmienStan("z4");
                                default -> poleZPoprzedniegoWiersza.zmienStan("p1");
                            }
                        }
                        for(int i = 1; i<(rozmiar - pole.getWiersz()); i++){
                            Pole poleZNastepengoWiersza = getPole((pole.getWiersz()+i), (pole.getKolumna()));
                            if (Objects.equals(poleZNastepengoWiersza.getStan(), "c")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "0")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "1")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "2")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepengoWiersza.getStan()) {
                                case "x" -> poleZNastepengoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZNastepengoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZNastepengoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZNastepengoWiersza.zmienStan("xp4");
                                case "p1" -> poleZNastepengoWiersza.zmienStan("p2");
                                case "p2" -> poleZNastepengoWiersza.zmienStan("p3");
                                case "p3" -> poleZNastepengoWiersza.zmienStan("p4");
                                case "z" -> poleZNastepengoWiersza.zmienStan("z1");
                                case "z1" -> poleZNastepengoWiersza.zmienStan("z2");
                                case "z2" -> poleZNastepengoWiersza.zmienStan("z3");
                                case "z3" -> poleZNastepengoWiersza.zmienStan("z4");
                                default -> poleZNastepengoWiersza.zmienStan("p1");
                            }
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("xp4");
                    }
                }
                else if (Objects.equals(pole.getStan(), "z")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("b");
                        for(int i = 1; i<(rozmiar - pole.getKolumna()); i++){
                            Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna())+i);
                            if (Objects.equals(poleZNastepnejKolumny.getStan(), "c")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "0")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "1")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "2")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepnejKolumny.getStan()) {
                                case "xp1" -> poleZNastepnejKolumny.zmienStan("x");
                                case "xp2" -> poleZNastepnejKolumny.zmienStan("xp1");
                                case "xp3" -> poleZNastepnejKolumny.zmienStan("xp2");
                                case "xp4" -> poleZNastepnejKolumny.zmienStan("xp3");
                                case "p2" -> poleZNastepnejKolumny.zmienStan("p1");
                                case "p3" -> poleZNastepnejKolumny.zmienStan("p2");
                                case "p4" -> poleZNastepnejKolumny.zmienStan("p3");
                                case "z1" -> poleZNastepnejKolumny.zmienStan("z");
                                case "z2" -> poleZNastepnejKolumny.zmienStan("z1");
                                case "z3" -> poleZNastepnejKolumny.zmienStan("z2");
                                case "z4" -> poleZNastepnejKolumny.zmienStan("z3");
                                default -> poleZNastepnejKolumny.zmienStan("b");
                            }
                        }
                        for(int i = (pole.getKolumna()-1); i>=0; i--){
                            Pole poleZPoprzedniejKolumny = getPole(pole.getWiersz(), (i));
                            if (Objects.equals(poleZPoprzedniejKolumny.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniejKolumny.getStan()) {
                                case "xp1" -> poleZPoprzedniejKolumny.zmienStan("x");
                                case "xp2" -> poleZPoprzedniejKolumny.zmienStan("xp1");
                                case "xp3" -> poleZPoprzedniejKolumny.zmienStan("xp2");
                                case "xp4" -> poleZPoprzedniejKolumny.zmienStan("xp3");
                                case "p2" -> poleZPoprzedniejKolumny.zmienStan("p1");
                                case "p3" -> poleZPoprzedniejKolumny.zmienStan("p2");
                                case "p4" -> poleZPoprzedniejKolumny.zmienStan("p3");
                                case "z1" -> poleZPoprzedniejKolumny.zmienStan("z");
                                case "z2" -> poleZPoprzedniejKolumny.zmienStan("z1");
                                case "z3" -> poleZPoprzedniejKolumny.zmienStan("z2");
                                case "z4" -> poleZPoprzedniejKolumny.zmienStan("z3");
                                default -> poleZPoprzedniejKolumny.zmienStan("b");
                            }
                        }
                        for(int i = (pole.getWiersz()-1); i>=0; i--){
                            Pole poleZPoprzedniegoWiersza = getPole(i, (pole.getKolumna()));
                            if (Objects.equals(poleZPoprzedniegoWiersza.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniegoWiersza.getStan()) {
                                case "xp1" -> poleZPoprzedniegoWiersza.zmienStan("x");
                                case "xp2" -> poleZPoprzedniegoWiersza.zmienStan("xp1");
                                case "xp3" -> poleZPoprzedniegoWiersza.zmienStan("xp2");
                                case "xp4" -> poleZPoprzedniegoWiersza.zmienStan("xp3");
                                case "p2" -> poleZPoprzedniegoWiersza.zmienStan("p1");
                                case "p3" -> poleZPoprzedniegoWiersza.zmienStan("p2");
                                case "p4" -> poleZPoprzedniegoWiersza.zmienStan("p3");
                                case "z1" -> poleZPoprzedniegoWiersza.zmienStan("z");
                                case "z2" -> poleZPoprzedniegoWiersza.zmienStan("z1");
                                case "z3" -> poleZPoprzedniegoWiersza.zmienStan("z2");
                                case "z4" -> poleZPoprzedniegoWiersza.zmienStan("z3");
                                default -> poleZPoprzedniegoWiersza.zmienStan("b");
                            }
                        }
                        for(int i = 1; i<(rozmiar - pole.getWiersz()); i++){
                            Pole poleZNastepengoWiersza = getPole((pole.getWiersz()+i), (pole.getKolumna()));
                            if (Objects.equals(poleZNastepengoWiersza.getStan(), "c")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "0")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "1")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "2")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepengoWiersza.getStan()) {
                                case "xp1" -> poleZNastepengoWiersza.zmienStan("x");
                                case "xp2" -> poleZNastepengoWiersza.zmienStan("xp1");
                                case "xp3" -> poleZNastepengoWiersza.zmienStan("xp2");
                                case "xp4" -> poleZNastepengoWiersza.zmienStan("xp3");
                                case "p2" -> poleZNastepengoWiersza.zmienStan("p1");
                                case "p3" -> poleZNastepengoWiersza.zmienStan("p2");
                                case "p4" -> poleZNastepengoWiersza.zmienStan("p3");
                                case "z1" -> poleZNastepengoWiersza.zmienStan("z");
                                case "z2" -> poleZNastepengoWiersza.zmienStan("z1");
                                case "z3" -> poleZNastepengoWiersza.zmienStan("z2");
                                case "z4" -> poleZNastepengoWiersza.zmienStan("z3");
                                default -> poleZNastepengoWiersza.zmienStan("b");
                            }
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("x");
                        for(int i = 1; i<(rozmiar - pole.getKolumna()); i++){
                            Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna())+i);
                            if (Objects.equals(poleZNastepnejKolumny.getStan(), "c")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "0")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "1")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "2")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepnejKolumny.getStan()) {
                                case "xp1" -> poleZNastepnejKolumny.zmienStan("x");
                                case "xp2" -> poleZNastepnejKolumny.zmienStan("xp1");
                                case "xp3" -> poleZNastepnejKolumny.zmienStan("xp2");
                                case "xp4" -> poleZNastepnejKolumny.zmienStan("xp3");
                                case "p2" -> poleZNastepnejKolumny.zmienStan("p1");
                                case "p3" -> poleZNastepnejKolumny.zmienStan("p2");
                                case "p4" -> poleZNastepnejKolumny.zmienStan("p3");
                                case "z1" -> poleZNastepnejKolumny.zmienStan("z");
                                case "z2" -> poleZNastepnejKolumny.zmienStan("z1");
                                case "z3" -> poleZNastepnejKolumny.zmienStan("z2");
                                case "z4" -> poleZNastepnejKolumny.zmienStan("z3");
                                default -> poleZNastepnejKolumny.zmienStan("b");
                            }
                        }
                        for(int i = (pole.getKolumna()-1); i>=0; i--){
                            Pole poleZPoprzedniejKolumny = getPole(pole.getWiersz(), (i));
                            if (Objects.equals(poleZPoprzedniejKolumny.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniejKolumny.getStan()) {
                                case "xp1" -> poleZPoprzedniejKolumny.zmienStan("x");
                                case "xp2" -> poleZPoprzedniejKolumny.zmienStan("xp1");
                                case "xp3" -> poleZPoprzedniejKolumny.zmienStan("xp2");
                                case "xp4" -> poleZPoprzedniejKolumny.zmienStan("xp3");
                                case "p2" -> poleZPoprzedniejKolumny.zmienStan("p1");
                                case "p3" -> poleZPoprzedniejKolumny.zmienStan("p2");
                                case "p4" -> poleZPoprzedniejKolumny.zmienStan("p3");
                                case "z1" -> poleZPoprzedniejKolumny.zmienStan("z");
                                case "z2" -> poleZPoprzedniejKolumny.zmienStan("z1");
                                case "z3" -> poleZPoprzedniejKolumny.zmienStan("z2");
                                case "z4" -> poleZPoprzedniejKolumny.zmienStan("z3");
                                default -> poleZPoprzedniejKolumny.zmienStan("b");
                            }
                        }
                        for(int i = (pole.getWiersz()-1); i>=0; i--){
                            Pole poleZPoprzedniegoWiersza = getPole(i, (pole.getKolumna()));
                            if (Objects.equals(poleZPoprzedniegoWiersza.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniegoWiersza.getStan()) {
                                case "xp1" -> poleZPoprzedniegoWiersza.zmienStan("x");
                                case "xp2" -> poleZPoprzedniegoWiersza.zmienStan("xp1");
                                case "xp3" -> poleZPoprzedniegoWiersza.zmienStan("xp2");
                                case "xp4" -> poleZPoprzedniegoWiersza.zmienStan("xp3");
                                case "p2" -> poleZPoprzedniegoWiersza.zmienStan("p1");
                                case "p3" -> poleZPoprzedniegoWiersza.zmienStan("p2");
                                case "p4" -> poleZPoprzedniegoWiersza.zmienStan("p3");
                                case "z1" -> poleZPoprzedniegoWiersza.zmienStan("z");
                                case "z2" -> poleZPoprzedniegoWiersza.zmienStan("z1");
                                case "z3" -> poleZPoprzedniegoWiersza.zmienStan("z2");
                                case "z4" -> poleZPoprzedniegoWiersza.zmienStan("z3");
                                default -> poleZPoprzedniegoWiersza.zmienStan("b");
                            }
                        }
                        for(int i = 1; i<(rozmiar - pole.getWiersz()); i++){
                            Pole poleZNastepengoWiersza = getPole((pole.getWiersz()+i), (pole.getKolumna()));
                            if (Objects.equals(poleZNastepengoWiersza.getStan(), "c")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "0")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "1")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "2")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepengoWiersza.getStan()) {
                                case "xp1" -> poleZNastepengoWiersza.zmienStan("x");
                                case "xp2" -> poleZNastepengoWiersza.zmienStan("xp1");
                                case "xp3" -> poleZNastepengoWiersza.zmienStan("xp2");
                                case "xp4" -> poleZNastepengoWiersza.zmienStan("xp3");
                                case "p2" -> poleZNastepengoWiersza.zmienStan("p1");
                                case "p3" -> poleZNastepengoWiersza.zmienStan("p2");
                                case "p4" -> poleZNastepengoWiersza.zmienStan("p3");
                                case "z1" -> poleZNastepengoWiersza.zmienStan("z");
                                case "z2" -> poleZNastepengoWiersza.zmienStan("z1");
                                case "z3" -> poleZNastepengoWiersza.zmienStan("z2");
                                case "z4" -> poleZNastepengoWiersza.zmienStan("z3");
                                default -> poleZNastepengoWiersza.zmienStan("b");
                            }
                        }
                    }
                }
                else if (Objects.equals(pole.getStan(), "x")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("z");
                        for(int i = 1; i<(rozmiar - pole.getKolumna()); i++){
                            Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna())+i);
                            if (Objects.equals(poleZNastepnejKolumny.getStan(), "c")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "0")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "1")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "2")||
                                    Objects.equals(poleZNastepnejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepnejKolumny.getStan()) {
                                case "x" -> poleZNastepnejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZNastepnejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZNastepnejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZNastepnejKolumny.zmienStan("xp4");
                                case "p1" -> poleZNastepnejKolumny.zmienStan("p2");
                                case "p2" -> poleZNastepnejKolumny.zmienStan("p3");
                                case "p3" -> poleZNastepnejKolumny.zmienStan("p4");
                                case "z" -> poleZNastepnejKolumny.zmienStan("z1");
                                case "z1" -> poleZNastepnejKolumny.zmienStan("z2");
                                case "z2" -> poleZNastepnejKolumny.zmienStan("z3");
                                case "z3" -> poleZNastepnejKolumny.zmienStan("z4");
                                default -> poleZNastepnejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getKolumna()-1); i>=0; i--){
                            Pole poleZPoprzedniejKolumny = getPole(pole.getWiersz(), (i));
                            if (Objects.equals(poleZPoprzedniejKolumny.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniejKolumny.getStan()) {
                                case "x" -> poleZPoprzedniejKolumny.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniejKolumny.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniejKolumny.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniejKolumny.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniejKolumny.zmienStan("p2");
                                case "p2" -> poleZPoprzedniejKolumny.zmienStan("p3");
                                case "p3" -> poleZPoprzedniejKolumny.zmienStan("p4");
                                case "z" -> poleZPoprzedniejKolumny.zmienStan("z1");
                                case "z1" -> poleZPoprzedniejKolumny.zmienStan("z2");
                                case "z2" -> poleZPoprzedniejKolumny.zmienStan("z3");
                                case "z3" -> poleZPoprzedniejKolumny.zmienStan("z4");
                                default -> poleZPoprzedniejKolumny.zmienStan("p1");
                            }
                        }
                        for(int i = (pole.getWiersz()-1); i>=0; i--){
                            Pole poleZPoprzedniegoWiersza = getPole(i, (pole.getKolumna()));
                            if (Objects.equals(poleZPoprzedniegoWiersza.getStan(), "c")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "0")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "1")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "2")||
                                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZPoprzedniegoWiersza.getStan()) {
                                case "x" -> poleZPoprzedniegoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZPoprzedniegoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZPoprzedniegoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZPoprzedniegoWiersza.zmienStan("xp4");
                                case "p1" -> poleZPoprzedniegoWiersza.zmienStan("p2");
                                case "p2" -> poleZPoprzedniegoWiersza.zmienStan("p3");
                                case "p3" -> poleZPoprzedniegoWiersza.zmienStan("p4");
                                case "z" -> poleZPoprzedniegoWiersza.zmienStan("z1");
                                case "z1" -> poleZPoprzedniegoWiersza.zmienStan("z2");
                                case "z2" -> poleZPoprzedniegoWiersza.zmienStan("z3");
                                case "z3" -> poleZPoprzedniegoWiersza.zmienStan("z4");
                                default -> poleZPoprzedniegoWiersza.zmienStan("p1");
                            }
                        }
                        for(int i = 1; i<(rozmiar - pole.getWiersz()); i++){
                            Pole poleZNastepengoWiersza = getPole((pole.getWiersz()+i), (pole.getKolumna()));
                            if (Objects.equals(poleZNastepengoWiersza.getStan(), "c")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "0")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "1")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "2")||
                                    Objects.equals(poleZNastepengoWiersza.getStan(), "3")){
                                break;
                            }
                            switch (poleZNastepengoWiersza.getStan()) {
                                case "x" -> poleZNastepengoWiersza.zmienStan("xp1");
                                case "xp1" -> poleZNastepengoWiersza.zmienStan("xp2");
                                case "xp2" -> poleZNastepengoWiersza.zmienStan("xp3");
                                case "xp3" -> poleZNastepengoWiersza.zmienStan("xp4");
                                case "p1" -> poleZNastepengoWiersza.zmienStan("p2");
                                case "p2" -> poleZNastepengoWiersza.zmienStan("p3");
                                case "p3" -> poleZNastepengoWiersza.zmienStan("p4");
                                case "z" -> poleZNastepengoWiersza.zmienStan("z1");
                                case "z1" -> poleZNastepengoWiersza.zmienStan("z2");
                                case "z2" -> poleZNastepengoWiersza.zmienStan("z3");
                                case "z3" -> poleZNastepengoWiersza.zmienStan("z4");
                                default -> poleZNastepengoWiersza.zmienStan("p1");
                            }
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
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
