package fr.lunki.lwjgl.engine.graphics.render;

import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.io.Window;
import org.lwjgl.opengl.GL11;

public abstract class Renderer<T> {

    protected Window window;
    protected Shader shader;

    protected Renderer(Window window,Shader shader) {
        this.window = window;
        this.shader=shader;
    }

    protected void create(){
        this.shader.create();
    }

    protected void destroy(){
        this.shader.destroy();
    }

    protected abstract void render(T toRender);

    protected static void enableCulling(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    protected static void disableCulling(){
        GL11.glDisable(GL11.GL_CULL_FACE);
    }
}
