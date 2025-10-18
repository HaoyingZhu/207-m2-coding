package dogapi;

import java.util.List;

public interface BreedFetcher {

    /**
     * Fetch the list of sub breeds for the given breed.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist
     */
    List<String> getSubBreeds(String breed) throws BreedNotFoundException;


    /**
     * Custom exception for when a breed is not found.
     */
    class BreedNotFoundException extends Exception {

        /**
         * Constructor that takes a message.
         * @param message the detail message.
         */
        public BreedNotFoundException(String message) {
            super(message);
        }

        /**
         * Constructor that takes a message and a cause.
         * This is essential for exception chaining.
         * @param message the detail message.
         * @param cause the cause of the exception.
         */
        public BreedNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}