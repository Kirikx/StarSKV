package ru.skv;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarSKV extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;
	TextureRegion region;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("cloud.jpg");
		region = new TextureRegion(img, 20, 25, 100, 100);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0.2f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
//		batch.setColor(0.5f, 0.9f, 0.1f,1);
		batch.draw(img, 0, 0);
//		batch.setColor(0.5f, 0.9f, 0.1f,0.3f);
//		batch.draw(region, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
