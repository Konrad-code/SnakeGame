package com.snake.implementation;

/**
 * @author Janek Misiórski | https://github.com/janekjmf 	| janekjmf@gmail.com 		| +48 883 483 807
 */

import java.awt.Color;
import java.awt.Graphics;

public class Powerup {

    private int x;
    private int y;
    private int szerokosc;
    private int wysokosc;

    public Powerup(int x, int y, int rozmiar) {
        this.x = x;
        this.y = y;
        this.szerokosc = rozmiar;
        this.wysokosc = rozmiar;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 189, 222));
        g.fillOval(x * szerokosc, y * wysokosc, szerokosc, wysokosc);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public void setSzerokosc(int szerokosc) {
        this.szerokosc = szerokosc;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public void setWysokosc(int wysokosc) {
        this.wysokosc = wysokosc;
    }
}