package fr.lunki.lwjgl.engine.graphics.meshes;

import fr.lunki.lwjgl.engine.maths.Vector3f;

public class ColoredMesh extends NormalMesh{

    Vector3f[] colors;

    public ColoredMesh(Vector3f[] position, int[] indices,Vector3f[] normals,Vector3f[] colors) {
        super(position, indices,normals);
        this.colors=colors;
    }

    @Override
    public void create() {
        super.create();
        generateVBO(this.colors);
    }
}
