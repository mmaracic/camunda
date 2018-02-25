/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camunda.test.jpa.repository;

import camunda.test.jpa.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Marijo
 */
public interface TokenRepository extends JpaRepository<Token, Long>{
    Token findByText(String text);
}
