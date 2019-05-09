package com.distribuidas.distribuidas.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controlador.Controlador;
import view.ClienteView;

@Controller
public class HomeController {
	
	@RequestMapping(value="/clientes", method=RequestMethod.GET)
	public @ResponseBody String getClientes() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador controlador = Controlador.getInstancia();
		List<ClienteView> clientes = controlador.getClientes();
		if (clientes == null) {
			return "Not results for /clientes";
		} else {
			return mapper.writeValueAsString(clientes);			
		}
	}
}
