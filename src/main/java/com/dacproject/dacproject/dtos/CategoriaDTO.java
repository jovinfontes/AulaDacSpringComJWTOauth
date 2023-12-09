package com.dacproject.dacproject.dtos;

import java.io.Serializable;

import com.dacproject.dacproject.entities.Categoria;

public class CategoriaDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Long id;
	private String descricao;
	
	public CategoriaDTO() {
	}

	public CategoriaDTO(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public CategoriaDTO(Categoria entity) {
		this.id = entity.getId();
		this.descricao = entity.getDescricao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
