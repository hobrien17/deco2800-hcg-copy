//#version 130

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
uniform float u_health;
uniform float u_sick;
uniform float u_heat;
uniform float u_bloom;
uniform float u_contrast;

float getHeatDistortion(float time, vec2 texCoords) {
    return sin(texCoords.y * 50.0 + 2.0 * time) * 0.005 * u_heat;
}

vec4 getBloom(vec2 texCoords) {
    vec4 blur = vec4(0.0, 0.0, 0.0, 0.0);
    for(int i = -1; i <= 1; i++) {
        for(int j = -1; j <= 1; j++) {
            vec2 offset = vec2(i, j) * 0.001 * u_bloom;
            blur += texture2D(u_texture, texCoords + offset);
        }
    }

    return (blur / 30.0) * u_bloom;
}
vec4 getBlur(vec2 tc) {
    vec4 sum = vec4(0.0);



   	//apply blurring, using a 3x3 mean blur kernel

   	sum += texture2D(u_texture, vec2(tc.x - 0.001, tc.y - 0.001)) *  0.5/8.0;
   	sum += texture2D(u_texture, vec2(tc.x, tc.y - 0.001)) *  0.5/8.0;
   	sum += texture2D(u_texture, vec2(tc.x + 0.001, tc.y - 0.001)) *  0.5/8.0;

   	sum += texture2D(u_texture, vec2(tc.x - 0.001, tc.y)) * 0.5/8.0;
   	sum += texture2D(u_texture, vec2(tc.x, tc.y)) * 0.5;
   	sum += texture2D(u_texture, vec2(tc.x + 0.001, tc.y )) *  0.5/8.0;

   	sum += texture2D(u_texture, vec2(tc.x - 0.001, tc.y + 0.001)) *  0.5/8.0;
   	sum += texture2D(u_texture, vec2(tc.x, tc.y + 0.001)) *  0.5/8.0;
   	sum += texture2D(u_texture, vec2(tc.x + 0.001, tc.y + 0.001)) *  0.5/8.0;

   	//discard alpha
   	return vec4(sum.rgb, 1.0);
}

vec4 getVignette(vec2 tc) {


    //RADIUS of our vignette, where 0.5 results in a circle fitting the screen
    const float RADIUS = 0.7;

    //softness of our vignette, between 0.0 and 1.0
    const float SOFTNESS = 0.45;

    //sepia colour, adjust to taste
    const vec3 SEPIA = vec3(1.2, 1.0, 0.8);
    const vec3 RED = vec3(1.0, 0.0, 0.0);
    const vec3 SICK = vec3(0.1, 1.0, 0.0);



	//sample our texture
	vec4 texColor = texture2D(u_texture, tc);

	//1. VIGNETTE

	//determine center position
	vec2 position = v_texCoords - vec2(0.5);//(gl_FragCoord.xy / vec2(1920,1080)) - vec2(0.5);

	//determine the vector length of the center position
	float len = length(position);

	//use smoothstep to create a smooth vignette
	float vignette = smoothstep(RADIUS, RADIUS-SOFTNESS, len);

	//apply the vignette with 50% opacity
	texColor.rgb = mix(texColor.rgb, getBlur(tc).rgb * vignette, 1.0);
	texColor.rgb = mix(texColor.rgb, texColor.rgb * vignette, 0.5);
	texColor.rgb = mix(texColor.rgb , RED * vignette, u_health);
	texColor.rgb = mix(texColor.rgb, SICK * vignette, u_sick);

	//2. GRAYSCALE

	//convert to grayscale using NTSC conversion weights
	float gray = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));

	//3. SEPIA

	//create our sepia tone from some constant value
	vec3 sepiaColor = vec3(gray);

	//again we'll use mix so that the sepia effect is at 75%
	texColor.rgb = mix(texColor.rgb, sepiaColor, u_contrast*0.7);

	//final colour, multiplied by vertex colour
	return texColor;
}

void main() {
    // Set the colour of this pixel to its base colour value multiplied
    // by the colour found in the texture at the given texture coordinates
    vec2 tex_final = v_texCoords;
    
    // Apply heat distortion
    if(u_heat > 0.0) {
        tex_final.x = tex_final.x + getHeatDistortion(u_time, tex_final);
    }

    vec4 final = v_color * getVignette(tex_final);

    // Apply bloom
    if(u_bloom > 0.0) {
        final += getBloom(tex_final);
    }


    gl_FragColor = final;
}

