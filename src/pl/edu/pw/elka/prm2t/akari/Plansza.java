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
                /*włączanie żarowki z białego pola lub x*/
                if (Objects.equals(pole.getStan(), "b") || Objects.equals(pole.getStan(), "x")) {
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
                                case "x":
                                    poleZNastepnejKolumny.zmienStan("xp");
                                    break;
                                case "b":
                                    poleZNastepnejKolumny.zmienStan("p");
                                    break;
                                case "z":
                                case "p":
                                case "xp":
                                    poleZNastepnejKolumny.zwiekszOswietlenie();
                                    break;
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
                                case "x":
                                    poleZPoprzedniejKolumny.zmienStan("xp");
                                    break;
                                case "b":
                                    poleZPoprzedniejKolumny.zmienStan("p");
                                    break;
                                case "z":
                                case "p":
                                case "xp":
                                    poleZPoprzedniejKolumny.zwiekszOswietlenie();
                                    break;
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
                                case "x":
                                    poleZPoprzedniegoWiersza.zmienStan("xp");
                                    break;
                                case "b":
                                    poleZPoprzedniegoWiersza.zmienStan("p");
                                    break;
                                case "z":
                                case "p":
                                case "xp":
                                    poleZPoprzedniegoWiersza.zwiekszOswietlenie();
                                    break;
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
                                case "x":
                                    poleZNastepengoWiersza.zmienStan("xp");
                                    break;
                                case "b":
                                    poleZNastepengoWiersza.zmienStan("p");
                                    break;
                                case "z":
                                case "p":
                                case "xp":
                                    poleZNastepengoWiersza.zwiekszOswietlenie();
                                    break;
                            }
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3 && Objects.equals(pole.getStan(), "x") /*prawy*/) {
                        pole.zmienStan("b");
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3 && Objects.equals(pole.getStan(), "b")  /*prawy*/) {
                        pole.zmienStan("x");
                    }
                }
                /*włączanie żarówki z oświetlonego pola lub oświetlonego xp*/
                else if (Objects.equals(pole.getStan(), "p") || Objects.equals(pole.getStan(), "xp")) {
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
                                case "x":
                                    poleZNastepnejKolumny.zmienStan("xp");
                                    break;
                                case "b":
                                    poleZNastepnejKolumny.zmienStan("p");
                                    break;
                                case "z":
                                case "p":
                                case "xp":
                                    poleZNastepnejKolumny.zwiekszOswietlenie();
                                    break;
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
                                case "x":
                                    poleZPoprzedniejKolumny.zmienStan("xp");
                                    break;
                                case "b":
                                    poleZPoprzedniejKolumny.zmienStan("p");
                                    break;
                                case "z":
                                case "p":
                                case "xp":
                                    poleZPoprzedniejKolumny.zwiekszOswietlenie();
                                    break;
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
                                case "x":
                                    poleZPoprzedniegoWiersza.zmienStan("xp");
                                    break;
                                case "b":
                                    poleZPoprzedniegoWiersza.zmienStan("p");
                                    break;
                                case "z":
                                case "p":
                                case "xp":
                                    poleZPoprzedniegoWiersza.zwiekszOswietlenie();
                                    break;
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
                                case "x":
                                    poleZNastepengoWiersza.zmienStan("xp");
                                    break;
                                case "b":
                                    poleZNastepengoWiersza.zmienStan("p");
                                    break;
                                case "z":
                                case "p":
                                case "xp":
                                    poleZNastepengoWiersza.zwiekszOswietlenie();
                                    break;
                            }
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3 && Objects.equals(pole.getStan(), "p")  /*prawy*/) {
                        pole.zmienStan("xp");
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3 && Objects.equals(pole.getStan(), "xp")  /*prawy*/) {
                        pole.zmienStan("p");

                    }
                }
                /*wyłączanie żarówki na białe pole*/
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
                                case "xp":
                                    if (poleZNastepnejKolumny.getOswietlenie() == 1) {
                                        poleZNastepnejKolumny.zmienStan("x");
                                    }
                                    else {
                                        poleZNastepnejKolumny.zmniejszOswietlenie();
                                    }
                                    break;
                                case "p":
                                    if (poleZNastepnejKolumny.getOswietlenie() > 1) {
                                        poleZNastepnejKolumny.zmniejszOswietlenie();
                                    }
                                    else {
                                        poleZNastepnejKolumny.zmienStan("b");
                                    }
                                    break;
                                case "z":
                                    poleZNastepnejKolumny.zmniejszOswietlenie();
                                    break;

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
                                case "xp":
                                    if (poleZPoprzedniejKolumny.getOswietlenie() == 1) {
                                        poleZPoprzedniejKolumny.zmienStan("x");
                                    }
                                    else {
                                        poleZPoprzedniejKolumny.zmniejszOswietlenie();
                                    }
                                    break;
                                case "p":
                                    if (poleZPoprzedniejKolumny.getOswietlenie() > 1) {
                                        poleZPoprzedniejKolumny.zmniejszOswietlenie();
                                    }
                                    else {
                                        poleZPoprzedniejKolumny.zmienStan("b");
                                    }
                                    break;
                                case "z":
                                    poleZPoprzedniejKolumny.zmniejszOswietlenie();
                                    break;

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
                                case "xp":
                                    if (poleZPoprzedniegoWiersza.getOswietlenie() == 1) {
                                        poleZPoprzedniegoWiersza.zmienStan("x");
                                    }
                                    else {
                                        poleZPoprzedniegoWiersza.zmniejszOswietlenie();
                                    }
                                    break;
                                case "p":
                                    if (poleZPoprzedniegoWiersza.getOswietlenie() > 1) {
                                        poleZPoprzedniegoWiersza.zmniejszOswietlenie();
                                    }
                                    else {
                                        poleZPoprzedniegoWiersza.zmienStan("b");
                                    }
                                    break;
                                case "z":
                                    poleZPoprzedniegoWiersza.zmniejszOswietlenie();
                                    break;

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
                                case "xp":
                                    if (poleZNastepengoWiersza.getOswietlenie() == 1) {
                                        poleZNastepengoWiersza.zmienStan("x");
                                    }
                                    else {
                                        poleZNastepengoWiersza.zmniejszOswietlenie();
                                    }
                                    break;
                                case "p":
                                    if (poleZNastepengoWiersza.getOswietlenie() > 1) {
                                        poleZNastepengoWiersza.zmniejszOswietlenie();
                                    }
                                    else {
                                        poleZNastepengoWiersza.zmienStan("b");
                                    }
                                    break;
                                case "z":
                                    poleZNastepengoWiersza.zmniejszOswietlenie();
                                    break;

                            }
                        }
                    }
                    /*wyłączanie żarówki na pole x*/
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
                                case "xp":
                                    if (poleZNastepnejKolumny.getOswietlenie() == 1) {
                                        poleZNastepnejKolumny.zmienStan("x");
                                    }
                                    else {
                                        poleZNastepnejKolumny.zmniejszOswietlenie();
                                    }
                                    break;
                                case "p":
                                    if (poleZNastepnejKolumny.getOswietlenie() > 1) {
                                        poleZNastepnejKolumny.zmniejszOswietlenie();
                                    }
                                    else {
                                        poleZNastepnejKolumny.zmienStan("b");
                                    }
                                    break;
                                case "z":
                                    poleZNastepnejKolumny.zmniejszOswietlenie();
                                    break;

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
                                case "xp":
                                    if (poleZPoprzedniejKolumny.getOswietlenie() == 1) {
                                        poleZPoprzedniejKolumny.zmienStan("x");
                                    }
                                    else {
                                        poleZPoprzedniejKolumny.zmniejszOswietlenie();
                                    }
                                    break;
                                case "p":
                                    if (poleZPoprzedniejKolumny.getOswietlenie() > 1) {
                                        poleZPoprzedniejKolumny.zmniejszOswietlenie();
                                    }
                                    else {
                                        poleZPoprzedniejKolumny.zmienStan("b");
                                    }
                                    break;
                                case "z":
                                    poleZPoprzedniejKolumny.zmniejszOswietlenie();
                                    break;

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
                                case "xp":
                                    if (poleZPoprzedniegoWiersza.getOswietlenie() == 1) {
                                        poleZPoprzedniegoWiersza.zmienStan("x");
                                    }
                                    else {
                                        poleZPoprzedniegoWiersza.zmniejszOswietlenie();
                                    }
                                    break;
                                case "p":
                                    if (poleZPoprzedniegoWiersza.getOswietlenie() > 1) {
                                        poleZPoprzedniegoWiersza.zmniejszOswietlenie();
                                    }
                                    else {
                                        poleZPoprzedniegoWiersza.zmienStan("b");
                                    }
                                    break;
                                case "z":
                                    poleZPoprzedniegoWiersza.zmniejszOswietlenie();
                                    break;

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
                                case "xp":
                                    if (poleZNastepengoWiersza.getOswietlenie() == 1) {
                                        poleZNastepengoWiersza.zmienStan("x");
                                    }
                                    else {
                                        poleZNastepengoWiersza.zmniejszOswietlenie();
                                    }
                                    break;
                                case "p":
                                    if (poleZNastepengoWiersza.getOswietlenie() > 1) {
                                        poleZNastepengoWiersza.zmniejszOswietlenie();
                                    }
                                    else {
                                        poleZNastepengoWiersza.zmienStan("b");
                                    }
                                    break;
                                case "z":
                                    poleZNastepengoWiersza.zmniejszOswietlenie();
                                    break;

                            }
                        }
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
