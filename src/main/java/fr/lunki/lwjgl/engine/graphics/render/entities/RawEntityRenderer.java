package fr.lunki.lwjgl.engine.graphics.render.entities;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.objects.gameobjects.GameObject;
import fr.lunki.lwjgl.engine.objects.Light;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

public class RawEntityRenderer extends EntityRenderer<RawMesh,GameObject> {
    public RawEntityRenderer() {
        super(Main.window, new Shader("shaders/rawVertex.glsl", "shaders/rawFragment.glsl"));
    }

    @Override
    protected void render(GameObject toRender) {
        shader.setUniform("transform", Matrix4f.transform(toRender.getPosition(), toRender.getRotation(), toRender.getScale()));
    }

    @Override
    protected void prepareMesh(RawMesh mesh, Camera camera, ArrayList<Light> lights) {
        GL30.glBindVertexArray(mesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        /*
        for(int i=0;i<lights.size();i++){
            shader.setUniform("lightPosition["+i+"]", lights.get(i).getPosition());
            shader.setUniform("lightColour["+i+"]", lights.get(i).getColour());
            shader.setUniform("attenuation["+i+"]",lights.get(i).getAttenuation());
        }

         */
    }

    @Override
    protected void unbindMesh() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
}
