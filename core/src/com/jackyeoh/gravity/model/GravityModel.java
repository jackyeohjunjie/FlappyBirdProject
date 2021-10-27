package com.jackyeoh.gravity.model;
import com.jackyeoh.gravity.util.GravityConstants;
import com.jackyeoh.gravity.util.GravityConstants.Direction;

public class GravityModel {

    private float xPos;
    private float yPos;
    private float acceleration;
    private int jumpRate;
    private float fallVal;
    private float radius;
    private boolean gravityState;
    private int jumpCount;
    private int jumpFrames;
    private GravityConstants.Direction jumpDirection;
    private boolean bounded;

    public GravityModel(float xPos, float yPos, float acceleration, int jumpRate, float fallVal, float radius, boolean gravityState, boolean bounded) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.acceleration = acceleration;
        this.jumpRate = jumpRate;
        this.fallVal = fallVal;
        this.radius = radius;
        this.gravityState = gravityState;
        this.bounded = bounded;
        jumpFrames = 0;
        jumpCount = 0;
        jumpDirection = Direction.UP;
    }

    public boolean isBounded() {
        return bounded;
    }

    public void setBounded(boolean bounded) {
        this.bounded = bounded;
    }

    public GravityConstants.Direction getJumpDirection() {
        return jumpDirection;
    }

    public void setJumpDirection(GravityConstants.Direction jumpDirection) {
        this.jumpDirection = jumpDirection;
    }

    public int getJumpCount() {
        return jumpCount;
    }

    public void setJumpCount(int jumpCount) {
        this.jumpCount = jumpCount;
    }

    public int getJumpFrames() {
        return jumpFrames;
    }

    public void setJumpFrames(int jumpFrames) {
        this.jumpFrames = jumpFrames;
    }

    public boolean isGravityState() {
        return gravityState;
    }

    public void setGravityState(boolean gravityState) {
        this.gravityState = gravityState;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public int getJumpRate() {
        return jumpRate;
    }

    public void setJumpRate(int jumpRate) {
        this.jumpRate = jumpRate;
    }

    public float getFallVal() {
        return fallVal;
    }

    public void setFallVal(float fallVal) {
        this.fallVal = fallVal;
    }
}
