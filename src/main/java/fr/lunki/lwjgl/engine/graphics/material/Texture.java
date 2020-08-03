package fr.lunki.lwjgl.engine.graphics.material;

import static org.lwjgl.opengl.GL12.*;

public abstract class Texture {

    protected int imageID;

    public abstract void create();

    public int getImageID() {
        return imageID;
    }

    public void destroy(){
        glDeleteTextures(this.imageID);
    }
}
