import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
 *
 * @author Janek Misiórski | https://github.com/janekjmf 	| janekjmf@gmail.com 		| +48 883 483 807
 */

public class Main
{
    public static void main(String[] args)
    {
        JFrame ramka = new JFrame();
        Okno okno = new Okno();

        ramka.add(okno);
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.setTitle("Zaskroniec");
        ramka.setResizable(false);
        ramka.pack();
        ramka.setLocationRelativeTo(null);
        ramka.setVisible(true);
    }
}

class Okno extends JPanel implements Runnable, KeyListener
{
    public static final int szerokosc = 400, wysokosc = 400;
    private Thread thread;
    private boolean dalejPelza = false;
    private Wonsz wonsz;
    private ArrayList<Wonsz> snake;
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
    private boolean dol =false;
    private int czekanie = 70;
    private int stopienSzybkosci = 5;

    public Okno()
    {
        setFocusable(true);
        addKeyListener(this);
        setPreferredSize(new Dimension(szerokosc, wysokosc));
        random = new Random();
        snake = new ArrayList<Wonsz>();
        jablka = new ArrayList<Jablko>();
        start();
    }

    public void paint(Graphics g)
    {
        g.clearRect(0, 0, szerokosc, szerokosc);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, szerokosc, szerokosc);

        for (int i = 0; i < snake.size(); i++)
        {
            if (i == snake.size()-1)
                snake.get(i).draw(g, 1600);
            else
                snake.get(i).draw(g, i);
        }

        for (int i = 0; i < jablka.size(); i++)
            jablka.get(i).draw(g);

        Font font = new Font("Arial", Font.BOLD, 14);
        g.setFont(font);
        g.setColor(Color.GRAY);
        g.drawOval(5,5,10,10);
        g.drawString(": "+(snake.size() - startSize), 20, 15);
        Font font2 = new Font("Arial Black", Font.ITALIC, 10);
        Font font3 = new Font("Arial Black", Font.ITALIC, 40);
        g.setFont(font2);

        if (info)
        {
            g.setColor(Color.RED);
            g.fillRect(7, 30, 70, 15);//X Wê¿a
            g.fillRect(7, 50, 70, 15);//Y Wê¿a
            g.fillRect(7, 70, 110, 15);//D³ugoœæ Wê¿a
            g.fillRect(7, 90, 110, 15);//Prêdkoœæ Wê¿a
            g.fillRect(7, 110, 80, 15);//X jab³ka
            g.fillRect(7, 130, 80, 15);//Y jab³ka
            g.fillRect(7, 170, 110, 15);//Informacje
            g.fillRect(7, 190, 240, 15);//Przechodzenie przez œciany
            g.fillRect(7, 210, 95, 15);//Pauza
            g.fillRect(7, 230, 190, 15);//Zmniejsz prêdkoœæ
            g.fillRect(7, 250, 185, 15);//Zwiêksz prêdkoœæ

            g.setColor(Color.WHITE);
            g.drawString("X Wê¿a: " + X, 10, 40);
            g.drawString("Y Wê¿a: " + Y, 10, 60);
            g.drawString("D³ugoœæ Wê¿a: " + (snake.size()), 10, 80);
            g.drawString("Prêdkoœæ Wê¿a: " + (stopienSzybkosci), 10, 100);
            g.drawString("X jab³ka : " + jablko.getX(), 10, 120);
            g.drawString("Y jab³ka : " + jablko.getY(), 10, 140);
            g.drawString("\" I \" -  Informacje", 10, 180);
            g.drawString("\" O \" -  Przechodzenie przez œciany: " + przechodzeniePrzezScany, 10, 200);
            g.drawString("\" Esc \" -  Pauza", 10, 220);
            g.drawString("\" Z \" -  Zmniejsz prêdkoœæ wê¿a", 10, 240);
            g.drawString("\" X \" -  Zwiêksz prêdkoœæ wê¿a", 10, 260);
        }

        if (pauza)
        {
            g.setFont(font3);
            g.setColor(Color.RED);
            g.fillRect(137, 153, 140, 50);
            g.setColor(Color.WHITE);
            g.drawString("Pauza", 140, 190);
            g.setColor(Color.GRAY);
            g.setFont(font2);
        }

        if (!dalejPelza)
        {
            g.setFont(font3);
            g.setColor(Color.RED);
            g.fillRect(100, 253, 235, 50);
            g.setColor(Color.WHITE);
            g.drawString("Przegra³eœ", 100, 290);
            g.setColor(Color.GRAY);
            g.setFont(font2);
        }
    }

    public void start()
    {
        dalejPelza = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop()
    {
        dalejPelza = false;
        try {thread.join();} catch (InterruptedException e){e.printStackTrace();}
    }

    public void run()
    {
        while (dalejPelza)
        {
            if(!pauza)
            {
                if (snake.size() == 0)
                {
                    wonsz = new Wonsz(X, Y, 10);
                    snake.add(wonsz);
                }
                if(jablka.size() == 0)
                {
                    int xJablka = random.nextInt(39);
                    int yJablka = random.nextInt(39);

                    jablko = new Jablko(xJablka, yJablka, 10);
                    jablka.add(jablko);
                }

                for(int i = 0; i < jablka.size(); i++)
                {
                    if(X == jablka.get(i).getX() && Y == jablka.get(i).getY())
                    {
                        size++;
                        jablka.remove(i);
                        i++;
                    }
                }

                for(int i =0; i < snake.size(); i++)
                {
                    if(X == snake.get(i).getX() && Y == snake.get(i).getY())
                    {
                        if(i != snake.size() - 1)
                            stop();
                    }
                }

                if(przechodzeniePrzezScany)
                {
                    if (X < 0)
                        X = 39;
                    if (X > 39)
                        X = 0;
                    if (Y < 0)
                        Y = 39;
                    if (Y > 39)
                        Y = 0;
                }
                else
                {
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

                wonsz = new Wonsz(X, Y, 10);
                snake.add(wonsz);

                if(snake.size() > size)
                    snake.remove(0);
            }
            try {Thread.sleep(czekanie);} catch (Exception ignored) {}
            repaint();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int wcisniety = e.getKeyCode();

        if(wcisniety == KeyEvent.VK_RIGHT && !lewo)
        {
            gora = false;
            dol = false;
            prawo = true;
        }

        if(wcisniety == KeyEvent.VK_LEFT && !prawo)
        {
            gora = false;
            dol = false;
            lewo = true;
        }

        if(wcisniety == KeyEvent.VK_UP && !dol)
        {
            lewo = false;
            prawo = false;
            gora = true;
        }

        if(wcisniety == KeyEvent.VK_DOWN && !gora)
        {
            lewo = false;
            prawo = false;
            dol = true;
        }
        if (wcisniety == KeyEvent.VK_I)
        {
            if (info)
                info = false;
            else
                info = true;
        }

        if(wcisniety == KeyEvent.VK_O && info)
        {
            if (przechodzeniePrzezScany)
                przechodzeniePrzezScany = false;
            else
                przechodzeniePrzezScany = true;
        }

        if(wcisniety == KeyEvent.VK_ESCAPE)
        {
            if (pauza)
                pauza = false;
            else
                pauza = true;
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
    }
    @Override
    public void keyReleased(KeyEvent arg0) {}
    public void keyTyped(KeyEvent arg0) {}
}

class Wonsz
{
    private int x;
    private int y;
    private int szerokosc;
    private int wysokosc;

    public Wonsz(int x, int y, int rozmiar)
    {
        this.x = x;
        this.y = y;
        this.szerokosc = rozmiar;
        this.wysokosc = rozmiar;
    }

    public void draw(Graphics g, int index)
    {
        if (index == 1600)
            g.setColor(new Color(232, 232, 0));
        else if (index % 2 == 0)
            g.setColor(Color.BLACK);
        else if (index % 2 != 0)
            g.setColor(new Color(75, 75, 75));

        g.fillRect(x * szerokosc, y * wysokosc, szerokosc, wysokosc);
    }
    public int getX() {return x;}
    public void setX(int x) {this.x = x;}
    public int getY() {return y;}
    public void setY(int y) {this.y = y;}
    public int getWysokosc() {return wysokosc;}
    public void setWysokosc(int wysokosc) {this.wysokosc = wysokosc;}
    public int getSzerokosc() {return szerokosc;}
    public void setSzerokosc(int szerokosc) {this.szerokosc = szerokosc;}
}

class Jablko
{
    private int x;
    private int y;
    private int szerokosc;
    private int wysokosc;

    public Jablko(int x, int y, int rozmiar)
    {
        this.x = x;
        this.y = y;
        this.szerokosc = rozmiar;
        this.wysokosc = rozmiar;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillOval(x * szerokosc, y * wysokosc, szerokosc, wysokosc);
    }
    public int getX() {return x;}
    public void setX(int x) {this.x = x;}
    public int getY() {return y;}
    public void setY(int y) {this.y = y;}
    public int getWysokosc() {return wysokosc;}
    public void setWysokosc(int wysokosc) {this.wysokosc = wysokosc;}
    public int getSzerokosc() {return szerokosc;}
    public void setSzerokosc(int szerokosc) {this.szerokosc = szerokosc;}
}