package py.com.prestosoftware.ui.viewmodel;

import java.util.Date;

public class AjusteCuenta {
	
	private String documento;
	private Date vencimiento;
	private Double valor;
	private Double saldo;
	private Double valorPago;
	
	public AjusteCuenta() {
		super();
	}

	public AjusteCuenta(String documento, Date vencimiento, Double valor, Double saldo, Double valorPago) {
		super();
		this.documento = documento;
		this.vencimiento = vencimiento;
		this.valor = valor;
		this.saldo = saldo;
		this.valorPago = valorPago;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

}