#version 460

in vec3 aPosition;
in vec3 aTangent;
in vec3 aNormal;
in vec2 aTextureCoord;

out vec3 position;
out vec2 textureCoord;
out mat3 TBN;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

void main() {

    vec4 worldPos = transform * vec4(aPosition,1.0);

    vec3 T = normalize(vec3(transform*vec4(aTangent,0.0)));
    vec3 N = normalize(vec3(transform*vec4(aNormal,0.0)));
    T = normalize(T - dot(T,N) * N);
    vec3 B = cross(N,T);
    TBN = mat3(T,B,N);

    position = worldPos.xyz;

    textureCoord=aTextureCoord;

    gl_Position = projection * view * transform * vec4(position,1.0);

}
