package fr.lunki.lwjgl.engine.maths;

import java.util.Arrays;

public class Matrix4f {

    public static final int SIZE = 4;
    private final float[] elements = new float[SIZE * SIZE];

    public Matrix4f(Vector4f vector4f) {
        set(0, 0, vector4f.getX());
        set(1, 1, vector4f.getY());
        set(2, 2, vector4f.getZ());
        set(3, 3, vector4f.getW());
    }

    public Matrix4f() {
    }

    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result.set(i, j, 0);
            }
        }

        result.set(0, 0, 1);
        result.set(1, 1, 1);
        result.set(2, 2, 1);
        result.set(3, 3, 1);

        return result;
    }

    public static Matrix4f translate(Vector3f translate) {
        Matrix4f result = Matrix4f.identity();

        result.set(3, 0, translate.getX());
        result.set(3, 1, translate.getY());
        result.set(3, 2, translate.getZ());

        return result;
    }

    public static Matrix4f invert(Matrix4f matrix4f) {
        Matrix4f matrix = new Matrix4f();

        int[][] numb = {{1, -1, 1, -1}, {-1, 1, -1, 1}, {1, -1, 1, -1}, {-1, 1, -1, 1}};
        float det = getDet(matrix4f);
        float val = det == 0 ? 0 : 1 / det;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix.set(i, j, Matrix3f.getDet(getUnderMatrix(j, i, matrix4f)) * val * numb[i][j]);
            }
        }
        return matrix;
    }

    public static Matrix3f getUnderMatrix(int ignoreX, int ignoreY, Matrix4f matrix4f) {
        Matrix3f matrix3f = new Matrix3f();
        int[][] pos = {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}};
        int lvl = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i != ignoreX && j != ignoreY) {
                    matrix3f.set(pos[lvl][0], pos[lvl][1], matrix4f.get(i, j));
                    lvl++;
                }
            }
        }

        return matrix3f;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                s += get(i, j) + "\t";
            }
            s += "\n";
        }
        return s;
    }

    public static float getDet(Matrix4f matrix4f) {

        float det = matrix4f.get(0, 0) * Matrix3f.getDet(getUnderMatrix(0, 0, matrix4f)) - matrix4f.get(1, 0) * Matrix3f.getDet(getUnderMatrix(1, 0, matrix4f)) + matrix4f.get(2, 0) * Matrix3f.getDet(getUnderMatrix(2, 0, matrix4f)) - matrix4f.get(3, 0) * Matrix3f.getDet(getUnderMatrix(3, 0, matrix4f));
        return det;

    }

    public static Matrix4f rotate(float angle, Vector3f axis) {
        Matrix4f result = Matrix4f.identity();

        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        float C = 1 - cos;

        result.set(0, 0, axis.getX() * axis.getX() * C + cos);
        result.set(0, 1, axis.getX() * axis.getY() * C - axis.getZ() * sin);
        result.set(0, 2, axis.getX() * axis.getZ() * C + axis.getY() * sin);
        result.set(1, 0, axis.getY() * axis.getX() * C + axis.getZ() * sin);
        result.set(1, 1, cos + axis.getY() * axis.getY() * C);
        result.set(1, 2, axis.getY() * axis.getZ() * C - axis.getX() * sin);
        result.set(2, 0, axis.getZ() * axis.getX() * C - axis.getY() * sin);
        result.set(2, 1, axis.getZ() * axis.getY() * C + axis.getX() * sin);
        result.set(2, 2, cos + axis.getZ() * axis.getZ() * C);

        return result;
    }

    public static Matrix4f scale(Vector3f scalar) {
        Matrix4f result = Matrix4f.identity();

        result.set(0, 0, scalar.getX());
        result.set(1, 1, scalar.getY());
        result.set(2, 2, scalar.getZ());

        return result;
    }


    public static Matrix4f transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        Matrix4f result = Matrix4f.identity();
        Matrix4f translationMatrix = Matrix4f.translate(position);
        Matrix4f rotXMatrix = Matrix4f.rotate(rotation.getX(), new Vector3f(1, 0, 0));
        Matrix4f rotYMatrix = Matrix4f.rotate(rotation.getY(), new Vector3f(0, 1, 0));
        Matrix4f rotZMatrix = Matrix4f.rotate(rotation.getZ(), new Vector3f(0, 0, 1));
        Matrix4f scaleMatrix = Matrix4f.scale(scale);

        Matrix4f rotationMatrix = Matrix4f.multiply(rotXMatrix, Matrix4f.multiply(rotYMatrix, rotZMatrix));

        result = Matrix4f.multiply(result, translationMatrix);
        result = Matrix4f.multiply(result, rotationMatrix);
        result = Matrix4f.multiply(result, scaleMatrix);

        return result;
    }

    public static Matrix4f multiply(Matrix4f matrix, Matrix4f other) {
        Matrix4f result = Matrix4f.identity();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result.set(i, j, other.get(i, 0) * matrix.get(0, j) +
                        other.get(i, 1) * matrix.get(1, j) +
                        other.get(i, 2) * matrix.get(2, j) +
                        other.get(i, 3) * matrix.get(3, j));
            }
        }

        return result;
    }

    public static Matrix4f projection(float fov, float aspect, float near, float far) {
        Matrix4f result = Matrix4f.identity();

        float tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float range = far - near;

        result.set(0, 0, 1.0f / (aspect * tanFOV));
        result.set(1, 1, 1.0f / tanFOV);
        result.set(2, 2, -((far + near) / range));
        result.set(2, 3, -1.0f);
        result.set(3, 2, -((2 * far * near) / range));
        result.set(3, 3, 0.0f);

        return result;
    }

    public static Matrix4f view(Vector3f position, Vector3f rotation) {
        Matrix4f result = Matrix4f.identity();

        Matrix4f rotXMatrix = Matrix4f.rotate(rotation.getX(), new Vector3f(1, 0, 0));
        Matrix4f rotYMatrix = Matrix4f.rotate(rotation.getY(), new Vector3f(0, 1, 0));
        Vector3f negative = new Vector3f(-position.getX(), -position.getY(), -position.getZ());
        Matrix4f translationMatrix = Matrix4f.translate(negative);

        result = Matrix4f.multiply(result, rotXMatrix);
        result = Matrix4f.multiply(result, rotYMatrix);
        result = Matrix4f.multiply(result, translationMatrix);

        return result;
    }

    public static Vector4f multiply(Matrix4f matrix4f, Vector4f vector4f) {
        Vector4f v2 = new Vector4f();
        v2.setX(vector4f.getX() * matrix4f.get(0, 0) + vector4f.getY() * matrix4f.get(1, 0) + vector4f.getZ() * matrix4f.get(2, 0) + vector4f.getW() * matrix4f.get(3, 0));
        v2.setY(vector4f.getX() * matrix4f.get(0, 1) + vector4f.getY() * matrix4f.get(1, 1) + vector4f.getZ() * matrix4f.get(2, 1) + vector4f.getW() * matrix4f.get(3, 1));
        v2.setZ(vector4f.getX() * matrix4f.get(0, 2) + vector4f.getY() * matrix4f.get(1, 2) + vector4f.getZ() * matrix4f.get(2, 2) + vector4f.getW() * matrix4f.get(3, 2));
        v2.setW(vector4f.getX() * matrix4f.get(0, 3) + vector4f.getY() * matrix4f.get(1, 3) + vector4f.getZ() * matrix4f.get(2, 3) + vector4f.getW() * matrix4f.get(3, 3));
        return v2;
    }


    public float get(int x, int y) {
        return elements[y * SIZE + x];
    }

    public void set(int x, int y, float value) {
        elements[y * SIZE + x] = value;
    }

    public float[] getAll() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix4f matrix4f = (Matrix4f) o;
        return Arrays.equals(elements, matrix4f.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }
}
