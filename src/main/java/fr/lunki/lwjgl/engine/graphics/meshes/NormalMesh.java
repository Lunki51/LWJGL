package fr.lunki.lwjgl.engine.graphics.meshes;

import fr.lunki.lwjgl.engine.maths.Vector3f;


public abstract class NormalMesh extends RawMesh {

    protected Vector3f[] normals;
    protected Vector3f[] tangents;

    public NormalMesh(Vector3f[] position, int[] indices, Vector3f[] normals, Vector3f[] tangents) {
        super(position, indices);
        this.normals = normals;
        this.tangents = tangents;
    }

    public NormalMesh(Vector3f[] position, int[] indices, Vector3f[] normals) {
        this(position,indices,normals,new Vector3f[0]);
    }

    @Override
    public void create() {
        if (!created) {
            super.create();
            generateVBO(tangents);
            generateVBO(normals);
            created = true;
        }

    }


}
