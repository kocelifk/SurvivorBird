package com.elifkoc.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;

	Texture background;
	Texture bird;
	Texture alien1;
	Texture alien2;
	Texture alien3;

	Texture sun;
	Texture flower;
	Texture flower2;
	Texture flower3;

	Texture tree;
	Texture tree2;
	Texture tree3;


	float birdX =0 ;
	float birdY = 0;
	float velocity =0;
	float gravity =0.1f;
	float enemyVelocity = 2;
	float distance =0;


	int score=0;
	int scoredEnemy=0;
	int gameState =0;
	int numberOfEnemies = 4;

	Random random;
	Circle birdCircle;


	ShapeRenderer shapeRenderer;
	BitmapFont font;//NUMBER
	BitmapFont font2;//GAME OVER
	BitmapFont font3;//TAP TO PLAY AGAIN
	BitmapFont font4; //POINT:


	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOffSet = new float[numberOfEnemies];
	float [] enemyOffSet2 = new float[numberOfEnemies];
	float [] enemyOffSet3 = new float[numberOfEnemies];

	Circle[]  enemyCircles;
	Circle[]  enemyCircles2;
	Circle[]  enemyCircles3;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");

		alien1= new Texture("enemy.png");
		alien2= new Texture("enemy.png");
		alien3= new Texture("enemy.png");

		tree=new Texture("tree.png");
		tree2=new Texture("tree.png");
		tree3=new Texture("tree.png");

		sun = new Texture("sun.png");

		flower= new Texture("flower.png");
		flower2= new Texture("flower.png");
		flower3= new Texture("flower.png");

		birdX = Gdx.graphics.getWidth()/2-bird.getHeight()/2;
		birdY = Gdx.graphics.getHeight()/3;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();

		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font=new BitmapFont();
		font.setColor(Color.YELLOW);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.YELLOW);
		font2.getData().setScale(6);

		font3 = new BitmapFont();
		font3.setColor(Color.YELLOW);
		font3.getData().setScale(6);

		font4= new BitmapFont();
		font4.setColor(Color.YELLOW);
		font4.getData().setScale(4);

		distance = Gdx.graphics.getWidth()/2;
		random = new Random();

		for(int i =0;i<numberOfEnemies; i++) {
			enemyOffSet[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
			enemyOffSet2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
			enemyOffSet3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);

			enemyX[i] = Gdx.graphics.getWidth()-alien1.getWidth()/2+i*distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}
	}

	@Override
	public void render () {
		batch.begin();

		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		batch.draw(tree,50,200, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/4);
		batch.draw(tree2,700,200, Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()/3);
		batch.draw(tree3,1900,200, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/4);

		batch.draw(sun, 1700,1000,Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/5);

		batch.draw(flower,550,200, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/10);
		batch.draw(flower2,1000,200, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/10);
		batch.draw(flower3,1500,200, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/10);

		if(gameState==1) {

			if(enemyX[scoredEnemy] < Gdx.graphics.getWidth()/2-bird.getHeight()/2){
				score++;

				if(scoredEnemy<numberOfEnemies-1){
					scoredEnemy++;
				}else{
					scoredEnemy=0;
				}
			}
			if(Gdx.input.justTouched()){
				velocity=-7;
			}

			for(int i =0;i<numberOfEnemies; i++) {
				if(enemyX[i] < Gdx.graphics.getWidth()/15) {
					enemyX[i]=enemyX[i]+numberOfEnemies*distance;

					enemyOffSet[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffSet2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffSet3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
				}else{
					enemyX[i] = enemyX[i]-enemyVelocity;
				}

				batch.draw(alien1, enemyX[i], Gdx.graphics.getHeight()/2+enemyOffSet[i], Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(alien2, enemyX[i], Gdx.graphics.getHeight()/2+enemyOffSet2[i], Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(alien3, enemyX[i], Gdx.graphics.getHeight()/2+enemyOffSet3[i], Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

				enemyCircles[i] = new Circle(enemyX[i]+ Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCircles2[i] = new Circle(enemyX[i]+ Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCircles3[i] = new Circle(enemyX[i]+ Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			}

			if(birdY>0) {
				velocity = velocity+gravity;
				birdY = birdY-velocity;
				//
			}else {
				gameState=2;
			}

		}else if(gameState==0){
			if(Gdx.input.justTouched()){
				gameState=1;
			}
		}else if(gameState==2){

			font2.draw(batch,"GAME OVER", 900,Gdx.graphics.getHeight()-500);
			font3.draw(batch,"Tap To Play Again", 840,Gdx.graphics.getHeight()/2);

			if(Gdx.input.justTouched()){
				gameState=1;
				birdY = Gdx.graphics.getHeight()/3;
				for(int i =0;i<numberOfEnemies; i++) {
					enemyOffSet[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffSet2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffSet3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);

					enemyX[i] = Gdx.graphics.getWidth()-alien1.getWidth()/2+i*distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
				}
				velocity=0;
				scoredEnemy=0;
				score=0;
			}
		}

		batch.draw(bird, birdX, birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
		font4.draw(batch,"Point:",60,80);
		font.draw(batch,String.valueOf(score),250,80);
		batch.end();

		birdCircle.set(birdX+Gdx.graphics.getWidth()/30,birdY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y,birdCircle.radius);

		for(int i=0;i<numberOfEnemies;i++) {
			//shapeRenderer.circle(enemyX[i]+ Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i]+ Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i]+ Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			if(Intersector.overlaps(birdCircle, enemyCircles2[i])  || Intersector.overlaps(birdCircle, enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i]))
			{
				gameState=2;
			}
		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
	}
}
