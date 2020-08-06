package fr.lunki.lwjgl.engine.graphics;

import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.maths.Vector2f;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import fr.lunki.lwjgl.engine.utils.FileUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private String vertexFile;
    private String fragmentFile;
    private int vertexID, fragmentID, programID;

    public Shader(String vertexPath, String fragmentPath) {
        this.vertexFile = FileUtils.loadAsString(vertexPath);
        this.fragmentFile = FileUtils.loadAsString(fragmentPath);
    }

    public void create() {
        programID = glCreateProgram();

        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexFile);
        glCompileShader(vertexID);

        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Vertex shader error : " + glGetShaderInfoLog(vertexID));
            System.err.println("Vertex shader " + vertexFile);
            return;
        }

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentFile);
        glCompileShader(fragmentID);

        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Fragment shader error : " + glGetShaderInfoLog(fragmentID));
            System.err.println("Fragment shader " + fragmentFile);
            return;
        }

        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);

        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Program link error : " + glGetProgramInfoLog(programID));
        }

        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE) {
            System.err.println("Program validation error : " + glGetProgramInfoLog(programID));
        }

        glDeleteShader(fragmentID);
        glDeleteShader(vertexID);
    }

    public int getUniformLocation(String name) {
        return glGetUniformLocation(programID, name);
    }

    public void setUniform(String name, float value) {
        glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform(String name, int value) {
        glUniform1i(getUniformLocation(name), value);
    }

    public void setUniform(String name, boolean value) {
        glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }

    public void setUniform(String name, Vector3f value) {
        glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
    }

    public void setUniform(String name, Vector2f value) {
        GL20.glUniform2f(getUniformLocation(name), value.getX(), value.getY());
    }


    public void setUniform(String name, Matrix4f value) {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);
        matrix.put(value.getAll()).flip();
        GL20.glUniformMatrix4fv(getUniformLocation(name), true, matrix);
    }

    public void bind() {
        glUseProgram(programID);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void destroy() {
        glDetachShader(programID, vertexID);
        glDetachShader(programID, fragmentID);
        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
        glDeleteProgram(programID);
    }

}
