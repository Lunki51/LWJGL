#version 460

in vec3 outPosition;
in vec3 outNormals;
in vec2 outextureCoord;

layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gColorSpec;

uniform sampler2D tex;

void main() {

    gPosition = outPosition;

    gNormal = outNormals;

    gColorSpec.rgb = texture(tex,outextureCoord).rgb;
    gColorSpec.a = 0;

}
