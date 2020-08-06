package fr.lunki.lwjgl.engine.graphics.render;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.gui.GuiTexture;
import fr.lunki.lwjgl.engine.graphics.material.Material;
import fr.lunki.lwjgl.engine.graphics.meshes.ColoredMesh;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.render.entities.ColoredEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.entities.NormalEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.entities.RawEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.entities.TexturedEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.environment.SkyBoxRenderer;
import fr.lunki.lwjgl.engine.graphics.render.environment.TerrainRenderer;
import fr.lunki.lwjgl.engine.graphics.render.player.GuiRenderer;
import fr.lunki.lwjgl.engine.objects.Light;
import fr.lunki.lwjgl.engine.objects.Scene;
import fr.lunki.lwjgl.engine.objects.SkyBox;
import fr.lunki.lwjgl.engine.objects.gameobjects.ColoredObject;
import fr.lunki.lwjgl.engine.objects.gameobjects.GameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import fr.lunki.lwjgl.engine.terrain.Terrain;

import java.util.ArrayList;
import java.util.HashMap;

public class SceneRenderer extends Renderer<Scene> {

    ColoredEntityRenderer coloredEntityRenderer;
    NormalEntityRenderer normalEntityRenderer;
    RawEntityRenderer rawEntityRenderer;
    TexturedEntityRenderer texturedEntityRenderer;
    SkyBoxRenderer skyBoxRenderer;
    TerrainRenderer terrainRenderer;
    GuiRenderer guiRenderer ;



    public SceneRenderer() {
        super(Main.window, null);
        coloredEntityRenderer = new ColoredEntityRenderer();
        normalEntityRenderer = new NormalEntityRenderer();
        rawEntityRenderer = new RawEntityRenderer();
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

            if(toRender.getRawObjectsToRender()!=null){
                rawEntityRenderer.renderObject(toRender.getRawObjectsToRender(),camera,lights);
            }
            if(toRender.getNormalObjectsToRender()!=null){
                normalEntityRenderer.renderObject(toRender.getNormalObjectsToRender(),camera,lights);
            }
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
        normalEntityRenderer.create();
        rawEntityRenderer.create();
        texturedEntityRenderer.create();
        skyBoxRenderer.create();
        terrainRenderer.create();
        guiRenderer.create();
    }

    @Override
    public void destroy() {
        coloredEntityRenderer.destroy();
        normalEntityRenderer.destroy();
        rawEntityRenderer.destroy();
        texturedEntityRenderer.destroy();
        skyBoxRenderer.destroy();
        terrainRenderer.destroy();
        guiRenderer.destroy();
    }
}
