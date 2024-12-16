package com.level.tech.service;

import com.level.tech.entity.State;
import com.level.tech.exception.AlreadyExistsException;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    @Override
    @Transactional
    public void addState(final String name) {
        if (stateRepository.existsByName(name)) {
            throw new AlreadyExistsException("State are exists");
        } else {
            State state = new State();
            state.setName(name);
            stateRepository.save(state);
        }
    }

    @Override
    @Transactional
    public void updateState(final Long id, final String name) {
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State not found"));
        state.setName(name);
        stateRepository.save(state);
    }

    @Override
    @Transactional(readOnly = true)
    public List<State> getStates() {
        return stateRepository.findAll();
    }

    @Override
    public void deleteState(final Long id) {
        stateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteStates(final List<Long> statesIds) {
        statesIds.forEach(this::deleteState);
    }
}
