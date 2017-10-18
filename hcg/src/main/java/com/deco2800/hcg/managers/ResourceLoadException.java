package com.deco2800.hcg.managers;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * An extension of UncheckedIOException, which can serve as a wrapper around other exceptions
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
     * Create a ResourceLoadException from some Exception
     * @param cause The GdxRuntimeException which triggered this exception
     */
    public ResourceLoadException(Exception cause) {
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
     * Create a ResourceLoadException from some Exception and a description of the failure.
     * @param message What went wrong
     * @param cause The GdxRuntimeException which triggered this exception
     */
    public ResourceLoadException(String message, Exception cause) {
        super(message, new IOException(cause));
    }

}
