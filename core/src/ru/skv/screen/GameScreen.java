package ru.skv.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.skv.base.BaseScreen;
import ru.skv.math.Rect;
import ru.skv.pool.BulletPool;
import ru.skv.pool.EnemyPool;
import ru.skv.pool.ExplosionPool;
import ru.skv.sprite.Background;
import ru.skv.sprite.Bullet;
import ru.skv.sprite.ButtonNewGame;
import ru.skv.sprite.Enemy;
import ru.skv.sprite.MessageGameOver;
import ru.skv.sprite.Star;
import ru.skv.sprite.StarShip;
import ru.skv.sprite.TrackingStar;
import ru.skv.utils.EnemiesEmitter;
import ru.skv.utils.Font;

public class GameScreen extends BaseScreen {

    private static final int COUNT_STAR = 64;
    private static final float FONT_SIZE = 0.02f;
    private static final String FRAGS = "Frars:";
    private static final String HP= "HP:";
    private static final String LEVEL = "Level:";
    private static final float PADDING = 0.01f;

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private TrackingStar[] stars;

    private Texture bg;
    private TextureAtlas atlas;
    private Background background;

    private StarShip starShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private Music music;
    private Sound bulletSound;
    private Sound explosionSound;

    private EnemiesEmitter enemiesEmitter;

    private State state;
    private State pervState;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    private Font font;

    private int frags;

    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        stars = new TrackingStar[COUNT_STAR];
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, worldBounds);
        enemiesEmitter = new EnemiesEmitter(atlas, enemyPool, worldBounds);
        starShip = new StarShip(atlas, bulletPool, explosionPool);
        for (int i = 0; i < COUNT_STAR; i++) {
            stars[i] = new TrackingStar(atlas, starShip.getvSpeed());
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);
        frags = 0;
        state = State.PLAYING;
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        starShip.resize(worldBounds);
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void pause() {
        music.pause();
        pervState = state;
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        music.play();
        state = pervState;
    }

    public void startNewGame() {

        state = State.PLAYING;
        frags = 0;

        starShip.startNewGame();

        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();

    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        music.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
        starShip.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            starShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            starShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            starShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            starShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
        }
        return false;
    }


    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);

        if (state == State.PLAYING) {
            starShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemiesEmitter.generate(delta, frags);
        }

    }

    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            float minDist = starShip.getHalfWidth() + enemy.getHalfWidth();
            if (starShip.pos.dst(enemy.pos) <= minDist) {
                enemy.destroy();
                starShip.damage(enemy.getDamage());
                frags++;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() != starShip) {
                if (starShip.isBulletCollision(bullet)) {
                    starShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for (Enemy enemy : enemyList) {
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        frags++;
                    }
                }
            }
        }
        if (starShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }


    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0.2f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        if (state == State.PLAYING) {
            starShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        printInfo();
        batch.end();
    }

    private void printInfo () {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft()+PADDING, worldBounds.getTop() -PADDING );

        sbHp.setLength(0);
        font.draw(batch, sbHp.append(HP).append(starShip.getHp()), worldBounds.pos.x, worldBounds.getTop() -PADDING, Align.center);

        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemiesEmitter.getLevel()), worldBounds.getRight() - PADDING, worldBounds.getTop() -PADDING, Align.right );
    }
}
