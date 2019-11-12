package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

	@Autowired private ProductoRepository repository;

    public DataService() { }

    public void updateStockItems(List<Producto> items) {
    	List<Producto> productos = new ArrayList<>();

//		items.forEach(e -> {
//			Optional<Producto> pOptional = repository.findById(e.getProductoId());
//
//			if (pOptional.isPresent()) {
//				Producto p = pOptional.get();
//
//				int depesitoId = tfDepositoID.getText().isEmpty() ? 0 : Integer.parseInt(tfDepositoID.getText());
//				Double salPend = p.getSalidaPend() != null ? p.getSalidaPend() : 0;
//				Double cantItem = e.getCantidad();
//
//				switch (depesitoId) {
//					case 1:
//						Double depBloq = p.getDepO1Bloq() != null ? p.getDepO1Bloq() : 0;
//						p.setDepO1Bloq(depBloq - cantItem);
//						p.setSalidaPend(salPend + cantItem);
//						break;
//					case 2:
//						Double depBloq02 = p.getDepO2Bloq() != null ? p.getDepO2Bloq() : 0;
//						p.setDepO2Bloq(depBloq02 - cantItem);
//						p.setSalidaPend(salPend + cantItem);
//						break;
//					case 3:
//						Double depBloq03 = p.getDepO3Bloq() != null ? p.getDepO3Bloq() : 0;
//						p.setDepO3Bloq(depBloq03 - cantItem);
//						p.setSalidaPend(salPend + cantItem);
//						break;
//					case 4:
//						Double depBloq04 = p.getDepO4Bloq() != null ? p.getDepO4Bloq() : 0;
//						p.setDepO4Bloq(depBloq04 - cantItem);
//						p.setSalidaPend(salPend + cantItem);
//						break;
//					case 5:
//						Double depBloq05 = p.getDepO5Bloq() != null ? p.getDepO5Bloq() : 0;
//						p.setDepO5Bloq(depBloq05 - cantItem);
//						p.setSalidaPend(salPend + cantItem);
//						break;
//					default:
//						break;
//				}
//
//				productos.add(p);
//			}
//		});

		repository.saveAll(productos);
    }

}
