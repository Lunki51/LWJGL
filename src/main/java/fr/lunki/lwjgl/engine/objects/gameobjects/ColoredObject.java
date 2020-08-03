package fr.lunki.lwjgl.engine.objects.gameobjects;

import fr.lunki.lwjgl.engine.graphics.meshes.ColoredMesh;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class ColoredObject extends NormalObject {
    public ColoredObject(Vector3f position, Vector3f rotation, Vector3f scale, ColoredMesh[] meshes) {
        super(position, rotation, scale, meshes);
    }

    @Override
    public ColoredMesh[] getMesh() {
        return (ColoredMesh[]) super.getMesh();
    }

    @Override
    public void create() {
        for (ColoredMesh mesh : getMesh()) {
            mesh.create();
        }
    }

    @Override
    public void destroy() {
        for(ColoredMesh mesh : getMesh()){
            mesh.destroy();
        }
    }
}
