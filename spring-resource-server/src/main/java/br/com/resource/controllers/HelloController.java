package br.com.resource.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {
	@RequestMapping("/people")
	@GetMapping
	public List<String> people() {
		List<String> names = Arrays.asList("John", "Petter", "Ane", "Josh");
		return names;
	}
}
