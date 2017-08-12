
package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Producto;

@Stateless
public class ProductoDaoImpl implements ProductoDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> findAllProductos() {
		return em.createNamedQuery("Producto.findAll").getResultList();
	}

	@Override
	public Producto obtenerProductoById(Producto instance) {
		return em.find(Producto.class, instance.getIdproducto());
	}

	@Override
	public void insertProducto(Producto instance) {
		em.persist(instance);

	}

	@Override
	public void updateProducto(Producto instance) {
		em.merge(instance);

	}

	@Override
	public void deleteProducto(Producto instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
