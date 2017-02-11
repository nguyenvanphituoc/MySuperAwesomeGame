package com.example.dell.mysuperawesomegame.mrnom2d;

import com.badlogic.androidgames.framework.GRAPHICS.Graphics.*;
import com.badlogic.androidgames.framework.GRAPHICS.Graphics;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;

/**
 * Created by dell on 8/2/2016.
 */

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }
    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("2dnom/background.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("2dnom/logo.png", PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("2dnom/mainmenu.png", PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("2dnom/buttons.png", PixmapFormat.ARGB4444);
        Assets.help1 = g.newPixmap("2dnom/help1.png", PixmapFormat.ARGB4444);
        Assets.help2 = g.newPixmap("2dnom/help2.png", PixmapFormat.ARGB4444);
        Assets.help3 = g.newPixmap("2dnom/help3.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("2dnom/numbers.png", PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("2dnom/ready.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("2dnom/pausemenu.png", PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("2dnom/gameover.png", PixmapFormat.ARGB4444);
        Assets.headUp = g.newPixmap("2dnom/headup.png", PixmapFormat.ARGB4444);
        Assets.headLeft = g.newPixmap("2dnom/headleft.png", PixmapFormat.ARGB4444);
        Assets.headDown = g.newPixmap("2dnom/headdown.png", PixmapFormat.ARGB4444);
        Assets.headRight = g.newPixmap("2dnom/headright.png", PixmapFormat.ARGB4444);
        Assets.tail = g.newPixmap("2dnom/tail.png", PixmapFormat.ARGB4444);
        Assets.stain1 = g.newPixmap("2dnom/stain1.png", PixmapFormat.ARGB4444);
        Assets.stain2 = g.newPixmap("2dnom/stain2.png", PixmapFormat.ARGB4444);
        Assets.stain3 = g.newPixmap("2dnom/stain3.png", PixmapFormat.ARGB4444);
        Assets.click = game.getAudio().newSound("2dnom/click.ogg");
        Assets.eat = game.getAudio().newSound("2dnom/eat.ogg");
        Assets.bitten = game.getAudio().newSound("2dnom/bitten.ogg");
        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }
    @Override
    public void present(float deltaTime) {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void dispose() {
    }
}
