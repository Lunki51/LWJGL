package fr.lunki.lwjgl.engine.objects.lights;

import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class SpotLight extends Light {

    Vector3f rotation;
    float angle;

    private SpotLight(Vector3f position, Vector3f colour) {
        super(position, colour);
    }

    private SpotLight(Vector3f position, Vector3f colour, Vector3f attenuation) {
        super(position, colour, attenuation);
    }

    public SpotLight(Vector3f position,Vector3f rotation,float angle,Vector3f colour,Vector3f attenuation){
        super(position,colour,attenuation);
        this.rotation = rotation;
        this.angle = angle;
        this.lightType = 2;
    }

    @Override
    public void setupLight(Shader shader, int indice) {
        super.setupLight(shader,indice);
        shader.setUniform("lights[" + indice + "].cutOff", this.angle);
        shader.setUniform("lights[" + indice + "].Rotation", this.rotation);
    }
}
