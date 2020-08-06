package fr.lunki.lwjgl.engine.graphics.meshes;

import fr.lunki.lwjgl.engine.maths.Vector3f;

public abstract class NormalMesh extends RawMesh{

    protected Vector3f[] normals;

    public NormalMesh(Vector3f[] position, int[] indices,Vector3f[] normals) {
        super(position, indices);
        this.normals=normals;
    }

    @Override
    public void create() {
        if(!created){
            super.create();
            generateVBO(this.normals);
            created=true;
        }

    }
}
