#version 460

in layout(location = 0) vec3 position;
in layout(location = 1) vec3 normals;
in layout(location = 2) vec2 textureCoord;

out vec3 outPosition;
out vec3 outNormals;
out vec2 outextureCoord;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

void main() {

    vec4 worldPos = transform * vec4(position,1.0);

    outPosition = worldPos.xyz;

    outextureCoord=textureCoord;

    outNormals = normals;

    gl_Position = projection * view * transform * vec4(position,1.0);

}
