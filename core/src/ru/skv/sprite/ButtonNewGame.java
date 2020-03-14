package ru.skv.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.skv.base.ScaledButton;
import ru.skv.math.Rect;
import ru.skv.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private static final float PADDING = 0f;


    private GameScreen gameScreen;


    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));

        this.gameScreen = gameScreen;
    }


    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setTop(PADDING);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
