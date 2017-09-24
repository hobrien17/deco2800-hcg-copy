package com.deco2800.hcg.managers;

import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * An extension of UncheckedIOException, which can serve as a wrapper around both IOExceptions and GdxRuntimeExceptions
 * @author Richy McGregor
 */
public class ResourceLoadException extends UncheckedIOException {

    /**
     * Create a ResourceLoadException with only a text message of what failed
     * @param message A description of what failed
     */
    public ResourceLoadException(String message) {
        super(message, new IOException());
    }

    /**
     * Create a ResourceLoadException from an IOException
     * @param cause The IOException which triggered this exception
     */
    public ResourceLoadException(IOException cause) {
        super(cause);
    }

    /**
     * Create a ResourceLoadException from a GdxRuntimeException
     * @param cause The GdxRuntimeException which triggered this exception
     */
    public ResourceLoadException(GdxRuntimeException cause) {
        super(new IOException(cause));
    }

    /**
     * Create a ResourceLoadException from an IOException and a description of the failure.
     * @param message What went wrong
     * @param cause The IOException which triggered this exception
     */
    public ResourceLoadException(String message, IOException cause) {
        super(message, cause);
    }

    /**
     * Create a ResourceLoadException from a GdxRuntimeException and a description of the failure.
     * @param message What went wrong
     * @param cause The GdxRuntimeException which triggered this exception
     */
    public ResourceLoadException(String message, GdxRuntimeException cause) {
        super(message, new IOException(cause));
    }

}
