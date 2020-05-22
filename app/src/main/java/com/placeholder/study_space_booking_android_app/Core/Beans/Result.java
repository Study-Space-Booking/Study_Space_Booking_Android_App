package com.placeholder.study_space_booking_android_app.Core.Beans;

public class Result<T> {
    private Result() {
    }

    public static final class Accepted<T> extends Result<T> {
        private T model;

        public Accepted(T model) {
            this.model = model;
        }

        public T getModel() {
            return this.model;
        }
    }

    public static final class Handle extends Result {
        private Exception exception;

        public Handle(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}
