package com.jackyeoh.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jackyeoh.coordinator.AppStateConstants;
import com.jackyeoh.gravity.engine.GravityEngine;
import com.jackyeoh.map.engine.MapEngine;
import com.jackyeoh.map.model.TubeModel;

import java.util.List;

public class AnimationEngine extends AbstractAnimation{

    private String birdTag;
    private float birdRadius;

    private MapEngine mapEngine;
    private GravityEngine gravityEngine;

    private Texture background;
    private Animation<TextureRegion> birdAnimation;
    private Texture bottomTube;
    private Texture topTube;
    private Texture splashBg;
    private Texture logo;
    private Texture pressToStart;
    private Texture gameOverLogo;
    private Array<TextureAtlas.AtlasRegion> numbers;

    public AnimationEngine(String numberAtlasPath, String birdAtlasPath, String topTubePath, String bottmTubePath, String backGroundPath, String splashBackGroundPath, String splashLogoPath, String startLogoPath, String gameOverPath, MapEngine mapEngine, GravityEngine gravityEngine, String birdTag, float birdRadius){

        super();
        this.mapEngine = mapEngine;
        this.gravityEngine = gravityEngine;
        this.birdRadius = birdRadius;
        this.birdTag = birdTag;

        background = new Texture(backGroundPath);
        numbers = new TextureAtlas(numberAtlasPath).findRegions("numbers");
        logo = new Texture(splashLogoPath);
        pressToStart = new Texture(startLogoPath);
        gameOverLogo = new Texture(gameOverPath);
        splashBg = new Texture(splashBackGroundPath);
        birdAnimation = new Animation<TextureRegion>(0.25f, new TextureAtlas(birdAtlasPath).findRegions(birdTag), Animation.PlayMode.LOOP);
        bottomTube = new Texture(bottmTubePath);
        topTube = new Texture(topTubePath);

    }

    public void animateSplashScreen() {
        batch.begin();
        batch.draw(splashBg,0 , 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(logo,Gdx.graphics.getWidth() /4 , Gdx.graphics.getHeight() / 3,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        batch.end();
    }

    public void animateGame(int score, AppStateConstants.AppState appState) {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if(appState == AppStateConstants.AppState.ONGOING) {
            drawTubes(mapEngine.getCoords());
            drawScore(score);
            batch.draw(birdAnimation.getKeyFrame(stateTime, true), gravityEngine.getCoords(birdTag)[0] - birdRadius, gravityEngine.getCoords(birdTag)[1] - birdRadius, birdRadius ,birdRadius,birdRadius * 2, birdRadius * 2,1,1,gravityEngine.getCoords(birdTag)[2],true);
        } else if(appState == AppStateConstants.AppState.SETUP) {
            batch.draw(pressToStart,Gdx.graphics.getWidth() /4 ,Gdx.graphics.getHeight() / 4, Gdx.graphics.getWidth() /2 , Gdx.graphics.getHeight() /2);
        } else if(appState == AppStateConstants.AppState.END) {
            drawTubes(mapEngine.getCoords());
            drawScore(score);
            batch.draw(birdAnimation.getKeyFrame(stateTime, true), gravityEngine.getCoords(birdTag)[0] - birdRadius, gravityEngine.getCoords(birdTag)[1] - birdRadius, birdRadius ,birdRadius,birdRadius * 2, birdRadius * 2,1,1,gravityEngine.getCoords(birdTag)[2],true);
            batch.draw(gameOverLogo,Gdx.graphics.getWidth() /6 ,Gdx.graphics.getHeight() / 2, (Gdx.graphics.getWidth() /6) * 4, Gdx.graphics.getHeight() /5);
        }

        batch.end();
    }

    private void drawScore(int score){
        if(score < 10) {
            batch.draw(numbers.get(score),100,200,100,100);
        } else if (score > 9 && score < 100) {
            int firstIndex = score / 10;
            int lastIndex = score - (firstIndex * 10);
            batch.draw(numbers.get(firstIndex),75,200,100,100);
            batch.draw(numbers.get(lastIndex),175,200,100,100);
        } else if (score > 99 && score < 1000) {
            int firstIndex = score / 100;
            int secondIndex = (score - (firstIndex * 100)) / 10;
            int lastIndex = score - ((firstIndex * 100) + (secondIndex * 10));
            batch.draw(numbers.get(firstIndex),50,200,100,100);
            batch.draw(numbers.get(secondIndex),150,200,100,100);
            batch.draw(numbers.get(lastIndex),250,200,100,100);
        }
    }

    private void drawTubes(List<TubeModel> tubeCoords) {
        for(int i = 0; i < 4; i++) {
            TubeModel tubeModel = tubeCoords.get(i);
            batch.draw(bottomTube,tubeModel.getxPos(), tubeModel.getBottomY(), tubeModel.getWidth(), tubeModel.getBottomHeight());
            batch.draw(topTube,tubeModel.getxPos(), tubeModel.getTopY(), tubeModel.getWidth(), tubeModel.getTopHeight());
        }
    }

}
