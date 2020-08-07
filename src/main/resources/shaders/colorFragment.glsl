#version 460

in vec3 outPosition;
in vec3 outNormals;
in vec3 outColor;

layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gColorSpec;


void main() {

    gPosition = outPosition;

    gNormal = outNormals;

    gColorSpec.rgb = outColor;

    gColorSpec.a = 0;

}
