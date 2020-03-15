package ru.skv.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.skv.base.BaseScreen;
import ru.skv.math.Rect;
import ru.skv.sprite.Background;
import ru.skv.sprite.ButtonExit;
import ru.skv.sprite.ButtonPlay;
import ru.skv.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final int COUNT_STAR = 256;

    private Star[] stars;

    private final Game game;

    private TextureAtlas atlas;

    private Texture bg;

    private Background background;

    private ButtonExit buttonExit;

    private ButtonPlay buttonPlay;


    public MenuScreen(Game game) {
        this.game = game;
    }


    @Override
    public void show() {
        super.show();
        bg = new Texture("castom/sky.jpg");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        stars = new Star[COUNT_STAR];
        for (int i = 0; i < COUNT_STAR; i++) {
            stars[i] = new Star(atlas);
        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        update(delta);
        drow();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch,pointer,button);
        buttonPlay.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }


    }

    private void drow() {
        Gdx.gl.glClearColor(1, 0.2f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }
}
