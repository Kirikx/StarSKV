package ru.skv;

import com.badlogic.gdx.Game;
import ru.skv.screen.MenuScreen;

public class StarSKV extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen());
    }
}
