#version 460

in vec3 position;
in vec3 normals;
in vec3 color;

out vec3 outPosition;
out vec3 outNormals;
out vec3 outColor;

uniform mat4 transform;

void main() {

    outPosition = (transform * vec4(position,1.0)).xyz;

    outColor=color;

    outNormals = normals;

}
