package com.example.chicken.common.constant;

public class PathConstant {

	private PathConstant() {
		throw new IllegalStateException("Constant class");
	}

	public static class Auth {

		private Auth() {
			throw new IllegalStateException("Constant class");
		}

		public static final String AUTH_PREFIX = "/api/auth";

		public static final String SIGN_UP = "/sign-up";
		public static final String SIGN_IN = "/sign-in";
		public static final String REISSUE = "/reissue";
		public static final String LOGOUT = "/logout";

		public static class Password {

			private Password() {
				throw new IllegalStateException("Constant class");
			}

			private static final String PASSWORD_PREFIX = "/password";
			public static final String REQUEST_RESET = PASSWORD_PREFIX + "/request-reset";
			public static final String VERIFY = PASSWORD_PREFIX + "/reset/verify";
			public static final String RESET = PASSWORD_PREFIX + "/reset";

		}
	}

}
