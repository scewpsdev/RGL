package net.rabbit.rgl.core.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

public final class Texture2D extends Texture {

    public Texture2D() {
        super(TextureTarget.Texture2D);
    }

    @Override
    public void setData(int width, int height, int[] pixels) {
        bind();
        glTexImage2D(target.handle, 0, GL_RGBA8, width, height, 0, GL_BGRA, GL_UNSIGNED_INT, pixels);
    }

}