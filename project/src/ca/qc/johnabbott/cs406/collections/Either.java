/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import ca.qc.johnabbott.cs406.serialization.util.Date;
import ca.qc.johnabbott.cs406.serialization.util.Integer;

import java.io.IOException;

/**
 * Represents an value that can be one of two generic data types: either a "left" value of type S, or a "right" value of type T.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface Either<S, T> extends Serializable {

    enum Type { LEFT, RIGHT }

    /**
     * Determine if the object is a left or right Either.
     * @return
     */
    Type getType();

    /**
     * Get the left value of the either.
     * @return
     */
    S getLeft();

    /**
     * Get the right value of the either.
     * @return
     */
    T getRight();


    /**
     * Generate a left Either object.
     * @param val
     * @param <S>
     * @param <T>
     * @return
     */
    static <S extends Serializable,T > Either<S,T> left(S val) {
        return new LeftEither<S, T>(val);
    }

    static <S ,T extends Serializable> Either<S,T> right(T val) {
        return new RightEither<S, T>(val);
    }

    /**
     * Class to store left Either values.
     * TODO: should be private but Java won't let me. Update to anonymous inner class next semester ;)
     * @param <S>
     * @param <T>
     */
    class LeftEither<S extends Serializable,T > implements Either<S,T> {

        public static final byte SERIAL_ID = 0x25;
        private S val;

        public LeftEither(S val) {
            this.val = val;
        }

        public LeftEither() { }

        @Override
        public Type getType() {
            return Type.LEFT;
        }

        @Override
        public S getLeft() {
            return val;
        }

        @Override
        public T getRight() {
            throw new RuntimeException();
        }

        @Override
        public String toString() {
            return "LeftEither{" +
                    "val=" + val +
                    '}';
        }

        @Override
        public byte getSerialId() {
            return SERIAL_ID;
        }

        @Override
        public void serialize(Serializer serializer) throws IOException {
            serializer.write(this.val);
        }

        @Override
        public void deserialize(Serializer serializer) throws IOException, SerializationException {
            this.val = (S) serializer.readSerializable();
        }
    }

    /**
     * Class to store right Either values.
     * TODO: should be private but Java won't let me. Update to anonymous inner class next semester ;)
     * @param <S>
     * @param <T>
     */
    class RightEither<S ,T extends Serializable > implements Either<S,T> {

        public static final byte SERIAL_ID = 0x26;
        private T val;

        public RightEither(T val) {
            this.val = val;
        }

        public RightEither() {
        }

        @Override
        public Type getType() {
            return Type.RIGHT;
        }

        @Override
        public S getLeft() {
            throw new RuntimeException();
        }

        @Override
        public T getRight() {
            return val;
        }

        @Override
        public String toString() {
            return "RightEither{" +
                    "val=" + val;
        }

        @Override
        public byte getSerialId() {
            return SERIAL_ID;
        }

        @Override
        public void serialize(Serializer serializer) throws IOException {
            serializer.write(this.val);
        }

        @Override
        public void deserialize(Serializer serializer) throws IOException, SerializationException {
            this.val = (T) serializer.readSerializable();
        }
    }
}
