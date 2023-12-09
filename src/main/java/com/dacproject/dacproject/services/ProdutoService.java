package com.dacproject.dacproject.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dacproject.dacproject.dtos.CategoriaDTO;
import com.dacproject.dacproject.dtos.ProdutoDTO;
import com.dacproject.dacproject.entities.Categoria;
import com.dacproject.dacproject.entities.Produto;
import com.dacproject.dacproject.repositories.CategoriaRepository;
import com.dacproject.dacproject.repositories.ProdutoRepository;
import com.dacproject.dacproject.services.execptions.DatabaseException;
import com.dacproject.dacproject.services.execptions.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;

/*As classes de serviços não têm acesso direto às classes de entidade 
 * do módulo de Models.
 * Para isso criamos as classes DTO'S para fazermos cópias que criamos 
 * a depender das necessidades das informações no Frontend.
 */

@Service
public class ProdutoService {

    @Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	/*Aqui neste método usamos o Pageable
	 * esse objeto do spring oferece ferramentas para paginação,
	 * muito utilizado para criação de páginas numeradas no frontend.
	 */
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> findAllPaged(Long categoriaId, String nome, Pageable pageable) {
		List<Categoria> categorias = (categoriaId == 0) ? null : Arrays.asList(categoriaRepository.getOne(categoriaId));
		Page<Produto> page = repository.find(categorias, nome, pageable);
		repository.findProductsWithCategories(page.getContent());
		return page.map(x -> new ProdutoDTO(x, x.getCategorias()));
	}

	@Transactional(readOnly = true)
	public ProdutoDTO findById(Long id) {
		Optional<Produto> obj = repository.findById(id);
		Produto entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProdutoDTO(entity, entity.getCategorias());
	}

	@Transactional
	public ProdutoDTO insert(ProdutoDTO dto) {
		Produto entity = new Produto();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProdutoDTO(entity);
	}

	@Transactional
	public ProdutoDTO update(Long id, ProdutoDTO dto) {
		try {
			Produto entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProdutoDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(ProdutoDTO dto, Produto entity) {

		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
		entity.setPreco(dto.getPreco());
        entity.setQuantidade(dto.getQuantidade());
		
		entity.getCategorias().clear();
		for (CategoriaDTO catDto : dto.getCategorias()) {
			Categoria categoria = categoriaRepository.getOne(catDto.getId());
			entity.getCategorias().add(categoria);
	    }
	}
    
}
