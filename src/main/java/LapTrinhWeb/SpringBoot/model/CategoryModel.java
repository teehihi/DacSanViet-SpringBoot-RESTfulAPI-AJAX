package LapTrinhWeb.SpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {
	private Integer categoryId;
	private String categoryname;
	private String categorycode;
	private String images;
	private Boolean status = true;
	private String username;
	private Boolean isEdit = false;
}
