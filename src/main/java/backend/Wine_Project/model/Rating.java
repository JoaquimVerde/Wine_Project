package backend.Wine_Project.model;

import jakarta.persistence.*;

@Entity
@Table
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Client client;
    private Wine wine;
    private double rating;


}
