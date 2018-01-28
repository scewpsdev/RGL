#version 330 core

layout (location = 0) in vec2 in_Position;
layout (location = 1) in vec3 in_Color;

out data {
    vec3 color;
} pass;

void main() {
    gl_Position = vec4(in_Position, 0.0, 1.0);
    pass.color = in_Color;
}