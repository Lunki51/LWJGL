package fr.lunki.lwjgl.engine.graphics.render.entities;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.meshes.ColoredMesh;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.objects.gameobjects.ColoredObject;
import fr.lunki.lwjgl.engine.objects.gameobjects.GameObject;
import fr.lunki.lwjgl.engine.objects.Light;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL15;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;

public class ColoredEntityRenderer extends EntityRenderer<ColoredMesh,ColoredObject>{

    public ColoredEntityRenderer() {
        super(Main.window, new Shader("shaders/colorVertex.glsl","shaders/colorFragment.glsl"));
    }

    @Override
    protected void prepareMesh(RawMesh mesh, Camera camera) {

        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,mesh.getIBO());
        shader.setUniform("view", Matrix4f.view(camera.getPosition(),camera.getRotation()));

    }

    @Override
    protected void render(ColoredObject toRender) {
        shader.setUniform("transform", Matrix4f.transform(toRender.getPosition(), toRender.getRotation(), toRender.getScale()));
    }


    @Override
    protected void unbindMesh() {
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(0);
        glBindVertexArray(0);
    }

}
