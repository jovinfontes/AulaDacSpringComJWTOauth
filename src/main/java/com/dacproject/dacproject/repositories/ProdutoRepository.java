package com.dacproject.dacproject.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dacproject.dacproject.entities.Categoria;
import com.dacproject.dacproject.entities.Produto;

/*Interface que herda componentes do spring: JPA
 * Existem métodos já pré definidos
 */

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	/*Aqui, além da existência dos métodos da JPA do Spring,
	 * podemos customizar as CUSTOM QUERIES
	 */
    @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cats WHERE "
			+ "(COALESCE(:categorias) IS NULL OR cats IN :categorias) AND "
			+ "(LOWER(obj.descricao) LIKE LOWER(CONCAT('%',:descricao,'%'))) ")
	Page<Produto> find(List<Categoria> categorias, String descricao, Pageable pageable);
	
	@Query("SELECT obj FROM Produto obj JOIN FETCH obj.categorias WHERE obj IN :produtos")
	List<Produto> findProductsWithCategories(List<Produto> produtos);
    
}
