package net.rabbit.rgl.core.opengl;

import static net.rabbit.rgl.core.utils.Const.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rabbit.rgl.core.opengl.Buffer.BufferUsage;

public final class VertexArray implements AutoCloseable {

    public enum DrawMode {
        Triangles(GL_TRIANGLES),
        Quads(GL_QUADS),
        Lines(GL_LINES),
        Points(GL_POINTS),
        TriangleStrip(GL_TRIANGLE_STRIP),
        TriangleFan(GL_TRIANGLE_FAN);

        final int handle;

        DrawMode(int id) {
            handle = id;
        }
    }

    static VertexArray bound;
    static List<Integer> attribArrays;

    static {
        bound = null;
        attribArrays = new ArrayList<Integer>();
    }

    final int handle;

    Map<Integer, ArrayBuffer> arrayBuffers;
    ElementArrayBuffer elementBuffer;

    public VertexArray() {
        handle = glGenVertexArrays();

        arrayBuffers = new HashMap<Integer, ArrayBuffer>();
        elementBuffer = null;
    }

    public void bind() {
        if (bound != this) {
            glBindVertexArray(handle);
            bound = this;
        }
    }

    public void unbind() {
        if (bound != null) {
            glBindVertexArray(NULL);
            bound = null;
        }
    }

    public static void enableAttributes(int... attribs) {
        for (int attrib : attribs) {
            if (!attribArrays.contains(attrib)) {
                glEnableVertexAttribArray(attrib);
                attribArrays.add(attrib);
            }
        }
    }

    public void enableAttributes() {
        for (int attrib : arrayBuffers.keySet()) {
            if (!attribArrays.contains(attrib)) {
                glEnableVertexAttribArray(attrib);
                attribArrays.add(attrib);
            }
        }
    }

    public static void disableAttributes(int... attribs) {
        for (int attrib : attribs) {
            if (attribArrays.contains(attrib)) {
                glDisableVertexAttribArray(attrib);
                attribArrays.remove(attrib);
            }
        }
    }

    public void disableAttributes() {
        for (int attrib : arrayBuffers.keySet()) {
            if (attribArrays.contains(attrib)) {
                glDisableVertexAttribArray(attrib);
                attribArrays.remove(attrib);
            }
        }
    }

    public void addElementBuffer(short[] data, BufferUsage usage) {
        bind();
		ElementArrayBuffer buffer = new ElementArrayBuffer();
		buffer.setData(data, usage);
		elementBuffer = buffer;
	}
	
	public void addElementBuffer(int[] data, BufferUsage usage) {
        bind();
		ElementArrayBuffer buffer = new ElementArrayBuffer();
		buffer.setData(data, usage);
		elementBuffer = buffer;
	}
	
	public void addBuffer(int index, int size, int[] data, BufferUsage usage) {
        bind();
		ArrayBuffer buffer = new ArrayBuffer();
        buffer.setData(data, usage);
		glVertexAttribIPointer(index, size, GL_UNSIGNED_INT, 0, 0);
        buffer.elementSize = size;
		arrayBuffers.put(index, buffer);
	}
	
	public void addBuffer(int index, int size, float[] data, BufferUsage usage) {
		bind();
		ArrayBuffer buffer = new ArrayBuffer();
        buffer.setData(data, usage);
		glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        buffer.elementSize = size;
		arrayBuffers.put(index, buffer);
    }

    public void draw(DrawMode mode, int count, int offset) {
        bind();
        if (elementBuffer == null) {
            glDrawArrays(mode.handle, offset, count);
        } else {
            glDrawElements(mode.handle, count, elementBuffer.type, offset);
        }
    }

    public void draw(DrawMode mode) {
        bind();
        if (elementBuffer == null) {
            glDrawArrays(mode.handle, 0, arrayBuffers.get(0).length / arrayBuffers.get(0).elementSize);
        } else {
            glDrawElements(mode.handle, elementBuffer.length, elementBuffer.type, 0);
        }
    }
    
    @Override
    public void close() {
        if (bound == this) {
            unbind();
        }
        for (Buffer buffer : arrayBuffers.values()) {
            buffer.close();
        }
        if (elementBuffer != null) {
            elementBuffer.close();
        }
        glDeleteVertexArrays(handle);
    }

}