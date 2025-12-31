package com.example.chicken.common.constant;

public class PathConstant {

    private static final String ERROR_MESSAGE = "Constant class";

    private PathConstant() {
        throw new IllegalStateException(ERROR_MESSAGE);
    }

    public static class Auth {

        public static final String AUTH_PREFIX = "/api/auth";
        public static final String SIGN_UP = "/sign-up";
        public static final String SIGN_IN = "/sign-in";
        public static final String REISSUE = "/reissue";
        public static final String LOGOUT = "/logout";

        private Auth() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

        public static class Password {

            private static final String PASSWORD_PREFIX = "/password";
            public static final String REQUEST_RESET = PASSWORD_PREFIX + "/request-reset";
            public static final String VERIFY = PASSWORD_PREFIX + "/reset/verify";
            public static final String RESET = PASSWORD_PREFIX + "/reset";

            private Password() {
                throw new IllegalStateException(ERROR_MESSAGE);
            }

        }
    }

    public static class User {

        public static final String USER_PREFIX = "/api/users";
        public static final String ME = "/me";
        public static final String MY_PRODUCTS = ME + "/products";

        private User() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

    }

    public static class Product {

        public static final String PRODUCT_PREFIX = "/api/products";
        public static final String PRODUCT_ID = "/{productId}";
        public static final String PRODUCT_CATEGORIES = "/categories";

        private Product() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }
    }

    public static class S3 {

        public static final String S3_PREFIX = "/api/s3";
        public static final String UPLOAD_URL = "/upload-url";

        private S3() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

    }

    public static class Order {

        public static final String ORDER_PREFIX = "/api/orders";
        public static final String ORDER_ID = "/{orderId}";
        public static final String SHIPPING_INFO = ORDER_ID + "/shipping-info";

        private Order() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }
    }

    public static class Payment {

        public static final String PAYMENT_PREFIX = "/api/payments";

        private Payment() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

    }

    public static class Announcement { // Notice 클래스 -> Announcement 클래스 변경
        public static final String ANNOUNCEMENT_PREFIX = "/api/announcements"; // NOTICE_PREFIX -> ANNOUNCEMENT_PREFIX 변경
        public static final String ANNOUNCEMENT_ID = "/{announcementId}"; // NOTICE_ID -> ANNOUNCEMENT_ID 변경

        private Announcement() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }
    }

    public static class Faq {
        public static final String FAQ_PREFIX = "/api/faq";
        public static final String FAQ_ID = "/{faqId}";

        private Faq() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }
    }

    public static class Qna {
        public static final String QNA_PREFIX = "/api/qna";
        public static final String QNA_ID = "/{qnaId}";

        private Qna() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }
    }

    public static class Admin {

        public static final String ADMIN_PREFIX = "/api/admin";
        public static final String ADMIN_AUTH_PREFIX = ADMIN_PREFIX + "/auth";
        public static final String ADMIN_USERS_PREFIX = ADMIN_PREFIX + "/users";
        public static final String ADMIN_PRODUCTS_PREFIX = ADMIN_PREFIX + "/products";
        public static final String ADMIN_ORDERS_PREFIX = ADMIN_PREFIX + "/orders";
        public static final String ADMIN_ANNOUNCEMENT_PREFIX = ADMIN_PREFIX + "/announcements";
        public static final String ADMIN_FAQ_PREFIX = ADMIN_PREFIX + "/faq";
        public static final String ADMIN_QNA_PREFIX = ADMIN_PREFIX + "/qna";
        public static final String ADMIN_CATEGORIES_PREFIX = ADMIN_PREFIX + "/categories";
        public static final String ADMIN_USERS_ID = "/{userId}";
        public static final String ADMIN_PRODUCTS_ID = "/{productId}";
        public static final String ADMIN_ANNOUNCEMENT_ID = "/{announcementId}";
        public static final String ADMIN_FAQ_ID = "/{faqId}";
        public static final String ADMIN_QNA_ID = "/{qnaId}";
        public static final String ADMIN_QNA_ANSWER = ADMIN_QNA_ID + "/answer";
        public static final String ADMIN_CATEGORIES_ID = "/{categoryId}";
        public static final String ADMIN_STATISTICS_PREFIX = ADMIN_PREFIX + "/statistics";
        public static final String ADMIN_STATISTICS_USERS_DAILY_REGISTRATIONS = "/users/daily-registrations";
        public static final String ADMIN_STATISTICS_PRODUCTS_DAILY_REGISTRATIONS = "/products/daily-registrations";
        public static final String ADMIN_STATISTICS_BIDS_DAILY_REGISTRATIONS = "/bids/daily-registrations";

        private Admin() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }

    }

}
