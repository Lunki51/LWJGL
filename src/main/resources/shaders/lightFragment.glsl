#version 460
out vec4 FragColor;

in vec2 TexCoords;

uniform sampler2D gPosition;
uniform sampler2D gNormal;
uniform sampler2D gColorSpec;
uniform sampler2D normalMap;

struct Light {
    vec3 Position;
    vec3 Color;
    vec3 Attenuation;
    vec3 Rotation;
    float cutOff;
    int lightType;
};
const int NR_LIGHTS = 32;
uniform Light lights[NR_LIGHTS];
uniform vec3 viewPos;


const float ambientLight = 0.1;


void main()
{

    vec3 FragPos = texture(gPosition, TexCoords).rgb;
    vec3 Normal = texture(gNormal, TexCoords).rgb;
    vec3 Albedo = texture(gColorSpec, TexCoords).rgb;
    float Specular = texture(gColorSpec, TexCoords).a;

    vec3 lighting = Albedo * 0.1;

    vec3 viewDirection = normalize(viewPos - FragPos);
    for(int i=0;i<NR_LIGHTS;i++){
        vec3 vectorToLight = normalize(lights[i].Position-FragPos);

        //Attenuation
        float distance = length(vectorToLight);
        float attFactor = (lights[i].Attenuation.x) + (lights[i].Attenuation.y*distance) + (lights[i].Attenuation.z*distance*distance);

        //Case of a sun
        if(lights[i].lightType==0){
            vectorToLight = normalize(-lights[i].Position);
            attFactor = 1;
        }
        //Diffuse
        vec3 diff = max(dot(Normal, vectorToLight), 0.0) * Albedo * lights[i].Color;

        //Specular
        vec3 halfwayDir = normalize(vectorToLight + viewDirection);
        float spec = pow(max(dot(Normal, halfwayDir), 0.0), 16.0);
        vec3 specular = lights[i].Color * spec * Specular;

        if(lights[i].lightType == 2){
            float theta = dot(normalize(FragPos - lights[i].Position),normalize(-lights[i].Rotation));
            if(theta < lights[i].cutOff){
                lighting += diff/attFactor + specular/attFactor;
            }
        }else{
            lighting += diff/attFactor + specular/attFactor;
        }

    }

    FragColor = vec4(lighting , 1.0);
}