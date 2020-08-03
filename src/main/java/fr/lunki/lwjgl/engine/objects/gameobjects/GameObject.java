package fr.lunki.lwjgl.engine.objects.gameobjects;

import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class GameObject {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;
    private RawMesh[] meshes;
    private boolean shouldRender;

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, RawMesh[] meshes) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.meshes = meshes;
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

    public RawMesh[] getMesh() {
        return meshes;
    }

    public void create(){
        for(RawMesh mesh : getMesh()){
            mesh.create();
        }
    }

    public void destroy(){
        for(RawMesh mesh : getMesh()){
            mesh.destroy();
        }
    }
}
