package org.softwarecave.springboottours;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Service
@Transactional
public class RandomNumberGenService {

    private static final String SECURE_ALGORITHM = "L64X256MixRandom";

    private final RandomGeneratorFactory defaultFactory = RandomGeneratorFactory.getDefault();
    private final RandomGeneratorFactory secureFactory = RandomGeneratorFactory.of(SECURE_ALGORITHM);

    public int generateRandomNumber(int min, int max) {
        RandomGenerator randomGenerator = defaultFactory.create();
        return randomGenerator.nextInt(min, max + 1);
    }

    // generate method to generate a secure random  number between two arguments
    public int generateSecureRandomNumber(int min, int max) {
        RandomGenerator randomGenerator = secureFactory.create();
        return randomGenerator.nextInt(min, max + 1);
    }
}
