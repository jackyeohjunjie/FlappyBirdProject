package com.jackyeoh.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jackyeoh.gravity.engine.GravityEngine;
import com.jackyeoh.map.engine.MapEngine;
import com.jackyeoh.map.model.TubeModel;

import java.util.List;

public class AnimationEngine {

    private String atlasPath;
    private String topTubePath;
    private String bottmTubePath;
    private String backGroundPath;
    private String birdTag;
    private float birdRadius;
    private float stateTime;

    private MapEngine mapEngine;
    private GravityEngine gravityEngine;

    private SpriteBatch batch;
    private Texture background;
    private TextureAtlas atlas;
    private Animation<TextureRegion> birdAnimation;
    private Texture bottomTube;
    private Texture topTube;

    public AnimationEngine(String atlasPath, String topTubePath, String bottmTubePath, String backGroundPath, MapEngine mapEngine, GravityEngine gravityEngine, String birdTag, float birdRadius){

        this.atlasPath = atlasPath;
        this.topTubePath = topTubePath;
        this.bottmTubePath = bottmTubePath;
        this.backGroundPath = backGroundPath;
        this.mapEngine = mapEngine;
        this.gravityEngine = gravityEngine;
        this.birdRadius = birdRadius;
        this.birdTag = birdTag;

        batch = new SpriteBatch();
        background = new Texture(backGroundPath);

        atlas = new TextureAtlas(atlasPath);
        Array<TextureAtlas.AtlasRegion> ArrayOfRegions = new Array<>();
        ArrayOfRegions.add(atlas.findRegion("bird"));
        ArrayOfRegions.add(atlas.findRegion("bird2"));
        birdAnimation = new Animation<TextureRegion>(0.33f, ArrayOfRegions, Animation.PlayMode.LOOP);
        bottomTube = new Texture(bottmTubePath);
        topTube = new Texture(topTubePath);

        // A variable for tracking elapsed time for the animation
        stateTime = 0f;
    }

    public void animate() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        drawTubes(mapEngine.getCoords());

        TextureRegion currentFrame = birdAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, gravityEngine.getCoords(birdTag)[0] - birdRadius, gravityEngine.getCoords(birdTag)[1] - birdRadius, birdRadius * 2, birdRadius * 2);
        batch.end();
    }

    private void drawTubes(List<TubeModel> tubeCoords) {
        for(int i = 0; i < 4; i++) {
            TubeModel tubeModel = tubeCoords.get(i);
            batch.draw(bottomTube,tubeModel.getxPos(), tubeModel.getBottomY(), tubeModel.getWidth(), tubeModel.getBottomHeight());
            batch.draw(topTube,tubeModel.getxPos(), tubeModel.getTopY(), tubeModel.getWidth(), tubeModel.getTopHeight());
        }
    }

    public void updateStateTime(){
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
    }

    public SpriteBatch getSpriteBatch(){
        return batch;
    }

}
