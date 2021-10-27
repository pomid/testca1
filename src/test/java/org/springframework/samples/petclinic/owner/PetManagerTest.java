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

class PetManagerTest {
    
    PetManager unit;
    PetTimedCache pets;
    OwnerRepository owners;
    Logger log;
    Owner o;
    Pet p;

    @BeforeEach
    public void setup(){
        this.pets = mock(PetTimedCache.class);
        this.owners = mock(OwnerRepository.class);
        this.log = spy(Logger.class);
        this.unit = new PetManager(this.pets, this.owners, this.log);


        this.o = new Owner();
        o.setId(1);
        this.p = new Pet();
        p.setId(100);
    }

    @Test
    public void findOwnerLogTest(){
        this.unit.findOwner(o.getId());
        verify(this.log).info("find owner {}", o.getId());
    }

    @Test
    public void findOwnerResultTest(){
        doReturn(o).when(this.owners).findById(o.getId());
        assertEquals(this.unit.findOwner(o.getId()), o);
    }

    @Test
    public void newPetLogTest(){
        this.unit.newPet(o);
        verify(this.log).info("add pet for owner {}", o.getId());
    }

    @Test
    public void newPetRelationTest(){
        Owner no = spy(Owner.class);
        Pet p = unit.newPet(no);
        verify(no).addPet(p);
    }

    @Test
    public void findPetLogTest(){
        this.unit.findPet(p.getId());
        verify(log).info("find pet by id {}", p.getId());
    }

    @Test
    public void findPetResultTest(){
        doReturn(p).when(pets).get(p.getId());
        assertEquals(p, unit.findPet(p.getId()));
    }

    @Test
    public void savePetLogTest(){
        unit.savePet(p, o);
        verify(log).info("save pet {}", p.getId());
    }

    @Test
    public void savePetOwnerCheck(){
        Owner no = mock(Owner.class);
        unit.savePet(p, no);
        verify(no).addPet(p);
    }

    @Test
    public void savePetPetsCheck(){
        unit.savePet(p, o);
        verify(pets).save(p);
    }

    @Test
    public void getOwnerPetsLogTest(){
        Owner no = mock(Owner.class);
        doReturn(no).when(owners).findById(1);
        List<Pet> npets = new ArrayList<Pet>();
        doReturn(npets).when(no).getPets();
        unit.getOwnerPets(1);
        verify(log).info("finding the owner's pets by id {}", 1);
        verify(log).info("find owner {}", 1); // this is bullcrap m8
    }

    @Test
    public void getOwnerPetsResultTest(){
        Owner no = mock(Owner.class);
        doReturn(no).when(owners).findById(1);
        List<Pet> npets = new ArrayList<Pet>();
        doReturn(npets).when(no).getPets();
        List<Pet> result = unit.getOwnerPets(1);
        
        assertEquals(npets, result);
    }

    @Test
    public void getOwnerPetTypesLogTest(){
        Owner no = mock(Owner.class);
        doReturn(no).when(owners).findById(1);
        List<Pet> npets = new ArrayList<Pet>();
        doReturn(npets).when(no).getPets();
        
        unit.getOwnerPetTypes(1);

        verify(log).info("finding the owner's petTypes by id {}", 1);
        verify(log).info("find owner {}", 1); // this is bullcrap m8
    }

    @Test
    public void getOwnerPetTypesResultTest(){
        Owner no = mock(Owner.class);
        doReturn(no).when(owners).findById(1);
        List<Pet> npets = new ArrayList<Pet>();
        doReturn(npets).when(no).getPets();
        
        Set<PetType> result = unit.getOwnerPetTypes(1);

        assertEquals(result, new HashSet<PetType>());
    }

    @Test
    public void getVisitsBetweenLogTest(){
        Pet np = mock(Pet.class);
        doReturn(np).when(pets).get(1);
        List<Visit> visits = new ArrayList<Visit>();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(7);
        doReturn(visits).when(np).getVisitsBetween(start, end);

        unit.getVisitsBetween(1, start, end);

        verify(log).info("get visits for pet {} from {} since {}", 1, start, end);
    }

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
