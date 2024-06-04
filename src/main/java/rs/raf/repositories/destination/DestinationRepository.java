package rs.raf.repositories.destination;

import rs.raf.entities.Destination;

import java.util.List;

public interface DestinationRepository {

    Destination addDestination(Destination destination);
    Destination findDestination(String name);
    long countDestinations();
    List<Destination> allDestinations(int page, int size);
    String removeDestination(Destination destination);
    Destination updateDestination(Destination destination);
    Destination findDestination(Long id);
}
