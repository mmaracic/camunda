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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "text")
    private List<Token> tokens = new ArrayList<>();
}
