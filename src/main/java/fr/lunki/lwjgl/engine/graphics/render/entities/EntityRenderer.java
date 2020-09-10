package fr.lunki.lwjgl.engine.graphics.render.entities;

import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.render.MeshRenderer;
import fr.lunki.lwjgl.engine.io.Window;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import fr.lunki.lwjgl.engine.objects.gameobjects.GameObject;
import fr.lunki.lwjgl.engine.objects.gameobjects.TexturedGameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL11;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;

public abstract class EntityRenderer<K extends RawMesh, T extends GameObject> extends MeshRenderer<K, T> {

    protected EntityRenderer(Window window, Shader shader) {
        super(window, shader);
    }

    public TreeMap<Float, T> renderObject(Map<K, ArrayList<T>> toRender, Camera camera) {
        shader.bind();
        enableCulling();
        shader.setUniform("projection", window.getProjection());
        TreeMap<Float, T> transparentsObject = new TreeMap<>();
        for (K mesh : toRender.keySet()) {
            prepareMesh(mesh, camera);
            List<T> batch = toRender.get(mesh);
            for (T object : batch) {
                if (object.isShouldRender()) {
                    if (object instanceof TexturedGameObject) {
                        if (!((TexturedGameObject) object).getMesh().getMaterial().isTransparent()) {
                            render(object);
                            GL11.glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);
                        } else {
                            transparentsObject.put(Vector3f.length(Vector3f.subtract(camera.getPosition(), object.getPosition())), object);
                        }
                    } else {
                        GL11.glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);
                        render(object);
                    }
                }
            }

            unbindMesh();
        }
        disableBend();
        shader.unbind();
        return transparentsObject;
    }

    protected abstract void render(T toRender);

    protected abstract void prepareMesh(K mesh, Camera camera);

    protected abstract void unbindMesh();
}
