package fr.lunki.lwjgl.engine.graphics.render.environment;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.graphics.render.MeshRenderer;
import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import fr.lunki.lwjgl.engine.objects.Light;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import fr.lunki.lwjgl.engine.terrain.Terrain;
import fr.lunki.lwjgl.engine.terrain.TerrainTextures;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class TerrainRenderer extends MeshRenderer<RawMesh, Terrain> {
    public TerrainRenderer() {
        //TODO Implements the new render method
        super(Main.window, new Shader("shaders/texturedVertex.glsl", "shaders/terrainFragment.glsl"));
    }

    public void renderTerrain(HashMap<TexturedMesh, ArrayList<Terrain>> terrainsToRender, Camera camera) {
        shader.bind();

        for (TexturedMesh mesh : terrainsToRender.keySet()) {
            prepareTerrain(mesh, camera);
            for (Terrain terrain : terrainsToRender.get(mesh)) {
                bindTextures(terrain);
                render(terrain);
            }

        }
        shader.unbind();
    }

    @Override
    protected void render(Terrain toRender) {
        renderTerrain(toRender);
        unbindTerrain();
    }


    @Override
    public void create() {
        super.create();
        shader.setUniform("backgroundTexture", 0);
        shader.setUniform("rTexture", 1);
        shader.setUniform("gTexture", 2);
        shader.setUniform("bTexture", 3);
        shader.setUniform("blendMap", 4);
    }

    protected void prepareTerrain(TexturedMesh mesh, Camera camera) {
        GL30.glBindVertexArray(mesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        create();
        shader.setUniform("shineDamper", mesh.getMaterial().getShininess());
        shader.setUniform("reflectivity", mesh.getMaterial().getSpecular());
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjection());
        shader.setUniform("skyColour", window.BACKGROUND);
    }

    protected void bindTextures(Terrain terrain) {
        TerrainTextures textures = terrain.getTerrainTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL_TEXTURE_2D, textures.getFont().getImageID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL13.glBindTexture(GL_TEXTURE_2D, textures.getR().getImageID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL13.glBindTexture(GL_TEXTURE_2D, textures.getG().getImageID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL13.glBindTexture(GL_TEXTURE_2D, textures.getB().getImageID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL13.glBindTexture(GL_TEXTURE_2D, textures.getMap().getImageID());
    }

    protected void renderTerrain(Terrain terrain) {
        shader.setUniform("model", Matrix4f.transform(new Vector3f(terrain.getX(), 0, terrain.getZ()), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)));
        GL11.glDrawElements(GL_TRIANGLES, terrain.getMesh().getIndices().length, GL_UNSIGNED_INT, 0);
    }

    protected void unbindTerrain() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public ArrayList<Light> prepareLights(ArrayList<Light> lights) {
        ArrayList<Light> lightsToRender = new ArrayList<>();
        if (lights.size() != 6) {
            for (int i = 0; i < 6; i++) {
                if (i < lights.size()) {
                    lightsToRender.add(lights.get(i));
                } else {
                    lightsToRender.add(new Light(new Vector3f(1, 1, 1), new Vector3f(1, 1, 1)));
                }
            }
        } else {
            lightsToRender = lights;
        }
        return lightsToRender;
    }

}
