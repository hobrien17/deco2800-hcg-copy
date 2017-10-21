package com.deco2800.hcg.shading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.NumberUtils;

public class LightBatch implements Batch {
    public static final String POSITION_ATTRIBUTE = "a_position";
    public static final String COLOR_ATTRIBUTE = "a_color";
    public static final String TEXCOORD_ATTRIBUTE = "a_texCoord";
    public static final String LIGHT_COLOR_ATTRIBUTE = "a_lightColor";
    public static final String LIGHT_INTENSITY_ATTRIBUTE = "a_lightIntensity";
    
    private static final int VERTEX_SIZE = 4;
    private static final int SPRITE_SIZE = 4 * VERTEX_SIZE;
    
    private Mesh mesh;
    
    final float[] vertices;
    int idx = 0;
    Texture lastTexture = null;
    float invTexWidth = 0;
    float invTexHeight = 0;
    
    boolean drawing = false;
    
    private final Matrix4 transformMatrix = new Matrix4();
    private final Matrix4 projectionMatrix = new Matrix4();
    private final Matrix4 combinedMatrix = new Matrix4();
    
    private boolean blendingDisabled = false;
    private int blendSrcFunc = GL20.GL_SRC_ALPHA;
    private int blendDstFunc = GL20.GL_ONE_MINUS_SRC_ALPHA;
    
    private ShaderProgram shader;
    
    float color = Color.WHITE.toFloatBits();
    private Color tempColor = new Color(1, 1, 1, 1);
    
    public int renderCalls = 0;
    public int totalRenderCalls = 0;
    
    public int maxSpritesInBatch = 0;
    
    public LightBatch(int size, ShaderProgram shader) {
        
        // 32767 is max vertex index, so 32767 / 4 vertices per sprite = 8191 sprites
        // max.
        if(size > 8191) {
            throw new IllegalArgumentException("Can't have more than 8191 sprites per batch: " + size);
        }
        
        VertexDataType vertexDataType = Gdx.gl30 != null ? VertexDataType.VertexBufferObjectWithVAO
                : VertexDataType.VertexArray;
        
        this.mesh = new Mesh(vertexDataType, false, size * 4, size * 6,
                new VertexAttribute(Usage.Position, 2, POSITION_ATTRIBUTE),
                new VertexAttribute(Usage.ColorPacked, 4, COLOR_ATTRIBUTE),
                new VertexAttribute(Usage.TextureCoordinates, 2, TEXCOORD_ATTRIBUTE + "0"),
                new VertexAttribute(Usage.ColorPacked, 4, LIGHT_COLOR_ATTRIBUTE),
                new VertexAttribute(Usage.Generic, 1, LIGHT_INTENSITY_ATTRIBUTE));
        
        this.projectionMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        this.vertices = new float[size * SPRITE_SIZE];
        
        int len = size * 6;
        short[] indices = new short[len];
        short j = 0;
        for(int i = 0; i < len; i += 6, j += 4) {
            indices[i] = j;
            indices[i + 1] = (short) (j + 1);
            indices[i + 2] = (short) (j + 2);
            indices[i + 3] = (short) (j + 2);
            indices[i + 4] = (short) (j + 3);
            indices[i + 5] = j;
        }
        this.mesh.setIndices(indices);
        
        this.shader = shader;
    }
    
    @Override
    public void dispose() {
        this.mesh.dispose();
    }
    
    @Override
    public void begin() {
        if(this.isDrawing()) {
            throw new IllegalStateException("LightBatch cannot begin drawing while it is already drawing.");
        } else {
            this.renderCalls = 0;
            
            Gdx.gl.glDepthMask(false);
            this.shader.begin();
            // TODO SET UP MATRICES
            this.drawing = true;
        }
        
    }
    
    @Override
    public void end() {
        if(!this.isDrawing()) {
            throw new IllegalStateException("Lightbatch cannot finish drawing if it is not already drawing");
        } else {
            if(this.idx > 0) {
                this.flush();
            }
            
            this.lastTexture = null;
            this.drawing = false;
            
            Gdx.gl.glDepthMask(true);
            if(this.isBlendingEnabled()) {
                Gdx.gl.glDisable(GL20.GL_BLEND);
            }
            this.shader.end();
        }
        
    }
    
    @Override
    public void setColor(Color tint) {
        this.color = tint.toFloatBits();
        
    }
    
    @Override
    public void setColor(float r, float g, float b, float a) {
        this.setColor(new Color(r, g, b, a));
        
    }
    
    @Override
    public void setColor(float color) {
        this.color = color;
        
    }
    
    @Override
    public Color getColor() {
        int colourBits = NumberUtils.floatToIntColor(this.color);
        return new Color(colourBits);
    }
    
    @Override
    public float getPackedColor() {
        return this.color;
    }
    
    @Override
    public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height,
            float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX,
            boolean flipY) {
        if(!this.isDrawing()) {
            throw new IllegalStateException("LightBatch must begin before drawing.");
		}
		float[] vertices = this.vertices;

		if (texture != this.lastTexture) {
			// TODO USE NEW TEXTURE
		} else if (this.idx == vertices.length) {
			this.flush();
		}

		final float worldOriginX = x + originX;
		final float worldOriginY = y + originY;
		float fx = -originX;
		float fy = -originY;
		float fx2 = width - originX;
		float fy2 = height - originY;

		// scale
		if ((int) scaleX != 1 || (int) scaleY != 1) {
			fx *= scaleX;
			fy *= scaleY;
			fx2 *= scaleX;
			fy2 *= scaleY;
		}

		// construct corner points, start from top left and go counter clockwise
		final float p1x = fx;
		final float p1y = fy;
		final float p2x = fx;
		final float p2y = fy2;
		final float p3x = fx2;
		final float p3y = fy2;
		final float p4x = fx2;
		final float p4y = fy;

		float x1;
		float y1;
		float x2;
		float y2;
		float x3;
		float y3;
		float x4;
		float y4;

		// rotate
		if ((int) rotation != 0) {
			final float cos = MathUtils.cosDeg(rotation);
			final float sin = MathUtils.sinDeg(rotation);

			x1 = cos * p1x - sin * p1y;
			y1 = sin * p1x + cos * p1y;

			x2 = cos * p2x - sin * p2y;
			y2 = sin * p2x + cos * p2y;

			x3 = cos * p3x - sin * p3y;
			y3 = sin * p3x + cos * p3y;

			x4 = x1 + (x3 - x2);
			y4 = y3 - (y2 - y1);
		} else {
			x1 = p1x;
			y1 = p1y;

			x2 = p2x;
			y2 = p2y;

			x3 = p3x;
			y3 = p3y;

			x4 = p4x;
			y4 = p4y;
		}

		x1 += worldOriginX;
		y1 += worldOriginY;
		x2 += worldOriginX;
		y2 += worldOriginY;
		x3 += worldOriginX;
		y3 += worldOriginY;
		x4 += worldOriginX;
		y4 += worldOriginY;

		float u = srcX * this.invTexWidth;
		float v = (srcY + srcHeight) * this.invTexHeight;
		float u2 = (srcX + srcWidth) * this.invTexWidth;
		float v2 = srcY * this.invTexHeight;

		if (flipX) {
			float tmp = u;
			u = u2;
			u2 = tmp;
		}

		if (flipY) {
			float tmp = v;
			v = v2;
			v2 = tmp;
		}

		float color = this.color;
		int idx = this.idx;
		vertices[idx] = x1;
		vertices[idx + 1] = y1;
		vertices[idx + 2] = color;
		vertices[idx + 3] = u;
		vertices[idx + 4] = v;

		vertices[idx + 5] = x2;
		vertices[idx + 6] = y2;
		vertices[idx + 7] = color;
		vertices[idx + 8] = u;
		vertices[idx + 9] = v2;

		vertices[idx + 10] = x3;
		vertices[idx + 11] = y3;
		vertices[idx + 12] = color;
		vertices[idx + 13] = u2;
		vertices[idx + 14] = v2;

		vertices[idx + 15] = x4;
		vertices[idx + 16] = y4;
		vertices[idx + 17] = color;
		vertices[idx + 18] = u2;
		vertices[idx + 19] = v;
		this.idx = idx + 20;
        }
    
    @Override
    public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth,
            int srcHeight, boolean flipX, boolean flipY) {
        this.draw(texture, x, y, 0, 0, width, height, 1, 1, 0, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
    }
    
    @Override
    public void draw(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
        this.draw(texture, x, y, 0, 0, texture.getWidth(), texture.getHeight(), 1, 1, 0, srcX, srcY, srcWidth,
                srcHeight, false, false);
    }
    
    @Override
    public void draw(Texture texture, float x, float y, float width, float height, float u, float v, float u2,
            float v2) {
        this.draw(texture, x, y, 0, 0, width, height, 1, 1, 0, (int) (width * u), (int) (height * v),
                (int) (width * u2), (int) (height * v2), false, false);
    }
    
    @Override
    public void draw(Texture texture, float x, float y) {
        int width = texture.getWidth();
        int height = texture.getHeight();
        this.draw(texture, x, y, 0, 0, width, height, 1, 1, 0, 0, 0, width, height, false, false);
    }
    
    @Override
    public void draw(Texture texture, float x, float y, float width, float height) {
        this.draw(texture, x, y, width, height, 0, 0, 1, 1);
    }
    
    @Override
    public void draw(Texture texture, float[] spriteVertices, int offset, int count) {
        if(!this.isDrawing()) {
            throw new IllegalStateException("LightBatch must begin before drawing.");
        } else {
            int verticesLength = this.vertices.length;
            int remainingVertices = verticesLength;
            if (texture != this.lastTexture) {
                //TODO SWITCH TEXTURE
            } else {
                remainingVertices -= this.idx;
                if (remainingVertices == 0) {
                    flush();
                    remainingVertices = verticesLength;
                }
            }
            int copyCount = Math.min(remainingVertices, count);
    
            System.arraycopy(spriteVertices, offset, vertices, idx, copyCount);
            idx += copyCount;
            count -= copyCount;
            while (count > 0) {
                offset += copyCount;
                flush();
                copyCount = Math.min(verticesLength, count);
                System.arraycopy(spriteVertices, offset, vertices, 0, copyCount);
                idx += copyCount;
                count -= copyCount;
            }    
        }
    }
    
    @Override
    public void draw(TextureRegion region, float x, float y) {
        this.draw(region.getTexture(), x, y, region.getRegionWidth(), region.getRegionHeight());
    }
    
    @Override
    public void draw(TextureRegion region, float x, float y, float width, float height) {
        // These are never actually used so I'll only bother implementing them if they're needed
        
    }
    
    @Override
    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height,
            float scaleX, float scaleY, float rotation) {
        // These are never actually used so I'll only bother implementing them if they're needed
        
    }
    
    @Override
    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height,
            float scaleX, float scaleY, float rotation, boolean clockwise) {
        // These are never actually used so I'll only bother implementing them if they're needed
        
    }
    
    @Override
    public void draw(TextureRegion region, float width, float height, Affine2 transform) {
        // These are never actually used so I'll only bother implementing them if they're needed
        
    }
    
    @Override
    public void flush() {
        if(this.idx == 0) {
            return;
        }
        
        this.renderCalls++;
        this.totalRenderCalls++;
        int spritesInBatch = this.idx / 20;
        if(spritesInBatch > this.maxSpritesInBatch) {
            this.maxSpritesInBatch = spritesInBatch;
        }
        int count = spritesInBatch * 6;
        
        Gdx.gl.glActiveTexture(0);
        this.lastTexture.bind(0);
        Mesh mesh = this.mesh;
        mesh.setVertices(this.vertices, 0, this.idx);
        mesh.getIndicesBuffer().position(0);
        mesh.getIndicesBuffer().limit(count);
        
        if(!this.isBlendingEnabled()) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } else {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            if(this.blendSrcFunc != -1) {
                Gdx.gl.glBlendFunc(blendSrcFunc, blendDstFunc);
            }
        }
        
        mesh.render(this.shader, GL20.GL_TRIANGLES, 0, count);
        
        this.idx = 0;
    }
    
    @Override
    public void disableBlending() {
        if(!this.isBlendingEnabled()) {
            return;
        } else {
            this.flush();
            this.blendingDisabled = true;
        }
        
    }
    
    @Override
    public void enableBlending() {
        if(this.isBlendingEnabled()) {
            return;
        } else {
            this.flush();
            this.blendingDisabled = false;
        }
        
    }
    
    @Override
    public void setBlendFunction(int srcFunc, int dstFunc) {
        if(this.blendSrcFunc == srcFunc && this.blendDstFunc == dstFunc) {
            return;
        } else {
            this.flush();
            this.blendSrcFunc = srcFunc;
            this.blendDstFunc = dstFunc;
        }
    }
    
    @Override
    public int getBlendSrcFunc() {
        return this.blendSrcFunc;
    }
    
    @Override
    public int getBlendDstFunc() {
        return this.blendDstFunc;
    }
    
    @Override
    public Matrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }
    
    @Override
    public Matrix4 getTransformMatrix() {
        return this.transformMatrix;
    }
    
    @Override
    public void setProjectionMatrix(Matrix4 projection) {
        if(this.isDrawing()) {
            this.flush();
        }
        
        this.projectionMatrix.set(projection);
        
        if(this.isDrawing()) {
            //TODO Set up Matrices
        }
    }
    
    @Override
    public void setTransformMatrix(Matrix4 transform) {
        if(this.isDrawing()) {
            this.flush();
        }
        
        this.transformMatrix.set(transform);
        
        if(this.isDrawing()) {
            //TODO Set up Matrices
        }
    }
    
    @Override
    public void setShader(ShaderProgram shader) {
        if(this.isDrawing()) {
            flush();
            this.shader.end();
        }
        
        this.shader = shader;
        
        if(drawing) {
            this.shader.begin();
            //TODO Set up Matrices
        }
    }
    
    @Override
    public ShaderProgram getShader() {
        return this.shader;
    }
    
    @Override
    public boolean isBlendingEnabled() {
        return !this.blendingDisabled;
    }
    
    @Override
    public boolean isDrawing() {
        return this.drawing;
    }
    
}
