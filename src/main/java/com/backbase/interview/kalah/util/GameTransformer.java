package com.backbase.interview.kalah.util;

import com.backbase.interview.kalah.model.domain.GameStatusEntity;
import com.backbase.interview.kalah.model.dto.GameModel;
import com.backbase.interview.kalah.model.dto.GameModelStatus;
import com.backbase.interview.kalah.model.domain.GameEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class GameTransformer {
    public static GameModel transformEntityToModel(GameEntity entity){
        GameModel output = new GameModel();
        if(Objects.nonNull(entity)) {
            output.setId(entity.getId());
            output.setUri(getUrl(entity.getId()));
        }
        return output;
    }

    public static GameModelStatus transformEntityToFullModel(GameEntity entity){
        GameModelStatus output = new GameModelStatus();
        if(Objects.nonNull(entity)){
            output.setId(entity.getId());
            output.setUri(getUrl(entity.getId()));
            output.setStatus(statusesToMap(entity.getStatuses()));
        }
        return output;
    }

    public static Map<Integer, Integer> statusesToMap(List<GameStatusEntity> input){
        return input.parallelStream().collect(Collectors.toMap(item -> item.getIndex(), item -> item.getValue()));
    }

    private static String getUrl(Long id){
        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return baseUri + "/games/" + String.valueOf(id);
    }
}
