package LapTrinhWeb.SpringBoot.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "videos")
@NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v")
public class Video implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "videold")
	private Integer videold;

	@Column(name = "title", columnDefinition = "NVARCHAR(255)")
	private String title;

	@Column(name = "poster", columnDefinition = "NVARCHAR(MAX)")
	private String poster;

	@Column(name = "views")
	private Integer views = 0;

	@Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
	private String description;

	@Column(name = "active")
	private Boolean active = true;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
}
