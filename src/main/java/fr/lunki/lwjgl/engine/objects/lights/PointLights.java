package fr.lunki.lwjgl.engine.objects.lights;

import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class PointLights extends Light{

    public PointLights(Vector3f position, Vector3f colour, Vector3f attenuation) {
        super(position, colour, attenuation);
        this.lightType=1;
    }

    public PointLights(Vector3f position, Vector3f colour) {
        super(position, colour);
        this.lightType=1;
    }

    @Override
    public void setupLight(Shader shader, int indice) {
        super.setupLight(shader,indice);
        shader.setUniform("lights[" + indice + "].cutOff", 0.0f);
        shader.setUniform("lights[" + indice + "].Rotation", new Vector3f(0,0,0));
    }

}
