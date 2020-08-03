package fr.lunki.lwjgl.engine.objects.gameobjects;

import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class TexturedGameObject extends NormalObject {
    public TexturedGameObject(Vector3f position, Vector3f rotation, Vector3f scale, TexturedMesh[] meshes) {
        super(position, rotation, scale, meshes);
    }

    @Override
    public TexturedMesh[] getMesh() {
        return (TexturedMesh[]) super.getMesh();
    }

    @Override
    public void create() {
        for (TexturedMesh mesh : getMesh()) {
            mesh.create();
        }
    }

    @Override
    public void destroy() {
        for(TexturedMesh mesh : getMesh()){
            mesh.destroy();
        }
    }
}
