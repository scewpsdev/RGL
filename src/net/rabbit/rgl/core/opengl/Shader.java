package net.rabbit.rgl.core.opengl;

import static net.rabbit.rgl.core.utils.Const.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;
import net.rabbit.rgl.core.vector.Matrix4;

public final class Shader implements AutoCloseable {

    static Shader bound;

    final int program;
    final int vertex;
    final int fragment;

    final Map<String, Integer> uniforms;

    public Shader(String vertexSrc, String fragmentSrc) {

        program = glCreateProgram();
        vertex = glCreateShader(GL_VERTEX_SHADER);
        fragment = glCreateShader(GL_FRAGMENT_SHADER);

        uniforms = new HashMap<String, Integer>();

        glShaderSource(vertex, vertexSrc);
        glCompileShader(vertex);
        if (glGetShaderi(vertex, GL_COMPILE_STATUS) == GL_FALSE) {
            int logLength = glGetShaderi(this.vertex, GL_INFO_LOG_LENGTH);
            throw new IllegalStateException("Failed to compile vertex shader:\n" + glGetShaderInfoLog(vertex, logLength));
        }

        glShaderSource(fragment, fragmentSrc);
        glCompileShader(fragment);
        if (glGetShaderi(fragment, GL_COMPILE_STATUS) == GL_FALSE) {
            int logLength = glGetShaderi(this.fragment, GL_INFO_LOG_LENGTH);
            throw new IllegalStateException("Failed to compile fragment shader:\n" + glGetShaderInfoLog(vertex, logLength));
        }

        glAttachShader(program, vertex);
        glAttachShader(program, fragment);
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            int logLength = glGetProgrami(program, GL_INFO_LOG_LENGTH);
            throw new IllegalStateException("Failed to link program:\n" + glGetProgramInfoLog(program, logLength));
        }

        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
            int logLength = glGetProgrami(program, GL_INFO_LOG_LENGTH);
            throw new IllegalStateException("Failed to validate program:\n" + glGetProgramInfoLog(program, logLength));
        }

        glDeleteShader(vertex);
        glDeleteShader(fragment);

    }

    public void enable() {
        if (bound != this) {
            glUseProgram(program);
            bound = this;
        }
    }

    public void disable() {
        if (bound != null) {
            glUseProgram(NULL);
            bound = null;
        }
    }

    public void bindTexture(Texture texture, int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
        texture.bind();
    }

    public void setMat4(String name, boolean transpose, Matrix4 m) {
        glUniformMatrix4fv(getUniformLocation(name), transpose, m.array);
    }

    private int getUniformLocation(String name) {
        if (!uniforms.containsKey(name)) {
            int location = glGetUniformLocation(this.program, name);
            uniforms.put(name, location);
        }

        return uniforms.get(name);
    }

    @Override
    public void close() {
        if (bound == this) {
            disable();
        }
        
        glDetachShader(program, vertex);
        glDetachShader(program, fragment);
        glDeleteProgram(program);
    }

}