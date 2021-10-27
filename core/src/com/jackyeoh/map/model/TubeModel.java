package com.jackyeoh.map.model;

public class TubeModel {

    private float xPos;
    private float bottomY;
    private float bottomHeight;
    private float topY;
    private float topHeight;
    private float width;

    public TubeModel() {}

    public TubeModel(float xPos,
                     float bottomY, float bottomHeight,
                     float topY, float topHeight,
                     float width) {
        this.xPos = xPos;
        this.bottomY = bottomY;
        this.bottomHeight = bottomHeight;
        this.topY = topY;
        this.topHeight = topHeight;
        this.width = width;
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getBottomY() {
        return bottomY;
    }

    public void setBottomY(float bottomY) {
        this.bottomY = bottomY;
    }

    public float getBottomHeight() {
        return bottomHeight;
    }

    public void setBottomHeight(float bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    public float getTopY() {
        return topY;
    }

    public void setTopY(float topY) {
        this.topY = topY;
    }

    public float getTopHeight() {
        return topHeight;
    }

    public void setTopHeight(float topHeight) {
        this.topHeight = topHeight;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
