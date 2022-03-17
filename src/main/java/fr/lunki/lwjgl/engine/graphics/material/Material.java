package fr.lunki.lwjgl.engine.graphics.material;

import fr.lunki.lwjgl.engine.graphics.components.specular.Specular;

public class Material {

    private boolean transparent = false;
    private boolean usingFakeLighting = false;
    private int atlasSize = 1;
    private Specular specular;
    private Texture texture;
    private Texture normalMap;
    private boolean created;

    public Material(Specular specular,Texture texture,Texture normalMap,int atlasSize){
        this.specular = specular;
        this.texture = texture;
        this.normalMap = normalMap;
        this.atlasSize = atlasSize;
    }

    public Material(Specular specular,Texture texture,Texture normalMap){
        this(specular,texture,normalMap,1);
    }

    //TODO FIX TEXTURED ENTITIES RENDERED WITHOUT NORMAL MAP
    public Material(Specular specular,Texture texture){
        this(specular,texture,new FlatTexture(""),1);
    }

    public Material(Specular specular,Texture texture,int atlasSize){
        this(specular,texture,new FlatTexture(""),1);
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
            if(normalMap!=null)this.normalMap.create();
            this.texture.create();
            this.specular.create();
            created = true;
        }

    }

    public Texture getNormalMap() {
        return normalMap;
    }

    public boolean isCreated() {
        return created;
    }

    public Specular getSpecular() {
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

    public void setNormalMap(Texture normalMap) {
        if(this.normalMap!=null)this.normalMap.destroy();
        this.normalMap = normalMap;
        if(this.normalMap!=null)normalMap.create();
    }

    public void setSpecular(Specular specular) {
        if(this.specular!=null)this.specular.destroy();
        this.specular = specular;
        specular.create();
    }
}
