package fr.lunki.lwjgl.engine.graphics.material;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengles.GLES20;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;

public class CubeMapTexture extends Texture {

    String[] paths;

    public CubeMapTexture(String[] paths) {
        this.paths = paths;
    }

    @Override
    public void create() {
        try {
            this.imageID = glGenTextures();
            glBindTexture(GL_TEXTURE_CUBE_MAP, imageID);
            glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
            glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_BASE_LEVEL, 0);
            glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAX_LEVEL, 0);
            for (int i = 0; i < this.paths.length; i++) {
                BufferedImage image = ImageIO.read(new File("res/texture/" + paths[i]));
                BufferedImage converted = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                converted.getGraphics().drawImage(image, 0, 0, null);
                converted.getGraphics().dispose();
                int[] data = converted.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
                ByteBuffer buffer = BufferUtils.createByteBuffer(data.length * 4);

                for (int pixel : data) {
                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) (pixel & 0xFF));
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }

                buffer.flip();
                glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            }

        } catch (IOException e) {
            System.err.println("Impossible de lire : " + paths);
            e.printStackTrace();
        }
    }
}
