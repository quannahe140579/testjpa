package fu.hl.api;

import com.github.javafaker.Faker;
import fu.hl.dto.UserDTO;
import fu.hl.entity.User;
import fu.hl.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserAPI {

  private final UserRepository userRepository;

  public UserAPI(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/test")
  public UserDTO test() {

    // If user does not exists in DB, create it
    if (userRepository.count() <= 0) {

      User user1 = _createUser();
      User friend1 = _createUser();
      User friend2 = _createUser();

      friend1.setParent(user1);
      friend2.setParent(user1);
      List<User> user1Friends = new ArrayList<>();
      user1Friends.add(friend1);
      user1Friends.add(friend2);
      user1.setListFriend(user1Friends);

      // save to DB
      userRepository.save(user1);
      userRepository.save(friend1);
      userRepository.save(friend2);
    }

    // then retrieve from DB
    Optional<User> userInDB = userRepository.findById(1L);

    return _mapUserToUserDTO(userInDB.orElse(null));
  }

  private User _createUser() {
    Faker faker = Faker.instance();

    // create user first
    User createdUser = new User();
    createdUser.setUsername(faker.name().username());
    createdUser.setPassword(faker.crypto().sha256());
    createdUser.setActive(true);
    createdUser.setFullName(faker.name().fullName());
    createdUser.setDateOfBirth(faker.date().birthday());
    createdUser.setPhone(faker.phoneNumber().cellPhone());
    createdUser.setAddress(faker.address().fullAddress());

    return createdUser;
  }

  private UserDTO _mapUserToUserDTO(User user) {
    if (user == null) {
      return null;
    }

    UserDTO dto = new UserDTO();
    dto.setId(user.getId());
    dto.setUsername(user.getUsername());
    dto.setFullName(user.getFullName());
    dto.setDateOfBirth(user.getDateOfBirth());
    dto.setPhone(user.getPhone());
    dto.setAddress(user.getAddress());
    dto.setFriends(
        CollectionUtils.isEmpty(user.getListFriend())
            ? null
            : user.getListFriend()
                .stream()
                .map(u -> _mapUserToUserDTO(u))
                .collect(Collectors.toList())
    );

    return dto;
  }
}
