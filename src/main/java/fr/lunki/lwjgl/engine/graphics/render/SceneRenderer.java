package fr.lunki.lwjgl.engine.graphics.render;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.render.entities.ColoredEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.entities.NormalEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.entities.RawEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.entities.TexturedEntityRenderer;
import fr.lunki.lwjgl.engine.graphics.render.environment.SkyBoxRenderer;
import fr.lunki.lwjgl.engine.graphics.render.environment.TerrainRenderer;
import fr.lunki.lwjgl.engine.graphics.render.player.GuiRenderer;
import fr.lunki.lwjgl.engine.objects.Scene;

public class SceneRenderer extends Renderer<Scene> {

    ColoredEntityRenderer coloredEntityRenderer = new ColoredEntityRenderer();
    ;
    NormalEntityRenderer normalEntityRenderer = new NormalEntityRenderer();
    RawEntityRenderer rawEntityRenderer = new RawEntityRenderer();
    TexturedEntityRenderer texturedEntityRenderer = new TexturedEntityRenderer();
    SkyBoxRenderer skyBoxRenderer = new SkyBoxRenderer();
    TerrainRenderer terrainRenderer = new TerrainRenderer();
    GuiRenderer guiRenderer = new GuiRenderer();


    public SceneRenderer() {
        super(Main.window, null);

    }

    @Override
    public void render(Scene toRender) {

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
