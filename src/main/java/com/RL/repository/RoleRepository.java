package com.RL.repository;
import com.RL.domain.Role;
import com.RL.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  RoleRepository extends JpaRepository<Role,Long> {


        Optional<Role> findByName(RoleType name);

	/*Java Optional sınıfı, null değerlerle zarif bir şekilde
	ilgilenen bir kapsayıcıdır. Optional sınıfı, uygulamanızı çalışma zamanında NullPointerExceptions
	'a karşı koruma sağlamak için birden çok null değer denetime olan ihtiyacı ortadan kaldırmak için
	Java 8 ile birlikte Java.util paketine eklenmiştir.*/
    }
