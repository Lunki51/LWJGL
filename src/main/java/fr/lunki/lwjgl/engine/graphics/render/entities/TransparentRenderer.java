package fr.lunki.lwjgl.engine.graphics.render.entities;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.io.Window;
import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.objects.gameobjects.TexturedGameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.TreeMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class TransparentRenderer extends EntityRenderer<TexturedMesh, TexturedGameObject> {

    public TransparentRenderer() {
        super(Main.window, new Shader("shaders/transparentVertex.glsl","shaders/transparentFragment.glsl"));
    }

    public void renderTransparent(TreeMap<Float, TexturedGameObject> treeMap,Camera camera){
        this.shader.bind();
        shader.setUniform("projection",window.getProjection());
        for (Float f : treeMap.descendingKeySet()) {
            TexturedGameObject object = treeMap.get(f);
            prepareMesh(object.getMesh(),camera);
            render(object);
            glDrawElements(GL_TRIANGLES, object.getMesh().getIndices().length, GL_UNSIGNED_INT, 0);
            unbindMesh();
        }
        this.shader.unbind();
    }

    @Override
    protected void render(TexturedGameObject toRender) {
        shader.setUniform("transform", Matrix4f.transform(toRender.getPosition(), toRender.getRotation(), toRender.getScale()));
    }

    @Override
    protected void prepareMesh(TexturedMesh mesh, Camera camera) {
        if (mesh.getMaterial().isTransparent()) {
            enableBlend();
            disableCulling();
        }
        shader.bind();
        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mesh.getMaterial().getTexture().getImageID());
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
    }

    @Override
    protected void unbindMesh() {
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(3);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
