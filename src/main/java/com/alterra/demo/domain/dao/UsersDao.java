package com.alterra.demo.domain.dao;

import com.alterra.demo.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "M_USERS")
@SQLDelete(sql = "UPDATE M_USERS SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL")

public class UsersDao extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(columnDefinition ="boolean default true")
    private boolean active = true;

    @ManyToOne
    private CityDao city;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return this.active;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return this.active;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return this.active;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.active;
//    }
}
