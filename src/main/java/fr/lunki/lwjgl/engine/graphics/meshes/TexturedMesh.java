package fr.lunki.lwjgl.engine.graphics.meshes;

import fr.lunki.lwjgl.engine.graphics.material.Material;
import fr.lunki.lwjgl.engine.maths.Vector2f;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class TexturedMesh extends NormalMesh {

    protected Vector2f[] textureCoord;
    protected Material material;
    protected int index;

    public TexturedMesh(Vector3f[] position, int[] indices, Vector3f[] normals, Vector2f[] textureCoord, Material material) {
        super(position, indices, normals);
        this.textureCoord = textureCoord;
        this.material = material;
        this.index = 0;
    }

    public TexturedMesh(Vector3f[] position, int[] indices, Vector3f[] normals, Vector2f[] textureCoord, Material material, int index) {
        super(position, indices, normals);
        this.textureCoord = textureCoord;
        this.material = material;
        this.index = index;
    }


    //TODO Check if the texture offset work
    public float getTextureXOffset() {
        Material atlasMaterial = getMaterial();
        int column = this.index % atlasMaterial.getAtlasSize();
        return (float) column / (float) atlasMaterial.getAtlasSize();
    }

    public float getTextureYOffset() {
        Material atlasMaterial = getMaterial();
        int row = this.index / atlasMaterial.getAtlasSize();
        return (float) row / (float) atlasMaterial.getAtlasSize();
    }

    @Override
    public void create() {
        if (!created) {
            if (this.material != null) this.material.create();
            super.create();
            generateVBO(this.textureCoord);
            created = true;
        }

    }


    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
