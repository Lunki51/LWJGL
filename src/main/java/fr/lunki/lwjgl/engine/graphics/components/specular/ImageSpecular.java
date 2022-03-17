package fr.lunki.lwjgl.engine.graphics.components.specular;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class ImageSpecular extends Specular{

    String path;

    public ImageSpecular(String path){
        this.path=path;
    }

    @Override
    public void create() {
        try{
            BufferedImage image = ImageIO.read(new File("res/texture/" +path));
            imageID = glGenTextures();
            int[] texData = image.getRGB(0,0,image.getWidth(),image.getHeight(),null,0,image.getWidth());
            ByteBuffer buffer = BufferUtils.createByteBuffer(texData.length*4);
            for(int i:texData){
                buffer.put((byte) ((i >> 16) & 0xFF));
                buffer.put((byte) ((i >> 8) & 0xFF));
                buffer.put((byte) (i & 0xFF));
                buffer.put((byte) ((i >> 24) & 0xFF));
            }
            buffer.flip();
            width = image.getWidth();
            height = image.getHeight();
            glBindTexture(GL_TEXTURE_2D, imageID);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            System.out.println("LOADED TEXTURE "+path);
        }catch (IOException e){
            System.out.println("Impossible de lire la map specular "+path);
            e.printStackTrace();
        }
    }
}
