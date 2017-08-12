package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Producto;

public interface ProductoDao {
	
	public List<Producto> findAllProductos();

	public Producto obtenerProductoById(Producto instance);

	public void insertProducto(Producto instance);

	public void updateProducto(Producto instance);

	public void deleteProducto(Producto instance);

}
