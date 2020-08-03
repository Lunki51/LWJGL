package fr.lunki.lwjgl.engine.utils;

import com.sun.istack.internal.Nullable;
import fr.lunki.lwjgl.engine.graphics.material.EmbeedFlatTexture;
import fr.lunki.lwjgl.engine.graphics.material.FlatTexture;
import fr.lunki.lwjgl.engine.graphics.material.Material;
import fr.lunki.lwjgl.engine.graphics.material.Texture;
import fr.lunki.lwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.lwjgl.engine.maths.Vector2f;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.assimp.Assimp.*;

public class FileModelLoader {

    public static TexturedMesh[] readModelFile(String filename, @Nullable String dataFile, @Nullable int flags) {
        AIScene fileScene = aiImportFile("res/model/" + filename, flags);
        if (fileScene == null) System.err.println("Impossible de lire " + fileScene);
        PointerBuffer aiMats = fileScene.mMaterials();

        ArrayList<TexturedMesh> meshes = new ArrayList<>();

        //Retrieving textures
        HashMap<String, Texture> textures = processEmbeedTextures(fileScene);

        ArrayList<Material> materials = new ArrayList<>();

        while (aiMats.hasRemaining()) {
            AIMaterial material = AIMaterial.create(aiMats.get());
            Vector3f color = processColor(material);
            materials.add(processMaterial(material, textures, dataFile));
        }

        PointerBuffer aiMeshes = fileScene.mMeshes();

        while (aiMeshes.hasRemaining()) {
            AIMesh aiMesh = AIMesh.create(aiMeshes.get());
            meshes.add(processMesh(aiMesh, materials));
        }
        return meshes.toArray(new TexturedMesh[meshes.size()]);
    }

    private static TexturedMesh processMesh(AIMesh mesh, ArrayList<Material> materials) {
        Vector3f[] normals = processNormals(mesh);
        Vector2f[] textCoord = processTextCoords(mesh);
        Vector3f[] positions = processPosition(mesh);
        int[] indices = processIndices(mesh);
        return new TexturedMesh(positions, indices, normals, textCoord, materials.get(mesh.mMaterialIndex()));
    }

    public static Vector3f[] processNormals(AIMesh aiMesh) {
        AIVector3D.Buffer buffer = aiMesh.mNormals();
        if (buffer == null) return new Vector3f[]{new Vector3f(1, 1, 1)};
        Vector3f[] normals = new Vector3f[buffer.remaining()];
        for (int i = 0; buffer.remaining() > 0; i++) {
            AIVector3D vector3D = buffer.get();
            normals[i] = new Vector3f(vector3D.x(), vector3D.y(), vector3D.z());
        }
        return normals;
    }

    public static Vector2f[] processTextCoords(AIMesh aiMesh) {
        AIVector3D.Buffer aiTexturesCoord = aiMesh.mTextureCoords(0);

        ArrayList<Vector2f> textCorrd = new ArrayList<>();
        int numTextCoords = aiTexturesCoord != null ? aiTexturesCoord.remaining() : 0;
        for (int i = 0; i < numTextCoords; i++) {
            AIVector3D textCoord = aiTexturesCoord.get();
            textCorrd.add(new Vector2f(textCoord.x(), 1f - textCoord.y()));
        }
        return textCorrd.toArray(new Vector2f[textCorrd.size()]);
    }

    public static Vector3f[] processPosition(AIMesh aiMesh) {
        AIVector3D.Buffer aiVertices = aiMesh.mVertices();
        Vector3f[] vertices = new Vector3f[aiVertices.remaining()];
        for (int i = 0; aiVertices.remaining() > 0; i++) {
            AIVector3D aiVertex = aiVertices.get();
            vertices[i] = new Vector3f(aiVertex.x(), aiVertex.y(), aiVertex.z());
        }
        return vertices;
    }

    public static int[] processIndices(AIMesh aiMesh) {
        int numFaces = aiMesh.mNumFaces();
        AIFace.Buffer aiFaces = aiMesh.mFaces();
        int[] indices = new int[numFaces * 3];
        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer buffer = aiFace.mIndices();
            for (int j = 0; buffer.remaining() > 0; j++) {
                indices[j + i * 3] = buffer.get();
            }
        }
        return indices;
    }

    private static HashMap<String, Texture> processEmbeedTextures(AIScene scene) {
        HashMap<String, Texture> textures = new HashMap<String, Texture>();
        if (scene.mNumTextures() != 0) {
            PointerBuffer AITextures = scene.mTextures();
            while (AITextures.hasRemaining()) {
                AITexture aiTexture = AITexture.create(AITextures.get());
                String fileName = aiTexture.mFilename().dataString();
                if (aiTexture.mHeight() == 0) {
                    try {
                        long address = aiTexture.pcData(0).address0();
                        ByteBuffer buffer = MemoryUtil.memByteBuffer(address, aiTexture.mWidth());
                        BufferedImage image = ImageIO.read(new ByteBufferBackedInputStream(buffer));
                        textures.put(fileName, new EmbeedFlatTexture(image));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AITexel.Buffer texels = aiTexture.pcData(aiTexture.mWidth() * aiTexture.mHeight());
                    textures.put(fileName, new EmbeedFlatTexture(texels, aiTexture.mWidth(), aiTexture.mHeight()));
                }


            }
        }
        return textures;
    }

    private static Vector3f processColor(AIMaterial aiMaterial) {
        AIColor4D color4D = AIColor4D.calloc();
        aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_DIFFUSE, 0, 0, color4D);
        return new Vector3f(color4D.r(), color4D.g(), color4D.b());
    }

    private static Material processMaterial(AIMaterial material, HashMap<String, Texture> textures, String dataFile) {

        //Retrieving properties
        PointerBuffer aiMaterialProperties = material.mProperties();
        float shininess = 0.0f;
        float reflectivity = 0.0f;

        while (aiMaterialProperties.hasRemaining()) {
            AIMaterialProperty materialProperty = AIMaterialProperty.create(aiMaterialProperties.get());
            AIString propertyName = materialProperty.mKey();
            if (propertyName.dataString().equals("$mao.shininess")) {
                ByteBuffer var = materialProperty.mData();
                int value = Integer.valueOf(String.valueOf(var.array()));
                shininess = value;
            }
            if (propertyName.dataString().equals("$mao.reflectivity")) {
                ByteBuffer var = materialProperty.mData();
                int value = Integer.valueOf(String.valueOf(var.array()));
                reflectivity = value;
            }
        }

        //Processing material
        AIString aiPath = AIString.calloc();
        for (int i = 0; i < 13; i++) {
            int valid = aiGetMaterialTexture(material, i, 0, aiPath, (int[]) null, null, null, null, null, null);
            if (valid != -1) {
                String path = aiPath.dataString();
                if (path.contains(".fbm")) {
                    return new Material(shininess, reflectivity, textures.get(path));
                } else {
                    return new Material(shininess, reflectivity, new FlatTexture(dataFile + path));
                }

            }
        }
        System.err.println("ERROR WITH A MATERIAL IT IS NULL");
        return null;
    }

}
