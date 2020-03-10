package ru.skv.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skv.math.Rect;
import ru.skv.pool.BulletPool;
import ru.skv.pool.ExplosionPool;
import ru.skv.sprite.Bullet;
import ru.skv.sprite.Explosion;

public class Ship extends Sprite {
    protected Vector2 vSpeed;
    protected Vector2 move;

    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPosition;
    protected float bulletHeight;
    protected int damage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;

    protected int hp;

    public Ship() {

    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(vSpeed, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval && getTop() < worldBounds.getTop()) {
            reloadTimer = 0f;
            shoot();
        }
    }

    public void dispose() {
        shootSound.dispose();
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    protected void shoot() {
        shootSound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion,bulletPosition, bulletV, bulletHeight, worldBounds, damage);
    }

    protected void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }
}

