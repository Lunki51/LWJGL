package fr.lunki.lwjgl.engine.objects;

import fr.lunki.lwjgl.engine.graphics.material.CubeMapTexture;
import fr.lunki.lwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class SkyBox{

    private RawMesh mesh ;
    private CubeMapTexture texture;

    public SkyBox(int size, String[] texture_files) {
        this.mesh = new RawMesh(getPositions(size),getIndices());
        this.texture = new CubeMapTexture(texture_files);
    }

    public void create(){
        this.mesh.create();
        this.texture.create();
    }

    public void destroy(){
        this.mesh.destroy();
        this.texture.destroy();
    }

    public RawMesh getMesh() {
        return mesh;
    }

    public CubeMapTexture getTexture() {
        return texture;
    }

    private static int[] getIndices() {
        return new int[]{
                0, 1, 2,
                3, 4, 5,

                6, 7, 8,
                9, 10, 11,

                12, 13, 14,
                15, 16, 17,

                18, 19, 20,
                21, 22, 23,

                24, 25, 26,
                27, 28, 29,

                30, 31, 32,
                33, 34, 35
        };
    }

    private static Vector3f[] getPositions(int size){
        return
                new Vector3f[]{
                        new Vector3f(-size, size, -size),
                        new Vector3f(-size, -size, -size),
                        new Vector3f(size, -size, -size),
                        new Vector3f(size, -size, -size),
                        new Vector3f(size, size, -size),
                        new Vector3f(-size, size, -size),

                        new Vector3f(-size, -size, size),
                        new Vector3f(-size, -size, -size),
                        new Vector3f(-size, size, -size),
                        new Vector3f(-size, size, -size),
                        new Vector3f(-size, size, size),
                        new Vector3f(-size, -size, size),

                        new Vector3f(size, -size, -size),
                        new Vector3f(size, -size, size),
                        new Vector3f(size, size, size),
                        new Vector3f(size, size, size),
                        new Vector3f(size, size, -size),
                        new Vector3f(size, -size, -size),

                        new Vector3f(-size, -size, size),
                        new Vector3f(-size, size, size),
                        new Vector3f(size, size, size),
                        new Vector3f(size, size, size),
                        new Vector3f(size, -size, size),
                        new Vector3f(-size, -size, size),

                        new Vector3f(-size, size, -size),
                        new Vector3f(size, size, -size),
                        new Vector3f(size, size, size),
                        new Vector3f(size, size, size),
                        new Vector3f(-size, size, size),
                        new Vector3f(-size, size, -size),

                        new Vector3f(-size, -size, -size),
                        new Vector3f(-size, -size, size),
                        new Vector3f(size, -size, -size),
                        new Vector3f(size, -size, -size),
                        new Vector3f(-size, -size, size),
                        new Vector3f(size, -size, size)
                };
    }


}
