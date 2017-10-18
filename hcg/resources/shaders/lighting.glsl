varying vec4 v_color;
varying vec2 v_texCoord;

uniform vec4 u_globalLight;
uniform sampler2D u_texture;

void main() {
    gl_FragColor = v_color * (texture2D(u_texture, v_texCoord) + u_globalLight);
}