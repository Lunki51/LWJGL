#version 460

in vec3 position;
in vec3 normal;
in vec2 textureCoord;
in mat3 TBN;

layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gColorSpec;

uniform sampler2D tex;
uniform sampler2D specular;
uniform sampler2D normalMap;

void main() {

    gPosition = position;

    gNormal = texture(normalMap,textureCoord).rgb;
    gNormal = gNormal * 2.0 - 1.0;
    gNormal = normalize(TBN * gNormal);

    gColorSpec.rgb = texture(tex,textureCoord).rgb;
    gColorSpec.a = texture(specular,textureCoord).r;

}
