package fr.lunki.lwjgl.engine.graphics.material;

import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AITexel;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class EmbeedFlatTexture extends FlatTexture {

    protected Object image;

    public EmbeedFlatTexture(AITexel.Buffer image, int width, int height) {
        super("");
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public EmbeedFlatTexture(BufferedImage image) {
        super("");
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    @Override
    public void create() {
        ByteBuffer buffer = BufferUtils.createByteBuffer(1);
        if (image instanceof AITexel.Buffer) {
            AITexel.Buffer image = (AITexel.Buffer) this.image;
            buffer = BufferUtils.createByteBuffer(image.capacity() * 4);
            int i = 0;
            for (AITexel texel : image) {
                buffer.put(texel.a());
                buffer.put(texel.r());
                buffer.put(texel.g());
                buffer.put(texel.b());
            }

            buffer.flip();

        } else if (image instanceof BufferedImage) {

            BufferedImage image = (BufferedImage) this.image;

            BufferedImage converted = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            converted.getGraphics().drawImage(image, 0, 0, null);
            converted.getGraphics().dispose();
            int[] data = converted.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            buffer = BufferUtils.createByteBuffer(data.length * 4);

            for (int pixel : data) {
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }

            buffer.flip();
        }

        this.imageID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, imageID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);


    }
}
