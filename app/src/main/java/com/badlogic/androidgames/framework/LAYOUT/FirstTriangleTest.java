package com.badlogic.androidgames.framework.LAYOUT;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.INPUT.Input;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gl.Vertices;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.badlogic.androidgames.framework.math.Vector2;
import com.example.dell.mysuperawesomegame.R;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by dell on 8/4/2016.
 */
public class FirstTriangleTest extends GLGame {
    Context mcontext = this;
    String tests[] = {"FirstTriangleScreen", "ColoredTriangleScreen", "TexturedTriangleScreen", "CannonScreen",
                        "CannonGravityScreen"};
    int position = 1;

    @Override
    public Screen getStartScreen() {
        Intent caller = getIntent();
        Bundle dulieunhan = caller.getBundleExtra("dulieugoi");
        position = dulieunhan.getInt("position");
        switch (position){
            case 1:
                return new ColoredTriangleScreen(this);
            case 2:
                return new TexturedTriangleScreen(this);
            case 3:
                return new CannonScreen(this);
            case 4:
                return new CannonGravityScreen(this);
            default:
                return new FirstTriangleScreen(this);
        }
    }

    class FirstTriangleScreen extends Screen {
        GLGraphics glGraphics;
        FloatBuffer vertices;
        public FirstTriangleScreen(Game game) {
            super(game);
            glGraphics = ((GLGame)game).getGLGraphics();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * 2 * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            vertices = byteBuffer.asFloatBuffer();
            vertices.put( new float[] { 0.0f, 0.0f,
                    319.0f, 0.0f,
                    160.0f, 479.0f});
            vertices.flip();
        }
        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, 320, 0, 480, 1, -1);
            gl.glColor4f(1, 0, 0, 1);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glVertexPointer( 2, GL10.GL_FLOAT, 0, vertices);
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        }
        @Override
        public void update(float deltaTime) {
            game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
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
    class ColoredTriangleScreen extends Screen {
        final int VERTEX_SIZE = (2 + 4) * 4;
        GLGraphics glGraphics;
        FloatBuffer vertices;
        public ColoredTriangleScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * VERTEX_SIZE);
            byteBuffer.order(ByteOrder.nativeOrder());
            vertices = byteBuffer.asFloatBuffer();
            vertices.put(new float[]{0.0f, 0.0f, 1, 0, 0, 1,//
                    319.0f, 0.0f, 0, 1, 0, 1,
                    160.0f, 479.0f, 0, 0, 1, 1});
            vertices.flip();
        }

        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, 320, 0, 480, 1, -1);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            vertices.position(0);
            gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
            vertices.position(2);
            gl.glColorPointer(4, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        }

        @Override
        public void update(float deltaTime) {

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
    class TexturedTriangleScreen extends Screen {
        final int VERTEX_SIZE = (2 + 2) * 4;
        GLGraphics glGraphics;
        FloatBuffer vertices;
        int textureId;

        public TexturedTriangleScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * VERTEX_SIZE);
            byteBuffer.order(ByteOrder.nativeOrder());
            vertices = byteBuffer.asFloatBuffer();
            vertices.put(new float[]{0.0f, 0.0f, 0.0f, 1.0f,
                    319.0f, 0.0f, 1.0f, 1.0f,
                    160.0f, 479.0f, 0.5f, 0.0f});
            vertices.flip();
            textureId = loadTexture("dave.png");

        }

        public int loadTexture(String fileName) {
            try {
                Bitmap bitmap =
                        BitmapFactory.decodeStream(game.getFileIO().readAsset(fileName));
                GL10 gl = glGraphics.getGL();
                int textureIds[] = new int[1];
                gl.glGenTextures(1, textureIds, 0);
                int textureId = textureIds[0];
                gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
                gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                        GL10.GL_NEAREST);
                gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                        GL10.GL_NEAREST);
                gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
                bitmap.recycle();
                return textureId;
            } catch (IOException e) {
                Log.d("TexturedTriangleTest", "couldn't load asset 'bobargb8888.png'!");
                throw new RuntimeException("couldn't load asset '" + fileName + "'");
            }
        }

        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, 320, 0, 480, 1, -1);
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            vertices.position(0);
            gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
            vertices.position(2);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        }
        @Override
        public void update(float deltaTime) {

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
    class CannonScreen extends Screen {
        float FRUSTUM_WIDTH = 4.8f;
        float FRUSTUM_HEIGHT = 3.2f;
        GLGraphics glGraphics;
        Vertices vertices;
        Vector2 cannonPos = new Vector2(2.4f, 0.5f);
        float cannonAngle = 0;
        Vector2 touchPos = new Vector2();
        public CannonScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
            vertices = new Vertices(glGraphics, 3, 0, false, false);
            vertices.setVertices(new float[] { -0.5f, -0.5f,
                    0.5f, 0.0f,
                    -0.5f, 0.5f }, 0, 6);
        }
        @Override
        public void update(float deltaTime) {
            List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                Input.TouchEvent event = touchEvents.get(i);
                touchPos.x = (event.x / (float) glGraphics.getWidth())
                        * FRUSTUM_WIDTH;
                touchPos.y = (1 - event.y / (float) glGraphics.getHeight())
                        * FRUSTUM_HEIGHT;
                cannonAngle = (float)touchPos.sub(cannonPos).angle();
            }
        }
        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, FRUSTUM_WIDTH, 0, FRUSTUM_HEIGHT, 1, -1);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef((float)cannonPos.x, (float)cannonPos.y, 0);
            gl.glRotatef(cannonAngle, 0, 0, 1);
            vertices.bind();
            vertices.draw(GL10.GL_TRIANGLES, 0, 3);
            vertices.unbind();
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
    class CannonGravityScreen extends Screen {
        float FRUSTUM_WIDTH = 9.6f;
        float FRUSTUM_HEIGHT = 6.4f;
        GLGraphics glGraphics;
        Vertices cannonVertices;
        Vertices ballVertices;
        Vector2 cannonPos = new Vector2();
        float cannonAngle = 0;
        Vector2 touchPos = new Vector2();
        Vector2 ballPos = new Vector2(0,0);
        Vector2 ballVelocity = new Vector2(0,0);
        Vector2 gravity = new Vector2(0,-10);
        public CannonGravityScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
            cannonVertices = new Vertices(glGraphics, 3, 0, false, false);
            cannonVertices.setVertices(new float[] { -0.5f, -0.5f,
                    0.5f, 0.0f,
                    -0.5f, 0.5f }, 0, 6);
            ballVertices = new Vertices(glGraphics, 4, 6, false, false);
            ballVertices.setVertices(new float[] { -0.1f, -0.1f,
                    0.1f, -0.1f,
                    0.1f, 0.1f,
                    -0.1f, 0.1f }, 0, 8);
            ballVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
        }
        @Override
        public void update(float deltaTime) {
            List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                Input.TouchEvent event = touchEvents.get(i);
                touchPos.x = (event.x / (float) glGraphics.getWidth())
                        * FRUSTUM_WIDTH;
                touchPos.y = (1 - event.y / (float) glGraphics.getHeight())
                        * FRUSTUM_HEIGHT;
                cannonAngle = (float)touchPos.sub(cannonPos).angle();
                if(event.type == Input.TouchEvent.TOUCH_UP) {
                    double radians = cannonAngle * Vector2.TO_RADIANS;
                    double ballSpeed = touchPos.len();
                    ballPos.set(cannonPos);
                    ballVelocity.x = Math.cos(radians) * ballSpeed;
                    ballVelocity.y = Math.sin(radians) * ballSpeed;
                }
            }
            ballVelocity.add(gravity.x * deltaTime, gravity.y * deltaTime);
            ballPos.add(ballVelocity.x * deltaTime, ballVelocity.y * deltaTime);
        }
        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, FRUSTUM_WIDTH, 0, FRUSTUM_HEIGHT, 1, -1);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef((float) cannonPos.x, (float) cannonPos.y, 0);
            gl.glRotatef(cannonAngle, 0, 0, 1);
            gl.glColor4f(1,1,1,1);
            cannonVertices.bind();
            cannonVertices.draw(GL10.GL_TRIANGLES, 0, 3);
            cannonVertices.unbind();
            gl.glLoadIdentity();
            gl.glTranslatef((float) ballPos.x, (float) ballPos.y, 0);
            gl.glColor4f(1,0,0,1);
            ballVertices.bind();
            ballVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            ballVertices.unbind();
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

}