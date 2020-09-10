#version 460

in vec3 position;
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

    gNormal = normalize(texture(normalMap,textureCoord).rgb);
    gNormal = gNormal * 2.0 - 1.0;
    gNormal = normalize(TBN * gNormal);

    gColorSpec.rgb = texture(tex,textureCoord).rgb;
    gColorSpec.a = normalize(texture(specular,textureCoord).r) * 0.299 + normalize(texture(specular,textureCoord).g) * 0.587 + normalize(texture(specular,textureCoord).b) * 0.114;

}
