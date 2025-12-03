package LapTrinhWeb.SpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
	private String username;
	private String password;
	private String phone;
	private String fullname;
	private String email;
	private Boolean admin = false;
	private Boolean active = true;
	private String images;
	private Boolean isEdit = false;
}
