package fr.lunki.lwjgl.engine.objects.lights;

import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class DirectionalLight extends Light {

    public DirectionalLight(Vector3f rotation, Vector3f colour) {
        super(rotation,colour);
        this.lightType=0;
    }

    public DirectionalLight(Vector3f rotation, Vector3f colour, Vector3f attenuation) {
        super(rotation, colour, attenuation);
        this.lightType=0;
    }

    @Override
    public void setupLight(Shader shader, int indice) {
        super.setupLight(shader,indice);
        shader.setUniform("lights[" + indice + "].cutOff", 0.0f);
        shader.setUniform("lights[" + indice + "].Rotation", new Vector3f(0,0,0));
    }

}
