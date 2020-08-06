#version 460 core

in vec3 passColor;
in vec2 passTextureCoord;
in vec3 surfaceNormal;
in vec3 toLightVector[6];
in vec3 toCameraVector;
in float visibility;

out vec4 outColor;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform vec3 lightColour[6];
uniform vec3 attenuation[6];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

void main() {

    vec4 blendMapColour = texture(blendMap,passTextureCoord);

    float blackTextureAmount = 1 - (blendMapColour.r+blendMapColour.g+blendMapColour.b);
    vec2 tiledCoords = passTextureCoord *128;
    vec4 backgroundTextureColour = texture(backgroundTexture,tiledCoords)*blackTextureAmount;
    vec4 rTextureColour = texture(rTexture,tiledCoords)*blendMapColour.r;
    vec4 gTextureColour = texture(gTexture,tiledCoords)*blendMapColour.g;
    vec4 bTextureColour = texture(bTexture,tiledCoords)*blendMapColour.b;

    vec4 totalColour = backgroundTextureColour+rTextureColour+gTextureColour+bTextureColour;

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for(int i=0;i<6;i++){
        float distance = length(toLightVector[i]);
        float attFactor = (attenuation[i].x) + (attenuation[i].y*distance) + (attenuation[i].z*distance*distance);

        vec3 unitLightVector = normalize(toLightVector[i]);
        float nDot1 = dot(unitNormal,unitLightVector);
        float brightness = max(nDot1,0.0);
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
        float specularFactor = dot(reflectedLightDirection,unitToCamera);
        specularFactor = max(specularFactor,0.0);
        float dampedFactor = pow(specularFactor,shineDamper);
        totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor ;
        totalSpecular = totalSpecular+ (dampedFactor * reflectivity * lightColour[i])/attFactor;
    }

    totalDiffuse = max(totalDiffuse,0.2);

    outColor = mix(vec4(skyColour,1.0),(vec4(totalDiffuse,1.0) * totalColour),visibility);
}