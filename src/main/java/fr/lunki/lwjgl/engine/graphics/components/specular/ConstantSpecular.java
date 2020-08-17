package fr.lunki.lwjgl.engine.graphics.components.specular;

import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class ConstantSpecular extends Specular{

    public float specular;

    public ConstantSpecular(float specular) {
        this.specular = specular;
    }

    @Override
    public void create() {
        ByteBuffer buffer = BufferUtils.createByteBuffer(4*3);
        for(int i=0;i<4;i++){
            buffer.put((byte) specular);
            buffer.put((byte) specular);
            buffer.put((byte) specular);
        }
        width = 2;
        height = 2;
        glBindTexture(GL_TEXTURE_2D, imageID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D,0,GL_RGB8,2,2,0,GL_RGB,GL_UNSIGNED_BYTE,buffer);
    }
}
