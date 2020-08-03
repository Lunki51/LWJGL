package fr.lunki.lwjgl.engine.graphics.render;

import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.io.Window;

public abstract class MeshRenderer<K,T> extends Renderer<T>{

    protected MeshRenderer(Window window, Shader shader) {
        super(window, shader);
    }

    protected abstract void render(T toRender);
}
