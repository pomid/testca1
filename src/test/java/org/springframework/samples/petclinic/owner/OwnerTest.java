package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

class OwnerTest {

	@Test
	void testCommonData(){
		Owner o = new Owner();

		String randomAddress = "random address";
		String randomPhone = "0000000000";
		String randomCity = "random city";

		o.setAddress(randomAddress);
		o.setTelephone(randomPhone);
		o.setCity(randomCity);

		assertEquals(randomAddress, o.getAddress());
		assertEquals(randomPhone, o.getTelephone());
		assertEquals(randomCity, o.getCity());
	}

	@Test
	void testAddress(){
		Owner o = new Owner();
		String r = "random Address";
		o.setAddress(r);
		assertEquals(r, o.getAddress());
	}

	@Test
	void testTelephone(){
		Owner o = new Owner();
		String r = "random Telephone";
		o.setTelephone(r);
		assertEquals(r, o.getTelephone());
	}

	@Test
	void testCity(){
		Owner o = new Owner();
		String r = "random City";
		o.setCity(r);
		assertEquals(r, o.getCity());
	}

	@Test
	void testPets(){
		Pet a = new Pet();
		Pet b = new Pet();

		a.setName("a");
		b.setName("b");

		Owner o = new Owner();

		o.addPet(a);
		o.addPet(b);

		List<Pet> pl = new ArrayList<Pet>();
		
        pl.add(a);
		pl.add(b);
		assertEquals(Collections.unmodifiableList(pl), o.getPets());

        o.removePet(a);
        pl.remove(a);
        assertEquals(Collections.unmodifiableList(pl), o.getPets());
	}

}
