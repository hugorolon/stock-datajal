package py.com.prestosoftware.ui.helpers;

import java.util.List;
//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRField;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DataSource { //implements JRDataSource
	    
	private int contador = 0;
	@SuppressWarnings("unchecked")
	private List lista;
	private List listaAux;
	private List listaAux2;

	public DataSource(){
	    super();    
	}

//	public boolean next() throws JRException {    
//	    this.contador++;
//	    if (this.contador == 1)
//	      return true;      
//	    
//	    return false;
//	}
	
//	public Object getFieldValue(JRField field) throws JRException{ 
//	    if(field == null)
//	        return null;      
//	    if (field.getName().equalsIgnoreCase("lista"))	        	
//	    	return new JRBeanCollectionDataSource(lista);
//	    if (field.getName().equalsIgnoreCase("listaAux"))	        	
//	    	return new JRBeanCollectionDataSource(listaAux);
//	    if (field.getName().equalsIgnoreCase("listaAux2"))	        	
//	    	return new JRBeanCollectionDataSource(listaAux2);
//	    
//	    return null;
//	}
	
	public int getContador() {
		return contador;
	}
	
	public void setContador(int contador) {
		this.contador = contador;
	}
	
	@SuppressWarnings("unchecked")
	public List getLista() {
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public void setLista(List lista) {
		this.lista = lista;
	}
	@SuppressWarnings("unchecked")
	public List getListaAux() {
		return listaAux;
	}
	
	@SuppressWarnings("unchecked")
	public void setListaAux(List listaAux) {
		this.listaAux = listaAux;
	}
	@SuppressWarnings("unchecked")
	public List getListaAux2() {
		return listaAux2;
	}
	
	@SuppressWarnings("unchecked")
	public void setListaAux2(List listaAux2) {
		this.listaAux2 = listaAux2;
	}
} 