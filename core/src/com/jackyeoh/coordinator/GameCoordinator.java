package com.jackyeoh.coordinator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.jackyeoh.map.model.TubeModel;

import java.util.List;


public class GameCoordinator {

    private int numberOfTubes;
    private float birdRadius;
    private float birdYpos;
    private boolean enableDebug;
    private Circle birdCircle;
    private Rectangle[] topTubeRectangle;
    private Rectangle[] bottomTubeRectangle;
    private ShapeRenderer shapeRenderer;
    private int scoringTube;
    private int score;

    public GameCoordinator(int numberOfTubes, float birdRadius, boolean enableDebug){
        this.numberOfTubes = numberOfTubes;
        this.birdRadius = birdRadius;
        this.enableDebug = enableDebug;
        this.scoringTube = 0;
        this.score = 0;
        birdYpos = Gdx.graphics.getHeight();
        bottomTubeRectangle = new Rectangle[numberOfTubes];
        topTubeRectangle = new Rectangle[numberOfTubes];
        birdCircle = new Circle();
        for(int i = 0; i < numberOfTubes; i++) {
            topTubeRectangle[i] = new Rectangle();
            bottomTubeRectangle[i] = new Rectangle();
        }

        if(enableDebug) {
            shapeRenderer = new ShapeRenderer();
        }
    }

    public void setTubePositions(List<TubeModel> tubeCoords, float midPoint) {
        for(int i = 0; i < numberOfTubes; i++) {
            TubeModel tubeModel = tubeCoords.get(i);
            bottomTubeRectangle[i].set(tubeModel.getxPos(), tubeModel.getBottomY(), tubeModel.getWidth(), tubeModel.getBottomHeight());
            topTubeRectangle[i].set(tubeModel.getxPos(), tubeModel.getTopY(), tubeModel.getWidth(), tubeModel.getTopHeight());

            if (enableDebug) {
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.rect(tubeModel.getxPos(), tubeModel.getBottomY(), tubeModel.getWidth(), tubeModel.getBottomHeight());
                shapeRenderer.end();

                shapeRenderer.setColor(Color.RED);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.rect(tubeModel.getxPos(), tubeModel.getTopY(), tubeModel.getWidth(), tubeModel.getTopHeight());
                shapeRenderer.end();

            }

            calculateScore(tubeCoords, midPoint);
        }
    }

    private void calculateScore(List<TubeModel> tubeCoords, float midPoint) {
        if(tubeCoords.get(scoringTube).getxPos() < midPoint) {

            if(score < 999){
                score++;
            } else {
                score = 0;
            }

            if(scoringTube < numberOfTubes - 1) {
                scoringTube ++;
            } else {
                scoringTube = 0;
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void setBirdPosition(float[] birdPosition, float birdRadius) {
        birdCircle.set(birdPosition[0], birdPosition[1], birdRadius);
        birdYpos = birdPosition[1];
        if(enableDebug) {
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.circle(birdPosition[0], birdPosition[1], birdRadius);
            shapeRenderer.end();
        }
    }

    public boolean getGameState() {
        for (int i = 0; i < numberOfTubes; i++) {
            if(Intersector.overlaps(birdCircle, topTubeRectangle[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangle[i])){
                return false;
            }
        }
        if(birdYpos <= birdRadius) {
            return false;
        }
        return true;
    }

}
