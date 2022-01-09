package com.jdc.bookshop.model.repo.generator;

public class IdGenerator {

	public static interface Generator {
		int next();
	}

	public static interface CodeGenerator {
		String next(int infoId);
	}

	public static interface ResetableGenerator extends Generator {
		void reset();
	}

	public static CodeGenerator getBookCodeGenerator() {
		return new BookCodeGenerator();
	}

	public static Generator getGenerator() {
		return getResetaleGenerator();
	}

	public static ResetableGenerator getResetaleGenerator() {

		return new ResetableGenerator() {

			private int state;

			public void reset() {
				state = 0;
			}

			public int next() {
				return ++ state;
			}
		};
	}
}