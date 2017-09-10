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

uniform int u_effects;

const int POST_HEAT  = 1;
const int POST_BLOOM = 2;

float getHeatDistortion(float time) {
    return sin(v_texCoords.y * 50 + 2 * time) * 0.005;
}

void main() {
    // Set the colour of this pixel to its base colour value multiplied
    // by the colour found in the texture at the given texture coordinates
    vec2 tex_final = v_texCoords;

    // Apply heat distortion
    if((u_effects & POST_HEAT) == POST_HEAT) {
        tex_final.x = tex_final.x + getHeatDistortion(u_time);
    }

    vec4 final = v_color * texture2D(u_texture, tex_final);

    // Apply bloom
    if((u_effects & POST_BLOOM) == POST_BLOOM) {
        vec4 blur = vec4(0, 0, 0, 0);
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                vec2 offset = vec2(i, j) * 0.001;
                blur += texture2D(u_texture, tex_final + offset);
            }
        }

        final += (blur / 30.0);
    }

    gl_FragColor = final;
}