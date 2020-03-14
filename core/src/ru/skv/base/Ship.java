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

    protected final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

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

    protected float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

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
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    public void dispose() {
        shootSound.dispose();
    }

    @Override
    public void destroy() {
        super.destroy();
        this.hp = 0;
        boom();
    }

    public void damage (int damage) {
        this.hp -= damage;
        if (hp <= 0) {
            destroy();
        }
        damageAnimateTimer = 0f;
        frame = 1;
    }

    public int getDamage() {
        return damage;
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

    public int getHp() {
        return hp;
    }

    public Vector2 getvSpeed() {
        return vSpeed;
    }
}

