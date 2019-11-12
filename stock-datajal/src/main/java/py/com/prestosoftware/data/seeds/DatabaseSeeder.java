package py.com.prestosoftware.data.seeds;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Categoria;
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Color;
import py.com.prestosoftware.data.models.CondicionPago;
import py.com.prestosoftware.data.models.Departamento;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Empaque;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Impuesto;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.models.Marca;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.Ncm;
import py.com.prestosoftware.data.models.Pais;
import py.com.prestosoftware.data.models.PlanCuenta;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.models.Rol;
import py.com.prestosoftware.data.models.Subgrupo;
import py.com.prestosoftware.data.models.Tamanho;
import py.com.prestosoftware.data.models.UnidadMedida;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.data.repository.CajaRepository;
import py.com.prestosoftware.data.repository.CategoriaRepository;
import py.com.prestosoftware.data.repository.CiudadRepository;
import py.com.prestosoftware.data.repository.ClienteRepository;
import py.com.prestosoftware.data.repository.ColorRepository;
import py.com.prestosoftware.data.repository.CondicionPagoRepository;
import py.com.prestosoftware.data.repository.DepartamentoRepository;
import py.com.prestosoftware.data.repository.DepositoRepository;
import py.com.prestosoftware.data.repository.EmpaqueRepository;
import py.com.prestosoftware.data.repository.EmpresaRepository;
import py.com.prestosoftware.data.repository.GrupoRepository;
import py.com.prestosoftware.data.repository.ImpuestoRepository;
import py.com.prestosoftware.data.repository.ListaPrecioRepository;
import py.com.prestosoftware.data.repository.MarcaRepository;
import py.com.prestosoftware.data.repository.MonedaRepository;
import py.com.prestosoftware.data.repository.NcmRepository;
import py.com.prestosoftware.data.repository.PaisRepository;
import py.com.prestosoftware.data.repository.PlanCuentaRepository;
import py.com.prestosoftware.data.repository.ProductoRepository;
import py.com.prestosoftware.data.repository.ProveedorRepository;
import py.com.prestosoftware.data.repository.RolRepository;
import py.com.prestosoftware.data.repository.SubgrupoRepository;
import py.com.prestosoftware.data.repository.TamanhoRepository;
import py.com.prestosoftware.data.repository.UnidadMedidaRepository;
import py.com.prestosoftware.data.repository.UsuarioRepository;

@Component
public class DatabaseSeeder {
	 
	private UsuarioRepository userRepository;
	private EmpresaRepository empresaRepository;
	private RolRepository rolRepository;
	private DepositoRepository depositoRepository;
	private PaisRepository paisRepository;
	private DepartamentoRepository depRepository;
	private CiudadRepository ciudadRepository;
	
	private TamanhoRepository tamanhoRepository;
	private GrupoRepository grupoRepository;
	private SubgrupoRepository subgrupoRepository;
	private ImpuestoRepository impuestoRepository;
	private MarcaRepository marcaRepository;
	private CategoriaRepository categororiaRepository;
	private EmpaqueRepository empaqueRepository;
	private NcmRepository ncmRepository;
	private ColorRepository colorRepository;
	private ListaPrecioRepository listaPrecioRepository;
	private UnidadMedidaRepository uomRepository;
	private ProductoRepository productoRepository;
	
	private MonedaRepository monedaRepository;
	private CajaRepository cajaRepository;
	private PlanCuentaRepository planCuentaRepository;
	private CondicionPagoRepository condicionPagoRepository;
	private ClienteRepository clienteRepository;
	private ProveedorRepository proveedorRepository;
	
	@Autowired
    public DatabaseSeeder(UsuarioRepository userRepository, EmpresaRepository empresaRepository,
    	RolRepository rolRepository, DepositoRepository depositoRepositor, CiudadRepository ciudadRepository,
    		TamanhoRepository tamanhoRepository, GrupoRepository grupoRepository,
    		ImpuestoRepository impuestoRepository, MarcaRepository marcaRepository,
    		CategoriaRepository categororiaRepository, EmpaqueRepository empaqueRepository, 
    		NcmRepository ncmRepository, MonedaRepository monedaRepository, CajaRepository cajaRepository,
    		SubgrupoRepository subgrupoRepository, PlanCuentaRepository planCuentaRepository,
    		PaisRepository paisRepository, DepartamentoRepository depRepository,
    		ColorRepository colorRepository, ClienteRepository clienteRepository, 
    		ProveedorRepository proveedorRepository, ListaPrecioRepository listaPrecioRepository,
    		UnidadMedidaRepository uomRepository, ProductoRepository productoRepository,
    		CondicionPagoRepository condicionPagoRepository) {
        this.userRepository = userRepository;
        this.empresaRepository = empresaRepository;
        this.rolRepository = rolRepository;
        this.depositoRepository = depositoRepositor;
        this.tamanhoRepository = tamanhoRepository;
        this.grupoRepository = grupoRepository;
        this.impuestoRepository = impuestoRepository;
        this.marcaRepository = marcaRepository;
        this.categororiaRepository = categororiaRepository;
        this.empaqueRepository = empaqueRepository;
        this.ncmRepository = ncmRepository;
        this.monedaRepository=monedaRepository;
        this.cajaRepository = cajaRepository;
        this.subgrupoRepository=subgrupoRepository;
        this.planCuentaRepository = planCuentaRepository;
        this.paisRepository = paisRepository;
        this.depRepository = depRepository;
        this.ciudadRepository = ciudadRepository;
        this.colorRepository = colorRepository;
        this.clienteRepository = clienteRepository;
        this.proveedorRepository = proveedorRepository;
        this.listaPrecioRepository = listaPrecioRepository;
        this.uomRepository = uomRepository;
        this.productoRepository = productoRepository;
        this.condicionPagoRepository = condicionPagoRepository;
    }
	 
	@EventListener
    public void seed(ContextRefreshedEvent event) {
		if (!getUserById()) {
			seedPaisTable();
			seedEmpresaTable();
			seedRolTable();
	        seedUsuarioTable();
	        seedDepositoTable();
	        seedDepartamentoTable();
	        seedTamanhoTable();
	        seedGrupoTable();
	        seedListaPrecioTable();
	        seedCategoriaTable();
	        seedCajaTable();
	        seedMonedaTable();
	        seedNcmTable();
	        seedEmpaqueTable();
	        seedImpuestoTable();
	        seedMarcaTable();
	        seedSubgrupoTable();
	        seedCiudadTable();
	        seedPlanCuentaTable();
	        seedColorTable();
	        seedClienteTable();
	        seedProveedorTable();
	        seedUomTable();
	        seedProductoTable();
	        seedCondicionPagoTable();
		}
    }
	
	private boolean getUserById() {
		Optional<Usuario> usuario = userRepository.findById(1L);
		
		if (usuario.isPresent()) {
			return true;
		}
		
		return false;
	}
	
	private void seedEmpresaTable() {
		empresaRepository.save(new Empresa("DATAJAL", "1111", "2222", 1));
	}
	
	private void seedRolTable() {
		rolRepository.save(new Rol("ADMIN", 1));
		rolRepository.save(new Rol("GERENTE", 0));
		rolRepository.save(new Rol("COMPRAS", 0));
		rolRepository.save(new Rol("VENTAS", 0));
		rolRepository.save(new Rol("CAJA", 0));
	}
	
	private void seedUsuarioTable() {
		userRepository.save(new Usuario("ROOT", "123", new Empresa(1L), new Rol(1L)));
	}
	
	private void seedDepositoTable() {
		depositoRepository.save(new Deposito("DEPOSITO 01", 1, new Empresa(1L)));
		depositoRepository.save(new Deposito("DEPOSITO 02", 1, new Empresa(1L)));
		depositoRepository.save(new Deposito("DEPOSITO 03", 1, new Empresa(1L)));
		depositoRepository.save(new Deposito("DEPOSITO 04", 1, new Empresa(1L)));
		depositoRepository.save(new Deposito("DEPOSITO 05", 1, new Empresa(1L)));
		depositoRepository.save(new Deposito("DEPOSITO 06", 1, new Empresa(1L)));
	}
	
	private void seedTamanhoTable() {
		tamanhoRepository.save(new Tamanho("P", 1));
		tamanhoRepository.save(new Tamanho("M", 1));
		tamanhoRepository.save(new Tamanho("G", 1));
	}
	
	private void seedGrupoTable() {
		grupoRepository.save(new Grupo("GENERAL", "GENERAL", new Tamanho(1L), 1));
		//grupoRepository.save(new Grupo("SERVICIOS", "SERVICIOS", new Tamanho(1L), "MERCADERIAS", 1));
	}
	
	private void seedSubgrupoTable() {
		subgrupoRepository.save(new Subgrupo("DIVERSOS", new Grupo(1L), "M", 1));
	}
	
	private void seedCategoriaTable() {
		categororiaRepository.save(new Categoria("DIVERSOS", 1));
	}
	
	private void seedCajaTable() {
		cajaRepository.save(new Caja("CAJA 01", 1, new Empresa(1L)));
		cajaRepository.save(new Caja("CAJA 02", 1, new Empresa(1L)));
		cajaRepository.save(new Caja("CAJA 03", 1, new Empresa(1L)));
		cajaRepository.save(new Caja("CAJA 05", 1, new Empresa(1L)));
		cajaRepository.save(new Caja("CAJA 05", 1, new Empresa(1L)));
	}
	
	private void seedMonedaTable() {
		monedaRepository.save(new Moneda("GUARANI", "GS$", "*", 1, 1));
		monedaRepository.save(new Moneda("DOLAR", "US$", "/", 0, 1));
		monedaRepository.save(new Moneda("REAL", "RS$", "/", 0, 1));
		monedaRepository.save(new Moneda("PESO", "PS$", "/", 0, 1));
		monedaRepository.save(new Moneda("EUROS", "EU$", "/", 0, 1));
	}
	
	private void seedNcmTable() {
		ncmRepository.save(new Ncm("DIVERSOS","DIV", 1));
	}
	
	private void seedEmpaqueTable() {
		empaqueRepository.save(new Empaque("DIVERSOS", 1));
	}
	
	private void seedImpuestoTable() {
		impuestoRepository.save(new Impuesto("EXENTAS", 0d, 1));
		impuestoRepository.save(new Impuesto("IVA 5", 5d, 1));
		impuestoRepository.save(new Impuesto("IVA 10", 10d, 1));
	}
	
	private void seedMarcaTable() {
		marcaRepository.save(new Marca("DIVERSOS", 1));
	}
	
	private void seedPlanCuentaTable() {
		planCuentaRepository.save(new PlanCuenta("(+) VENTA", "1", "E", 1));
		planCuentaRepository.save(new PlanCuenta("(-) COMPRA", "2", "S", 1));
		planCuentaRepository.save(new PlanCuenta("(+) COBRO DE CLIENTE", "3", "E", 1));
		planCuentaRepository.save(new PlanCuenta("(-) DEVOLUCION DE CLIENTES", "4", "S", 1));
		planCuentaRepository.save(new PlanCuenta("(+) DEVOLUCION A PROVEEDORES", "5", "E", 1));
		planCuentaRepository.save(new PlanCuenta("(-) PAGO A PROVEEDORES", "6", "S", 1));
	}
	
	private void seedPaisTable() {
		paisRepository.save(new Pais("PARAGUAY", 1));
		paisRepository.save(new Pais("BRASIL", 1));
		paisRepository.save(new Pais("ARGENTINA", 1));
	}
	
	private void seedDepartamentoTable() {
		depRepository.save(new Departamento("ALTO PARANA", new Pais(1L), 1));
		depRepository.save(new Departamento("DISTRITO CAPITAL", new Pais(1L), 1));
		depRepository.save(new Departamento("CENTRAL", new Pais(1L), 1));
		depRepository.save(new Departamento("ALTO PARAGUAY", new Pais(1L), 1));
	}
	
	private void seedCiudadTable() {
		ciudadRepository.save(new Ciudad("CIUDAD DEL ESTE", new Departamento(1L), 1));
		ciudadRepository.save(new Ciudad("CIUDAD PTE FRANCO", new Departamento(1L), 1));
		ciudadRepository.save(new Ciudad("HERNANDARIAS", new Departamento(1L), 1));
		ciudadRepository.save(new Ciudad("MINGA GUAZU", new Departamento(1L), 1));
	}
	
	private void seedColorTable() {
		colorRepository.save(new Color("DIVERSOS", 1));
	}
	
	private void seedProveedorTable() {
		proveedorRepository.save(new Proveedor("DIVERSOS", "DIVERSOS", "FISICOP", "88888801", "5", "CENTRO", 
			"A", new Pais(1L), new Departamento(1L), new Ciudad(1L), new Empresa(1L), "11111111", "mail@mail.com"));
	}
	
	private void seedListaPrecioTable() {
		listaPrecioRepository.save(new ListaPrecio("A", 1));
		listaPrecioRepository.save(new ListaPrecio("B", 1));
		listaPrecioRepository.save(new ListaPrecio("C", 1));
		listaPrecioRepository.save(new ListaPrecio("D", 1));
		listaPrecioRepository.save(new ListaPrecio("E", 1));
	}
	
	private void seedClienteTable() {
		clienteRepository.save(new Cliente("OCASIONAL", "OCASIONAL", "44444401", "7", "CDE",
			new Pais(1L), new Departamento(1L), new Ciudad(1L), new Empresa(1L), new Date(), "FISICO", 1, new ListaPrecio(1L)));
	}
	
	private void seedUomTable() {
		uomRepository.save(new UnidadMedida("UNIDAD", 1));
		uomRepository.save(new UnidadMedida("CAJA", 1));
	}
	
	private void seedProductoTable() {
		productoRepository.save(new Producto("PRODUCTO DESCRI", "PRODUCTO DESCRI FISCAL", 
				"REF-001", "SUB-REF001", new Grupo(1L), new Subgrupo(1L), new Marca(1L), new Color(1L),
				 new Tamanho(1L), new UnidadMedida(1L), new Impuesto(1L), new Categoria(1L), new Ncm(1L), 
				100d, 90d, 80d, 70d, 60d));
	}
	
	private void seedCondicionPagoTable() {
		condicionPagoRepository.save(new CondicionPago("CONTADO", 0, 1));
		condicionPagoRepository.save(new CondicionPago("CREDITO 1 DIA", 1, 1));
		condicionPagoRepository.save(new CondicionPago("CREDITO 5 DIAS", 5, 1));
		condicionPagoRepository.save(new CondicionPago("CREDITO 10 DIAS", 10, 1));
		condicionPagoRepository.save(new CondicionPago("CREDITO 15 DIAS", 15, 1));
		condicionPagoRepository.save(new CondicionPago("CREDITO 20 DIAS", 20, 1));
		condicionPagoRepository.save(new CondicionPago("CREDITO 30 DIAS", 30, 1));
		condicionPagoRepository.save(new CondicionPago("CREDITO 60 DIAS", 60, 1));
		condicionPagoRepository.save(new CondicionPago("CREDITO 90 DIAS", 90, 1));
	}
	
}
