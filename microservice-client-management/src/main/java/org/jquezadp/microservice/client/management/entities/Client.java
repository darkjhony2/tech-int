package org.jquezadp.microservice.client.management.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clientes", uniqueConstraints = {
        @UniqueConstraint(name = "clientId", columnNames = {"clientId"}),
        @UniqueConstraint(name = "identification", columnNames = {"identification"}),
        @UniqueConstraint(name = "nameIdentification", columnNames = {"name", "identification"})
})
@Data
@NoArgsConstructor
public class Client extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientId;
    private String password;
    private boolean status = true;
}
