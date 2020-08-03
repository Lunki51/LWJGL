package fr.lunki.lwjgl.engine.maths;

import java.util.Arrays;

public class Matrix2f {

    public static final int SIZE = 2;
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

    public static Matrix2f invert(Matrix2f matrix4f) {
        Matrix2f matrix = new Matrix2f();

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

    public static float getDet(Matrix2f matrix2f) {
        float det = matrix2f.get(0, 0) * matrix2f.get(1, 1) - matrix2f.get(0, 1) * matrix2f.get(1, 0);
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
        Matrix2f matrix2f = (Matrix2f) o;
        return Arrays.equals(elements, matrix2f.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }
}
