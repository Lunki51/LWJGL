package fr.lunki.lwjgl.engine.graphics.material;

public class Material {

    private float shininess = 0;
    private float specular = 0;
    private boolean transparent = false;
    private boolean usingFakeLighting = false;
    private int atlasSize = 1;
    private Texture texture;
    private boolean created;

    public Material(Texture texture) {
        this.texture = texture;
    }

    public Material(int atlasSize, Texture texture) {
        this.atlasSize = atlasSize;
        this.texture = texture;
    }

    public Material(float shininess, float specular, Texture texture) {
        this.shininess = shininess;
        this.specular = specular;
        this.texture = texture;
    }

    public Material(float shininess, float specular, int atlasSize, Texture texture) {
        this.shininess = shininess;
        this.specular = specular;
        this.atlasSize = atlasSize;
        this.texture = texture;
    }

    public int getAtlasSize() {
        return this.atlasSize;
    }

    public void setTransparent() {
        this.transparent = true;
    }

    public void setUsingFakeLighting() {
        this.usingFakeLighting = true;
    }

    public void create() {
        if (!created) {
            this.texture.create();
            created = true;
        }

    }

    public float getShininess() {
        return shininess;
    }

    public float getSpecular() {
        return specular;
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

    public void destroy() {
        texture.destroy();
    }
}
