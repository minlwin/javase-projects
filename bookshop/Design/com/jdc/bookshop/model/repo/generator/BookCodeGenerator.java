package com.jdc.bookshop.model.repo.generator;

import java.util.*;

class BookCodeGenerator implements IdGenerator.CodeGenerator {

	private Map<Integer, IdGenerator.Generator> generators;

	BookCodeGenerator() {
		generators = new HashMap<>();
	}

	public String next(int infoId) {

		var generator = generators.get(infoId);

		if(null == generator) {
			generator = IdGenerator.getGenerator();
			generators.put(infoId, generator);
		}

		// 0000-00 [Boook Info Id]-[Sequence]
		return "%04d-%02d".formatted(infoId, generator.next());
	}

}