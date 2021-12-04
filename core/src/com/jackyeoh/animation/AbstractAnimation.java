package com.jackyeoh.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AbstractAnimation {

    protected SpriteBatch batch;
    protected float stateTime;

    public AbstractAnimation() {
        batch = new SpriteBatch();
        stateTime = 0f;
    }

    public void updateStateTime(){
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
    }

    public SpriteBatch getSpriteBatch(){
        return batch;
    }
}
