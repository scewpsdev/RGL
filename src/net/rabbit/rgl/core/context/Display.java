package net.rabbit.rgl.core.context;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL;

import static net.rabbit.rgl.core.utils.Const.*;

public final class Display implements AutoCloseable {

    private static boolean initialized;
    private static List<Display> openDisplays;

    static {
        openDisplays = new ArrayList<Display>();
    }

    private final long handle;

    private Color bg;

    public Display(int width, int height, String title) {
        if (!initialized) {
            if (!glfwInit()) {
                throw new IllegalStateException("Failed to initialize GLFW");
            }
            initialized = true;
        }

        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL) {
            throw new IllegalStateException("Failed to create window");
        }

        glfwMakeContextCurrent(handle);
        GL.createCapabilities();

        openDisplays.add(this);

        bg = Color.BLACK;
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(handle);
    }

    public void update() {
        glfwPollEvents();
        glfwSwapBuffers(handle);
        
        glClearColor(bg.getRed() / 255.0f, bg.getGreen() / 255.0f, bg.getBlue() / 255.0f, bg.getAlpha() / 255.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void close() {
        glfwDestroyWindow(handle);
        openDisplays.remove(this);
        if (openDisplays.isEmpty()) {
            glfwTerminate();
        }
    }

    public void setBackground(Color c) {
        bg = c;
    }

}