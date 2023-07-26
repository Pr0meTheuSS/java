// Copyright 2023 Olimpiev Y.
package AbstractFactory;

public interface AbstractFactory <T> {
    
    /**
     * @param id - unique product's identifier.
     * @return Product type instance.
     */
    T get(String id);
    
    /**
     * @param id
     * @param productClassName
     * @return Successful of operation.
     */
    boolean register(String id, String productClassName);
    
    /**
     * @param id - unique product's identifier.
     * @return Successful of operation.
     */
    boolean unregister(String id);
}
