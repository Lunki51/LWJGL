package fr.lunki.lwjgl.engine.objects.gameobjects;

import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public abstract class GameObject {

    private final Vector3f position;
    private final Vector3f rotation;
    private final Vector3f scale;
    private final RawMesh mesh;
    private boolean shouldRender;

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, RawMesh mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
        this.shouldRender = true;
    }

    public boolean isShouldRender() {
        return shouldRender;
    }

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public RawMesh getMesh() {
        return mesh;
    }

    public void create() {
        mesh.create();
    }

    public void destroy() {
        mesh.destroy();
    }
}
