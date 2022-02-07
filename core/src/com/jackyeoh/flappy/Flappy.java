package com.jackyeoh.flappy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import com.jackyeoh.animation.AnimationEngine;
import com.jackyeoh.coordinator.AppStateConstants;
import com.jackyeoh.coordinator.GameCoordinator;
import com.jackyeoh.gravity.engine.GravityEngine;
import com.jackyeoh.gravity.util.GravityConstants;
import com.jackyeoh.gravity.util.GravityConstants.Direction;
import com.jackyeoh.map.engine.MapEngine;

public class Flappy extends ApplicationAdapter {

    private static final String BIRD_TAG = "bird";
    private static final int NUMBER_OF_TUBES = 4;
    private static final int JUMP_RATE = 15;
    private static final int STARTING_SPEED = 5;

    private MapEngine mapEngine;
    private GameCoordinator gameCoordinator;
    private GravityEngine gravityEngine;
    private AnimationEngine animationEngine;

    private float tubeWidth;
    private float tubeHeight;
    private float birdRadius;
    private float offsetConstant;
    private int distanceBetweenTubes;
    private int tubeSpeed;
    private long startTime;

    @Override
    public void create() {

        //Necessary calculations
        tubeHeight = Gdx.graphics.getHeight() / 2;
        birdRadius = Gdx.graphics.getHeight() / 30;
        offsetConstant = Gdx.graphics.getHeight() / 4;
        distanceBetweenTubes = Gdx.graphics.getWidth() / 2;
        tubeWidth =  Gdx.graphics.getWidth() / 5;
        tubeSpeed = STARTING_SPEED;
        startTime = TimeUtils.millis();

        //Enable gravity on bird
        gravityEngine = new GravityEngine(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gravityEngine.addModel(GravityConstants.StartPositionX.MIDDLE, GravityConstants.StartPositionY.MIDDLE, JUMP_RATE, birdRadius, GravityConstants.GravityState.NORMAL,true, BIRD_TAG);

        //Generate map
        mapEngine = new MapEngine(NUMBER_OF_TUBES, distanceBetweenTubes, Gdx.graphics.getWidth(), tubeWidth, tubeHeight, tubeSpeed, offsetConstant);

        //Coordinate map and model
        gameCoordinator = new GameCoordinator(NUMBER_OF_TUBES, birdRadius, false);

        //Animation Engine
        animationEngine = new AnimationEngine("num.atlas","bird.atlas", "toptube.png", "bottomtube.png","bg.png", "splashBg.png", "splashLogo.png", "startLogo.png","gameover.png", mapEngine, gravityEngine, BIRD_TAG, birdRadius);
    }

    @Override
    public void dispose() {
        animationEngine.getSpriteBatch().dispose();
    }

    @Override
    public void render() {

        switch(gameCoordinator.getAppState()) {
            case START:
                animationEngine.animateSplashScreen();
                if (TimeUtils.timeSinceMillis(startTime) > 2500) {
                    gameCoordinator.setAppState(AppStateConstants.AppState.SETUP);
                }
                break;
            case SETUP:
                animationEngine.animateGame(0, gameCoordinator.getAppState());
                if (Gdx.input.justTouched()) {
                    mapEngine.resetTubes();
                    gravityEngine.resetOnModel(BIRD_TAG,GravityConstants.StartPositionX.MIDDLE, GravityConstants.StartPositionY.MIDDLE);
                    Gdx.app.log("STATE","Setting to ONGOING..");
                    gameCoordinator.setAppState(AppStateConstants.AppState.ONGOING);
                }
                break;
            case ONGOING:
                animationEngine.animateGame(gameCoordinator.getScore(), gameCoordinator.getAppState());
                gameCoordinator.setBirdPosition(gravityEngine.getCoords(BIRD_TAG), birdRadius);
                gameCoordinator.setTubePositions(mapEngine.getCoords() , distanceBetweenTubes);
                if(gameCoordinator.getGameState()) {
                    animationEngine.updateStateTime();

                    if (Gdx.input.justTouched()) {
                        gravityEngine.jumpOnModel(BIRD_TAG, Direction.UP);
                    }

                    gravityEngine.calibrateCoords();
                    mapEngine.calibratePos();

                } else {
                    Gdx.app.log("STATE","Setting to END..");
                    gameCoordinator.setAppState(AppStateConstants.AppState.END);
                }
                break;
            case END:
                animationEngine.animateGame(gameCoordinator.getScore(), gameCoordinator.getAppState());
                if (Gdx.input.justTouched()) {
                    Gdx.app.log("STATE","Setting to SETUP..");
                    gameCoordinator.setAppState(AppStateConstants.AppState.SETUP);
                }
                break;
        }
    }

}

