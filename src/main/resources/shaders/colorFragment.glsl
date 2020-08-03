#version 460

in vec3 passColor;

out vec4 outColor;


void main() {
    outColor = vec4(passColor,1.0);
}
