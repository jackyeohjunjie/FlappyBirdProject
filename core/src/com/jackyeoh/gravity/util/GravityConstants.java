package com.jackyeoh.gravity.util;

public class GravityConstants {

    public enum Direction {
        UP,
        LEFT,
        RIGHT
    }

    public enum StartPositionX{
        LEFT_EDGE,
        MIDDLE,
        RIGHT_EDGE
    }

    public enum StartPositionY{
        BOTTOM_EDGE,
        MIDDLE,
        TOP_EDGE
    }

    public enum GravityState{
        NORMAL,
        ANTI
    }
}
