package fr.lunki.lwjgl.engine.objects.gameobjects;

import fr.lunki.lwjgl.engine.graphics.meshes.ColoredMesh;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class ColoredObject extends NormalObject {
    public ColoredObject(Vector3f position, Vector3f rotation, Vector3f scale, ColoredMesh mesh) {
        super(position, rotation, scale, mesh);
    }

    @Override
    public ColoredMesh getMesh() {
        return (ColoredMesh) super.getMesh();
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
