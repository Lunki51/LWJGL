#version 460 core

in vec3 textureCoords;

out vec4 outColor;

uniform samplerCube cubeMap;
uniform vec3 fogColor;

const float lowerLimit = 0.0;
const float upperLimit = 30.0;

void main() {

    float factor = (textureCoords.y-lowerLimit ) / (upperLimit-lowerLimit);
    factor = clamp(factor,0.0,1.0);

    outColor = texture(cubeMap,textureCoords);
    //outColor = mix(vec4(fogColor,1.0),outColor,factor);
}
