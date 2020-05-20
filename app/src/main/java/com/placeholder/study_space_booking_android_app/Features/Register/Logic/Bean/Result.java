package com.placeholder.study_space_booking_android_app.Features.Register.Logic.Bean;

import com.placeholder.study_space_booking_android_app.Core.Beans.UserBean;

public class Result {
    public final static class Handle extends Result {
        private Exception exception;

        public Handle(Exception exception) {
            this.exception = exception;
        }

        public Exception getMessage() {
            return this.exception;
        }
    }

    public final static class Accepted extends Result {
        public Accepted() {
        }
    }
}
