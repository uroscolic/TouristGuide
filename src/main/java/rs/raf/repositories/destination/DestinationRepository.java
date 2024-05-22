package rs.raf.repositories.destination;

import rs.raf.entities.Destination;

import java.util.List;

public interface DestinationRepository {

    public Destination addDestination(Destination destination);
    public Destination findDestination(Integer id);
    public List<Destination> allDestinations();
}
