/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camunda.test.jpa.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author Marijo
 */
@Getter
@Setter
@Entity
@Table(name = "DTA_TEXT")
@SequenceGenerator(name = "sequence_generator", sequenceName = "seq_text_id", allocationSize = 50)
@EntityListeners(AuditingEntityListener.class)
public class Text extends DatabaseObject implements Serializable {
    @Column(name = "text", nullable = false)
    private String text;
    
    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private OffsetDateTime createDate;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id", nullable = false)
    private List<Token> tokens = new ArrayList<>();
}
