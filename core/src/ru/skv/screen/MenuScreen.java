package ru.skv.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.skv.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;

    private Vector2 touch;
    private Vector2 v;

    private Vector2 cursor;

    private Vector2 pos;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        touch = new Vector2();
        v = new Vector2(1, 1);
        cursor = new Vector2(0,0);
        pos = new Vector2(0, 0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0.2f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Math.round(pos.x) != touch.x && Math.round(pos.y) != touch.y ) { // решение костыльное но все же оно работает))
            pos.add(cursor);
        }
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        cursor.set(touch);
        cursor.set(cursor.sub(pos).nor()); // заначение курсора получаем путем вычитания из текущего значения курсора позиции элемента и нормализуем для получения вектора направления
        System.out.println("touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }
}
