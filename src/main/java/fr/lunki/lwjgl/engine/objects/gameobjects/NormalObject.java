package fr.lunki.lwjgl.engine.objects.gameobjects;

import fr.lunki.lwjgl.engine.graphics.meshes.NormalMesh;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class NormalObject extends GameObject {
    public NormalObject(Vector3f position, Vector3f rotation, Vector3f scale, NormalMesh mesh) {
        super(position, rotation, scale, mesh);
    }

    @Override
    public NormalMesh getMesh() {
        return (NormalMesh) super.getMesh();
    }

    @Override
    public void create() {
        getMesh().create();
    }

    @Override
    public void destroy() {
        getMesh().destroy();
    }

}
