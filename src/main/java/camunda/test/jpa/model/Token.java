/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camunda.test.jpa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Marijo
 */
@Getter
@Setter
@Entity
@Table(name = "DTA_TOKEN")
@SequenceGenerator(name = "sequence_generator", sequenceName = "seq_token_id", allocationSize = 50)
public class Token extends DatabaseObject implements Serializable {
    
    @Column(name = "token", nullable = false, length = 50)
    private String value;
    
    @OneToOne(mappedBy = "token", cascade = CascadeType.ALL)
    private StatisticData statisticData;
    
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "tokens")
    private List<Text> texts = new ArrayList<>();
}
