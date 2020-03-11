package ru.skv.pool;

import ru.skv.base.SpritesPool;
import ru.skv.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
