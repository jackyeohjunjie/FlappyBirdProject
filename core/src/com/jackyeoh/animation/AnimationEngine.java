package com.jackyeoh.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jackyeoh.gravity.engine.GravityEngine;
import com.jackyeoh.map.engine.MapEngine;
import com.jackyeoh.map.model.TubeModel;

import java.util.List;

public class AnimationEngine extends AbstractAnimation{

    private String birdTag;
    private float birdRadius;

    private MapEngine mapEngine;
    private GravityEngine gravityEngine;

    private Texture background;
    private Animation<TextureRegion> birdAnimation;
    private Texture bottomTube;
    private Texture topTube;

    public AnimationEngine(String atlasPath, String topTubePath, String bottmTubePath, String backGroundPath, MapEngine mapEngine, GravityEngine gravityEngine, String birdTag, float birdRadius){

        super();
        this.mapEngine = mapEngine;
        this.gravityEngine = gravityEngine;
        this.birdRadius = birdRadius;
        this.birdTag = birdTag;

        background = new Texture(backGroundPath);

        birdAnimation = new Animation<TextureRegion>(0.33f, new TextureAtlas(atlasPath).findRegions(birdTag), Animation.PlayMode.LOOP);
        bottomTube = new Texture(bottmTubePath);
        topTube = new Texture(topTubePath);

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





}
