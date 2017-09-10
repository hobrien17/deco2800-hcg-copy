#version 130

/* Inputs from vertex shader
/* These are linearly interpolated between vertices to get values for pixels
/* that don't fall exactly on a vertex. */

// The colour of the pixel
varying vec4 v_color;
// The texture coordinates for the pixel
varying vec2 v_texCoords;


// The texture to be sampled
uniform sampler2D u_texture;

uniform float u_time;

uniform float u_heat;
uniform float u_bloom;

float getHeatDistortion(float time, vec2 texCoords) {
    return sin(texCoords.y * 50 + 2 * time) * 0.005 * u_heat;
}

vec4 getBloom(vec2 texCoords) {
    vec4 blur = vec4(0, 0, 0, 0);
    for(int i = -1; i <= 1; i++) {
        for(int j = -1; j <= 1; j++) {
            vec2 offset = vec2(i, j) * 0.001 * u_bloom;
            blur += texture2D(u_texture, texCoords + offset);
        }
    }

    return (blur / 30.0) * u_bloom;
}

void main() {
    // Set the colour of this pixel to its base colour value multiplied
    // by the colour found in the texture at the given texture coordinates
    vec2 tex_final = v_texCoords;
    
    // Apply heat distortion
    if(u_heat > 0) {
        tex_final.x = tex_final.x + getHeatDistortion(u_time, tex_final);
    }

    vec4 final = v_color * texture2D(u_texture, tex_final);

    // Apply bloom
    if(u_bloom > 0) {
        final += getBloom(tex_final);
    }

    gl_FragColor = final;
}