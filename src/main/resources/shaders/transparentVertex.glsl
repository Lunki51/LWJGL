#version 460

in layout(location = 0) vec3 aPosition;
in layout(location = 1) vec3 aTangent;
in layout(location = 2) vec3 aNormal;
in layout(location = 3) vec2 aTexturePos;


out vec2 texturePos;

uniform mat4 view;
uniform mat4 projection;
uniform mat4 transform;

void main() {

    texturePos = aTexturePos;

    gl_Position = projection*view*transform*vec4(aPosition,1.0);

}
