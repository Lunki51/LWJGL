package fr.lunki.lwjgl;

import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.graphics.render.TestRenderer;
import fr.lunki.lwjgl.engine.io.Window;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import fr.lunki.lwjgl.engine.objects.gameobjects.TexturedGameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import fr.lunki.lwjgl.engine.objects.player.PlayerEntity;
import fr.lunki.lwjgl.engine.terrain.Terrain;
import fr.lunki.lwjgl.engine.utils.FileModelLoader;

import java.util.ArrayList;

import static org.lwjgl.assimp.Assimp.*;

public class Main {

    public static Window window = new Window(500, 500, "Hello world");

    public static Shader shader = new Shader("/shaders/texturedVertex.glsl", "/shaders/texturedFragment.glsl");
    public static TestRenderer testRenderer = new TestRenderer(shader);
    public static ArrayList<Terrain> terrains = new ArrayList<>();
    public static ArrayList<TexturedGameObject> gameObjects = new ArrayList<>();
    public static Camera camera = new Camera(new Vector3f(0, 0, 5), new Vector3f(0, 0, 0), new PlayerEntity(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), FileModelLoader.readModelFile("eric.fbx", "player/", aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_JoinIdenticalVertices), 2));

    public static void main(String[] args) {
        window.create();

        shader.create();

        TexturedMesh[] cart = FileModelLoader.readModelFile("CartEmbbed.fbx", "", aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_JoinIdenticalVertices);
        TexturedMesh[] cartImage = FileModelLoader.readModelFile("Cart.fbx", "", aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_JoinIdenticalVertices);

        gameObjects.add(new TexturedGameObject(new Vector3f(0, 0, -1), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), cart));
        gameObjects.add(new TexturedGameObject(new Vector3f(0, 0, -3), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), cartImage));
        for (TexturedGameObject object : gameObjects) {
            object.create();
        }
        while (!window.shouldClose()) {
            window.update();
            camera.update();

            for (TexturedGameObject object : gameObjects) {
                object.getRotation().add(0, 0.5f, 0);
            }
            testRenderer.render(gameObjects, camera);

            window.swapBuffers();
        }
        window.destroy();

    }

}
