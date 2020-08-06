#version 460

in vec3 aPosition;

out vec2 TexCoords;

void main() {

    gl_Position = vec4(aPosition,1.0);

    TexCoords = vec2((aPosition.x+1)/2,(aPosition.y+1)/2);

}
