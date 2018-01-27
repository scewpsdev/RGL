package net.rabbit.rgl.core.vector;

import java.util.Arrays;

public final class Matrix4 {

    public final float[] array;

    public Matrix4() {
        this.array = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f };
    }

    public Matrix4 setIdentity() {
        Arrays.fill(this.array, 0.0f);
        this.array[0] = 1.0f;
        this.array[5] = 1.0f;
        this.array[10] = 1.0f;
        this.array[15] = 1.0f;

        return this;
    }

    public Matrix4 set(int x, int y, float f) {
        this.array[x * 4 + y] = f;

        return this;
    }

    public float get(int x, int y) {
        return this.array[x * 4 + y];
    }

    public static Matrix4 mul(Matrix4 m0, Matrix4 m1, Matrix4 dst) {
        dst = dst == null ? new Matrix4() : dst.setIdentity();
        float[] tmp = new float[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float sum = 0.0f;
                for (int k = 0; k < 4; k++) {
                    sum += m0.array[j + k * 4] * m1.array[k + i * 4];
                }
                tmp[i * 4 + j] = sum;
            }
        }
        System.arraycopy(tmp, 0, dst.array, 0, 16);

        return dst;
    }

    public static Matrix4 ortho(Vector2 v, Matrix4 dst) {
        return ortho(v.x, v.y, dst);
    }

    public static Matrix4 ortho(float w, float h, Matrix4 dst) {
        dst = dst == null ? new Matrix4() : dst.setIdentity();
        dst.array[0] = 2.0f / w;
        dst.array[5] = 2.0f / h;

        return dst;
    }

    public static Matrix4 translation(Vector3 v, Matrix4 dst) {
        return translation(v.x, v.y, v.z, dst);
    }

    public static Matrix4 translation(float x, float y, float z, Matrix4 dst) {
        dst = dst == null ? new Matrix4() : dst.setIdentity();
        dst.array[12] = x;
        dst.array[13] = y;
        dst.array[14] = z;

        return dst;
    }

}