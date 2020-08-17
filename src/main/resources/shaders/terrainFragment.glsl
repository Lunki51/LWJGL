#version 460

in vec3 position;
in vec3 normal;
in vec2 textureCoord;
in mat3 TBN;

layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gColorSpec;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform sampler2D specular;
uniform sampler2D normalMap;

void main() {

    vec4 blendMapColour = texture(blendMap,textureCoord);

    float blackTextureAmount = 1 - (blendMapColour.r+blendMapColour.g+blendMapColour.b);
    vec2 tiledCoords = textureCoord *128;
    vec4 backgroundTextureColour = texture(backgroundTexture,tiledCoords)*blackTextureAmount;
    vec4 rTextureColour = texture(rTexture,tiledCoords)*blendMapColour.r;
    vec4 gTextureColour = texture(gTexture,tiledCoords)*blendMapColour.g;
    vec4 bTextureColour = texture(bTexture,tiledCoords)*blendMapColour.b;

    vec4 totalColour = backgroundTextureColour+rTextureColour+gTextureColour+bTextureColour;

    gPosition = position;

    gNormal = texture(normalMap,textureCoord).rgb;
    gNormal = gNormal * 2.0 - 1.0;
    gNormal = normalize(TBN * gNormal);

    gColorSpec.rgb = totalColour.rgb;
    gColorSpec.a = texture(specular,textureCoord).r;

}