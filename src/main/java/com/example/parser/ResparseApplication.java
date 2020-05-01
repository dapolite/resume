package com.example.parser;

import com.example.parser.model.Resume;
import com.example.parser.parser.impl.ResumeParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;


import static java.nio.charset.StandardCharsets.UTF_8;

@SpringBootApplication
public class ResparseApplication {

	public static void demo() {
		try {
			ResumeParser resumeParser = new ResumeParser();
			String resume = resumeParser.parse(new File("sample_cv_en.pdf"));
			System.out.println(resume);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

		SpringApplication.run(ResparseApplication.class, args);

	}
}


