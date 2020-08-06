package fr.lunki.lwjgl.engine.graphics.render.environment;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.render.Renderer;
import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.objects.SkyBox;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL15;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class SkyBoxRenderer extends Renderer<SkyBox> {


    public SkyBoxRenderer() {
        super(Main.window, new Shader("shaders/skyboxVertex.glsl", "shaders/skyboxFragment.glsl"));
    }

    public void renderSkyBox(SkyBox skyBox, Camera camera) {
        shader.bind();
        Matrix4f view = Matrix4f.view(camera.getPosition(), camera.getRotation());
        view.set(3, 0, 0);
        view.set(3, 1, 0);
        view.set(3, 2, 0);
        shader.setUniform("fogColor", window.BACKGROUND);
        shader.setUniform("view", view);
        shader.setUniform("projection", window.getProjection());
        render(skyBox);
        shader.unbind();
    }

    @Override
    protected void render(SkyBox toRender) {
        glBindVertexArray(toRender.getMesh().getVAO());
        glEnableVertexAttribArray(0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP, toRender.getTexture().getImageID());
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, toRender.getMesh().getIBO());
        glDrawElements(GL_TRIANGLES, toRender.getMesh().getIndices().length, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
