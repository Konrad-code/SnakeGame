package com.snake.implementation;

/**
 * @author Konrad ¯o³yñski | https://github.com/Konrad-code | konrad.zolynski@gmail.com | +48 533 683 168
**/

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Music {
    public Clip playMusic(){
        Clip music = null;
        try{
            File musicFile = new File("C:\\Users\\mHm_MaXi\\Documents\\NetBeansProjects\\Nauka\\src\\music\\nocnyKochanekWaz.wav");
            if(musicFile.exists()){
                AudioInputStream audio = AudioSystem.getAudioInputStream(musicFile);
                music = AudioSystem.getClip();
                music.open(audio);
                music.start();
                music.loop(Clip.LOOP_CONTINUOUSLY);
//                JOptionPane.showMessageDialog(null, "it's a trap!");
            } else{
                System.out.println("Can't find file");
            }
        }catch(Exception e){
            System.err.println("Failed to run music file: " + e.getMessage());
        }
        return music;
    }
}