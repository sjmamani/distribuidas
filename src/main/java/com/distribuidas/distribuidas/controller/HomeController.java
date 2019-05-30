package com.distribuidas.distribuidas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controlador.Controlador;
import exceptions.CambioPasswordException;
import exceptions.ClienteException;
import exceptions.LoginException;
import exceptions.PedidoException;
import exceptions.ProductoException;
import exceptions.RubroException;
import exceptions.SubRubroException;
import exceptions.UsuarioException;
import view.ClienteView;
import view.ItemPedidoView;
import view.PedidoView;
import view.ProductoView;
import view.RubroView;
import view.SubRubroView;

/**
 * TODOS LOS TESTS FUERON REALIZADOS CON POSTMAN
 * 
 * @author Joaquin Mamani y Nicolas Marquez - Aplicaciones Distribuidas 1C 2019
 *
 */


@Controller
@CrossOrigin(value = "http://localhost:3000")
public class HomeController {
	
	/* Testeado */
	@PostMapping("/login")
	public @ResponseBody String login(@RequestBody Map<String, String> request)
			throws JsonProcessingException, LoginException, CambioPasswordException, UsuarioException {
		ObjectMapper mapper = new ObjectMapper();
		String nombre = request.get("nombre");
		String password = request.get("password");
		Controlador.getInstancia().login(nombre, password);
		return mapper.writeValueAsString("Usuario logueado");
	}
	
	/* Testeado */
	@PostMapping("/cambioPassword")
	public @ResponseBody String cambioPassword(@RequestBody Map<String, String> request)
			throws JsonProcessingException, LoginException, CambioPasswordException, UsuarioException {
		ObjectMapper mapper = new ObjectMapper();
		String username = request.get("username");
		String password = request.get("password");
		Controlador.getInstancia().cambioPassword(username, password);
		return mapper.writeValueAsString("Contraseña modificada");
	}

	@PostMapping("/producto")
	/* Error en el negocio, cod de barra tipo numeric en db y String en codigo */
	public @ResponseBody String altaProducto(@RequestBody ProductoView producto)
			throws RubroException, SubRubroException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador.getInstancia().altaProducto(producto);
		return mapper.writeValueAsString("Producto creado");
	}

	/* Testeado */
	@DeleteMapping("/producto")
	public @ResponseBody String bajaProducto(@RequestBody ProductoView producto) throws ProductoException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador.getInstancia().bajaProducto(producto);
		return mapper.writeValueAsString("Producto eliminado");
	}
	
	/* Testeado */
	@PutMapping("/producto")
	public @ResponseBody String modificaProducto(@RequestBody ProductoView producto)
			throws ProductoException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador.getInstancia().modificaProducto(producto);
		return mapper.writeValueAsString("Producto modificado");
	}
	
	/* Testeado */
	@PostMapping("/pedido/{cuit}")
	public @ResponseBody String crearPedido(@PathVariable String cuit)
			throws ClienteException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		PedidoView pedido = Controlador.getInstancia().crearPedido(cuit);
		return mapper.writeValueAsString(pedido);
	}
	
	/* Testeado */
	@PostMapping("/agregarProductoEnPedido/{numero}/{id}/{cantidad}")
	public @ResponseBody String agregarProductoEnPedido(@PathVariable int numero, @PathVariable int id, @PathVariable int cantidad)
			throws PedidoException, ProductoException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ItemPedidoView item = Controlador.getInstancia().agregarProductoEnPedido(numero, id, cantidad);
		return mapper.writeValueAsString(item);
	}
	
	/* Testeado */
	@DeleteMapping("/pedido/{numeroPedido}")
	public @ResponseBody String eliminarPedido(@PathVariable int numeroPedido) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador.getInstancia().eliminarPedido(numeroPedido);
		return mapper.writeValueAsString("Pedido eliminado");
	}
	
	/* Testeado */
	@PutMapping("/facturarPedido/{numero}")
	public @ResponseBody String facturarPedido(@PathVariable int numero)
			throws JsonProcessingException, PedidoException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador.getInstancia().facturarPedido(numero);
		return mapper.writeValueAsString("Pedido facturado");
	}
	
	/* Testeado */
	@GetMapping("/pedido/{numero}")
	public @ResponseBody String getPedidoById(@PathVariable int numero)
			throws JsonProcessingException, PedidoException {
		ObjectMapper mapper = new ObjectMapper();
		PedidoView pedido = Controlador.getInstancia().getPedidoById(numero);
		return mapper.writeValueAsString(pedido);
	}
	
	@GetMapping("/pedidos")
	public @ResponseBody String getPedidos() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<PedidoView> pedidos = Controlador.getInstancia().getPedidos();
		return mapper.writeValueAsString(pedidos);
	}
	
	/* Testeado */
	@GetMapping("/rubros")
	public @ResponseBody String getRubros() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<RubroView> rubros = Controlador.getInstancia().getRubros();
		return mapper.writeValueAsString(rubros);
	}
	
	/* Testeado */
	@GetMapping("/subRubros")
	public @ResponseBody String getSubRubros() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<SubRubroView> subRubros = Controlador.getInstancia().getSubRubros();
		return mapper.writeValueAsString(subRubros);
	}
	
	/* Testeado */
	@GetMapping("/productos")
	public @ResponseBody String getProductos() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<ProductoView> productos = Controlador.getInstancia().getProductos();
		return mapper.writeValueAsString(productos);
	}
	
	/* Testeado */
	@GetMapping("/productosByRubro/{codigo}")
	public @ResponseBody String getProductosByRubro(@PathVariable int codigo) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		RubroView rubro = new RubroView(codigo, "", true);
		List<ProductoView> pv = Controlador.getInstancia().getProductosByRubro(rubro);
		return mapper.writeValueAsString(pv);
	}
	
	/* Testeado */
	@GetMapping("/productosBySubRubro/{codigo}")
	public @ResponseBody String getProductosBySubRubro(@PathVariable int codigo) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		SubRubroView subrubro = new SubRubroView(codigo, "", null);
		List<ProductoView> productos = Controlador.getInstancia().getProductosBySubRubro(subrubro);
		return mapper.writeValueAsString(productos);
	}
	
//	@GetMapping("/productos/{id}")
//	public @ResponseBody String getProductoById(@PathVariable int id) {
//		ObjectMapper mapper = new ObjectMapper();
//		List<ProductoView> productos = Controlador.getInstancia().getProductosBySubRubro(subrubro);
//		return mapper.writeValueAsString(productos);
//	}
	
	/* Testeado */
	@GetMapping("/clientes")
	public @ResponseBody String getClientes() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<ClienteView> clientes = Controlador.getInstancia().getClientes();
		return mapper.writeValueAsString(clientes);
	}
}
