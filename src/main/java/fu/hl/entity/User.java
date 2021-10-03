package fu.hl.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "[User]")
@Data
public class User extends Account {

  @Column(name = "full_name", length = 30)
  private String fullName;

  @Column(name = "birth_date")
  @Temporal(TemporalType.DATE)
  private Date dateOfBirth;

  @Column(name = "phone")
  private String phone;

  @Column(name = "address")
  private String address;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "friend_id")
  private User parent;

//  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @OneToMany(mappedBy = "parent")
  private List<User> listFriend;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Post> listPos;

  @Lob
  @Column(name = "avatar")
  private byte[] avatar;

}
