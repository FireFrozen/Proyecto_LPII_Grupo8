package com.restaurante.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido", nullable = false)
    private int idPedido;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "estado_pedido", nullable = false, length = 20)
    private String estadoPedido;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "id_mesa")
    @JsonIgnoreProperties({"listaPedidos"})
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @JsonIgnoreProperties({"listaPedidos"})
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({"listaPedidos"})
    private Usuario usuario;
    
    @OneToMany(mappedBy = "pedido")
    @JsonIgnore
    private List<DetallePedido> listaDetalles;

    public Pedido() {
    }

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<DetallePedido> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<DetallePedido> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}
}