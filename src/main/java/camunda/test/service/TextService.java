/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camunda.test.service;

import camunda.test.jpa.model.StatisticData;
import camunda.test.jpa.model.Text;
import camunda.test.jpa.model.Token;
import camunda.test.jpa.repository.TextRepository;
import camunda.test.jpa.repository.TokenRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marijo
 */
@Slf4j
@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class TextService {

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public Text storeText(String text) {
        Text t = new Text();
        t.setText(text);
        return textRepository.save(t);
    }

    public void tokenizeText(Long textId) {
        Text text = textRepository.findOne(textId);
        String[] tokens = text.getText().split(" |\r\n|\t");
        for (String token : tokens) {
            Token t = tokenRepository.findByValue(token);
            if (t == null) {

                t = new Token();
                StatisticData statisticData = new StatisticData();

                t.setValue(token);
                t.getTexts().add(text);
                t.setStatisticData(statisticData);

                statisticData.setTokenCount(1L);
                statisticData.setToken(t);

                text.getTokens().add(t);
                tokenRepository.save(t);
            } else {
                StatisticData sd = t.getStatisticData();
                sd.setTokenCount(sd.getTokenCount() + 1L);
                
                if (!text.getTokens().contains(t)){
                    text.getTokens().add(t);
                }
            }
        }
    }

    public Map<String, Long> fetchTextStatistics(Long textId) {
        Text text = textRepository.findOne(textId);
        Map<String, Long> statistics = new HashMap<>();
        List<Token> tokens = text.getTokens();
        tokens.stream().forEach(token -> statistics.put(token.getValue(), token.getStatisticData().getTokenCount()));
        log.info("Statistics: "+statistics.toString());
        return statistics;
    }
}
