/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections;

/**
 * Exception thrown when a queue underflows.
 *
 * @author Ian Clement
 */
public class QueueUnderflowException extends RuntimeException {

    public QueueUnderflowException() {
        super();
    }

    public QueueUnderflowException(String message) {
        super(message);
    }
}
