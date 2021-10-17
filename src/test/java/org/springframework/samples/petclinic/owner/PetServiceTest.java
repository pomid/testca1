package org.springframework.samples.petclinic.owner;

import org.slf4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class PetServiceTest {

    public String[][] actions;

    public PetServiceTest(String[][] actions){
        this.actions = actions;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{

            }
        );
    }

}
