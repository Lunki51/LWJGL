package fr.lunki.lwjgl.engine.graphics.gui;

import fr.lunki.lwjgl.engine.graphics.material.Material;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.maths.Vector2f;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class GuiTexture {

    private Vector2f textPos;
    private Vector2f textScale;
    private Material material;

    public GuiTexture(Vector2f position, Vector2f scale, Material material) {
        this.textPos=position;
        this.textScale=scale;
        this.material=material;
        this.material.setTransparent();
    }

    public void create(){
        this.material.create();
    }

    public void destroy(){
        this.material.destroy();
    }

    public Vector2f getTextPos() {
        return textPos;
    }

    public Vector2f getTextScale() {
        return textScale;
    }

    public Material getMaterial() {
        return material;
    }
}