package fr.lunki.lwjgl.engine.objects.player;

import fr.lunki.lwjgl.engine.io.Input;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class Camera {

    private Vector3f position, rotation;
    private float cameraDistance;
    private final Playable playerEntity;
    private final int VIEWANGLE = 10;
    private double oldScroll = 0;

    public Camera(Vector3f position, Vector3f rotation, Playable playerEntity) {
        this.position = position;
        this.rotation = rotation;
        this.playerEntity = playerEntity;
        this.cameraDistance = 0;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public boolean isNear() {
        return cameraDistance == 0;
    }

    public void update() {
        updateCameraDistance();
        this.playerEntity.move();

        Vector3f playerPosition = playerEntity.getPosition();
        if (isNear()) {
            playerEntity.setShouldRender(false);
            setPosition(new Vector3f(playerPosition.getX(), playerPosition.getY() + (playerEntity.isCrouching() ? 1 : playerEntity.getHeadhight()), playerPosition.getZ()));
            getRotation().add(playerEntity.getCurrentRotation());
            if (getRotation().getX() > 80) getRotation().setX(80);
            if (getRotation().getX() < -80) getRotation().setX(-80);

        } else {

            playerEntity.setShouldRender(true);
            double backLength = cameraDistance * Math.cos(Math.toRadians(VIEWANGLE));
            double upLength = cameraDistance * Math.sin(Math.toRadians(VIEWANGLE));
            double xDirection = Math.sin(Math.toRadians(playerEntity.getRotation().getY()));
            double zDirection = Math.cos(Math.toRadians(playerEntity.getRotation().getY()));
            setPosition(new Vector3f((float) (playerPosition.getX() + (xDirection * backLength)), (float) (playerPosition.getY() + (playerEntity.isCrouching() ? 1 : playerEntity.getHeadhight()) + upLength), (float) (playerPosition.getZ() + (zDirection * backLength))));
            setRotation(new Vector3f(0, getRotation().getY(), 0));
            getRotation().add(new Vector3f(0, playerEntity.getCurrentRotation().getY(), 0));

        }
    }

    public void updateCameraDistance() {
        double scroll = Input.getScrollY() - oldScroll;
        cameraDistance -= scroll;
        cameraDistance = this.cameraDistance < 0 ? 0 : cameraDistance;
        oldScroll += scroll;
    }


    public Playable getPlayerEntity() {
        return playerEntity;
    }
}