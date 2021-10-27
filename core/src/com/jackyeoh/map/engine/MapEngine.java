package com.jackyeoh.map.engine;

import com.badlogic.gdx.Gdx;
import com.jackyeoh.map.model.TubeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapEngine {

    private int numberOfTubes;
    private int distanceBetweenTubes;
    private int startPosition;
    private int speed;
    private float tubeWidth;
    private float tubeMaxHeight;
    private float offsetConstant;
    private float[] tubeXPositions;
    private float[] tubeBottomYPositions;
    private List<TubeModel> tubeYPositions;
    Random randomGenerator;

    public MapEngine(int numberOfTubes, int distanceBetweenTubes, int startPosition, float tubeWidth, float tubeMaxHeight, int speed, float offsetConstant) {
        this.numberOfTubes = numberOfTubes;
        this.distanceBetweenTubes = distanceBetweenTubes;
        this.startPosition = startPosition;
        this.tubeWidth = tubeWidth;
        this.tubeMaxHeight = tubeMaxHeight;
        this.speed = speed;
        this.offsetConstant = offsetConstant;

        tubeYPositions = new ArrayList<>();
        tubeXPositions = new float[numberOfTubes];
        tubeBottomYPositions = new float[numberOfTubes];

        randomGenerator = new Random();

        for(int i = 0; i < numberOfTubes; i++) {
            tubeBottomYPositions[i] = (float)(tubeMaxHeight - ((randomGenerator.nextFloat() * 0.5) * tubeMaxHeight));
            if(i == 0) {
                tubeXPositions[i] = startPosition;
            } else {
                tubeXPositions[i] = tubeXPositions[i - 1] +  distanceBetweenTubes;
            }
            tubeYPositions.add(new TubeModel());
        }
    }

    public void calibratePos() {
        for(int i = 0; i < numberOfTubes ; i ++) {
            //if the tube is still within the screen
            if(tubeXPositions[i] > -tubeWidth) {
                tubeXPositions[i]  = tubeXPositions[i] - speed;
            } else {
                tubeBottomYPositions[i] = tubeMaxHeight - (float)((randomGenerator.nextFloat() * 0.5) * tubeMaxHeight);
                if(i == 0)  {
                    tubeXPositions[i] = tubeXPositions[numberOfTubes - 1] + distanceBetweenTubes;
                } else {
                    tubeXPositions[i] = tubeXPositions[i - 1] + distanceBetweenTubes;
                }

            }
        }
    }

    public List<TubeModel> getCoords(){
        for(int i = 0; i < numberOfTubes; i++) {
            TubeModel tubeModel = tubeYPositions.get(i);
            float offsetVal = tubeBottomYPositions[i] + offsetConstant;
            tubeModel.setxPos((tubeXPositions[i]));
            tubeModel.setBottomY(0);
            tubeModel.setBottomHeight(tubeBottomYPositions[i]);
            tubeModel.setTopY(offsetVal);
            tubeModel.setTopHeight(Gdx.graphics.getHeight() - offsetVal);
            tubeModel.setWidth(tubeWidth);
        }
        return tubeYPositions;
    }

}
