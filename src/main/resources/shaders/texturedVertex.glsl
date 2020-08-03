#version 460

in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 textureCoord;

out vec2 passTextureCoord;

uniform mat4 transform;
uniform mat4 view;
uniform mat4 projection;

void main() {
    gl_Position = projection * view * transform * vec4(position,1.0);
    passTextureCoord=textureCoord;
}
