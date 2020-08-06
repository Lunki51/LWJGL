package fr.lunki.lwjgl.engine.objects;

import fr.lunki.lwjgl.engine.maths.Vector3f;

public class Light {
    //TODO Rework for new light rendering

    private Vector3f position;
    private Vector3f colour;
    private Vector3f attenuation = new Vector3f(1, 0, 0);

    public Light(Vector3f postion, Vector3f colour) {
        this.position = postion;
        this.colour = colour;
    }

    public Light(Vector3f postion, Vector3f colour, Vector3f attenuation) {
        this.position = postion;
        this.colour = colour;
        this.attenuation = attenuation;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
}
