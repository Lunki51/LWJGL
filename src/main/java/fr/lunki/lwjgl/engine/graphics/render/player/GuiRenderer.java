package fr.lunki.lwjgl.engine.graphics.render.player;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.gui.GuiTexture;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.render.Renderer;
import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import org.lwjgl.opengl.GL13;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class GuiRenderer extends Renderer<GuiTexture> {

    private RawMesh mesh;

    public GuiRenderer() {
        super(Main.window, new Shader("shaders/guiVertex.glsl", "shaders/guiFragment.glsl"));
        this.mesh = new RawMesh(new Vector3f[]{new Vector3f(-1, 1, 0), new Vector3f(-1, -1, 0), new Vector3f(1, 1, 0), new Vector3f(1, -1, 0)}, new int[4]);
    }

    public void guiRenderer(List<GuiTexture> toRender) {
        shader.bind();
        glBindVertexArray(this.mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        for (GuiTexture gui : toRender) {
            render(gui);
        }
        glDisable(GL_BLEND);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        disableCulling();
        shader.unbind();
    }

    @Override
    protected void render(GuiTexture toRender) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL_TEXTURE_2D, toRender.getMaterial().getTexture().getImageID());
        shader.setUniform("transformation", Matrix4f.transform(new Vector3f(toRender.getTextPos(), 0), new Vector3f(0, 0, 0), new Vector3f(toRender.getTextScale(), 1)));
        shader.setUniform("position", toRender.getTextPos());
        glDrawArrays(GL_TRIANGLE_STRIP, 0, mesh.getIndices().length);
    }

    @Override
    public void destroy() {
        super.destroy();
        this.mesh.destroy();
    }
}
