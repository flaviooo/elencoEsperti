package it.ccse.bandiEsperti.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger atomicInteger;
   
    public Counter(int initialValue){
        this.atomicInteger = new AtomicInteger(initialValue); 
    }
    
    public int getCounterMinus() {
        return atomicInteger.getAndDecrement();
    }
    
    public int getCounterAdd() {
        return atomicInteger.getAndIncrement();
    }   
}
