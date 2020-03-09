package ru.skv.pool;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.skv.base.SpritesPool;
import ru.skv.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
