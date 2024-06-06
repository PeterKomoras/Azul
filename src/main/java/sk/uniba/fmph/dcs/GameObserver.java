package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class GameObserver implements GameObserverInterface{
    private final List<ObserverInterface> observers = new ArrayList<>();
    @Override
    public void notifyEverybody(String state){
        for (ObserverInterface observer: observers) {
            observer.notify(state);
        }
    }
    @Override
    public void registerObserver(ObserverInterface observer) {
        this.observers.add(observer);
    }
    @Override
    public void cancelObserver(ObserverInterface observer) {
        this.observers.remove(observer);
    }

}
