package rs.raf.services;

import rs.raf.entities.Destination;
import rs.raf.repositories.destination.DestinationRepository;

import javax.inject.Inject;
import java.util.List;

public class DestinationService {

    @Inject
    private DestinationRepository destinationRepository;

    public DestinationService() {
        System.out.println("Creating DestinationService");
    }

    public Destination addDestination(Destination destination) {
        return this.destinationRepository.addDestination(destination);
    }
    public Destination findDestination(String name) {
        return this.destinationRepository.findDestination(name);
    }
    public List<Destination> allDestinations() {
        return this.destinationRepository.allDestinations();
    }


}
