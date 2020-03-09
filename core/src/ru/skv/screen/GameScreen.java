package ru.skv.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.skv.base.BaseScreen;
import ru.skv.math.Rect;
import ru.skv.pool.BulletPool;
import ru.skv.sprite.Background;
import ru.skv.sprite.Star;
import ru.skv.sprite.StarShip;

public class GameScreen extends BaseScreen {

    private static final int COUNT_STAR = 64;

    private Star[] stars;

    private Texture bg;
    private TextureAtlas atlas;
    private Background background;

    private StarShip starShip;

    private BulletPool bulletPool;

    private Music music;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[COUNT_STAR];
        for (int i = 0; i < COUNT_STAR; i++) {
            stars[i] = new Star(atlas);
        }

        bulletPool = new BulletPool();
        starShip = new StarShip(atlas, bulletPool);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyed();
        drow();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        starShip.resize(worldBounds);
    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        music.dispose();
        starShip.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        starShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        starShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        starShip.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        starShip.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }


    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        starShip.update(delta);
        bulletPool.updateActiveSprites(delta);
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
    }

    private void drow() {
        Gdx.gl.glClearColor(1, 0.2f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        starShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }
}
