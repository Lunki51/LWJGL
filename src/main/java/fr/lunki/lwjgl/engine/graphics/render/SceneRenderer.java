package fr.lunki.lwjgl.engine.graphics.render;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.render.entities.TexturedEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.entities.TransparentRenderer;
import fr.lunki.lwjgl.engine.graphics.render.environment.SkyBoxRenderer;
import fr.lunki.lwjgl.engine.graphics.render.environment.TerrainRenderer;
import fr.lunki.lwjgl.engine.graphics.render.player.GuiRenderer;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import fr.lunki.lwjgl.engine.objects.Light;
import fr.lunki.lwjgl.engine.objects.Scene;
import fr.lunki.lwjgl.engine.objects.SkyBox;
import fr.lunki.lwjgl.engine.objects.gameobjects.TexturedGameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.TreeMap;

import static org.lwjgl.opengl.GL30.*;

public class SceneRenderer extends Renderer<Scene> {

    TexturedEntityRenderer texturedEntityRenderer;
    SkyBoxRenderer skyBoxRenderer;
    TerrainRenderer terrainRenderer;
    GuiRenderer guiRenderer;
    TransparentRenderer transparentRenderer;
    private int gPosition, gNormal, gColorSpec;
    private int gBuffer;
    private RawMesh screenMesh = new RawMesh(new Vector3f[]{new Vector3f(-1, -1, 0), new Vector3f(1, -1, 0), new Vector3f(-1, 1, 0), new Vector3f(1, 1, 0)}, new int[]{1, 2, 0, 1, 3, 2});

    //TODO Find why TF when creating too much shaders, the gBuffer goes brbrbrbrbrb
    public SceneRenderer() {
        super(Main.window, new Shader("shaders/lightVertex.glsl", "shaders/lightFragment.glsl"));
        texturedEntityRenderer = new TexturedEntityRenderer();
        skyBoxRenderer = new SkyBoxRenderer();
        terrainRenderer = new TerrainRenderer();
        guiRenderer = new GuiRenderer();
        transparentRenderer = new TransparentRenderer();
    }

    @Override
    public void render(Scene toRender) {

        Camera camera = toRender.getCamera();
        ArrayList<Light> lights = toRender.getLights();
        SkyBox skybox = toRender.getSkyBox();
        TreeMap<Float, TexturedGameObject> treeMap = new TreeMap<>();

        glBindFramebuffer(GL_FRAMEBUFFER, gBuffer);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (toRender.getTerrainsToRender() != null)
            terrainRenderer.renderTerrain(toRender.getTerrainsToRender(), camera);

        if (camera != null) {
            if (toRender.getTexturedObjectsToRender() != null) {
                treeMap = texturedEntityRenderer.renderObject(toRender.getTexturedObjectsToRender(), camera);
            }
        }


        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        renderLights(camera, lights);

        glBindFramebuffer(GL_READ_FRAMEBUFFER, gBuffer);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        glBlitFramebuffer(0, 0, window.getWidth(), window.getHeight(), 0, 0, window.getHeight(), window.getWidth(), GL_DEPTH_BUFFER_BIT, GL_NEAREST);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);


        if (skybox != null) skyBoxRenderer.renderSkyBox(skybox, camera);
        if (toRender.getGuisToRender() != null) guiRenderer.guiRenderer(toRender.getGuisToRender());

        transparentRenderer.renderTransparent(treeMap, camera);

    }

    //TODO Remake the lights calculation
    //TODO Why TF are objects black
    public void renderLights(Camera camera, ArrayList<Light> lights) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        shader.bind();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, gPosition);
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, gNormal);
        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, gColorSpec);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("gPosition", 0);
        shader.setUniform("gNormal", 1);
        shader.setUniform("gColorSpec", 2);
        glBindVertexArray(this.screenMesh.getVAO());
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.screenMesh.getIBO());
        glEnableVertexAttribArray(0);
        for (int i = 0; i < lights.size(); i++) {
            shader.setUniform("lights[" + i + "].Position", lights.get(i).getPosition());
            shader.setUniform("lights[" + i + "].Color", lights.get(i).getColour());
        }
        glDrawElements(GL_TRIANGLES, this.screenMesh.getIndices().length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.unbind();

    }

    @Override
    public void create() {
        shader.create();
        texturedEntityRenderer.create();
        skyBoxRenderer.create();
        terrainRenderer.create();
        transparentRenderer.create();
        guiRenderer.create();


        this.screenMesh.create();
        gBuffer = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, gBuffer);

        gPosition = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, gPosition);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA16F, window.getWidth(), window.getHeight(), 0, GL_RGBA, GL_FLOAT, (FloatBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, gPosition, 0);

        gNormal = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, gNormal);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA16F, window.getWidth(), window.getHeight(), 0, GL_RGBA, GL_FLOAT, (FloatBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT1, GL_TEXTURE_2D, gNormal, 0);


        gColorSpec = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, gColorSpec);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, window.getWidth(), window.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT2, GL_TEXTURE_2D, gColorSpec, 0);

        glDrawBuffers(new int[]{GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2});

        int rboDepth = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rboDepth);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, window.getWidth(), window.getHeight());
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboDepth);
        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
            System.err.println("Framebuffer not complete");
        glBindFramebuffer(GL_FRAMEBUFFER, 0);


    }

    @Override
    public void destroy() {
        texturedEntityRenderer.destroy();
        skyBoxRenderer.destroy();
        terrainRenderer.destroy();
        shader.destroy();
        transparentRenderer.destroy();
        guiRenderer.destroy();
    }
}
