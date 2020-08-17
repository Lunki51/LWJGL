package fr.lunki.lwjgl.engine.graphics.components.specular;

import fr.lunki.lwjgl.engine.graphics.material.Texture;

//Speculars must be given in tangent space
public abstract class Specular extends Texture {

    int width,height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
