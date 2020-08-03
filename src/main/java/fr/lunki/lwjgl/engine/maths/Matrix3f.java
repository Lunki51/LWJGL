package fr.lunki.lwjgl.engine.maths;

import java.util.Arrays;

public class Matrix3f {

    public static final int SIZE = 3;
    private float[] elements = new float[SIZE * SIZE];

    public float get(int x, int y) {
        return elements[y * SIZE + x];
    }

    public void set(int x, int y, float value) {
        elements[y * SIZE + x] = value;
    }

    public float[] getAll() {
        return elements;
    }

    public static Matrix3f invert(Matrix3f matrix4f) {
        Matrix3f matrix = new Matrix3f();

        float det = getDet(matrix4f);
        if (det != 0) {
            float val = 1 / det;
            for (int i = 0; i < matrix4f.SIZE; i++) {
                for (int j = 0; j < matrix4f.SIZE; j++) {
                    matrix.set(i, j, matrix4f.get(i, j) * val);
                }
            }
        }

        return matrix;
    }

    public static Matrix2f getUnderMatrix(int ignoreX, int ignoreY, Matrix3f matrix3f) {
        Matrix2f matrix2f = new Matrix2f();
        int[][] pos = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        int lvl = 0;
        for (int i = 0; i < matrix3f.SIZE; i++) {
            for (int j = 0; j < matrix3f.SIZE; j++) {
                if (i != ignoreX && j != ignoreY) {
                    matrix2f.set(pos[lvl][0], pos[lvl][1], matrix3f.get(i, j));
                    lvl++;
                }
            }
        }

        return matrix2f;
    }

    public static float getDet(Matrix3f matrix3f) {

        float det = matrix3f.get(0, 0) * Matrix2f.getDet(getUnderMatrix(0, 0, matrix3f)) - matrix3f.get(1, 0) * Matrix2f.getDet(getUnderMatrix(1, 0, matrix3f)) + matrix3f.get(2, 0) * Matrix2f.getDet(getUnderMatrix(2, 0, matrix3f));
        if (det != 0) {
            return det;
        } else {
            return 0;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix3f matrix3f = (Matrix3f) o;
        return Arrays.equals(elements, matrix3f.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }
}
