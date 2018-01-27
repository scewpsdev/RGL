package net.rabbit.rgl.core.opengl;

import static net.rabbit.rgl.core.utils.Const.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.BufferUtils;

public class Buffer implements AutoCloseable {

	public enum BufferUsage {
		StaticDraw(GL_STATIC_DRAW), DynamicDraw(GL_DYNAMIC_DRAW), StreamDraw(GL_STREAM_DRAW);

		final int handle;

		BufferUsage(int id) {
			handle = id;
		}
	}

	public enum BufferTarget {
		ArrayBuffer(GL_ARRAY_BUFFER), ElementArrayBuffer(GL_ELEMENT_ARRAY_BUFFER);

		final int handle;

		BufferTarget(int id) {
			handle = id;
		}
	}

	static final Map<BufferTarget, Buffer> bound;

	static {
		bound = new HashMap<BufferTarget, Buffer>();
	}

	final int id;
	final BufferTarget target;

	int length;

	public Buffer(BufferTarget target) {
		id = glGenBuffers();
		this.target = target;
	}

	public void bind() {
		if (bound.get(target) != this) {
			glBindBuffer(target.handle, id);
			bound.put(target, this);
		}
	}

	public void unbind() {
		if (bound.get(target) != null) {
			glBindBuffer(target.handle, NULL);
			bound.put(target, null);
		}
	}

	public void setData(byte[] data, BufferUsage usage) {
		bind();
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		glBufferData(target.handle, buffer, usage.handle);
		buffer.clear();
		buffer = null;

		length = data.length;
	}

	public void setData(short[] data, BufferUsage usage) {
		bind();
		ShortBuffer buffer = BufferUtils.createShortBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		glBufferData(target.handle, buffer, usage.handle);
		buffer.clear();
		buffer = null;

		length = data.length;
	}

	public void setData(int[] data, BufferUsage usage) {
		bind();
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		glBufferData(target.handle, buffer, usage.handle);
		buffer.clear();
		buffer = null;

		length = data.length;
	}

	public void setData(float[] data, BufferUsage usage) {
		bind();
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		glBufferData(target.handle, buffer, usage.handle);
		buffer.clear();
		buffer = null;

		length = data.length;
	}

	public void setData(double[] data, BufferUsage usage) {
		bind();
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		glBufferData(target.handle, buffer, usage.handle);
		buffer.clear();
		buffer = null;

		length = data.length;
	}

	@Override
	public void close() {
		if (bound.get(target) == this) {
			unbind();
		}
		glDeleteBuffers(id);
	}

}