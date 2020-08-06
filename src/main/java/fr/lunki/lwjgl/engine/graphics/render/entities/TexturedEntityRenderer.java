package fr.lunki.lwjgl.engine.graphics.render.entities;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.graphics.Shader;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.objects.Light;
import fr.lunki.lwjgl.engine.objects.gameobjects.TexturedGameObject;
import fr.lunki.lwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class TexturedEntityRenderer extends EntityRenderer<TexturedMesh, TexturedGameObject> {
    public TexturedEntityRenderer() {
        super(Main.window, new Shader("shaders/texturedVertex.glsl", "shaders/texturedFragment.glsl"));
    }

    @Override
    protected void prepareMesh(RawMesh mesh, Camera camera, ArrayList<Light> lights) {
        TexturedMesh texturedMesh = (TexturedMesh) mesh;
        if (texturedMesh.getMaterial().isTransparent()) {
            enableBlend();
            disableCulling();
        }
        shader.bind();
        glBindVertexArray(texturedMesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, texturedMesh.getIBO());
        glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturedMesh.getMaterial().getTexture().getImageID());
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        /*
        shader.setUniform("shineDamper", texturedMesh.getMaterial().getShininess());
        shader.setUniform("reflectivity", texturedMesh.getMaterial().getReflectivity());


        for(int i=0;i<lights.size();i++){
            shader.setUniform("lightPosition["+i+"]", lights.get(i).getPosition());
            shader.setUniform("lightColour["+i+"]", lights.get(i).getColour());
            shader.setUniform("attenuation["+i+"]",lights.get(i).getAttenuation());
        }


        shader.setUniform("useFakeLighting",texturedMesh.getMaterial().isUsingFakeLighting());
        shader.setUniform("skyColour",window.BACKGROUND);

         */
    }

    @Override
    protected void render(TexturedGameObject toRender) {
        shader.setUniform("transform", Matrix4f.transform(toRender.getPosition(), toRender.getRotation(), toRender.getScale()));
    }

    @Override
    protected void unbindMesh() {
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
