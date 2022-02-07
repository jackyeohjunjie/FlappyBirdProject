package com.jackyeoh.gravity.engine;

import com.jackyeoh.gravity.model.GravityModel;
import com.jackyeoh.gravity.util.GravityConstants;
import com.jackyeoh.gravity.util.GravityConstants.Direction;

import java.util.HashMap;

public class GravityEngine {
    private static final float START_ACCELERATION = 0;
    private static final float FALL_VAL = 1;

    private final int width;
    private final int height;
    private final HashMap<String, GravityModel> modelHashMap = new HashMap<>();


    public GravityEngine(int width, int height) {
        this.width = width;
        this.height = height;
    }

    //start xpos, start ypos, jumprate, size, gravitystate, bounded and description
    public void addModel(GravityConstants.StartPositionX posx, GravityConstants.StartPositionY posy, int jumpRate, float radius, GravityConstants.GravityState gravState, boolean bounded, String description ) {

        float posXcoords = 0;
        float posYcoords = 0;
        boolean gravDirection = true;

        if(posx == GravityConstants.StartPositionX.LEFT_EDGE) {
            posXcoords = 0;
        } else if(posx == GravityConstants.StartPositionX.MIDDLE) {
            posXcoords = width / 2;
        } else if (posx == GravityConstants.StartPositionX.RIGHT_EDGE) {
            posXcoords = width;
        }

        if(posy == GravityConstants.StartPositionY.BOTTOM_EDGE) {
            posYcoords = 0;
        } else if(posy == GravityConstants.StartPositionY.MIDDLE) {
            posYcoords = height / 2;
        } else if (posy == GravityConstants.StartPositionY.TOP_EDGE) {
            posYcoords = height;
        }

        if(gravState == GravityConstants.GravityState.ANTI) {
            gravDirection = false;
        }

        modelHashMap.put(description,new GravityModel(posXcoords, posYcoords, START_ACCELERATION, jumpRate, FALL_VAL, radius, gravDirection, bounded, 90));
    }

    public void resetOnModel(String description, GravityConstants.StartPositionX posx, GravityConstants.StartPositionY posy) {
        GravityModel model = modelHashMap.get(description);

        if(posx == GravityConstants.StartPositionX.LEFT_EDGE) {
            model.setxPos(0);
        } else if(posx == GravityConstants.StartPositionX.MIDDLE) {
            model.setxPos(width / 2);
        } else if (posx == GravityConstants.StartPositionX.RIGHT_EDGE) {
            model.setxPos(width);
        }

        if(posy == GravityConstants.StartPositionY.BOTTOM_EDGE) {
            model.setyPos(0);
        } else if(posy == GravityConstants.StartPositionY.MIDDLE) {
            model.setyPos(height / 2);
        } else if (posy == GravityConstants.StartPositionY.TOP_EDGE) {
            model.setyPos(height);
        }
        model.setRotation(90);
        model.setAcceleration(START_ACCELERATION);
    }

    public void jumpOnModel(String name, GravityConstants.Direction direction) {

        if(modelHashMap.containsKey(name)) {
            GravityModel model = modelHashMap.get(name);

            if(model.getRotation() <= 115) {
                model.setRotation(model.getRotation() + 25);
            }

            if(model.getJumpCount() < 2) {

                model.setAcceleration(-model.getJumpRate());

                if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                    model.setJumpCount(model.getJumpCount() + 1);
                    model.setJumpFrames(model.getJumpRate() * 2);
                    model.setJumpDirection(direction);
                }
            }
        }
    }

    //needs to be called at every frame
    public void calibrateCoords() {
        for(GravityModel model : modelHashMap.values()) {
            model.setAcceleration(model.getAcceleration()  + model.getFallVal());
            if (model.getRotation() >= 50) {
                model.setRotation(model.getRotation() - 1);
            }

            if(model.isGravityState()) {
                 model.setyPos(model.getyPos() - model.getAcceleration());
            } else {
                model.setyPos(model.getyPos() + model.getAcceleration());
            }

            if(model.getJumpCount() > 0) {
                int frames = model.getJumpFrames();
                if(frames == 0) {
                    model.setJumpCount(model.getJumpCount() - 1);
                    model.setJumpDirection(Direction.UP);
                } else {
                    model.setJumpFrames(frames - 1);
                    if(model.getJumpDirection() == Direction.LEFT) {
                        model.setxPos(model.getxPos() - 5);
                    } else if(model.getJumpDirection() == Direction.RIGHT) {
                        model.setxPos(model.getxPos() + 5);
                    }
                }
            }

            if(model.isBounded()) {
                //set to floor
                if(model.getyPos() < model.getRadius()) {
                    model.setyPos(model.getRadius());
                }
                //set to roof
                if(model.getyPos() > (height - model.getRadius())) {
                    model.setyPos(height - model.getRadius());
                }
                //set to left edge
                if(model.getxPos() < model.getRadius()) {
                    model.setxPos(model.getRadius());
                }
                //set to right edge
                if(model.getxPos() > (width - model.getRadius())) {
                    model.setxPos(width - model.getRadius());
                }
            }
        }
    }

    public float[] getCoords(String name) {
        float [] coords = new float[3];
        if(modelHashMap.containsKey(name)) {
            GravityModel model = modelHashMap.get(name);
            coords[0] = model.getxPos();
            coords[1] = model.getyPos();
            coords[2] = model.getRotation();
        }
        return coords;
    }

}
