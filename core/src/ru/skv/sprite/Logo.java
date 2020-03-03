package ru.skv.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skv.base.Sprite;
import ru.skv.math.Rect;

public class Logo extends Sprite {

    private Vector2 touch, vspeed, buffer;

    private final float V_LEN = 0.01f;


    public Logo(Texture region) {
        super(new TextureRegion(region));
        touch = new Vector2();
        vspeed = new Vector2();
        buffer = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.4f);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        vspeed.set(touch.sub(pos)).setLength(V_LEN);
    }

    @Override
    public void update(float delta) {
        buffer.set(touch);
        if (buffer.sub(pos).len() > V_LEN) {
            pos.add(vspeed);
        } else pos.set(touch);
    }
}

