package com.distribuidas.distribuidas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.distribuidas.distribuidas.mensajes.MensajesHttp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controlador.Controlador;
import daos.RubroDao;
import daos.SubRubroDao;
import exceptions.CambioPasswordException;
import exceptions.ClienteException;
import exceptions.LoginException;
import exceptions.PedidoException;
import exceptions.ProductoException;
import exceptions.RubroException;
import exceptions.SubRubroException;
import exceptions.UsuarioException;
import negocio.Producto;
import negocio.Rubro;
import negocio.SubRubro;
import view.ClienteView;
import view.PedidoView;
import view.ProductoView;
import view.RubroView;
import view.SubRubroView;

@Controller
public class HomeController {
	
	@PostMapping("/login")	
	@ResponseBody
	public String login (@RequestParam(name="user", required = true) String user, @RequestParam(name="password", required = true) String password ) throws JsonProcessingException, LoginException, CambioPasswordException, UsuarioException {
		ObjectMapper mapper=new ObjectMapper();
		
		//Controlador.getInstancia().login(user, password);
		return mapper.writeValueAsString(MensajesHttp.get200Code());
		
	}
	

	@PostMapping("/cambioPassword")	
	@ResponseBody
	public String cambioPassword (@RequestParam(name="user", required = true) String user, @RequestParam(name="password", required = true) String password ) throws JsonProcessingException, LoginException, CambioPasswordException, UsuarioException {
		ObjectMapper mapper=new ObjectMapper();
		
		Controlador.getInstancia().cambioPassword(user, password);
		return mapper.writeValueAsString(MensajesHttp.get200Code());
		
	}

	@PostMapping("/altaProducto")	
	@ResponseBody
	public String altaProducto(
			@RequestParam(name="codigoRubro", required = true) int codigoRubro, 
			@RequestParam(name="codigoSubRubro", required = true) int codigoSubRubro,
			@RequestParam(name="nombre", required = true) String pnombre,
			@RequestParam(name="marca", required = true) String pmarca,
			@RequestParam(name="codigoDeBarras", required = true) String pcodigo,
			@RequestParam(name="precio", required = true) float pprecio
			
	) throws RubroException, SubRubroException, JsonProcessingException {
		
		ObjectMapper mapper=new ObjectMapper();
		RubroView rv = new RubroView (codigoRubro, pcodigo, false);
		SubRubroView srv = new SubRubroView (codigoSubRubro, "", rv);
		ProductoView recibido = new ProductoView (srv, rv, pnombre, pmarca, pcodigo, pprecio);
		
		Controlador.getInstancia().altaProducto(recibido);
		
		return mapper.writeValueAsString(MensajesHttp.get200Code());
	}

	@PostMapping("/bajaProducto")	
	@ResponseBody
	public String bajaProducto(@RequestParam(name="identificador", required = true) int identificador) throws ProductoException, JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		ProductoView pv = new ProductoView (null, null, null, null, null, 0);
		pv.setIdentificador(identificador); //lo tuve que agregar
		Controlador.getInstancia().bajaProducto(pv);
		return mapper.writeValueAsString(MensajesHttp.get200Code());
	}

	@PostMapping("/modificaProducto")	
	@ResponseBody
	public String modificaProducto(@RequestParam(name="identificador", required = true) int identificador) throws ProductoException, JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		ProductoView pv = new ProductoView (null, null, null, null, null, 0);
		pv.setIdentificador(identificador); //lo tuve que agregar
		Controlador.getInstancia().modificaProducto(pv);
		return mapper.writeValueAsString(MensajesHttp.get200Code());
	}
	
	//Medio al pedo? Si ya esta el de abajo que pide el cuit
	/*@PostMapping("/crearPedido")	
	@ResponseBody
	public String crearPedido(@RequestParam(name="cuit", required = true) String cuit) throws ClienteException, JsonProcessingException  {
		ObjectMapper mapper=new ObjectMapper();
		ClienteView cliente = new ClienteView ();
		cliente.setCuil(cuit);
		PedidoView pedido = new PedidoView ();
		pedido.setCliente(cliente);
		int pnro = Controlador.getInstancia().crearPedido(pedido);
		return mapper.writeValueAsString(pnro);
	}*/
	
	@PostMapping("/crearPedido") /*Crear mensaje que contenga el nro de pedido*/
	public String crearPedido(@RequestParam(name="cuit", required = true) String cuit) throws ClienteException, JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		int pnro = Controlador.getInstancia().crearPedido(cuit);
		return mapper.writeValueAsString(pnro);
	}
	
	@PostMapping("/agregarProductoEnPedido")
	public String agregarProductoEnPedido(
			@RequestParam(name="numeroPedido", required = true) int numeroPedido, 
			@RequestParam(name="identificadorProd", required = true) int identificadorProducto,
			@RequestParam(name="cantidad", required = true)int cantidad
	) throws PedidoException, ProductoException, JsonProcessingException{	
		
		ObjectMapper mapper=new ObjectMapper();
		Controlador.getInstancia().agregarProductoEnPedido(numeroPedido, identificadorProducto, cantidad);
		return mapper.writeValueAsString(MensajesHttp.get200Code());
	}
	
	@PostMapping("/eliminarPedido")
	public String eliminarPedido(@RequestParam(name="numeroPedido", required = true)int numeroPedido) throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		Controlador.getInstancia().eliminarPedido(numeroPedido);
		return mapper.writeValueAsString(MensajesHttp.get200Code());
	}
	
	@PostMapping("/facturarPedido")
	public String facturarPedido(@RequestParam(name="nroFactura", required = true) int numero) throws JsonProcessingException, PedidoException{
		ObjectMapper mapper=new ObjectMapper();
		Controlador.getInstancia().facturarPedido(numero);
		return mapper.writeValueAsString(MensajesHttp.get200Code());
	}
	
	@GetMapping("/getPedidoById")
	public String getPedidoById(@RequestParam(name="nroPedido", required = true) int numero) throws JsonProcessingException, PedidoException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getPedidoById(numero));
	}

	@GetMapping("/getRubros")
	public String getRubros() throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getRubros());
	}
	
	@GetMapping("/getSubRubros")
	public String getSubRubros() throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getSubRubros());
	}
	
	@GetMapping("/getProductos")
	public String getProductos() throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(Controlador.getInstancia().getProductos());
	}
	
	/*Para buscar por rubro solo se necesita el codigo. No sera necesario para dicha operacion que se ingresen obligatoriamente los parametros de descripcion y habilitado*/
	//,@RequestParam(name="descripcion", required = false) String descripcion, @RequestParam(name="habilitado", required = false) boolean habilitado    - es necesario?
	@GetMapping("/getProductosByRubro")
	public String getProductosByRubro(@RequestParam(name="codigo", required = true) int codigo) throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		RubroView rubro = new RubroView(codigo, "", true);
		return mapper.writeValueAsString(Controlador.getInstancia().getProductosByRubro(rubro));
	}
	
	/*Lo mismo que en el metodo anterior. Para buscar el subrubro solo usa el getCodigo*/
	/* @RequestParam(name="codigo", required = true) int codigo,
	 * @RequestParam(name="descripcion", required = false) String descripcion,
	 * datos del rubroview:
	 * @RequestParam(name="codigorubro", required = true) int codigorubro,
	 * @RequestParam(name="descripcionrubro", required = false) String descripcionrubro, 
	 * @RequestParam(name="habilitado", required = false) boolean habilitado*/
	public String getProductosBySubRubro(@RequestParam(name="codigo", required = true) int codigo) throws JsonProcessingException{
		ObjectMapper mapper=new ObjectMapper();
		SubRubroView subrubro = new SubRubroView(codigo, "", null);
		return mapper.writeValueAsString(Controlador.getInstancia().getProductosBySubRubro(subrubro));
	}
	
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
