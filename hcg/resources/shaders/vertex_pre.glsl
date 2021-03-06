/* Inputs from spritebatch
/* These are the standard attributes used by SpriteBatch so we 
/* have to use them. */

// The world position of the vertex
attribute vec4 a_position;
// The colour of the vertex
attribute vec4 a_color;
// The texture coordinates for the vertex
attribute vec2 a_texCoord0;

/* Projection matrix
/* Takes the world position and translates it into a screen position. */
uniform mat4 u_projTrans;

#define PI 3.14159265359
/* Outputs into fragment shader
/* These are linearly interpolated between vertices to get values for pixels
/* that don't fall exactly on a vertex. */

// The colour of the vertex
varying vec4 v_color;
// The texture coordinates for the vertex
varying vec2 v_texCoords;

uniform vec4 u_globalColor;
uniform vec4 u_globalLight;

void main() {
    // Send colour information through the pipeline to frag shader
    v_color = /*u_globalColor * */u_globalLight * a_color;
    v_color.a = v_color.a * (255.0/254.0);
	// Send texture information through the pipeline to frag shader
	v_texCoords = a_texCoord0;

	// Set the screen posiiton of this vertex
	gl_Position =  u_projTrans * a_position;
}