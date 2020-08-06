#version 460

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D guiTex;

void main() {

    out_Color = texture(guiTex,textureCoords);

}
