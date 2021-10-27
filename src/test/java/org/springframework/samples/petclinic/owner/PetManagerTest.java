package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Stubbing;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;
import org.springframework.samples.petclinic.visit.Visit;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;

/**
 * @author      omid amini <omid.amini@ut.ac.ir>
 * @version     1          
 * @since       1
 * <p>
 *   Dummy object — used when a parameter is needed for the tested method but without actually needing to use the parameter
 *   Test stub    — used for providing the tested code with "indirect input"
 *   Test spy     — used for verifying "indirect output" of the tested code, by asserting the expectations afterwards, without having defined the expectations before the tested code is executed
 *   Mock object  — used for verifying "indirect output" of the tested code, by first defining the expectations before the tested code is executed
 *   Fake object  — used as a simpler implementation, e.g. using an in-memory database in the tests instead of doing real database access
 * </p>
 * 
 * <p>
 *   all of the tests are Mockisty ( atleast i hope they are lol )
 * </p>
 * 
 * <p>
 *   state verification means you're verifying that the code under test returns the right results
 *   behavioral verification means you're verifying that the code under test calls certain methods properly
 * </p>
 * 
 * <p>
 *   honestly mockisty looks better when SUT and components and their relation is more complicated
 *   otherwise classical would do the job just fine
 * </p>
 * 
 */
class PetManagerTest {
    /**
     * Unit under test !
     */
    PetManager unit;

    /**
     * pets double
     */
    PetTimedCache pets;
    
    /**
     * owners double
     */
    OwnerRepository owners;

    /**
     * logger double
     */
    Logger log;

    /**
     * owner double
     */
    Owner o;

    /**
     * pet double
     */
    Pet p;

    /**
     * setup function
     */
    @BeforeEach
    public void setup(){
        /** 
         * Create Interacting Components
         */
        this.pets = mock(PetTimedCache.class);
        this.owners = mock(OwnerRepository.class);
        this.log = spy(Logger.class);
        
        /**
         * Create SUT
         */
        this.unit = new PetManager(this.pets, this.owners, this.log);

        /** 
         * stubs 
        */
        this.o = new Owner();
        o.setId(1);
        this.p = new Pet();
        p.setId(100);
    }

    /**
     * Verifies that SUT logs the call
     * using owner stub and log spy
     * behavioral verification
     */
    @Test
    public void shouldLogFindOwner(){
        this.unit.findOwner(o.getId());
        verify(this.log).info("find owner {}", o.getId());
    }

    /**
     * Verifies that SUT returns
     * using owner stub and log spy
     * state verification
     */
    @Test
    public void shouldReturnFindOwner(){
        doReturn(o).when(this.owners).findById(o.getId());
        assertEquals(this.unit.findOwner(o.getId()), o);
    }

    /**
     * Verifies that SUT logs the call
     * using owner stub and log spy
     * behavioral verification
     */
    @Test
    public void shouldLogNewPet(){
        this.unit.newPet(o);
        verify(this.log).info("add pet for owner {}", o.getId());
    }

    /**
     * Verifies that SUT links new pet to owner
     * using a spy on the owner
     * behavioral verification
     */
    @Test
    public void shouldAssociateNewPet(){
        Owner no = spy(Owner.class);
        Pet p = unit.newPet(no);
        verify(no).addPet(p);
    }

    /**
     * Verifies that SUT logs findPet
     * using pet stub and log spy
     * behavioral verification
     */
    @Test
    public void shouldLogFindPet(){
        this.unit.findPet(p.getId());
        verify(log).info("find pet by id {}", p.getId());
    }

    /** 
     * Verifies that SUT finds pets
     * using pet stub and pets mock
     * state verification
    */
    @Test
    public void shouldReturnFindPet(){
        doReturn(p).when(pets).get(p.getId());
        assertEquals(p, unit.findPet(p.getId()));
    }

    /**
     * Verifies that SUT logs savePet
     * using pet & owner stubs and log spy
     * behavioral verification
     */
    @Test
    public void shouldLogSavePet(){
        unit.savePet(p, o);
        verify(log).info("save pet {}", p.getId());
    }

    /**
     * Verifies that SUT does addPet on owner
     * using owner spy and pet stub
     * behavioral verification
     */
    @Test
    public void shouldAddPetOnSavePet(){
        Owner no = mock(Owner.class);
        unit.savePet(p, no);
        verify(no).addPet(p);
    }

    /**
     * Verifies that SUT saves pets
     * using pet and owner stubs and pets spy
     * behavioral verification
     */
    @Test
    public void shouldSavePet(){
        unit.savePet(p, o);
        verify(pets).save(p);
    }

    /**
     * Verifies that SUT logs getOwnerPets
     * using log spy and everything else is literal dummy
     * behavioral verification
     */
    @Test
    public void shouldLogGetOwnerPets(){
        Owner no = mock(Owner.class);
        doReturn(no).when(owners).findById(1);
        List<Pet> npets = new ArrayList<Pet>();
        doReturn(npets).when(no).getPets();
        unit.getOwnerPets(1);
        verify(log).info("finding the owner's pets by id {}", 1);
        verify(log).info("find owner {}", 1); // this is bullcrap m8
    }

    /**
     * Verifies that SUT returns owner's pets
     * using owner stub and owners stub
     * state verification
     */
    @Test
    public void shouldReturnOwnerPets(){
        Owner no = mock(Owner.class);
        doReturn(no).when(owners).findById(1);
        List<Pet> npets = new ArrayList<Pet>();
        doReturn(npets).when(no).getPets();
        List<Pet> result = unit.getOwnerPets(1);
        
        assertEquals(npets, result);
    }

    /**
     * Verifies that SUT logs getOwnerPetTypes
     * using log spy and everything else is literal dummy
     * behavioral verification
    */
    @Test
    public void shouldLogGetOwnerPetTypes(){
        Owner no = mock(Owner.class);
        doReturn(no).when(owners).findById(1);
        List<Pet> npets = new ArrayList<Pet>();
        doReturn(npets).when(no).getPets();
        
        unit.getOwnerPetTypes(1);

        verify(log).info("finding the owner's petTypes by id {}", 1);
        verify(log).info("find owner {}", 1); // this is bullcrap m8
    }

    /**
     * Verifies that SUT returns owner pet types
     * owner and owners stub
     * state verification
     */
    @Test
    public void shouldReturnOwnerPetTypes(){
        Owner no = mock(Owner.class);
        doReturn(no).when(owners).findById(1);
        List<Pet> npets = new ArrayList<Pet>();
        doReturn(npets).when(no).getPets();
        
        Set<PetType> result = unit.getOwnerPetTypes(1);

        assertEquals(result, new HashSet<PetType>());
    }

    /**
     * Verifies that SUT logs getVisitsBetween
     * using log spy and bunch of dummies
     * behavioral verification
     */
    @Test
    public void shouldLogGetVisitsBetween(){
        Pet np = mock(Pet.class);
        doReturn(np).when(pets).get(1);
        List<Visit> visits = new ArrayList<Visit>();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(7);
        doReturn(visits).when(np).getVisitsBetween(start, end);

        unit.getVisitsBetween(1, start, end);

        verify(log).info("get visits for pet {} from {} since {}", 1, start, end);
    }

    /**
     * Verifies that SUT returns visits between
     * using pet, visits, start and end stubs
     * state verification
     */
    @Test
    public void getVisitsBetweenResultTest(){
        Pet np = mock(Pet.class);
        doReturn(np).when(pets).get(1);
        List<Visit> visits = new ArrayList<Visit>();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(7);
        doReturn(visits).when(np).getVisitsBetween(start, end);

        List<Visit> result = unit.getVisitsBetween(1, start, end);

        assertEquals(visits, result);
    }

}
