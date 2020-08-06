package fr.lunki.lwjgl.engine.graphics.render.entities;

import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.render.MeshRenderer;
import fr.lunki.lwjgl.engine.graphics.render.Renderer;
import fr.lunki.lwjgl.engine.io.Window;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import fr.lunki.lwjgl.engine.objects.gameobjects.GameObject;
import fr.lunki.lwjgl.engine.objects.Light;
import fr.lunki.lwjgl.engine.objects.gameobjects.TexturedGameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL11;

import java.util.*;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;

public abstract class EntityRenderer<K extends RawMesh,T extends GameObject> extends MeshRenderer<K,T> {

    protected EntityRenderer(Window window, Shader shader) {
        super(window,shader);
    }

    public void renderObject(HashMap<K, ArrayList<T>> toRender, Camera camera, ArrayList<Light> lights){
        shader.bind();
        enableCulling();
        shader.setUniform("projection", window.getProjection());
        TreeMap<Float,T> transparentsObject = new TreeMap<>();
        ArrayList<Light> lightsToRender = prepareLights(lights);
        for (K mesh : toRender.keySet()) {
            prepareMesh(mesh,camera,lightsToRender);
            List<T> batch = toRender.get(mesh);
            for (T object : batch) {
                if(object.isShouldRender()){
                    if(object instanceof TexturedGameObject ){
                        if(!((TexturedGameObject)object).getMesh().getMaterial().isTransparent()){
                            render(object);
                            GL11.glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);
                        }else{
                            transparentsObject.put(Vector3f.length(Vector3f.subtract(camera.getPosition(),object.getPosition())),object);
                        }
                    }else{
                        GL11.glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);render(object);

                    }
                }
            }

            unbindMesh();
        }
        for(Float f : transparentsObject.descendingKeySet()){
            T object = transparentsObject.get(f);
            prepareMesh(object.getMesh(),camera,lightsToRender);
            render(object);
            GL11.glDrawElements(GL_TRIANGLES, object.getMesh().getIndices().length, GL_UNSIGNED_INT, 0);
            unbindMesh();
        }
        disableBend();
        shader.unbind();
    }

    public ArrayList<Light> prepareLights(ArrayList<Light> lights){
        ArrayList<Light> lightsToRender = new ArrayList<>();
        if(lights.size()!=6){
            for(int i=0;i<6;i++){
                if(i<lights.size()){
                    lightsToRender.add(lights.get(i));
                }else{
                    lightsToRender.add(new Light(new Vector3f(1,1,1),new Vector3f(1,1,1)));
                }
            }
        }else{
            lightsToRender=lights;
        }
        return lightsToRender;
    }

    protected abstract void render(T toRender);

    protected abstract void prepareMesh(RawMesh mesh, Camera camera,ArrayList<Light> lights);

    protected abstract void unbindMesh();
}
