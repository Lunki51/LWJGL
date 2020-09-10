package fr.lunki.lwjgl.engine.objects.lights;

import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public abstract class Light {
    //TODO Rework for new light rendering

    protected int lightType;
    protected Vector3f position;
    protected Vector3f colour;
    protected Vector3f attenuation = new Vector3f(1, 0, 0);

    public Light(Vector3f position, Vector3f colour) {
        this(position,colour,new Vector3f(1,0,0));
    }

    public Light(Vector3f position, Vector3f colour, Vector3f attenuation) {
        this.position = position;
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

    public void setupLight(Shader shader,int indice){
        shader.setUniform("lights[" + indice + "].Position", getPosition());
        shader.setUniform("lights[" + indice + "].Color", getColour());
        shader.setUniform("lights[" + indice + "].Attenuation", getAttenuation());
        shader.setUniform("lights[" + indice + "].lightType", this.lightType);
    }
}
