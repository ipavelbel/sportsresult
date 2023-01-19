package com.belavus.sportsresult.service;



import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamService { // TODO: n.kvetko: perform code formatting

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }


    public List<Team> findAll(){
        return teamRepository.findAll();
    }

    public void save(Team team){
        teamRepository.save(team); // TODO: n.kvetko: Return value of the method is never used
    }

    public void deleteById(Integer id){
        teamRepository.deleteById(id);
    }

    public List<Team> getTeamsByEventId(Integer id) {

        return teamRepository.findTeamsByEventsId(id);
    }

    public Team findOne(int teamId) {
        Optional<Team> foundTeam = teamRepository.findById(teamId);
        return foundTeam.orElse(null);
    }

    public void update(int teamId, Team updateTeam) {
        updateTeam.setId(teamId);
        teamRepository.save(updateTeam);
    }

    public Set<Event> getTeamInEvent(int id) {
        return teamRepository.findById(id).map(Team::getEvents).orElse(null);
    }

    public void assign(int id, Set<Event> selectedEvent) {
        teamRepository.findById(id).ifPresent(
                team -> {
                    team.setEvents((selectedEvent));
                }
        );


    }
//    public Event getTeamWithEvent(int id) {
//        return teamRepository.findById(id).map(Team::getEvents).orElse(null);
//    }
}
