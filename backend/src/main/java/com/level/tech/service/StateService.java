package com.level.tech.service;

import com.level.tech.entity.State;

import java.util.List;

public interface StateService {

    void addState(String name);

    void updateState(Long id, String name);

    List<State> getStates();

    void deleteState(Long id);

    void deleteStates(List<Long> id);

}
