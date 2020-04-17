package com.myapps.minhasfinancas.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioResource {

	@GetMapping("/")
	public String halloWorld() {
		return "hello world";
	}
}
