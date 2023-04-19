package castleuk.selfsecurity.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class User {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;

		private String username;
		private String password;
		private String email;
		private String role; //ROLE_MANAGER, ROLE_ADMIN

		@CreationTimestamp
		private Timestamp createDate;

}
