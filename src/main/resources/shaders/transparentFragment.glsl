#version 460

in vec2 texturePos;

out vec4 outColor;

uniform sampler2D tex;

void main() {

    outColor = texture(tex,texturePos);

}
