package fr.lunki.lwjgl;

import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.graphics.render.SceneRenderer;
import fr.lunki.lwjgl.engine.io.Window;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import fr.lunki.lwjgl.engine.objects.Scene;
import fr.lunki.lwjgl.engine.objects.gameobjects.TexturedGameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import fr.lunki.lwjgl.engine.objects.player.PlayerEntity;
import fr.lunki.lwjgl.engine.utils.FileModelLoader;

import static org.lwjgl.assimp.Assimp.*;

public class Main {

    public static Window window = new Window(500, 500, "Hello world");

    public static SceneRenderer renderer = new SceneRenderer();
    public static Scene scene = new Scene();
    public static Scene currentScene;
    public static Camera camera = new Camera(new Vector3f(0, 0, 5), new Vector3f(0, 0, 0), new PlayerEntity(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), FileModelLoader.readModelFile("eric.fbx", "player/", aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_JoinIdenticalVertices)[0], 2));

    public static void main(String[] args) {
        window.create();

        TexturedMesh[] cart = FileModelLoader.readModelFile("CartEmbbed.fbx", "", aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_JoinIdenticalVertices);
        TexturedMesh[] cartImage = FileModelLoader.readModelFile("Cart.fbx", "", aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_JoinIdenticalVertices);

        for(TexturedMesh mesh : cartImage){
            scene.addGameObject(new TexturedGameObject(new Vector3f(0, 0, -1), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh));
        }

        scene.setCamera(camera);

        setMainScene(scene);

        while (!window.shouldClose()) {
            window.update();
            camera.update();

            renderer.render(currentScene);

            window.swapBuffers();
        }
        getMainScene().destroy();
        window.destroy();

    }

    public static void setMainScene(Scene scene){
        currentScene = scene;
    }

    public static Scene getMainScene(){
        return currentScene;
    }

}
