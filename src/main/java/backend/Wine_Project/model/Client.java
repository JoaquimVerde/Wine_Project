package backend.Wine_Project.model;

import jakarta.persistence.*;
import org.hibernate.mapping.List;

@Entity
@Table
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private int nif;
    private List<Wine> wineList;
    private List<Wine> wishlist;







}
