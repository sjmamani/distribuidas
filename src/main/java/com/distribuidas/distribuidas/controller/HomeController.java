package com.distribuidas.distribuidas.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controlador.Controlador;
import daos.RubroDao;
import exceptions.RubroException;
import exceptions.SubRubroException;
import negocio.Rubro;
import view.ClienteView;
import view.ProductoView;
import view.RubroView;
import view.SubRubroView;

@Controller
public class HomeController {

	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
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

	@RequestMapping(value = "/productosBySubRubro/", method = RequestMethod.POST)
	public @ResponseBody String getProductosBySubRubro(SubRubroView subRubroView) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador controlador = Controlador.getInstancia();
		List<ProductoView> productos = controlador.getProductosBySubRubro(subRubroView);
		return mapper.writeValueAsString(productos);
	}

	@RequestMapping(value = "/productosByRubro/{codigo}", method = RequestMethod.GET)
	public @ResponseBody String getProductosByRubro(@PathVariable int codigo) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<ProductoView> productos = Controlador.getInstancia()
				.getProductosByRubro(new RubroView(codigo, null, true));
		return mapper.writeValueAsString(productos);
	}

//	@RequestMapping(value = "/altaProducto", method = RequestMethod.GET)
//	public @ResponseBody String altaProducto() {
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			Controlador.getInstancia().altaProducto(new ProductoView());
//			// List<ProductoView> productos = controlador.getProductosByRubro(rubroView);
//			return mapper.writeValueAsString(productos);
//		} catch (RubroException e) {
//			ObjectMapper mapper = new ObjectMapper();
//			return mapper.writeValueAsString("Hubo un error");
//		} catch (SubRubroException e) {
//			ObjectMapper mapper = new ObjectMapper();
//			return mapper.writeValueAsString("Hubo un error");
//		}
//	}
}
