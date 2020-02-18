package ru.skv;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class StarSKV extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("cloud.jpg");
		Vector2 v1 = new Vector2(1,1);
		Vector2 v2 = new Vector2(2,2);
		v1.add(v2);
		System.out.println("v1 add v2 v1.x = " + v1.x + " v1.y = " + v1.y);

		v1.set(3,2);
		v2.set(5,6);
		v2.sub(v1);
        System.out.println("v1 sub v2 v1.x = " + v2.x + " v1.y = " + v2.y);
		System.out.println("len v2 = " + v2.len());

		v1.set(10,0);
		System.out.println("len v1 = " + v1.len());
		v1.scl(0.9f);
		System.out.println("len v1 = " + v1.len());
		System.out.println("v1 scl (0.9f) = " + v1.x + " v1.y = " + v1.y);

		v1.nor(); // нормализация сохраняет то же направление вектора но делает длинну равной 1
		System.out.println("len v1 = " + v1.len());
		System.out.println("v1.x = " + v1.x + " v1.y = " + v1.y);

		v1.set(1,1);
		v2.set(-1,1);
		v1.nor();
		v2.nor();
		System.out.println(Math.acos(v1.dot(v2)));

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0.2f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
