package LapTrinhWeb.SpringBoot.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "username", columnDefinition = "NVARCHAR(50)")
	private String username;

	@Column(name = "password", columnDefinition = "NVARCHAR(255)")
	private String password;

	@Column(name = "phone", columnDefinition = "NVARCHAR(20)")
	private String phone;

	@Column(name = "fullname", columnDefinition = "NVARCHAR(255)")
	private String fullname;

	@Column(name = "email", columnDefinition = "NVARCHAR(255)")
	private String email;

	@Column(name = "admin")
	private Boolean admin = false;

	@Column(name = "active")
	private Boolean active = true;

	@Column(name = "images", columnDefinition = "NVARCHAR(MAX)")
	private String images;

	@OneToMany(mappedBy = "user")
	private List<Category> categories;
}
