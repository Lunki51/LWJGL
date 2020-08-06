package fr.lunki.lwjgl.engine.terrain;

import fr.lunki.lwjgl.engine.graphics.material.Texture;

public class TerrainTextures {

    private final Texture font;
    private final Texture elemR;
    private final Texture elemG;
    private final Texture elemB;
    private final Texture map;
    private boolean created = false;

    public TerrainTextures(Texture font, Texture elemR, Texture elem2, Texture elemB, Texture map) {
        this.font = font;
        this.elemR = elemR;
        this.elemG = elem2;
        this.elemB = elemB;
        this.map = map;
    }

    public void create() {
        this.font.create();
        this.elemR.create();
        this.elemG.create();
        this.elemB.create();
        this.map.create();
        created = true;
    }

    public void destroy() {
        this.font.destroy();
        this.elemR.destroy();
        this.elemG.destroy();
        this.elemB.destroy();
        this.map.destroy();
    }

    public boolean isCreated() {
        return this.created;
    }

    public Texture getFont() {
        return font;
    }

    public Texture getR() {
        return elemR;
    }

    public Texture getG() {
        return elemG;
    }

    public Texture getB() {
        return elemB;
    }

    public Texture getMap() {
        return map;
    }
}
