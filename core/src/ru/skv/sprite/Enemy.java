package ru.skv.sprite;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skv.base.Ship;
import ru.skv.math.Rect;
import ru.skv.pool.BulletPool;
import ru.skv.pool.ExplosionPool;

public class Enemy extends Ship {

    private final Vector2 startSpeed;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;

        this.vSpeed = new Vector2();
        this.move = new Vector2();

        this.bulletV = new Vector2();
        this.bulletPosition = new Vector2();
        this.startSpeed = new Vector2(0f, -0.3f);
    }

    @Override
    public void update(float delta) {
        bulletPosition.set(pos.x, getBottom());
        super.update(delta);
        if (getTop() < worldBounds.getTop()) {
            vSpeed.set(move);
        } else {
            this.reloadTimer = reloadInterval*0.9f;
        }
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }

    }

    public void set(TextureRegion[] regions,
                    Vector2 move,
                    TextureRegion bulletRegion,
                    float bulletHeight,
                    float bulletVY,
                    int damage,
                    float reloadInterval,
                    float height,
                    int hp) {
        this.regions = regions;
        this.move.set(move);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0,bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = 0f;
        setHeightProportion(height);
        this.hp = hp;
        vSpeed.set(startSpeed);
    }

    public  boolean isBulletCollision (Rect bullet) {
       return !(bullet.getRight() < getLeft()
               || bullet.getLeft() > getRight()
               || bullet.getBottom() > getTop()
               || bullet.getTop() < pos.y);
    }


}
