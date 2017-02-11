package com.badlogic.androidgames.framework.LAYOUT;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.INPUT.Input;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gamedev2d.Cannon;
import com.badlogic.androidgames.framework.gamedev2d.DynamicGameObject;
import com.badlogic.androidgames.framework.gamedev2d.GameObject;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.FPSCounter;
import com.badlogic.androidgames.framework.gl.SpatialHashGrid;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.gl.Vertices;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Vector2;
import com.badlogic.androidgames.glbasics.Bob;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by dell on 8/8/2016.
 */
public class IndexedTest extends GLGame{
    static int position = 1;

    @Override
    public Screen getStartScreen() {
        Intent caller = getIntent();
        Bundle dulieunhan = caller.getBundleExtra("dulieugoi");
         position = dulieunhan.getInt("position");
        switch (position){
            case 1:
                return new BlendingScreen(this);
            case 2:
                return new BobScreen(this);
            case 3:
                return new CollisionScreen(this);
            case 4:
                return new Camera2DScreen(this);
            case 5:
                return new TextureAtlasScreen(this);
            case 6:
                return new SpriteBatcherScreen(this);
            default:
                return new IndexedScreen(this);
        }
    }
    class IndexedScreen extends Screen {
        final int VERTEX_SIZE = (2 + 2) * 4;
        GLGraphics glGraphics;
        FloatBuffer vertices;
        ShortBuffer indices;
        Texture texture;

        public IndexedScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * VERTEX_SIZE);
            byteBuffer.order(ByteOrder.nativeOrder());
            vertices = byteBuffer.asFloatBuffer();
            vertices.put(new float[]{100.0f, 100.0f, 0.0f, 1.0f,
                    228.0f, 100.0f, 1.0f, 1.0f,
                    228.0f, 228.0f, 1.0f, 0.0f,
                    100.0f, 228.0f, 0.0f, 0.0f});
            vertices.flip();
            byteBuffer = ByteBuffer.allocateDirect(6 * 2);
            byteBuffer.order(ByteOrder.nativeOrder());
            indices = byteBuffer.asShortBuffer();
            indices.put(new short[]{0, 1, 2,
                    2, 3, 0});
            indices.flip();
            texture = new Texture((GLGame) game, "bobrgb888.png");
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
            texture.bind();
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            vertices.position(0);
            gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
            vertices.position(2);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
            gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, indices);
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
    class BlendingScreen extends Screen {
        GLGraphics glGraphics;
        Vertices vertices;
        Texture textureRgb;
        Texture textureRgba;

        public BlendingScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
            textureRgb = new Texture((GLGame) game, "bobrgb888.png");
            textureRgba = new Texture((GLGame) game, "bobargb8888.png");
            vertices = new Vertices(glGraphics, 8, 12, true, true);
            float[] rects = new float[]{
                    100, 100, 1, 1, 1, 0.5f, 0, 1,
                    228, 100, 1, 1, 1, 0.5f, 1, 1,
                    228, 228, 1, 1, 1, 0.5f, 1, 0,
                    100, 228, 1, 1, 1, 0.5f, 0, 0,
                    228, 300, 1, 1, 1, 1, 1, 1,
                    228, 428, 1, 1, 1, 1, 1, 0,
                    100, 428, 1, 1, 1, 1, 0, 0
            };
            vertices.setVertices(rects, 0, rects.length);
            vertices.setIndices(new short[]{0, 1, 2, 2, 3, 0,
                    4, 5, 6, 6, 7, 4}, 0, 12);
        }

        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glClearColor(1, 0, 0, 1);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, 320, 0, 480, 1, -1);
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glEnable(GL10.GL_TEXTURE_2D);
            textureRgb.bind();
            vertices.draw(GL10.GL_TRIANGLES, 0, 6);
            textureRgba.bind();
            vertices.draw(GL10.GL_TRIANGLES, 6, 6);
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
    class BobScreen extends Screen {
        static final int NUM_BOBS = 100;
        GLGraphics glGraphics;
        Texture bobTexture;
        Vertices bobModel;
        FPSCounter fpsCounter;
        Bob[] bobs;
        public BobScreen(Game game) {
            super(game);
            glGraphics = ((GLGame)game).getGLGraphics();
            bobTexture = new Texture((GLGame)game, "dave.png");
            bobModel = new Vertices(glGraphics, 4, 12, false, true);
            bobModel.setVertices(new float[] { -16, -16, 0, 1,
                    16, -16, 1, 1,
                    16, 16, 1, 0,
                    -16, 16, 0, 0, }, 0, 16);
            bobModel.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
            bobs = new Bob[100];
            for(int i = 0; i < 100; i++) {
                bobs[i] = new Bob();
            }
            fpsCounter = new FPSCounter();
        }
        @Override
        public void update(float deltaTime) {
            game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
            for(int i = 0; i < NUM_BOBS; i++) {
                bobs[i].update(deltaTime);
            }
        }
        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            bobModel.bind();
            for(int i = 0; i < NUM_BOBS; i++) {
                gl.glLoadIdentity();
                gl.glTranslatef(bobs[i].x, bobs[i].y, 0);
                bobModel.draw(GL10.GL_TRIANGLES, 0, 6);
            }
            bobModel.unbind();
            fpsCounter.logFrame();
        }
        @Override
        public void pause() {
        }
        @Override
        public void resume() {
            GL10 gl = glGraphics.getGL();
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glClearColor(1, 0, 0, 1);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, 320, 0, 480, 1, -1);

            gl.glMatrixMode(GL10.GL_MODELVIEW);

            bobTexture.reload();
            gl.glEnable(GL10.GL_TEXTURE_2D);
            bobTexture.bind();
        }
        @Override
        public void dispose() {
        }
    }
    class CollisionScreen extends Screen {
        final int NUM_TARGETS = 20;
        final float WORLD_WIDTH = 9.6f;
        final float WORLD_HEIGHT = 4.8f;
        GLGraphics glGraphics;
        Cannon cannon;
        DynamicGameObject ball;
        List<GameObject> targets;
        SpatialHashGrid grid;
        Vertices cannonVertices;
        Vertices ballVertices;
        Vertices targetVertices;
        Vector2 touchPos = new Vector2();
        Vector2 gravity = new Vector2(0, -10);

        public CollisionScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
            cannon = new Cannon(0, 0, 1, 1);
            ball = new DynamicGameObject(0, 0, 0.2f, 0.2f);
            targets = new ArrayList<GameObject>(NUM_TARGETS);
            grid = new SpatialHashGrid(WORLD_WIDTH, WORLD_HEIGHT, 2.5f);
            for (int i = 0; i < NUM_TARGETS; i++) {
                GameObject target = new GameObject((float) Math.random() * WORLD_WIDTH,
                        (float) Math.random() * WORLD_HEIGHT,
                        0.5f, 0.5f);
                grid.insertStaticObject(target);
                targets.add(target);
            }
            cannonVertices = new Vertices(glGraphics, 3, 0, false, false);
            cannonVertices.setVertices(new float[]{-0.5f, -0.5f,
                    0.5f, 0.0f,
                    -0.5f, 0.5f}, 0, 6);
            ballVertices = new Vertices(glGraphics, 4, 6, false, false);
            ballVertices.setVertices(new float[]{-0.1f, -0.1f,
                    0.1f, -0.1f,
                    0.1f, 0.1f,
                    -0.1f, 0.1f}, 0, 8);
            ballVertices.setIndices(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
            targetVertices = new Vertices(glGraphics, 4, 6, false, false);
            targetVertices.setVertices(new float[]{-0.25f, -0.25f,
                    0.25f, -0.25f,
                    0.25f, 0.25f,
                    -0.25f, 0.25f}, 0, 8);
            targetVertices.setIndices(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
        }

        @Override
        public void update(float deltaTime) {
            List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                Input.TouchEvent event = touchEvents.get(i);
                touchPos.x = (event.x / (float) glGraphics.getWidth()) * WORLD_WIDTH;
                touchPos.y = (1 - event.y / (float) glGraphics.getHeight()) * WORLD_HEIGHT;
                cannon.angle = (float) touchPos.sub(cannon.position).angle();
                if (event.type == Input.TouchEvent.TOUCH_UP) {
                    float radians = (float)(cannon.angle * Vector2.TO_RADIANS);
                    float ballSpeed = (float)touchPos.len() * 2;
                    ball.position.set(cannon.position);
                    ball.velocity.x = Math.cos(radians) * ballSpeed;
                    ball.velocity.y = Math.sin(radians) * ballSpeed;
                    ball.bounds.lowerLeft.set(ball.position.x - 0.1f, ball.position.y - 0.1f);
                }
            }
            ball.velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);
            ball.position.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
            ball.bounds.lowerLeft.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
            List<GameObject> colliders = grid.getPotentialColliders(ball);
            len = colliders.size();
            for (int i = 0; i < len; i++) {
                GameObject collider = colliders.get(i);
                if (OverlapTester.overlapRectangles(ball.bounds, collider.bounds)) {
                    grid.removeObject(collider);
                    targets.remove(collider);
                }
            }
        }

        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, WORLD_WIDTH, 0, WORLD_HEIGHT, 1, -1);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glColor4f(0, 1, 0, 1);
            targetVertices.bind();
            int len = targets.size();
            for (int i = 0; i < len; i++) {
                GameObject target = targets.get(i);
                gl.glLoadIdentity();
                gl.glTranslatef((float)target.position.x, (float)target.position.y, 0);
                targetVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            }
            targetVertices.unbind();
            gl.glLoadIdentity();
            gl.glTranslatef((float)ball.position.x, (float)ball.position.y, 0);
            gl.glColor4f(1, 0, 0, 1);
            ballVertices.bind();
            ballVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            ballVertices.unbind();
            gl.glLoadIdentity();
            gl.glTranslatef((float)cannon.position.x, (float)cannon.position.y, 0);
            gl.glRotatef(cannon.angle, 0, 0, 1);
            gl.glColor4f(1, 1, 1, 1);
            cannonVertices.bind();
            cannonVertices.draw(GL10.GL_TRIANGLES, 0, 3);
            cannonVertices.unbind();
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
    class Camera2DScreen extends Screen {
        final int NUM_TARGETS = 20;
        final float WORLD_WIDTH = 9.6f;
        final float WORLD_HEIGHT = 4.8f;
        GLGraphics glGraphics;
        Cannon cannon;
        DynamicGameObject ball;
        List<GameObject> targets;
        SpatialHashGrid grid;
        Vertices cannonVertices;
        Vertices ballVertices;
        Vertices targetVertices;
        Vector2 touchPos = new Vector2();
        Vector2 gravity = new Vector2(0, -10);
        Camera2D camera;

        public Camera2DScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
            cannon = new Cannon(0, 0, 1, 1);
            ball = new DynamicGameObject(0, 0, 0.2f, 0.2f);
            targets = new ArrayList<GameObject>(NUM_TARGETS);
            grid = new SpatialHashGrid(WORLD_WIDTH, WORLD_HEIGHT, 2.5f);
            camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
            for (int i = 0; i < NUM_TARGETS; i++) {
                GameObject target = new GameObject((float) Math.random() * WORLD_WIDTH,
                        (float) Math.random() * WORLD_HEIGHT,
                        0.5f, 0.5f);
                grid.insertStaticObject(target);
                targets.add(target);
            }

            cannonVertices = new Vertices(glGraphics, 3, 0, false, false);
            cannonVertices.setVertices(new float[]{-0.5f, -0.5f,
                    0.5f, 0.0f,
                    -0.5f, 0.5f}, 0, 6);
            ballVertices = new Vertices(glGraphics, 4, 6, false, false);
            ballVertices.setVertices(new float[]{-0.1f, -0.1f,
                    0.1f, -0.1f,
                    0.1f, 0.1f,
                    -0.1f, 0.1f}, 0, 8);
            ballVertices.setIndices(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
            targetVertices = new Vertices(glGraphics, 4, 6, false, false);
            targetVertices.setVertices(new float[]{-0.25f, -0.25f,
                    0.25f, -0.25f,
                    0.25f, 0.25f,
                    -0.25f, 0.25f}, 0, 8);
            targetVertices.setIndices(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
        }

        @Override
        public void update(float deltaTime) {
            List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                Input.TouchEvent event = touchEvents.get(i);
                camera.touchToWorld(touchPos.set(event.x, event.y));
                cannon.angle = (float) touchPos.sub(cannon.position).angle();
                if (event.type == Input.TouchEvent.TOUCH_UP) {
                    float radians = (float)(cannon.angle * Vector2.TO_RADIANS);
                    float ballSpeed = (float)touchPos.len() * 2;
                    ball.position.set(cannon.position);
                    ball.velocity.x = Math.cos(radians) * ballSpeed;
                    ball.velocity.y = Math.sin(radians) * ballSpeed;
                    ball.bounds.lowerLeft.set(ball.position.x - 0.1f, ball.position.y - 0.1f);
                }
            }
            ball.velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);
            ball.position.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
            ball.bounds.lowerLeft.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
            List<GameObject> colliders = grid.getPotentialColliders(ball);
            len = colliders.size();
            for (int i = 0; i < len; i++) {
                GameObject collider = colliders.get(i);
                if (OverlapTester.overlapRectangles(ball.bounds, collider.bounds)) {
                    grid.removeObject(collider);
                    targets.remove(collider);
                }
            }
            if(ball.position.y > 0) {
                camera.position.set(ball.position);
                camera.zoom = 1 + (float)ball.position.y / WORLD_HEIGHT;
            } else {
                camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
                camera.zoom = 1;
            }
        }

        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            camera.setViewportAndMatrices();
            gl.glColor4f(0, 1, 0, 1);
            targetVertices.bind();
            int len = targets.size();
            for (int i = 0; i < len; i++) {
                GameObject target = targets.get(i);
                gl.glLoadIdentity();
                gl.glTranslatef((float)target.position.x, (float)target.position.y, 0);
                targetVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            }
            targetVertices.unbind();
            gl.glLoadIdentity();
            gl.glTranslatef((float)ball.position.x, (float)ball.position.y, 0);
            gl.glColor4f(1, 0, 0, 1);
            ballVertices.bind();
            ballVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            ballVertices.unbind();
            gl.glLoadIdentity();
            gl.glTranslatef((float)cannon.position.x, (float)cannon.position.y, 0);
            gl.glRotatef(cannon.angle, 0, 0, 1);
            gl.glColor4f(1, 1, 1, 1);
            cannonVertices.bind();
            cannonVertices.draw(GL10.GL_TRIANGLES, 0, 3);
            cannonVertices.unbind();
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
    class TextureAtlasScreen extends Screen {
        final int NUM_TARGETS = 20;
        final float WORLD_WIDTH = 9.6f;
        final float WORLD_HEIGHT = 4.8f;
        GLGraphics glGraphics;
        Cannon cannon;
        DynamicGameObject ball;
        List<GameObject> targets;
        SpatialHashGrid grid;
        Vertices cannonVertices;
        Vertices ballVertices;
        Vertices targetVertices;
        Vector2 touchPos = new Vector2();
        Vector2 gravity = new Vector2(0, -10);
        Camera2D camera;
        Texture texture;
        public TextureAtlasScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
            cannon = new Cannon(0, 0, 1, 0.5f);
            ball = new DynamicGameObject(0, 0, 0.2f, 0.2f);
            targets = new ArrayList<GameObject>(NUM_TARGETS);
            grid = new SpatialHashGrid(WORLD_WIDTH, WORLD_HEIGHT, 2.5f);
            camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
            for (int i = 0; i < NUM_TARGETS; i++) {
                GameObject target = new GameObject((float) Math.random() * WORLD_WIDTH,
                        (float) Math.random() * WORLD_HEIGHT,
                        0.5f, 0.5f);
                grid.insertStaticObject(target);
                targets.add(target);
            }

            cannonVertices = new Vertices(glGraphics, 4, 6, false, true);
            cannonVertices.setVertices(new float[] { -0.5f, -0.25f, 0.0f, 0.5f,
                            0.5f, -0.25f, 1.0f, 0.5f,
                            0.5f, 0.25f, 1.0f, 0.0f,
                            -0.5f, 0.25f, 0.0f, 0.0f },
                    0, 16);
            cannonVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
            ballVertices = new Vertices(glGraphics, 4, 6, false, true);
            ballVertices.setVertices(new float[] { -0.1f, -0.1f, 0.0f, 0.75f,
                            0.1f, -0.1f, 0.25f, 0.75f,
                            0.1f, 0.1f, 0.25f, 0.5f,
                            -0.1f, 0.1f, 0.0f, 0.5f },
                    0, 16);
            ballVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
            targetVertices = new Vertices(glGraphics, 4, 6, false, true);
            targetVertices.setVertices(new float[] { -0.25f, -0.25f, 0.5f, 1.0f,
                            0.25f, -0.25f, 1.0f, 1.0f,
                            0.25f, 0.25f, 1.0f, 0.5f,
                            -0.25f, 0.25f, 0.5f, 0.5f },
                    0, 16);
            targetVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
        }

        @Override
        public void update(float deltaTime) {
            List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                Input.TouchEvent event = touchEvents.get(i);
                camera.touchToWorld(touchPos.set(event.x, event.y));
                cannon.angle = (float) touchPos.sub(cannon.position).angle();
                if (event.type == Input.TouchEvent.TOUCH_UP) {
                    float radians = (float)(cannon.angle * Vector2.TO_RADIANS);
                    float ballSpeed = (float)touchPos.len() * 2;
                    ball.position.set(cannon.position);
                    ball.velocity.x = Math.cos(radians) * ballSpeed;
                    ball.velocity.y = Math.sin(radians) * ballSpeed;
                    ball.bounds.lowerLeft.set(ball.position.x - 0.1f, ball.position.y - 0.1f);
                }
            }
            ball.velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);
            ball.position.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
            ball.bounds.lowerLeft.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
            List<GameObject> colliders = grid.getPotentialColliders(ball);
            len = colliders.size();
            for (int i = 0; i < len; i++) {
                GameObject collider = colliders.get(i);
                if (OverlapTester.overlapRectangles(ball.bounds, collider.bounds)) {
                    grid.removeObject(collider);
                    targets.remove(collider);
                }
            }
            if(ball.position.y > 0) {
                camera.position.set(ball.position);
                camera.zoom = 1 + (float)ball.position.y / WORLD_HEIGHT;
            } else {
                camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
                camera.zoom = 1;
            }
        }

        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            camera.setViewportAndMatrices();
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glEnable(GL10.GL_TEXTURE_2D);
            texture.bind();
            targetVertices.bind();
            int len = targets.size();
            for(int i = 0; i < len; i++) {
                GameObject target = targets.get(i);
                gl.glLoadIdentity();
                gl.glTranslatef((float)target.position.x, (float)target.position.y, 0);
                targetVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            }
            targetVertices.unbind();
            gl.glLoadIdentity();
            gl.glTranslatef((float)ball.position.x, (float)ball.position.y, 0);
            ballVertices.bind();
            ballVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            ballVertices.unbind();
            gl.glLoadIdentity();
            gl.glTranslatef((float)cannon.position.x, (float)cannon.position.y, 0);
            gl.glRotatef(cannon.angle, 0, 0, 1);
            cannonVertices.bind();
            cannonVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            cannonVertices.unbind();
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {
            texture = new Texture(((GLGame)game), "atlas.png");
        }

        @Override
        public void dispose() {

        }
    }
    class SpriteBatcherScreen extends Screen {
        final int NUM_TARGETS = 20;
        final float WORLD_WIDTH = 9.6f;
        final float WORLD_HEIGHT = 4.8f;
        GLGraphics glGraphics;
        Cannon cannon;
        DynamicGameObject ball;
        List<GameObject> targets;
        SpatialHashGrid grid;
        Vertices cannonVertices;
        Vertices ballVertices;
        Vertices targetVertices;
        Vector2 touchPos = new Vector2();
        Vector2 gravity = new Vector2(0, -10);
        Camera2D camera;
        Texture texture;
        TextureRegion cannonRegion;
        TextureRegion ballRegion;
        TextureRegion bobRegion;
        SpriteBatcher batcher;
        public SpriteBatcherScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
            cannon = new Cannon(0, 0, 1, 0.5f);
            ball = new DynamicGameObject(0, 0, 0.2f, 0.2f);
            targets = new ArrayList<GameObject>(NUM_TARGETS);
            grid = new SpatialHashGrid(WORLD_WIDTH, WORLD_HEIGHT, 2.5f);
            camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
            batcher = new SpriteBatcher(glGraphics, 100);

            for (int i = 0; i < NUM_TARGETS; i++) {
                GameObject target = new GameObject((float) Math.random() * WORLD_WIDTH,
                        (float) Math.random() * WORLD_HEIGHT,
                        0.5f, 0.5f);
                grid.insertStaticObject(target);
                targets.add(target);
            }

            cannonVertices = new Vertices(glGraphics, 4, 6, false, true);
            cannonVertices.setVertices(new float[] { -0.5f, -0.25f, 0.0f, 0.5f,
                            0.5f, -0.25f, 1.0f, 0.5f,
                            0.5f, 0.25f, 1.0f, 0.0f,
                            -0.5f, 0.25f, 0.0f, 0.0f },
                    0, 16);
            cannonVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
            ballVertices = new Vertices(glGraphics, 4, 6, false, true);
            ballVertices.setVertices(new float[] { -0.1f, -0.1f, 0.0f, 0.75f,
                            0.1f, -0.1f, 0.25f, 0.75f,
                            0.1f, 0.1f, 0.25f, 0.5f,
                            -0.1f, 0.1f, 0.0f, 0.5f },
                    0, 16);
            ballVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
            targetVertices = new Vertices(glGraphics, 4, 6, false, true);
            targetVertices.setVertices(new float[] { -0.25f, -0.25f, 0.5f, 1.0f,
                            0.25f, -0.25f, 1.0f, 1.0f,
                            0.25f, 0.25f, 1.0f, 0.5f,
                            -0.25f, 0.25f, 0.5f, 0.5f },
                    0, 16);
            targetVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
        }

        @Override
        public void update(float deltaTime) {
            List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                Input.TouchEvent event = touchEvents.get(i);
                camera.touchToWorld(touchPos.set(event.x, event.y));
                cannon.angle = (float) touchPos.sub(cannon.position).angle();
                if (event.type == Input.TouchEvent.TOUCH_UP) {
                    float radians = (float)(cannon.angle * Vector2.TO_RADIANS);
                    float ballSpeed = (float)touchPos.len() * 2;
                    ball.position.set(cannon.position);
                    ball.velocity.x = Math.cos(radians) * ballSpeed;
                    ball.velocity.y = Math.sin(radians) * ballSpeed;
                    ball.bounds.lowerLeft.set(ball.position.x - 0.1f, ball.position.y - 0.1f);
                }
            }
            ball.velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);
            ball.position.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
            ball.bounds.lowerLeft.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
            List<GameObject> colliders = grid.getPotentialColliders(ball);
            len = colliders.size();
            for (int i = 0; i < len; i++) {
                GameObject collider = colliders.get(i);
                if (OverlapTester.overlapRectangles(ball.bounds, collider.bounds)) {
                    grid.removeObject(collider);
                    targets.remove(collider);
                }
            }
            if(ball.position.y > 0) {
                camera.position.set(ball.position);
                camera.zoom = 1 + (float)ball.position.y / WORLD_HEIGHT;
            } else {
                camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
                camera.zoom = 1;
            }
        }

        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            camera.setViewportAndMatrices();
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glEnable(GL10.GL_TEXTURE_2D);
            batcher.beginBatch(texture);
            int len = targets.size();
            for(int i = 0; i < len; i++) {
                GameObject target = targets.get(i);
                batcher.drawSprite((float)target.position.x, (float)target.position.y, 0.5f, 0.5f, bobRegion);
            }
            batcher.drawSprite((float)ball.position.x, (float)ball.position.y, 0.2f, 0.2f, ballRegion);
            batcher.drawSprite((float)cannon.position.x, (float)cannon.position.y, 1, 0.5f, cannon.angle,
                    cannonRegion);
            batcher.endBatch();
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {
            texture = new Texture(((GLGame)game), "atlas.png");
            cannonRegion = new TextureRegion(texture, 0, 0, 64, 32);
            ballRegion = new TextureRegion(texture, 0, 32, 16, 16);
            bobRegion = new TextureRegion(texture, 32, 32, 32, 32);
        }

        @Override
        public void dispose() {

        }
    }
}
