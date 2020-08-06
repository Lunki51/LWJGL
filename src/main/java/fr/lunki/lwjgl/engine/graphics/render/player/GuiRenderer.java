package fr.lunki.lwjgl.engine.graphics.render.player;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.gui.GuiTexture;
import fr.lunki.lwjgl.engine.graphics.material.Material;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.graphics.render.Renderer;
import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.maths.Vector2f;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class GuiRenderer extends Renderer<GuiTexture> {

    private RawMesh mesh;

    public GuiRenderer() {
        super(Main.window, new Shader("shaders/guiVertex.glsl", "shaders/guiFragment.glsl"));
        this.mesh = new TexturedMesh(new Vector3f[]{new Vector3f(-1, 1, 0), new Vector3f(-1, -1, 0), new Vector3f(1, 1, 0), new Vector3f(1, -1, 0)}, new int[4],new Vector3f[0],new Vector2f[0],null);
    }

    public void guiRenderer(HashMap<Material, ArrayList<GuiTexture>> guisToRender) {
        shader.bind();
        glBindVertexArray(this.mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        for (Material material : guisToRender.keySet()) {
            prepareMaterial(material);
            for (GuiTexture gui : guisToRender.get(material)) {
                render(gui);
            }
        }
        glDisable(GL_BLEND);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        disableCulling();
        shader.unbind();
    }

    protected void prepareMaterial(Material material) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL_TEXTURE_2D, material.getTexture().getImageID());
    }

    @Override
    protected void render(GuiTexture toRender) {
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
