package com.restaurante.backend.service;

import java.util.List;

import com.restaurante.backend.vo.CategoriaRequestVO;
import com.restaurante.backend.vo.CategoriaResponseVO;

/**
 * Servicio que define las operaciones relacionadas con las categorías de platos.
 * Se encarga de la lógica de negocio para crear, consultar, actualizar y eliminar categorías.
 */
public interface CategoriaService {
	
	 CategoriaResponseVO crearCategoria(CategoriaRequestVO vo);
	    List<CategoriaResponseVO> listarCategorias();
	    CategoriaResponseVO obtenerCategoriaPorId(Long id);
	    CategoriaResponseVO actualizarCategoria(Long id, CategoriaRequestVO vo);
	    void eliminarCategoria(Long id);

}
