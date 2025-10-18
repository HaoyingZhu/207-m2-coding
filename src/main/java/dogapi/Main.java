package dogapi;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String breed = "hound";

        BreedFetcher realFetcher = new CachingBreedFetcher(new DogApiBreedFetcher());

        System.out.println("--- Demonstrating BreedFetcher ---");

        System.out.println("Querying for breed: " + breed);
        int result = getNumberOfSubBreeds(breed, realFetcher);
        System.out.println("Result: " + breed + " has " + result + " sub-breeds.");

        String invalidBreed = "cat";
        System.out.println("\nQuerying for breed: " + invalidBreed);
        result = getNumberOfSubBreeds(invalidBreed, realFetcher);
        System.out.println("Result: " + invalidBreed + " has " + result + " sub-breeds.");
    }

    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) {
        try {
            List<String> subBreeds = breedFetcher.getSubBreeds(breed);
            return subBreeds.size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }
}