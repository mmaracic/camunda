/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camunda.test.jpa.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author Marijo
 */
@Getter
@Setter
@Entity
@Table(name = "DTA_TOKEN_STATISTIC")
@SequenceGenerator(name = "sequence_generator", sequenceName = "seq_token_statistic_id", allocationSize = 50)
public class StatisticData extends DatabaseObject implements Serializable {
    
    @Column(name = "token_count")
    private Long tokenCount;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id", nullable = false)
    private Token token;
}
