package org.softwarecave.springboottours.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TOUR_PACKAGE")
public class TourPackage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Exclude
    private String id;

    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;

    public TourPackage(String code, String name) {
        this.id = null;
        this.code = code;
        this.name = name;
    }


}
