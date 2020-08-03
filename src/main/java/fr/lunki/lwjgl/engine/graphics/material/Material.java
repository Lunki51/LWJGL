package fr.lunki.lwjgl.engine.graphics.material;

import fr.lunki.lwjgl.engine.maths.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Material {

    private float shininess =0;
    private float reflectivity = 0;
    private boolean transparent = false;
    private boolean usingFakeLighting = false;
    private int atlasSize = 1;
    private Texture texture;

    public Material(Texture texture) {
        this.texture = texture;
    }

    public Material(int atlasSize, Texture texture) {
        this.atlasSize = atlasSize;
        this.texture = texture;
    }

    public Material(float shininess, float reflectivity, Texture texture) {
        this.shininess = shininess;
        this.reflectivity = reflectivity;
        this.texture = texture;
    }

    public Material(float shininess, float reflectivity, int atlasSize, Texture texture) {
        this.shininess = shininess;
        this.reflectivity = reflectivity;
        this.atlasSize = atlasSize;
        this.texture = texture;
    }

    public int getAtlasSize(){
        return this.atlasSize;
    }

    public void setTransparent(){
        this.transparent=true;
    }

    public void setUsingFakeLighting(){
        this.usingFakeLighting=true;
    }

    public void create(){
        this.texture.create();
    }

    public float getShininess() {
        return shininess;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public boolean isUsingFakeLighting() {
        return usingFakeLighting;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public Texture getTexture() {
        return texture;
    }

    public void destroy(){
        texture.destroy();
    }
}
