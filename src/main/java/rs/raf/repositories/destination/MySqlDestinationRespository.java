package rs.raf.repositories.destination;

import rs.raf.entities.Destination;
import rs.raf.repositories.MySqlAbstractRepository;

import java.util.List;

public class MySqlDestinationRespository extends MySqlAbstractRepository implements DestinationRepository{
    @Override
    public Destination addDestination(Destination destination) {
        return null;
    }

    @Override
    public Destination findDestination(Integer id) {
        return null;
    }

    @Override
    public List<Destination> allDestinations() {
        return null;
    }
}
