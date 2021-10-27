package com.jackyeoh.flappy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jackyeoh.coordinator.GameCoordinator;
import com.jackyeoh.gravity.engine.GravityEngine;
import com.jackyeoh.gravity.util.GravityConstants;
import com.jackyeoh.gravity.util.GravityConstants.Direction;
import com.jackyeoh.map.engine.MapEngine;

public class Game extends ApplicationAdapter {

    private static final int NUMBER_OF_TUBES = 4;
    private static final int JUMP_RATE = 15;
    private static final int STARTING_SPEED = 5;
    private static final String BIRD_TAG = "bird";

    Texture background;
    SpriteBatch batch;

    MapEngine mapEngine;
    GameCoordinator gameCoordinator;
    GravityEngine gravityEngine;

    private float tubeWidth;
    private float tubeHeight;
    private float birdRadius;
    private float offsetConstant;
    private int distanceBetweenTubes;
    private int tubeSpeed;
    private boolean gameOngoing;

    @Override
    public void create() {

        //Necessary calculations
        gameOngoing = true;
        tubeHeight = Gdx.graphics.getHeight() / 2;
        birdRadius = Gdx.graphics.getHeight() / 30;
        offsetConstant = Gdx.graphics.getHeight() / 4;
        distanceBetweenTubes = Gdx.graphics.getWidth() / 2;
        tubeWidth =  Gdx.graphics.getWidth() / 5;
        tubeSpeed = STARTING_SPEED;

        //Background texture
        batch = new SpriteBatch();
        background = new Texture("bg.png");

        //Enable gravity on bird
        gravityEngine = new GravityEngine(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gravityEngine.addModel(GravityConstants.StartPositionX.MIDDLE, GravityConstants.StartPositionY.MIDDLE, JUMP_RATE, birdRadius, GravityConstants.GravityState.NORMAL,true, BIRD_TAG);

        //Generate map
        mapEngine = new MapEngine(NUMBER_OF_TUBES, distanceBetweenTubes, Gdx.graphics.getWidth(), tubeWidth, tubeHeight, tubeSpeed, offsetConstant);

        //Coordinate map and model
        gameCoordinator = new GameCoordinator(NUMBER_OF_TUBES, birdRadius, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if(gameOngoing) {
            gravityEngine.calibrateCoords();
            gameCoordinator.setBirdPosition(gravityEngine.getCoords(BIRD_TAG), birdRadius);
            mapEngine.calibratePos();
            gameCoordinator.setTubePositions(mapEngine.getCoords());
        }

        if(gameOngoing && !gameCoordinator.getGameState()) {
            gameOngoing = false;
        }

        if (Gdx.input.justTouched()) {
           gravityEngine.jumpOnModel(BIRD_TAG, Direction.UP);
        }
    }
}
