#version 460

in vec3 position;
in vec3 normals;
in vec3 color;

out vec3 outPosition;
out vec3 outNormals;
out vec3 outColor;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

void main() {

    vec4 worldPos = transform * vec4(position,1.0);

    outPosition = worldPos.xyz;

    outColor=color;

    outNormals = normals;

    gl_Position = projection * view * worldPos;

}
