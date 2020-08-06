package fr.lunki.lwjgl.engine.graphics.render.entities;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.meshes.NormalMesh;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.objects.gameobjects.GameObject;
import fr.lunki.lwjgl.engine.objects.Light;
import fr.lunki.lwjgl.engine.objects.gameobjects.NormalObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL15;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class NormalEntityRenderer extends EntityRenderer<NormalMesh,NormalObject> {
    public NormalEntityRenderer() {
        super(Main.window, new Shader("shaders/normalVertex.glsl","shaders/normalFragment.glsl"));
    }

    @Override
    protected void prepareMesh(RawMesh mesh, Camera camera, ArrayList<Light> lights) {
        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
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
    protected void render(NormalObject toRender) {
        shader.setUniform("transform", Matrix4f.transform(toRender.getPosition(), toRender.getRotation(), toRender.getScale()));
    }

    @Override
    protected void unbindMesh() {
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
