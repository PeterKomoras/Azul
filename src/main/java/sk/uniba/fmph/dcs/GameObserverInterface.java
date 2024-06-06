package sk.uniba.fmph.dcs;

public interface GameObserverInterface {
    void notifyEverybody(String state);
    void registerObserver(ObserverInterface observer);
    void cancelObserver(ObserverInterface observer);
}
