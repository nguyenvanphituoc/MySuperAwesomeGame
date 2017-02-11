package com.example.dell.mysuperawesomegame.jumper;

import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGraphics;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by dell on 8/5/2016.
 */
public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;
    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.batcher = batcher;
    }
    public void render() {
        if(world.bob.position.y > cam.position.y )
            cam.position.y = world.bob.position.y;
        cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();
    }
    public void renderBackground() {
        batcher.beginBatch(Assets.background);
        batcher.drawSprite((float)cam.position.x, (float)cam.position.y,
                FRUSTUM_WIDTH, FRUSTUM_HEIGHT,
                Assets.backgroundRegion);
        batcher.endBatch();
    }
    public void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.items);
        renderBob();
        renderPlatforms();
        renderItems();
        renderSquirrels();
        renderCastle();
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }
    private void renderBob() {
        TextureRegion keyFrame;
        switch(world.bob.state) {
            case Bob.BOB_STATE_FALL:
                keyFrame = Assets.bobFall.getKeyFrame(world.bob.stateTime,
                        Animation.ANIMATION_LOOPING);
                break;
            case Bob.BOB_STATE_JUMP:
                keyFrame = Assets.bobJump.getKeyFrame(world.bob.stateTime,
                        Animation.ANIMATION_LOOPING);
                break;
            case Bob.BOB_STATE_HIT:
            default:
                keyFrame = Assets.bobHit;
        }
        float side = world.bob.velocity.x < 0? -1: 1;
        batcher.drawSprite((float)world.bob.position.x, (float)world.bob.position.y, side * 1, 1,
                keyFrame);
    }
    private void renderPlatforms() {
        int len = world.platforms.size();
        for(int i = 0; i < len; i++) {
            Platform platform = world.platforms.get(i);
            TextureRegion keyFrame = Assets.platform;
            if(platform.state == Platform.PLATFORM_STATE_PULVERIZING) {
                keyFrame = Assets.brakingPlatform.getKeyFrame(platform.stateTime,
                        Animation.ANIMATION_NONLOOPING);
            }
            batcher.drawSprite((float)platform.position.x, (float)platform.position.y,
                    2, 0.5f, keyFrame);
        }
    }
    private void renderItems() {
        int len = world.springs.size();
        for(int i = 0; i < len; i++) {
            Spring spring = world.springs.get(i);
            batcher.drawSprite((float)spring.position.x, (float)spring.position.y, 1, 1,
                    Assets.spring);
        }
        len = world.coins.size();
        for(int i = 0; i < len; i++) {
            Coin coin = world.coins.get(i);
            TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime,
                    Animation.ANIMATION_LOOPING);
            batcher.drawSprite((float)coin.position.x, (float)coin.position.y, 1, 1, keyFrame);
        }
    }
    private void renderSquirrels() {
        int len = world.squirrels.size();
        for(int i = 0; i < len; i++) {
            Squirrel squirrel = world.squirrels.get(i);
            TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(squirrel.stateTime,
                    Animation.ANIMATION_LOOPING);
            float side = squirrel.velocity.x < 0?-1:1;
            batcher.drawSprite((float)squirrel.position.x, (float)squirrel.position.y, side * 1, 1,
                    keyFrame);
        }
    }
    private void renderCastle() {
        Castle castle = world.castle;
        batcher.drawSprite((float)castle.position.x, (float)castle.position.y, 2, 2, Assets.castle);
    }
}
