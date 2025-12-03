package LapTrinhWeb.SpringBoot.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "categories")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoryId")
	private Integer categoryId;

	@Column(name = "categoryname", columnDefinition = "NVARCHAR(255)")
	private String categoryname;

	@Column(name = "categorycode", columnDefinition = "NVARCHAR(50)")
	private String categorycode;

	@Column(name = "images", columnDefinition = "NVARCHAR(MAX)")
	private String images;

	@Column(name = "status")
	private Boolean status = true;

	@ManyToOne
	@JoinColumn(name = "username")
	private User user;

	@OneToMany(mappedBy = "category")
	private List<Video> videos;
}
