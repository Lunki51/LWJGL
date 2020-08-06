#version 460 core

in vec3 position;
in vec2 textureCoord;
in vec3 normal;

out vec2 passTextureCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {

    gl_Position = projection*view*model*vec4(position, 1.0);

    passTextureCoord = textureCoord;
}