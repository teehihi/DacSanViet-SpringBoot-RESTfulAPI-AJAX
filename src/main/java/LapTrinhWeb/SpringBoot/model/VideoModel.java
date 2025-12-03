package LapTrinhWeb.SpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoModel {
	private Integer videold;
	private String title;
	private String poster;
	private Integer views = 0;
	private String description;
	private Boolean active = true;
	private Integer categoryId;
	private Boolean isEdit = false;
}
