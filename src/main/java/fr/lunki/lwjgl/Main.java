package fr.lunki.lwjgl;

import fr.lunki.lwjgl.engine.graphics.components.specular.ConstantSpecular;
import fr.lunki.lwjgl.engine.graphics.material.FlatTexture;
import fr.lunki.lwjgl.engine.graphics.material.Material;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.graphics.render.SceneRenderer;
import fr.lunki.lwjgl.engine.io.Window;
import fr.lunki.lwjgl.engine.maths.Vector2f;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import fr.lunki.lwjgl.engine.objects.Scene;
import fr.lunki.lwjgl.engine.objects.SkyBox;
import fr.lunki.lwjgl.engine.objects.gameobjects.TexturedGameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import fr.lunki.lwjgl.engine.objects.player.PlayerEntity;
import fr.lunki.lwjgl.engine.utils.FileModelLoader;

import static org.lwjgl.assimp.Assimp.*;

public class Main {

    public static Window window = new Window(500, 500, "Hello world");
    public static Scene currentScene;

    public static SceneRenderer renderer = new SceneRenderer();
    public static Scene scene = new Scene();
    public static Camera camera = new Camera(new Vector3f(0, 0, 5), new Vector3f(0, 0, 0), new PlayerEntity(new Vector3f(0, 0, 0), new Vector3f(180, 0, 180), new Vector3f(0.012f, 0.012f, 0.012f), FileModelLoader.readModelFile("eric.fbx", "player/", aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_JoinIdenticalVertices)[0], 2));

    public static void main(String[] args) {

        window.create();

        //SETTING UP THE TEST SCENE
        TexturedMesh[] cart = FileModelLoader.readModelFile("CartEmbbed.fbx", "", aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_JoinIdenticalVertices);
        TexturedMesh windowmesh = new TexturedMesh(
                new Vector3f[]{
                        new Vector3f(1, -1, 0),
                        new Vector3f(-1, -1, 0),
                        new Vector3f(-1, 1, 0),
                        new Vector3f(1, 1, 0)
                },
                new int[]{
                        0, 1, 2,
                        0, 2, 3
                }, new Vector3f[]{new Vector3f(1, 1, 1)},
                new Vector2f[]{
                        new Vector2f(1, 0),
                        new Vector2f(0, 0),
                        new Vector2f(0, 1),
                        new Vector2f(1, 1)
                }, new Material(new ConstantSpecular(0),new FlatTexture("window.png")));

        windowmesh.getMaterial().setTransparent();


        scene.addGameObject(new TexturedGameObject(new Vector3f(0, 1, 0), new Vector3f(0, 0, 0), new Vector3f(2, 2, 2), windowmesh));
        scene.addGameObject(new TexturedGameObject(new Vector3f(0, 1, -5), new Vector3f(0, 0, 0), new Vector3f(2, 2, 2), windowmesh));
        for (TexturedMesh mesh : cart) {
            for (int i = 0; i < 10; i++) {
                scene.addGameObject(new TexturedGameObject(new Vector3f((float) Math.random() * 20, 0, (float) Math.random() * 20), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh));
            }
        }

        scene.setSkyBox(new SkyBox(500, new String[]{"sky/right.png", "sky/left.png", "sky/top.png", "sky/bottom.png", "sky/back.png", "sky/front.png"}));


        setMainScene(scene);

        //Renderer must be created at the end to avoid problems with new InputStreamReader
        renderer.create();
        //Don't know why but the camera must be set after the renderer creation for the player to render
        scene.setCamera(camera);

        //ENGINE LOOP
        while (!window.shouldClose()) {
            window.update();
            camera.update();

            renderer.render(currentScene);

            window.swapBuffers();
        }

        //DESTROYING
        getMainScene().destroy();
        window.destroy();
        renderer.destroy();

    }

    public static void setMainScene(Scene scene) {
        currentScene = scene;
    }

    public static Scene getMainScene() {
        return currentScene;
    }

}
