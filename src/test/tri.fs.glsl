#version 330 core

in data {
    vec3 color;
} pass;

out vec4 out_Color;

void main() {
    out_Color = vec4(pass.color, 1.0);
}