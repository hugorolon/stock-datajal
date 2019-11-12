package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "condiciones_de_pagos")
public class CondicionPago {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nombre;
	
	@NotNull
	@Column(name = "cant_dia")
	private int cantDia;
	
	@ColumnDefault(value = "1")
	private int activo;
	
	public CondicionPago() {}
	
	public CondicionPago(Long id) {
		this.id = id;
	}
	
	public CondicionPago(String nombre, int cantDia, int activo) {
		this.nombre = nombre;
		this.cantDia = cantDia;
		this.activo = activo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	public int getCantDia() {
		return cantDia;
	}

	public void setCantDia(int cantDia) {
		this.cantDia = cantDia;
	}

	@Override
	public String toString() {
		return this.cantDia + " ";
	}

}