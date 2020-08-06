package fr.lunki.lwjgl.engine.objects.player;

import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import fr.lunki.lwjgl.engine.objects.gameobjects.TexturedGameObject;


public abstract class Playable extends TexturedGameObject {

    protected float headhight = 0;
    protected Vector3f currentSpeed;
    protected Vector3f currentRotation;
    protected boolean isCrouching;

    public Playable(Vector3f position, Vector3f rotation, Vector3f scale, TexturedMesh mesh) {
        super(position, rotation, scale, mesh);
        currentSpeed = new Vector3f(0, 0, 0);
        currentRotation = rotation;
    }

    public float getHeadhight() {
        return headhight;
    }

    public Vector3f getCurrentSpeed() {
        return currentSpeed;
    }

    public Vector3f getCurrentRotation() {
        return currentRotation;
    }

    public abstract void move();

    public boolean isCrouching() {
        return isCrouching;
    }
}
