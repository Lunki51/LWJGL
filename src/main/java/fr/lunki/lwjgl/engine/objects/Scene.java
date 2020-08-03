package fr.lunki.lwjgl.engine.objects;

import fr.lunki.lwjgl.engine.graphics.gui.GuiTexture;
import fr.lunki.lwjgl.engine.objects.gameobjects.GameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import fr.lunki.lwjgl.engine.terrain.Terrain;

import java.util.ArrayList;

public class Scene {
    private ArrayList<Light> lights;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<Terrain> terrains;
    public ArrayList<GuiTexture> guis;
    private SkyBox skyBox;
    private Camera camera;

    public Scene() {
        this.lights = new ArrayList<>();
        this.gameObjects = new ArrayList<>();
        this.terrains = new ArrayList<>();
        this.guis = new ArrayList<>();
    }

    public void addLight(Light light) {
        this.lights.add(light);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObject.create();
    }

    public void addTerrain(Terrain terrain) {
        this.terrains.add(terrain);
        terrain.create();
    }

    public void setSkyBox(SkyBox skyBox) {
        if (this.skyBox != null) this.skyBox.destroy();
        this.skyBox = skyBox;
        skyBox.create();
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void destroy() {
        for (GameObject object : gameObjects) {
            object.destroy();
        }
        for (Terrain terrain : terrains) {
            terrain.destroy();
        }
        this.skyBox.destroy();
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public ArrayList<Terrain> getTerrains() {
        return terrains;
    }

    public ArrayList<GuiTexture> getGuis() {
        return guis;
    }

    public SkyBox getSkyBox() {
        return skyBox;
    }

    public Camera getCamera() {
        return camera;
    }
}
