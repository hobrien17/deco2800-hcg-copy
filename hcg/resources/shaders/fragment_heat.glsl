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

float getHeatDistortion(float time) {
    return sin(v_texCoords.y * 50 + 2 * time) * 0.005;
}

void main() {
    // Set the colour of this pixel to its base colour value multiplied
    // by the colour found in the texture at the given texture coordinates
    vec2 tex_final = v_texCoords;

    if(u_effects == 1) {
        tex_final.x = tex_final.x + getHeatDistortion(u_time);
    }

    vec4 final = v_color * texture2D(u_texture, tex_final);

    gl_FragColor = final;
}