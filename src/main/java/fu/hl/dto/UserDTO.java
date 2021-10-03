package fu.hl.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class UserDTO {

  private long id;
  private String username;
  private String fullName;
  private Date dateOfBirth;
  private String phone;
  private String address;
  private List<UserDTO> friends;
  private boolean isActive;

}
