package fr.lunki.lwjgl.engine.graphics.meshes;

import fr.lunki.lwjgl.engine.maths.Vector2f;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;

public class RawMesh {

    protected Vector3f[] position;
    protected int[] indices;
    protected boolean created;

    protected int vao, ibo;
    protected ArrayList<Integer> vbos = new ArrayList<>();

    public RawMesh(Vector3f[] position, int[] indices) {
        this.position = position;
        this.indices = indices;
    }

    public void create() {
        if (!created) {
            this.vao = glGenVertexArrays();
            glBindVertexArray(vao);

            generateVBO(this.position);

            storeIndices(this.indices);
            created = true;
        }
    }

    protected void generateVBO(Vector3f[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length * 3);
        float[] positionData = new float[data.length * 3];
        for (int i = 0; i < data.length; i++) {
            positionData[i * 3] = data[i].getX();
            positionData[i * 3 + 1] = data[i].getY();
            positionData[i * 3 + 2] = data[i].getZ();
        }
        buffer.put(positionData).flip();

        vbos.add(storeData(buffer, vbos.size(), 3));
    }

    protected void generateVBO(Vector2f[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length * 2);
        float[] positionData = new float[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            positionData[i * 2] = data[i].getX();
            positionData[i * 2 + 1] = data[i].getY();
        }
        buffer.put(positionData).flip();

        vbos.add(storeData(buffer, vbos.size(), 2));
    }

    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, bufferID);

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    private void storeIndices(int[] indices) {
        ibo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, ibo);
        glBufferData(GL_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int getVAO() {
        return vao;
    }

    public Vector3f[] getPosition() {
        return position;
    }

    public int getIBO() {
        return ibo;
    }

    public int[] getIndices() {
        return indices;
    }

    public void destroy() {
        glBindVertexArray(vao);
        for (int i = 0; i < vbos.size(); i++) {
            glDeleteBuffers(i);
        }
        glBindVertexArray(0);
        glDeleteVertexArrays(vao);
    }
}
