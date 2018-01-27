package net.rabbit.rgl.core.opengl;

public final class ArrayBuffer extends Buffer {

    int elementSize;

    public ArrayBuffer() {
        super(BufferTarget.ArrayBuffer);
    }

}