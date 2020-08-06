#version 460

in vec3 outPosition;
in vec3 outNormals;
in vec2 outextureCoord;

layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gAlbedoSpec;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

void main() {

    vec4 blendMapColour = texture(blendMap,outextureCoord);

    float blackTextureAmount = 1 - (blendMapColour.r+blendMapColour.g+blendMapColour.b);
    vec2 tiledCoords = outextureCoord *128;
    vec4 backgroundTextureColour = texture(backgroundTexture,tiledCoords)*blackTextureAmount;
    vec4 rTextureColour = texture(rTexture,tiledCoords)*blendMapColour.r;
    vec4 gTextureColour = texture(gTexture,tiledCoords)*blendMapColour.g;
    vec4 bTextureColour = texture(bTexture,tiledCoords)*blendMapColour.b;

    vec4 totalColour = backgroundTextureColour+rTextureColour+gTextureColour+bTextureColour;

    gPosition = outPosition;

    gNormal = outNormals;

    gAlbedoSpec.rgb = totalColour.rgb;

}