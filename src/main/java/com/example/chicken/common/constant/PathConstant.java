package com.example.chicken.common.constant;

public class PathConstant {

    private static final String ERROR_MESSAGE = "Constant class";

    private PathConstant() {
        throw new IllegalStateException(ERROR_MESSAGE);
    }

    public static class Auth {

        private Auth() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

        public static final String AUTH_PREFIX = "/api/auth";

        public static final String SIGN_UP = "/sign-up";
        public static final String SIGN_IN = "/sign-in";
        public static final String REISSUE = "/reissue";
        public static final String LOGOUT = "/logout";

        public static class Password {

            private Password() {
                throw new IllegalStateException(ERROR_MESSAGE);
            }

            private static final String PASSWORD_PREFIX = "/password";
            public static final String REQUEST_RESET = PASSWORD_PREFIX + "/request-reset";
            public static final String VERIFY = PASSWORD_PREFIX + "/reset/verify";
            public static final String RESET = PASSWORD_PREFIX + "/reset";

        }
    }

    public static class User {

        private User() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

        public static final String USER_PREFIX = "/api/users";

        public static final String ME = "/me";
        public static final String MY_PRODUCTS = ME + "/products";

    }

    public static class Product {

        private Product() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

        public static final String PRODUCT_PREFIX = "/api/products";

        public static final String PRODUCT_ID = "/{productId}";

        public static final String PRODUCT_CATEGORIES = "/categories";
    }

    public static class S3 {

        private S3() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

        public static final String S3_PREFIX = "/api/s3";

        public static final String UPLOAD_URL = "/upload-url";

    }

    public static class Order {

        private Order() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

        public static final String ORDER_PREFIX = "/api/orders";

        public static final String ORDER_ID = "/{orderId}";
        public static final String SHIPPING_INFO = ORDER_ID + "/shipping-info";
    }

    public static class Payment {

        private Payment() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

        public static final String PAYMENT_PREFIX = "/api/payments";

    }

    public static class Admin {

        private Admin() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

        public static final String ADMIN_PREFIX = "/api/admin";

        public static final String ADMIN_USERS_PREFIX = ADMIN_PREFIX + "/users";
        public static final String ADMIN_PRODUCTS_PREFIX = ADMIN_PREFIX + "/products";
        public static final String ADMIN_CATEGORIES_PREFIX = ADMIN_PREFIX + "/categories";

        public static final String ADMIN_USERS_ID = "/{userId}";
        public static final String ADMIN_PRODUCTS_ID = "/{productId}";
        public static final String ADMIN_CATEGORIES_ID = "/{categoryId}";

        public static final String ADMIN_STATISTICS_PREFIX = ADMIN_PREFIX + "/statistics";
        public static final String ADMIN_STATISTICS_USERS_DAILY_REGISTRATIONS = "/users/daily-registrations";
    }

}
