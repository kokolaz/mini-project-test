package com.alterra.demo.domain.dao;

import com.alterra.demo.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "M_RESTAURANT")
@SQLDelete(sql = "UPDATE M_RESTAURANT SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL")

public class RestaurantDao extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CityDao city;

    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @ManyToOne
    private UsersDao ownerId;
}
