package ru.skv.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.skv.base.Ship;
import ru.skv.math.Rect;
import ru.skv.pool.BulletPool;
import ru.skv.pool.ExplosionPool;

public class StarShip extends Ship {

    private static final float PADDING = 0.04f;

    private static final int INVALID_POINTER = -1;
    private static final int HP = 100;

    private boolean pressedLeft;

    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public StarShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.vSpeed = new Vector2();
        this.move = new Vector2(0.5f,0);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletPosition = new Vector2();
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.reloadInterval = 0.2f;
        this.hp = HP;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void update(float delta) {
        bulletPosition.set(pos.x, getTop());
        super.update(delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return;
            }
            rightPointer = pointer;
            moveRight();
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }

    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
        }
    }


    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
    }


    public void startNewGame () {
        this.hp = HP;
        stop();
        pressedLeft = false;
        pressedRight = false;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        this.pos.x = worldBounds.pos.x;
        flushDestroy();

    }


    public void moveRight() {
        vSpeed.set(move);
    }

    public void moveLeft() {
        vSpeed.set(move).rotate(180);
    }

    public void stop() {
        vSpeed.setZero();

    }

    public  boolean isBulletCollision (Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom());
    }

}
