package com.example.dell.mysuperawesomegame.mrnom2d;

import com.badlogic.androidgames.framework.FILEIO.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by dell on 8/2/2016.
 */
public class Settings {
    public static boolean soundEnabled = true;
    public static int[] highscores = new int[] { 100, 80, 50, 30, 10 };
    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    files.readFile(".mrnom")));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException | NumberFormatException e) {
// :( It's ok we have defaults
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException ignored) {
            }
        }
    }
    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.writeFile(".mrnom")));
            out.write(Boolean.toString(soundEnabled));
            for (int i = 0; i < 5; i++) {
                out.write(Integer.toString(highscores[i]));
            }
        } catch (IOException ignored) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException ignored) {
            }
        }
    }
    public static void addScore(int score) {
        for (int i = 0; i < 5; i++) {
            if (highscores[i] < score) {
                System.arraycopy(highscores, i, highscores, i + 1, 4 - i);
                highscores[i] = score;
                break;
            }
        }
    }
}
