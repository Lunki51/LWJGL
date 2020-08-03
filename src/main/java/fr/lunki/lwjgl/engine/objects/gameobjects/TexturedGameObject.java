package fr.lunki.lwjgl.engine.objects.gameobjects;

import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class TexturedGameObject extends NormalObject {
    public TexturedGameObject(Vector3f position, Vector3f rotation, Vector3f scale, TexturedMesh mesh) {
        super(position, rotation, scale, mesh);
    }

    @Override
    public TexturedMesh getMesh() {
        return (TexturedMesh) super.getMesh();
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
