package com.hotel.entity;

import com.hotel.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@SQLRestriction("is_deleted = false")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String password;
    private String phone;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;
}