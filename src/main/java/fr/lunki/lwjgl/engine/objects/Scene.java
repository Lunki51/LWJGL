package fr.lunki.lwjgl.engine.objects;

import fr.lunki.lwjgl.engine.graphics.gui.GuiTexture;
import fr.lunki.lwjgl.engine.graphics.material.Material;
import fr.lunki.lwjgl.engine.graphics.meshes.NormalMesh;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.objects.gameobjects.GameObject;
import fr.lunki.lwjgl.engine.objects.gameobjects.NormalObject;
import fr.lunki.lwjgl.engine.objects.gameobjects.TexturedGameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import fr.lunki.lwjgl.engine.terrain.Terrain;

import java.util.ArrayList;
import java.util.HashMap;

public class Scene {
    private final ArrayList<Light> lights;
    private SkyBox skyBox;
    private Camera camera;

    HashMap<RawMesh, ArrayList<GameObject>> rawObjectsToRender;
    HashMap<TexturedMesh, ArrayList<TexturedGameObject>> texturedObjectsToRender;
    HashMap<TexturedMesh, ArrayList<Terrain>> terrainsToRender;
    HashMap<Material, ArrayList<GuiTexture>> guisToRender;

    public Scene() {
        this.lights = new ArrayList<>();

        rawObjectsToRender = new HashMap<>();
        texturedObjectsToRender = new HashMap<>();
        terrainsToRender = new HashMap<>();
        guisToRender = new HashMap<>();
    }

    public void addLight(Light light) {
        this.lights.add(light);
    }

    public void addGameObject(GameObject gameObject) {
        HashMap selectedMap = rawObjectsToRender;

        if (gameObject instanceof NormalObject) {
            if (gameObject instanceof TexturedGameObject) {
                selectedMap = texturedObjectsToRender;
            }
        }
        if (selectedMap.containsKey(gameObject.getMesh())) {
            ((ArrayList) selectedMap.get(gameObject.getMesh())).add(gameObject);
        } else {
            selectedMap.put(gameObject.getMesh(), new ArrayList<>());
            ((ArrayList) selectedMap.get(gameObject.getMesh())).add(gameObject);
        }
        gameObject.create();
    }

    public void addTerrain(Terrain terrain) {
        if (terrainsToRender.containsKey(terrain.getMesh())) {
            terrainsToRender.get(terrain.getMesh()).add(terrain);
        } else {
            terrainsToRender.put(terrain.getMesh(), new ArrayList<>());
            terrainsToRender.get(terrain.getMesh()).add(terrain);
        }
        terrain.create();
    }

    public void addGui(GuiTexture guiTexture) {
        if (getGuisToRender().containsKey(guiTexture.getMaterial())) {
            getGuisToRender().get(guiTexture.getMaterial()).add(guiTexture);
        } else {
            getGuisToRender().put(guiTexture.getMaterial(), new ArrayList<>());
            getGuisToRender().get(guiTexture.getMaterial()).add(guiTexture);
        }
        guiTexture.create();
    }

    public void setSkyBox(SkyBox skyBox) {
        if (this.skyBox != null) this.skyBox.destroy();
        this.skyBox = skyBox;
        skyBox.create();
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        addGameObject(camera.getPlayerEntity());
    }

    public void destroy() {
        for (RawMesh mesh : getRawObjectsToRender().keySet()) {
            mesh.destroy();
        }
        for (TexturedMesh mesh : getTexturedObjectsToRender().keySet()) {
            mesh.destroy();
        }
        for (RawMesh mesh : getTerrainsToRender().keySet()) {
            mesh.destroy();
        }
        for (Material material : getGuisToRender().keySet()) {
            material.destroy();
        }

        if (this.skyBox != null) this.skyBox.destroy();
        if (getRawObjectsToRender() != null) getRawObjectsToRender().clear();
        if (getTexturedObjectsToRender() != null) getTexturedObjectsToRender().clear();
        if (getTerrainsToRender() != null) getTerrainsToRender().clear();
        if (getGuisToRender() != null) getGuisToRender().clear();
    }

    public ArrayList<Light> getLights() {
        return lights;
    }


    public HashMap<RawMesh, ArrayList<GameObject>> getRawObjectsToRender() {
        return rawObjectsToRender;
    }

    public HashMap<TexturedMesh, ArrayList<TexturedGameObject>> getTexturedObjectsToRender() {
        return texturedObjectsToRender;
    }

    public HashMap<TexturedMesh, ArrayList<Terrain>> getTerrainsToRender() {
        return terrainsToRender;
    }

    public HashMap<Material, ArrayList<GuiTexture>> getGuisToRender() {
        return guisToRender;
    }

    public SkyBox getSkyBox() {
        return skyBox;
    }

    public Camera getCamera() {
        return camera;
    }
}
