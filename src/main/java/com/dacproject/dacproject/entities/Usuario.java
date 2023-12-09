package com.dacproject.dacproject.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*Aqui implementaremos a interface UserDetails 
 * como falado no slide de Oauth2
 */

@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {
	private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	
	@Column(unique = true)
	private String email;
	private String password;
	
	/*Essa coleção terá uma associação a um tipo de usuário
	 * com o seu perfil adotado.
	 * O usuário pode ter mais de um perfil
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_usuario_role",
		joinColumns = @JoinColumn(name = "usuario_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))	
	private Set<Role> roles = new HashSet<>();
	
	public Usuario() {
	}

	public Usuario(Long id, String firstName, String lastName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
	/*A partir daqui são os métodos da interface UserDetails
	 * 
	 * Neste método converteremos os perfis - Roles para uma colecção de GrantedAuthority,
	 * isso permitirar percorrer todos os perfis de uma lista para verificação.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority()))
				.collect(Collectors.toList());
	}

	/*Aqui utilizaremos este método da interface implementada pela nossa classe Usuario
	 * e usaremos o atributo email como credencial nome de usuário
	 */
	@Override
	public String getUsername() {
		return email;
	}

	/*Configuremos este método como true,
	 * indicando que a conta não está expeirada
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*Configuremos este método como true,
	 * indicando que a conta não está bloqueada
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*Configuremos este método como true,
	 * indicando que as credenciais não estão expeirada
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*Configuremos este método como true,
	 * indicando que a conta está ativa
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}
