#version 460 core

in layout(location = 0) vec3 position;
in layout(location = 1) vec3 normal;
in layout(location = 2) vec2 textureCoord;

out vec2 passTextureCoord;

uniform mat4 transform;
uniform mat4 view;
uniform mat4 projection;

void main() {

    gl_Position = projection * view * transform *vec4(position,1.0);

    passTextureCoord = textureCoord;

}