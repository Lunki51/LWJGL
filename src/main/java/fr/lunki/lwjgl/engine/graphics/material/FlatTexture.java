package fr.lunki.lwjgl.engine.graphics.material;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class FlatTexture extends Texture {

    protected int width;
    protected int height;
    protected String path;


    public FlatTexture(String path) {
        this.path = path;
    }

    public void create() {
        try {
            BufferedImage image = ImageIO.read(new File("res/texture/" + this.path));


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

            this.width = image.getWidth();
            this.height = image.getHeight();
            this.imageID = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, imageID);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            System.out.println("LOADED TEXTURE "+path);
        } catch (IOException e) {
            System.err.println("Impossible de lire : " + path);
        }
    }
}
