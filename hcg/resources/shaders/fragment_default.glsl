/* Inputs from vertex shader
/* These are linearly interpolated between vertices to get values for pixels
/* that don't fall exactly on a vertex. */

// The colour of the pixel
varying vec4 v_color;
// The texture coordinates for the pixel
varying vec2 v_texCoords;


// The texture to be sampled
uniform sampler2D u_texture;


void main() {
    // Set the colour of this pixel to its base colour value multiplied
    // by the colour found in the texture at the given texture coordinates
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}