package ru.skv.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.List;

import ru.skv.base.ScaledButton;
import ru.skv.math.Rect;
import ru.skv.pool.BulletPool;
import ru.skv.pool.EnemyPool;
import ru.skv.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private static final float PADDING = 0f;

    private StarShip starShip;
    private EnemyPool enemyPool;
    private BulletPool bulletPool;
    private GameScreen gameScreen;


    public ButtonNewGame(TextureAtlas atlas, StarShip starShip, EnemyPool enemyPool, BulletPool bulletPool, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.enemyPool = enemyPool;
        this.bulletPool = bulletPool;
        this.starShip = starShip;
        this.gameScreen = gameScreen;
    }


    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setTop(PADDING);
    }

    @Override
    public void action() {

        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            enemy.destroy();
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            bullet.destroy();
        }

        starShip.newGame();
        gameScreen.setStatePlay();

    }
}
