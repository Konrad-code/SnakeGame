package com.snake.implementation;

/**
 * @author Janek Misiórski | https://github.com/janekjmf 	| janekjmf@gmail.com 		| +48 883 483 807
 */

import java.awt.Color;
import java.awt.Graphics;

public class Waz {
	
    private int x;
    private int y;
    private int szerokosc;
    private int wysokosc;

    public Waz(int x, int y, int rozmiar) {
        this.x = x;
        this.y = y;
        this.szerokosc = rozmiar;
        this.wysokosc = rozmiar;
    }

    public void draw(Graphics g, int index) {
        if (index == 1600)
            g.setColor(new Color(232, 232, 0));
        else if (index % 2 == 0)
            g.setColor(Color.BLACK);
        else if (index % 2 != 0)
            g.setColor(new Color(75, 75, 75));

        g.fillRect(x * szerokosc, y * wysokosc, szerokosc, wysokosc);
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