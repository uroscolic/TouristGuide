package rs.raf.repositories.destination;

import rs.raf.entities.Destination;

import java.util.List;

public interface DestinationRepository {

    public Destination addDestination(Destination destination);
    public Destination findDestination(String name);
    public List<Destination> allDestinations();
}
