varying vec4 v_color;
varying vec2 v_texCoord;

uniform sampler2D u_texture;
uniform sampler2D u_lightmap;

uniform vec2 resolution; // screen res

void main() {
    vec2 lightCoord = (gl_FragCoord.xy / resolution.xy);
    vec4 Light = texture2D(u_lightmap, lighCoord);

    gl_FragColor = v_color * Light;
}