package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
class FakeObserver implements ObserverInterface {
    private String state = null;
    @Override
    public void notify(String state){
        this.state = state;
    }
    public String getState() {
        return state;
    }
}
public class GameObserverTest {
    FakeObserver observer1, observer2, observer3;
    GameObserverInterface gameObserver;

    @Before
    public void setUp(){
        observer1 = new FakeObserver();
        observer2 = new FakeObserver();
        observer3 = new FakeObserver();
        gameObserver = new GameObserver();
    }

    @Test
    public void testObserver() {

        gameObserver.notifyEverybody("");
        assertNull("Concrete observer's state at start should be empty", observer1.getState());
        gameObserver.registerObserver(observer1);
        gameObserver.registerObserver(observer2);
        assertNull("Concrete observer's state at start should be empty", observer1.getState());
        gameObserver.notifyEverybody("Start game");
        assertEquals("Observer's state after notify should something contains", "Start game", observer1.getState());
        assertEquals("Observer's state after notify should something contains", "Start game", observer2.getState());
        assertNull("Concrete observer's state at start should be empty", observer3.getState());
        gameObserver.cancelObserver(observer3);
        gameObserver.notifyEverybody("Start new game");
        assertEquals("Observer's state after notify should contains notified state", "Start new game", observer1.getState());
        assertEquals("Observer's state after notify should contains notified state", "Start new game", observer2.getState());
        gameObserver.cancelObserver(observer1);
        gameObserver.registerObserver(observer3);
        gameObserver.notifyEverybody("End of game");
        assertEquals("Observer's state after cancelling shouldn't changed", "Start new game", observer1.getState());
        assertEquals("Observer's state after notify should contains notified state", "End of game", observer2.getState());
        assertEquals("Observer's state after notify should contains notified state", "End of game", observer3.getState());
        gameObserver.cancelObserver(observer2);
        gameObserver.cancelObserver(observer3);
        gameObserver.registerObserver(observer1);
        gameObserver.notifyEverybody("Player 2 won game");
        assertEquals("Observer's state after notify should contains notified state", "Player 2 won game", observer1.getState());
        assertEquals("Observer's state after cancelling shouldn't changed", "End of game", observer2.getState());
        assertEquals("Observer's state after cancelling shouldn't changed", "End of game", observer3.getState());
    }
}