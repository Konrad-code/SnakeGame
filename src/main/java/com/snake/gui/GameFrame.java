package com.snake.gui;

/**
 * @author Janek Misiórski | https://github.com/janekjmf 	| janekjmf@gmail.com 		| +48 883 483 807
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import com.snake.implementation.Jablko;
import com.snake.implementation.PlayerDAO;
import com.snake.implementation.Powerup;
import com.snake.implementation.Waz;

public class GameFrame  extends javax.swing.JFrame implements WindowListener, Runnable, KeyListener {
    
    /*
    powerupy:
    - przyspieszenie przez 15s
    - zwolnienie przez 15s
    - zaciemnienie przez 30s
    - wyÅ‚Ä…czenie przechodzenia przez sciany na 30s
    - +10 pkt
    - skrÃ³cenie wÄ™Å¼a o +- 1/3 jego dÅ‚ugoÅ›ci
     */
	
	public static final int szerokosc = 400, wysokosc = 400;
    private Thread thread;
    private boolean dalejPelza = false;
    private Waz wonsz; //
    private ArrayList<Waz> snake;
    private Jablko jablko;
    private ArrayList<Jablko> jablka;
    private Random random;
    private int X = 10;
    private int Y = 10;
    public int size = 5;
    final private int startSize = size;
    public boolean info = false;
    boolean pauza = false;
    private boolean przechodzeniePrzezScany = true; // true - mo¿na przechodziæ przez œciany
    private boolean prawo = true;
    private boolean lewo = false;
    private boolean gora = false;
    private boolean dol = false;
    private int czekanie = 70;
    private ImageIcon gameIcon;
    private int stopienSzybkosci = 5;
    private PlayerDAO player;
    private Clip music;
    private boolean doesMusicPlay = true;
    private int points, iloscJablek, iloscPowerupow, iloscPunktow;

    private ArrayList<Powerup> powerupy; //

    boolean ruchWykonany = false;
    private boolean zaciemnienie = false;
    private boolean sciany = false;
    private boolean powerup1activated = false;
    private int czasPowerup1 = 0;
    private boolean powerup2activated = false;
    private int czasPowerup2 = 0;
    private boolean powerup3activated = false;
    private int czasPowerup3 = 0;
    private boolean powerup4activated = false;
    private int czasPowerup4 = 0;
    private boolean moznaSpawnowac = true;
    private boolean poIntro = false;
    
    public GameFrame(PlayerDAO player, Clip music, boolean doesMusicPlay) {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setFocusable(true);
    	setPreferredSize(new Dimension(szerokosc, wysokosc));
        addKeyListener(this);
        this.addWindowListener(this);
        this.setLocationRelativeTo(null);
        gameIcon = new ImageIcon("img\\snake_min.png");
    	setIconImage(gameIcon.getImage());
    	
        random = new Random();
        snake = new ArrayList<Waz>();
        jablka = new ArrayList<Jablko>();
        powerupy = new ArrayList<Powerup>();
        
        this.doesMusicPlay = doesMusicPlay;
        this.music = music;
        this.player = player;
        System.out.println("Czekanie: " + czekanie + " | Zaciemnienie: " + zaciemnienie + " | "
        		+ "Sciany: " + przechodzeniePrzezScany);
        System.out.println(" | Ilosc jablek: " + iloscJablek + " | " + "Ilosc powerupow: " 
        		+ iloscPowerupow + " | Ilosc punktow: " + iloscPunktow);
    	czekanie = player.getGameSettings().getGameSpeed();
    	zaciemnienie = player.getGameSettings().isIfNarrowedView();
    	przechodzeniePrzezScany = player.getGameSettings().isIfMoveThroughWalls();
    	iloscJablek = player.getGameSettings().getSpawnedApplesNo();
    	iloscPowerupow = player.getGameSettings().getSpawnedPowerupsNo();
    	iloscPunktow = player.getGameSettings().getScoreForApple();
    	System.out.println("Czekanie: " + czekanie + " | Zaciemnienie: " + zaciemnienie + " | "
        		+ "Sciany: " + przechodzeniePrzezScany);
        System.out.println(" | Ilosc jablek: " + iloscJablek + " | " + "Ilosc powerupow: " 
        		+ iloscPowerupow + " | Ilosc punktow: " + iloscPunktow);
    	if(!player.getGameSettings().isIfMusic())
        	doesMusicPlay = false;
    	
    	start();

    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, szerokosc, szerokosc);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, szerokosc, szerokosc);

        for (int i = 0; i < snake.size(); i++) {
            if ((i == snake.size() - 1))
                snake.get(i).draw(g, 1600);
            else
                snake.get(i).draw(g, i);
        }

        for (Jablko value : jablka) value.draw(g);

        for (Powerup powerup : powerupy) powerup.draw(g);

        Font font = new Font("Arial Black", Font.ITALIC, 14);
        Font font2 = new Font("Arial Black", Font.ITALIC, 10);
        Font font3 = new Font("Arial Black", Font.ITALIC, 40);

        if (poIntro) {
            g.setFont(font);
            g.setColor(Color.GRAY);
            g.drawOval(10, 10, 10, 10);
            g.drawString(": " + (snake.size() - startSize), 25, 20);
            points = snake.size() - startSize;
        }

        if (!poIntro) {
            g.setColor(Color.RED);
            g.fillRect(50, 50, 135, 15);
            g.fillRect(130, 70, 95, 15);
            g.fillRect(150, 90, 130, 15);
            g.fillRect(150, 110, 107, 15);
            g.fillRect(150, 130, 122, 15);
            g.fillRect(150, 150, 150, 15);
            g.fillRect(150, 170, 165, 15);
            g.fillRect(150, 185, 115, 15);
            g.fillRect(150, 205, 200, 15);
            g.fillRect(10, 225, 120, 15);
            g.fillRect(60, 245, 160, 15);
            g.fillRect(110, 265, 210, 15);
            g.fillRect(80, 300, 150, 15);


            g.setColor(new Color(133, 107, 88));
            g.fillOval(53, 52, 10, 10);
            g.setColor(Color.WHITE);
            g.setFont(font2);
            g.drawString(" - Wydluza weza o 1",65,60);

            g.setColor(new Color(0, 189, 222));
            g.fillOval(133, 72, 10, 10);
            g.setColor(Color.WHITE);
            g.drawString(" - Power - up :",145,80);
                g.drawString(" - Przyspieszenie - 15s",152,100);
                g.drawString(" - Zwolnienie - 15s",152,120);
                g.drawString(" - Dlugosc weza +10",152,140);
                g.drawString(" - Zaciemnienie przez 30s",152,160);
                g.drawString(" - Brak mozliwosci przejscia",152,180);
                g.drawString("przez sciany - 30s",160,195);
                g.drawString(" - Skrocenie weza o 1 / 3 dlugosci",152,215);

            g.drawString("Sterujesz stralkami",12,235);
                g.drawString("Stworz najdluzszego weza.", 62, 255);
                    g.drawString("Power - upy sa generowane losowo", 112, 275);
                g.setFont(font);
                g.drawString("Enter - aby zaczac", 82, 312);

        }

        if (info) {
            g.setColor(Color.RED);
            g.fillRect(7, 30, 70, 15);//X Wï¿½a
            g.fillRect(7, 50, 70, 15);//Y Wï¿½a
            g.fillRect(7, 70, 110, 15);//Dï¿½ugoï¿½ï¿½ Wï¿½a
            g.fillRect(7, 90, 110, 15);//Prï¿½dkoï¿½ï¿½ Wï¿½a
            g.fillRect(7, 110, 80, 15);//X jabï¿½ka
            g.fillRect(7, 130, 80, 15);//Y jabï¿½ka
            g.fillRect(7, 170, 110, 15);//Informacje
            g.fillRect(7, 190, 240, 15);//Przechodzenie przez ï¿½ciany
            g.fillRect(7, 210, 95, 15);//Pauza
            g.fillRect(7, 230, 190, 15);//Zmniejsz prï¿½dkoï¿½ï¿½
            g.fillRect(7, 250, 185, 15);//Zwiï¿½ksz prï¿½dkoï¿½ï¿½

            g.setColor(Color.WHITE);
            g.setFont(font2);
            g.drawString("X Weza: " + X, 10, 40);
            g.drawString("Y Weza: " + Y, 10, 60);
            g.drawString("Dlugosc weza: " + (snake.size()), 10, 80);
            g.drawString("Prekosc weza: " + (stopienSzybkosci), 10, 100);
            g.drawString("X jablka : " + jablko.getX(), 10, 120);
            g.drawString("Y jablka : " + jablko.getY(), 10, 140);
            g.drawString("\" I \" -  Informacje", 10, 180);
            g.drawString("\" O \" -  Przechodzenie przez sciany: " + przechodzeniePrzezScany, 10, 200);
            g.drawString("\" Esc \" -  Pauza", 10, 220);
            g.drawString("\" Z \" -  Zmniejsz predkosc weza", 10, 240);
            g.drawString("\" X \" -  Zwieksz predkosc weza", 10, 260);
        }

        if (zaciemnienie) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(X*10 - (szerokosc + 50), Y*10 - (wysokosc +50), szerokosc+100,wysokosc);
            g.fillRect(X*10 - (szerokosc + 50), Y*10-50, szerokosc,wysokosc+100);
            g.fillRect(X*10 + 50, Y*10 - (wysokosc + 50), szerokosc,wysokosc +100);
            g.fillRect(X*10 - 50, Y*10 + 50, szerokosc + 100, wysokosc);
        }

        if (sciany) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0, szerokosc, 10);
            g.fillRect(0,0, 10, wysokosc);
            g.fillRect(0,wysokosc -10, szerokosc, 10);
            g.fillRect(szerokosc - 10,0, 10, wysokosc);
        }

        if (pauza) {
            g.setFont(font3);
            g.setColor(Color.RED);
            g.fillRect(137, 153, 140, 50);
            g.setColor(Color.WHITE);
            g.drawString("Pauza", 140, 190);
            g.setColor(Color.GRAY);
            g.setFont(font2);
        }

        if (!dalejPelza) {
            g.setFont(font3);
            g.setColor(Color.RED);
            g.fillRect(90, 253, 235, 50);
            g.setColor(Color.WHITE);
            g.drawString("Przegrales", 90, 290);
            g.setColor(Color.GRAY);
            g.setFont(font2);
            
            player.updatePlayerScore(points);
        }
    }

    public void start() {
        dalejPelza = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
    	dalejPelza = false;
        try {
//        	thread.join();
        	System.out.println("Crashed with yourself");
            try { Thread.sleep(4000); } catch (InterruptedException e) { /* leave action */ }
            System.out.println("Switch to ranking");
            RankingFrame frame = new RankingFrame(player, music, doesMusicPlay);
            frame.setVisible(true);
            frame.pack();
            frame.setLocationRelativeTo(null);
//            this.dispose();
        } catch (Exception e){e.printStackTrace();}
    }

    public void run() {
        while (dalejPelza) {
            if(!pauza && poIntro) {
                ruchWykonany = false;
                Waz wonsz;
                if (snake.size() == 0) {
                    wonsz = new Waz(X, Y, 10);
                    snake.add(wonsz);
                }

                if(jablka.size() < iloscJablek) {
                    int xJablka = random.nextInt(37)+1;
                    int yJablka = random.nextInt(37)+1;

                    jablko = new Jablko(xJablka, yJablka, 10);
                    jablka.add(jablko);
                }

                for(int i = 0; i < jablka.size(); i++) {
                    if(X == jablka.get(i).getX() && Y == jablka.get(i).getY()) {
                        size += iloscPunktow;
                        jablka.remove(i);
                        i++;
                    }
                }

                if(powerupy.size() < iloscPowerupow && moznaSpawnowac) {
                    int xPowerupa = random.nextInt(37)+1;
                    int yPowerupa = random.nextInt(37)+1;

                    Powerup powerup = new Powerup(xPowerupa, yPowerupa, 10);
                    powerupy.add(powerup);
                }

                for(int i = 0; i < powerupy.size(); i++) {
                    if(X == powerupy.get(i).getX() && Y == powerupy.get(i).getY()) {
                        int funkcja = random.nextInt(6);

                        if (funkcja == 0)
                            size += 10;

                        if (funkcja == 1) {
                            zaciemnienie = true;
                            powerup1activated = true;
                        }

                        if (funkcja == 2) {
                            czekanie = 40;
                            powerup2activated = true;
                        }

                        if (funkcja == 3) {
                            czekanie = 120;
                            powerup3activated = true;
                        }

                        if (funkcja == 4) {
                            przechodzeniePrzezScany = false;
                            powerup4activated = true;
                        }

                        if (funkcja == 5) {
                            int punktSkrocenia = snake.size()/3;

                            if (snake.size() - punktSkrocenia > startSize)
                                for (int j = 0; j < punktSkrocenia + 1; j++) {
                                    snake.remove(0);
                                    size--;
                                }
                        }
                        powerupy.remove(i);
                        i++;
                    }
                }

                for(int i =0; i < snake.size(); i++) {
                    if(X == snake.get(i).getX() && Y == snake.get(i).getY()) {
                        if(i != snake.size() - 1)
                            stop();
                    }
                }

                if (powerup1activated) {
                    moznaSpawnowac = false;
                    czasPowerup1 += czekanie;

                    if (czasPowerup1 >= 30000) {  //30 sekund 
                        zaciemnienie = false;
                        czasPowerup1 = 0;
                        powerup1activated = false;
                        moznaSpawnowac = true;
                    }
                }

                if (powerup2activated) {
                    moznaSpawnowac = false;
                    czasPowerup2 += czekanie;

                    if (czasPowerup2 >= 15000) {  //15 sekund
                        czekanie = 80;
                        czasPowerup2 = 0;
                        powerup2activated = false;
                        moznaSpawnowac = true;
                    }
                }

                if (powerup3activated) {
                    moznaSpawnowac = false;
                    czasPowerup3 += czekanie;

                    if (czasPowerup3 >= 15000) { //15 sekund 
                        czekanie = 80;
                        czasPowerup3 = 0;
                        powerup3activated = false;
                        moznaSpawnowac = true;
                    }
                }

                if (powerup4activated) {
                    moznaSpawnowac = false;
                    czasPowerup4 += czekanie;

                    if (czasPowerup4 >= 30000) {   //30 sekund
                        przechodzeniePrzezScany = true;
                        czasPowerup4 = 0;
                        powerup4activated =false;
                        moznaSpawnowac = true;
                    }
                }

                if(przechodzeniePrzezScany) {
                    sciany = false;

                    if (X < 0)
                        X = 39;
                    if (X > 39)
                        X = 0;
                    if (Y < 0)
                        Y = 39;
                    if (Y > 39)
                        Y = 0;
                }
                else {
                    sciany = true;

                    if (X < 0 || X > 39 || Y < 0 || Y > 39)
                        stop();
                }

                if(prawo)
                    X++;
                if(lewo)
                    X--;
                if(gora)
                    Y--;
                if(dol)
                    Y++;

                wonsz = new Waz(X, Y, 10);
                snake.add(wonsz);

                if(snake.size() > size)
                    snake.remove(0);
            }
//            System.out.println("Frequency of game speed");
            try {Thread.sleep(czekanie);} catch (Exception ignored) {}
            repaint();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int wcisniety = e.getKeyCode();

        if(wcisniety == KeyEvent.VK_RIGHT && !lewo && !ruchWykonany)
        {
            gora = false;
            dol = false;
            prawo = true;
            ruchWykonany = true;
        }

        if(wcisniety == KeyEvent.VK_LEFT && !prawo && !ruchWykonany)
        {
            gora = false;
            dol = false;
            lewo = true;
            ruchWykonany = true;
        }

        if(wcisniety == KeyEvent.VK_UP && !dol && !ruchWykonany)
        {
            lewo = false;
            prawo = false;
            gora = true;
            ruchWykonany = true;
        }

        if(wcisniety == KeyEvent.VK_DOWN && !gora && !ruchWykonany)
        {
            lewo = false;
            prawo = false;
            dol = true;
            ruchWykonany = true;
        }

        if (wcisniety == KeyEvent.VK_I)
        {
            info = !info;
        }

        if(wcisniety == KeyEvent.VK_O && info)
        {
            przechodzeniePrzezScany = !przechodzeniePrzezScany;
        }

        if(wcisniety == KeyEvent.VK_ESCAPE)
        {
            pauza = !pauza;
        }

        if(wcisniety == KeyEvent.VK_X && stopienSzybkosci > 0 && stopienSzybkosci <= 10)
        {
            czekanie += 10;
            stopienSzybkosci--;
        }

        if(wcisniety == KeyEvent.VK_Z && stopienSzybkosci >= 0 && stopienSzybkosci < 10)
        {
            czekanie -= 10;
            stopienSzybkosci++;
        }

        if(wcisniety == KeyEvent.VK_ENTER)
        {
            poIntro = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent arg0) {}
    public void keyTyped(KeyEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent e) { }
	@Override
	public void windowClosing(WindowEvent e) { }
	@Override
	public void windowClosed(WindowEvent e) { 
		System.exit(0);
	}
	@Override
	public void windowActivated(WindowEvent e) { }
	@Override
	public void windowDeactivated(WindowEvent e) { }

	@Override
    public void windowIconified(WindowEvent e) {
        if(music.isRunning() && player.getGameSettings().isIfMusic()){
            music.stop();
            System.out.println("Music stopped");
        }
        System.out.println("Window iconified");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        if(!music.isRunning() && doesMusicPlay && player.getGameSettings().isIfMusic()){
            music.start();
            System.out.println("Music is back");
        }
        System.out.println("Window deiconified");
    }
}