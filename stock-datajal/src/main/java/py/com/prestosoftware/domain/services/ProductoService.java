package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Subgrupo;
import py.com.prestosoftware.data.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private ProductoRepository repository;

    @Autowired
    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }
    
    public List<Producto> findAllByNombre() {
        return repository.findAllByOrderByDescripcionAsc();
    }

    public List<Producto> findAll() {
        return repository.findAll();
    }
    
    public List<Producto> findProductByFilter(String filter) {
        return repository.findProductsByFilter(filter);
    }
    
    public List<Producto> getProductoBySubrupo(Subgrupo subgrupo) {
        return repository.findBySubgrupo(subgrupo);
    }
    
    public List<Producto> getProductoByGrupo(Grupo grupo) {
		return repository.findByGrupo(grupo);
	}
    
    public Producto getPrecioByProductoId(Long productoId) {
    	return repository.getPrecioById(productoId);
    }
    
    public Optional<Producto> findByReferencia(String referencia) {
    	return repository.findByReferencia(referencia);
    }
    
    public Optional<Producto> findByCodigoBarra(String codigobarra) {
    	return repository.findByProductByCodigo(Long.valueOf(codigobarra));
    }
    
    public Optional<Producto> findById(Long id) {
    	return repository.findById(id);
    }
    
    public Optional<Producto> findByFilter(String filter) {
    	return repository.findByProductByFilter(filter);
    }

    public Optional<Producto> getProductoStockByDepositoId(Long id) {
    	return repository.findById(id);
    }
    
    public Producto getStockDepositoByProductoId(Long productoId) {
    	return repository.getStockById(productoId);
    }

    public List<Producto> findByNombre(String name) {
        return repository.findByDescripcionContaining(name);
    }

//    public void save(Producto producto) {
//        repository.save(producto);
//    }
    public Producto save(Producto producto) {
        Producto p = repository.save(producto);
        return p;
    }

    public void remove(Producto producto) {
        repository.delete(producto);
    }

	public long addNewProduct() {
		return repository.getMaxId();
	}

	@Transactional
	public void updateStock(List<Producto> productos) {
		repository.saveAll(productos);
		repository.flush();
	}
	
	@Transactional
	public void updateStock(Producto producto) {
		repository.save(producto);
		repository.flush();
	}
	
}
