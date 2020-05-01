package com.example.parser.parser.section.impl;

import com.example.parser.helpers.Helper;
import com.example.parser.model.Resume;
import com.example.parser.model.Section;
import com.example.parser.parser.SectionParser;


public final class InterestsParser extends SectionParser {

	@Override
	public void parse(Section section, Resume resume) {
		for (String line : section.getContent()) {
			resume.getInterests().add(Helper.removeNonCharacterBegin(line));
		}
	}

}
