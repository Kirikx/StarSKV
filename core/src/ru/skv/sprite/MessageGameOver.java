package ru.skv.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.skv.base.Sprite;
import ru.skv.math.Rect;

public class MessageGameOver extends Sprite {
    public MessageGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.09f);
        setTop(0.1f);
    }
}
