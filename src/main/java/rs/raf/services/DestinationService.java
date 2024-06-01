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

    public String removeDestination(Destination destination) {
        return this.destinationRepository.removeDestination(destination);
    }
    public Destination updateDestination(Destination destination) {
        return this.destinationRepository.updateDestination(destination);
    }
    public Destination findDestination(Long id) {
        return this.destinationRepository.findDestination(id);
    }

}
