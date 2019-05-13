package com.distribuidas.distribuidas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@CrossOrigin(value="http://localhost:3000") // Necesario para realizar los requests desde React
public class HomeController {
	
	/* CORREGIR
	 * Los datos del LOGIN deben pasarse por el body, jamás por la URL
	 */
	@PostMapping("/login")
	@ResponseBody
	/* Testeado */
	public String login(@RequestParam(name = "user", required = true) String user,
			@RequestParam(name = "password", required = true) String password)
			throws JsonProcessingException, LoginException, CambioPasswordException, UsuarioException {
		ObjectMapper mapper = new ObjectMapper();

		Controlador.getInstancia().login(user, password);
		return mapper.writeValueAsString(HttpStatus.OK.toString());

	}

	@PostMapping("/cambioPassword")
	@ResponseBody
	/* Testeado */
	public String cambioPassword(@RequestParam(name = "user", required = true) String user,
			@RequestParam(name = "password", required = true) String password)
			throws JsonProcessingException, LoginException, CambioPasswordException, UsuarioException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador.getInstancia().cambioPassword(user, password);
		return mapper.writeValueAsString(HttpStatus.OK.toString());

	}

	@PostMapping("/altaProducto")
	@ResponseBody
	/* Testeado */
	public String altaProducto( /* Error en el negocio, cod de barra tipo numeric en db y String en codigo */
			@RequestParam(name = "codigoRubro", required = true) int codigoRubro,
			@RequestParam(name = "codigoSubRubro", required = true) int codigoSubRubro,
			@RequestParam(name = "nombre", required = true) String pnombre,
			@RequestParam(name = "marca", required = true) String pmarca,
			@RequestParam(name = "codigoDeBarras", required = true) String pcodigo,
			@RequestParam(name = "precio", required = true) float pprecio

	) throws RubroException, SubRubroException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		RubroView rv = new RubroView(codigoRubro, pcodigo, false);
		SubRubroView srv = new SubRubroView(codigoSubRubro, "", rv);
		ProductoView recibido = new ProductoView(srv, rv, pnombre, pmarca, pcodigo, pprecio);

		Controlador.getInstancia().altaProducto(recibido);

		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}

	@PostMapping("/bajaProducto")	
	@ResponseBody
	/*Testeado*/
	public String bajaProducto(@RequestParam(name="identificador", required = true) int identificador) throws ProductoException, JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		ProductoView pv = new ProductoView (null, null, null, null, null, 0);
		pv.setIdentificador(identificador); //lo tuve que agregar
		Controlador.getInstancia().bajaProducto(pv);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}

	@PostMapping("/modificaProducto")	
	@ResponseBody
	/*Testeado*/ 
	public String modificaProducto(//el modificaProducto que no modifica en el negocio
			@RequestParam(name="identificador", required = true) int identificador,
			@RequestParam(name="codigoRubro", required = true) int codigoRubro, 
			@RequestParam(name="codigoSubRubro", required = true) int codigoSubRubro,
			@RequestParam(name="nombre", required = true) String pnombre,
			@RequestParam(name="marca", required = true) String pmarca,
			@RequestParam(name="codigoDeBarras", required = true) String pcodigo,
			@RequestParam(name="precio", required = true) float pprecio
			
	) throws ProductoException, JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		RubroView rv = new RubroView (codigoRubro, pcodigo, true);
		SubRubroView srv = new SubRubroView (codigoSubRubro, "", rv);
		ProductoView pv = new ProductoView (srv, rv, pnombre, pmarca, pcodigo, pprecio);
		pv.setIdentificador(identificador); //lo tuve que agregar
		Controlador.getInstancia().modificaProducto(pv);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}

	@PostMapping("/crearPedido")
	/* Testeado */
	public @ResponseBody String crearPedido(@RequestParam(name = "cuit", required = true) String cuit)
			throws ClienteException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		int pnro = Controlador.getInstancia().crearPedido(cuit);
		return mapper.writeValueAsString("Numero de pedido: " + pnro);
	}

	@PostMapping("/agregarProductoEnPedido")
	/* Testeado */
	public @ResponseBody String agregarProductoEnPedido(
			@RequestParam(name = "numeroPedido", required = true) int numeroPedido,
			@RequestParam(name = "identificadorProd", required = true) int identificadorProducto,
			@RequestParam(name = "cantidad", required = true) int cantidad)
			throws PedidoException, ProductoException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		Controlador.getInstancia().agregarProductoEnPedido(numeroPedido, identificadorProducto, cantidad);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}

	@PostMapping("/eliminarPedido")
	@ResponseBody
	/* Testeado */
	public String eliminarPedido(@RequestParam(name = "numeroPedido", required = true) int numeroPedido)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador.getInstancia().eliminarPedido(numeroPedido);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}

	@PostMapping("/facturarPedido")
	@ResponseBody
	/* Testeado */
	public String facturarPedido(@RequestParam(name = "nroPedido", required = true) int numero)
			throws JsonProcessingException, PedidoException {
		ObjectMapper mapper = new ObjectMapper();
		Controlador.getInstancia().facturarPedido(numero);
		return mapper.writeValueAsString(HttpStatus.OK.toString());
	}

	@GetMapping("/pedidoById")
	/* Testeado */
	public @ResponseBody String getPedidoById(@RequestParam(name = "nroPedido", required = true) int numero)
			throws JsonProcessingException, PedidoException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getPedidoById(numero));
	}

	@GetMapping("/rubros")
	/* Testeado */
	public @ResponseBody String getRubros() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getRubros());
	}

	@GetMapping("/subRubros")
	/* Testeado */
	public @ResponseBody String getSubRubros() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getSubRubros());
	}
	
	@GetMapping("/productos")
	/* Testeado */
	public @ResponseBody String getProductos() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getProductos());
	}

	@GetMapping("/productosByRubro")
	/* Testeado */
	public @ResponseBody String getProductosByRubro(@RequestParam(name = "codigo", required = true) int codigo)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		RubroView rubro = new RubroView(codigo, "", true);
		List<ProductoView> pv = Controlador.getInstancia().getProductosByRubro(rubro);

		return mapper.writeValueAsString(pv);
	}

	@GetMapping("/productosBySubRubro")
	/* Testeado */
	public @ResponseBody String getProductosBySubRubro(@RequestParam(name = "codigo", required = true) int codigo)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		SubRubroView subrubro = new SubRubroView(codigo, "", null);
		try {
			String s = mapper.writeValueAsString(Controlador.getInstancia().getProductosBySubRubro(subrubro));
			System.out.println(s);
			return s;
		} catch (JsonProcessingException e) {

			return e.getMessage();
		}

	}

	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	/* Testeado */
	public @ResponseBody String getClientes() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<ClienteView> clientes = Controlador.getInstancia().getClientes();
		return mapper.writeValueAsString(clientes);
	}
}
