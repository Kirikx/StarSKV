package ru.skv.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skv.base.Sprite;
import ru.skv.math.Rect;

public class StarShip extends Sprite {

    private static final float PADDING = 0.01f;

    private final Vector2 vSpeed = new Vector2();
    private Vector2 vRight = new Vector2(0.5f, 0);
    private Vector2 vLeft = new Vector2(-0.5f, 0);

    private static TextureRegion region;
    private static TextureRegion[][] splitRegion;

    public StarShip(TextureAtlas atlas) {
        super(getRegion(atlas));

    }

    private static TextureRegion getRegion(TextureAtlas atlas) {
        region = new TextureRegion(atlas.findRegion("main_ship"));
        splitRegion = region.split(region.getRegionWidth() / 2, region.getRegionHeight());
        return splitRegion[0][0];
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(vSpeed, delta);
    }


    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                vSpeed.set(vLeft);
                break;
            case Input.Keys.RIGHT:
                vSpeed.set(vRight);
                break;
        }
    }


    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                vSpeed.set(0, 0);
                break;
            case Input.Keys.RIGHT:
                vSpeed.set(0, 0);
                break;
        }
    }
}
