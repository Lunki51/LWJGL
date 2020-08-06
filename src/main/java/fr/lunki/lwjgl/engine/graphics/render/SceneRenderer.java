package fr.lunki.lwjgl.engine.graphics.render;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.render.entities.ColoredEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.entities.TexturedEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.environment.SkyBoxRenderer;
import fr.lunki.lwjgl.engine.graphics.render.environment.TerrainRenderer;
import fr.lunki.lwjgl.engine.graphics.render.player.GuiRenderer;
import fr.lunki.lwjgl.engine.objects.Light;
import fr.lunki.lwjgl.engine.objects.Scene;
import fr.lunki.lwjgl.engine.objects.SkyBox;
import fr.lunki.lwjgl.engine.objects.player.Camera;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;

public class SceneRenderer extends Renderer<Scene> {

    ColoredEntityRenderer coloredEntityRenderer;
    TexturedEntityRenderer texturedEntityRenderer;
    SkyBoxRenderer skyBoxRenderer;
    TerrainRenderer terrainRenderer;
    GuiRenderer guiRenderer;

    private int gBuffer;



    public SceneRenderer() {
        super(Main.window, null);
        coloredEntityRenderer = new ColoredEntityRenderer();
        texturedEntityRenderer = new TexturedEntityRenderer();
        skyBoxRenderer = new SkyBoxRenderer();
        terrainRenderer = new TerrainRenderer();
        guiRenderer = new GuiRenderer();
    }

    @Override
    public void render(Scene toRender) {

        Camera camera = toRender.getCamera();
        ArrayList<Light> lights = toRender.getLights();
        SkyBox skybox = toRender.getSkyBox();

        if(lights==null)lights = new ArrayList<>();

        if(skybox!=null)skyBoxRenderer.renderSkyBox(skybox,camera);
        if(toRender.getTerrainsToRender()!=null)terrainRenderer.renderTerrain(toRender.getTerrainsToRender(),camera,lights);



        if(camera!=null){
            if(toRender.getColoredObjectsToRender()!=null){
                coloredEntityRenderer.renderObject(toRender.getColoredObjectsToRender(),camera,lights);
            }
            if(toRender.getTexturedObjectsToRender()!=null){
                texturedEntityRenderer.renderObject(toRender.getTexturedObjectsToRender(),camera,lights);
            }



        }

        if(toRender.getGuisToRender()!=null)guiRenderer.guiRenderer(toRender.getGuisToRender());


    }

    @Override
    public void create() {
        coloredEntityRenderer.create();
        texturedEntityRenderer.create();
        skyBoxRenderer.create();
        terrainRenderer.create();
        guiRenderer.create();
        gBuffer = glGenFramebuffers();




        glBindFramebuffer(GL_FRAMEBUFFER,gBuffer);

        int gPosition,gNormal,gColorSpec;

        gPosition = glGenTextures();
        glBindTexture(GL_TEXTURE_2D,gPosition);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA16F, window.getWidth(), window.getHeight(), 0, GL_RGBA, GL_FLOAT, (FloatBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, gPosition, 0);

        gNormal = glGenTextures();
        glBindTexture(GL_TEXTURE_2D,gNormal);
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

        glDrawBuffers(new int[]{ GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2});

        glBindFramebuffer(GL_FRAMEBUFFER,0);

    }

    @Override
    public void destroy() {
        coloredEntityRenderer.destroy();
        texturedEntityRenderer.destroy();
        skyBoxRenderer.destroy();
        terrainRenderer.destroy();
        guiRenderer.destroy();
    }
}
