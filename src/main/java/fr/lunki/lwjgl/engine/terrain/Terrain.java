package fr.lunki.lwjgl.engine.terrain;

import com.github.czyzby.noise4j.map.Grid;
import com.github.czyzby.noise4j.map.generator.noise.NoiseGenerator;
import com.github.czyzby.noise4j.map.generator.util.Generators;
import fr.lunki.lwjgl.engine.graphics.material.Material;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.maths.Vector2f;
import fr.lunki.lwjgl.engine.maths.Vector3f;

public class Terrain {
    private static final float SIZE = 512;
    private static final float MAX_PIXEL_COLOUR = 256*256*256;
    private Grid grid;
    private int terrainModifier;
    private int terrainRadius;

    private float x, z;
    private TexturedMesh mesh;
    private TerrainTextures terrainTextures;

    public Terrain(float gridX, float gridZ,TerrainTextures terrainTextures,int terrainModifier,int terrainRadius) {
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.terrainModifier=terrainModifier;
        this.terrainRadius=terrainRadius;
        this.grid = new Grid((int) (SIZE*4));
        this.terrainTextures=terrainTextures;
        if(terrainTextures!=null)this.mesh = generateTerrain(new Material(0,0,terrainTextures.getFont()));
    }

    public boolean contains(float posX,float posZ){
        if(posX>x && posZ>z && posX<x+SIZE && posZ<z+SIZE)return true;
        return false;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public TexturedMesh getMesh() {
        return mesh;
    }

    public void create(){
        mesh.create();
        if(!terrainTextures.isCreated())terrainTextures.create();
    }

    public void destroy(){
        this.mesh.destroy();
        this.terrainTextures.destroy();
    }

    public float getHeight(int x,int z){
        return grid.get(x*4,z*4);
    }

    public TerrainTextures getTerrainTextures() {
        return terrainTextures;
    }

    private TexturedMesh generateTerrain(Material material) {
        final NoiseGenerator generator = new NoiseGenerator();
        generator.setModifier(this.terrainModifier);
        generator.setRadius(this.terrainRadius);
        generator.setSeed(Generators.rollSeed());
        generator.generate(grid);
        int VERTEX_COUNT =  grid.getHeight();

        int count = VERTEX_COUNT * VERTEX_COUNT;
        Vector3f[] vertices = new Vector3f[count];
        Vector3f[] normals = new Vector3f[count];
        Vector2f[] textureCoords = new Vector2f[count];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;
        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                vertices[vertexPointer] = new Vector3f((float) j / ((float) VERTEX_COUNT - 1) * SIZE, grid.get(j,i), (float) i / ((float) VERTEX_COUNT - 1) * SIZE);
                normals[vertexPointer] = new Vector3f(0, 1, 0);
                textureCoords[vertexPointer] = new Vector2f((float) j / ((float) VERTEX_COUNT - 1), (float) i / ((float) VERTEX_COUNT - 1));
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return new TexturedMesh(vertices, indices, normals, textureCoords, material);
    }
}
