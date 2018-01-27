package net.rabbit.rgl.core.opengl;

import static net.rabbit.rgl.core.utils.Const.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;
import java.util.Map;

public class Texture implements AutoCloseable {

    public enum TextureTarget {
        Texture2D(GL_TEXTURE_2D);

        final int handle;

        TextureTarget(int id) {
            handle = id;
        }
    }

    static final Map<TextureTarget, Texture> bound;

    static {
        bound = new HashMap<TextureTarget, Texture>();
    }

    final int handle;
    final TextureTarget target;

    public Texture(TextureTarget target) {
        handle = glGenTextures();
        this.target = target;

        glBindTexture(target.handle, handle);
        glTexParameteri(target.handle, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(target.handle, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(target.handle, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(target.handle, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glBindTexture(target.handle, NULL);
    }

    public void bind() {
        if (bound.get(target) != this) {
            glBindTexture(target.handle, handle);
            bound.put(target, this);
        }
    }

    public void unbind() {
        if (bound.get(target) != null) {
            glBindTexture(target.handle, NULL);
            bound.put(target, null);
        }
    }

    public void setData(int width, int height, int[] pixels) {
    }

	@Override
	public void close() {
		if (bound.get(target) == this) {
            unbind();
        }
	}

}