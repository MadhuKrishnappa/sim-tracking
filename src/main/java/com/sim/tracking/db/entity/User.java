package com.sim.tracking.db.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Setter
@Getter
@ToString
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private BigInteger id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "partner_id")
    private BigInteger partnerId;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
