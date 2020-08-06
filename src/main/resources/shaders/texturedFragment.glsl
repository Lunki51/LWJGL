#version 460

in vec3 outPosition;
in vec3 outNormals;
in vec2 outextureCoord;

layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gAlbedoSpec;

uniform sampler2D tex;

void main() {

    gPosition = outPosition;

    gNormal = outNormals;

    gAlbedoSpec.rgb = texture(tex,outextureCoord).rgb;
    gAlbedoSpec.a = 0;

}
