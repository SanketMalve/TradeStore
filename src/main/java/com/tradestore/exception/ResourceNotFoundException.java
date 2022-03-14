package com.tradestore.exception;

/**
 * exception to 
 * 
 * @author SanketMalve
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String id;

    public ResourceNotFoundException(final String id) {
        super("Invalid Trade: " + id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
