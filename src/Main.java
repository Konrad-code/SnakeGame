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
        ramka.setTitle("Wonsz Rzeczny");
        ramka.setResizable(false);
        ramka.pack();
        ramka.setLocationRelativeTo(null);
        ramka.setVisible(true);
    }
}

class Okno extends JPanel implements Runnable, KeyListener
{
    public static final int szerokosc = 400, wysokosc = 400;
    private int szybkosc = 7;//im wiêcej tym wolniej
    private Thread thread;
    private boolean dalejPelza = false;
    private Wonsz wonsz;
    private ArrayList<Wonsz> snake;
    private Jablko jablko;
    private ArrayList<Jablko> jablka;
    private Random random;
    private int x = 10, y = 10;
    private int size = 5;

    private boolean prawo = true;
    private boolean lewo = false;
    private boolean gora = false;
    private boolean dol =false;

    private int z = 0;


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
        g.clearRect(0, 0, szerokosc, wysokosc);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, szerokosc, wysokosc);


        for (int i = 0; i < szerokosc / 10; i++)
            g.drawLine(i * 10, 0, i * 10, wysokosc);

        for (int i = 0; i < wysokosc / 10; i++)
            g.drawLine(0, i * 10, szerokosc, i * 10);

        for (int i = 0; i < snake.size(); i++)
            snake.get(i).draw(g);

        for(int i = 0; i < jablka.size(); i++)
            jablka.get(i).draw(g);
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
            if (snake.size() == 0)
            {
                wonsz = new Wonsz(x, y, 10);
                snake.add(wonsz);
            }
            if(jablka.size() == 0)
            {
                int xCoor = random.nextInt(39);
                int yCoor = random.nextInt(39);

                jablko = new Jablko(xCoor, yCoor, 10);
                jablka.add(jablko);
            }

            for(int i = 0; i < jablka.size(); i++)
            {
                if(x == jablka.get(i).getX() && y == jablka.get(i).getY())
                {
                    size++;
                    jablka.remove(i);
                    i++;
                }
            }

            for(int i =0; i < snake.size(); i++)
            {
                if(x == snake.get(i).getX() && y == snake.get(i).getY())
                {
                    if(i != snake.size() - 1)
                        stop();
                }
            }

            if(x < 0 || x > 39 || y < 0 || y > 39)
                stop();

            z++;

            if(z > szybkosc*100000)
            {
                if(prawo)
                    x++;

                if(lewo)
                    x--;

                if(gora)
                    y--;

                if(dol)
                    y++;

                z = 0;

                wonsz = new Wonsz(x, y, 10);
                snake.add(wonsz);

                if(snake.size() > size)
                    snake.remove(0);
            }
            repaint();
        }
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

    public Wonsz(int x, int y, int tileSize)
    {
        this.x = x;
        this.y = y;
        this.szerokosc = tileSize;
        this.wysokosc = tileSize;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
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

    public Jablko(int x, int y, int tileSize)
    {
        this.x = x;
        this.y = y;
        this.szerokosc = tileSize;
        this.wysokosc = tileSize;
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