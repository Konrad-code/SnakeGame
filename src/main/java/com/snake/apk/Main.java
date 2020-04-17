package com.snake.apk;
import javax.swing.*;

import com.snake.implementation.Okno;

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