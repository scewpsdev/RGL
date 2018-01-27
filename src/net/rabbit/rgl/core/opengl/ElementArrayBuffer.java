package net.rabbit.rgl.core.opengl;

import static org.lwjgl.opengl.GL11.*;

public final class ElementArrayBuffer extends Buffer {

    int type;

    public ElementArrayBuffer() {
        super(BufferTarget.ElementArrayBuffer);
    }

    @Override
    public void setData(byte[] data, BufferUsage usage) {
        super.setData(data, usage);
        type = GL_UNSIGNED_BYTE;
    }

    @Override
    public void setData(short[] data, BufferUsage usage) {
        super.setData(data, usage);
        type = GL_UNSIGNED_SHORT;
    }

    @Override
    public void setData(int[] data, BufferUsage usage) {
        super.setData(data, usage);
        type = GL_UNSIGNED_INT;
    }

    @Override
    public void setData(float[] data, BufferUsage usage) {
        throw new IllegalStateException("Data type FLOAT is not supported for element buffers");
    }

    @Override
    public void setData(double[] data, BufferUsage usage) {
        throw new IllegalStateException("Data type DOUBLE is not supported for element buffers");
    }

}