package com.distribuidas.distribuidas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import view.ProductoView;
import view.RubroView;
import view.SubRubroView;

@Controller
public class HomeController {
	
	@GetMapping("/")
	@ResponseBody
	public String hola() {
		return "Hola mundo";
	}
	
	@PostMapping("/login")	
	@ResponseBody
	/*Testeado*/
	public String login (@RequestBody Map<String,String> request) throws JsonProcessingException, LoginException, CambioPasswordException, UsuarioException {
		ObjectMapper mapper=new ObjectMapper();
		String username = request.get("username");
		String password = request.get("password");
		Controlador.getInstancia().login(username, password);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
		
	}
	

	@PostMapping("/cambioPassword")	
	@ResponseBody
	/*Testeado*/
	public String cambioPassword (@RequestBody Map<String,String> request ) throws JsonProcessingException, LoginException, CambioPasswordException, UsuarioException {
		ObjectMapper mapper=new ObjectMapper();	
		String username = request.get("username");
		String password = request.get("password");
		Controlador.getInstancia().cambioPassword(username, password);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
		
	}

	@PostMapping("/altaProducto")	
	@ResponseBody
	/*Testeado*/
	public String altaProducto( /*Error en el negocio, cod de barra tipo numeric en db y String en codigo */
			@RequestBody ProductoView pv
			
	) throws RubroException, SubRubroException, JsonProcessingException {
		
		ObjectMapper mapper=new ObjectMapper();
		
		Controlador.getInstancia().altaProducto(pv);
		
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}

	@PostMapping("/bajaProducto")	
	@ResponseBody
	/*Testeado*/
	public String bajaProducto(@RequestBody ProductoView pv) throws ProductoException, JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		Controlador.getInstancia().bajaProducto(pv);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}

	@PostMapping("/modificaProducto")	
	@ResponseBody
	/*Testeado*/ 
	public String modificaProducto(//el modificaProducto que no modifica en el negocio
			@RequestBody ProductoView pv
			
	) throws ProductoException, JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		Controlador.getInstancia().modificaProducto(pv);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}
	
	@PostMapping("/crearPedido") 
	/*Testeado*/
	public @ResponseBody String crearPedido(@RequestBody Map<String,String> request) throws ClienteException, JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		String cuit = request.get("cuit");
		//request.forEach((k,v) -> System.out.println("Key: " + k + ": Value: " + v));
		int pnro = Controlador.getInstancia().crearPedido(cuit);
		return mapper.writeValueAsString("Numero de pedido: " + pnro);
		
	}
	
	@PostMapping("/agregarProductoEnPedido")
	/*Testeado*/
	public @ResponseBody String agregarProductoEnPedido(
			@RequestBody  Map<String,Integer> request
	) throws PedidoException, ProductoException, JsonProcessingException{	
		
		ObjectMapper mapper=new ObjectMapper();
		int numeroPedido = request.get("numeropedido");
		int identificadorProducto = request.get("identificador");
		int cantidad = request.get("cantidad");
		Controlador.getInstancia().agregarProductoEnPedido(numeroPedido, identificadorProducto, cantidad);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}
	
	@PostMapping("/eliminarPedido")
	@ResponseBody
	/*Testeado*/
	public String eliminarPedido(@RequestBody  Map<String,Integer> request ) throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		int numeroPedido = request.get("numeropedido");
		Controlador.getInstancia().eliminarPedido(numeroPedido);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}
	
	@PostMapping("/facturarPedido")
	@ResponseBody
	/*Testeado*/
	public String facturarPedido(@RequestBody Map<String,Integer> request) throws JsonProcessingException, PedidoException{
		ObjectMapper mapper=new ObjectMapper();
		int numero = request.get("numeropedido");
		Controlador.getInstancia().facturarPedido(numero);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}
	
	@GetMapping("/getPedidoById")
	/*Testeado*/
	public @ResponseBody String getPedidoById(@RequestParam(name="numeropedido", required = true) int numero) throws JsonProcessingException, PedidoException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getPedidoById(numero));
	}

	@GetMapping("/getRubros")
	/*Testeado*/
	public @ResponseBody String getRubros() throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getRubros());
	}
	
	@GetMapping("/getSubRubros")
	/*Testeado*/
	public @ResponseBody String getSubRubros() throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getSubRubros());
	}
	
	@GetMapping("/getProductos")
	/*Testeado*/
	public @ResponseBody String getProductos() throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getProductos());
	}
	
	@GetMapping("/getProductosByRubro")
	/*Testeado*/
	public @ResponseBody String getProductosByRubro(@RequestParam(name="codigo", required = true) int codigo) throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		RubroView rubro = new RubroView(codigo, "", true);
		List<ProductoView> pv = Controlador.getInstancia().getProductosByRubro(rubro);
			
		return mapper.writeValueAsString(pv);
	}
	
	@GetMapping("/getProductosBySubRubro")
	/*Testeado*/
	public @ResponseBody String getProductosBySubRubro(@RequestParam(name="codigo", required = true) int codigo) throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		SubRubroView subrubro = new SubRubroView(codigo, "", null);	
		return mapper.writeValueAsString(Controlador.getInstancia().getProductosBySubRubro(subrubro));
		
		 
	}
	
	@RequestMapping(value="/clientes", method=RequestMethod.GET)
	/*Testeado*/
	public @ResponseBody String getClientes() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador controlador = Controlador.getInstancia();
		List<ClienteView> clientes = controlador.getClientes();
		/*if (clientes == null) {
			return "Not results for /clientes";
		} else {
			return mapper.writeValueAsString(clientes);			
		}*/
		return mapper.writeValueAsString(clientes); //no importa si esta vacio o no, del msje a mostrar se encarga el front
	}
}
